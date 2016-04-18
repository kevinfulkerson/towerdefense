package gamelogic;

import logging.Constants;

import com.badlogic.gdx.Gdx;

import exceptions.InvalidActionOnEntityException;
import renderables.Entity;

public class DamageCalculator {
	
	// damageTable[0][0] gets the damage dealt by a normal attack on a light defense target
	// order of values for accessing the table are: 1.attackType 2.defenseType
	private static float damageTable[][] = {{1.0f, 2.0f, 0.5f},  //light, medium, heavy (defense) -> normal (attack) 
									 		{2.0f, 0.5f, 1.0f},  //light, medium, heavy (defense) -> incendiary (attack)
									 		{0.5f, 1.0f, 2.0f}}; //light, medium, heavy (defense) -> piercing (attack)
	
	private static float calculateDamageDealt(Entity attacker, Entity defender) throws InvalidActionOnEntityException{
		float damageDealt = attacker.attackDamage;
		if(defender.defenseValue > 0){
			damageDealt -= (float) (attacker.attackDamage * ((0.1 * defender.defenseValue)/(1 + (0.1 * defender.defenseValue))));
			Gdx.app.log(Constants.LOG, String.valueOf((0.1 * defender.defenseValue)/(1 + (0.1 * defender.defenseValue))));
		} else if(defender.defenseValue < 0) {
			damageDealt = (float) (attacker.attackDamage * (2 - Math.pow(.9, Math.abs(defender.defenseValue))));
		}
		damageDealt *= damageTable[attacker.attackType.indexOfDamageTable][defender.defenseType.indexOfDamageTable];
		return damageDealt;
	}
	
	/**Attempts to deal damage to the damageReciever based on the attackDamage of the damageDealer.
	 * 
	 * @param damageDealer: the Entity that is dealing the damage
	 * @param damageReceiver: the Entity that is being dealt damage
	 * @return true if damage is dealt, or false if damage was not dealt.
	 */
	public static boolean dealDamage(Entity damageDealer, Entity damageReceiver){
		try{
			damageReceiver.manipulateHealth(-calculateDamageDealt(damageDealer, damageReceiver));
		} catch(InvalidActionOnEntityException e){
			System.err.println(e.getMessage()); //report to logging device later on?
			Gdx.app.error(Constants.LOG, e.getMessage(), e);
			return false;
		}
		return true;
	}
	
}