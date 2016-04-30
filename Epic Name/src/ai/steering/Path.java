package ai.steering;

import java.awt.Graphics;

import geometry.Vector2D;

public interface Path {

	public double getParam(Vector2D position,double lastParam);
	
	public Vector2D getPosition(double param);
	
	public double getTotalDistance();
	
	public void render(Graphics g);
	
}
