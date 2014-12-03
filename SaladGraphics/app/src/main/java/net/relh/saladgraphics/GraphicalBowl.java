package net.relh.saladgraphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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


public class GraphicalBowl extends Activity {

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
                "Romaine Lettuce",
                "Iceberg Lettuce"
        };
        String[] proteins = new String[]{
                "Diced Egg",
                "Diced Ham",
                "Bacon Bits",
                "Kidney Beans",
                "Shrimp",
                "Garbanzo Beans",
                "Chicken Breast"
        };
        String[] toppings = new String[]{
                "Applesauce",
                "Artichokes",
                "Black Olives",
                "Broccoli",
                "Cauliflower",
                "Cherry Tomatoes",
                "Corn",
                "Cottage Cheese",
                "Craisins",
                "Crispy Noodles",
                "Croutons",
                "Feta Cheese",
                "Gelatin",
                "Gelatin Whip",
                "Green Olives",
                "Hummus",
                "Jalapenos",
                "Matchstick Carrots",
                "Mushrooms",
                "Parmesan Cheese",
                "Peas",
                "Pepperoncini",
                "Pickled Beets",
                "Radishes",
                "Raisins",
                "Roasted Red Pepper",
                "Shredded Mozzarella",
                "Shredded Monterey Jack Cheese",
                "Sliced Cucumber",
                "Sliced Peaches",
                "Sliced Red Onion",
                "Stewed Prunes",
                "Strawberry Whip",
                "Sunflower Seeds"
        };

        ArrayAdapter<String> baseAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, bases);
        ArrayAdapter<String> proteinAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, proteins);
        ArrayAdapter<String> toppingAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, toppings);

        // Assign adapter to ListView
        baseList.setAdapter(baseAdapter);
        proteinList.setAdapter(proteinAdapter);
        toppingList.setAdapter(toppingAdapter);

        // Add headers
        final TextView baseHeader = (TextView) findViewById(R.id.baseHeader);
        baseHeader.setText("Bases:");
        baseHeader.setBackgroundColor(Color.RED);

        TextView proteinHeader = (TextView) findViewById(R.id.proteinHeader);
        proteinHeader.setText("Protein:");
        proteinHeader.setBackgroundColor(Color.GREEN);

        TextView toppingHeader = (TextView) findViewById(R.id.toppingHeader);
        toppingHeader.setText("Toppings:");
        toppingHeader.setBackgroundColor(Color.BLUE);

        items = new ArrayList<ImageView>();
        saladItemClickListener listener = new saladItemClickListener();

        // ListView Item Click Listener
        baseList.setOnItemClickListener(listener);
        proteinList.setOnItemClickListener(listener);
        toppingList.setOnItemClickListener(listener);

        // IMAGES
        layout = (GridLayout) findViewById(R.id.graphicLayout);

    }


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

    public ArrayList<ImageView> items;
    public GridLayout layout;

    public class saladItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {

            // ListView Clicked item index
            int itemPosition     = position;

            // ListView Clicked item value
            String  itemValue    = (String) parent.getItemAtPosition(position);

            itemValue = itemValue.replaceAll(" ", "_").toLowerCase();

            // Show Alert
            Toast.makeText(getApplicationContext(),
                    "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                    .show();

            ImageView item = new ImageView(getApplicationContext());
            item.setBackgroundResource(getResources().getIdentifier(itemValue, "drawable", getApplicationContext().getPackageName()));

            layout.addView(item);
            items.add(item);

            Log.i("array list of items", items.toString());

            //rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
        }
    }

}
