package maps;

import java.util.ArrayList;
import java.util.Random;

import geometry.Rectangle;
import world.World;

public class LevelGenerator {
	
	
	public static World generate(long seed){
		Random r=new Random();
		Level l=new Level();
		World w=new World();
		
		l.addRoom("rooms/room1.png", 0);
		
		return l.toWorld();
	}
	
}