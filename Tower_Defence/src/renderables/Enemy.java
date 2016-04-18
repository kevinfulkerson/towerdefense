package renderables;

import screens.GridScreen;
import logging.Constants;
import gamelogic.AttackType;
import gamelogic.DefenseType;
import gamelogic.Tile;
import Tower.Defence.TowerDefence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy extends Entity{
	
	public Enemy(int unitType, int waveMultiplier) {
		type = 1;
		if (unitType == 1){
			sprite = new Sprite(TowerDefence.manager.get("enemy1.png", Texture.class));
			attackType = AttackType.NORMAL;
			defenseType = DefenseType.HEAVY;
			isRotatable = true;
			health = 90 + (waveMultiplier*10);
			attackDamage = 0;
			attackSpeed = 0;
			defenseValue = 0;
			movementSpeed = 0.2f;
			worth = 7;
			score = 10;
		} else if (unitType == 2){
			sprite = new Sprite(TowerDefence.manager.get("enemy2.png", Texture.class));
			attackType = AttackType.NORMAL;
			defenseType = DefenseType.HEAVY;
			isRotatable = true;
			health = (waveMultiplier*100);
			attackDamage = 0;
			attackSpeed = 0;
			defenseValue = 0;
			movementSpeed = 0.1f;
			worth = 21;
			score = 50;
		}
//		System.out.println(health);
		Gdx.app.log(Constants.LOG, "new spawned units health: " + health);
		Tile tile = GridScreen.road.getTile(0);
		sprite.setBounds(tile.sprite.getX(), tile.sprite.getY() - GridScreen.road.getTile(GridScreen.road.size()-1).sprite.getHeight(), tile.sprite.getWidth(), tile.sprite.getHeight());
		sprite.setOrigin(tile.sprite.getWidth()/2, tile.sprite.getHeight()/2);
		isMovable = true;
		isMovementSpeedModifiable = true;
		isArmed = false;
		isTargetable = true;
		isVisible = true;
		isDefenseModifiable = false;
		isAttackModifiable = false;
		isPrioritizable = true;
		priority = 1;
	}
	
}
