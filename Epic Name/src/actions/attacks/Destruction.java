package actions.attacks;

import java.awt.Color;

import stats.DieRoll;
import weapons.entities.Wave;

public class Destruction extends WaveAttackInfo {

	public Destruction() {
		super(new Wave(Math.PI*2,30,400,10,6,Color.red),new DieRoll(3,6),"int","wis");
	}

}
