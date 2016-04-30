package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ai.stateMachine.StateMachine;
import ai.stateMachine.WaitForPlayer;
import geometry.Circle;
import geometry.Shape;
import stats.Stats;
import weapons.PowerRod;
import world.World;

public class Beholder extends StateAgent{

	public Beholder(double x, double y, World world) {
		super(x, y, new Circle(32), world, new Stats(30,30,30,30,60));
		setWeapon(new PowerRod(this));
		StateMachine sm=getStateMachine();
		sm.changeState(new WaitForPlayer(this,sm,world.player));
	}
	
	public void render(Graphics g){
	
		
		Graphics2D g2d = (Graphics2D)(g);
		g2d.rotate(rotation, position.x, position.y);
		g2d.scale(4, 4);

		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/beholder.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g2d.drawImage(image, (int) ((position.x - 20) / 4.0), (int) ((position.y - 24) / 4.0), null);
		
		g2d.scale(.25, .25);
		g2d.rotate(-rotation,position.x,position.y);
	}

}
