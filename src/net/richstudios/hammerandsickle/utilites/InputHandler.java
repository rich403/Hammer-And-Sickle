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
import java.util.ArrayList;

import net.richstudios.hammerandsickle.Game;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, FocusListener {

	public static final int NUM_KEYS = KeyEvent.KEY_LAST;

	private boolean keyState[] = new boolean[NUM_KEYS];
	private boolean prevKeyState[] = new boolean[NUM_KEYS];

	private int modifiers[] = new int[NUM_KEYS];

	public static final int UP = KeyEvent.VK_UP;
	public static final int LEFT = KeyEvent.VK_LEFT;
	public static final int DOWN = KeyEvent.VK_DOWN;
	public static final int RIGHT = KeyEvent.VK_RIGHT;
	public static final int BUTTON1 = KeyEvent.VK_Z;
	public static final int BUTTON2 = KeyEvent.VK_X;
	public static final int BUTTON3 = KeyEvent.VK_C;
	public static final int BUTTON4 = KeyEvent.VK_V;
	public static final int ENTER = KeyEvent.VK_ENTER;
	public static final int ESCAPE = KeyEvent.VK_ESCAPE;
	public static final int F12 = KeyEvent.VK_F12;
	public static final int F11 = KeyEvent.VK_F11;
	public static final int Q = KeyEvent.VK_Q;
	public static final int W = KeyEvent.VK_W;
	public static final int E = KeyEvent.VK_E;

	public static final int NUM_MOUSEBUTTONS = 3;

	private boolean mouseButtonState[] = new boolean[NUM_MOUSEBUTTONS];
	private boolean prevMouseButtonState[] = new boolean[NUM_MOUSEBUTTONS];

	public static int MOUSEBUTTONL = 0;
	public static int MOUSEBUTTONM = 1;
	public static int MOUSEBUTTONR = 2;

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
		keyState[i] = b;
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

	public boolean isKeyPressed(int i) {
		return keyState[i];
	}

	public boolean isKeyTyped(int i) {
		return keyState[i] && !prevKeyState[i];
	}

	public int getModifiers(int i) {
		return modifiers[i];
	}

	public boolean anyKeyPress() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (keyState[i])
				return true;
		}
		return false;
	}

	public boolean anyConfirmKeyPress() {
		Integer[] keys = keysThatArePressed();
		for (int i = 0; i < keys.length; i++) {
			int keyCode = keys[i];
			if (isKeyTyped(keyCode) && (keyCode == BUTTON1 || keyCode == BUTTON2 || keyCode == BUTTON3 || keyCode == BUTTON4 || keyCode == ENTER))
				return true;
		}
		return false;
	}

	public Integer[] keysThatArePressed() {
		ArrayList<Integer> out = new ArrayList<Integer>();
		for (int i = 0; i < NUM_KEYS; i++) {
			if (isKeyPressed(i))
				out.add(i);
		}
		return out.toArray(new Integer[out.size()]);
	}
	
	public Integer[] keysThatAreTyped() {
		ArrayList<Integer> out = new ArrayList<Integer>();
		for (int i = 0; i < NUM_KEYS; i++) {
			if (isKeyTyped(i))
				out.add(i);
		}
		return out.toArray(new Integer[out.size()]);
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
		modifiers[e.getKeyCode()] = e.getModifiers();
		keySet(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		keySet(e.getKeyCode(), false);
		modifiers[e.getKeyCode()] = 1;
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

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

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
		if (notches > 0) {
			mouseWheelMovement++;
		} else if (notches < 0) {
			mouseWheelMovement--;
		}
	}

	public void focusGained(FocusEvent e) {
	}

	public void focusLost(FocusEvent e) {
		for (int i = 0; i < NUM_KEYS; i++) {
			keyState[i] = false;
		}
		for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
			mouseButtonState[i] = false;
		}
	}

	public boolean isMouseInside(int x, int y, int width, int height) {
		if (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height) {
			return true;
		}
		return false;
	}

}
