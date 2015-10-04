package net.richstudios.hammerandsickle.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Material {
	
	public static final Material GRASS = new Material("grassland", 0, new Integer[] {0, 1, 2, 3});
	public static final Material DIRT = new Material("grassland", 1, new Integer[] {0, 1, 2});
	public static final Material WATER = new Material("grassland", 2, new Integer[] {0, 1, 2});
	
	private List<Integer> resourceIds;
	private String sheet;
	private int sheetId;
	
	private Material(String sheet, int sheetId, Integer[] resourceIds) {
		this.sheet = sheet;
		this.sheetId = sheetId;
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

}
