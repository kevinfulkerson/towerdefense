package gamelogic;

public enum DefenseType {

	LIGHT(0),
	MEDIUM(1),
	HEAVY(2);
	
	final int indexOfDamageTable;
	
	DefenseType(int index){
		indexOfDamageTable = index;
	}
	
}
