package net.richstudios.hammerandsickle.utilites;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import net.richstudios.hammerandsickle.Game;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, FocusListener {

	public static final int NUM_KEYS = 16;

	private boolean keyState[] = new boolean[NUM_KEYS];
	private boolean prevKeyState[] = new boolean[NUM_KEYS];

	public static int UP = 0;
	public static int LEFT = 1;
	public static int DOWN = 2;
	public static int RIGHT = 3;
	public static int BUTTON1 = 4;
	public static int BUTTON2 = 5;
	public static int BUTTON3 = 6;
	public static int BUTTON4 = 7;
	public static int ENTER = 8;
	public static int ESCAPE = 9;
	public static int F12 = 10;
	public static final int F11 = 11;
	public static int Q = 12;
	public static int W = 13;
	public static int E = 14;
	
	public static final int NUM_MOUSEBUTTONS = 3;

	private boolean mouseButtonState[] = new boolean[NUM_MOUSEBUTTONS];
	private boolean prevMouseButtonState[] = new boolean[NUM_MOUSEBUTTONS];
	
	public static int MOUSEBUTTONL = 1;
	public static int MOUSEBUTTONM = 2;
	public static int MOUSEBUTTONR = 3;
	
	public double mouseWheelMovement = 0;
	
	public int mouseX = 0, mouseY = 0;
	
	public InputHandler(Game gp) {
		gp.addKeyListener(this);
		gp.addMouseListener(this);
		gp.addMouseMotionListener(this);
		gp.addMouseWheelListener(this);
		gp.addFocusListener(this);
	}

	public void keySet(int i, boolean b) {
		if (i == KeyEvent.VK_UP)
			keyState[UP] = b;
		else if (i == KeyEvent.VK_LEFT)
			keyState[LEFT] = b;
		else if (i == KeyEvent.VK_DOWN)
			keyState[DOWN] = b;
		else if (i == KeyEvent.VK_RIGHT)
			keyState[RIGHT] = b;
		else if (i == KeyEvent.VK_Z)
			keyState[BUTTON1] = b;
		else if (i == KeyEvent.VK_X)
			keyState[BUTTON2] = b;
		else if (i == KeyEvent.VK_C)
			keyState[BUTTON3] = b;
		else if (i == KeyEvent.VK_V)
			keyState[BUTTON4] = b;
		else if (i == KeyEvent.VK_ENTER)
			keyState[ENTER] = b;
		else if (i == KeyEvent.VK_ESCAPE)
			keyState[ESCAPE] = b;
		else if (i == KeyEvent.VK_F12)
			keyState[F12] = b;
		else if (i == KeyEvent.VK_F11)
			keyState[F11] = b;
		else if (i == KeyEvent.VK_Q)
			keyState[Q] = b;
		else if (i == KeyEvent.VK_W)
			keyState[W] = b;
		else if (i == KeyEvent.VK_E)
			keyState[E] = b;
	}
	
	public void mouseSet(int i, boolean b) {
		if (i == MouseEvent.BUTTON1)
			mouseButtonState[MOUSEBUTTONL] = b;
		else if (i == MouseEvent.BUTTON2)
			mouseButtonState[MOUSEBUTTONM] = b;
		else if (i == MouseEvent.BUTTON3)
			mouseButtonState[MOUSEBUTTONR] = b;
	}

	public void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
		
		for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
			prevMouseButtonState[i] = mouseButtonState[i];
		}
		
		mouseWheelMovement = 0;
	}

	public boolean isPressed(int i) {
		return keyState[i];
	}

	public boolean isTyped(int i) {
		return keyState[i] && !prevKeyState[i];
	}

	public boolean anyKeyPress() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (keyState[i])
				return true;
		}
		return false;
	}

	public boolean anyConfirmKeyPress() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (keyState[i] && (i == BUTTON1 || i == BUTTON2 || i == BUTTON3 || i == BUTTON4 || i == ENTER))
				return true;
		}
		return false;
	}
	
	public boolean isMousePressed(int i) {
		return mouseButtonState[i];
	}

	public boolean isMouseClicked(int i) {
		return mouseButtonState[i] && !prevMouseButtonState[i];
	}

	public boolean anyMousePress() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (mouseButtonState[i])
				return true;
		}
		return false;
	}
	
	public void keyPressed(KeyEvent e) {
		keySet(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		keySet(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		mouseSet(e.getButton(), true);
	}

	public void mouseReleased(MouseEvent e) {
		mouseSet(e.getButton(), false);
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if(notches > 0) {
			mouseWheelMovement++;
		} else if(notches < 0) {
			mouseWheelMovement--;
		}
	}

	public void focusGained(FocusEvent e) {}

	public void focusLost(FocusEvent e) {
		for (int i = 0; i < NUM_KEYS; i++) {
			keyState[i] = false;
		}
		for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
			mouseButtonState[i] = false;
		}
	}
	
	public boolean isMouseInside(int x, int y, int width, int height) {
		if(mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height) {
			return true;
		}
		return false;
	}

}
