package com.kdl.nlfdc.action.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import com.kdl.nlfdc.action.AbstractActionBean;

/**
 * 获取校验码图片,将随机产生的字符串绘制成图片返回到网页并将该字符串存到session中，供提交验证码的时候进行判断
 * 
 * @author cjia
 *
 */
@SessionScope
@UrlBinding("/getimagecode")
public class GetImageCode extends AbstractActionBean
{
    private static final long serialVersionUID = -39845780250120945L;

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("getimage")
    public void getImage() throws IOException
    {
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 在内存中创建图象
        int width = 60, height = 20;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(220, 250));
        g.fillRect(0, 0, width, height);
        // 设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        // 画边框
        // g.drawRect(0,0,width-1,height-1);
        g.draw3DRect(0, 0, width - 1, height - 1, true);
        // 随机产生100条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 100; i++)
        {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的认证码(6位数字)
        StringBuilder sRand = new StringBuilder();
        String s = "0123456789ABCDEFGHIJK0123456789abcdefghigklmnopqrst0123456789uvwxyzLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < 4; i++)
        {
            char rand = s.charAt(random.nextInt(s.length()));
            sRand.append(rand);
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(String.valueOf(rand), 13 * i + 6, 16);
        }
        g.drawOval(0, 12, 60, 11);
        // 将认证码存入SESSION,不区分大小写
        request.getSession().setAttribute("identity_image_code", sRand.toString().toLowerCase());
        // 图象生效
        g.dispose();
        ServletOutputStream output = null;
        try
        {
            output = response.getOutputStream();
            // 输出图象到页面
            ImageIO.write(image, "JPEG", output);
        }
        catch (IOException e)
        {
        }
        finally
        {
            output.close();
        }
    }

    /**
     * 生成随机颜色
     */
    private Color getRandColor(int fc, int bc)
    {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
