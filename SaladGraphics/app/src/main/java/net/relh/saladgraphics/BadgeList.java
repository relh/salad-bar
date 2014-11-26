package net.relh.saladgraphics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class BadgeList extends Activity {

    public static boolean active = false;
    public static boolean started = false;
    public static ListView listView;
    public static ArrayList<Badge> data;
    public static BadgeAdapter adapter;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge_list);
        started = true;
        context = getApplicationContext();
        data = new ArrayList<Badge>();
        adapter = new BadgeAdapter(this, R.layout.badge_item, data);
        listView = (ListView) findViewById(R.id.list);

        Button newPlace = new Button(getBaseContext());
        newPlace.setText("Add new place");
        newPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SetLocation.class);
                startActivity(intent);
            }
        });

        listView.addFooterView(newPlace);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        active = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        active = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.badge_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            data = new ArrayList<Badge>();
            adapter = new BadgeAdapter(this, R.layout.badge_item, data);
            listView.setAdapter(adapter);
        }

        return super.onOptionsItemSelected(item);
    }
}
