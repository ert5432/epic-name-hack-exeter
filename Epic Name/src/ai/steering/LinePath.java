package ai.steering;

import java.awt.Graphics;

import geometry.Circle;
import geometry.Vector2D;
import geometry.Line;

public class LinePath implements Path{

	private Vector2D[] points;
	private double[] pointStarts;
	private double totalDistance;
	private Line[] lines;
	
	public LinePath(Vector2D[] points){
		this.points=points;
		pointStarts=new double[points.length];
		double totalDist=0;
		for(int i=0;i<points.length;i++){
			pointStarts[i]=totalDist;
			if(i<points.length-1)
				totalDist+=points[i].distanceTo(points[i+1]);
		}
		totalDistance=totalDist;
		if(points.length>0){
			lines=new Line[points.length-1];
			for(int i=0;i<points.length-1;i++){
				lines[i]=new Line(points[i],points[i+1]);
			}
		}
	}

	@Override
	public double getParam(Vector2D position, double lastParam) {
		int begin,end;
		if(lastParam<0||lastParam>totalDistance){
			begin=0;
			end=points.length-1;
		}
		else{
			int i;
			for(i=1;i<pointStarts.length;i++){
				if(lastParam<pointStarts[i]){
					break;
				}
			}
			begin=(int)Math.max(0, i-2);
			end=(int)Math.min(i+1, lines.length);
		}
		Vector2D bestPosition=null;
		double bestDistance=Double.POSITIVE_INFINITY;
		int bestIndex=begin;
		for(int i=begin;i<end;i++){
			System.out.println(lines[i]);
			Vector2D candidate=lines[i].nearestPoint(position);
			double candidateDist=position.distanceTo(candidate);
			System.out.println(candidateDist+"  "+candidate);
			if(candidateDist<bestDistance){
				bestPosition=candidate;
				bestDistance=candidateDist;
				bestIndex=i;
			}
		}
		return pointStarts[bestIndex]+lines[bestIndex].getPoint1().distanceTo(bestPosition);
	}

	@Override
	public Vector2D getPosition(double param) {
		if(param<=0)
			return points[0];
		else if(param>=totalDistance)
			return points[points.length-1];
		int i;
		for(i=1;i<pointStarts.length;i++){
			if(param<pointStarts[i]){
				break;
			}
		}
		Vector2D direction=points[i].sub(points[i-1]).normalize();
		double distAlong=param-pointStarts[i-1];
		return points[i].add(direction.mult(distAlong));
	}
	
	public double getTotalDistance(){
		return totalDistance;
	}
	
	public void render(Graphics g){
		for(Vector2D point:points){
			new Circle(point.x,point.y,20).render(g);
		}
		for(Line l:lines){
			l.render(g);
		}
	}
	
	public static void render(Vector2D[] points,Graphics g){
		if(points.length<=0)
			return;
		Line[] lines=new Line[points.length-1];
		for(int i=0;i<points.length-1;i++){
			lines[i]=new Line(points[i],points[i+1]);
		}
		for(Vector2D point:points){
			g.drawOval((int)(point.getX()-20),(int)(point.getY()-20),(int)20*2,(int)20*2);
		}
		for(Line l:lines){
			l.render(g);
		}
	}
}
