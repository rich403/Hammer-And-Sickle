package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Color;
import java.awt.Graphics2D;

import net.richstudios.hammerandsickle.utilites.InputHandler;

public abstract class HudComponent {

	protected int x, y, width, height, size;
	protected HudAction action = null;
	protected boolean enabled = true;

	public HudComponent(int x, int y, int width, int height, int size) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.size = size;
	}

	public void addAction(HudAction action) {
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
