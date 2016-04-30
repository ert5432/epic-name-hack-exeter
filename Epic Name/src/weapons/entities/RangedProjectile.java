package weapons.entities;

import world.World;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.GameAgent;
import geometry.Shape;
import geometry.Vector2D;

public class RangedProjectile extends Projectile{

	public double accuracyRange;
	private Vector2D relativePosition;
	
	public RangedProjectile(Vector2D relativePosition,Vector2D direction,Shape shape, World world,GameAgent owner,
			double maxSpeed,double accuracyRange,int coolDown,int flyTime,int damage) {
		super(owner.position.add(relativePosition),direction,maxSpeed,shape, world,owner,coolDown,flyTime,damage);
		this.accuracyRange=accuracyRange;
		this.maxVelocity=maxSpeed;
		heading=direction.clone();
		shape.rotate(-heading.angleTo(new Vector2D(1,0)));
		velocity=heading.mult(maxSpeed);
	}

	public RangedProjectile(Vector2D relativePosition,Shape shape,double maxSpeed,
			double accuracyRange,int coolDown,int flyTime) {
		super(shape,coolDown,flyTime);
		this.relativePosition=relativePosition;
		this.accuracyRange=accuracyRange;
		this.maxVelocity=maxSpeed;
	}
	
	public void addCopytoWorld(World world,Vector2D position,GameAgent owner,int damage){
		world.addEntity(new RangedProjectile(relativePosition.clone(),owner.heading.clone(),shape.clone(),world,owner,this.maxVelocity,
				accuracyRange,coolDown,duration,damage));
	}
	
	public void addCopytoWorld(World world,Vector2D position,Vector2D direction,double velocity,GameAgent owner,int damage){
		world.addEntity(new RangedProjectile(relativePosition.clone(),direction,shape.clone(),world,owner,velocity,
				accuracyRange,coolDown,duration,damage));
	}
	
	public double getAccuracyRange(){
		return accuracyRange;
	}
	
	public void render(Graphics g) {
		//super.render(g);
		System.out.println(rotation);
		Graphics2D g2d = (Graphics2D) (g);
		
		g2d.rotate(rotation+owner.rotation+Math.PI,position.x,position.y);
		
		g2d.scale(3, 3);

		System.out.println("sword");
		
		
		try {
			BufferedImage image = ImageIO.read(new File("res/arrow.png"));
			
			
			
			g2d.drawImage(image,(int)((position.x)/3.0),(int)((position.y+10)/3.0),null);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		g2d.scale(1.0/3.0,1.0/3.0);
		g2d.rotate(-owner.rotation-rotation-Math.PI,position.x,position.y);
	}
}
