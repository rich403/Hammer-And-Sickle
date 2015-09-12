package net.richstudios.hammerandsickle.gamestate;

import java.awt.Graphics2D;
import java.util.LinkedList;

import net.richstudios.hammerandsickle.utilites.InputHandler;

public class GameStateManager {

	private boolean blockInput = false;
	
	private InputHandler keys;
	private LinkedList<GameState> states;
	
	public GameStateManager(InputHandler keys) {
		states = new LinkedList<GameState>();
		this.keys = keys;
	}
	
	public void update() {
		if (!blockInput)
			states.peek().handleInput(keys);
		states.peek().update();
	}
	
	public void draw(Graphics2D d) {
		states.peek().draw(d);
	}
	
	public void pop() {
		states.pop();
	}
	
	public void push(GameState s) {
		s.init();
		states.push(s);
	}
	
	public void set(GameState s) {
		states.pop();
		s.init();
		states.push(s);
	}
	
	public void blockInput(boolean b) {
		blockInput = b;
	}
}
