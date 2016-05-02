package weapons.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.GameAgent;
import geometry.Shape;
import geometry.Vector2D;
import world.World;

public class Stab extends MeleeEntity{

	private double stabSpeed;
	private long stabEnd;
	private long stabTimer;
	private double stabLength;
	private int stabDuration;
	private Vector2D direction;
	
	public Stab(Shape shape, World world,double stabLength,int stabDuration,GameAgent owner,int damage) {
		super(owner.position, shape, world,owner,stabDuration+10,stabDuration,damage,Color.green);
		shape.rotate(new Vector2D(1,0).angleTo(owner.heading));
		setHeading(heading.rotate(new Vector2D(1,0).angleTo(owner.heading)));
		this.stabLength=stabLength;
		this.stabDuration=stabDuration;
		stabTimer=0;
		stabEnd=world.time+stabDuration;
		stabSpeed=stabLength/stabDuration;
		direction=owner.heading.normalize();
	}
	
	public Stab(Shape shape,double stabLength,int stabDuration) {
		super(shape,stabDuration+10,stabDuration,Color.green);
		this.stabLength=stabLength;
		this.stabDuration=stabDuration;
		stabSpeed=stabLength/stabDuration;
		System.out.println(stabSpeed);
	}
	
	public void update(double time){
		super.update(time);
		stabTimer+=time;
		position=owner.position.add(direction.mult(stabTimer*stabSpeed));
		shape.setPosition(position);
		shape.rotate(direction.angleTo(owner.heading));
		direction=owner.heading.normalize();
		setHeading(direction.clone());
	}

	public void addCopytoWorld(World world,Vector2D position,GameAgent owner,int damage){
		world.addEntity(new Stab(shape.clone(),world,stabLength,stabDuration,owner,damage));
	}
	public void render(Graphics g){
		System.out.println(rotation);
		Graphics2D g2d = (Graphics2D) (g);
		
		g2d.rotate(rotation-Math.PI/2,position.x,position.y);
		
		g2d.scale(3, 3);

		System.out.println("sword");
		
		
		try {
			BufferedImage image = ImageIO.read(new File("res/sword.png"));
			
			
			
			g2d.drawImage(image,(int)((position.x-6)/3.0),(int)((position.y+10)/3.0),null);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		g2d.scale(1.0/3.0,1.0/3.0);
		g2d.rotate(-rotation+Math.PI/2,position.x,position.y);
	}
}
