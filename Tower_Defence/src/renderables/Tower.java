package renderables;

import screens.GridScreen;
import towerdefense.TowerDefense;
import gamelogic.AttackType;
import gamelogic.Tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Tower extends Entity{
	
	Vector2 projectileSpawnPosition;
	private float timer = 0;
	
	public Tower(Texture texture, Vector2 gridIndexLocation, int towerType){
		sprite = new Sprite(texture);
		type = 0;
		attackType = AttackType.INCENDIARY;
		if (towerType == 1){
			attackDamage = 25f;
			attackType = AttackType.INCENDIARY;
			attackSpeed = 0.8f;
			range = 500 * (GridScreen.screenHeight/540);
		} else if (towerType == 2){
			attackDamage = 3.35f;
			attackType = AttackType.PIERCING;
			attackSpeed = 0.1f;
			range = 250 * (GridScreen.screenHeight/540);
		}
		isRotatable = true;
		placeTower(gridIndexLocation);
	}
	
	private void placeTower(Vector2 gridLocation){
		Tile location = GridScreen.grid.get(gridLocation);
		if (!location.isOccupied){
			location.isOccupied = true;
			float X = location.sprite.getX();
			float Y = location.sprite.getY();
			float sizeX = location.sprite.getWidth();
			float sizeY = location.sprite.getHeight();
			sprite.setBounds(X, Y, sizeX, sizeY);
			sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		}
	}
	
	public Entity shoot(Entity target){
		return Entity.createProjectile(TowerDefense.assetManager.get("fireBall.png", Texture.class), this, target);
	}
	
	public boolean canFire(float delta){
		timer += delta;
		if (timer > attackSpeed){
			timer = 0;
			return true;
		} else {
			return false;
		}
	}

	public static int towerCost(int i) {
		return (i == 1) ? 45 : (i == 2) ? 70 : 100000;
	}
	
	public static String getTowerRange(int towerType){
		return (towerType == 1) ? "500" : (towerType == 2) ? "250" : "";
	}
	
	public static String getTowerDamage(int towerType){
		return (towerType == 1) ? "25" : (towerType == 2) ? "6" : "";
	}
	
	public static String getTowerAttackSpeed(int towerType){
		return (towerType == 1) ? "1" : (towerType == 2) ? "10" : "";
	}
	
}
