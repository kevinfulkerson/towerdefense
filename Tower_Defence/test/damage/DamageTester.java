package damage;

import gamelogic.DamageCalculator;

import org.junit.Assert;
import org.junit.Test;

import renderables.Entity;

public class DamageTester {

	public static void main(String[] args) {
		
	}

	@Test
	public void testingOne(){
		Assert.assertTrue(DamageCalculator.dealDamage(Entity.createGenericAttacker(), Entity.createGenericDefender()));
	}
	
}
