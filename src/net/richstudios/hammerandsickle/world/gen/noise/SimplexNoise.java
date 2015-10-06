package net.richstudios.hammerandsickle.world.gen.noise;

import java.util.Random;

public class SimplexNoise {
	
	private SimplexNoiseOctave[] octaves;
	double[] frequencies;
	double[] amplitudes;
	
	public SimplexNoise(int numOfOctaves, double persistence, int seed) {
		Random random = new Random(seed);
		
		octaves = new SimplexNoiseOctave[numOfOctaves];
		frequencies = new double[numOfOctaves];
		amplitudes = new double[numOfOctaves];
		
		for(int i = 0; i < numOfOctaves; i++) {
			octaves[i] = new SimplexNoiseOctave(random.nextInt());
			
			frequencies[i] = Math.pow(2, i);
			amplitudes[i] = Math.pow(persistence, numOfOctaves - i);
		}
	}

}
