package com.challengr.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.ImageIcon;

import sun.misc.BASE64Decoder;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {
	/**
	 * 缩放图片
	 * @param originalFile 原图
 	 * @param resizedFile 新图
	 * @param newWidth 新的宽度
	 * @param quality 缩放质量
	 * @throws IOException
	 */
	public static void resize(String originalImagePath, String resizedImagePath,
			int newWidth, float quality) throws IOException {
		File originalFile = new File(originalImagePath);
		File resizedFile = new File(resizedImagePath);
		
		// 过滤掉不存在的图片
		if(!originalFile.exists()) {
			System.out.println("文件不存在！" + originalFile.getAbsolutePath());
			return;
		}
		
		// 过滤掉不能读取的图片
		if(!originalFile.exists()) {
			System.out.println("图片不能读取！" + originalFile.getAbsolutePath());
			return;
		}

		if(newWidth <= 50) {
			throw new IllegalArgumentException("亲，人家这么小要用显微镜看了啦！");
		}
		
		if (quality > 1 && quality<=0) {
			throw new IllegalArgumentException(
					"压缩系数只能在0-1之间！");
		}

		ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
		Image i = ii.getImage();
		Image resizedImage = null;

		int iWidth = i.getWidth(null);
		if(iWidth<=5) {
			return;
		}
		int iHeight = i.getHeight(null);

		if (iWidth > iHeight) {
			resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)
					/ iWidth, Image.SCALE_SMOOTH);
		} else {
			resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,
					newWidth, Image.SCALE_SMOOTH);
		}

		// This code ensures that all the pixels in the image are loaded.
		Image temp = new ImageIcon(resizedImage).getImage();

		// Create the buffered image.
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
				temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

		// Copy image to buffered image.
		Graphics g = bufferedImage.createGraphics();

		// Clear background and paint the image.
		g.setColor(Color.white);
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
		g.drawImage(temp, 0, 0, null);
		g.dispose();

		// Soften.
		float softenFactor = 0.05f;
		float[] softenArray = { 0, softenFactor, 0, softenFactor,
				1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bufferedImage = cOp.filter(bufferedImage, null);

		// Write the jpeg to a file.
		FileOutputStream out = new FileOutputStream(resizedFile);

		// Encodes image as a JPEG data stream
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

		JPEGEncodeParam param = encoder
				.getDefaultJPEGEncodeParam(bufferedImage);

		param.setQuality(quality, true);

		encoder.setJPEGEncodeParam(param);
		encoder.encode(bufferedImage);
	} 
	
	/**
	 *  将base64编码的图片保存成为jpg格式
	 * @param imgStr base64编码内容
	 * @param imgFilePath 文件保存路径
	 */
		public static void GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图�?
	        if (imgStr == null) // 图像数据为空
	        	System.out.println("图像转换失败！");
	        BASE64Decoder decoder = new BASE64Decoder();
	        OutputStream out = null;
	        try {
	            // Base64解码
	            byte[] bytes = decoder.decodeBuffer(imgStr);
	            for (int i = 0; i < bytes.length; ++i) {
	                if (bytes[i] < 0) {// 调整异常数据
	                    bytes[i] += 256;
	                }
	            }
	            // 生成jpeg图片
	            out = new FileOutputStream(imgFilePath);
	            out.write(bytes);
	        } catch (Exception e) {
	            System.out.println("图像转换失败！");
	        } finally {
	        	 try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }

}