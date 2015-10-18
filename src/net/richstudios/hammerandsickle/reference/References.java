package net.richstudios.hammerandsickle.reference;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.URL;

public class References {

	// GAME
	public static final String NAME = "Hammer And Sickle";
	public static final String VERSION = "0.13";
	public static final int FPS = 60;

	// GRAPHICS
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int SCALE = 1;
	public static final String FONT_NAME = "micra";
	static {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			URL url = References.class.getResource("/" + FONT_NAME + ".ttf");
			Font tf = Font.createFont(Font.TRUETYPE_FONT, new File(url.toURI()));
			ge.registerFont(tf);
			FONT = tf.deriveFont(12f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Font FONT;

	// MAP
	public static final int TILE_SIZE = 16;
	public static final int CHUNK_SIZE = 16;
	public static final int CHUNKLOADING_DISTANCE = 5;

	// NOISE
	public static final int NUM_OF_SWAPS = 400;
	public static short[] P_SUPPLY = new short[256];
	static {
		for (int i = 0; i < 256; i++) {
			P_SUPPLY[i] = (short) i;
		}
	}
	public static final double F2 = 0.5 * (Math.sqrt(3.0) - 1.0);
	public static final double G2 = (3.0 - Math.sqrt(3.0)) / 6.0;

	// DIFFICULTY
	public enum Difficulty {
		EASY("Little Baby Man"), NORMAL("Manly Man"), MEDIUM("Bear Fight"), HARD("Eating The Bear"), DEBUG("Test Mode");
		
		private String name;
		
		private Difficulty(String name) {
			this.name = name;
		}
		
		public String toString() {
			return name;
		}
	}
	
	public static final int STARTING_MONEY = 500;
	public static final int STARTING_WOOD = 1000;
	public static final int STARTING_FOOD = 50;
	public static final int STARTING_POP = 10;
}
