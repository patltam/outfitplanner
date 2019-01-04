package com.example.patrick.outfitplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;

/**
 * Created by Patrick on 12/20/2016.
 */

public class ClosetActivity extends AppCompatActivity {
    String msg = "Closet : ";
    String clothesType;
    ArrayList<Clothes> clothesList;
    ArrayList<Clothes> headList = new ArrayList<>();
    ArrayList<Clothes> neckList = new ArrayList<>();
    ArrayList<Clothes> shirtList = new ArrayList<>();
    ArrayList<Clothes> jacketList = new ArrayList<>();
    ArrayList<Clothes> fullbodyList = new ArrayList<>();
    ArrayList<Clothes> pantsList = new ArrayList<>();
    ArrayList<Clothes> socksList = new ArrayList<>();
    ArrayList<Clothes> shoesList = new ArrayList<>();
    ImageButton backButton;
    ImageButton addButton;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet_activity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            clothesType = bundle.getString("type");
            switch (clothesType) {
                case "HEAD":
                    headList = (ArrayList<Clothes>)bundle.getSerializable("list");
                    clothesList = headList;
                    break;

                case "NECK":
                    neckList = (ArrayList<Clothes>)bundle.getSerializable("list");
                    clothesList = neckList;
                    break;

                case "SHIRTS":
                    shirtList = (ArrayList<Clothes>)bundle.getSerializable("list");
                    clothesList = shirtList;
                    break;

                case "JACKETS":
                    jacketList = (ArrayList<Clothes>)bundle.getSerializable("list");
                    clothesList = jacketList;
                    break;

                case "FULL BODY":
                    fullbodyList = (ArrayList<Clothes>)bundle.getSerializable("list");
                    clothesList = fullbodyList;
                    break;

                case "PANTS":
                    pantsList = (ArrayList<Clothes>)bundle.getSerializable("list");
                    clothesList = pantsList;
                    break;

                case "SOCKS":
                    socksList = (ArrayList<Clothes>)bundle.getSerializable("list");
                    clothesList = socksList;
                    break;

                case "SHOES":
                    shoesList = (ArrayList<Clothes>)bundle.getSerializable("list");
                    clothesList = shoesList;
                    break;

                default:
                    clothesList = (ArrayList<Clothes>)bundle.getSerializable("list");
                    break;
            }
        }

        loadArray(this);
        TextView textView = (TextView)findViewById(R.id.titletext);
        textView.setText(clothesType);

        backButton = (ImageButton)findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveArray();
                Intent in = new Intent(ClosetActivity.this, MainActivity.class);
                in.putExtra("type", clothesType);
                in.putExtra("list", clothesList);
                startActivity(in);
            }
        });

        addButton = (ImageButton)findViewById(R.id.addbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ClosetActivity.this, EditClothesInfoActivity.class);
                in.putExtra("type", clothesType);
                in.putExtra("list", clothesList);
                in.putExtra("id", -1);
                startActivity(in);
            }
        });

        GridView gridview = (GridView) findViewById(R.id.closetgrid);
        gridview.setAdapter(new ImageAdapter(this, clothesList, 400));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){
                // Send intent to SingleViewActivity
                Intent in = new Intent(ClosetActivity.this, EditClothesInfoActivity.class);
                // Pass image index
                Log.d(String.valueOf(position), "The onStart() event");
                in.putExtra("id", position);
                in.putExtra("type", clothesType);
                in.putExtra("list", clothesList);
                startActivity(in);
            }
        });

        Log.d(msg, "The onCreate() event");
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
        saveArray();
        Intent in = new Intent(ClosetActivity.this, MainActivity.class);
        in.putExtra("type", clothesType);
        in.putExtra("list", clothesList);
        startActivity(in);
    }

    public boolean saveArray()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();

        switch (clothesType) {
            case "HEAD":
                mEdit1.remove("head_size");
                mEdit1.putInt("head_size", clothesList.size());
                for (int i = 0; i < clothesList.size(); ++i) {
                    mEdit1.remove("head_image" + i);
                    mEdit1.putString("head_image" + i, clothesList.get(i).getImage());
                    mEdit1.remove("head_allowed" + i);
                    mEdit1.putBoolean("head_allowed" + i, clothesList.get(i).getAllowed());
                    mEdit1.remove("head_desc" + i);
                    mEdit1.putString("head_desc" + i, clothesList.get(i).getDesc());
                    mEdit1.remove("head_type" + i);
                    mEdit1.putString("head_type" + i, clothesList.get(i).getType());
                    mEdit1.remove("head_color_size" + i);
                    mEdit1.putInt("head_color_size" + i, clothesList.get(i).getColors().length);
                    for (int k = 0; k < clothesList.get(i).getColors().length; ++k) {
                        mEdit1.remove("head_colors" + i + "_" + k);
                        mEdit1.putInt("head_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("head_weather_size" + i);
                    mEdit1.putInt("head_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < clothesList.get(i).getWeathers().length; ++k) {
                        mEdit1.remove("head_weathers" + i + "_" + k);
                        mEdit1.putInt("head_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("head_formality_size" + i);
                    mEdit1.putInt("head_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < clothesList.get(i).getFormalities().length; ++k) {
                        mEdit1.remove("head_formalities" + i + "_" + k);
                        mEdit1.putInt("head_formalities" + i + "_" + k, clothesList.get(i).getFormalities()[k]);
                    }
                }
                break;

            case "NECK":
                mEdit1.remove("neck_size");
                mEdit1.putInt("neck_size", clothesList.size());
                for (int i = 0; i < clothesList.size(); ++i) {
                    mEdit1.remove("neck_image" + i);
                    mEdit1.putString("neck_image" + i, clothesList.get(i).getImage());
                    mEdit1.remove("neck_allowed" + i);
                    mEdit1.putBoolean("neck_allowed" + i, clothesList.get(i).getAllowed());
                    mEdit1.remove("neck_desc" + i);
                    mEdit1.putString("neck_desc" + i, clothesList.get(i).getDesc());
                    mEdit1.remove("neck_type" + i);
                    mEdit1.putString("neck_type" + i, clothesList.get(i).getType());
                    mEdit1.remove("neck_color_size" + i);
                    mEdit1.putInt("neck_color_size" + i, clothesList.get(i).getColors().length);
                    for (int k = 0; k < clothesList.get(i).getColors().length; ++k) {
                        mEdit1.remove("neck_colors" + i + "_" + k);
                        mEdit1.putInt("neck_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("neck_weather_size" + i);
                    mEdit1.putInt("neck_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < clothesList.get(i).getWeathers().length; ++k) {
                        mEdit1.remove("neck_weathers" + i + "_" + k);
                        mEdit1.putInt("neck_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("neck_formality_size" + i);
                    mEdit1.putInt("neck_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < clothesList.get(i).getFormalities().length; ++k) {
                        mEdit1.remove("neck_formalities" + i + "_" + k);
                        mEdit1.putInt("neck_formalities" + i + "_" + k, clothesList.get(i).getFormalities()[k]);
                    }
                }
                break;

            case "SHIRTS":
                mEdit1.remove("shirt_size");
                mEdit1.putInt("shirt_size", clothesList.size());
                for (int i = 0; i < clothesList.size(); ++i) {
                    mEdit1.remove("shirt_image" + i);
                    mEdit1.putString("shirt_image" + i, clothesList.get(i).getImage());
                    mEdit1.remove("shirt_allowed" + i);
                    mEdit1.putBoolean("shirt_allowed" + i, clothesList.get(i).getAllowed());
                    mEdit1.remove("shirt_desc" + i);
                    mEdit1.putString("shirt_desc" + i, clothesList.get(i).getDesc());
                    mEdit1.remove("shirt_type" + i);
                    mEdit1.putString("shirt_type" + i, clothesList.get(i).getType());
                    mEdit1.remove("shirt_color_size" + i);
                    mEdit1.putInt("shirt_color_size" + i, clothesList.get(i).getColors().length);
                    for (int k = 0; k < clothesList.get(i).getColors().length; ++k) {
                        mEdit1.remove("shirt_colors" + i + "_" + k);
                        mEdit1.putInt("shirt_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("shirt_weather_size" + i);
                    mEdit1.putInt("shirt_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < clothesList.get(i).getWeathers().length; ++k) {
                        mEdit1.remove("shirt_weathers" + i + "_" + k);
                        mEdit1.putInt("shirt_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("shirt_formality_size" + i);
                    mEdit1.putInt("shirt_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < clothesList.get(i).getFormalities().length; ++k) {
                        mEdit1.remove("shirt_formalities" + i + "_" + k);
                        mEdit1.putInt("shirt_formalities" + i + "_" + k, clothesList.get(i).getFormalities()[k]);
                    }
                }
                break;

            case "JACKETS":
                mEdit1.remove("jacket_size");
                mEdit1.putInt("jacket_size", clothesList.size());
                for (int i = 0; i < clothesList.size(); ++i) {
                    mEdit1.remove("jacket_image" + i);
                    mEdit1.putString("jacket_image" + i, clothesList.get(i).getImage());
                    mEdit1.remove("jacket_allowed" + i);
                    mEdit1.putBoolean("jacket_allowed" + i, clothesList.get(i).getAllowed());
                    mEdit1.remove("jacket_desc" + i);
                    mEdit1.putString("jacket_desc" + i, clothesList.get(i).getDesc());
                    mEdit1.remove("jacket_type" + i);
                    mEdit1.putString("jacket_type" + i, clothesList.get(i).getType());
                    mEdit1.remove("jacket_color_size" + i);
                    mEdit1.putInt("jacket_color_size" + i, clothesList.get(i).getColors().length);
                    for (int k = 0; k < clothesList.get(i).getColors().length; ++k) {
                        mEdit1.remove("jacket_colors" + i + "_" + k);
                        mEdit1.putInt("jacket_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("jacket_weather_size" + i);
                    mEdit1.putInt("jacket_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < clothesList.get(i).getWeathers().length; ++k) {
                        mEdit1.remove("jacket_weathers" + i + "_" + k);
                        mEdit1.putInt("jacket_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("jacket_formality_size" + i);
                    mEdit1.putInt("jacket_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < clothesList.get(i).getFormalities().length; ++k) {
                        mEdit1.remove("jacket_formalities" + i + "_" + k);
                        mEdit1.putInt("jacket_formalities" + i + "_" + k, clothesList.get(i).getFormalities()[k]);
                    }
                }
                break;

            case "FULL BODY":
                mEdit1.remove("fullbody_size");
                mEdit1.putInt("fullbody_size", clothesList.size());
                for (int i = 0; i < clothesList.size(); ++i) {
                    mEdit1.remove("fullbody_image" + i);
                    mEdit1.putString("fullbody_image" + i, clothesList.get(i).getImage());
                    mEdit1.remove("fullbody_allowed" + i);
                    mEdit1.putBoolean("fullbody_allowed" + i, clothesList.get(i).getAllowed());
                    mEdit1.remove("fullbody_desc" + i);
                    mEdit1.putString("fullbody_desc" + i, clothesList.get(i).getDesc());
                    mEdit1.remove("fullbody_type" + i);
                    mEdit1.putString("fullbody_type" + i, clothesList.get(i).getType());
                    mEdit1.remove("fullbody_color_size" + i);
                    mEdit1.putInt("fullbody_color_size" + i, clothesList.get(i).getColors().length);
                    for (int k = 0; k < clothesList.get(i).getColors().length; ++k) {
                        mEdit1.remove("fullbody_colors" + i + "_" + k);
                        mEdit1.putInt("fullbody_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("fullbody_weather_size" + i);
                    mEdit1.putInt("fullbody_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < clothesList.get(i).getWeathers().length; ++k) {
                        mEdit1.remove("fullbody_weathers" + i + "_" + k);
                        mEdit1.putInt("fullbody_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("fullbody_formality_size" + i);
                    mEdit1.putInt("fullbody_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < clothesList.get(i).getFormalities().length; ++k) {
                        mEdit1.remove("fullbody_formalities" + i + "_" + k);
                        mEdit1.putInt("fullbody_formalities" + i + "_" + k, clothesList.get(i).getFormalities()[k]);
                    }
                }
                break;

            case "PANTS":
                mEdit1.remove("pants_size");
                mEdit1.putInt("pants_size", clothesList.size());
                for (int i = 0; i < clothesList.size(); ++i) {
                    mEdit1.remove("pants_image" + i);
                    mEdit1.putString("pants_image" + i, clothesList.get(i).getImage());
                    mEdit1.remove("pants_allowed" + i);
                    mEdit1.putBoolean("pants_allowed" + i, clothesList.get(i).getAllowed());
                    mEdit1.remove("pants_desc" + i);
                    mEdit1.putString("pants_desc" + i, clothesList.get(i).getDesc());
                    mEdit1.remove("pants_type" + i);
                    mEdit1.putString("pants_type" + i, clothesList.get(i).getType());
                    mEdit1.remove("pants_color_size" + i);
                    mEdit1.putInt("pants_color_size" + i, clothesList.get(i).getColors().length);
                    for (int k = 0; k < clothesList.get(i).getColors().length; ++k) {
                        mEdit1.remove("pants_colors" + i + "_" + k);
                        mEdit1.putInt("pants_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("pants_weather_size" + i);
                    mEdit1.putInt("pants_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < clothesList.get(i).getWeathers().length; ++k) {
                        mEdit1.remove("pants_weathers" + i + "_" + k);
                        mEdit1.putInt("pants_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("pants_formality_size" + i);
                    mEdit1.putInt("pants_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < clothesList.get(i).getFormalities().length; ++k) {
                        mEdit1.remove("pants_formalities" + i + "_" + k);
                        mEdit1.putInt("pants_formalities" + i + "_" + k, clothesList.get(i).getFormalities()[k]);
                    }
                }
                break;

            case "SOCKS":
                mEdit1.remove("socks_size");
                mEdit1.putInt("socks_size", clothesList.size());
                for (int i = 0; i < clothesList.size(); ++i) {
                    mEdit1.remove("socks_image" + i);
                    mEdit1.putString("socks_image" + i, clothesList.get(i).getImage());
                    mEdit1.remove("socks_allowed" + i);
                    mEdit1.putBoolean("socks_allowed" + i, clothesList.get(i).getAllowed());
                    mEdit1.remove("socks_desc" + i);
                    mEdit1.putString("socks_desc" + i, clothesList.get(i).getDesc());
                    mEdit1.remove("socks_type" + i);
                    mEdit1.putString("socks_type" + i, clothesList.get(i).getType());
                    mEdit1.remove("socks_color_size" + i);
                    mEdit1.putInt("socks_color_size" + i, clothesList.get(i).getColors().length);
                    for (int k = 0; k < clothesList.get(i).getColors().length; ++k) {
                        mEdit1.remove("socks_colors" + i + "_" + k);
                        mEdit1.putInt("socks_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("socks_weather_size" + i);
                    mEdit1.putInt("socks_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < clothesList.get(i).getWeathers().length; ++k) {
                        mEdit1.remove("socks_weathers" + i + "_" + k);
                        mEdit1.putInt("socks_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("socks_formality_size" + i);
                    mEdit1.putInt("socks_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < clothesList.get(i).getFormalities().length; ++k) {
                        mEdit1.remove("socks_formalities" + i + "_" + k);
                        mEdit1.putInt("socks_formalities" + i + "_" + k, clothesList.get(i).getFormalities()[k]);
                    }
                }
                break;

            case "SHOES":
                mEdit1.remove("shoes_size");
                mEdit1.putInt("shoes_size", clothesList.size());
                for (int i = 0; i < clothesList.size(); ++i) {
                    mEdit1.remove("shoes_image" + i);
                    mEdit1.putString("shoes_image" + i, clothesList.get(i).getImage());
                    mEdit1.remove("shoes_allowed" + i);
                    mEdit1.putBoolean("shoes_allowed" + i, clothesList.get(i).getAllowed());
                    mEdit1.remove("shoes_desc" + i);
                    mEdit1.putString("shoes_desc" + i, clothesList.get(i).getDesc());
                    mEdit1.remove("shoes_type" + i);
                    mEdit1.putString("shoes_type" + i, clothesList.get(i).getType());
                    mEdit1.remove("shoes_color_size" + i);
                    mEdit1.putInt("shoes_color_size" + i, clothesList.get(i).getColors().length);
                    for (int k = 0; k < clothesList.get(i).getColors().length; ++k) {
                        mEdit1.remove("shoes_colors" + i + "_" + k);
                        mEdit1.putInt("shoes_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("shoes_weather_size" + i);
                    mEdit1.putInt("shoes_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < clothesList.get(i).getWeathers().length; ++k) {
                        mEdit1.remove("shoes_weathers" + i + "_" + k);
                        mEdit1.putInt("shoes_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("shoes_formality_size" + i);
                    mEdit1.putInt("shoes_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < clothesList.get(i).getFormalities().length; ++k) {
                        mEdit1.remove("shoes_formalities" + i + "_" + k);
                        mEdit1.putInt("shoes_formalities" + i + "_" + k, clothesList.get(i).getFormalities()[k]);
                    }
                }
                break;

            default:
                break;
        }
        /*mEdit1.putInt("Status_size", clothesList.size());

        for(int i=0;i<clothesList.size();i++)
        {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, clothesList.get(i));
        }*/

        return mEdit1.commit();
    }

    public void loadArray(Context mContext)
    {
        SharedPreferences mSharedPreference1 =  PreferenceManager.getDefaultSharedPreferences(mContext);
        clothesList.clear();
        int size = 0;
        String imgPath;
        boolean allowedBool;
        String itemDesc;
        String itemType;
        int[] colorsList = new int[19];
        int[] weatherList = new int[6];
        int[] formalityList = new int[5];
        switch (clothesType) {
            case "HEAD":
                size = mSharedPreference1.getInt("head_size", 0);
                for (int i = 0; i < size; ++i) {
                    imgPath = mSharedPreference1.getString("head_image" + i, null);
                    allowedBool = mSharedPreference1.getBoolean("head_allowed" + i, false);
                    itemDesc = mSharedPreference1.getString("head_desc" + i, null);
                    itemType = mSharedPreference1.getString("head_type" + i, null);
                    for (int k = 0; k < 19; ++k) {
                        colorsList[k] = mSharedPreference1.getInt("head_colors" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 6; ++k) {
                        weatherList[k] = mSharedPreference1.getInt("head_weathers" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 5; ++k) {
                        formalityList[k] = mSharedPreference1.getInt("head_formalities" + i + "_" + k, 0);
                    }
                    Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                            weatherList, formalityList);
                    clothesList.add(createdClothes);
                }
                break;

            case "NECK":
                size = mSharedPreference1.getInt("neck_size", 0);
                for (int i = 0; i < size; ++i) {
                    imgPath = mSharedPreference1.getString("neck_image" + i, null);
                    allowedBool = mSharedPreference1.getBoolean("neck_allowed" + i, false);
                    itemDesc = mSharedPreference1.getString("neck_desc" + i, null);
                    itemType = mSharedPreference1.getString("neck_type" + i, null);
                    for (int k = 0; k < 19; ++k) {
                        colorsList[k] = mSharedPreference1.getInt("neck_colors" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 6; ++k) {
                        weatherList[k] = mSharedPreference1.getInt("neck_weathers" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 5; ++k) {
                        formalityList[k] = mSharedPreference1.getInt("neck_formalities" + i + "_" + k, 0);
                    }
                    Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                            weatherList, formalityList);
                    clothesList.add(createdClothes);
                }
                break;

            case "SHIRTS":
                size = mSharedPreference1.getInt("shirt_size", 0);
                for (int i = 0; i < size; ++i) {
                    imgPath = mSharedPreference1.getString("shirt_image" + i, null);
                    allowedBool = mSharedPreference1.getBoolean("shirt_allowed" + i, false);
                    itemDesc = mSharedPreference1.getString("shirt_desc" + i, null);
                    itemType = mSharedPreference1.getString("shirt_type" + i, null);
                    for (int k = 0; k < 19; ++k) {
                        colorsList[k] = mSharedPreference1.getInt("shirt_colors" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 6; ++k) {
                        weatherList[k] = mSharedPreference1.getInt("shirt_weathers" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 5; ++k) {
                        formalityList[k] = mSharedPreference1.getInt("shirt_formalities" + i + "_" + k, 0);
                    }
                    Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                            weatherList, formalityList);
                    clothesList.add(createdClothes);
                }
                break;

            case "JACKETS":
                size = mSharedPreference1.getInt("jacket_size", 0);
                for (int i = 0; i < size; ++i) {
                    imgPath = mSharedPreference1.getString("jacket_image" + i, null);
                    allowedBool = mSharedPreference1.getBoolean("jacket_allowed" + i, false);
                    itemDesc = mSharedPreference1.getString("jacket_desc" + i, null);
                    itemType = mSharedPreference1.getString("jacket_type" + i, null);
                    for (int k = 0; k < 19; ++k) {
                        colorsList[k] = mSharedPreference1.getInt("jacket_colors" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 6; ++k) {
                        weatherList[k] = mSharedPreference1.getInt("jacket_weathers" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 5; ++k) {
                        formalityList[k] = mSharedPreference1.getInt("jacket_formalities" + i + "_" + k, 0);
                    }
                    Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                            weatherList, formalityList);
                    clothesList.add(createdClothes);
                }
                break;

            case "FULL BODY":
                size = mSharedPreference1.getInt("fullbody_size", 0);
                for (int i = 0; i < size; ++i) {
                    imgPath = mSharedPreference1.getString("fullbody_image" + i, null);
                    allowedBool = mSharedPreference1.getBoolean("fullbody_allowed" + i, false);
                    itemDesc = mSharedPreference1.getString("fullbody_desc" + i, null);
                    itemType = mSharedPreference1.getString("fullbody_type" + i, null);
                    for (int k = 0; k < 19; ++k) {
                        colorsList[k] = mSharedPreference1.getInt("fullbody_colors" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 6; ++k) {
                        weatherList[k] = mSharedPreference1.getInt("fullbody_weathers" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 5; ++k) {
                        formalityList[k] = mSharedPreference1.getInt("fullbody_formalities" + i + "_" + k, 0);
                    }
                    Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                            weatherList, formalityList);
                    clothesList.add(createdClothes);
                }
                break;

            case "PANTS":
                size = mSharedPreference1.getInt("pants_size", 0);
                for (int i = 0; i < size; ++i) {
                    imgPath = mSharedPreference1.getString("pants_image" + i, null);
                    allowedBool = mSharedPreference1.getBoolean("pants_allowed" + i, false);
                    itemDesc = mSharedPreference1.getString("pants_desc" + i, null);
                    itemType = mSharedPreference1.getString("pants_type" + i, null);
                    for (int k = 0; k < 19; ++k) {
                        colorsList[k] = mSharedPreference1.getInt("pants_colors" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 6; ++k) {
                        weatherList[k] = mSharedPreference1.getInt("pants_weathers" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 5; ++k) {
                        formalityList[k] = mSharedPreference1.getInt("pants_formalities" + i + "_" + k, 0);
                    }
                    Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                            weatherList, formalityList);
                    clothesList.add(createdClothes);
                }
                break;

            case "SOCKS":
                size = mSharedPreference1.getInt("socks_size", 0);
                for (int i = 0; i < size; ++i) {
                    imgPath = mSharedPreference1.getString("socks_image" + i, null);
                    allowedBool = mSharedPreference1.getBoolean("socks_allowed" + i, false);
                    itemDesc = mSharedPreference1.getString("socks_desc" + i, null);
                    itemType = mSharedPreference1.getString("socks_type" + i, null);
                    for (int k = 0; k < 19; ++k) {
                        colorsList[k] = mSharedPreference1.getInt("socks_colors" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 6; ++k) {
                        weatherList[k] = mSharedPreference1.getInt("socks_weathers" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 5; ++k) {
                        formalityList[k] = mSharedPreference1.getInt("socks_formalities" + i + "_" + k, 0);
                    }
                    Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                            weatherList, formalityList);
                    clothesList.add(createdClothes);
                }
                break;

            case "SHOES":
                size = mSharedPreference1.getInt("shoes_size", 0);
                for (int i = 0; i < size; ++i) {
                    imgPath = mSharedPreference1.getString("shoes_image" + i, null);
                    allowedBool = mSharedPreference1.getBoolean("shoes_allowed" + i, false);
                    itemDesc = mSharedPreference1.getString("shoes_desc" + i, null);
                    itemType = mSharedPreference1.getString("shoes_type" + i, null);
                    for (int k = 0; k < 19; ++k) {
                        colorsList[k] = mSharedPreference1.getInt("shoes_colors" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 6; ++k) {
                        weatherList[k] = mSharedPreference1.getInt("shoes_weathers" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 5; ++k) {
                        formalityList[k] = mSharedPreference1.getInt("shoes_formalities" + i + "_" + k, 0);
                    }
                    Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                            weatherList, formalityList);
                    clothesList.add(createdClothes);
                }
                break;

            default:
                break;
        }
    }
}
