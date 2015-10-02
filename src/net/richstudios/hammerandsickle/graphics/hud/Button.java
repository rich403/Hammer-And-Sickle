package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.richstudios.hammerandsickle.audio.Sound;
import net.richstudios.hammerandsickle.graphics.Textures;
import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.utilites.InputHandler;
import net.richstudios.hammerandsickle.utilites.StringUtils;

public class Button extends Box {

	private String text;
	private Font font;
	private Color color;

	private static final int NORMAL = 0, HOVERED = 1, CLICKED = 2, NO_OF_STATUSES = 3;
	private int status;

	private BufferedImage[][][] buttonSprites;

	public Button(int x, int y, int width, int height, String text, int size) {
		super(x, y, width, height, size);
		this.text = text;
		this.font = new Font("Times New Roman", Font.PLAIN, 12);
		this.color = Color.YELLOW;
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
		int tx = (ox + x) + (width / 2) - (StringUtils.getStringWidth(g.getFont(), text) / 2);
		int ty = (oy + y) + (StringUtils.getStringHeight(g.getFont(), text) - 1);
		g.setFont(font);
		g.setColor(color);
		if(status == CLICKED) {
			ty += 2;
		} else if(status == HOVERED) {
			g.setColor(color.brighter().brighter());
		}
		g.drawString(text, tx, ty);
	}

	private boolean isMouseInside(InputHandler input, int ox, int oy) {
		return input.isMouseInside((x + ox) * References.SCALE, (y + oy) * References.SCALE, width * References.SCALE, height * References.SCALE);
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

	public void setColor(Color color) {
		if (!this.color.equals(color)) {
			this.color = color;
		}
	}

	public void setFont(Font font) {
		if (!this.font.equals(font)) {
			this.font = font;
		}
	}

}
