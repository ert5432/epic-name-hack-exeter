package world;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FloorTile {

	static BufferedImage image = null;
	int x;
	int y;
	public FloorTile(int x, int y) {
		this.x=x;
		this.y=y;
		try {
			image = ImageIO.read(new File("res/floor1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void render(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		


		g2d.drawImage(image,x,y,40,40,null);


		g2d.scale(2.5, 2.5);
		g2d.drawImage(image,x,y,null);
		
		g2d.scale(1.0/2.5, 1.0/2.5);
	}

}
