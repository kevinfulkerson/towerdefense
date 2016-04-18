package renderables;

import gamelogic.DamageCalculator;
import screens.GridScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Projectile extends Entity{
	
	private Entity target;
	private Entity parent;

	public Projectile(Texture texture, Entity parent, Entity target){
		sprite = new Sprite(texture);
		sprite.setBounds(parent.sprite.getX() + parent.sprite.getWidth()/2, parent.sprite.getY() + parent.sprite.getHeight()/2, parent.sprite.getWidth()/10, parent.sprite.getWidth()/10);
		sprite.setOrigin(texture.getWidth() / 2, texture.getHeight() / 2);
		sprite.scale(0.25f);
		isMovable = true;
		isMovementSpeedModifiable = false;
		isArmed = false;
		isTargetable = false;
		isVisible = true;
		isDefenseModifiable = false;
		isAttackModifiable = false;
		isPrioritizable = false;
		priority = -2;
		movementSpeed = (float) (0.5*(GridScreen.screenWidth * Gdx.graphics.getDeltaTime()));
		type = TYPE_PROJECTILE;
		this.target = target;
		this.parent = parent;
	}
	
	public boolean isColliding(){
		return (Math.abs((sprite.getX() + sprite.getWidth()/2) - (target.sprite.getX() + target.sprite.getWidth()/2)) <= ((target.sprite.getWidth() + sprite.getWidth()) / 6) 
				&& Math.abs((sprite.getY() + sprite.getHeight()/2) - (target.sprite.getY() + target.sprite.getHeight()/2)) <= ((target.sprite.getHeight() + sprite.getHeight()) / 6));
	}
	
	public boolean trackTarget(){
		double angle = Math.atan2((target.sprite.getY() + target.sprite.getHeight()/2) - sprite.getY(), (target.sprite.getX() + target.sprite.getWidth()/2) - sprite.getX());
		float yAmount = (float) (Math.sin(angle) * movementSpeed);
		float xAmount = (float) (Math.cos(angle) * movementSpeed);
		sprite.translate(xAmount, yAmount);
		if(isColliding()){
			DamageCalculator.dealDamage(parent, target);
			state = STATE_DEAD;
		}
		return (state == STATE_DEAD);
	}
}
