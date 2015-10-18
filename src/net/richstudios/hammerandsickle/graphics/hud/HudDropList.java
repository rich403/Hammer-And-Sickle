package net.richstudios.hammerandsickle.graphics.hud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.richstudios.hammerandsickle.graphics.GameFont;
import net.richstudios.hammerandsickle.graphics.Textures;
import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.utilites.InputHandler;

public class HudDropList extends HudComponent {

	public static final int PEICE_HEIGHT = 12, PEICE_WIDTH = 4, LIST_PEICE_SIZE = 4;

	private boolean selected;
	private int selectedIndex = -1;

	protected int aWidth;
	protected Object[] list;
	protected BufferedImage[][] sprites, listSprites;
	protected BufferedImage[] boxSprites;
	protected int listHeight;
	protected BufferedImage dropArrow;

	public HudDropList(int x, int y, int width, int size) {
		super(x, y, width * PEICE_WIDTH, PEICE_HEIGHT, size);
		this.aWidth = width;
		this.selected = false;
		this.sprites = Textures.getSpriteSheet("dropList");
		this.dropArrow = Textures.getTexture("dropListArrow");
		this.boxSprites = new BufferedImage[sprites[0].length];
		this.listSprites = new BufferedImage[3][3];
		this.list = null;
		this.listHeight = 0;

		for (int col = 0; col < 3; col++) {
			BufferedImage b1 = sprites[0][col], b2 = sprites[1][col], b3 = sprites[2][col];
			BufferedImage result = new BufferedImage(PEICE_WIDTH, PEICE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			Graphics g = result.getGraphics();
			g.drawImage(b1, 0, 0, null);
			g.drawImage(b2, 0, b1.getHeight(), null);
			g.drawImage(b3, 0, b1.getHeight() + b2.getHeight(), null);
			g.dispose();
			boxSprites[col] = result;
		}

		for (int row = 3; row < sprites.length; row++) {
			for (int col = 0; col < sprites[col].length; col++) {
				listSprites[col][row - 3] = sprites[row][col];
			}
		}
	}

	public void setList(Object[] list) {
		this.list = list;
		if (this.list == null) {
			this.selectedIndex = -1;
			this.listHeight = 2;
		} else {
			this.selectedIndex = 0;
			int height = 2;
			for (int i = 0; i < list.length; i++) {
				height += GameFont.HEIGHT + 2;
			}
			height /= LIST_PEICE_SIZE;
			this.listHeight = height;
		}
	}

	public void setSelectedIndex(int i) {
		if (i < 0 || i > list.length)
			return;
		selectedIndex = i;
	}

	public void update(int ox, int oy) {
	}

	private boolean isMouseInside(InputHandler input, int ox, int oy) {
		int objx = (x + ox) * References.SCALE;
		int objy = (y + oy) * References.SCALE;
		int objw = width * size * References.SCALE;
		int objh = height * size * References.SCALE;
		int lh = (listHeight * LIST_PEICE_SIZE) * size * References.SCALE;
		if (!selected)
			return input.isMouseInside(objx, objy, objw, objh);
		else
			return input.isMouseInside(objx, objy, objw, objh + lh);
	}

	private int hoverIndex = -1;

	protected void draw(Graphics2D g, int ox, int oy) {
		for (int x = 0; x < aWidth; x++) {
			int dx = (this.x + ox) + (PEICE_WIDTH * x) * size;
			int dy = (this.y + oy);
			if (x == 0)
				g.drawImage(boxSprites[0], dx, dy, PEICE_WIDTH * size, PEICE_HEIGHT * size, null);
			else if (x > 0 && x < aWidth - 1)
				g.drawImage(boxSprites[1], dx, dy, PEICE_WIDTH * size, PEICE_HEIGHT * size, null);
			else if (x == aWidth - 1)
				g.drawImage(boxSprites[2], dx, dy, PEICE_WIDTH * size, PEICE_HEIGHT * size, null);
			if (selected) {

				for (int y = 0; y < listHeight; y++) {
					dy = (this.y + oy) + PEICE_HEIGHT * size + LIST_PEICE_SIZE * y * size;
					if (x == 0 && y == 0)
						g.drawImage(listSprites[0][0], dx, dy, LIST_PEICE_SIZE * size, LIST_PEICE_SIZE * size, null);
					else if (x == 0 && y == listHeight - 1)
						g.drawImage(listSprites[0][2], dx, dy, LIST_PEICE_SIZE * size, LIST_PEICE_SIZE * size, null);
					else if (x == aWidth - 1 && y == 0)
						g.drawImage(listSprites[2][0], dx, dy, LIST_PEICE_SIZE * size, LIST_PEICE_SIZE * size, null);
					else if (x == 0)
						g.drawImage(listSprites[0][1], dx, dy, LIST_PEICE_SIZE * size, LIST_PEICE_SIZE * size, null);
					else if (y == 0)
						g.drawImage(listSprites[1][0], dx, dy, LIST_PEICE_SIZE * size, LIST_PEICE_SIZE * size, null);
					else if (x == aWidth - 1 && y == listHeight - 1)
						g.drawImage(listSprites[2][2], dx, dy, LIST_PEICE_SIZE * size, LIST_PEICE_SIZE * size, null);
					else if (x == aWidth - 1)
						g.drawImage(listSprites[2][1], dx, dy, LIST_PEICE_SIZE * size, LIST_PEICE_SIZE * size, null);
					else if (y == listHeight - 1)
						g.drawImage(listSprites[1][2], dx, dy, LIST_PEICE_SIZE * size, LIST_PEICE_SIZE * size, null);
					else
						g.drawImage(listSprites[1][1], dx, dy, LIST_PEICE_SIZE * size, LIST_PEICE_SIZE * size, null);
				}
			}
		}
		int tx = x + ox + 2 * size;
		int ty = y + oy + 3 * size;
		GameFont.render(list[selectedIndex].toString(), g, tx, ty, size);
		if (selected) {
			for (int i = 0; i < getAllUnselected().length; i++) {
				int lty = y + oy + height * size + (GameFont.HEIGHT * size) * i + (2 * i + 2) * size;
				GameFont.render(getAllUnselected()[i].toString(), g, tx, lty, size);
			}
			System.out.println(hoverIndex);
			if (hoverIndex != -1) {
				int lw = ((aWidth * 4 - 4) * size);
				int lh = (GameFont.HEIGHT * size);
				int lx = (x + ox + 2 * size);
				int ly = (y + oy + lh * hoverIndex + (2 * hoverIndex) * size) + 5 * size;
				g.setColor(new Color(255, 255, 255, 100));
				g.fillRect(lx, ly, lw, lh);
			}
		}

		g.drawImage(dropArrow, (this.x + ox) + width * size - 5 * size, (this.y + oy), PEICE_WIDTH * size, PEICE_HEIGHT * size, null);
	}

	private Object[] getAllUnselected() {
		ArrayList<Object> tl = new ArrayList<Object>();
		for (int i = 0; i < list.length; i++) {
			if (i != selectedIndex)
				tl.add(list[i]);
		}
		return tl.toArray();
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void checkInteraction(InputHandler input, int ox, int oy) {
		if (input.isMousePressed(InputHandler.MOUSEBUTTONL) && isMouseInside(input, ox, oy)) {
			selected = true;

		} else if (input.isMousePressed(InputHandler.MOUSEBUTTONL) && !isMouseInside(input, ox, oy)) {
			selected = false;
		}
		if (selected) {
			int lx = (x + ox) * References.SCALE;
			int ly = (y + oy) * References.SCALE;
			int lw = width * size * References.SCALE;
			int lh = (listHeight * LIST_PEICE_SIZE) * size * References.SCALE;
			if (input.isMouseInside(ox, oy, ox, oy)) {
				int j = 0;
				for (int i = 0; i < list.length; i++) {
					if (i != selectedIndex)
						j++;
					int liw = ((aWidth * 4 - 4) * size);
					int lih = (GameFont.HEIGHT * size);
					int lix = (x + ox + 2 * size);
					int liy = (y + oy + lih * j + (2 * j) * size) + 5 * size;
					if (input.isMouseInside(lix * References.SCALE, liy * References.SCALE, liw * References.SCALE, lih * References.SCALE)) {
						if (input.isMousePressed(InputHandler.MOUSEBUTTONL)) {
							selectedIndex = i;
							selected = false;
						} else {
							hoverIndex = j;
						}
					} else {
						hoverIndex = -1;
					}
				}
			}
		}
	}

}
