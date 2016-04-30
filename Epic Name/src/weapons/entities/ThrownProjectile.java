package weapons.entities;

import world.World;
import entities.GameAgent;
import geometry.Shape;
import geometry.Vector2D;

public class ThrownProjectile extends RangedProjectile {

	public ThrownProjectile(Vector2D relativePosition, Vector2D direction,Shape shape, World world, GameAgent owner, double maxSpeed,
			double accuracyRange, int coolDown, int flyTime, int damage,double mass) {
		super(relativePosition, direction, shape, world, owner, maxSpeed,
				accuracyRange, coolDown, flyTime, damage);
		this.mass=mass;
	}

	public ThrownProjectile(Vector2D relativePosition, Shape shape,
			double maxSpeed, double accuracyRange, int coolDown, int flyTime,double mass) {
		super(relativePosition, shape, maxSpeed, accuracyRange, coolDown,
				flyTime);
		this.mass=mass;
	}

}
