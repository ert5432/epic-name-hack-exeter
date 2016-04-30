package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	private BufferedImage spriteSheet;
	private int tileSize;
	
	public Sprite(String ref, int tileSize){
		this.tileSize = tileSize;
		spriteSheet = loadSprite(ref);
	}

	public BufferedImage loadSprite(String file) {

		BufferedImage sprite = null;

		try {
			sprite = ImageIO.read(new File("res/" + file + ".PNG"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sprite;
	}

	public BufferedImage getSprite(int xGrid, int yGrid) {
		return spriteSheet.getSubimage(xGrid * tileSize, yGrid * tileSize, tileSize, tileSize);
	}
}