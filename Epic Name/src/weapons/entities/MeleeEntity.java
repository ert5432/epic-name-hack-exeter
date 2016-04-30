package weapons.entities;

import java.awt.Color;

import world.World;
import entities.GameAgent;
import geometry.Shape;
import geometry.Vector2D;

public abstract class MeleeEntity extends WeaponEntity {

	public MeleeEntity(Shape shape, int coolDown, int duration, Color color) {
		super(shape, coolDown, duration, color, false);
	}

	public MeleeEntity(Vector2D position, Shape shape, World world,GameAgent owner, int coolDown, int duration, int damage,Color color) {
		super(position, shape, world, owner, coolDown, duration, damage, color,false);
		System.out.println(damage);
		System.out.println("hey");
	}

	public void update(double time){
		super.update(time);
		boolean flag=!owner.isDead();
		if(flag){
			if(!hasHit&&!lastHit.isEmpty()){
				lastHit.get(0).damage(damage);
				hasHit=true;
			}
		}
		else
			world.removeEntity(this);
	}
	
}
