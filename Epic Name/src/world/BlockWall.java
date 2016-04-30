package world;

import java.util.ArrayList;

import geometry.Polygon;
import geometry.Rectangle;

public class BlockWall extends Wall{

	Rectangle body;
	public int x,y,width,height;
	
	public BlockWall(int x,int y,int width,int height) {
		super(new Rectangle(x+width/2.0,y+height/2.0,width,height));
		body=new Rectangle(x+width/2.0,y+height/2.0,width,height);
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}

	public Polygon getRect() {
		return body;
	}
	
	public void setRect(int x,int y,int width,int height){
		body=new Rectangle(x+width/2.0,y+height/2.0,width,height);
		this.setShape(body);
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	
	public boolean merge(BlockWall bw){
		if(bw.width==width&&bw.x==x&&bw.y==y+height&&width>0){
			setRect(x,y,width,height+bw.height);
			bw.setRect(0, 0, 0, 0);
			return true;
		}
		return false;
	}
}
