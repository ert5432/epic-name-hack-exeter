package input;

import entities.GameAgent;
import entities.Player;
import entities.StateAgent;
import geometry.Circle;
import geometry.Line;
import geometry.PartialArc;
import geometry.Polygon;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Vector2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import stats.Stats;
import weapons.Bow;
import weapons.BowSword;
import weapons.PowerRod;
import weapons.Stone;
import weapons.Sword;
import world.Wall;
import world.World;
import ai.stateMachine.Charging;
import ai.stateMachine.StateMachine;
import ai.steering.*;

public class ViewScreen extends JPanel implements ActionListener{
//DWANG
	private World world;
	/*private Path patrol=new LinePath(new Vector2D[]{
		new Vector2D(605,656),new Vector2D(745,706),new Vector2D(817,709),
		new Vector2D(903,668),new Vector2D(935,598),new Vector2D(942,519),
		new Vector2D(968,436),new Vector2D(913,372),new Vector2D(1044,315),
		new Vector2D(957,206),new Vector2D(798,202),new Vector2D(690,159),
		new Vector2D(520,180),new Vector2D(433,241),new Vector2D(252,278),
		new Vector2D(211,334),new Vector2D(208,420),new Vector2D(251,531),
		new Vector2D(338,645),new Vector2D(434,797),new Vector2D(527,782),
		new Vector2D(492,706),new Vector2D(540,694),new Vector2D(641,785),
		new Vector2D(749,820),new Vector2D(837,810)
	});*/
	private ArrayList<Vector2D> path=new ArrayList<Vector2D>();
	private Path patrol=null;
	private boolean planningRoute=false;
	private Map<Integer, Action> actionMap = new HashMap<Integer, Action>();
	private Input input;
	protected int mouseX,mouseY;
	long totalUpdate=0,totalRender=0;
	Player g1;
	Player g2;
	GameAgent g3;
	private Behavior steer;
//	JFrame f;
	public static final double fps=100;
	
