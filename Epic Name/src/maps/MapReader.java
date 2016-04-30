package maps;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapReader {
	
	public static int[] readImage(String file){
		
		int[] pixels=new int[0];
		
		
		try {
			BufferedImage image=ImageIO.read(MapReader.class.getResource(file));
			int width=image.getWidth();
			int height=image.getHeight();
			pixels=new int[width*height];
			image.getRGB(0, 0,width, height, pixels,0,width);
			
			for(int x=0; x<width;x++){
				for(int y=0; y<height; y++){
					Color c=new Color(image.getRGB(x,y));
					int r=c.getRed();
					int b=c.getBlue();
					int g=c.getGreen();
					int rgb = r;
					rgb = (rgb << 8) + g;
					rgb = (rgb << 8) + b;
					pixels[x+y*width]=rgb;
				}
			}
		} catch (IOException e) {
			System.out.println("FILE NOT FOUND!");
			e.printStackTrace();
		}
		
		return pixels;
		
	}
	
	public static void main(String args[]){
		if(readImage("yee.jpg").length==0)
			System.out.println("YAAAA");
		else
			System.out.println(readImage("yee.jpg")[0]+"");
	}
	
}
