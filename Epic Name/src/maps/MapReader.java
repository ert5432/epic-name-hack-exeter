package maps;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import entities.Entity;
import geometry.Polygon;
import geometry.Rectangle;
import world.BlockWall;
import world.Wall;
import world.World;

public class MapReader {
	
	public static int[] readImage(String file){
		
		int[] pixels=new int[0];
		
		
		try {
			BufferedImage image=ImageIO.read(MapReader.class.getResource(file));
			int width=image.getWidth();
			int height=image.getHeight();
			pixels=new int[width*height+2];
			image.getRGB(0, 0,width, height, pixels,0,width);
			
			pixels[pixels.length-2]=width;
			pixels[pixels.length-1]=height;
			
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
	
	//absolute black images are walls
	public static void readMap(String map,World world){
		int[] layout=readImage(map);
		int width=layout[layout.length-2];
		int height=layout[layout.length-1];
		layout=Arrays.copyOfRange(layout, 0, layout.length-2);
		
		ArrayList<BlockWall> walls= new ArrayList<BlockWall>();
		ArrayList<Entity> entities= new ArrayList<Entity>();
		BlockWall lastWall=new BlockWall(0,0,0,0);
		boolean wallMade=false;
		for(int i=0;i<layout.length;i++){
			int x=(i%width)*40;
			int y=(i/height)*40;
			switch(layout[i]){
				case 0:{
					if(wallMade&&x!=0){
						Polygon r=lastWall.getRect();
						lastWall.setRect(lastWall.x,lastWall.y,lastWall.width+40,lastWall.height);
					}
					else{
						lastWall=new BlockWall(x,y,40,40);
						walls.add(lastWall);
					}
					break;
				}
				case 0x0000ff:{
					
				}
			}
			if(layout[i]==0)
				wallMade=true;
			else
				wallMade=false;
		}
		
		boolean complete=true;
		do{
		complete=true;
		for(int i=0;i<walls.size()-1;i++){
			for(int b=i+1;b<walls.size();b++){
				if(walls.get(i).merge(walls.get(b)))
					complete=false;
			}
		}
		}while(!complete);
		
		for(Entity e:entities)
			world.addEntity(e);
		for(BlockWall e:walls){
			if(e.width!=0)
			world.addWall(e);
		}
	}
	
	public static void spawnMonster(int x,int y,int id){
		
	}
	
	public static void main(String args[]){
		World w=new World();
		readMap("map1.png", w);
		System.out.println(w.getWalls().size());
	}
	
}
