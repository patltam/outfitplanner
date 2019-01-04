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
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class EditSavedCloset extends AppCompatActivity {
    String msg = "Edit Saved Closet : ";
    boolean confirm = false;
    boolean delete = false;
    int listID;
    ArrayList<Clothes> savedList = new ArrayList<>();
    Button cancelButton;
    Button deleteButton;
    Button finishButton;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editsavedcloset);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            listID = bundle.getInt("id");
            savedList = (ArrayList<Clothes>)bundle.getSerializable("list");
        }

        cancelButton = (Button)findViewById(R.id.cancelbutton);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(EditSavedCloset.this, SavedCloset.class);
                startActivity(in);
            }
        });

        deleteButton = (Button)findViewById(R.id.deletebutton);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                delete = true;
                Intent in = new Intent(EditSavedCloset.this, SavedCloset.class);
                startActivity(in);
            }
        });

        finishButton = (Button)findViewById(R.id.finishbutton);
        finishButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                confirm = true;
                Intent in = new Intent(EditSavedCloset.this, SavedCloset.class);
                startActivity(in);
            }
        });

        GridView gridview = (GridView) findViewById(R.id.savedrow);
        gridview.setAdapter(new ImageAdapter(this, savedList, 200));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){
                // Send intent to SingleViewActivity
                Intent in = new Intent(EditSavedCloset.this, EditSavedClothes.class);
                // Pass image index
                Log.d(String.valueOf(position), "The onStart() event");
                String type = "";
                switch (position) {
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
                in.putExtra("id", listID);
                in.putExtra("type", type);
                in.putExtra("list", savedList);
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
        if (confirm) {
            saveList(EditSavedCloset.this);
        } else if (delete) {
            deleteList(EditSavedCloset.this);
        }
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
        Intent in = new Intent(EditSavedCloset.this, SavedCloset.class);
        startActivity(in);
    }

    // loads full saved outfits list, replaces chosen list, saves new resulting list
    public void saveList(Context mContext) {
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
        ArrayList<Clothes> fullList = new ArrayList<>();
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
                fullList.add(createdClothes);
            }
        }
        for (int i = 0; i < 8; ++i) {
            fullList.set(listID * 8 + i, savedList.get(i));
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.remove("save_size");
        mEdit1.putInt("save_size", fullList.size()/8); // amount of "lists" (8 items each)
        for (int listID = 0; listID < fullList.size()/8; ++listID) {
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
                mEdit1.remove("saved_" + type + "_image" + listID);
                mEdit1.putString("saved_" + type + "_image" + listID, fullList.get(listID*8+i).getImage());
                mEdit1.remove("saved_" + type + "_allowed" + listID);
                mEdit1.putBoolean("saved_" + type + "_allowed" + listID, fullList.get(listID*8+i).getAllowed());
                mEdit1.remove("saved_" + type + "_desc" + listID);
                mEdit1.putString("saved_" + type + "_desc" + listID, fullList.get(listID*8+i).getDesc());
                mEdit1.remove("saved_" + type + "_type" + listID);
                mEdit1.putString("saved_" + type + "_type" + listID, fullList.get(listID*8+i).getType());
                mEdit1.remove("saved_" + type + "_color_size" + listID);
                mEdit1.putInt("saved_" + type + "_color_size" + listID, fullList.get(listID*8+i).getColors().length);
                for (int k = 0; k < fullList.get(listID*8+i).getColors().length; ++k) {
                    mEdit1.remove("saved_" + type + "_colors" + listID + "_" + k);
                    mEdit1.putInt("saved_" + type + "_colors" + listID + "_" + k, fullList.get(listID*8+i).getColors()[k]);
                }
                mEdit1.remove("saved_" + type + "_weather_size" + listID);
                mEdit1.putInt("saved_" + type + "_weather_size" + listID, fullList.get(listID*8+i).getWeathers().length);
                for (int k = 0; k < fullList.get(listID*8+i).getWeathers().length; ++k) {
                    mEdit1.remove("saved_" + type + "_weathers" + listID + "_" + k);
                    mEdit1.putInt("saved_" + type + "_weathers" + listID + "_" + k, fullList.get(listID*8+i).getWeathers()[k]);
                }
                mEdit1.remove("saved_" + type + "_formality_size" + listID);
                mEdit1.putInt("saved_" + type + "_formality_size" + listID, fullList.get(listID*8+i).getFormalities().length);
                for (int k = 0; k < fullList.get(listID*8+i).getFormalities().length; ++k) {
                    mEdit1.remove("saved_" + type + "_formalities" + listID + "_" + k);
                    mEdit1.putInt("saved_" + type + "_formalities" + listID + "_" + k, fullList.get(listID*8+i).getFormalities()[k]);
                }
            }
        }
        mEdit1.commit();
    }

    // loads full saved outfits list, deletes chosen list, saves new resulting list
    public void deleteList(Context mContext) {
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
        ArrayList<Clothes> fullList = new ArrayList<>();
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
                fullList.add(createdClothes);
            }
        }
        for (int i = 7; i >= 0; --i) {
            fullList.remove(listID * 8 + i);
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.remove("save_size");
        mEdit1.putInt("save_size", fullList.size()/8); // amount of "lists" (8 items each)
        for (int listID = 0; listID < fullList.size()/8; ++listID) {
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
                mEdit1.remove("saved_" + type + "_image" + listID);
                mEdit1.putString("saved_" + type + "_image" + listID, fullList.get(listID*8+i).getImage());
                mEdit1.remove("saved_" + type + "_allowed" + listID);
                mEdit1.putBoolean("saved_" + type + "_allowed" + listID, fullList.get(listID*8+i).getAllowed());
                mEdit1.remove("saved_" + type + "_desc" + listID);
                mEdit1.putString("saved_" + type + "_desc" + listID, fullList.get(listID*8+i).getDesc());
                mEdit1.remove("saved_" + type + "_type" + listID);
                mEdit1.putString("saved_" + type + "_type" + listID, fullList.get(listID*8+i).getType());
                mEdit1.remove("saved_" + type + "_color_size" + listID);
                mEdit1.putInt("saved_" + type + "_color_size" + listID, fullList.get(listID*8+i).getColors().length);
                for (int k = 0; k < fullList.get(listID*8+i).getColors().length; ++k) {
                    mEdit1.remove("saved_" + type + "_colors" + listID + "_" + k);
                    mEdit1.putInt("saved_" + type + "_colors" + listID + "_" + k, fullList.get(listID*8+i).getColors()[k]);
                }
                mEdit1.remove("saved_" + type + "_weather_size" + listID);
                mEdit1.putInt("saved_" + type + "_weather_size" + listID, fullList.get(listID*8+i).getWeathers().length);
                for (int k = 0; k < fullList.get(listID*8+i).getWeathers().length; ++k) {
                    mEdit1.remove("saved_" + type + "_weathers" + listID + "_" + k);
                    mEdit1.putInt("saved_" + type + "_weathers" + listID + "_" + k, fullList.get(listID*8+i).getWeathers()[k]);
                }
                mEdit1.remove("saved_" + type + "_formality_size" + listID);
                mEdit1.putInt("saved_" + type + "_formality_size" + listID, fullList.get(listID*8+i).getFormalities().length);
                for (int k = 0; k < fullList.get(listID*8+i).getFormalities().length; ++k) {
                    mEdit1.remove("saved_" + type + "_formalities" + listID + "_" + k);
                    mEdit1.putInt("saved_" + type + "_formalities" + listID + "_" + k, fullList.get(listID*8+i).getFormalities()[k]);
                }
            }
        }
        mEdit1.commit();
    }
}
