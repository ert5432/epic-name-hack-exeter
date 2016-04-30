package stats;

public class Stat {

	public int val;
	public int modifier;
	public double multiplier;
	
	public Stat(int value) {
		setValue(value);
	}
	
	public void setValue(int value){
		val=value;
		modifier=value/3-5;
		if(modifier<0){
			multiplier=1.0/(1.0-modifier);
		}
		else{
			multiplier=modifier+1;
		}
	}

}
