package geometry;

import java.awt.Graphics;
import java.awt.geom.Area;

public class Polygon extends Shape {

	public Vector2D[] points;
	
	public Polygon(double[] relativexpos,double[] relativeypos){
		points=new Vector2D[relativexpos.length];
		for(int i=0;i<points.length;i++){
			points[i]=new Vector2D(relativexpos[i],relativeypos[i]);
		}
		position=new Vector2D(0,0);
		boundingRadius=0;
		for(Vector2D p:points){
			boundingRadius=Math.max(boundingRadius, p.magnitude());
		}
	}
	
	public Polygon(double[] relativexpos,double[] relativeypos,double x,double y){
		points=new Vector2D[relativexpos.length];
		for(int i=0;i<points.length;i++){
			points[i]=new Vector2D(relativexpos[i],relativeypos[i]);
		}
		position=new Vector2D(x,y);
		boundingRadius=0;
		for(Vector2D p:points){
			boundingRadius=Math.max(boundingRadius, p.magnitude());
		}
	}
	
	public Polygon(Vector2D[] relativePoints,Vector2D position){
		points=relativePoints;
		this.position=position;
		boundingRadius=0;
		for(Vector2D p:points){
			boundingRadius=Math.max(boundingRadius, p.magnitude());
		}
	}

	public Area toArea(){
		int[] xpos=new int[points.length],ypos=new int[points.length];
		for(int i=0;i<points.length;i++){
			xpos[i]=(int)(points[i].getX()+position.getX());
			ypos[i]=(int)(points[i].getY()+position.getY());
		}
		return new Area(new java.awt.Polygon(xpos, ypos, points.length));
	}

	public void rotate(double theta) {
		for(int i=0;i<points.length;i++){
			points[i]=points[i].rotate(theta);
		}
	}
	
	public Shape translate(Vector2D translation){
		return new Polygon(points,position.add(translation));
	}
	
	public Line[] toLines(){
		Line[] lines=new Line[points.length];
		for(int i=0;i<points.length-1;i++){
			lines[i]=new Line(points[i].add(position),points[i+1].add(position));
		}
		lines[points.length-1]=new Line(points[points.length-1].add(position),points[0].add(position));
		return lines;
	}
	
	public Vector2D[] toPoints(){
		Vector2D[] actualPoints=new Vector2D[points.length];
		for(int i=0;i<points.length;i++){
			actualPoints[i]=points[i].add(position);
		}
		return actualPoints;
	}
	
	public boolean includes(Vector2D point){
		int intersections=0;
		for(Line l:toLines()){
			if(rayIntersectsSegment(point, l))
				intersections++;
		}
		return intersections%2==1;
	}

	public Shape clone() {
		Vector2D[] newPoints=new Vector2D[points.length];
		for(int i=0;i<points.length;i++){
			Vector2D p=points[i];
			newPoints[i]=new Vector2D(p.x,p.y);
		}
		return new Polygon(newPoints,new Vector2D(position.x,position.y));
	}

	public boolean intersects(Line line) {
		if(includes(line.getPoint1())||includes(line.getPoint2()))
			return true;
		else{
			Line[] lines=toLines();
			for(Line l:lines){
				if(l.intersects(line))
					return true;
			}
			return false;
		}
	}

	@Override
	public void render(Graphics g) {
		int[] xpos=new int[points.length],ypos=new int[points.length];
		for(int i=0;i<points.length;i++){
			xpos[i]=(int)(points[i].getX()+position.getX());
			ypos[i]=(int)(points[i].getY()+position.getY());
		}
		g.fillPolygon(xpos, ypos, points.length);
	}

	@Override
	public void applyStretch(double mult) {
		for(Vector2D point:points){
			point.imult(mult);
		}
		boundingRadius*=mult;
	}

	@Override
	public Shape stretch(double mult) {
		Vector2D[] newPoints=new Vector2D[points.length];
		for(int i=0;i<points.length;i++){
			newPoints[i]=points[i].mult(mult);
		}
		return new Polygon(newPoints,position);
	}
}
