package net.richstudios.hammerandsickle.world;

import net.richstudios.hammerandsickle.reference.References;

public class Chunk {
	
	Tile[][] tiles;
	
	int chunkX;
	int chunkY;
	
	public Chunk(int chunkX, int chunkY) {
		this.chunkX = chunkX;
		this.chunkY = chunkY;
	}
	
	public void populate() {
		tiles = new Tile[References.CHUNK_SIZE][References.CHUNK_SIZE];
		
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[x].length; y++) {
				tiles[x][y] = new Tile(Material.GRASS);
			}
		}
	}
	
	public boolean equals(Object object) {
		if(!(object instanceof Chunk)) return false;
		
		Chunk chunk = (Chunk) object;
		return chunkX == chunk.chunkX && chunkY == chunk.chunkY;
	}

}
