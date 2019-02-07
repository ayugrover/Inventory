package com.invent.ayu.myinventory.Data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.invent.ayu.myinventory.invent;
import com.invent.ayu.myinventory.Item_detail;
import com.invent.ayu.myinventory.R;
import android.graphics.Bitmap;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry.ITEM_NAME;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry.ITEM_PICTURE;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry.ITEM_PRICE;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry.ITEM_QUANTITY;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry.TABLE;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry._ID;
import android.graphics.BitmapFactory;

public class CursorAdapter extends android.widget.CursorAdapter{

    private LayoutInflater Inflater;

    public CursorAdapter(Context context, Cursor c, int flag) {
        super(context, c, flag);
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return Inflater.inflate(R.layout.item_list, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView Quantityview = (TextView) view.findViewById(R.id.quantity1);
        TextView Priceview = (TextView) view.findViewById(R.id.Price);
        Button buyBtn = (Button) view.findViewById(R.id.buyBtn);
        LinearLayout linear = (LinearLayout) view.findViewById(R.id.list_Item);
        TextView Namee1 = (TextView) view.findViewById(R.id.namEe);
        ImageView imagee = (ImageView) view.findViewById(R.id.image_View);
        final int ID1 = cursor.getInt(cursor.getColumnIndex(_ID));
        final String itemm = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
        final int Quantityy = cursor.getInt(cursor.getColumnIndex(ITEM_QUANTITY));
        final int Pricee = cursor.getInt(cursor.getColumnIndex(ITEM_PRICE));
        final int imageindex = cursor.getColumnIndex(ITEM_PICTURE);

        Namee1.setText(itemm.toUpperCase());
        Quantityview.setText("Quantity :- " + Quantityy + " ");
        Priceview.setText("Price :- " + Pricee + " ");

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Quantity = Quantityy - 1;
                if (Quantity < 0) {
                    Quantity = 0;

                    Toast.makeText(context, "We've Run Out of items", Toast.LENGTH_SHORT).show();
                }
                ContentValues valuess = new ContentValues();
                valuess.put(ITEM_QUANTITY, Quantity);
                String selectionn = _ID + "=?";
                String[] selectionArggs = {String.valueOf(ID1)};
                Query.getInstance(context).updateData(TABLE, valuess, selectionn, selectionArggs);
                invent.onCursorRefresh();
            }
        });

      final byte[] image = cursor.getBlob(imageindex);
        Bitmap image_bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        imagee.setImageBitmap(image_bitmap);


        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", ID1);
                bundle.putString("name", itemm);
                bundle.putInt("price", Pricee);
                bundle.putInt("quantity", Quantityy);
                //bundle.putInt("image", imageindex);
                bundle.putByteArray("imageByte", image);
                Intent i = new Intent(context, Item_detail.class);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }
}
