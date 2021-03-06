package net.richstudios.hammerandsickle.utilites;

import net.richstudios.hammerandsickle.reference.Settings;

public class Logger {

	public enum Level {
		INFO(0), DEBUG(1), ERROR(2), FATAL(3);

		int level;

		private Level(int l) {
			level = l;
		}

		public int getLevel() {
			return level;
		}
	}

	private static void log(Level l, String s) {
		if (l.level >= Settings.log_level)
			System.out.println("[" + l.name() + "]" + s);
	}

	public static void info(String s) {
		log(Level.INFO, s);
	}

	public static void debug(String s) {
		log(Level.DEBUG, s);
	}

	public static void error(String s) {
		log(Level.ERROR, s);
	}

	public static void fatal(String s) {
		log(Level.FATAL, s);
	}

}
