package actions.attacks;

import java.awt.Color;

import stats.DieRoll;
import weapons.entities.Wave;

public class FlameBurst extends WaveAttackInfo {

	public FlameBurst() {
		super(new Wave(Math.PI*2,20,200,10,4,Color.red),new DieRoll(1,3),"wis","int");
	}

}
