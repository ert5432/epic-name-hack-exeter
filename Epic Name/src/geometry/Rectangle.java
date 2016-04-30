package geometry;

import java.awt.Graphics;
import java.awt.geom.Area;

public class Rectangle extends Polygon{

	//private double width;
	//private double height;
	
	public Rectangle(double x,double y,double width,double height){
		super(new double[]{-width/2,width/2,width/2,-width/2},
				new double[]{-height/2,-height/2,height/2,height/2}, x, y);
	}

	public boolean isAdjacent(Rectangle r){
		return false;
	}
	/*public void render(Graphics g) {
		g.fillRect((int)(position.getX()-width/2),(int)(position.getY()-height/2),(int)width,(int)height);;
	}

	public Area toArea() {
		return new Area(new java.awt.geom.Rectangle2D.Double((int)(position.getX()-width/2),(int)(position.getY()-height/2),(int)width,(int)height));
	}

	//does nothing
	public void rotate(double theta) {}

	public Shape translate(Vector2D translation) {
		return new Rectangle(position.getX()+translation.getX(),position.getY()+translation.getY(),width,height);
	}
	
	public Polygon toPolygon(){
		return new Polygon(new double[]{-width/2,width/2,width/2,-width/2},
				new double[]{-height/2,-height/2,height/2,height/2}, position.getX(), position.getY());
	}*/
	
}
