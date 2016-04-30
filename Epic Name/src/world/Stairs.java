package world;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import geometry.Rectangle;

public class Stairs {
	public Rectangle rect;
	BufferedImage sprite;
	int x,y;
	public Stairs(int x,int y){
		rect=new Rectangle(x+20,y+20,40,40);
		this.x=x;
		this.y=y;
		try {
			sprite = ImageIO.read(new File("res/stairs.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g){
		Graphics2D g2=(Graphics2D) g;
		g2.drawImage(sprite, x, y, 40, 40, null);
	}
}
