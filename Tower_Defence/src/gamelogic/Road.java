package gamelogic;

import java.util.ArrayList;
import java.util.Random;

import logging.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Road {

	private static Sprite roadSprite;
	private static ArrayList<Tile> road;
	public static Grid grid;
	
	// 0 is equivalent to TURN_LEFT (go up)
	// 1 is equivalent to TURN_RIGHT (go right)
	private static byte previousDirection, currentDirection;
	
	private static AssetManager manager;
	
	public Road(AssetManager manager) {
		Road.manager = manager;
		road = new ArrayList<Tile>();
		previousDirection = 0;
		currentDirection = 0;
	}

	public void setRoad(Grid gridIn) {
		grid = gridIn;
		Vector2 gridSize = grid.getGridSize(), location = new Vector2(0, 0);
		boolean done = false;

		while (!done) {
//			System.out.println("Location: " + location + "    Grid: " + gridSize);
			setRoad(location);
			
			if (atEnd(gridSize, location)) {
//				System.out.println("done");
				if (currentDirection == 0) {
					road.get(road.size()-1).sprite.rotate90(true);
				}
				done = true;
			}
			
			previousDirection = currentDirection;
			if (!canGoRight(gridSize, location) && canGoUp(gridSize, location)) {
				location = goUp(location);
			} else if (!canGoUp(gridSize, location) && canGoRight(gridSize, location)) {
				location = goRight(location);
			} else {
				if (calculateChance()) {
					location = goRight(location);
				} else {
					location = goUp(location);
				}
			}
		}
	}

	private boolean calculateChance() {
		Random random = new Random();
		if (random.nextBoolean()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean atEnd(Vector2 gridSize, Vector2 location) {
		return ((gridSize.x == location.x) && (gridSize.y == location.y));
	}

	private Vector2 goRight(Vector2 location) {
		currentDirection = 1;
		return new Vector2(location.x + 1, location.y);
	}

	private Vector2 goUp(Vector2 location) {
		currentDirection = 0;
		return new Vector2(location.x, location.y + 1);
	}
	
	private boolean canGoUp(Vector2 gridSize, Vector2 location) {
		return location.y < gridSize.y;
	}

	private boolean canGoRight(Vector2 gridSize, Vector2 location) {
		return location.x < gridSize.x;
	}
	
	private static void setRoad(Vector2 location) {
//		System.out.println("Road Size : " + road.size() + "         Attempt : " + (road.size()-1));
		Gdx.app.log(Constants.LOG, "Road Size : " + road.size() + "         Attempt : " + (road.size()-1));
//		System.out.println("currentDirection : " + currentDirection + "           previousDirection : " + previousDirection);
		Gdx.app.log(Constants.LOG, "currentDirection : " + currentDirection + "           previousDirection : " + previousDirection);
		if (currentDirection == previousDirection && previousDirection == 1){//STRAIGHT UP
			roadSprite = new Sprite(manager.get("pathStraight.png", Texture.class));
			grid.get(location).sprite.setTexture(roadSprite.getTexture());
		} else if (currentDirection == previousDirection && previousDirection == 0) {//STRAIGHT RIGHT
			roadSprite = new Sprite(manager.get("pathStraight.png", Texture.class));
			grid.get(location).sprite.setTexture(roadSprite.getTexture());
			if (!road.isEmpty()) {
				road.get(road.size()-1).sprite.rotate90(true);
			}
		} else if (currentDirection == 0){//LEFT_TURN
			road.get(road.size()-1).sprite.setTexture(manager.get("pathLeft.png", Texture.class));
			grid.get(location).sprite.setTexture(roadSprite.getTexture());
		} else if (currentDirection == 1){//RIGHT_TURN
			road.get(road.size()-1).sprite.setTexture(manager.get("pathRight.png", Texture.class));
			grid.get(location).sprite.setTexture(roadSprite.getTexture());
		}
		grid.get(location).isRoad = true;
		grid.get(location).nextDirection = currentDirection;
		road.add(grid.get(location));
	}
	
	public Tile getTile(int tile){
		return road.get(tile);
	}
	
	public int size(){
		return road.size();
	}
	
}
