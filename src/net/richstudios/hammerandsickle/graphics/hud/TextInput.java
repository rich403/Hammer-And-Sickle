package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import net.richstudios.hammerandsickle.audio.Sound;
import net.richstudios.hammerandsickle.graphics.Textures;
import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.utilites.InputHandler;
import net.richstudios.hammerandsickle.utilites.StringUtils;

public class TextInput extends HudComponent {

	public static final int PEICE_HEIGHT = 12, PEICE_WIDTH = 4;

	private String text;
	private Font font;
	private Color color;
	private boolean selected;

	protected int aWidth, aHeight;
	protected BufferedImage[] sprites;

	private final String accepted_characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890!@#$%^&*()_+\\|`~\"',.<>/?[]{}";

	public TextInput(int x, int y, int width, String text, int size) {
		super(x, y, width * PEICE_WIDTH, PEICE_HEIGHT, size);
		this.aWidth = width;
		this.text = text;
		this.font = new Font("Times New Roman", Font.PLAIN, 12);
		this.color = Color.YELLOW;
		this.selected = true;
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
		int tx = (this.x + ox) + 2;
		int ty = (this.y + oy) + StringUtils.getStringHeight(font, text) - 2;
		g.setFont(font);
		g.setColor(color);
		g.drawString(text, tx, ty);
		if (selected && (System.currentTimeMillis() % 1000 < 500)) {
			int cx = StringUtils.getStringWidth(font, text) + tx + 1;
			g.drawLine(cx, ty - 9, cx, ty - 1);
		}
	}

	private boolean isMouseInside(InputHandler input, int ox, int oy) {
		return input.isMouseInside((x + ox) * References.SCALE, (y + oy) * References.SCALE, width * References.SCALE, height * References.SCALE);
	}

	public void checkInteraction(InputHandler input, int ox, int oy) {
		if (input.isMousePressed(InputHandler.MOUSEBUTTONL) && isMouseInside(input, ox, oy)) {
			selected = true;
		} else if (input.isMousePressed(InputHandler.MOUSEBUTTONL) && !isMouseInside(input, ox, oy)) {
			selected = false;
		}
		if (selected) {
			for (int i = 0; i < InputHandler.NUM_KEYS; i++) {
				if (input.isPressed(i)) {
					String sym = "" + (char) i;
					if (i == KeyEvent.VK_BACK_SPACE || i == KeyEvent.VK_DELETE) {
						text.substring(0, text.length() - 1);
						continue;
					}
					if (accepted_characters.contains(KeyEvent.getKeyText(i))) {
						text.concat(sym);
					}
				}
			}
		}
	}
}
