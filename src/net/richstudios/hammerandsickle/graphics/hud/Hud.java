package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Graphics2D;
import java.util.ArrayList;

import net.richstudios.hammerandsickle.utilites.InputHandler;

public class Hud {

	private ArrayList<HudComponent> components;

	private int x, y;

	public Hud(int x, int y) {
		this.x = x;
		this.y = y;
		components = new ArrayList<HudComponent>();
	}

	public Hud() {
		this(0, 0);
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics2D g) {
		for (HudComponent c : components) {
			c.drawTo(g, x, y);
		}
	}

	public void update() {
		for (HudComponent c : components) {
			if(!c.enabled) continue;
			c.update(x, y);
		}
	}

	public void handleInput(InputHandler input) {
		for (HudComponent c : components) {
			if(!c.enabled) continue;
			c.checkInteraction(input, x, y);
		}
	}

	public void add(HudComponent c) {
		components.add(c);
	}
}
