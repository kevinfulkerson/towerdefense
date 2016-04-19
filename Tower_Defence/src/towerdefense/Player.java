package towerdefense;

import logging.Constants;

import com.badlogic.gdx.Gdx;

public class Player {
	
	private int gold, score, unitsKilled, health;
	
	public Player(int startingGold){
		gold = startingGold;
		score = 0;
		unitsKilled = 0;
		health = 5;
	}
	
	public boolean canAfford(int amount){
		return (gold >= amount);
	}
	
	//Used if selling tower?
	public void addGold(int amount){
		gold += amount;
	}
	
	public int getGold(){
		return gold;
	}
	
	public void addScore(int amount){
		score += amount;
	}
	
	public int getScore(){
		return score;
	}
	
	public boolean canAffordTower(int towerCost){
		return (gold >= towerCost) ? true : false;
	}
	
	public void buy(int cost){
		gold -= cost;
	}
	
	public void unitKilled(){
		unitsKilled++;
	}
	
	public int getUnitsKilled(){
		return unitsKilled;
	}
	
	public void playerLoseHealth(){
		health--;
//		System.out.println("Current health: " + health);
		Gdx.app.log(Constants.LOG, "Current player health: " + health);
	}
	
	public boolean playerDead(){
		return (health <= 0);
	}

	public int getLives() {
		return health;
	}
	
}
