package net.richstudios.hammerandsickle.world;

import java.awt.image.BufferedImage;

import net.richstudios.hammerandsickle.graphics.Textures;

public class Tile {
	
	public Material material;
	public BufferedImage texture;
	
	public Tile(Material material) {
		this.material = material;
		this.texture = Textures.getSpriteSheet(material.getSheet())[material.getSheetId()][material.getRandomResourceId()];
	}

}
