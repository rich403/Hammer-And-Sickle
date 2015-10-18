package net.richstudios.hammerandsickle.gamestate;

import net.richstudios.hammerandsickle.reference.References;
import net.richstudios.hammerandsickle.reference.References.Difficulty;

public class GameInfo {
	
	private int money, wood, food, population;
	
	public void setValues(Difficulty diff) {
		float multiplier = 1f;
		switch(diff) {
		case EASY:
			multiplier = 1.2f;
			break;
		case NORMAL:
			multiplier = 1f;
			break;
		case MEDIUM:
			multiplier = .8f;
			break;
		case HARD:
			multiplier = .6f;
			break;
		case DEBUG:
			multiplier = 10f;
			break;
		}
		money = (int) (References.STARTING_MONEY * multiplier);
		wood = (int) (References.STARTING_WOOD * multiplier);
		food = (int) (References.STARTING_FOOD * multiplier);
		population = (int) (References.STARTING_POP * multiplier);
	}
	
	// MONEY

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	public void addMoney(int money) {
		if(money > 0) {
			this.money += money;
		}
	}
	
	public void removeMoney(int cost) {
		if(hasEnoughMoney(cost)) {
			this.money -= cost;
		} else {
			this.money = 0;
		}
	}
	
	public boolean hasEnoughMoney(int cost) {
		return cost <= money;
	}
	
	// WOOD

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		if(wood < 0) {
			wood = 0;
		}
		this.wood = wood;
	}
	
	public void addWood(int wood) {
		if(wood < 0) {
			wood = 0;
		}
		this.wood += wood;
	}
	
	public void removeWood(int cost) {
		if(hasEnoughWood(cost)) {
			this.wood -= cost;
		} else {
			this.wood = 0;
		}
	}
	
	public boolean hasEnoughWood(int cost) {
		return cost <= wood;
	}
	
	// FOOD

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}
	
	public void addFood(int food) {
		if(food < 0) {
			food = 0;
		}
		this.food += food;
	}
	
	public void removeFood(int cost) {
		if(hasEnoughFood(cost)) {
			this.food -= cost;
		} else {
			this.food = 0;
		}
	}
	
	public boolean hasEnoughFood(int cost) {
		return cost <= food;
	}
	
	// POPULATION

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

}
