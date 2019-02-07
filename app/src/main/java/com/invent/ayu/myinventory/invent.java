package com.invent.ayu.myinventory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.invent.ayu.myinventory.Data.Query;
import com.invent.ayu.myinventory.Data.CursorAdapter;

import static com.invent.ayu.myinventory.Data.Contract.ItemEntry.TABLE;

public class invent extends AppCompatActivity {
    private static CursorAdapter adaPter;
    private static Context conText;
    ListView listView;
    Cursor curSor;

    public static void onCursorRefresh() {
        Cursor Cursor = Query.getInstance(conText).readFromTable(TABLE, null);
        adaPter.swapCursor(Cursor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invent);

        conText = this;
        listView = (ListView) findViewById(R.id.list);
        RelativeLayout emptyView = (RelativeLayout) findViewById(R.id.empty);
        listView.setEmptyView(emptyView);
        curSor = Query.getInstance(this).readFromTable(TABLE, null);
        if (curSor != null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    adaPter = new CursorAdapter(conText, curSor, 0);
                    listView.setAdapter(adaPter);
                }
            });
        } else
            emptyView.setVisibility(View.VISIBLE);

        Button addBtn = (Button) findViewById(R.id.add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(conText, add.class);
                startActivity(n);
            }
        });
    }
}
