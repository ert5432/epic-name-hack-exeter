package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.GameAgent;
import geometry.Line;
import geometry.Polygon;
import geometry.Shape;
import geometry.Vector2D;
import graphics.Renderable;

public class Wall implements Renderable{

	public static BufferedImage image=loadImage();
	private Line[] sides;
	private Polygon shape;
	private Vector2D[] normals;
	private Color color;
	private int minX=Integer.MAX_VALUE,minY=Integer.MAX_VALUE,maxX=Integer.MIN_VALUE,maxY=Integer.MIN_VALUE;
	
	public Wall(Polygon shape,Color c) {
		sides=shape.toLines();
		this.shape=shape;
		normals=calculateNormals(sides);
		color=c;
		for(Vector2D v:shape.points){
			minX=(int) Math.min(minX, v.x);
			minY=(int) Math.min(minY, v.y);
			maxX=(int) Math.max(maxX, v.x);
			maxY=(int) Math.max(maxY, v.y);
		}
		Vector2D v=shape.getPosition();
	}

	public Wall(Polygon shape){
		sides=shape.toLines();
		this.shape=shape;
		normals=calculateNormals(sides);
		color=Color.black;
		for(Vector2D v:shape.points){
			minX=(int) Math.min(minX, v.x);
			minY=(int) Math.min(minY, v.y);
			maxX=(int) Math.max(maxX, v.x);
			maxY=(int) Math.max(maxY, v.y);
		}
	}
	
	public void render(Graphics g){
		/*//System.out.println("hey");
		Graphics2D g2d=(Graphics2D)g;
		
		int x=((int) (shape.getPosition().x+minX))-40,y=(int) (shape.getPosition().y+minY),width=maxX-minX,height=maxY-minY;
		for(int i=x;i<x+width;i+=40){
			for(int j=y;j<y+height;j+=40){
				g2d.scale(2.5, 2.5);
				g2d.drawImage(image,(int)(i/2.5),(int) (j/2.5), null);
				g2d.drawImage(image, (int)(i/2.5), (int)(j/2.5), null);
				g2d.scale(1/2.5, 1/2.5);
			}
		}*/
		g.setColor(Color.black);
		shape.render(g);
		g.setColor(color.white);
		((Graphics2D)g).draw(shape.toArea());
	}
	
	public Shape getShape(){
		return shape;
	}
	
	public boolean contains(Vector2D point){
		return shape.toArea().contains(point.toPoint());
	}
	
	public boolean contains(Shape other){
		return shape.intersects(other);
	}
	
	public Vector2D getNormal(Shape other){
		if(!shape.intersects(other))
			return new Vector2D(0,0);
		else{
			Vector2D result=new Vector2D(0,0);
			for(int i=0;i<sides.length;i++){
				if(other.intersects(sides[i]))
					result.iadd(normals[i]);
			}
			//if shape intersects a side, return resulting normal
			if(!result.isZeroed()){
				return result.normalize();
			}
			else{
			//normal along vector from center to shape if shape is inside wall
				shape.intersects(other);
				result=other.getPosition().sub(shape.getPosition());
				if(!result.isZeroed()){
					return result.normalize();
				}
				else
					return new Vector2D(1,0);//default
			}
		}
		
	}
	
	public static Vector2D[] calculateNormals(Line[] sides){
		Vector2D[] normals=new Vector2D[sides.length];
		
		//calculate normals of sides
		for(int i=0;i<sides.length;i++){
			Vector2D ab=sides[i].getPoint2().sub(sides[i].getPoint1());//find vector from a to b
			Vector2D ac;//find vector from a to c
			if(i<sides.length-1)
				ac=sides[i+1].getPoint2().sub(sides[i].getPoint1());
			else
				ac=sides[0].getPoint1().sub(sides[i].getPoint1());
			Vector2D n=new Vector2D(ab.y,-ab.x);//take perpendicular to ab
			if(n.dot(ac)>0){
				normals[i]=n.negative().normalize();
			}
			else
				normals[i]=n.normalize();
			
		}
		return normals;
	}
	
	public void setShape(Polygon s){
		shape=s;
	}
	
	public static BufferedImage loadImage(){
		BufferedImage image=null;
		try {
			image = ImageIO.read(new File("res/wall.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
}
