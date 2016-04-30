package stats;

import java.util.Random;

public class DieRoll {

	public static final Random rand=new Random();
	
	private int dice,sides;
	
	public DieRoll(int dice,int sides) {
		this.dice=dice;
		this.sides=sides;
	}

	public int roll(){
		int roll=0;
		for(int i=0;i<dice;i++){
			roll+=rand.nextInt(sides)+1;
		}
		return roll;
	}
	
	public String toString(){
		return dice+"d"+sides;
	}
	
}
