package gamelogic;

import screens.GridScreen;
import logging.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Grid {

	private static Tile[][] grid;
	private static Sprite defaultGrid;
	private static Sprite clickedGrid;

	public Grid(AssetManager manager, float gridWidthPixels, float gridHeightPixels, int gridSizeX, int gridSizeY,
			Road road) {
		defaultGrid = new Sprite(manager.get("mapDefault.png", Texture.class));
		clickedGrid = new Sprite(manager.get("mapSelected.png", Texture.class));

		grid = new Tile[gridSizeX][gridSizeY];
		for (int i = gridSizeX - 1; i >= 0; i--) {
			for (int j = gridSizeY - 1; j >= 0; j--) {
				defaultGrid.setBounds(((gridWidthPixels / gridSizeX) * i), ((gridHeightPixels / gridSizeY) * j),
						(gridWidthPixels / gridSizeX), (gridHeightPixels / gridSizeY));
				grid[i][j] = new Tile(defaultGrid);
			}
		}
		road.setRoad(this);
	}

	public Vector2 getGridSize() {
		return new Vector2((grid.length) - 1, (grid[0].length) - 1);
	}

	public void draw(SpriteBatch batch) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				getGridTile(i, j).sprite.draw(batch);
			}
		}
	}

	public Tile getGridTile(float x, float y) {
		return grid[(int) x][(int) y];
	}

	public boolean toggleClick() {
		if (Grid.findLocation()) {
			Tile location = grid[(int) GridScreen.gridClickedLocation.x][(int) GridScreen.gridClickedLocation.y];
			if (location.isOccupied || location.isRoad) {
				//
				if (location.isOccupied)
//					System.out.println("that tile is occupied");
					Gdx.app.log(Constants.LOG, "that tile is occupied");
				if (location.isRoad)
//					System.out.println("that tile is a road");
					Gdx.app.log(Constants.LOG, "that tile is a road");
				//
				return false;
			} else {
				location.sprite.setTexture(Grid.clickedGrid.getTexture());
				return true;
			}
		} else {
			GridScreen.game.pause();
			GridScreen.game.setScreen(GridScreen.game.homeScreen);
			return false;
		}
	}

	public void resetTile() {
		grid[(int) GridScreen.gridClickedLocation.x][(int) GridScreen.gridClickedLocation.y].sprite
				.setTexture(Grid.defaultGrid.getTexture());
//		System.out.println("tile reset");
		Gdx.app.log(Constants.LOG, "tile reset");
	}

	public static boolean findLocation() {
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		
		if (y < GridScreen.screenOffset){
			return false;
		}
		
		y -= GridScreen.screenOffset;
		x /= defaultGrid.getWidth();
		y /= defaultGrid.getHeight();
		y = (int) (grid[0].length - y - 1);

//		System.out.println("Clicked Location: (" + x + ", " + y + ")");
		Gdx.app.log(Constants.LOG, "Clicked Location: (" + x + ", " + y + ")");
		GridScreen.gridClickedLocation = new Vector2(x, y);
		return true;
	}

	public Tile get(Vector2 location) {
		return grid[(int) location.x][(int) location.y];
	}

	public void dispose() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j].sprite.getTexture().dispose();
			}
		}
		defaultGrid.getTexture().dispose();
		clickedGrid.getTexture().dispose();
	}

}
