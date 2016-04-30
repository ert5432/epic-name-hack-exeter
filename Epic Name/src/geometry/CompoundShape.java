package geometry;

import java.awt.Graphics;
import java.awt.geom.Area;

public class CompoundShape extends Shape {
	
	private Shape[] shapes;
	
	public CompoundShape(Shape[] shapes,double x,double y) {
		this.shapes=shapes;
		position=new Vector2D(x,y);
		boundingRadius=0;
		for(Shape s:shapes){
			boundingRadius=Math.max(boundingRadius, s.boundingRadius+s.position.magnitude());
		}
	}
	
	public CompoundShape(Shape[] shapes,Vector2D position) {
		this.shapes=shapes;
		this.position=position;
		boundingRadius=0;
		for(Shape s:shapes){
			boundingRadius=Math.max(boundingRadius, s.boundingRadius+s.position.magnitude());
		}
	}
	
	public CompoundShape(Shape[] shapes) {
		this.shapes=shapes;
		position=new Vector2D(0,0);
		boundingRadius=0;
		for(Shape s:shapes){
			boundingRadius=Math.max(boundingRadius, s.boundingRadius+s.position.magnitude());
		}
	}

	public void render(Graphics g) {
		for(Shape s:shapes){
			s.translate(position).render(g);
		}
	}

	public Area toArea() {
		Area a=new Area();
		for(Shape s:shapes){
			a.add(s.translate(position).toArea());
		}
		return a;
	}

	@Override
	public void rotate(double theta) {
		for(Shape s:shapes){
			s.rotate(theta);
			s.setPosition(s.getPosition().rotate(theta));
		}
	}

	public Shape translate(Vector2D translation) {
		Shape[] newShapes=new Shape[shapes.length];
		for(int i=0;i<shapes.length;i++){
			newShapes[i]=shapes[i].clone();
		}
		return new CompoundShape(newShapes,position.add(translation));
	}

	public Shape clone() {
		Shape[] newShapes=new Shape[shapes.length];
		for(int i=0;i<shapes.length;i++){
			newShapes[i]=shapes[i].clone();
		}
		return new CompoundShape(newShapes,position.clone());
	}
	
	public Shape[] getShapes(){
		return shapes;
	}

	@Override
	public boolean intersects(Line line) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean includes(Vector2D point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Shape stretch(double mult) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void applyStretch(double mult) {
		// TODO Auto-generated method stub
		boundingRadius*=mult;
	}

}
