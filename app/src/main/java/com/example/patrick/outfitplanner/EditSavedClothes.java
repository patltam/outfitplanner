package com.example.patrick.outfitplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.ArrayList;

public class EditSavedClothes extends AppCompatActivity {
    String msg = "EditSavedClothes : ";
    ImageButton backButton;
    ArrayList<Clothes> clothesOptionsList = new ArrayList<>();
    ArrayList<Clothes> clothesList = new ArrayList<>();
    int listID;
    int position;
    String clothesType;
    int[] colorsList = new int[19];
    int[] weatherList = new int[6];
    int[] formalityList = new int[5];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(msg, "The onCreate() event");
        setContentView(R.layout.editsavedclothes);

        Bundle bundle = getIntent().getExtras();
        clothesList = (ArrayList<Clothes>)bundle.getSerializable("list");
        listID = bundle.getInt("id");
        clothesType = bundle.getString("type");
        switch (clothesType) {
            case "head":
                position = 0;
                break;

            case "neck":
                position = 1;
                break;

            case "shirt":
                position = 2;
                break;

            case "jacket":
                position = 3;
                break;

            case "fullbody":
                position = 4;
                break;

            case "pants":
                position = 5;
                break;

            case "socks":
                position = 6;
                break;

            case "shoes":
                position = 7;
                break;

            default:
                position = 0;
                break;
        }

        loadArray(this, clothesType);
        clothesOptionsList.add(0, new Clothes("ALL",true,"","ALL",colorsList,weatherList,formalityList));
        clothesOptionsList.add(1, new Clothes("NONE",false,"","NONE",colorsList,weatherList,formalityList));

        backButton = (ImageButton)findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(EditSavedClothes.this, EditSavedCloset.class);
                in.putExtra("id", listID);
                in.putExtra("list", clothesList);
                startActivity(in);
            }
        });

        GridView gridview = (GridView) findViewById(R.id.clothesoptionsgrid);
        gridview.setAdapter(new ImageAdapter(this, clothesOptionsList, 400));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int gridPosition, long id){
                // Send intent to SingleViewActivity
                Intent in = new Intent(EditSavedClothes.this, EditSavedCloset.class);
                // Pass image index
                //Log.d(String.valueOf(gridPosition), "The onStart() event");
                clothesList.set(position, clothesOptionsList.get(gridPosition));
                in.putExtra("id", listID);
                in.putExtra("list", clothesList);
                startActivity(in);
            }
        });
    }

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        //Log.d(msg, "The onStart() event");
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        //Log.d(msg, "The onResume() event");
    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        //Log.d(msg, "The onPause() event");
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        //Log.d(msg, "The onStop() event");
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.d(msg, "The onDestroy() event");
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(EditSavedClothes.this, EditSavedCloset.class);
        in.putExtra("id", listID);
        in.putExtra("list", clothesList);
        startActivity(in);
    }

    // load the closet for the specified clothes type
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
        size = mSharedPreference1.getInt(clothesType + "_size", 0);
        for (int i = 0; i < size; ++i) {
            imgPath = mSharedPreference1.getString(clothesType + "_image" + i, null);
            allowedBool = mSharedPreference1.getBoolean(clothesType + "_allowed" + i, false);
            itemDesc = mSharedPreference1.getString(clothesType + "_desc" + i, null);
            itemType = mSharedPreference1.getString(clothesType + "_type" + i, null);
            for (int k = 0; k < 19; ++k) {
                colorsList[k] = mSharedPreference1.getInt(clothesType + "_colors" + i + "_" + k, 0);
            }
            for (int k = 0; k < 6; ++k) {
                weatherList[k] = mSharedPreference1.getInt(clothesType + "_weathers" + i + "_" + k, 0);
            }
            for (int k = 0; k < 5; ++k) {
                formalityList[k] = mSharedPreference1.getInt(clothesType + "_formalities" + i + "_" + k, 0);
            }
            Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                    weatherList, formalityList);
            clothesOptionsList.add(createdClothes);
        }
    }
}
