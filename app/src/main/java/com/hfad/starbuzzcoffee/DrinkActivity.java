package com.hfad.starbuzzcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hfad.starbuzzcoffee.persistence.Drink;

public class DrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        Drink drink = Drink.drinks[(int) getIntent().getExtras().get(Drink.EXTRA_DRINK_DATA)];

        ImageView img = findViewById(R.id.photo);
        TextView name = findViewById(R.id.name);
        TextView desc = findViewById(R.id.desc);

        img.setImageResource(drink.getImg());
        img.setContentDescription(drink.toString());
        name.setText(drink.getName());
        desc.setText(drink.getDesc());
    }
}