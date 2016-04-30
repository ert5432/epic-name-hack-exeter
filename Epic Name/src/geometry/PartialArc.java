package geometry;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.util.Arrays;

public class PartialArc extends Shape{

	private double radius;
	private double holeRadius;
	private double orientation;
	private double arcAngle;
	private Vector2D[] points;
	
	/**
	 * 
	 * arcs are put in the counterclockwise direction 
	 */
	public PartialArc(double x,double y,double radius,double holeRadius,double arcAngle,double orientation) {
		position=new Vector2D(x,y);
		this.boundingRadius=radius;
		this.radius=radius;
		this.holeRadius=holeRadius;
		this.arcAngle = arcAngle;
		this.orientation=orientation;
		points=new Vector2D[4];
		points[0]=new Vector2D(Math.cos(orientation-arcAngle/2)*holeRadius,Math.sin(orientation-arcAngle/2)*holeRadius);
		points[1]=new Vector2D(Math.cos(orientation-arcAngle/2)*radius,Math.sin(orientation-arcAngle/2)*radius);
		points[2]=new Vector2D(Math.cos(orientation+arcAngle/2)*holeRadius,Math.sin(orientation+arcAngle/2)*holeRadius);
		points[3]=new Vector2D(Math.cos(orientation+arcAngle/2)*radius,Math.sin(orientation+arcAngle/2)*radius);
	}
	
	public PartialArc(Vector2D position,double radius,double holeRadius,double arcAngle,double orientation){
		this.position=position;
		this.boundingRadius=radius;
		this.radius=radius;
		this.holeRadius=holeRadius;
		this.arcAngle=arcAngle;
		this.orientation=orientation;
		points=new Vector2D[4];
		points[0]=new Vector2D(Math.cos(orientation-arcAngle/2)*holeRadius,Math.sin(orientation-arcAngle/2)*holeRadius);
		points[1]=new Vector2D(Math.cos(orientation-arcAngle/2)*radius,Math.sin(orientation-arcAngle/2)*radius);
		points[2]=new Vector2D(Math.cos(orientation+arcAngle/2)*holeRadius,Math.sin(orientation+arcAngle/2)*holeRadius);
		points[3]=new Vector2D(Math.cos(orientation+arcAngle/2)*radius,Math.sin(orientation+arcAngle/2)*radius);
	}

	public Area toArea() {
		Area outer=new Area(new Arc2D.Double(position.x-radius,position.y-radius,radius*2,radius*2,-Math.toDegrees(orientation-arcAngle/2),-Math.toDegrees(arcAngle),Arc2D.PIE));
		Area hole=new Area(new Arc2D.Double(position.x-holeRadius,position.y-holeRadius,holeRadius*2,holeRadius*2,-Math.toDegrees(orientation-arcAngle/2),-Math.toDegrees(arcAngle),Arc2D.PIE));
		outer.subtract(hole);
		return outer;
	}
	
	@Override
	public void rotate(double theta) {
		orientation=modulus(orientation+theta,Math.PI*2);
		for(int i=0;i<points.length;i++){
			points[i]=points[i].rotate(theta);
		}
	}

	public boolean includes(Vector2D point){
		Vector2D toPoint=point.sub(position);
		double distSq=toPoint.magnitudeSq();
		if(distSq>radius*radius||distSq<holeRadius*holeRadius)
			return false;
		else{
			double angle=Math.atan2(toPoint.y, toPoint.x);
			if(Arc.angleInArc(angle, orientation, arcAngle))
				return true;
		}
		return false;
	}
	
	public boolean intersects(Line line) {
		if(includes(line.getPoint1()))
			return true;
		else if(includes(line.getPoint2()))
			return true;
		//test if line intersects edges of arc
		Vector2D[] actualPoints=getPoints();
		Line edge1=new Line(actualPoints[0],actualPoints[1]);
		if(edge1.intersects(line))
			return true;
		Line edge2=new Line(actualPoints[2],actualPoints[3]);
		if(edge2.intersects(line))
			return true;
		//does outer arc and line intersect
		if(new Arc(position,radius,orientation,arcAngle).intersects(line))
			return true;
		else
			return false;
	}

	@Override
	public Shape clone() {
		return new PartialArc(position.clone(),radius,holeRadius,arcAngle,orientation);
	}

	@Override
	public Shape translate(Vector2D translation) {
		return new PartialArc(position.add(translation),radius,holeRadius,arcAngle,orientation);
	}

	@Override
	public void render(Graphics g) {
		((Graphics2D)g).fill(toArea());
	}
	
	public Vector2D[] getPoints(){
		Vector2D[] actualPoints=new Vector2D[points.length];
		for(int i=0;i<points.length;i++){
			actualPoints[i]=points[i].add(position);
		}
		return actualPoints;
	}
	
	public static boolean detectLineCircleIntersection(Line line,Vector2D circleCenter,double radius){
		Vector2D d=line.getPoint2().sub(line.getPoint2());//vector along line
		Vector2D f=line.getPoint2().sub(circleCenter);//vector from center of circle to circle
		//t^2 * (d DOT d) + 2t*( f DOT d ) + ( f DOT f - r^2 ) = 0,
		//where t is the projection of the intersection along the line
		//solving for t using quadratic equation
		double a=d.dot(d);
		double b=2*f.dot(d);
		double c=f.dot(f)-radius*radius;
		double discriminant=b*b-4*a*c;
		if(discriminant<0){
			//no intersection because the line is not facing the circle
			return false;
		}
		else{
			//line is facing the circle, solution for t
			discriminant=Math.sqrt(discriminant);
			double t1=(-b-discriminant)/(2*a);
			double t2=(-b+discriminant)/(2*a);
			
			if(t1>=0 && t1<=1){
				//t1 intersects the circle
				return true;
			}
			else if(t2>=0 && t2<=1){
				//t2 intersects the circle
				return true;
			}
			else //neither intersect circle
				return false;
		}
	}

	public double getRadius() {
		return radius;
	}
	
	public double getHoleRadius(){
		return holeRadius;
	}

	public double getOrientation() {
		return orientation;
	}
	
	public double getArcAngle(){
		return arcAngle;
	}

	@Override
	public void applyStretch(double mult) {
		holeRadius*=mult;
		radius*=mult;
		for(Vector2D p:points){
			p.imult(mult);
		}
		boundingRadius=radius;
	}

	@Override
	public Shape stretch(double mult) {
		return new PartialArc(position,radius*mult,holeRadius*mult,arcAngle,orientation);
	}
	
	public void expand(double amount){
		radius+=amount;
		holeRadius+=amount;
		boundingRadius=radius;
		for(Vector2D p:points){
			p.iadd(p.normalize().mult(amount));
		}
	}
}
