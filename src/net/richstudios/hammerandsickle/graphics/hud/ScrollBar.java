package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.richstudios.hammerandsickle.audio.Sound;
import net.richstudios.hammerandsickle.graphics.Textures;
import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.utilites.InputHandler;

public class ScrollBar extends HudComponent {

	private static final int SLIDER_WIDTH = 4, SLIDER_HEIGHT = 6, PEICE_HEIGHT = 4;
	private static final int NORMAL = 0, HOVERED = 1, CLICKED = 2;
	private int height, status;
	private int value, maxValue;
	private double step;
	private BufferedImage[] sprites;
	private BufferedImage[] buttonSprites;

	public ScrollBar(int x, int y, int height, int maxValue, int size) {
		super(x, y, 0, 0, size);
		this.status = NORMAL;
		this.size = size;
		this.maxValue = maxValue;
		this.sprites = new BufferedImage[Textures.getSpriteSheet("scrollbar").length];
		for (int i = 0; i < Textures.getSpriteSheet("scrollbar").length; i++) {
			this.sprites[i] = Textures.getSpriteSheet("scrollbar")[i][0];
		}
		this.buttonSprites = new BufferedImage[Textures.getSpriteSheet("scrollbarButton").length];
		for (int i = 0; i < Textures.getSpriteSheet("scrollbarButton").length; i++) {
			this.buttonSprites[i] = Textures.getSpriteSheet("scrollbarButton")[i][0];
		}
		this.height = height * PEICE_HEIGHT * size;
		step = (double) (this.height - (PEICE_HEIGHT * size) / 2D) / (double) maxValue;
	}

	public void update(int ox, int oy) {
	}

	protected void draw(Graphics2D g, int ox, int oy) {
		int sSize = PEICE_HEIGHT * size;
		int nParts = (height / PEICE_HEIGHT) / size;
		for (int i = 0; i <= nParts; i++) {
			int py = (y + oy) + (i * sSize);
			if (i == 0)
				g.drawImage(sprites[0], x + ox, py, sSize, sSize, null);
			else if (i == nParts)
				g.drawImage(sprites[2], x + ox, py, sSize, sSize, null);
			else
				g.drawImage(sprites[1], x + ox, py, sSize, sSize, null);
		}
		int sliderY = (int) ((y + oy) + (value * step));
		g.drawImage(buttonSprites[status], x + ox, sliderY, SLIDER_WIDTH * size, SLIDER_HEIGHT * size, null);
	}

	private boolean isMouseInside(InputHandler input, int ox, int oy) {
		int sliderX = x + ox;
		int sliderY = (int) ((y + oy) + (value * step));
		int sliderWidth = SLIDER_WIDTH * size;
		int sliderHeight = SLIDER_HEIGHT * size;
		return input.isMouseInside(sliderX * References.SCALE, sliderY * References.SCALE, sliderWidth * References.SCALE, sliderHeight * References.SCALE);
	}

	private void setValue(int i) {
		value = i;
		if (value < 0)
			value = 0;
		if (value > maxValue)
			value = maxValue;
	}

	public void checkInteraction(InputHandler input, int ox, int oy) {
		if (isMouseInside(input, ox, oy)) {
			if (input.isMousePressed(InputHandler.MOUSEBUTTONL)) {
				if (status != CLICKED) {
					status = CLICKED;
					doAction();
					Sound.play("click");
				}
			} else {
				status = HOVERED;
			}
		}
		if (!input.isMousePressed(InputHandler.MOUSEBUTTONL)) {
			status = NORMAL;
			if (isMouseInside(input, ox, oy)) {
				status = HOVERED;
			}
		}
		if (status == CLICKED)
			setValue((int) (((input.mouseY - (y + oy)) / References.SCALE - (SLIDER_HEIGHT * size) / 2) / step));
	}

	public int getValue() {
		return value;
	}

}
