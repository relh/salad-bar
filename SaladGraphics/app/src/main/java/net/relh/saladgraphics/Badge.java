package net.relh.saladgraphics;

import android.graphics.Bitmap;

/**
 * Created by aqwin on 11/3/14.
 */
public class Badge  {

    public String place;
    public String country;
    public Bitmap flag;

    public Badge(String place, String country, Bitmap flag) {
        super();
        this.place = place;
        this.country = country;
        this.flag = flag;
    }

    @Override
    public boolean equals(Object object) {
        boolean areWe = false;

        if (object != null && object instanceof Badge) {
            areWe = this.place.equals(((Badge) object).place);
        }

        return areWe;
    }

    @Override
    public String toString() {
        return "(" + place + "," + country + ")";
    }
}