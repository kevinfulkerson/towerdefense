package gamelogic;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tile {

	public Sprite sprite;
	public boolean isOccupied = false;
	public boolean isRoad = false;
	//0 = Up; 1 = Right;
	public int nextDirection;
	
	Tile(Sprite spriteGiven) {
		nextDirection = -1;
		sprite = new Sprite(spriteGiven.getTexture());
		sprite.setBounds(spriteGiven.getX(), spriteGiven.getY(),
				spriteGiven.getWidth(), spriteGiven.getHeight());
	}

}
