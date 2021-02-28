package com.hfad.starbuzzcoffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "starbuzz";

    public static final String TABLE_DRINK_NAME = "DRINK";

    public static final String TABLE_DRINK_COL_ID = "id";
    public static final String TABLE_DRINK_COL_NAME = "name";
    public static final String TABLE_DRINK_COL_DESC = "description";
    public static final String TABLE_DRINK_COL_IMG = "image_resource_id";

    public static final int DATABASE_OLD_VERSION = 0;
    public static final int DATABASE_NEW_VERSION = 1;

    public static final String CREATE_DRINK_TABLE_SQL =
            "CREATE TABLE " + TABLE_DRINK_NAME + "(" +
                    TABLE_DRINK_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TABLE_DRINK_COL_NAME +" TEXT, " +
                    TABLE_DRINK_COL_DESC +" TEXT, " +
                    TABLE_DRINK_COL_IMG +" INTEGER);";

    public static final String ALTER_DRINK_TABLE_SQL =
            "ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;";


    public StarbuzzDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_NEW_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateDatabase(sqLiteDatabase, DATABASE_OLD_VERSION, DATABASE_NEW_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        updateDatabase(sqLiteDatabase, oldVersion, newVersion);
    }

    private void updateDatabase(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion < 1) {
            sqLiteDatabase.execSQL(CREATE_DRINK_TABLE_SQL);
            insertDrink(sqLiteDatabase,
                    "Latte",
                    "Espesso and Latte Milk",
                    R.drawable.latte);
            insertDrink(sqLiteDatabase,
                    "Cappucino",
                    "Espresso, hot milk and steamed milk foam",
                    R.drawable.cappuccino);
            insertDrink(sqLiteDatabase,
                    "Filter",
                    "Our best drip coffee",
                    R.drawable.filter);
        }
        if(newVersion < 2) {
            sqLiteDatabase.execSQL(ALTER_DRINK_TABLE_SQL);
        }
    }

    private void insertDrink(SQLiteDatabase sqLiteDatabase,
                             final String name,
                             final String description,
                             final int image_resource_id) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put(TABLE_DRINK_COL_NAME, name);
        drinkValues.put(TABLE_DRINK_COL_DESC, description);
        drinkValues.put(TABLE_DRINK_COL_IMG, image_resource_id);

        sqLiteDatabase.insert(TABLE_DRINK_COL_IMG, null, drinkValues);
    }

}
