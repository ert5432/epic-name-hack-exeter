package maps;

import input.ViewScreen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import sound.SoundHandler;
import stats.Stats;
import entities.Ant;
import entities.Beholder;
import entities.Entity;
import entities.GameAgent;
import entities.GoblinArcher;
import entities.GoblinFighter;
import entities.Player;
import geometry.Circle;
import geometry.Polygon;
import geometry.Rectangle;
import geometry.Vector2D;
import weapons.Bow;
import weapons.PowerRod;
import weapons.Sword;
import world.BlockWall;
import world.FloorTile;
import world.Stairs;
import world.Wall;
import world.World;

public class MapReader {
	
	public static final int ANT=0,BEHOLDER=1,GOBLIN_ARCHER=2,GOBLIN_FIGHTER=3;
	
	public static int level=0;
	
	static ViewScreen vs;
	
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
	
	public static void readMap(String map,World world){
		readMap(map,world,0,0);
	}
	
	//absolute black images are walls
	public static void readMap(String map,World world,int dx,int dy){
		int[] layout=readImage(map);
		int width=layout[layout.length-2];
		int height=layout[layout.length-1];
		layout=Arrays.copyOfRange(layout, 0, layout.length-2);
		if(world.player==null){
			world.player=new Player(0,0,new Circle(20),world,new Stats(30,30,15,15,1000));
			world.player.addToInventory(new Sword(world.player));
			world.player.addToInventory(new Bow(world.player));
			world.player.addToInventory(new PowerRod(world.player));
			}
		
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
				
				case 0xff0000:{
					world.player.position=new Vector2D(x+20,y+20);
					world.addEntity(world.player);
					break;
				}
				case 0x00ff00:{
					spawnMonster(x+20,y+20,MapReader.BEHOLDER,world);
					break;
				}
				case 0x00ee00:{
					spawnMonster(x+20,y+20,MapReader.ANT,world);
					break;
				}
				case 0x00dd00:{
					spawnMonster(x+20,y+20,MapReader.GOBLIN_FIGHTER,world);
					break;
				}
				case 0x00cc00:{
					spawnMonster(x+20,y+20,MapReader.GOBLIN_ARCHER,world);
					break;
				}
				case 0x00ffff:{
					world.stairs=new Stairs(x,y);
					break;
				}
			}
			if(layout[i]>0&&layout[i]<0xffffff)
				world.floors.add(new FloorTile(x,y));
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
		
		for(BlockWall e:walls){
			if(e.width!=0){
				world.addWall(e);
				e.setRect(e.x+dx, e.y+dy, e.width, e.height);
			}
		}
	}
	
	public static void spawnMonster(int x,int y,int id,World world){
		GameAgent monster = null;
		switch(id){
		case ANT:{
			monster=new Ant(x, y, world);
			break;
		}
		case GOBLIN_ARCHER:{
			monster=new GoblinArcher(x,y,world);
			break;
		}
		case GOBLIN_FIGHTER:{
			monster=new GoblinFighter(x,y,world);
			break;
		}
		case BEHOLDER:{
			monster=new Beholder(x,y,world);
			break;
		}
		}
		world.addEntity(monster);
	}
	
	public static void main(String args[]){
		
		World w=nextMap();
		vs=new ViewScreen();
		JFrame frame=new JFrame("Test");
		frame.add(vs);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		vs.world=w;
		System.out.println(vs.world.getWalls().size());
	}
	
	public static World nextMap(){
		level++;
		if(level==4){
			JOptionPane.showMessageDialog(null, "Good job, You win!!!", "Congrats!", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		World w=new World();
		readMap("rooms/level"+level+".png",w);
		//readMap("rooms/test1.png");
		return w;
	}
	
	public static void goToNextMap(){
		vs.world=nextMap();
	}
}
