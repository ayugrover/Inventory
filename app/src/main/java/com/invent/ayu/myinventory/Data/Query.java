package com.invent.ayu.myinventory.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Query {

    private static Query INSTANCE1;
    private Helper Help;
    private SQLiteDatabase dbase;

    private Query(Context context) {
        Help = new Helper(context);
    }

    public static Query getInstance(Context context) {
        if (context == null) {
            return null;
        }

        if (INSTANCE1 == null) {
            INSTANCE1 = new Query(context);
        }
        return INSTANCE1;
    }
    public void deleteEntry(String tabName, String selection, String[] selection_Args) {

        dbase = Help.getReadableDatabase();
        dbase.delete(tabName, selection, selection_Args);

    }
    public void insertIntoTable(String tableName, ContentValues val) {

        dbase = Help.getWritableDatabase();
        dbase.insert(tableName, null, val);

    }
    public void updateData(String table, ContentValues values1, String selection1, String[] selection_args) {

        dbase = Help.getWritableDatabase();
        dbase.update(table, values1, selection1, selection_args);

    }
    public Cursor readFromTable(String tName, String[] projections) {

        dbase = Help.getReadableDatabase();
        return dbase.query(tName, projections, null, null, null, null, null);

    }


}
