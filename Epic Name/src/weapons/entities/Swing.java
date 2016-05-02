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

public class Swing extends MeleeEntity {

	private double swingSpeed;
	private long swingEnd;
	private double swingAngle;
	private int swingDuration;

	public Swing(Shape shape, World world, double swingAngle, int swingDuration, GameAgent owner, int damage) {
		super(owner.position, shape, world, owner, swingDuration + 5, swingDuration, damage, Color.green);
		shape.rotate(new Vector2D(1, 0).angleTo(owner.heading) - swingAngle / 2);
		setHeading(heading.rotate(new Vector2D(1, 0).angleTo(owner.heading) - swingAngle / 2));
		swingEnd = world.time + swingDuration;
		swingSpeed = swingAngle / swingDuration;
		this.swingAngle = swingAngle;
		this.swingDuration = swingDuration;
	}

	public Swing(Shape shape, double swingAngle, int swingDuration) {
		super(shape, swingDuration + 5, swingDuration, Color.green);
		swingSpeed = swingAngle / swingDuration;
		this.swingAngle = swingAngle;
		this.swingDuration = swingDuration;
	}

	public void update(double time) {
		super.update(time);
		this.position = owner.position;
		shape.setPosition(position);
		shape.rotate(swingSpeed * time);
		setHeading(heading.rotate(swingSpeed * time));
	}

	public void addCopytoWorld(World world, Vector2D position, GameAgent owner, int damage) {
		world.addEntity(new Swing(shape.clone(), world, swingAngle, swingDuration, owner, damage));
	}

	public void render(Graphics g) {
		//super.render(g);
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
