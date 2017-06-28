package com.example.patrick.outfitplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Patrick on 3/28/2017.
 */

public class ClothesFilterActivity extends AppCompatActivity {
    String msg = "ClothesFilter : ";
    ImageButton backButton;
    ArrayList<Clothes> filterClothesList = new ArrayList<>();
    ArrayList<Clothes> clothesList = new ArrayList<>();
    int position;
    String clothesType;
    int[] colorsList = new int[19];
    int[] weatherList = new int[6];
    int[] formalityList = new int[5];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(msg, "The onCreate() event");
        setContentView(R.layout.clothesfilter_activity);

        Bundle bundle = getIntent().getExtras();
        clothesList = (ArrayList<Clothes>) bundle.getSerializable("list");
        position = bundle.getInt("id");
        switch (position) {
            case 0:
                clothesType = "HATS";
                break;

            case 1:
                clothesType = "SCARVES";
                break;

            case 2:
                clothesType = "SHIRTS";
                break;

            case 3:
                clothesType = "JACKETS";
                break;

            case 4:
                clothesType = "OVERALLS";
                break;

            case 5:
                clothesType = "PANTS";
                break;

            case 6:
                clothesType = "SOCKS";
                break;

            case 7:
                clothesType = "SHOES";
                break;

            default:
                break;
        }
        loadArray(this, clothesType);
        filterClothesList.add(0, new Clothes("NONE",false,"","NONE",colorsList,weatherList,formalityList));
        TextView textView = (TextView) findViewById(R.id.filtertitletext);
        textView.setText("FILTER: " + clothesType);

        backButton = (ImageButton)findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(ClothesFilterActivity.this, FilterActivity.class);
                startActivity(in);
            }
        });

        GridView gridview = (GridView) findViewById(R.id.clothesfiltergrid);
        gridview.setAdapter(new ImageAdapter(this, filterClothesList, 400));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int gridPosition, long id){
                // Send intent to SingleViewActivity
                Intent in = new Intent(ClothesFilterActivity.this, FilterActivity.class);
                // Pass image index
                Log.d(String.valueOf(gridPosition), "The onStart() event");
                clothesList.set(position, filterClothesList.get(gridPosition));
                in.putExtra("list", clothesList);
                startActivity(in);
            }
        });
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
        Intent in = new Intent(ClothesFilterActivity.this, FilterActivity.class);
        startActivity(in);
    }

    public void loadArray(Context mContext, String clothesType)
    {
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
        int size = 0;
        String imgPath;
        boolean allowedBool;
        String itemDesc;
        String itemType;
        int[] colorsList = new int[19];
        int[] weatherList = new int[6];
        int[] formalityList = new int[5];
        switch (clothesType) {
            case "HATS":
                filterClothesList.clear();
                size = mSharedPreference1.getInt("hat_size", 0);
                for (int i = 0; i < size; ++i) {
                    imgPath = mSharedPreference1.getString("hat_image" + i, null);
                    allowedBool = mSharedPreference1.getBoolean("hat_allowed" + i, false);
                    itemDesc = mSharedPreference1.getString("hat_desc" + i, null);
                    itemType = mSharedPreference1.getString("hat_type" + i, null);
                    for (int k = 0; k < 19; ++k) {
                        colorsList[k] = mSharedPreference1.getInt("hat_colors" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 6; ++k) {
                        weatherList[k] = mSharedPreference1.getInt("hat_weathers" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 5; ++k) {
                        formalityList[k] = mSharedPreference1.getInt("hat_formalities" + i + "_" + k, 0);
                    }
                    Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                            weatherList, formalityList);
                    filterClothesList.add(createdClothes);
                }
                break;

            case "SCARVES":
                filterClothesList.clear();
                size = mSharedPreference1.getInt("scarf_size", 0);
                for (int i = 0; i < size; ++i) {
                    imgPath = mSharedPreference1.getString("scarf_image" + i, null);
                    allowedBool = mSharedPreference1.getBoolean("scarf_allowed" + i, false);
                    itemDesc = mSharedPreference1.getString("scarf_desc" + i, null);
                    itemType = mSharedPreference1.getString("scarf_type" + i, null);
                    for (int k = 0; k < 19; ++k) {
                        colorsList[k] = mSharedPreference1.getInt("scarf_colors" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 6; ++k) {
                        weatherList[k] = mSharedPreference1.getInt("scarf_weathers" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 5; ++k) {
                        formalityList[k] = mSharedPreference1.getInt("scarf_formalities" + i + "_" + k, 0);
                    }
                    Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                            weatherList, formalityList);
                    filterClothesList.add(createdClothes);
                }
                break;

            case "SHIRTS":
                filterClothesList.clear();
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
                    filterClothesList.add(createdClothes);
                }
                break;

            case "JACKETS":
                filterClothesList.clear();
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
                    filterClothesList.add(createdClothes);
                }
                break;

            case "OVERALLS":
                filterClothesList.clear();
                size = mSharedPreference1.getInt("overall_size", 0);
                for (int i = 0; i < size; ++i) {
                    imgPath = mSharedPreference1.getString("overall_image" + i, null);
                    allowedBool = mSharedPreference1.getBoolean("overall_allowed" + i, false);
                    itemDesc = mSharedPreference1.getString("overall_desc" + i, null);
                    itemType = mSharedPreference1.getString("overall_type" + i, null);
                    for (int k = 0; k < 19; ++k) {
                        colorsList[k] = mSharedPreference1.getInt("overall_colors" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 6; ++k) {
                        weatherList[k] = mSharedPreference1.getInt("overall_weathers" + i + "_" + k, 0);
                    }
                    for (int k = 0; k < 5; ++k) {
                        formalityList[k] = mSharedPreference1.getInt("overall_formalities" + i + "_" + k, 0);
                    }
                    Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                            weatherList, formalityList);
                    filterClothesList.add(createdClothes);
                }
                break;

            case "PANTS":
                filterClothesList.clear();
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
                    filterClothesList.add(createdClothes);
                }
                break;

            case "SOCKS":
                filterClothesList.clear();
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
                    filterClothesList.add(createdClothes);
                }
                break;

            case "SHOES":
                filterClothesList.clear();
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
                    filterClothesList.add(createdClothes);
                }
                break;

            default:
                break;
        }
    }
}
