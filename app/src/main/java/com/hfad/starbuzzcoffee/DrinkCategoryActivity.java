package com.hfad.starbuzzcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.hfad.starbuzzcoffee.persistence.Drink;

public class DrinkCategoryActivity extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private Cursor cursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);

        ListView listDrinks = findViewById(R.id.list_drinks);

//        listDrinks.setAdapter(new ArrayAdapter<Drink>(
//                this,
//                android.R.layout.simple_list_item_1,
//                Drink.drinks
//        ));

        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        db = null;
        cursor = null;

        try {
            db = starbuzzDatabaseHelper.getReadableDatabase();
            cursor = db.query(
                    StarbuzzDatabaseHelper.TABLE_DRINK_NAME,
                    new String[] {
                            StarbuzzDatabaseHelper.TABLE_DRINK_COL_ID,
                            StarbuzzDatabaseHelper.TABLE_DRINK_COL_NAME
                    },
                    null,
                    null,
                    null,
                    null,
                    null
            );
            listDrinks.setAdapter(new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{ StarbuzzDatabaseHelper.TABLE_DRINK_COL_NAME },
                    new int[] { android.R.id.text1 },
                    0
            ));
        } catch (SQLiteException ex) {
            Toast.makeText(this, "Database Unavailable", Toast.LENGTH_LONG).show();
        }

        listDrinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int pos, long id) {
                Intent intent = new Intent(DrinkCategoryActivity.this,
                        DrinkActivity.class);
                intent.putExtra(Drink.EXTRA_DRINK_DATA, (int) id);
                startActivity(intent);
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        if(cursor != null) {
            cursor.close();
        }
        if(db != null) {
            db.close();
        }
    }
}