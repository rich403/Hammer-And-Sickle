package net.richstudios.hammerandsickle.reference;

public class References {

	// SCREEN
	public static final String NAME = "Hammer And Sickle";
	public static final String VERSION = "0.13";
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int SCALE = 2;
	public static final int FPS = 60;

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

}
