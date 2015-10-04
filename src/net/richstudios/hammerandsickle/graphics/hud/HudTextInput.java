package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.KeyStroke;

import net.richstudios.hammerandsickle.audio.Sound;
import net.richstudios.hammerandsickle.graphics.GameFont;
import net.richstudios.hammerandsickle.graphics.Textures;
import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.utilites.InputHandler;
import net.richstudios.hammerandsickle.utilites.StringUtils;

public class HudTextInput extends HudComponent {

	public static final int PEICE_HEIGHT = 12, PEICE_WIDTH = 4;

	private String text;
	private boolean selected;

	protected int aWidth, aHeight;
	protected BufferedImage[] sprites;
	
	private final String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ";

	public HudTextInput(int x, int y, int width, String text, int size) {
		super(x, y, width * PEICE_WIDTH, PEICE_HEIGHT, size);
		this.aWidth = width;
		this.text = text;
		this.selected = false;
		this.sprites = Textures.getSpriteSheet("textInput")[0];
	}

	public void update(int ox, int oy) {
	}

	protected void draw(Graphics2D g, int ox, int oy) {
		for (int x = 0; x < aWidth; x++) {
			int dx = (this.x + ox) + (PEICE_WIDTH * x) * size;
			int dy = (this.y + oy);
			if (x == 0)
				g.drawImage(sprites[0], dx, dy, PEICE_WIDTH * size, PEICE_HEIGHT * size, null);
			else if (x > 0 && x < aWidth - 1)
				g.drawImage(sprites[1], dx, dy, PEICE_WIDTH * size, PEICE_HEIGHT * size, null);
			else if (x == aWidth - 1)
				g.drawImage(sprites[2], dx, dy, PEICE_WIDTH * size, PEICE_HEIGHT * size, null);
		}

		int tx = x + ox + 2 * size;
		int ty = y + oy + 3 * size;
		GameFont.render(text, g, tx, ty, size);
		if (selected && (System.currentTimeMillis() % 1000 < 500)) {
			int cx = (GameFont.getStringWidth(text, size) + tx) * size;
			g.setColor(Color.YELLOW);
			g.drawLine(cx, ty + GameFont.HEIGHT * size - 1, cx, ty * size);
		}
	}

	private boolean isMouseInside(InputHandler input, int ox, int oy) {
		return input.isMouseInside((x + ox) * References.SCALE, (y + oy) * References.SCALE, width * References.SCALE, height * References.SCALE);
	}

	private boolean[] keyChecked = new boolean[InputHandler.NUM_KEYS];

	public void checkInteraction(InputHandler input, int ox, int oy) {
		if (input.isMousePressed(InputHandler.MOUSEBUTTONL) && isMouseInside(input, ox, oy)) {
			selected = true;
		} else if (input.isMousePressed(InputHandler.MOUSEBUTTONL) && !isMouseInside(input, ox, oy)) {
			selected = false;
		}
		if (selected) {
			for (int i = 0; i < InputHandler.NUM_KEYS; i++) {
				if(input.isKeyPressed(i) && !keyChecked[i]) {
					char characterTyped = (char) i;
					if(i == KeyEvent.VK_BACK_SPACE && text.length() != 0) {
						text = text.substring( 0, text.length() - 1);
					} else if(allowedCharacters.contains(("" + characterTyped).toUpperCase())) {
						text = text.concat("" + characterTyped).toUpperCase();
					}
					keyChecked[i] = true;
				} else if(!input.isKeyPressed(i) && keyChecked[i]) {
					keyChecked[i] = false;
				}
			}
		}
	}
}
