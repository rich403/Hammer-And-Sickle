package net.richstudios.hammerandsickle.world;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Material {
	
	public static final Material WATER = new Material("grassland", 2, new Integer[] {0, 1, 2}, 0.1f);
	public static final Material DIRT = new Material("grassland", 1, new Integer[] {0, 1, 2}, 0.3f);
	public static final Material GRASS = new Material("grassland", 0, new Integer[] {0, 1, 2, 3}, 1f);
	
	private List<Integer> resourceIds;
	private String sheet;
	private int sheetId;
	private float weight;
	
	private Material(String sheet, int sheetId, Integer[] resourceIds, float weight) {
		this.sheet = sheet;
		this.sheetId = sheetId;
		this.weight = weight;
		this.resourceIds = Arrays.asList(resourceIds);
	}
	
	public int getResourceId(int i) {
		return resourceIds.get(i);
	}
	
	Random random = new Random();
	public int getRandomResourceId() {
		return resourceIds.get(random.nextInt(resourceIds.size()));
	}

	public String getSheet() {
		return sheet;
	}
	
	public int getSheetId() {
		return sheetId;
	}
	
	public float getWeight() {
		return weight;
	}

}
