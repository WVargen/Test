package com.liangli.nj.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {
	public static void reduceImageEqualProportion(String srcImagePath,String destImagePath,double ratio, String imageType) {
		File destImageFile = new File(destImagePath);
		File srcImageFile = new File(srcImagePath);
		
		try {
			BufferedImage srcImage = ImageIO.read(srcImageFile);
			int width = (int) (srcImage.getWidth() * ratio);
			int height = (int) (srcImage.getHeight() * ratio);
			
			BufferedImage reduceImage = new BufferedImage(width, height, srcImage.getType());
			reduceImage.getGraphics().drawImage(srcImage, 0, 0, width, height, null);
			
			ImageIO.write(reduceImage, imageType, destImageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
