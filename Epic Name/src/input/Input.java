package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener,MouseListener,MouseMotionListener{

	private boolean[] buttonInput;
	public static int KEY_INPUT_LENGTH=256;
	public static int MOUSE_BUTTON1=KEY_INPUT_LENGTH;
	public static int MOUSE_BUTTON2=KEY_INPUT_LENGTH+1;
	public static int MOUSE_BUTTON3=KEY_INPUT_LENGTH+2;
	public static int MOUSE_BUTTON4=KEY_INPUT_LENGTH+3;
	public static int MOUSE_BUTTON5=KEY_INPUT_LENGTH+4;
	private int mouseX, mouseY;
	
	public Input(){
		buttonInput=new boolean[KEY_INPUT_LENGTH+5];
		mouseX=0;
		mouseY=0;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		if(key>=0&&key<KEY_INPUT_LENGTH)
			buttonInput[key]=true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		if(key>=0&&key<KEY_INPUT_LENGTH)
			buttonInput[key]=false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e){
		mouseMoved(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int button=e.getButton();
		buttonInput[KEY_INPUT_LENGTH+button-1]=true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int button=e.getButton();
		buttonInput[KEY_INPUT_LENGTH+button-1]=false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX=e.getX();
		mouseY=e.getY();
	}
	
	public boolean[] getButtonInput(){
		return buttonInput;
	}
	
	public int getMouseX(){
		return mouseX;
	}
	
	public int getMouseY(){
		return mouseY;
	}

}
