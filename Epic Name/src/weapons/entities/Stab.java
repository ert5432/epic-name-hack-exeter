package weapons.entities;

import java.awt.Color;

import world.World;
import entities.GameAgent;
import geometry.Polygon;
import geometry.Shape;
import geometry.Vector2D;

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
}
