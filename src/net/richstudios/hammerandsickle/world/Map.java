package net.richstudios.hammerandsickle.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.reference.Settings;

public class Map {

	List<Chunk> loadedChunks = new CopyOnWriteArrayList<Chunk>();

	List<MapObject> objects = new ArrayList<MapObject>();
	Camera camera;

	private int seed;

	public Map(Camera camera, int seed) {
		this.camera = camera;
		this.seed = seed;
		checkChunks(camera);
		for (MapObject object : objects) {
			checkChunks(object);
		}
	}

	public void addObject(MapObject object) {
		objects.add(object);
	}

	public void draw(Graphics2D g) {
		int camX = camera.getX() * References.TILE_SIZE + References.TILE_SIZE / 2 - References.WIDTH / 2;
		int camY = camera.getY() * References.TILE_SIZE + References.TILE_SIZE / 2 - References.HEIGHT / 2;
		for (Chunk chunk : loadedChunks) {
			int posX = chunk.chunkX * References.CHUNK_SIZE * References.TILE_SIZE - camX;
			int posY = chunk.chunkY * References.CHUNK_SIZE * References.TILE_SIZE - camY;
			for (int x = 0; x < chunk.tiles.length; x++) {
				for (int y = 0; y < chunk.tiles[x].length; y++) {
					g.drawImage(chunk.tiles[x][y].texture, x * References.TILE_SIZE + posX, y * References.TILE_SIZE + posY, References.TILE_SIZE, References.TILE_SIZE, null);
				}
			}
			if (Settings.debug) {
				g.setColor(Color.BLACK);
				g.drawLine(posX, 0, posX, References.HEIGHT);
				g.drawLine(0, posY, References.WIDTH, posY);
			}
		}
		for (MapObject object : objects) {
			object.draw(g);
		}
	}

	public void update() {
		checkChunks(camera);
		for (MapObject object : objects) {
			checkChunks(object);
		}
	}

	public void checkChunks(MapObject object) {
		int cam_chunk_x = camera.getX() / References.CHUNK_SIZE;
		int cam_chunk_y = camera.getY() / References.CHUNK_SIZE;

		unloadChunks(cam_chunk_x, cam_chunk_y);

		loadChunks(cam_chunk_x, cam_chunk_y);
	}

	private void unloadChunks(int cam_chunk_x, int cam_chunk_y) {
		for (Chunk chunk : loadedChunks) {
			if (chunk.chunkX > cam_chunk_x + (References.CHUNKLOADING_DISTANCE - 1) / 2 || chunk.chunkX < cam_chunk_x - (References.CHUNKLOADING_DISTANCE - 1) / 2 || chunk.chunkY > cam_chunk_y + (References.CHUNKLOADING_DISTANCE - 1) / 2 || chunk.chunkY < cam_chunk_y - (References.CHUNKLOADING_DISTANCE - 1) / 2) {
				loadedChunks.remove(chunk);
			}
		}
	}

	private void loadChunks(int cam_chunk_x, int cam_chunk_y) {
		for (int x = cam_chunk_x - (References.CHUNKLOADING_DISTANCE - 1) / 2; x <= cam_chunk_x + (References.CHUNKLOADING_DISTANCE - 1) / 2; x++) {
			for (int y = cam_chunk_y - (References.CHUNKLOADING_DISTANCE - 1) / 2; y <= cam_chunk_y + (References.CHUNKLOADING_DISTANCE - 1) / 2; y++) {
				Chunk chunk = new Chunk(x, y);
				if (!loadedChunks.contains(chunk)) {
					chunk.populate(seed);
					loadedChunks.add(chunk);
				}
			}
		}
	}

}
