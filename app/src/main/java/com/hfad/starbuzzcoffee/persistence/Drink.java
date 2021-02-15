package com.hfad.starbuzzcoffee.persistence;

import com.hfad.starbuzzcoffee.R;

public class Drink {

    private String name;
    private String desc;
    private int img;

    public static final String EXTRA_DRINK_DATA = "DRINK/EXTRA_DRINK_DATA";

    public static final Drink[] drinks = {
            new Drink("Latte", "A couple of espresso shots with steamed milk",
                R.drawable.latte),
            new Drink("Cappuccino", "Espresso, hot milk, and a steamed milk foam",
                R.drawable.cappuccino),
            new Drink("Filter", "Highest quality beans roasted and brewed fresh",
                R.drawable.filter)
    };

    private Drink(String name, String desc, int img) {
        this.name = name;
        this.desc = desc;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getImg() {
        return img;
    }

    @Override
    public String toString() {
        return name;
    }

}
