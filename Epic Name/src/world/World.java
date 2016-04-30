package world;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import weapons.entities.Projectile;
import entities.Entity;
import entities.GameAgent;
import entities.Player;
import geometry.Circle;
import geometry.Shape;
import geometry.Vector2D;
import graphics.Renderable;

public class World implements Renderable{

	private ArrayList<Entity> entities;
	private ArrayList<Wall> walls;
	private ArrayList<WallImage> wallImages;
	public long time=0;
	public Player player;
	
	public World(){
		entities=new ArrayList<Entity>();
		walls=new ArrayList<Wall>();
	}
	
	public World(ArrayList<Entity> entities,ArrayList<Wall> walls){
		this.entities=entities;
		this.walls=walls;
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	public ArrayList<Wall> getWalls(){
		return walls;
	}
	
	public void update(double time){
		this.time+=time;
		ArrayList<Entity> temp=new ArrayList<Entity>(entities);
		for(Entity e:temp){
			e.update(time);
		}
	}
	
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	public void addWall(Wall w){
		walls.add(w);
	}
	
	/**
	 * 
	 * @return first wall that is intersected, returns null if none found
	 */
	public Wall findIntersectedWall(Shape shape){
		for(Wall w:walls){
			if(w.contains(shape))
				return w;
		}
		return null;
	}
	
	/**
	 * 
	 * @param shape: shape to test for intersection with agents
	 * @param agent: agent to be ignored
	 * @return first agent that is intersected, returns null if none found
	 */
	public GameAgent findIntersectedAgent(Shape shape,GameAgent agent) {
		for(Entity e:entities){
			if(e instanceof GameAgent){
				GameAgent a=(GameAgent)e;
				if(a!=agent&&a.contains(shape))
						return (GameAgent) e;
			}
		}
		return null;
	}
	
	public ArrayList<GameAgent> findAllIntersectedAgents(Shape shape,GameAgent agent){
		ArrayList<GameAgent> intersected=new ArrayList<GameAgent>();
		for(Entity e:entities){
			if(e instanceof GameAgent){
				GameAgent a=(GameAgent)e;
				if(a!=agent&&a.contains(shape))
						intersected.add(a);
			}
		}
		return intersected;
	}
	
	/**
	 * @return normal of the intersected wall
	 * @return null if no walls are intersected
	 */
	public Vector2D intersectsWallNormal(Shape shape){
		Vector2D resultingNormal=new Vector2D(0,0);
		for(Wall w:walls){
			resultingNormal.iadd(w.getNormal(shape));
		}
		if(resultingNormal.isZeroed())
			return null;
		else
			return resultingNormal;
	}
	
	/**
	 * @return normal away from interected agents
	 * @return null if no agents are intersected
	 */
	public Vector2D intersectAgentNormal(Shape shape,GameAgent agent){
		Vector2D resultingNormal=new Vector2D(0,0);
		for(Entity e:entities){
			if(e instanceof GameAgent){
				GameAgent a=(GameAgent)e;
				if(a!=agent&&a.contains(shape)){
					resultingNormal.iadd(agent.position.sub(a.position));
				}
			}
		}
		if(resultingNormal.isZeroed())
			return null;
		else
			return resultingNormal.normalize();
	}
	
	public void handleCollisions(GameAgent agent,double time){
		Vector2D agentNormal=intersectAgentNormal(agent.shape.clone(),agent);
		if(agentNormal!=null){
			Vector2D agentForce=agentNormal.normalize().mult(Math.abs(agent.getVelocity().add(agent.acceleration.mult(time)).dot(agentNormal.negative()))/agentNormal.magnitude()*agent.mass*1);
			agent.applyForce(agentForce);
		}
		Vector2D wallNormal=intersectsWallNormal(agent.shape.translate(agent.velocity));
		if(wallNormal!=null){
			Vector2D wallForce=wallNormal.normalize().mult(Math.abs(agent.velocity.add(agent.acceleration.mult(time)).dot(wallNormal.negative()))/wallNormal.magnitude()*agent.mass*1);
			agent.applyForce(wallForce);
		}
	}
	
	public void render(Graphics g){
		long start=System.nanoTime();
		for(Entity e:entities){
			if(e instanceof Renderable){
				((Renderable)e).render(g);
			}
		}
		for(Wall w:walls){
			w.render(g);
		}
		//System.out.println("full time - "+(System.nanoTime()-start));
		start=System.nanoTime();
		//new Circle(200,200,20).render(g);
		//System.out.println("for circle - "+(System.nanoTime()-start));
		
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	
}
