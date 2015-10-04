package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Graphics2D;

import net.richstudios.hammerandsickle.graphics.GameFont;

public class HudLabel extends HudComponent {
	
	private String text;

	public HudLabel(String text, int x, int y, int size) {
		super(x, y, GameFont.getStringWidth(text, size), GameFont.HEIGHT * size, size);
		this.text = text.toUpperCase();
	}
	
	public void setText(String text) {
		this.text = text.toUpperCase();
		this.width = GameFont.getStringWidth(text, size);
		this.height = GameFont.HEIGHT * size;
	}

	public void update(int ox, int oy) {}

	protected void draw(Graphics2D g, int ox, int oy) {
		GameFont.render(text, g, x + ox, y + oy, size);
	}

}
