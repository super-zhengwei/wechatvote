package com.vote.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

import com.jfinal.kit.LogKit;
import com.jfinal.render.Render;

/**
 * 类用途说明：图片预览
 * By：厦门同创空间信息技术有限公司 www.chinaservices.com.cn
 * @author zhengwei
 */
public class ImageRender extends Render {
	private BufferedImage image;
	
	public ImageRender(BufferedImage image) {
		this.image = image;
	}
	
	public void render() {
		
		response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream sos = null;
        try {
			sos = response.getOutputStream();
			ImageIO.write(image, "jpg",sos);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if (sos != null) {
				try {sos.close();} catch (IOException e) {LogKit.logNothing(e);}
			}
		}
	}
}