package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.richstudios.hammerandsickle.audio.Sound;
import net.richstudios.hammerandsickle.graphics.GameFont;
import net.richstudios.hammerandsickle.graphics.Textures;
import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.utilites.InputHandler;

public class HudMainMenuButton extends HudComponent {

	public static final int DEFAULT_WIDTH = 64, DEFAULT_HEIGHT = 13;

	private static final int NORMAL = 0, HOVERED = 1, CLICKED = 2;
	private int status;
	private BufferedImage[] sprites;
	private static final String[] btnText = new String[] {
		"NEW GAME",
		"LOAD GAME",
		"HELP",
		"EXIT"
	};
	private final String text;

	public HudMainMenuButton(int x, int y, int id) {
		this(x, y, 1, id);
	}

	public HudMainMenuButton(int x, int y, int size, int id) {
		super(x, y, DEFAULT_WIDTH * size, DEFAULT_HEIGHT * size, size);
		this.status = NORMAL;
		this.sprites = Textures.getSpriteSheet("mainMenuButton")[0];
		this.text = btnText[id];
	}

	public void update(int ox, int oy) {
	}

	protected void draw(Graphics2D g, int ox, int oy) {
		g.drawImage(sprites[status], x + ox, y + oy, width, height, null);
		int tx = x + ox + width / 2 - GameFont.getStringWidth(text, size) / 2;
		int ty = y + oy + height / 2 - (GameFont.HEIGHT * size) / 2 - (1 * size);
		if(status == CLICKED) {
			ty += + 2 * size;
		}
		GameFont.render(text, g, tx, ty, size);
	}

	private boolean isMouseInside(InputHandler input, int ox, int oy) {
		return input.isMouseInside((x + ox) * References.SCALE, (y + oy)
				* References.SCALE, width * References.SCALE, height
				* References.SCALE);
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
	}

}
