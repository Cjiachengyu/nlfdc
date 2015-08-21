package com.kdl.nlfdc.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.kdl.nlfdc.domain.User;

/**
 * 操作Excel文件工具类
 * 
 * @author cjia
 *
 */
public class GenerateExcelUtils
{
    
    /**
     * 下载用户信息时生成Excel信息流
     *  
     * @param userList
     * @return
     */
    public static InputStream generateExcelStream(List<User> userList) throws IOException, WriteException
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        WritableWorkbook book = Workbook.createWorkbook(os);
        
        // 生成名为“第一页”的工作表，参数0表示这是第一页
        WritableSheet sheet = book.createSheet(" 第一页 ", 0);

        // 文字样式 第四个bool值表示是否斜体，true： 斜体
        WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10,
                WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
                jxl.format.Colour.RED);

        WritableCellFormat wcfFC = new WritableCellFormat(wfc);

        // 设置单元格样式
        wcfFC.setBackground(jxl.format.Colour.GRAY_25);// 单元格颜色
        wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中
        
        String[] cols = {"用户名","ID","登录名","邮箱"};
        
        for (int i = 0; i < cols.length; i++) {
            sheet.setColumnView(i, 25);
            Label label = new Label(i, 0, cols[i], wcfFC);
            sheet.addCell(label);
        }
        
        WritableCellFormat wcfFC2 = new WritableCellFormat();
        wcfFC2.setAlignment(jxl.format.Alignment.CENTRE);

        String[] values = new String[cols.length];
        for(int j = 0; j < userList.size(); j++)
        {
            User user = userList.get(j);
            values[0] = user.getUserName();
            values[1] = user.getLoginId();
            values[2] = user.getLoginName();
            values[3] = user.getEmail();
            
            for (int i = 0; i < cols.length; i++) {
                Label label = new Label(i, j+1, values[i], wcfFC2);
                sheet.addCell(label);
            }
        }
        
        book.write();
        book.close();
        
        byte[] content =os.toByteArray(); 
        InputStream is =new ByteArrayInputStream(content); 
        return is;
    }


}
