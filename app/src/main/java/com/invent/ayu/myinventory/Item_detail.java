package com.invent.ayu.myinventory;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.invent.ayu.myinventory.Data.Query;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry.ITEM_NAME;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry.ITEM_PRICE;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry.ITEM_QUANTITY;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry.ITEM_PICTURE;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry.TABLE;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry._ID;

public class Item_detail extends AppCompatActivity {

    TextView Name;
    TextView cost;
    TextView Quantity;
    ImageView images;

    ImageView incre;
    ImageView decre;
    Button order;
    Button del;
    int ID;
    String item;
    int mQuantity, mPrice;
    int imageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_edit);

        images = (ImageView) findViewById(R.id.itemImage);
        del = (Button) findViewById(R.id.Delete);
        order = (Button) findViewById(R.id.order);
        Name = (TextView) findViewById(R.id.nameEdit);
        incre = (ImageView) findViewById(R.id.removeAngle);
        decre = (ImageView) findViewById(R.id.addAngle);
        cost = (TextView) findViewById(R.id.priceEdit);
        Quantity = (TextView) findViewById(R.id.quantityEdit);
        Intent i = getIntent();
        if (i != null) {
            Bundle bundle = i.getExtras();
            mQuantity = bundle.getInt("quantity");
            ID = bundle.getInt("id");
            item = bundle.getString("name");
            Name.setText(item);
            mPrice = bundle.getInt("price");
            cost.setText(String.valueOf(mPrice));
            Quantity.setText(String.valueOf(mQuantity));
            byte[] image = bundle.getByteArray("imageByte");

            Bitmap image_bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            images.setImageBitmap(image_bitmap);

        }

        decre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuantity--;
                if (mQuantity < 0) {
                    mQuantity = 0;
                    Toast.makeText(Item_detail.this, " Out of stock", Toast.LENGTH_SHORT).show();
                } else {
                    Quantity.setText(String.valueOf(mQuantity));
                    }
            }
        });

        incre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuantity++;
                Quantity.setText(String.valueOf(mQuantity));

            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = "More order";
                String message = "Item Name: " + Name.getText() +
                        "\n Price: " + cost.getText() +
                        "\nQuantity: 10";
                String[] emails = {"ayushigrover001@gmail.com"};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, emails);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    public void deleteItem() {
        new AlertDialog.Builder(Item_detail.this)
                .setTitle("Warning!")
                .setMessage("Are you Sure?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selection = _ID + "=?";
                        String[] selectionArgs = {String.valueOf(ID)};
                        Query.getInstance(Item_detail.this).deleteEntry(TABLE, selection, selectionArgs);
                        Toast.makeText(Item_detail.this, "Deleted!!", Toast.LENGTH_SHORT).show();
                        invent.onCursorRefresh();
                        finish();
                    }

                })
                .setNegativeButton("Cancel", null)
                .show();
        invent.onCursorRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSave:
                ContentValues values = new ContentValues();
                values.put(ITEM_NAME, Name.getText().toString());
                values.put(ITEM_PRICE, Integer.parseInt(cost.getText().toString()));
                values.put(ITEM_QUANTITY, Integer.parseInt(Quantity.getText().toString()));

                String selection = _ID + "=?";
                String[] selectionArgs = {String.valueOf(ID)};
                Query.getInstance(Item_detail.this).updateData(TABLE, values, selection, selectionArgs);
                invent.onCursorRefresh();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
