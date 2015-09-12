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
	private boolean isImageUpdated = false;
	private int tox = 0, toy = 0;

	public TextBox(int x, int y, int width, int height, int size) {
		super(x, y, width, height, size);
		this.sprites = Textures.getSpriteSheet("textBox");
		this.font = new Font("Times New Roman", Font.PLAIN, 12);
		this.color = Color.YELLOW;
		if (width > 4 && height > 4)
			textImage = new BufferedImage(width - 4, height - 4, BufferedImage.TYPE_INT_ARGB);
	}

	public void update(int ox, int oy) {
		if (!isImageUpdated && !text.isEmpty() && textImage != null) {
			String[] textSplit = text.split("\n");
			Graphics g = textImage.getGraphics();
			g.setFont(font);
			g.setColor(Color.YELLOW);
			for(int i = 0; i < textSplit.length; i++) {
				g.drawString(textSplit[i], tox, toy + StringUtils.getStringHeight(font, textSplit[i]) * i);
			}
			g.dispose();
			isImageUpdated = true;
		}
	}

	public void setText(String text) {
		this.isImageUpdated = this.text.equals(text);
		this.text = text;
	}
	
	public void setColor(Color color) {
		this.isImageUpdated = this.color.equals(color);
		this.color = color;
	}
	
	public void setFont(Font font) {
		this.isImageUpdated = this.font.equals(font);
		this.font = font;
	}

	public void draw(Graphics2D g, int ox, int oy) {
		super.draw(g, ox, oy);
		if(isImageUpdated && textImage != null && !text.isEmpty()) {
			g.drawImage(textImage, x + ox + 2, y + oy + 2, textImage.getWidth() * size, textImage.getHeight() * size, null);
		}
	}

}
