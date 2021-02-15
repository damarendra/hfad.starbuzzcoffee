package com.hfad.starbuzzcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hfad.starbuzzcoffee.persistence.Drink;

public class DrinkCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);

        ListView listDrinks = findViewById(R.id.list_drinks);

        listDrinks.setAdapter(new ArrayAdapter<Drink>(
                this,
                android.R.layout.simple_list_item_1,
                Drink.drinks
        ));

        listDrinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int pos, long id) {
                Intent intent = new Intent(DrinkCategoryActivity.this,
                        DrinkActivity.class);
                Log.d("Starbuzz Debug", "id -> " + id);
                intent.putExtra(Drink.EXTRA_DRINK_DATA, (int) id);
                startActivity(intent);
            }
        });
    }
}