package renderables;

import java.util.ArrayList;

public class Wave {

	private double timeElapsed = 0;
	private boolean waveGoing = false;
	public int spawnedUnits = 0, aliveUnits = 0, waveNumber = 0, offset = 5, multi = 0; //offset is the number of "seconds" of delay before first wave starts
	
	public void doTheWave(ArrayList<Entity> drawableEntities, float delta) {
		timeElapsed += delta;
		
		if ((!waveGoing) && (waveNumber == 0) && (timeElapsed > offset)) {
			waveNumber++;
			waveGoing = true;
		}
		if ((waveNumber >= 1) && !waveGoing && aliveUnits == 0) {
			waveNumber++;
			spawnedUnits = 0;
			waveGoing = true;
			if (waveNumber % 5 == 0) {
				multi++;
			}
		}

		if (timeElapsed > 1 && waveGoing){
			spawnNewUnit(drawableEntities);
		}
		if ((spawnedUnits == waveNumber) || (spawnedUnits >= 10)){
			waveGoing = false;
		}
//		System.out.println("Wave : " + waveNumber + "     Spawned : " + spawnedUnits + "    Alive : " + aliveUnits);
	}

	private void spawnNewUnit(ArrayList<Entity> drawableEntities) {
		timeElapsed = 0;
		spawnedUnits++;
		aliveUnits++;
		int unitType = ((spawnedUnits == waveNumber) && (waveNumber > 4)) ? 2 : ((waveNumber > 9) && (spawnedUnits > 10-multi)) ? 2 : 1;
		drawableEntities.add(new Enemy(unitType, waveNumber));
	}
	
}
