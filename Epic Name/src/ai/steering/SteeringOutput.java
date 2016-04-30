package ai.steering;

import geometry.Vector2D;

public class SteeringOutput {

	public Vector2D linear;
	public double angular;
	
	public SteeringOutput(){
		angular=0;
		linear=new Vector2D(0,0);
	}
	
	public SteeringOutput(Vector2D linear,double angular){
		this.linear=linear;
		this.angular=angular;
	}
	
}