	@SuppressWarnings("serial")
	public ViewScreen(){
		super();
//		this.f=f;
		setPreferredSize(new Dimension(1500,950));
		setBackground(Color.WHITE);
		setFocusable(true);
		requestFocusInWindow();
		input=new Input(){
			public void mousePressed(MouseEvent e){
				super.mousePressed(e);
				if(planningRoute){
					path.add(new Vector2D(getMouseX(),getMouseY()));
					System.out.println("new Vector2D("+getMouseX()+","+getMouseY()+")");
				}
			}
		};
		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		world=new World();
		world.addWall(new Wall(new Rectangle(750,-10,1500,20)));
		world.addWall(new Wall(new Rectangle(-10,475,20,950)));
		world.addWall(new Wall(new Rectangle(1510,475,20,950)));
		world.addWall(new Wall(new Rectangle(750,960,1500,20)));
   		g1=new Player(new Vector2D(500,500),new Circle(20), world, new Stats(30,30,40,40,100000)){
  			public void update(double time){
				super.update(time);
				this.setTarget(new Vector2D(input.getMouseX(),input.getMouseY()));
			}
   		};
		world.addEntity(g1);
		g1.setWeapon(new Bow(g1));
		g2=new Player(new Vector2D(700,500), new Circle(20), world, new Stats(30,0,30,30,100)){
			public void update(double time){
				super.update(time);
				this.setTarget(new Vector2D(input.getMouseX(),input.getMouseY()));
			}
		};
		g2.setWeapon(new PowerRod(g1));
		world.addEntity(g2);
		g3=new StateAgent(600,600,new Circle(20),world,new Stats(30,30,30,30,100));
		g3.setWeapon(new Sword(g3));
		
		StateMachine sm=((StateAgent)g3).getStateMachine();
		sm.changeState(new Charging(g3,sm,g1));
		world.addEntity(g3);
		
		//world.addWall(new Wall(new Polygon(new double[]{50,150,50},new double[]{50,50,150}),Color.BLACK));
		world.addWall(new Wall(new Rectangle(750,70,1400,40)));
		Rectangle w=new Rectangle(750,70,1400,40);
		printRect(w);
		//world.addWall(new Wall(new Rectangle(70,475,40,770)));
		System.out.println();
		//world.addWall(new Wall(new Rectangle(70,475,40,770)));
		world.addWall(new Wall(new Rectangle(750,880,1400,40)));
		world.addWall(new Wall(new Rectangle(70, 232.5, 40, 285)));
		world.addWall(new Wall(new Rectangle(70, 717.5, 40, 285)));
		world.addWall(new Wall(new Rectangle(1425, 232.5, 50, 285)));
		world.addWall(new Wall(new Rectangle(1425, 717.5, 50, 285)));
		
		//world.addWall(new Wall(new Rectangle(745, 160, 1210, 40)));
		world.addWall(new Wall(new Rectangle(160, 475, 40, 590)));
		world.addWall(new Wall(new Rectangle(392.5, 160, 505, 40)));
		world.addWall(new Wall(new Rectangle(1097.5, 160, 505, 40)));
		world.addWall(new Wall(new Rectangle(392.5, 790, 505, 40)));
		world.addWall(new Wall(new Rectangle(1097.5, 790, 505, 40)));
		//world.addWall(new Wall(new Rectangle(745, 790, 1210, 40)));
		world.addWall(new Wall(new Rectangle(1330, 475, 40, 590)));
		
		w=new Rectangle(750,880,1400,40);
		printRect(w);
		new WallMaker().start();
		Timer t=new Timer((int) (1000/fps),this);
		t.start();
		addAction(KeyEvent.VK_W, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				g1.move(new Vector2D(0,-1).mult(g1.getMaxAcceleration()));
			}
		});
		addAction(KeyEvent.VK_S, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				g1.move(new Vector2D(0,1).mult(g1.getMaxAcceleration()));
			}
		});
		addAction(KeyEvent.VK_A, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				g1.move(new Vector2D(-1,0).mult(g1.getMaxAcceleration()));
			}
		});
		addAction(KeyEvent.VK_D, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				
				g1.move(new Vector2D(1,0).mult(g1.getMaxAcceleration()));
			}
		});
		addAction(KeyEvent.VK_UP, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				g2.move(new Vector2D(0,-1).mult(g2.getMaxAcceleration()));
			}
		});
		addAction(KeyEvent.VK_DOWN, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				g2.move(new Vector2D(0,1).mult(g2.getMaxAcceleration()));
			}
		});
		addAction(KeyEvent.VK_LEFT, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				g2.move(new Vector2D(-1,0).mult(g2.getMaxAcceleration()));
			}
		});
		addAction(KeyEvent.VK_RIGHT, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				g2.move(new Vector2D(1,0).mult(g2.getMaxAcceleration()));
			}
		});
		addAction(Input.MOUSE_BUTTON1, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				g1.act(0);				
			}
		});
		addAction(Input.MOUSE_BUTTON2, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				g1.act(1);
			}
		});
		addAction(Input.MOUSE_BUTTON3, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				g1.act(2);
			}
		});
		addAction(Input.MOUSE_BUTTON4, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				g1.act(3);
			}
		});
		addAction(Input.MOUSE_BUTTON5, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				g1.act(4);
			}
		});
		addAction(KeyEvent.VK_SPACE,new AbstractAction(){
			public void actionPerformed(ActionEvent arg0) {
				g2.act(1);
			}
		});
		addAction(KeyEvent.VK_SLASH,new AbstractAction(){
			public void actionPerformed(ActionEvent arg0) {
				g2.act(0);
			}
		});
		addAction(KeyEvent.VK_1,new AbstractAction(){
			public void actionPerformed(ActionEvent arg0) {
				g2.act(2);
			}
		});
		addAction(KeyEvent.VK_2,new AbstractAction(){
			public void actionPerformed(ActionEvent arg0) {
				if(planningRoute){
					planningRoute=false;
					patrol=new LinePath(path.toArray(new Vector2D[path.size()]));
					steer=new FollowPath(g3,patrol);
				}
			}
		});
		addAction(KeyEvent.VK_3,new AbstractAction(){
			public void actionPerformed(ActionEvent arg0) {
				planningRoute=true;
			}
		});
		addAction(KeyEvent.VK_4,new AbstractAction(){
			public void actionPerformed(ActionEvent arg0) {
				path.clear();
				patrol=null;
				steer=null;
			}
		});
	}
	
	public static void main(String[] args){
		JFrame frame=new JFrame("Test");
		frame.add(new ViewScreen());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		world.render(g);
		if(planningRoute||input.getButtonInput()[KeyEvent.VK_CONTROL]){
			LinePath.render(path.toArray(new Vector2D[path.size()]), g);
		}
		g.setColor(Color.blue);
		g.drawString(""+g1.hp+"    "+mouseX+","+mouseY, 2, 30);
	}
	
	public void addAction(int key, Action a){
		actionMap.put(key, a);
	}
	
	public void actionPerformed(ActionEvent e) {
		boolean[] buttonInputs=input.getButtonInput();
		mouseX=input.getMouseX();
		mouseY=input.getMouseY();
		for(int i=0;i<buttonInputs.length;i++){
			if(buttonInputs[i]){
				Action a=actionMap.get(i);
				if(a!=null){
					a.actionPerformed(e);
				}
			}
		}
		long start=System.nanoTime();
		if(!planningRoute){
			world.update((int)(1000/fps)/10);
		}
		long time=System.nanoTime()-start;
		totalUpdate+=time;
		//System.out.println("world update time - "+time);
		//System.out.println("average update time - "+(totalUpdate/world.time));
		repaint();
		time=System.nanoTime()-start;
		totalRender+=time;
		//System.out.println("world render time - "+time);
		//System.out.println("average render time - "+(totalRender/world.time));
	}
	
	public class WallMaker extends Thread {
		public void run(){
			Scanner scan=new Scanner(System.in);
			while(true){
				double x1=scan.nextInt();
				double y1=scan.nextInt();
				double x2=scan.nextInt();
				double y2=scan.nextInt();
				world.addWall(new Wall(new Rectangle((x1+x2)/2,(y1+y2)/2,x2-x1,y2-y1)));
				System.out.printf("world.addWall(new Wall(new Rectangle(%f, %f, %f, %f)))",(x1+x2)/2,(y1+y2)/2,x2-x1,y2-y1);
			}
		}
	}
	
	public static void printRect(Rectangle w){
		System.out.println(w.points[0].add(w.getPosition())+"   "+w.points[2].add(w.getPosition()));
	}
	
}
