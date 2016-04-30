package maps;

import geometry.Rectangle;
import world.World;

public class LevelGenerator {
	
	
	public static World generate(){
		return null;
	}
	
}
class Tile{
	public int x,y,width,height;
	public Rectangle room;
	String map;
	
	public Tile(int x,int y,int width,int height,String map){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.map=map;
	}
}