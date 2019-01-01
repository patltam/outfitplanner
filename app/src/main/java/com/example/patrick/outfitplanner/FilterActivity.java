package com.example.patrick.outfitplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Patrick on 2/25/2017.
 */

public class FilterActivity extends AppCompatActivity {

    String msg = "Filters : ";
    ImageButton backButton;
    int[] colorsList = new int[19];
    int[] weatherList = new int[6];
    int[] formalityList = new int[5];
    ArrayList<CheckBox> checkboxColorList = new ArrayList<>(19);
    ArrayList<CheckBox> checkboxWeatherList = new ArrayList<>(6);
    ArrayList<CheckBox> checkboxFormalityList = new ArrayList<>(5);
    ArrayList<Clothes> clothesList = new ArrayList<>(8);
    ImageView clothesImg;
    String img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_select);

        clothesImg = (ImageView)findViewById(R.id.noneimage);
        clothesImg.setDrawingCacheEnabled(true);
        clothesImg.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        clothesImg.layout(0, 0,
                clothesImg.getMeasuredWidth(), clothesImg.getMeasuredHeight());
        clothesImg.buildDrawingCache(true);
        clothesImg.setDrawingCacheEnabled(false);
        img = saveImage(((BitmapDrawable)clothesImg.getDrawable()).getBitmap(),"NONE");

        clothesImg = (ImageView)findViewById(R.id.allimage);
        clothesImg.setDrawingCacheEnabled(true);
        clothesImg.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        clothesImg.layout(0, 0,
                clothesImg.getMeasuredWidth(), clothesImg.getMeasuredHeight());
        clothesImg.buildDrawingCache(true);
        clothesImg.setDrawingCacheEnabled(false);
        img = saveImage(((BitmapDrawable)clothesImg.getDrawable()).getBitmap(),"ALL");

        Clothes empty = new Clothes("ALL",false,"","ALL",colorsList,weatherList,formalityList);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            clothesList = (ArrayList<Clothes>)bundle.getSerializable("list");
        }
        else if (loadArray(this)) {
            // do nothing, the non-empty array is loaded
        }
        else {
            clothesList.add(0, empty);
            clothesList.add(1, empty);
            clothesList.add(2, empty);
            clothesList.add(3, empty);
            clothesList.add(4, empty);
            clothesList.add(5, empty);
            clothesList.add(6, empty);
            clothesList.add(7, empty);
        }

        backButton = (ImageButton)findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(FilterActivity.this, MainActivity.class);
                startActivity(in);
            }
        });

        for (int i = 0; i < 19; ++i) {
            checkboxColorList.add((CheckBox)findViewById(R.id.blackbox));
            colorsList[i] = 1;
        }
        checkboxColorList.add(0, (CheckBox)findViewById(R.id.blackbox));
        checkboxColorList.add(1, (CheckBox)findViewById(R.id.graybox));
        checkboxColorList.add(2, (CheckBox)findViewById(R.id.whitebox));
        checkboxColorList.add(3, (CheckBox)findViewById(R.id.beigebox));
        checkboxColorList.add(4, (CheckBox)findViewById(R.id.taupebox));
        checkboxColorList.add(5, (CheckBox)findViewById(R.id.brownbox));
        checkboxColorList.add(6, (CheckBox)findViewById(R.id.olivebox));
        checkboxColorList.add(7, (CheckBox)findViewById(R.id.redbox));
        checkboxColorList.add(8, (CheckBox)findViewById(R.id.vermilionbox));
        checkboxColorList.add(9, (CheckBox)findViewById(R.id.orangebox));
        checkboxColorList.add(10, (CheckBox)findViewById(R.id.amberbox));
        checkboxColorList.add(11, (CheckBox)findViewById(R.id.yellowbox));
        checkboxColorList.add(12, (CheckBox)findViewById(R.id.chartreusebox));
        checkboxColorList.add(13, (CheckBox)findViewById(R.id.greenbox));
        checkboxColorList.add(14, (CheckBox)findViewById(R.id.tealbox));
        checkboxColorList.add(15, (CheckBox)findViewById(R.id.bluebox));
        checkboxColorList.add(16, (CheckBox)findViewById(R.id.violetbox));
        checkboxColorList.add(17, (CheckBox)findViewById(R.id.purplebox));
        checkboxColorList.add(18, (CheckBox)findViewById(R.id.magentabox));

        for (int i = 0; i < 6; ++i) {
            checkboxWeatherList.add((CheckBox)findViewById(R.id.hotbox));
            weatherList[i] = 1;
        }
        checkboxWeatherList.add(0, (CheckBox)findViewById(R.id.hotbox));
        checkboxWeatherList.add(1, (CheckBox)findViewById(R.id.warmbox));
        checkboxWeatherList.add(2, (CheckBox)findViewById(R.id.coldbox));
        checkboxWeatherList.add(3, (CheckBox)findViewById(R.id.rainybox));
        checkboxWeatherList.add(4, (CheckBox)findViewById(R.id.snowybox));

        for (int i = 0; i < 5; ++i) {
            checkboxFormalityList.add((CheckBox)findViewById(R.id.formalbox));
            formalityList[i] = 1;
        }
        checkboxFormalityList.add(0, (CheckBox)findViewById(R.id.formalbox));
        checkboxFormalityList.add(1, (CheckBox)findViewById(R.id.semiformalbox));
        checkboxFormalityList.add(2, (CheckBox)findViewById(R.id.informalbox));
        checkboxFormalityList.add(3, (CheckBox)findViewById(R.id.casualbox));

        GridView gridview = (GridView) findViewById(R.id.filtergrid);
        gridview.setAdapter(new ImageAdapter(this, clothesList, 200));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){
                // Send intent to SingleViewActivity
                Intent in = new Intent(FilterActivity.this, ClothesFilterActivity.class);
                // Pass image index
                Log.d(String.valueOf(position), "The onStart() event");
                in.putExtra("id", position);
                in.putExtra("list", clothesList);
                startActivity(in);
            }
        });

        loadFilters(this);
    }

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");

        for (int i = 0; i < 19; ++i) {
            if (checkboxColorList.get(i).isChecked()) {
                colorsList[i] = 1;
            }
            else {
                colorsList[i] = 0;
            }
        }
        for (int i = 0; i < 6; ++i) {
            if (checkboxWeatherList.get(i).isChecked()) {
                weatherList[i] = 1;
            }
            else {
                weatherList[i] = 0;
            }
        }
        for (int i = 0; i < 5; ++i) {
            if (checkboxFormalityList.get(i).isChecked()) {
                formalityList[i] = 1;
            }
            else {
                formalityList[i] = 0;
            }
        }
        saveFilters();
        saveArray();
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }

    @Override
    public void onBackPressed() {
        saveFilters();
        saveArray();
        Intent in = new Intent(FilterActivity.this, MainActivity.class);
        startActivity(in);
    }

    public boolean saveFilters() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();
        for (int i = 0; i < 19; ++i) {
            mEdit1.remove("filter_colors" + i);
            mEdit1.putInt("filter_colors" + i, colorsList[i]);
        }
        for (int i = 0; i < 6; ++i) {
            mEdit1.remove("filter_weathers" + i);
            mEdit1.putInt("filter_weathers" + i, weatherList[i]);
        }
        for (int i = 0; i < 5; ++i) {
            mEdit1.remove("filter_formalities" + i);
            mEdit1.putInt("filter_formalities" + i, formalityList[i]);
        }
        return mEdit1.commit();
    }

    public void loadFilters(Context mContext) {
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
        for (int i = 0; i < 19; ++i) {
            colorsList[i] = mSharedPreference1.getInt("filter_colors" + i, 1);
            if (colorsList[i] == 0) {
                checkboxColorList.get(i).setChecked(false);
            }
            else {
                checkboxColorList.get(i).setChecked(true);
            }
        }
        for (int i = 0; i < 6; ++i) {
            weatherList[i] = mSharedPreference1.getInt("filter_weathers" + i, 1);
            if (weatherList[i] == 0) {
                checkboxWeatherList.get(i).setChecked(false);
            }
            else {
                checkboxWeatherList.get(i).setChecked(true);
            }
        }
        for (int i = 0; i < 5; ++i) {
            formalityList[i] = mSharedPreference1.getInt("filter_formalities" + i, 1);
            if (formalityList[i] == 0) {
                checkboxFormalityList.get(i).setChecked(false);
            }
            else {
                checkboxFormalityList.get(i).setChecked(true);
            }
        }
    }

    public String saveImage(Bitmap bitmap, String str) {
        String fileName = str; //no .png or .jpg needed; make sure all descs are unique
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    public boolean saveArray() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();

        mEdit1.remove("filter_size");
        mEdit1.putInt("filter_size", clothesList.size());
        for (int i = 0; i < clothesList.size(); ++i) {
            mEdit1.remove("filter_image" + i);
            mEdit1.putString("filter_image" + i, clothesList.get(i).getImage());
            mEdit1.remove("filter_allowed" + i);
            mEdit1.putBoolean("filter_allowed" + i, clothesList.get(i).getAllowed());
            mEdit1.remove("filter_desc" + i);
            mEdit1.putString("filter_desc" + i, clothesList.get(i).getDesc());
            mEdit1.remove("filter_type" + i);
            mEdit1.putString("filter_type" + i, clothesList.get(i).getType());
            mEdit1.remove("filter_color_size" + i);
            mEdit1.putInt("filter_color_size" + i, clothesList.get(i).getColors().length);
            for (int k = 0; k < clothesList.get(i).getColors().length; ++k) {
                mEdit1.remove("filter_colors" + i + "_" + k);
                mEdit1.putInt("filter_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
            }
            mEdit1.remove("filter_weather_size" + i);
            mEdit1.putInt("filter_weather_size" + i, clothesList.get(i).getWeathers().length);
            for (int k = 0; k < clothesList.get(i).getWeathers().length; ++k) {
                mEdit1.remove("filter_weathers" + i + "_" + k);
                mEdit1.putInt("filter_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
            }
            mEdit1.remove("filter_formality_size" + i);
            mEdit1.putInt("filter_formality_size" + i, clothesList.get(i).getFormalities().length);
            for (int k = 0; k < clothesList.get(i).getFormalities().length; ++k) {
                mEdit1.remove("filter_formalities" + i + "_" + k);
                mEdit1.putInt("filter_formalities" + i + "_" + k, clothesList.get(i).getFormalities()[k]);
            }
        }

        return mEdit1.commit();
    }

    public boolean loadArray(Context mContext) {
        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(mContext);
        clothesList.clear();
        int size = 0;
        String imgPath;
        boolean allowedBool;
        String itemDesc;
        String itemType;
        int[] colorsList = new int[19];
        int[] weatherList = new int[6];
        int[] formalityList = new int[5];

        size = mSharedPreference1.getInt("filter_size", 0);
        for (int i = 0; i < size; ++i) {
            imgPath = mSharedPreference1.getString("filter_image" + i, null);
            allowedBool = mSharedPreference1.getBoolean("filter_allowed" + i, false);
            itemDesc = mSharedPreference1.getString("filter_desc" + i, null);
            itemType = mSharedPreference1.getString("filter_type" + i, null);
            for (int k = 0; k < 19; ++k) {
                colorsList[k] = mSharedPreference1.getInt("filter_colors" + i + "_" + k, 0);
            }
            for (int k = 0; k < 6; ++k) {
                weatherList[k] = mSharedPreference1.getInt("filter_weathers" + i + "_" + k, 0);
            }
            for (int k = 0; k < 5; ++k) {
                formalityList[k] = mSharedPreference1.getInt("filter_formalities" + i + "_" + k, 0);
            }
            Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                    weatherList, formalityList);
            clothesList.add(createdClothes);
        }
        if (size == 0) {
            return false;
        }
        return true;
    }
}
