package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Color;
import java.awt.Graphics2D;

import net.richstudios.hammerandsickle.utilites.InputHandler;

public abstract class HudComponent {

	protected int x, y, width, height, size;

	protected HudAction action = null;
	protected boolean enabled = true;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public HudComponent(int x, int y, int width, int height, int size) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.size = size;
	}

	public void setAction(HudAction action) {
		this.action = action;
	}

	protected void doAction() {
		if (action != null) {
			action.actionPerformed(this);
		}
	}

	public void setEnabled(boolean b) {
		this.enabled = b;
	}

	public abstract void update(int ox, int oy);

	protected abstract void draw(Graphics2D g, int ox, int oy);

	public void drawTo(Graphics2D g, int ox, int oy) {
		draw(g, ox, oy);
		if (!enabled) {
			g.setColor(new Color(100, 100, 100, 100));
			g.fillRect(x, y, width, height);
		}
	}

	public void checkInteraction(InputHandler input, int ox, int oy) {
	}

}
