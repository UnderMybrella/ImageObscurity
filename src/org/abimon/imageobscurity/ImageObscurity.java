package org.abimon.imageobscurity;

import java.awt.image.BufferedImage;
import java.io.File;

import org.abimon.omnis.io.Data;

public class ImageObscurity {
	public static void main(String[] args) throws Throwable{
		BufferedImage img = obscure(new Data(new File("SmellsLikeRickAstley.mp4")));
	}
	
	public static BufferedImage obscure(Data data){
		
		int pixels = (data.length() / 3) + (data.length() % 3 == 0 ? 0 : 1);
		
		int width = (int) Math.sqrt(pixels);
		int height = width + (width * width == pixels ? 0 : 1);
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		
		
		return img;
	}
}
