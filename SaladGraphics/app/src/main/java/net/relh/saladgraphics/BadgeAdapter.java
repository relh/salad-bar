package net.relh.saladgraphics;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BadgeAdapter extends ArrayAdapter<Badge> {

    Context context;
    int layoutResourceId;
    ArrayList<Badge> data = null;

    public BadgeAdapter(Context context, int layoutResourceId, ArrayList<Badge> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        BadgeHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new BadgeHolder();
            holder.flag = (ImageView)row.findViewById(R.id.flag);
            holder.place = (TextView)row.findViewById(R.id.place);
            holder.country = (TextView)row.findViewById(R.id.country);

            row.setTag(holder);
        }
        else
        {
            holder = (BadgeHolder)row.getTag();
        }

        Badge badge = data.get(position);
        holder.place.setText("Place: " + badge.place);
        holder.country.setText("Country: " + badge.country);
        holder.flag.setImageBitmap(badge.flag);

        return row;
    }

    static class BadgeHolder {
        TextView place;
        TextView country;
        ImageView flag;
    }

}