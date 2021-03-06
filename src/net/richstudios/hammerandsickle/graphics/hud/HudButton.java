package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.richstudios.hammerandsickle.audio.Sound;
import net.richstudios.hammerandsickle.graphics.GameFont;
import net.richstudios.hammerandsickle.graphics.Textures;
import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.utilites.InputHandler;

public class HudButton extends HudBox {

	private String text;

	private static final int NORMAL = 0, HOVERED = 1, CLICKED = 2, NO_OF_STATUSES = 3;
	private int status;

	private BufferedImage[][][] buttonSprites;

	public HudButton(int x, int y, int width, int height, String text, int size) {
		super(x, y, width, height, size);
		this.text = text;
		BufferedImage[][] srcSprites = Textures.getSpriteSheet("button");
		buttonSprites = new BufferedImage[NO_OF_STATUSES][srcSprites.length / 3][srcSprites[0].length];
		for (int ti = 0; ti < NO_OF_STATUSES; ti++) {
			for (int ty = 0; ty < buttonSprites[ti].length; ty++) {
				for (int tx = 0; tx < buttonSprites[ti][ty].length; tx++) {
					buttonSprites[ti][ty][tx] = srcSprites[ty + (ti * 3)][tx];
				}
			}
		}
	}

	protected void draw(Graphics2D g, int ox, int oy) {
		super.draw(g, ox, oy, buttonSprites[status]);
		int tx = x + ox + width * size / 2 - GameFont.getStringWidth(text, size) / 2;
		int ty = y + oy + height * size / 2 - (GameFont.HEIGHT * size) / 2 - (1 * size);
		if(status == CLICKED) {
			ty += + 2 * size;
		}
		GameFont.render(text, g, tx, ty, size);
	}

	private boolean isMouseInside(InputHandler input, int ox, int oy) {
		return input.isMouseInside((x + ox) * References.SCALE, (y + oy) * References.SCALE, width * size * References.SCALE, height * size * References.SCALE);
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
	
	public void setText(String text) {
		if (!this.text.equals(text)) {
			this.text = text;
		}
	}

}
