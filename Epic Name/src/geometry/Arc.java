package geometry;

import java.util.Scanner;

public class Arc {

	private double radius;
	private double orientation;
	private double arcAngle;
	private Vector2D center;
	
	public Arc(Vector2D center,double radius,double orientation,double arcAngle) {
		this.radius=radius;
		this.orientation=orientation;
		this.arcAngle=arcAngle;
		this.center=center;
	}
	
	public boolean intersects(Line line){
		Vector2D d=line.getPoint2().sub(line.getPoint1());//vector along line
		Vector2D f=line.getPoint1().sub(center);//vector from center of circle to circle
		//t^2 * (d DOT d) + 2t*( f DOT d ) + ( f DOT f - r^2 ) = 0,
		//where t is the projection of the intersection along the line
		//solving for t using quadratic equation
		double a=d.dot(d);
		double b=2*f.dot(d);
		double c=f.dot(f)-radius*radius;
		double discriminantSq=b*b-4*a*c;
		if(discriminantSq<0){
			//no intersection because the line is not facing the circle
			return false;
		}
		else{
			//line is facing the circle, solution for t
			double discriminant=Math.sqrt(discriminantSq);
			double t1=(-b-discriminant)/(2*a);
			double t2=(-b+discriminant)/(2*a);	
			if(t1>=0 && t1<=1){
				//t1 intersects the circle, is it on the arc?
				Vector2D p1=line.getPoint1().add(d.mult(t1));
				Vector2D toP1=p1.sub(center);
				double angle=Math.atan2(toP1.y, toP1.x);
				if(angleInArc(angle,orientation,arcAngle)){
					return true;
				}
			}
			if(t2>=0 && t2<=1){
				//t2 intersects the circle, is it on the arc?
				Vector2D p2=line.getPoint1().add(d.mult(t2));
				Vector2D toP2=p2.sub(center);
				double angle=Math.atan2(toP2.y, toP2.x);
				if(angleInArc(angle,orientation,arcAngle))
					return true;
			}
		}
		return false;
	}
	
	public boolean intersects(Circle c){
		double r2=c.getRadius();
		double distSq=center.sub(c.position).magnitudeSq(); 
		if(distSq>(radius+r2)*(radius+r2))
			return false;//separate from the circle
		else if(distSq<(radius-r2)*(radius-r2))
			return false;//circle contains arc or vice versa
		else if(distSq==0&&radius==r2)
			return true;//one is on top of the other
		else{
			double d=Math.sqrt(distSq);
			double planeDist=(radius*radius-r2*r2+distSq)/(2*d);
			Vector2D planePos=center.add(c.getPosition().sub(center).mult(planeDist/d));
			double h=Math.sqrt(radius*radius-planeDist*planeDist);
			Vector2D p1=new Vector2D(planePos.x+h*(c.position.y-center.y)/d,planePos.y-h*(c.position.x-center.x)/d);
			Vector2D p2=new Vector2D(planePos.x-h*(c.position.y-center.y)/d,planePos.y+h*(c.position.x-center.x)/d);
			Vector2D toP1=p1.sub(center);
			double angle1=Math.atan2(toP1.y,toP1.x);
			if(angleInArc(angle1,orientation,arcAngle))
				return true;
			Vector2D toP2=p2.sub(center);
			double angle2=Math.atan2(toP2.y,toP2.x);
			if(angleInArc(angle2,orientation,arcAngle))
				return true;
		}
		return false;
	}
	
	public boolean intersects(Arc a){
		double r2=a.getRadius();
		double distSq=center.sub(a.getCenter()).magnitudeSq(); 
		if(distSq>(radius+r2)*(radius+r2))
			return false;//separate from the circle
		else if(distSq<(radius-r2)*(radius-r2))
			return false;//circle contains arc or vice versa
		else if(distSq==0&&radius==r2)
			return true;//one is on top of the other
		else{
			double d=Math.sqrt(distSq);
			double planeDist=(radius*radius-r2*r2+distSq)/(2*d);
			Vector2D planePos=center.add(a.getCenter().sub(center).mult(planeDist/d));
			double h=Math.sqrt(radius*radius-planeDist*planeDist);
			Vector2D p1=new Vector2D(planePos.x+h*(a.getCenter().y-center.y)/d,planePos.y-h*(a.getCenter().x-center.x)/d);
			Vector2D p2=new Vector2D(planePos.x-h*(a.getCenter().y-center.y)/d,planePos.y+h*(a.getCenter().x-center.x)/d);
			Vector2D fromA1toP1=p1.sub(center);
			double angle11=Math.atan2(fromA1toP1.y,fromA1toP1.x);
			if(angleInArc(angle11,orientation,arcAngle)){
				Vector2D fromA2toP1=p1.sub(a.getCenter());
				if(angleInArc(Math.atan2(fromA2toP1.y,fromA2toP1.x),orientation,arcAngle));
					return true;
			}
			Vector2D fromA1toP2=p2.sub(center);
			double angle2=Math.atan2(fromA1toP2.y,fromA1toP2.x);
			if(angleInArc(angle2,orientation,arcAngle)){
				Vector2D fromA2toP2=p2.sub(a.getCenter());
				if(angleInArc(Math.atan2(fromA2toP2.y,fromA2toP2.x),orientation,arcAngle))
					return true;
			}
		}
		return false;
	}
	
	public static boolean angleInArc(double angle,double orientation,double arcAngle){
		System.out.println(angle+"    "+orientation+"   "+arcAngle);
		System.out.println(Math.abs(Shape.modulus(angle-orientation+Math.PI,Math.PI*2)-Math.PI)<=arcAngle/2);
		return Math.abs(Shape.modulus(angle-orientation+Math.PI,Math.PI*2)-Math.PI)<=arcAngle/2;
	}
	
	public static void main(String[] args){
		Scanner scan=new Scanner(System.in);
		while(true){
		
		System.out.println(5);
		
		}
	}
	
	public double getRadius(){
		return radius;
	}
	
	public Vector2D getCenter(){
		return center;
	}
}
