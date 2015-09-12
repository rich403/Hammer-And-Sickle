package net.richstudios.hammerandsickle.utilites;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class StringUtils {

	public static int getStringHeight(Font f, String s) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		return (int) (f.getStringBounds(s, frc).getHeight());
	}

	public static int getStringWidth(Font f, String s) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		return (int) (f.getStringBounds(s, frc).getWidth());
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

}
