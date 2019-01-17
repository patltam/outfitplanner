package com.example.patrick.outfitplanner;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Patrick on 12/25/2016.
 */

public class Clothes implements Parcelable {
    String image;
    boolean allowed;
    String desc;
    // 1: Hat; 2: Scarf; 3: Shirt; 4: Jacket; 5: Overalls; 6: Pants; 7: Socks; 8: Shoes
    String type;
    int[] colors = new int[19];
    int[] weathers = new int[6];
    int[] formalities = new int[5];

    public Clothes(String img, boolean allow, String description, String clothesType, int[] colorsList,
            int[] weathersList, int[] formalitiesList) {
        this.image = img;
        this.allowed = allow;
        this.desc = description;
        this.type = clothesType;
        for (int i = 0; i < colorsList.length; ++i) {
            this.colors[i] = colorsList[i];
        }
        for (int i = 0; i < weathersList.length; ++i) {
            this.weathers[i] = weathersList[i];
        }
        for (int i = 0; i < formalitiesList.length; ++i) {
            this.formalities[i] = formalitiesList[i];
        }
    }

    public String getImage() {
        return this.image;
    }

    public boolean getAllowed() { return this.allowed; }

    public String getDesc() {
        return this.desc;
    }

    public String getType() {
        return this.type;
    }

    public int[] getColors() {
        return this.colors;
    }

    public int[] getWeathers() {
        return this.weathers;
    }

    public int[] getFormalities() {
        return this.formalities;
    }

    public void setImage(String img) {
        this.image = img;
    }

    public void setAllowed(boolean allowBool) { this.allowed = allowBool; }

    public void setDesc(String description) {
        this.desc = description;
    }

    public void setType(String clothesType) {
        this.type = clothesType;
    }

    public void setColors(int[] colorsList) {
        this.colors = colorsList;
    }

    public void setWeathers(int[] weathersList) {
        this.weathers = weathersList;
    }

    public void setFormalities(int[] formalitiesList) {
        this.formalities = formalitiesList;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {

    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {

    }

    private void readObjectNoData() throws ObjectStreamException {

    }

    public Clothes(Parcel in){
        readFromParcel(in);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Clothes createFromParcel(Parcel in ) {
            return new Clothes( in );
        }

        public Clothes[] newArray(int size) {
            return new Clothes[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        if (this.allowed) {
            dest.writeInt(1);
        }
        else {
            dest.writeInt(0);
        }
        dest.writeString(this.desc);
        dest.writeString(this.type);
        dest.writeIntArray(this.colors);
        dest.writeIntArray(this.weathers);
        dest.writeIntArray(this.formalities);
    }

    private void readFromParcel(Parcel in) {
        this.image = in.readString();
        int allowedVal = in.readInt();
        if (allowedVal == 1) {
            this.allowed = true;
        }
        else {
            this.allowed = false;
        }
        this.desc = in.readString();
        this.type = in.readString();
        this.colors = in.createIntArray();
        this.weathers = in.createIntArray();
        this.formalities = in.createIntArray();
    }
}
