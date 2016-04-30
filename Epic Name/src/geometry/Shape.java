package geometry;

import java.awt.Color;
import java.awt.geom.Area;

import graphics.Renderable;

public abstract class Shape implements Renderable,Cloneable{
	
	protected Vector2D position;
	protected double boundingRadius;
	protected Color color;
	
	public abstract Area toArea();
	
	public abstract void rotate(double theta);
	
	public void setPosition(Vector2D position){
		this.position=position;
	}
	
	public Vector2D getPosition(){
		return position;
	}
	
	public boolean intersects(Shape other){
		return detectIntersection(this,other);
	}
	
	public abstract boolean intersects(Line line);
	
	public abstract boolean includes(Vector2D point);
	
	public static boolean detectIntersection(Shape s1,Shape s2){
//		System.out.println(s2);
		double dist=s1.getPosition().vectorTo(s2.getPosition()).magnitude();
		if(dist>s1.boundingRadius+s2.boundingRadius)
			return false;
		if(s1 instanceof Polygon){
			if(s2 instanceof Polygon)
				return detectPolygonPolygonIntersection((Polygon)s1,(Polygon)s2);
			else if(s2 instanceof Circle)
				return detectCirclePolygonIntersection((Circle)s2,(Polygon)s1);
			else if(s2 instanceof PartialArc)
				return detectPolygonPartialArcIntersection((Polygon)s1,(PartialArc)s2);
		}
		else if(s1 instanceof Circle){
			if(s2 instanceof Polygon)
				return detectCirclePolygonIntersection((Circle)s1,(Polygon)s2);
			else if(s2 instanceof Circle)
				return detectCircleCircleIntersection((Circle)s1,(Circle)s2);
			else if(s2 instanceof PartialArc){
				return detectCirclePartialArcIntersection((Circle)s1,(PartialArc)s2);
			}
		}
		else if(s1 instanceof PartialArc){
			if(s2 instanceof Polygon){
				return detectPolygonPartialArcIntersection((Polygon)s2,(PartialArc)s1);
			}
			else if(s2 instanceof Circle){
				return detectCirclePartialArcIntersection((Circle)s2,(PartialArc)s1);
			}
			else if(s2 instanceof PartialArc){
				return detectPartialArcPartialArcIntersection((PartialArc)s1,(PartialArc)s2);
			}
		}
		else if(s1 instanceof CompoundShape)
			return detectCompoundShapeIntersection((CompoundShape)s1,s2);
		else if(s2 instanceof CompoundShape)
			return detectCompoundShapeIntersection((CompoundShape)s2,s1);
		return false;
	}
	
	public static boolean detectCircleCircleIntersection(Circle c1, Circle c2) {
		double dist=c1.getPosition().vectorTo(c2.getPosition()).magnitude();
		if(dist<=c1.getRadius()+c2.getRadius())
			return true;
		return false;
	}

	public static boolean detectCirclePolygonIntersection(Circle c, Polygon p) {
		if(p.includes(c.getPosition()))
			return true;
		for(Line l:p.toLines()){
			if(c.intersects(l)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean detectCompoundShapeIntersection(CompoundShape shape,Shape s){
		for(Shape part:shape.getShapes()){
			if(part.intersects(s))
				return true;
		}
		return false;
	}

	public static boolean detectPolygonPolygonIntersection(Polygon p1, Polygon p2) {
		for(Vector2D p:p1.toPoints()){
			if(p2.includes(p))
				return true;
		}
		for(Line l1:p1.toLines())
			for(Line l2:p2.toLines())
				if(l1.intersects(l2))
					return true;
		return false;
	}
	
	public static boolean detectPolygonPartialArcIntersection(Polygon p,PartialArc a){
		for(Line line:p.toLines()){
			if(a.intersects(line))
				return true;
		}
		if(p.includes(a.getPoints()[0]))//only need to test 1 point, because edges do not intersect
			return true;
		else
			return false;
	}
	
	public static boolean detectCirclePartialArcIntersection(Circle c,PartialArc a){
		if(a.includes(c.position))
			return true;
		else if(new Arc(a.position,a.getRadius(),a.getOrientation(), a.getArcAngle()).intersects(c))
			return true;
		else if(new Arc(a.position, a.getHoleRadius(), a.getOrientation(), a.getArcAngle()).intersects(c))
			return true;
		else{
			Vector2D[] aPoints=a.getPoints();
			if(c.intersects(new Line(aPoints[0],aPoints[1]))||c.intersects(new Line(aPoints[2],aPoints[3]))){
				return true;
			}
			else{
				for(Vector2D point:aPoints){
					if(c.includes(point))
						return true;
				}
			}
		}
		return false;
	}
	
	public static boolean detectPartialArcPartialArcIntersection(PartialArc a1,PartialArc a2){
		
		return false;
	}

	public abstract Shape translate(Vector2D translation);
	
	public abstract void applyStretch(double mult);
	
	public abstract Shape stretch(double mult);
	
	public static void main(String[] args){
		long total=0;
		for(int i=0;i<1000000;i++){
			Polygon poly=new Polygon(new double[]{-20,0,20},new double[]{-20,20,-20},10,10);
			Circle circ=new Circle(40,40,20);
			long start=System.nanoTime();
			System.out.println(poly.intersects(circ));
			total+=(System.nanoTime()-start);
			System.out.println(i);
		}
		System.out.println(total);
	}
	
	public abstract Shape clone();
	
	public double getBoundingRadius(){
		return boundingRadius;
	}
	
	/**
	 * 
	 * @param point: point where the ray starts
	 * @param l: line segment
	 * @return whether or not a horizontal ray going to the right intersects the segment
	 */
	public static boolean rayIntersectsSegment(Vector2D point,Line l){
		Vector2D a=l.getPoint1(),b=l.getPoint2();
		Vector2D p=point.clone();
		if(a.y>b.y){
			Vector2D temp=b;
			b=a;
			a=temp;
		}
		if(p.y==a.y||p.y==b.y)
			p.y+=0.0001;
		if(p.y<a.y||p.y>b.y)
			return false;
		else if(p.x>Math.max(a.x, b.x))
			return false;
		else if(p.x<Math.min(a.x, b.x))
				return true;
		else{
			double th1,th2;
			if(a.x!=b.x)
				th1=(b.y-a.y)/(b.x-a.x);
			else
				th1=Double.POSITIVE_INFINITY;
			if(a.x!=p.x)
				th2=(p.y-a.y)/(p.x-a.x);
			else
				th2=Double.POSITIVE_INFINITY;
			if(th2>=th1)
				return true;
			else 
				return false;
		}
	}
	
	/**
	 * 
	 * @return a mod b
	 */
	public static double modulus(double a,double b){
		return (((a % b) + b) % b);		
	}
	
}
