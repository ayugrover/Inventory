package com.invent.ayu.myinventory.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry.TABLE;

public class Contract {
    public static final String AUTHORITY = "ayu.myinventory";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final String Db_name = "Item.db";
    public static final String PATH = TABLE;

    public Contract() {
    }

    public static abstract class ItemEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, PATH);
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH;

        public static final String _ID = BaseColumns._ID;
        public static final String TABLE = "item";
        public static final String ITEM_NAME = "name";
        public static final String ITEM_QUANTITY = "quantity";
        public static final String ITEM_PRICE = "price";
        public static final String ITEM_PICTURE = "picture";
    }
}
