package net.richstudios.hammerandsickle.graphics;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import net.richstudios.hammerandsickle.Game;
import net.richstudios.hammerandsickle.reference.Path;
import net.richstudios.hammerandsickle.utilites.Logger;

public class Textures {

	// ICONS
	public static final BufferedImage icon16x = loadImageRaw(Path.ICONS + "/icon16x.png");
	public static final BufferedImage icon32x = loadImageRaw(Path.ICONS + "/icon32x.png");
	public static final BufferedImage icon64x = loadImageRaw(Path.ICONS + "/icon64x.png");

	private static HashMap<String, BufferedImage> textures;
	private static HashMap<String, BufferedImage[][]> spriteSheets;

	public static void init() {
		textures = new HashMap<String, BufferedImage>();
		spriteSheets = new HashMap<String, BufferedImage[][]>();
	}

	public static BufferedImage loadImageRaw(String path) {
		try {
			Logger.info("Loading image: " + path);
			return ImageIO.read(Game.class.getResourceAsStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void loadImage(String path, String name) {
		BufferedImage i = loadImageRaw(path);
		textures.put(name, i);
		texturesLoaded++;
	}

	private static void loadSpritesheet(String path, String name, Dimension d) {
		BufferedImage i = loadImageRaw(path);
		int width = (int) d.getWidth();
		int height = (int) d.getHeight();
		int numtilesX = i.getWidth() / width;
		int numtilesY = i.getHeight() / height;
		BufferedImage[][] out = new BufferedImage[numtilesY][numtilesX];
		for (int y = 0; y < numtilesY; y++) {
			for (int x = 0; x < numtilesX; x++) {
				out[y][x] = i.getSubimage(x * width, y * height, width, height);
			}
		}
		spriteSheets.put(name, out);
		texturesLoaded++;
	}

	private enum TextureType {
		BACKGROUND, GUI, SPRITE, TILESET
	}

	private static int amountOfTextures = 0;
	private static int texturesLoaded = 0;

	public static void loadTextures() {
		loadTexturesFromFile("/loadTextures.txt");
	}

	private static void loadTexturesFromFile(String f) {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<TextureType> types = new ArrayList<TextureType>();
		ArrayList<Boolean> isSpritesheet = new ArrayList<Boolean>();
		ArrayList<Dimension> spriteSizes = new ArrayList<Dimension>();
		try {
			Scanner scanner = new Scanner(Textures.class.getResourceAsStream(f));
			TextureType type = null;
			Boolean spritesheetFlag = false;
			Dimension spriteSize = null;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] lineParts = line.split(",");
				if (line.startsWith("-")) {
					if (line.equalsIgnoreCase("-background-")) {
						type = TextureType.BACKGROUND;
						continue;
					} else if (line.equalsIgnoreCase("-gui-")) {
						type = TextureType.GUI;
						continue;
					} else if (line.equalsIgnoreCase("-sprite-")) {
						type = TextureType.SPRITE;
						continue;
					} else if (line.equalsIgnoreCase("-tileset-")) {
						type = TextureType.TILESET;
						continue;
					}
				} else if (lineParts.length == 3) {
					spritesheetFlag = true;
					int sWidth = Integer.parseInt(lineParts[1]);
					int sHeight = Integer.parseInt(lineParts[2]);
					spriteSize = new Dimension(sWidth, sHeight);
				} else {
					spritesheetFlag = false;
					spriteSize = new Dimension(0, 0);
				}
				if (spritesheetFlag)
					names.add(lineParts[0]);
				else
					names.add(line);
				types.add(type);
				isSpritesheet.add(spritesheetFlag);
				spriteSizes.add(spriteSize);
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		amountOfTextures = names.size();
		for (int i = 0; i < amountOfTextures; i++) {
			String name = names.get(i);
			Dimension spriteSize = spriteSizes.get(i);
			if (isSpritesheet.get(i)) {
				switch (types.get(i)) {
				case BACKGROUND:
					loadSpritesheet(Path.BACKGROUNDS + "/" + name + ".png", name, spriteSize);
					break;
				case GUI:
					loadSpritesheet(Path.GUI + "/" + name + ".png", name, spriteSize);
					break;
				case SPRITE:
					loadSpritesheet(Path.SPRITES + "/" + name + ".png", name, spriteSize);
					break;
				case TILESET:
					loadSpritesheet(Path.TILESETS + "/" + name + ".png", name, spriteSize);
					break;
				}
			} else {
				switch (types.get(i)) {
				case BACKGROUND:
					loadImage(Path.BACKGROUNDS + "/" + name + ".png", name);
					break;
				case GUI:
					loadImage(Path.GUI + "/" + name + ".png", name);
					break;
				case SPRITE:
					loadImage(Path.SPRITES + "/" + name + ".png", name);
					break;
				case TILESET:
					loadImage(Path.TILESETS + "/" + name + ".png", name);
					break;
				}
			}
		}
	}

	public static double getPercentageLoaded() {
		if(amountOfTextures == 0) return 1.0;
		if(texturesLoaded == 0) return 0.0;
		return (double) texturesLoaded / (double) amountOfTextures;
	}

	public static BufferedImage getTexture(String string) {
		return textures.get(string);
	}

	public static BufferedImage[][] getSpriteSheet(String string) {
		return spriteSheets.get(string);
	}

}
