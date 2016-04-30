package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import geometry.Shape;
import graphics.Animation;
import graphics.Sprite;
import stats.Stats;
import world.World;

public class Goblin extends StateAgent{

	private Sprite sprite = new Sprite("goblin", 12);
	private BufferedImage[] standing = {sprite.getSprite(2, 0) };
	private Animation standingAnim = new Animation(standing, 20);
	
	private BufferedImage[] walking= {sprite.getSprite(0, 0),sprite.getSprite(1, 0) };
	private Animation walkingAnim = new Animation(walking, 15);
	
	public Goblin(double x, double y, Shape shape, World world, Stats stats) {
		super(x, y, shape, world, stats);
		// TODO Auto-generated constructor stub
		
		walkingAnim.start();
		standingAnim.start();
	}
	
	public void update(double time){
		super.update(time);
		
		walkingAnim.update();
		
	}
	
	public void render(Graphics g){
		//super.render(g);
		
	
		Graphics2D g2d = (Graphics2D)(g);
		g2d.rotate(rotation,position.x,position.y);
		g2d.scale(4, 4);
		
		if(velocity.isZeroed()){
			g2d.drawImage(standingAnim.getSprite(), (int)((position.x-20)/4.0), (int)((position.y-24)/4.0), null);
		}else{
			g2d.drawImage(walkingAnim.getSprite(), (int)((position.x-20)/4.0), (int)((position.y-24)/4.0), null);
		}
		
		
		
		g2d.scale(.25, .25);
		g2d.rotate(-rotation,position.x,position.y);
	}

}
