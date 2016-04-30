package weapons.entities;

import java.awt.Color;

import entities.GameAgent;
import geometry.Shape;
import geometry.Vector2D;
import world.World;

public class Swing extends MeleeEntity{

	private double swingSpeed;
	private long swingEnd;
	private double swingAngle;
	private int swingDuration;
	
	public Swing(Shape shape, World world,double swingAngle,int swingDuration,GameAgent owner,int damage) {
		super(owner.position, shape, world,owner,swingDuration+5,swingDuration,damage,Color.green);
		shape.rotate(new Vector2D(1,0).angleTo(owner.heading)-swingAngle/2);
		swingEnd=world.time+swingDuration;
		swingSpeed=swingAngle/swingDuration;
		this.swingAngle=swingAngle;
		this.swingDuration=swingDuration;
	}
	
	public Swing(Shape shape,double swingAngle,int swingDuration){
		super(shape,swingDuration+5,swingDuration,Color.green);
		swingSpeed=swingAngle/swingDuration;
		this.swingAngle=swingAngle;
		this.swingDuration=swingDuration;
	}
	
	public void update(double time){
		super.update(time);
		this.position=owner.position;
		shape.setPosition(position);
		shape.rotate(swingSpeed*time);
		setHeading(heading.rotate(swingSpeed*time));
	}
	
	public void addCopytoWorld(World world,Vector2D position,GameAgent owner,int damage){
		world.addEntity(new Swing(shape.clone(),world,swingAngle,swingDuration,owner,damage));
	}

}
