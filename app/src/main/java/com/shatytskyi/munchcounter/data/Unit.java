package com.shatytskyi.munchcounter.data;

import java.io.Serializable;
import java.util.Date;

public class Unit implements Serializable, Cloneable {
    public long id;
    public String name;
    public int lvl;
    public int power;

    public Unit (String name, int lvl, int power) {
        this.id = new Date().getTime();
        this.name = name;
        this.lvl = lvl;
        this.power = power;
    }

    public int getScore () {
        return lvl+power;
    }

}
