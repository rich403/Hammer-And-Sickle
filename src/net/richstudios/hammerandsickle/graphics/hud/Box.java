package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.richstudios.hammerandsickle.graphics.Textures;

public class Box extends HudComponent {

	private static final int SIZE = 4;
	
	private BufferedImage[][] sprites;
	
	public Box(int x, int y, int width, int height, int size) {
		super(x, y, width * SIZE, height * SIZE, size);
		this.sprites = Textures.getSpriteSheet("box");
	}

	public void update(int ox, int oy) {
		
	}

	protected void draw(Graphics2D g, int ox, int oy) {
		
	}

}
