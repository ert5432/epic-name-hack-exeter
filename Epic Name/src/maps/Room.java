package maps;

import geometry.Rectangle;

public class Room {
	public int x,y,width,height,type=0;
	public Rectangle room;
	String map;
	

	public Room(int x,int y,int width,int height,String map,int type){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.map=map;
		
		room=new Rectangle(x+width/2,y+height/2,width,height);
	}
}
