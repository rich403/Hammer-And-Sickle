package net.richstudios.hammerandsickle.graphics.hud;

import net.richstudios.hammerandsickle.graphics.Textures;

public class TextBox extends Box {
	
	private String text = "";

	public TextBox(int x, int y, int width, int height, int size) {
		super(x, y, width, height, size);
		this.sprites = Textures.getSpriteSheet("textBox");
	}

}
