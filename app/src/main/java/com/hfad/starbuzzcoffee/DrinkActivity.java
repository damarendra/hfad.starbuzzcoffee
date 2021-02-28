package com.hfad.starbuzzcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.starbuzzcoffee.persistence.Drink;

public class DrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        ImageView img = findViewById(R.id.photo);
        TextView name = findViewById(R.id.name);
        TextView desc = findViewById(R.id.desc);

//        Drink drink = Drink.drinks[(int) getIntent().getExtras().get(Drink.EXTRA_DRINK_DATA)];
//        img.setImageResource(drink.getImg());
//        img.setContentDescription(drink.toString());
//        name.setText(drink.getName());
//        desc.setText(drink.getDesc());

        int drink_id = (int) getIntent().getExtras().get(Drink.EXTRA_DRINK_DATA);

        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = starbuzzDatabaseHelper.getReadableDatabase();
            cursor = db.query(
                    StarbuzzDatabaseHelper.TABLE_DRINK_NAME,
                    new String[] {
                            StarbuzzDatabaseHelper.TABLE_DRINK_COL_IMG,
                            StarbuzzDatabaseHelper.TABLE_DRINK_COL_NAME,
                            StarbuzzDatabaseHelper.TABLE_DRINK_COL_DESC },
                    StarbuzzDatabaseHelper.TABLE_DRINK_COL_ID + " = ?",
                    new String[] { Integer.toString(drink_id) }, // the drink id
                    null,
                    null,
                    null
            );
            if(cursor.moveToFirst()) {
                img.setImageResource(cursor.getInt(0));
                img.setContentDescription(cursor.getString(1));
                name.setText(cursor.getString(1));
                desc.setText(cursor.getString(2));
            }
        } catch (SQLiteException ex) {
            Toast.makeText(this, "Database Unavailable", Toast.LENGTH_LONG).show();
        } finally {
            if(cursor != null) {
                cursor.close();
            }
            if(db != null) {
                db.close();
            }
        }

    }
}