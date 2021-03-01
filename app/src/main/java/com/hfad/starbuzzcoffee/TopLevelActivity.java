package com.hfad.starbuzzcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.hfad.starbuzzcoffee.persistence.Drink;

public class TopLevelActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        setupDrinkListOptions();
        setupFavoriteDrinks();
    }

    private void setupDrinkListOptions() {
        ListView listView = findViewById(R.id.list_options);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int pos, long id) {
                if(pos == 0) {
                    startActivity(
                            new Intent(
                                    TopLevelActivity.this,
                                    DrinkCategoryActivity.class)
                    );
                }
            }
        });
    }

    private void setupFavoriteDrinks() {
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        db = null;
        cursor = null;

        try {
            db = starbuzzDatabaseHelper.getReadableDatabase();
            setFavoriteDrinksCursor();

            ListView favoriteDrinkList = findViewById(R.id.list_favorite_drinks);
            favoriteDrinkList.setAdapter(new SimpleCursorAdapter(
                    TopLevelActivity.this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{ StarbuzzDatabaseHelper.TABLE_DRINK_COL_NAME },
                    new int[] { android.R.id.text1 },
                    0
            ));

            favoriteDrinkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> listView, View itemView, int pos, long id) {
                    Intent intent = new Intent(TopLevelActivity.this,
                            DrinkActivity.class);
                    intent.putExtra(Drink.EXTRA_DRINK_DATA, (int) id);
                    Log.d("damn", "drink id = "+id);
                    startActivity(intent);
                }
            });

        } catch (SQLiteException ex) {
            Toast.makeText(this, "Database Unavailable", Toast.LENGTH_LONG).show();
        }
    }

    private void setFavoriteDrinksCursor() {
        if(db != null) {
            cursor = db.query(
                    StarbuzzDatabaseHelper.TABLE_DRINK_NAME,
                    new String[]{
                            StarbuzzDatabaseHelper.TABLE_DRINK_COL_ID,
                            StarbuzzDatabaseHelper.TABLE_DRINK_COL_NAME
                    },
                    StarbuzzDatabaseHelper.TABLE_DRINK_COL_FAVORITE + " = ?",
                    new String[]{ Integer.toString(1) },
                    null,
                    null,
                    null
            );
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        ListView favoriteDrinkList = findViewById(R.id.list_favorite_drinks);
        CursorAdapter cursorAdapter = (CursorAdapter) favoriteDrinkList.getAdapter();
        setFavoriteDrinksCursor();
        cursorAdapter.changeCursor(cursor);
    }

    @Override
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