package com.hfad.starbuzzcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
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
        CheckBox fav = findViewById(R.id.favorite);

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
                            StarbuzzDatabaseHelper.TABLE_DRINK_COL_DESC,
                            StarbuzzDatabaseHelper.TABLE_DRINK_COL_FAVORITE },
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
                fav.setChecked(cursor.getInt(3) == 1); // 1 means true
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

    public void onFavoriteClicked(View view) {

        new UpdateDrinkTask().execute((int) getIntent().getExtras().getInt(Drink.EXTRA_DRINK_DATA));

        /*
         * Use Main Thread
         *

        int drink_id = (int) getIntent().getExtras().get(Drink.EXTRA_DRINK_DATA);
        CheckBox favorite = findViewById(R.id.favorite);
        ContentValues drinkValues = new ContentValues();
        drinkValues.put(
                StarbuzzDatabaseHelper.TABLE_DRINK_COL_FAVORITE,
                favorite.isChecked() ? 1 : 0);

        SQLiteOpenHelper sqLiteOpenHelper = new StarbuzzDatabaseHelper(this);
        SQLiteDatabase db = null;
        try {
            db = sqLiteOpenHelper.getWritableDatabase();
            db.update(
                    StarbuzzDatabaseHelper.TABLE_DRINK_NAME,
                    drinkValues,
                    StarbuzzDatabaseHelper.TABLE_DRINK_COL_ID + " = ?",
                    new String[] {Integer.toString(drink_id)}
            );
        } catch (SQLiteException ex) {
            Toast.makeText(this, "Database Unavailable", Toast.LENGTH_LONG).show();
        } finally {
            if(db != null) {
                db.close();
            }
        }

         */
    }

    class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {

        private ContentValues drinkValues;

        @Override
        public void onPreExecute() {
            CheckBox favorite = findViewById(R.id.favorite);
            drinkValues = new ContentValues();
            drinkValues.put(
                    StarbuzzDatabaseHelper.TABLE_DRINK_COL_FAVORITE,
                    favorite.isChecked() ? 1 : 0);
        }

        @Override
        protected Boolean doInBackground(Integer... drinks) {
            int drink_id = drinks[0];
            SQLiteOpenHelper starbuzzDatabaseHelper =
                    new StarbuzzDatabaseHelper(DrinkActivity.this);
            SQLiteDatabase db = null;
            try {
                db = starbuzzDatabaseHelper.getWritableDatabase();
                db.update(
                        StarbuzzDatabaseHelper.TABLE_DRINK_NAME,
                        drinkValues,
                        StarbuzzDatabaseHelper.TABLE_DRINK_COL_ID + " = ?",
                        new String[] {Integer.toString(drink_id)}
                );
                return true;
            } catch (SQLiteException ex) {
                return false;
            } finally {
                if(db != null) {
                    db.close();
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean res) {
            if(!res) {
                Toast.makeText(DrinkActivity.this, "Database Unavailable", Toast.LENGTH_LONG).show();
            }
        }
    }
}