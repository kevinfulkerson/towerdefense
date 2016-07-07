package com.goonsquad.galactictd.gamelogic;

public enum AttackType {

    NORMAL(0),
    INCENDIARY(1),
    PIERCING(2);

    final int indexOfDamageTable;

    AttackType(int index){
        indexOfDamageTable = index;
    }

}