var recLength = 0,
    recBuffersL = [],
    recBuffersR = [],
    sampleRate;

this.onmessage = function (e) {
    switch (e.data.command)
    {
        case "init":
            init(e.data.config);
            break;
        case "record":
            record(e.data.buffer);
            break;
        case "exportWAV":
            exportWAV(e.data.type);
            break;
        case "getBuffer":
            getBuffer();
            break;
        case "clear":
            clear();
            break;
    }
};

function init (config)
{
    sampleRate = config.sampleRate;
}

function record (inputBuffer)
{
    recBuffersL.push(inputBuffer[0]);
    recBuffersR.push(inputBuffer[1]);
    recLength += inputBuffer[0].length;
}

function exportWAV (type)
{
    var bufferL = mergeBuffers(recBuffersL, recLength);
    // 变为单声道，所以去掉右声道    
    // var bufferR = mergeBuffers(recBuffersR, recLength);
    // var interleaved = interleave(bufferL, bufferR);
    var interleaved = interleave(bufferL);
    var dataview = encodeWAV(interleaved);
    var audioBlob = new Blob([dataview], {
        type: type
    });

    this.postMessage(audioBlob);
}

function getBuffer ()
{
    var buffers = [];
    buffers.push(mergeBuffers(recBuffersL, recLength));
    buffers.push(mergeBuffers(recBuffersR, recLength));
    this.postMessage(buffers);
}

function clear ()
{
    recLength = 0;
    recBuffersL = [];
    recBuffersR = [];
}

function mergeBuffers (recBuffers, recLength)
{
    var result = new Float32Array(recLength);
    var offset = 0;
    for (var i = 0; i < recBuffers.length; i++)
    {
        result.set(recBuffers[i], offset);
        offset += recBuffers[i].length;
    }
    return result;
}

// 变为单声道，所以去掉右声道    
//function interleave (inputL, inputR)
function interleave (inputL)
{
	/*
    //  var length = inputL.length + inputR.length;
    var length = inputL.length;
    var result = new Float32Array(length);

    var index = 0,
        inputIndex = 0;

    while (index < length)
    {
        result[index++] = inputL[inputIndex];
        // result[index++] = inputR[inputIndex];
        inputIndex++;
    }
    return result;
    */
	
	// 减小采样率到11025
    var compression = 44100 / 11025;	//计算压缩率 
    var length = inputL.length / compression;
    var result = new Float32Array(length);

    var index = 0,
      	inputIndex = 0;

    while (index < length) {
      result[index] = inputL[inputIndex];
      inputIndex += compression;//每次都跳过3个数据
      index++;
    }
    return result;
}

function floatTo16BitPCM (output, offset, input)
{
    for (var i = 0; i < input.length; i++, offset+=2)
    {
        var s = Math.max(-1, Math.min(1, input[i]));
        output.setInt16(offset, s < 0 ? s * 0x8000 : s * 0x7FFF, true);
    }
}

function writeString(view, offset, string)
{
	for (var i = 0; i < string.length; i++)
	{
		view.setUint8(offset + i, string.charCodeAt(i));
	}
}

function encodeWAV (samples)
{
	var dataLength = samples.length * 2;
	var buffer = new ArrayBuffer(44 + dataLength);
    var view = new DataView(buffer);

    // 减小采样率到11025
    // var sampleRateTmp = sampleRate;
    var sampleRateTmp = 11025;
    var sampleBits = 16;
    var channelCount = 1;
    var offset = 0;

    /* RIFF identifier */
    /* 资源交换文件标识符 */
    writeString(view, offset, "RIFF");
    offset += 4;

    /* file length */
    /* 下个地址开始到文件尾总字节数,即文件大小-8 */
    /* 原来例子是32，应该是36？ */
    view.setUint32(offset, 36 + dataLength, true);
    offset += 4;

    /* RIFF type */
    /* WAV文件标志 */
    writeString(view, offset, "WAVE");
    offset += 4;

    /* format chunk identifier */
    /* 波形格式标志 */
    writeString(view, offset, "fmt ");
    offset += 4;

    /* format chunk identifier */
    /* 过滤字节，一般为 0 * 10 = 16 */
    view.setUint32(offset, 16, true);
    offset += 4;

    /* sample format (raw) */
    /* 格式类别 (PCM形式采样数据) */
    view.setUint16(offset, 1, true);
    offset += 2;

    /* channel count */
    /* 通道数 */
    view.setUint16(offset, channelCount, true);
    offset += 2;

    /* sample rate */
    /* 采样率，每秒样本数，表示每个通道的播放速度 */
    view.setUint32(offset, sampleRateTmp, true);
    offset += 4;

    /* byte rate (sample rate * block align) */
    /* 波形数据传输率 (每秒平均字节数)：通道数 * 每秒数据位数 * 每样本数据位 / 8 */
    view.setUint32(offset, sampleRateTmp * channelCount * (sampleBits / 8), true);
    offset += 4;

    /* block align (channel count * bytes per sample) */
    /* 快数据调整数，采样一次占用字节数：通道数 * 每样本的数据位数 / 8 */
    view.setUint16(offset, channelCount * (sampleBits / 8), true);
    offset += 2;

    /* bits per sample */
    /* 每样本数据位数 */
    view.setUint16(offset, sampleBits, true);
    offset += 2;

    /* data chunk identifier */
    /* 数据标识符 */
    writeString(view, offset, 'data');
    offset += 4;

    /* data chunk length */
    /* 采样数据总数，即数据总大小-44 */
    view.setUint32(offset, dataLength, true);
    offset += 4;

    /*
    // RIFF identifier
    writeString(view, 0, "RIFF");
    //file length
    view.setUint32(4, 32 + samples.length * 2, true);
    // RIFF type
    writeString(view, 8, "WAVE");
    // format chunk identifier
    writeString(view, 12, "fmt ");
    // format chunk length
    view.setUint32(16, 16, true);
    // sample format (raw)
    view.setUint16(20, 1, true);
    // channel count
    view.setUint16(22, 2, true);
    // sample rate
    view.setUint32(24, sampleRate, true);
    // byte rate (sample rate * block align)
    view.setUint32(28, sampleRate * 4, true);
    // block align (channel count * bytes per sample)
    view.setUint16(32, 4, true);
    // bits per sample
    view.setUint16(34, 16, true);
    // data chunk identifier
    writeString(view, 36, "data");
    // data chunk length
    view.setUint32(40, samples.length * 2, true);
    */
    
    floatTo16BitPCM(view, 44, samples);

    return view;
}
