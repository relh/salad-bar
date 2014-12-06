package com.saladbar.houseoftoss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class GraphicalBowl extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphical_bowl);
        
        // LISTVIEWS
        final ListView baseList = (ListView) findViewById(R.id.base);
        final ListView proteinList = (ListView) findViewById(R.id.protein);
        final ListView toppingList = (ListView) findViewById(R.id.toppings);

        // Defined Array values to show in ListView
        String[] bases = new String[]{
                "Spinach",
                "Lettuce"
        };
        String[] proteins = new String[]{
                "Egg",
                "Ham",
                "Bacon",
                "Kidney Beans",
                "Shrimp",
                "Garbanzo Beans",
                "Chicken"
        };
        String[] toppings = new String[]{
                "Applesauce",
                "Artichokes",
                "Olives",
                "Broccoli",
                "Cauliflower",
                "Cherries",
                "Tomatoes",
                "Corn",
                "Cottage Cheese",
                "Craisins",
                "Noodles",
                "Croutons",
                "Feta Cheese",
                "Gelatin",
                "Hummus",
                "Jalapenos",
                "Carrots",
                "Mushrooms",
                "Parmesan",
                "Peas",
                "Pepperoncini",
                "Beets",
                "Radishes",
                "Raisins",
                "Peppers",
                "Mozzarella",
                "Monterey Jack",
                "Cucumbers",
                "Peaches",
                "Red Onion",
                "Prunes",
                "Strawberry Whip",
                "Sunflower Seeds"
        };

        ArrayAdapter<String> baseAdapter = new ArrayAdapter<String>(this,
                R.layout.salad_item, android.R.id.text1, bases);
        ArrayAdapter<String> proteinAdapter = new ArrayAdapter<String>(this,
                R.layout.salad_item, android.R.id.text1, proteins);
        ArrayAdapter<String> toppingAdapter = new ArrayAdapter<String>(this,
                R.layout.salad_item, android.R.id.text1, toppings);

        // Assign adapter to ListView
        baseList.setAdapter(baseAdapter);
        proteinList.setAdapter(proteinAdapter);
        toppingList.setAdapter(toppingAdapter);

        // Add headers
        TextView baseHeader = (TextView) findViewById(R.id.baseHeader);
        baseHeader.setText("Bases:");
        baseHeader.setBackgroundColor(Color.RED);

        TextView proteinHeader = (TextView) findViewById(R.id.proteinHeader);
        proteinHeader.setText("Protein:");
        proteinHeader.setBackgroundColor(Color.GREEN);

        TextView toppingHeader = (TextView) findViewById(R.id.toppingHeader);
        toppingHeader.setText("Toppings:");
        toppingHeader.setBackgroundColor(Color.BLUE);

        items = new HashMap<String, ImageView>();
        
        // IMAGES
        layout = (GridLayout) findViewById(R.id.graphicLayout);

        // Add spoken toppings
        Intent intent = getIntent();
        ArrayList<String> spokenToppings = (ArrayList<String>) intent.getSerializableExtra(MainActivity.EXTRA_SALAD);
        
        Log.i("Toppings:", spokenToppings.toString());
        
        for (int i = 0; i < spokenToppings.size(); i++) {
            String itemKey = spokenToppings.get(i).substring(0,1).toUpperCase() + spokenToppings.get(i).substring(1);
        	String imageSource = itemKey.replaceAll(" ", "_").toLowerCase();
        	
        	ImageView item = new ImageView(getApplicationContext());
            item.setBackgroundResource(getResources().getIdentifier(imageSource, "drawable", getApplicationContext().getPackageName()));

            layout.addView(item);
            items.put(itemKey, item);
            
            // Highlights the voice selected options - should simplify this in the future
            for (int j = 0; j < 3; j++) {
            	switch(j) {
            	case 0:
            		for (int k = 0; k < bases.length; k++) {
            			if (bases[k].equals(spokenToppings.get(i))) {
            				baseList.setItemChecked(k, true);
            				break;
            			}
            		}
            	case 1:
            		for (int k = 0; k < proteins.length; k++) {
            			if (proteins[k].equals(spokenToppings.get(i))) {
            				proteinList.setItemChecked(k, true);
            				break;
            			}
            		}
            	case 2:
            		for (int k = 0; k < toppings.length; k++) {
            			if (toppings[k].equals(spokenToppings.get(i))) {
            				toppingList.setItemChecked(k, true);
            				break;
            			}
            		}
            	}
            }
        }
        
        saladItemClickListener listener = new saladItemClickListener();

        // ListView Item Click Listener
        baseList.setOnItemClickListener(listener);
        proteinList.setOnItemClickListener(listener);
        toppingList.setOnItemClickListener(listener);

        // SENSOR MANAGER
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private static final int SHAKE_THRESHOLD = 1000;

    private float x, y, z, last_x, last_y, last_z;
    long curTime;
    long lastUpdate = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        curTime = System.currentTimeMillis();
        if (lastUpdate == 0) { lastUpdate = curTime; }

        // only allow one update every 100ms.
        if ((curTime - lastUpdate) > 100) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;


            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

            if (speed > SHAKE_THRESHOLD) {
                Log.d("sensor", "shake detected w/ speed: " + speed);
                Toast.makeText(this, "shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
                
                // Create intent to deliver some kind of result data
                Intent result = new Intent();
                ArrayList<String> toppings = new ArrayList<String>(items.keySet());
                Log.i("toppings being returned", toppings.toString());
                result.putExtra(MainActivity.EXTRA_SALAD, toppings);
                setResult(Activity.RESULT_OK, result);
                finish();
            }
            last_x = x;
            last_y = y;
            last_z = z;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.graphical_bowl, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();            

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public HashMap<String, ImageView> items;
    public GridLayout layout;

    public class saladItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {

            // ListView Clicked item index
            //int itemPosition     = position;

            // ListView Clicked item value
            String itemKey    = (String) parent.getItemAtPosition(position);
            String imageSource = itemKey.replaceAll(" ", "_").toLowerCase();

            // Show Alert
            //Toast.makeText(getApplicationContext(),
            //       "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
            //        .show();

            if (!items.containsKey(itemKey)) {
                ImageView item = new ImageView(getApplicationContext());
                item.setBackgroundResource(getResources().getIdentifier(imageSource, "drawable", getApplicationContext().getPackageName()));

                layout.addView(item);
                items.put(itemKey, item);
            } else {
                layout.removeView(items.get(itemKey));
                items.remove(itemKey);
            }
            //Log.i("array list of items", items.toString());

            //rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
        }
    }

}
