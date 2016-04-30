package maps;

import geometry.Line;
import geometry.Rectangle;
import geometry.Vector2D;

import java.util.ArrayList;

public class Level {
	ArrayList<Room> rooms=new ArrayList<Room>();
	ArrayList<DoorWay> doorPoints=new ArrayList<DoorWay>();
	
	public Level(){
		rooms.add(new Room(-200,-200,400,400,"",0));
	}
	
	public boolean testRoomFit(String r,int loc){
		countOpenings();
		int[] im=MapReader.readImage(r);
		int width=im[im.length-2],height=im[im.length-1];
		DoorWay dw=doorPoints.get(loc);
		Rectangle rec=new Rectangle(0,0,0,0);
		if(dw.dir==0)
			rec=new Rectangle(dw.x-width/2.0,dw.y-height,width,height);
		else if(dw.dir==1)
			rec=new Rectangle(dw.x,dw.y-height/2.0,width,height);
		else if(dw.dir==2)
			rec=new Rectangle(dw.x-width/2.0,dw.y,width,height);
		else if(dw.dir==3)
			rec=new Rectangle(dw.x-width,dw.y-height/2.0,width,height);
		
		for(Room room:rooms){
			if(room.room.isAdjacent(rec))
				return false;
		}
		return true;
	}
	
	public void addRoom(String r,int loc){
		int[] im=MapReader.readImage(r);
		int width=im[im.length-2],height=im[im.length-1];
		DoorWay dw=doorPoints.get(loc);
		Rectangle rec=new Rectangle(0,0,0,0);
		if(dw.dir==0)
			rec=new Rectangle(dw.x-width/2.0,dw.y-height,width,height);
		else if(dw.dir==1)
			rec=new Rectangle(dw.x,dw.y-height/2.0,width,height);
		else if(dw.dir==2)
			rec=new Rectangle(dw.x-width/2.0,dw.y,width,height);
		else if(dw.dir==3)
			rec=new Rectangle(dw.x-width,dw.y-height/2.0,width,height);
		
		Vector2D vec = rec.points[0];
		
		rooms.add(new Room((int)vec.x,(int)vec.y,width,height,r,loc));
	}
	
	public int countOpenings(){
		doorPoints.clear();
		for(Room r:rooms){
			for(Line l:r.room.toLines()){
				int x=(int) ((l.getPoint1().x+l.getPoint2().x)/2.0);
				int y=(int) ((l.getPoint1().y+l.getPoint2().y)/2.0);
				if(testDoorway(x,y))
					doorPoints.add(new DoorWay(x,y,r));
			}
		}
		return doorPoints.size();
	}
	
	public boolean testDoorway(int x,int y){
		int i=0;
		for(Room r:rooms){
			for(Line l:r.room.toLines()){
				if(l.distanceTo(new Vector2D(x,y))==0){
					i++;
					break;
				}
					
			}
			
		}
		if(i==1)
				return true;
		return false;
	}
}
