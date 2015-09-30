package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.richstudios.hammerandsickle.utilites.StringUtils;

public class Button extends Box {
	
	private String text;
	
	private static final int NORMAL = 0, HOVERED = 1, CLICKED = 2;
	private int status;
	
	public Button(int x, int y, int width, int height, String text, int size) {
		super(x, y, width, height, size);
		this.text = text;
	}
	
	protected void draw(Graphics2D g, int ox, int oy) {
		super.draw(g, ox, oy);
		int tx = ox + x + (width / 2) - (StringUtils.getStringWidth(g.getFont(), text) / 2);
		int ty = oy + y + (width / 2) - (StringUtils.getStringHeight(g.getFont(), text) / 2);
		g.setColor(Color.YELLOW);
		g.drawString(text, tx, ty);
	}

}
