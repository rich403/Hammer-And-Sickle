package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.richstudios.hammerandsickle.graphics.Textures;

public class Box extends HudComponent {

	public static final int SIZE = 4;
	
	private int aWidth, aHeight;
	private BufferedImage[][] sprites;
	
	public Box(int x, int y, int width, int height, int size) {
		super(x, y, width * SIZE, height * SIZE, size);
		this.aWidth = width;
		this.aHeight = height;
		this.sprites = Textures.getSpriteSheet("box");
	}

	public void update(int ox, int oy) {
		
	}

	protected void draw(Graphics2D g, int ox, int oy) {
		for (int x = 0; x <= aWidth; x++) {
			for (int y = 0; y <= aHeight; y++) {
				int dx = (this.x + ox) + (SIZE * x) * size;
				int dy = (this.y + oy) + (SIZE * y) * size;
				if(x == 0 && y == 0) g.drawImage(sprites[0][0], dx, dy, SIZE * size, SIZE * size, null);
				else if(x == 0 && y == aHeight) g.drawImage(sprites[2][0], dx, dy, SIZE * size, SIZE * size, null);
				else if(x == aWidth && y == 0) g.drawImage(sprites[0][2], dx, dy, SIZE * size, SIZE * size, null);
				else if(x == 0) g.drawImage(sprites[1][0], dx, dy, SIZE * size, SIZE * size, null);
				else if(y == 0) g.drawImage(sprites[0][1], dx, dy, SIZE * size, SIZE * size, null);
				else if(x == aWidth && y == aHeight) g.drawImage(sprites[2][2], dx, dy, SIZE * size, SIZE * size, null);
				else if(x == aWidth) g.drawImage(sprites[1][2], dx, dy, SIZE * size, SIZE * size, null);
				else if(y == aHeight) g.drawImage(sprites[2][1], dx, dy, SIZE * size, SIZE * size, null);
				else g.drawImage(sprites[1][1], dx, dy, SIZE * size, SIZE * size, null);
			}
		}
	}

}
