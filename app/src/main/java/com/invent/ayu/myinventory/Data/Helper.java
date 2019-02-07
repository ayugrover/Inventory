package com.invent.ayu.myinventory.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.invent.ayu.myinventory.Data.Contract.Db_name;

public class Helper extends SQLiteOpenHelper {
    Helper(Context context) {
        super(context, Db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + Contract.ItemEntry.TABLE + " ("
                + Contract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.ItemEntry.ITEM_NAME + " TEXT NOT NULL, "
                + Contract.ItemEntry.ITEM_PRICE + " INTEGER NOT NULL , "
                + Contract.ItemEntry.ITEM_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + Contract.ItemEntry.ITEM_PICTURE + " TEXT NOT NULL   );";

        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}