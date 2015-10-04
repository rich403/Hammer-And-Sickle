package net.richstudios.hammerandsickle.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GameFont {
	
	public static int WIDTH = 5, HEIGHT = 7;
	
	public static final String FONTSHEET_CHARS = 
  		      "ABCDEFGHIJKLMNOP"
			+ "QRSTUVWXYZ012345"
			+ "6789_`~!@#$%^&*("
			+ ")-=+|\\{}[]:;'\".,"
			+ "<>/?            "
			+ "АБВГДЕЁЖЗИЙКЛМНОП"
			+ "РСТУФХЦЧШЩЪЫЬЭЮЯ ";
	
	private static final String SPECIALLY_SPACED_CHARS = "`!^*()-=+|{}[]:;'\".,<>";
	
	private static BufferedImage[] fontSheet;
	
	public static void init() {
		BufferedImage[][] src = Textures.getSpriteSheet("font");
		fontSheet = new BufferedImage[src.length * src[0].length];
		for(int y = 0; y < src.length; y++) {
			for(int x = 0; x < src[y].length; x++) {
				int index = x + y * src[y].length;
				fontSheet[index] = src[y][x];
			}
		}
	}
	
	public static void render(String text, Graphics2D g, int x, int y, int size) {
		String txt = text.toUpperCase();
		int tx = x;
		int step = (WIDTH + 1) * size;
		for(int i = 0; i < txt.length(); i++) {
			char c = txt.charAt(i);
			if(Character.isWhitespace(c)) {
				tx += step / 2;
			} else if(FONTSHEET_CHARS.contains("" + c)) {
				int index = FONTSHEET_CHARS.indexOf(c);
				g.drawImage(fontSheet[index], tx, y, WIDTH * size, HEIGHT * size, null);
				if(SPECIALLY_SPACED_CHARS.contains("" + c)) tx += 4 * size;
				else tx += step;
			}
		}
	}
	
	public static void renderDown(String text, Graphics2D g, int x, int y, int size) {
		String txt = text.toUpperCase();
		int ty = y;
		int step = (HEIGHT + 1) * size;
		for(int i = 0; i < txt.length(); i++) {
			char c = txt.charAt(i);
			if(Character.isWhitespace(c)) {
				ty += step / 2;
			} else if(FONTSHEET_CHARS.contains("" + c)) {
				int index = FONTSHEET_CHARS.indexOf(c);
				g.drawImage(fontSheet[index], x, ty, WIDTH * size, HEIGHT * size, null);
				ty += step;
			}
		}
	}
	
	public static int getStringWidth(String text, int size) {
		String txt = text.toUpperCase();
		int sw = 0;
		int step = (WIDTH + 1) * size;
		for(int i = 0; i < txt.length(); i++) {
			char c = txt.charAt(i);
			if(Character.isWhitespace(c)) {
				sw += step / 2;
			} else if(FONTSHEET_CHARS.contains("" + c)) {
				if(SPECIALLY_SPACED_CHARS.contains("" + c)) sw += 4 * size;
				else sw += step;
			}
		}
		return sw;
	}
	
	public static int getDownStringHeight(String text, int size) {
		String txt = text.toUpperCase();
		int sw = 0;
		int step = (HEIGHT + 1) * size;
		for(int i = 0; i < txt.length(); i++) {
			char c = txt.charAt(i);
			if(Character.isWhitespace(c)) {
				sw += step / 2;
			} else if(FONTSHEET_CHARS.contains("" + c)) {
				sw += step;
			}
		}
		return sw;
	}
}
