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

public class SavedCloset extends AppCompatActivity {
    String msg = "Saved Closet : ";
    ArrayList<Clothes> savedList = new ArrayList<>();
    ImageButton backButton;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savedcloset_activity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

        }

        loadList(this);

        backButton = (ImageButton)findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(SavedCloset.this, MainActivity.class);
                in.putExtra("type", "SAVED");
                in.putExtra("list", savedList);
                startActivity(in);
            }
        });

        GridView gridview = (GridView) findViewById(R.id.savedgrid);
        gridview.setAdapter(new ImageAdapter(this, savedList, 200));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){
                // Send intent to SingleViewActivity
                Intent in = new Intent(SavedCloset.this, EditSavedCloset.class);
                // Pass image index
                Log.d(String.valueOf(position), "The onStart() event");
                int index = position % 8;
                ArrayList<Clothes> clothesList = new ArrayList<>();
                switch (index) {
                    case 0:
                        for (int i = 0; i < 8; ++i) {
                            clothesList.add(savedList.get(position + i));
                        }
                        break;

                    case 1:
                        position = position - 1;
                        for (int i = 0; i < 8; ++i) {
                            clothesList.add(savedList.get(position + i));
                        }
                        break;

                    case 2:
                        position = position - 2;
                        for (int i = 0; i < 8; ++i) {
                            clothesList.add(savedList.get(position + i));
                        }
                        break;

                    case 3:
                        position = position - 3;
                        for (int i = 0; i < 8; ++i) {
                            clothesList.add(savedList.get(position + i));
                        }
                        break;

                    case 4:
                        position = position - 4;
                        for (int i = 0; i < 8; ++i) {
                            clothesList.add(savedList.get(position + i));
                        }
                        break;

                    case 5:
                        position = position - 5;
                        for (int i = 0; i < 8; ++i) {
                            clothesList.add(savedList.get(position + i));
                        }
                        break;

                    case 6:
                        position = position - 6;
                        for (int i = 0; i < 8; ++i) {
                            clothesList.add(savedList.get(position + i));
                        }
                        break;

                    case 7:
                        position = position - 7;
                        for (int i = 0; i < 8; ++i) {
                            clothesList.add(savedList.get(position + i));
                        }
                        break;

                    default:
                        break;
                }
                in.putExtra("id", position/8);
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
        Intent in = new Intent(SavedCloset.this, MainActivity.class);
        in.putExtra("type", "SAVED");
        in.putExtra("list", savedList);
        startActivity(in);
    }

    public void loadList(Context mContext) {
        SharedPreferences mSharedPreference1 =  PreferenceManager.getDefaultSharedPreferences(mContext);
        int size = mSharedPreference1.getInt("save_size", 0);
        String imgPath;
        boolean allowedBool;
        String itemDesc;
        String itemType;
        int[] colorsList = new int[19];
        int[] weatherList = new int[6];
        int[] formalityList = new int[5];
        String type = "";
        for (int listID = 0; listID < size; ++listID) {
            for (int i = 0; i < 8; ++i) {
                switch (i) {
                    case 0:
                        type = "head";
                        break;

                    case 1:
                        type = "neck";
                        break;

                    case 2:
                        type = "shirt";
                        break;

                    case 3:
                        type = "jacket";
                        break;

                    case 4:
                        type = "fullbody";
                        break;

                    case 5:
                        type = "pants";
                        break;

                    case 6:
                        type = "socks";
                        break;

                    case 7:
                        type = "shoes";
                        break;

                    default:
                        break;
                }
                imgPath = mSharedPreference1.getString("saved_" + type + "_image" + listID, null);
                allowedBool = mSharedPreference1.getBoolean("saved_" + type + "_allowed" + listID, false);
                itemDesc = mSharedPreference1.getString("saved_" + type + "_desc" + listID, null);
                itemType = mSharedPreference1.getString("saved_" + type + "_type" + listID, null);
                for (int k = 0; k < 19; ++k) {
                    colorsList[k] = mSharedPreference1.getInt("saved_" + type + "_colors" + listID + "_" + k, 0);
                }
                for (int k = 0; k < 6; ++k) {
                    weatherList[k] = mSharedPreference1.getInt("saved_" + type + "_weathers" + listID + "_" + k, 0);
                }
                for (int k = 0; k < 5; ++k) {
                    formalityList[k] = mSharedPreference1.getInt("saved_" + type + "_formalities" + listID + "_" + k, 0);
                }
                Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                        weatherList, formalityList);
                savedList.add(createdClothes);
            }
        }
    }
}
