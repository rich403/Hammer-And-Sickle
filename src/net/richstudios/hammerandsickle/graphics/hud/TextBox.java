package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.richstudios.hammerandsickle.graphics.Textures;
import net.richstudios.hammerandsickle.utilites.StringUtils;

public class TextBox extends Box {

	private String text = "";
	private Font font;
	private Color color;
	private BufferedImage textImage = null;
	private int tox = 0, toy = 0;

	public TextBox(int x, int y, int width, int height, int size) {
		super(x, y, width, height, size);
		this.sprites = Textures.getSpriteSheet("textBox");
		this.font = new Font("Times New Roman", Font.PLAIN, 12);
		this.color = Color.YELLOW;
		reloadImage();
	}

	public void update(int ox, int oy) {
	}

	public void reloadImage() {

		textImage = new BufferedImage(this.width - 4, this.height - 4, BufferedImage.TYPE_INT_ARGB);
		if (!text.isEmpty()) {
			String[] textSplit = text.split("\n");
			Graphics g = textImage.getGraphics();
			g.setFont(font);
			g.setColor(Color.YELLOW);
			for (int i = 0; i < textSplit.length; i++) {
				int dy = toy + StringUtils.getStringHeight(font, textSplit[i])
						+ StringUtils.getStringHeight(font, textSplit[i]) * i;
				g.drawString(textSplit[i], tox, dy);
			}
			g.dispose();
		}
	}

	public void setText(String text) {
		if (!this.text.equals(text)) {
			this.text = text;
			reloadImage();
		}
	}

	public void setColor(Color color) {
		if (!this.color.equals(color)) {
			this.color = color;
			reloadImage();
		}
	}

	public void setFont(Font font) {
		if (!this.font.equals(font)) {
			this.font = font;
			reloadImage();
		}
	}

	public void draw(Graphics2D g, int ox, int oy) {
		super.draw(g, ox, oy);
		if (textImage != null && !text.isEmpty()) {
			g.drawImage(textImage, x + ox + 4, y + oy + 4, textImage.getWidth()
					* size, textImage.getHeight() * size, null);
		}
	}

	public void setTextOffset(int x, int y) {
		if ((this.tox != x) && (this.toy != y)) {
			this.tox = x;
			this.toy = y;
			reloadImage();
		}
	}

}
