package org.abimon.imageobscurity;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Random;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.abimon.omnis.io.Data;

public class ImageObscurity {

	public static BufferedImage obscure(Data data, char[] key) throws IOException{

		int pixels = (data.length() / 3) + (data.length() % 3 == 0 ? 0 : 1);

		int width = (int) Math.sqrt(pixels);
		int height = width + (width * width == pixels ? 0 : 1);

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		InputStream in = data.getAsInputStream();

		int keyCounter = 0;

		if(key.length < 3)
			key = "KEY".toCharArray();

		for(int x = 0; x < img.getWidth(); x++){
			for(int y = 0; y < img.getHeight(); y++){
				int r1 = (in.read());
				int g1 = (in.read());
				int b1 = (in.read());

				int a = 255;

				if(r1 == -1){
					r1 = new Random().nextInt(256);
					a--;
				}
				if(g1 == -1){
					g1 = new Random().nextInt(256);
					a--;
				}
				if(b1 == -1){
					b1 = new Random().nextInt(256);
					a--;
				}
				
				byte r = (byte) (r1 ^ key[keyCounter++]);

				if(keyCounter >= key.length)
					keyCounter = 0;
				
				byte g = (byte) (g1 ^ key[keyCounter++]);

				if(keyCounter >= key.length)
					keyCounter = 0;
				
				byte b = (byte) (b1 ^ key[keyCounter++]);

				if(keyCounter >= key.length)
					keyCounter = 0;

				img.setRGB(x, y, new Color(r & 0xFF, g & 0xFF, b & 0xFF, a).getRGB());
			}
			System.out.println("Completed column " + (x+1));
		}

		return img;
	}

	public static Data clarify(BufferedImage img, char[] key) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		int keyCounter = 0;

		if(key.length < 3)
			key = "KEY".toCharArray();

		for(int x = 0; x < img.getWidth(); x++){
			for(int y = 0; y < img.getHeight(); y++){
				Color c = new Color(img.getRGB(x, y), true);
				
				byte r = (byte) c.getRed();
				byte g = (byte) c.getGreen();
				byte b = (byte) c.getBlue();

				byte red = (byte) (r ^ key[keyCounter++]);

				if(keyCounter >= key.length)
					keyCounter = 0;

				byte green = (byte) (g ^ key[keyCounter++]);

				if(keyCounter >= key.length)
					keyCounter = 0;

				byte blue = (byte) (b ^ key[keyCounter++]);

				if(keyCounter >= key.length)
					keyCounter = 0;

				int alpha = c.getAlpha();

				if(alpha == 255)
					out.write(new byte[]{(byte) red, (byte) green, (byte) blue});
				if(alpha == 254)
					out.write(new byte[]{(byte) red, (byte) green});
				if(alpha == 253)
					out.write((byte) red);
			}
			System.out.println("Completed column " + (x+1));
		}

		return new Data(out.toByteArray());
	}
}
