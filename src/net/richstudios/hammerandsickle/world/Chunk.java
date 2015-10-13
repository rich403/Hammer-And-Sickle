package net.richstudios.hammerandsickle.world;

import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.world.gen.noise.SimplexNoise;

public class Chunk {
	
	Tile[][] tiles;
	
	int chunkX;
	int chunkY;
	
	public Chunk(int chunkX, int chunkY) {
		this.chunkX = chunkX;
		this.chunkY = chunkY;
	}
	
	public void populate(int seed) {
		tiles = new Tile[References.CHUNK_SIZE][References.CHUNK_SIZE];
		
		SimplexNoise simplexNoise = new SimplexNoise(7, 0.1, seed);
		
		int resolution = References.CHUNK_SIZE;
		
		double xStart = chunkX * resolution;
		double xEnd = xStart + resolution;
		double yStart = chunkY * resolution;
		double yEnd = yStart + resolution;
		
		for(int i = 0; i < resolution; i++) {
			for(int j = 0; j < resolution; j++) {
				int x = (int)(xStart + (i * (xEnd - xStart) / resolution));
				int y = (int)(yStart + (j * (yEnd - yStart) / resolution));
				
				double noise = (1 + simplexNoise.getNoise(x, y)) / 2;
				Material mat;
				if(noise < 0.495f)
					mat = Material.WATER;
				else if(noise < 0.5f)
					mat = Material.DIRT;
				else
					mat = Material.GRASS;
				tiles[i][j] = new Tile(mat);
			}
		}
	}
	
	public boolean equals(Object object) {
		if(!(object instanceof Chunk)) return false;
		
		Chunk chunk = (Chunk) object;
		return chunkX == chunk.chunkX && chunkY == chunk.chunkY;
	}

}
