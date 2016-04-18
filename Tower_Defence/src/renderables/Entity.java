package renderables;

import screens.GridScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import damage.*;
import exceptions.InvalidActionOnEntityException;
import gamelogic.AttackType;
import gamelogic.DefenseType;

public class Entity implements EntityADT {

	// TODO:--------------------------------------------------------------------------------

	// CURRENTLY VERY UNSTABLE CLASS; DYNAMIC, VOLATILE, LIABLE TO CHANGE -> ALL
	// GOOD DESCRIPTORS
	// AS SUCH, BE VERY CAREFUL WITH USING THIS, AS IT IS CHANGING NEARLY EVERY
	// TIME I VIEW IT
	//
	// ~KEVIN

	// TODO:--------------------------------------------------------------------------------

	public static final byte STATE_ALIVE = 1, STATE_DEAD = 2, TYPE_TOWER = 0, TYPE_ENEMY = 1, TYPE_PROJECTILE = 2;

	// potentially more states will need to be added, so I'll use an integer
	// state system just in case

	// 25 fields
	// potential expansion points: maxHealth, percentHealth, maxMovementSpeed,
	// maxAttackSpeed, attackRange, isRanged,
	
	// 0 = tower; 1 = Enemy; 2 = Projectile;
	public byte type = 4; //Entity Type
	public Sprite sprite;
	public float attackDamage, defenseValue, range;
	public byte state = STATE_ALIVE;
	public AttackType attackType;
	public DefenseType defenseType;
	public double facing = 0;
	
	protected float health;
	protected float scale = 1; // priority will never reach 2 billion
	protected float attackSpeed;
	protected float priority = 1;
	protected float movementSpeed;
	protected boolean isMovable, isMovementSpeedModifiable, isArmed, isTargetable, isVisible, isDefenseModifiable,
			isAttackModifiable, isPrioritizable, isRotatable, isScalable; // might
																			// require
																			// expansion
																			// later
	private int tileOn = 0, directionToTravel = 1;

	public int worth; //gold amount received when unit is killed
	public int score; //score amount received when unit is killed
	
	private float movementSpeedMultiplier;
	
	protected Entity() {

	}

	public static Entity createGenericEntity() {
		return new Entity();
	}
	
	public static Entity createProjectile(Texture texture, Entity parent, Entity target) {
		return new Projectile(texture, parent, target);
	}

	public Enemy createEnemy(int unitType) {
		directionToTravel = 1; //GridScreen.road.getTile(tileOn).nextDirection;
		return new Enemy(unitType, unitType);
	}

	public Tower createTower(Texture texture, Vector2 gridIndexLocation, int towerType) {
		return new Tower(texture, gridIndexLocation, towerType);
	}

	public static Entity createGenericDefender() {
		Entity e = new Entity();
		e.defenseValue = -1;
		e.health = 100;
		e.defenseType = DefenseType.LIGHT;
		e.isTargetable = true;
		return e;
	}

	public static Entity createGenericAttacker() {
		Entity e = new Entity();
		e.attackDamage = 20;
		e.attackType = AttackType.INCENDIARY;
		e.isArmed = true;
		return e;
	}

	// --------------------End Constructors---------------------
	// ---------------------------------------------------------
	// ---------------------------------------------------------
	// ----------------------Begin Methods----------------------

	public void setState(byte state) {
		this.state = state;
	}

	public void changePriority(float delta) throws InvalidActionOnEntityException {
		if (isPrioritizable)
			priority += (movementSpeed * delta);
		else
			throw new InvalidActionOnEntityException("Entity cannot have priority changed");
	}

	public void scaleSprite(float scalingValue) throws InvalidActionOnEntityException {
		if (isScalable) {
			scale = scalingValue;
			sprite.setScale(scale);
		} else
			throw new InvalidActionOnEntityException("Entity cannot be scaled");
	}

	public void moveSprite() { //TODO: rewrite basically this whole shitty thing
		if (type == TYPE_ENEMY){
			movementSpeedMultiplier = GridScreen.screenWidth * Gdx.graphics.getDeltaTime();
			if (directionToTravel == 1){
				sprite.translateY(movementSpeed * movementSpeedMultiplier);
			} else {
				sprite.translateX(movementSpeed * movementSpeedMultiplier);
			}
			if (isRotatable){
				sprite.rotate(1);
			}
			
			try {
				if (sprite.getY() >= GridScreen.road.getTile(tileOn).sprite.getY()){
					directionToTravel = 0;
					tileOn++;
				} else if (sprite.getX() >= GridScreen.road.getTile(tileOn).sprite.getX()){
					directionToTravel = 1;
					tileOn++;
				}
			} catch (IndexOutOfBoundsException e){}
			
			if (sprite.getX() >= GridScreen.screenWidth || sprite.getY() >= GridScreen.screenHeight){
				state = Entity.STATE_DEAD;
				GridScreen.player.playerLoseHealth();
			}
		}
	}

	public void setFacingToAngle(float facing) throws InvalidActionOnEntityException {
		if (isRotatable)
			sprite.setRotation(facing + 90);
		else
			throw new InvalidActionOnEntityException("Entity cannot be rotated");
	}

	public void setFacingToEntity(Entity entityToFace) throws InvalidActionOnEntityException {
		if (isRotatable) {
			facing = Math.atan2(this.sprite.getY() - entityToFace.sprite.getY(), this.sprite.getX() - entityToFace.sprite.getX());
			setFacingToAngle((float) (facing * (180 / Math.PI)));
		} else
			throw new InvalidActionOnEntityException("Entity cannot be rotated");
	}
	
	public float distanceBetween(Entity enemy){
		float y = this.sprite.getY() - enemy.sprite.getY();
		float x = this.sprite.getX() - enemy.sprite.getX();
		return (float) Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}
	
	public void manipulateHealth(float amount) throws InvalidActionOnEntityException {
		if (isTargetable) {
			health += amount;
//			System.out.println("health: " + health);
			if (health <= 0)
				setState(STATE_DEAD);
		} else {
			throw new InvalidActionOnEntityException("Entity cannot currently be the target of damage");
		}
	}

	public void changeMovementSpeed(float amount) throws InvalidActionOnEntityException {
		if (isMovementSpeedModifiable) {
			movementSpeed += amount;
		} else {
			throw new InvalidActionOnEntityException("Entity movement speed cannot be modified");
		}
	}

}
