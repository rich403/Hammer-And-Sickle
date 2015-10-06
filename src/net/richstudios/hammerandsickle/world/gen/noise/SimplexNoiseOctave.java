package net.richstudios.hammerandsickle.world.gen.noise;

import java.awt.Point;
import java.util.Random;

import net.richstudios.hammerandsickle.reference.References;

public class SimplexNoiseOctave {

	private Point[] points = { new Point(1, 1), new Point(-1, 1), new Point(1, -1), new Point(-1, -1) };

	private static short p[];

	private short perm[] = new short[512];
	private short permMod12[] = new short[512];

	public SimplexNoiseOctave(int seed) {
		p = References.P_SUPPLY.clone();
		Random random = new Random(seed);
		for (int i = 0; i < References.NUM_OF_SWAPS; i++) {
			int from = random.nextInt(p.length);
			int to = random.nextInt(p.length);

			short temp_p = p[from];
			p[from] = p[to];
			p[to] = temp_p;
		}
		for (int i = 0; i < perm.length; i++) {
			perm[i] = p[i & 255];
			permMod12[i] = (short) (perm[i] % 4);
		}
	}

	private int fastFloor(double x) {
		int xi = (int) x;
		return (x < xi) ? xi - 1 : xi;
	}

	private double dot(Point point, double x, double y) {
		return point.x * x + point.y * y;
	}

	public double noise(double xin, double yin) {
		double s = (xin + yin) * References.F2;
		int i = fastFloor(xin + s);
		int j = fastFloor(yin + s);
		double t = (i + j) * References.G2;
		double X0 = i - t;
		double Y0 = j - t;
		double x0 = xin - X0;
		double y0 = yin - Y0;

		int i1, j1;
		if (x0 > y0) {
			i1 = 1;
			j1 = 0;
		} else {
			i1 = 0;
			j1 = 1;
		}

		double x1 = x0 - i1 + References.G2;
		double y1 = y0 - j1 + References.G2;
		double x2 = x0 - 1.0 + 2.0 * References.G2;
		double y2 = y0 - 1.0 + 2.0 * References.G2;

		int ii = i & 255;
		int jj = j & 255;

		int gi0 = permMod12[ii + perm[jj]];
		int gi1 = permMod12[ii + i1 + perm[jj + j1]];
		int gi2 = permMod12[ii + 1 + perm[jj + 1]];
		
		double n0, n1, n2;

		double t0 = 0.5 - x0 * x0 - y0 * y0;

		if (t0 < 0)
			n0 = 0;
		else {
			t0 *= t0;
			n0 = t0 * t0 * dot(points[gi0], x0, y0);
		}

		double t1 = 0.5 - x1 * x1 - y1 * y1;

		if (t1 < 0)
			n1 = 0;
		else {
			t1 *= t1;
			n1 = t1 * t1 * dot(points[gi1], x1, y1);
		}
		
		double t2 = 0.5 - x2 * x2 - y2 * y2;

		if (t2 < 0)
			n2 = 0;
		else {
			t2 *= t2;
			n2 = t2 * t2 * dot(points[gi2], x2, y2);
		}
		
		return 70 * (n0 + n1 + n2);

	}

}
