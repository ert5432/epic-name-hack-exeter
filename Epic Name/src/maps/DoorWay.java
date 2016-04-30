package maps;

import geometry.Line;
import geometry.Vector2D;

public class DoorWay {
	public int x,y,type,dir;
	public Room Connect;
	public DoorWay(int x,int y,Room con){
		this.x=x;
		this.y=y;
		this.type=con.type;
		Connect=con;
		Line[] lines=con.room.toLines();
		for(int i=0;i<4;i++){
			if(lines[i].distanceTo(new Vector2D(x,y))==0){
				dir=i;
				return;
			}
		}
	}
}
