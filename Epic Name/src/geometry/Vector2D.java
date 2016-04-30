package geometry;

import java.awt.Point;

public class Vector2D implements Cloneable{

	public double x;
	public double y;

	public Vector2D(double x,double y){
		this.x=x;
		this.y=y;
	}

	public void zero(){
		x=0;
		y=0;
	}

	public boolean isZeroed(){
		return x==0&&y==0;
	}

	public Vector2D add(Vector2D other){
		return new Vector2D(x+other.getX(),y+other.getY());
	}

	public Vector2D sub(Vector2D other){
		return new Vector2D(x-other.getX(),y-other.getY());
	}

	public Vector2D mult(double mult){
		return new Vector2D(x*mult,y*mult);
	}

	public Vector2D div(double div){
		return new Vector2D(x/div,y/div);
	}

	public void iadd(Vector2D other){
		x+=other.getX();
		y+=other.getY();
	}

	public void isub(Vector2D other){
		x-=other.getX();
		y-=other.getY();
	}

	public void imult(double mult){
		x*=mult;
		y*=mult;
	}

	public void idiv(double div){
		x/=div;
		y/=div;
	}

	public Vector2D squared(){
		return mult(magnitude());
	}

	public Vector2D perpendicular(){
		return new Vector2D(-y,x);
	}

	public double magnitude(){
		return Math.sqrt(x*x+y*y);
	}

	public double magnitudeSq(){
		return x*x+y*y;
	}
	
	public Vector2D normalize(){
		if(isZeroed())
			return new Vector2D(0,0);
		else
			return this.div(this.magnitude());
	}

	public Vector2D truncate(double max){
		if(this.magnitudeSq()>max*max)
			return normalize().mult(max);
		else
			return this;
	}

	public Vector2D negative(){
		return mult(-1);
	}

	public double dot(Vector2D other){
		return (x*other.getX()+y*other.getY());
	}

	public double angleTo(Vector2D other){
		if(this.isZeroed()||other.isZeroed())
			return 0;
		double a=directionTo(other),b=magnitude(),c=other.magnitude(),d=dot(other),e=Math.acos(dot(other)/(magnitude()*other.magnitude()));
  		return Math.acos(round(dot(other)/(magnitude()*other.magnitude()),7))*directionTo(other);
	}
	
	public double angleToOrigin(){
		return new Vector2D(1,0).angleTo(this);
	}
	/*
	 * returns -1 if clockwise, 1 if counterclockwise
	 */
	public int directionTo(Vector2D other){
		if(x*other.getY()>y*other.getX())
			return 1;
		else if(x*other.getY()<y*other.getX())
			return -1;
		else
			return 0;
	}

	public Vector2D vectorTo(Vector2D other){
		return other.sub(this);
	}
	
	public double distanceToSq(Vector2D other){
		return (other.x-x)*(other.x-x)+(other.y-y)*(other.y-y);		
	}
	
	public double distanceTo(Vector2D other){
		return Math.sqrt((other.x-x)*(other.x-x)+(other.y-y)*(other.y-y));		
	}

	public Vector2D rotateRelativeTo(Vector2D other){
		if(isZeroed())
			return new Vector2D(0,0);
		else
			return rotate(other.angleTo(new Vector2D(1,0)));
	}

	public Vector2D translateToRelativeSpace(Vector2D position,Vector2D heading){
		Vector2D vprime=this.sub(position);
		return vprime.rotateRelativeTo(heading);
	}

	public Vector2D rotate(double theta){
		double xprime=x*Math.cos(theta)-y*Math.sin(theta);
		double yprime=y*Math.cos(theta)+x*Math.sin(theta);
		return new Vector2D(xprime,yprime);
	}

	public Vector2D rotateAbout(Vector2D other,double theta){
		Vector2D relative=this.sub(other);
		return relative.rotate(theta).add(other);
	}

	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}

	public static Vector2D pointToVector(Point p){
		return new Vector2D(p.x,p.y);
	}

	public Point toPoint(){
		return new Point((int)x,(int)y);
	}

	public String toString(){
		return "Vector2D["+x+","+y+"]";
	}

	public static double round(double n,int digits){
		double multiplier=Math.pow(10, digits);
		return (double)Math.round(n*multiplier)/multiplier;
	}
	
	public Vector2D clone(){
		return new Vector2D(x,y);
	}
	
	public static void main(String[] args){
		Vector2D v1=new Vector2D(1,1),v2=new Vector2D(Math.sqrt(2),0);
		System.out.println(v1.rotate(v1.angleTo(v2)));
		System.out.println(100.222221-100.222221%0.001);
	}
}
