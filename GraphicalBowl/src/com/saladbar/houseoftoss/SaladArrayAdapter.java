package com.saladbar.houseoftoss;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SaladArrayAdapter extends BaseAdapter{
    private final Context context;
    private final ArrayList<Salad> mItems = new ArrayList<Salad>();
  
	public SaladArrayAdapter(Context context) {
	    this.context = context;
	}
	
	public void add(Salad item) {
        mItems.add(item);
        notifyDataSetChanged();
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    
	    Salad salad = mItems.get(position);
	    
	    View rowView = inflater.inflate(R.layout.salad_row, parent, false);
	    ImageView saladImg = (ImageView) rowView.findViewById(R.id.salad_img);
	    TextView toppings = (TextView) rowView.findViewById(R.id.toppings);
	    TextView description = (TextView) rowView.findViewById(R.id.description);
	    
	    toppings.setText(salad.getToppingsString());
	    description.setText("Price: " +  salad.getPrice() + "\nCalories:" + salad.getCalories() + 
	    "\nReady: " + salad.getReady());

	    return rowView;
	}

	public void clearAll(){
		mItems.clear();
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
