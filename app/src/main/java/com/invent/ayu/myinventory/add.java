package com.invent.ayu.myinventory;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.invent.ayu.myinventory.Data.Contract;
import com.invent.ayu.myinventory.Data.Query;
import static com.invent.ayu.myinventory.Data.Contract.ItemEntry.TABLE;
import java.io.ByteArrayOutputStream;

public class add extends AppCompatActivity {

    ImageView imgView;
    byte[] imgBlob = null;
    Button adItem;
    int mprice;
    String mitem;
    int mquantity;
    private String LOG_TAG = invent.class.getSimpleName();
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        imgView = (ImageView) findViewById(R.id.itemImg);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent1.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(intent1, 1);
            }
        });
        adItem = (Button) findViewById(R.id.addIt);
        adItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data1) {
        Log.v(LOG_TAG, "onActivityResult Started successfully.");

        if (reqCode == REQUEST_IMAGE_CAPTURE && resCode == RESULT_OK) {
            Bundle extras = data1.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            imgView.setImageBitmap(image);
            Log.v(LOG_TAG, "Bitmap created.");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (image != null) {
                image.compress(Bitmap.CompressFormat.PNG, 0, stream);
                Log.v(LOG_TAG, "Image compressed.");
                imgBlob = stream.toByteArray();
                Log.v(LOG_TAG, "byte[] created");
            }
        }
    }

    public void saveItem() {
        EditText itName1 = (EditText) findViewById(R.id.nameEdit);
        EditText itPrice1 = (EditText) findViewById(R.id.cost_edit);

        EditText itQuantity1 = (EditText) findViewById(R.id.quantity);

        if (itPrice1.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), " Enter the price", Toast.LENGTH_SHORT).show();
            return;
        } else {
            mprice = Integer.parseInt(itPrice1.getText().toString());
        }

        if (itName1.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Name cannot be NULL", Toast.LENGTH_SHORT).show();
            return;
        } else mitem = itName1.getText().toString();

        if (itQuantity1.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Quantity needed", Toast.LENGTH_SHORT).show();
            return;
        } else {
            mquantity = Integer.parseInt(itQuantity1.getText().toString());
        }

        if (imgBlob == null) {
            Toast.makeText(getApplicationContext(), " Image required", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues value = new ContentValues();
        value.put(Contract.ItemEntry.ITEM_NAME, mitem);
        value.put(Contract.ItemEntry.ITEM_PRICE, mprice);
        value.put(Contract.ItemEntry.ITEM_QUANTITY, mquantity);
        value.put(Contract.ItemEntry.ITEM_PICTURE, imgBlob);
        Query.getInstance(getBaseContext()).insertIntoTable(TABLE, value);
        invent.onCursorRefresh();
        finish();
    }
}
