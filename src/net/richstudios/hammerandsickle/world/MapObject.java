package net.richstudios.hammerandsickle.world;

import java.awt.Graphics2D;

public abstract class MapObject {
	
	private int x, y;
	
	public MapObject(int x, int y) {
		
	}
	
	public abstract void draw(Graphics2D g);
	
	public abstract void update();
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
