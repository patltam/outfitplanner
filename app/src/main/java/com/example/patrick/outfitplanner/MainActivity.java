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
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    String msg = "Main : ";
    Button headButton;
    Button neckButton;
    Button shirtButton;
    Button jacketButton;
    Button fullbodyButton;
    Button pantsButton;
    Button socksButton;
    Button shoesButton;
    Button savedButton;
    Button filterButton;
    Button generateButton;
    Button randomButton;
    Button saveButton;
    ImageButton leftButton;
    ImageButton rightButton;
    ArrayList<Clothes> headList = new ArrayList<>();
    ArrayList<Clothes> neckList = new ArrayList<>();
    ArrayList<Clothes> shirtList = new ArrayList<>();
    ArrayList<Clothes> jacketList = new ArrayList<>();
    ArrayList<Clothes> fullbodyList = new ArrayList<>();
    ArrayList<Clothes> pantsList = new ArrayList<>();
    ArrayList<Clothes> socksList = new ArrayList<>();
    ArrayList<Clothes> shoesList = new ArrayList<>();
    ArrayList<Clothes> headList2 = new ArrayList<>();
    ArrayList<Clothes> neckList2 = new ArrayList<>();
    ArrayList<Clothes> shirtList2 = new ArrayList<>();
    ArrayList<Clothes> jacketList2 = new ArrayList<>();
    ArrayList<Clothes> fullbodyList2 = new ArrayList<>();
    ArrayList<Clothes> pantsList2 = new ArrayList<>();
    ArrayList<Clothes> socksList2 = new ArrayList<>();
    ArrayList<Clothes> shoesList2 = new ArrayList<>();
    ArrayList<Clothes> triadicList = new ArrayList<>();
    ArrayList<Clothes> splitCompList = new ArrayList<>();
    ArrayList<Clothes> compList = new ArrayList<>();
    ArrayList<Clothes> bloodlineList = new ArrayList<>();
    ArrayList<Clothes> analagousList = new ArrayList<>();
    ArrayList<Clothes> achromaticList = new ArrayList<>();
    ArrayList<Clothes> monochromaticList = new ArrayList<>();
    String clothesType = "";
    ArrayList<Clothes> clothesList = new ArrayList<>();
    ArrayList<ArrayList<Clothes>> listArray = new ArrayList<>(7);
    ArrayList<ArrayList<Clothes>> loadedList = new ArrayList<>(7);
    ArrayList<ArrayList<Clothes>> loadedList2 = new ArrayList<>(7);
    int listNumber = 0;
    ArrayList<Clothes> filtersList = new ArrayList<>();
    int[] emptyColorsList = new int[19];
    int[] emptyWeatherList = new int[6];
    int[] emptyFormalityList = new int[5];
    int[] filterColorsList = new int[19];
    int[] filterWeatherList = new int[6];
    int[] filterFormalityList = new int[5];
    Map<Integer,ArrayList<Integer>> color1List = new HashMap<>(); // (category, eligible list)
    Map<Integer,ArrayList<Integer>> color2List = new HashMap<>();
    Map<Integer,ArrayList<Integer>> color3List = new HashMap<>();
    int color1 = -1;
    int color2 = -1;
    int color3 = -1;
    boolean hasColor1 = false;
    boolean hasColor2 = false;
    boolean hasColor3 = false;
    int color1count = 0;
    int color2count = 0;
    int color3count = 0;
    int category;
    Map<Integer,Integer> typeCount = new TreeMap<>(); // (size of list, category)
    Map<Integer,Integer> fullTypeList = new HashMap<>();
    int randNum;
    TextView typeText;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeText = (TextView)findViewById(R.id.typetext);
        typeText.setText("");

        ImageView clothesImg = (ImageView)findViewById(R.id.emptyimage);
        clothesImg.setDrawingCacheEnabled(true);
        clothesImg.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        clothesImg.layout(0, 0,
                clothesImg.getMeasuredWidth(), clothesImg.getMeasuredHeight());
        clothesImg.buildDrawingCache(true);
        clothesImg.setDrawingCacheEnabled(false);
        saveImage(((BitmapDrawable)clothesImg.getDrawable()).getBitmap());

        Clothes empty = new Clothes("NONE",false,"","NONE",emptyColorsList,emptyWeatherList,emptyFormalityList);
        clothesList.add(0, empty);
        clothesList.add(1, empty);
        clothesList.add(2, empty);
        clothesList.add(3, empty);
        clothesList.add(4, empty);
        clothesList.add(5, empty);
        clothesList.add(6, empty);
        clothesList.add(7, empty);
        listArray.add(0, clothesList);
        listArray.add(1, clothesList);
        listArray.add(2, clothesList);
        listArray.add(3, clothesList);
        listArray.add(4, clothesList);
        listArray.add(5, clothesList);
        listArray.add(6, clothesList);
        loadedList.add(0, clothesList);
        loadedList.add(1, clothesList);
        loadedList.add(2, clothesList);
        loadedList.add(3, clothesList);
        loadedList.add(4, clothesList);
        loadedList.add(5, clothesList);
        loadedList.add(6, clothesList);
        loadedList.add(7, clothesList);
        loadedList2.add(0, clothesList);
        loadedList2.add(1, clothesList);
        loadedList2.add(2, clothesList);
        loadedList2.add(3, clothesList);
        loadedList2.add(4, clothesList);
        loadedList2.add(5, clothesList);
        loadedList2.add(6, clothesList);
        loadedList2.add(7, clothesList);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            clothesType = bundle.getString("type");
            switch (clothesType) {
                case "RESULT":
                    listNumber = bundle.getInt("number");
                    listArray = (ArrayList<ArrayList<Clothes>>)bundle.getSerializable("list");
                    clothesList = listArray.get(listNumber);
                    if (listNumber == 0) {
                        typeText.setText("Triadic");
                    }
                    else if (listNumber == 1) {
                        typeText.setText("Split Comp.");
                    }
                    else if (listNumber == 2) {
                        typeText.setText("Complementary");
                    }
                    else if (listNumber == 3) {
                        typeText.setText("Shared Bloodline");
                    }
                    else if (listNumber == 4) {
                        typeText.setText("Analagous");
                    }
                    else if (listNumber == 5) {
                        typeText.setText("Achromatic");
                    }
                    else {
                        typeText.setText("Monochromatic");
                    }
                    break;

                case "RANDOM":
                    listNumber = bundle.getInt("number");
                    listArray = (ArrayList<ArrayList<Clothes>>)bundle.getSerializable("list");
                    clothesList = listArray.get(listNumber);
                    if (listNumber == 0) {
                        typeText.setText("Random #1");
                    }
                    else if (listNumber == 1) {
                        typeText.setText("Random #2");
                    }
                    else if (listNumber == 2) {
                        typeText.setText("Random #3");
                    }
                    else if (listNumber == 3) {
                        typeText.setText("Random #4");
                    }
                    else if (listNumber == 4) {
                        typeText.setText("Random #5");
                    }
                    else if (listNumber == 5) {
                        typeText.setText("Random #6");
                    }
                    else {
                        typeText.setText("Random #7");
                    }
                    break;

                default:
                    break;
            }
        }

        GridView gridview = (GridView) findViewById(R.id.resultgrid);
        gridview.setAdapter(new ImageAdapter(this, clothesList, 200));

        headButton = (Button)findViewById(R.id.hatbutton);
        headButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, ClosetActivity.class);
                in.putExtra("type", "HEAD");
                in.putExtra("list", headList);
                startActivity(in);
            }
        });

        neckButton = (Button)findViewById(R.id.scarfbutton);
        neckButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, ClosetActivity.class);
                in.putExtra("type", "NECK");
                in.putExtra("list", neckList);
                startActivity(in);
            }
        });

        shirtButton = (Button)findViewById(R.id.shirtbutton);
        shirtButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, ClosetActivity.class);
                in.putExtra("type", "SHIRTS");
                in.putExtra("list", shirtList);
                startActivity(in);
            }
        });

        jacketButton = (Button)findViewById(R.id.jacketbutton);
        jacketButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, ClosetActivity.class);
                in.putExtra("type", "JACKETS");
                in.putExtra("list", jacketList);
                startActivity(in);
            }
        });

        fullbodyButton = (Button)findViewById(R.id.overallsbutton);
        fullbodyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, ClosetActivity.class);
                in.putExtra("type", "FULL BODY");
                in.putExtra("list", fullbodyList);
                startActivity(in);
            }
        });

        pantsButton = (Button)findViewById(R.id.pantsbutton);
        pantsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, ClosetActivity.class);
                in.putExtra("type", "PANTS");
                in.putExtra("list", pantsList);
                startActivity(in);
            }
        });

        socksButton = (Button)findViewById(R.id.socksbutton);
        socksButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, ClosetActivity.class);
                in.putExtra("type", "SOCKS");
                in.putExtra("list", socksList);
                startActivity(in);
            }
        });

        shoesButton = (Button)findViewById(R.id.shoesbutton);
        shoesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, ClosetActivity.class);
                in.putExtra("type", "SHOES");
                in.putExtra("list", shoesList);
                startActivity(in);
            }
        });

        filterButton = (Button)findViewById(R.id.filtersbutton);
        filterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(in);
            }
        });

        generateButton = (Button)findViewById(R.id.generatebutton);
        generateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                loadFilters(MainActivity.this);
                generate();

                intent.putExtra("type", "RESULT");
                intent.putExtra("list", listArray);
                intent.putExtra("number", 0);
                startActivity(intent);
            }
        });

        randomButton = (Button)findViewById(R.id.randombutton);
        randomButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                randomGenerate();

                intent.putExtra("type","RANDOM");
                intent.putExtra("list", listArray);
                intent.putExtra("number", 0);
                startActivity(intent);
            }
        });

        rightButton = (ImageButton)findViewById(R.id.rightbutton);
        rightButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listNumber != -1) {
                    if (listNumber == 6) {
                        listNumber = 0;
                    }
                    else {
                        listNumber = listNumber + 1;
                    }
                    Intent intent = getIntent();
                    finish();
                    overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    if (clothesType.equals("RANDOM")) {
                        intent.putExtra("type", "RANDOM");
                    }
                    else if (clothesType.equals("RESULT")) {
                        intent.putExtra("type", "RESULT");
                    }
                    intent.putExtra("list", listArray);
                    intent.putExtra("number", listNumber);
                    startActivity(intent);
                }
            }
        });

        leftButton = (ImageButton)findViewById(R.id.leftbutton);
        leftButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listNumber != -1) {
                    if (listNumber == 0) {
                        listNumber = 6;
                    }
                    else {
                        listNumber = listNumber - 1;
                    }
                    Intent intent = getIntent();
                    finish();
                    overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    if (clothesType.equals("RANDOM")) {
                        intent.putExtra("type", "RANDOM");
                    }
                    else if (clothesType.equals("RESULT")) {
                        intent.putExtra("type", "RESULT");
                    }
                    intent.putExtra("list", listArray);
                    intent.putExtra("number", listNumber);
                    startActivity(intent);
                }
            }
        });

        saveButton = (Button)findViewById(R.id.savebutton);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveList(listNumber, MainActivity.this);
            }
        });

        savedButton = (Button)findViewById(R.id.savedbutton);
        savedButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, SavedCloset.class);
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
        // do nothing
    }

    public void generate() {
        loadArray(this, "HEAD");
        loadArray(this, "NECK");
        loadArray(this, "SHIRTS");
        loadArray(this, "JACKETS");
        loadArray(this, "FULL BODY");
        loadArray(this, "PANTS");
        loadArray(this, "SOCKS");
        loadArray(this, "SHOES");
        loadedList.set(0,headList);
        loadedList.set(1,neckList);
        loadedList.set(2,shirtList);
        loadedList.set(3,jacketList);
        loadedList.set(4,fullbodyList);
        loadedList.set(5,pantsList);
        loadedList.set(6,socksList);
        loadedList.set(7,shoesList);
        narrowArray("HEAD");
        narrowArray("NECK");
        narrowArray("SHIRTS");
        narrowArray("JACKETS");
        narrowArray("FULL BODY");
        narrowArray("PANTS");
        narrowArray("SOCKS");
        narrowArray("SHOES");
        loadedList2.set(0,headList2);
        loadedList2.set(1,neckList2);
        loadedList2.set(2,shirtList2);
        loadedList2.set(3,jacketList2);
        loadedList2.set(4,fullbodyList2);
        loadedList2.set(5,pantsList2);
        loadedList2.set(6,socksList2);
        loadedList2.set(7,shoesList2);
        generateOutfit(4, 8, .25, .25, 1, 0); // Triadic
        generateOutfit(5, 7, .25, .25, 1, 1); // Split Complementary
        generateOutfit(6, 0, .375, 1, 1, 2); // Complementary
        generateOutfit(2, 0, .375, 1, 1, 3); // Shared Bloodline
        generateOutfit(1, 11, .25, .25, 1, 4); // Analogous
        generateOutfit(0, 0, .125, 0, 0, 5); // Achromatic
        generateOutfit(0, 0, 1, 1, 1, 6); // Monochromatic
    }

    public void randomGenerate() {
        loadArrayRandom(this, "HEAD");
        loadArrayRandom(this, "NECK");
        loadArrayRandom(this, "SHIRTS");
        loadArrayRandom(this, "JACKETS");
        loadArrayRandom(this, "FULL BODY");
        loadArrayRandom(this, "PANTS");
        loadArrayRandom(this, "SOCKS");
        loadArrayRandom(this, "SHOES");
        Random rand = new Random();
        for (int i = 0; i < 7; ++i) {
            if (headList.size() > 0) {
                randNum = rand.nextInt(headList.size());
                clothesList.set(0, headList.get(randNum));
            }
            if (neckList.size() > 0) {
                randNum = rand.nextInt(neckList.size());
                clothesList.set(1, neckList.get(randNum));
            }
            if (shirtList.size() > 0) {
                randNum = rand.nextInt(shirtList.size());
                clothesList.set(2, shirtList.get(randNum));
            }
            if (jacketList.size() > 0) {
                randNum = rand.nextInt(jacketList.size());
                clothesList.set(3, jacketList.get(randNum));
            }
            if (fullbodyList.size() > 0) {
                randNum = rand.nextInt(fullbodyList.size());
                clothesList.set(4, fullbodyList.get(randNum));
            }
            if (pantsList.size() > 0) {
                randNum = rand.nextInt(pantsList.size());
                clothesList.set(5, pantsList.get(randNum));
            }
            if (socksList.size() > 0) {
                randNum = rand.nextInt(socksList.size());
                clothesList.set(6, socksList.get(randNum));
            }
            if (shoesList.size() > 0) {
                randNum = rand.nextInt(shoesList.size());
                clothesList.set(7, shoesList.get(randNum));
            }
            switch (i) {
                case 0:
                    ArrayList<Clothes> copyList1 = new ArrayList<>(clothesList);
                    listArray.set(i, copyList1);
                    break;

                case 1:
                    ArrayList<Clothes> copyList2 = new ArrayList<>(clothesList);
                    listArray.set(i, copyList2);
                    break;

                case 2:
                    ArrayList<Clothes> copyList3 = new ArrayList<>(clothesList);
                    listArray.set(i, copyList3);
                    break;

                case 3:
                    ArrayList<Clothes> copyList4 = new ArrayList<>(clothesList);
                    listArray.set(i, copyList4);
                    break;

                case 4:
                    ArrayList<Clothes> copyList5 = new ArrayList<>(clothesList);
                    listArray.set(i, copyList5);
                    break;

                case 5:
                    ArrayList<Clothes> copyList6 = new ArrayList<>(clothesList);
                    listArray.set(i, copyList6);
                    break;

                case 6:
                    ArrayList<Clothes> copyList7 = new ArrayList<>(clothesList);
                    listArray.set(i, copyList7);
                    break;

                default:
                    break;
            }
        }
    }

    // saves current list to Saved Outfits
    public boolean saveList(int listNumber, Context mContext) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();
        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(mContext);

        int listID = mSharedPreference1.getInt("save_size", 0);
        String type = "";
        ArrayList<Clothes> list = new ArrayList<>(listArray.get(listNumber));

        for (int i = 0; i < list.size(); ++i) {
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
            mEdit1.putString("saved_" + type + "_image" + listID, list.get(i).getImage());
            mEdit1.remove("saved_" + type + "_allowed" + listID);
            mEdit1.putBoolean("saved_" + type + "_allowed" + listID, list.get(i).getAllowed());
            mEdit1.remove("saved_" + type + "_desc" + listID);
            mEdit1.putString("saved_" + type + "_desc" + listID, list.get(i).getDesc());
            mEdit1.remove("saved_" + type + "_type" + listID);
            mEdit1.putString("saved_" + type + "_type" + listID, list.get(i).getType());
            mEdit1.remove("saved_" + type + "_color_size" + listID);
            mEdit1.putInt("saved_" + type + "_color_size" + listID, list.get(i).getColors().length);
            for (int k = 0; k < list.get(i).getColors().length; ++k) {
                mEdit1.remove("saved_" + type + "_colors" + listID + "_" + k);
                mEdit1.putInt("saved_" + type + "_colors" + listID + "_" + k, list.get(i).getColors()[k]);
            }
            mEdit1.remove("saved_" + type + "_weather_size" + listID);
            mEdit1.putInt("saved_" + type + "_weather_size" + listID, list.get(i).getWeathers().length);
            for (int k = 0; k < list.get(i).getWeathers().length; ++k) {
                mEdit1.remove("saved_" + type + "_weathers" + listID + "_" + k);
                mEdit1.putInt("saved_" + type + "_weathers" + listID + "_" + k, list.get(i).getWeathers()[k]);
            }
            mEdit1.remove("saved_" + type + "_formality_size" + listID);
            mEdit1.putInt("saved_" + type + "_formality_size" + listID, list.get(i).getFormalities().length);
            for (int k = 0; k < list.get(i).getFormalities().length; ++k) {
                mEdit1.remove("saved_" + type + "_formalities" + listID + "_" + k);
                mEdit1.putInt("saved_" + type + "_formalities" + listID + "_" + k, list.get(i).getFormalities()[k]);
            }
        }
        mEdit1.remove("save_size");
        mEdit1.putInt("save_size", listID + 1);
        return mEdit1.commit();
    }

    public void loadFilters(Context mContext) {
        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(mContext);
        for (int i = 0; i < 19; ++i) {
            filterColorsList[i] = mSharedPreference1.getInt("filter_colors" + i, 1);
        }
        for (int i = 0; i < 6; ++i) {
            filterWeatherList[i] = mSharedPreference1.getInt("filter_weathers" + i, 1);
        }
        for (int i = 0; i < 5; ++i) {
            filterFormalityList[i] = mSharedPreference1.getInt("filter_formalities" + i, 1);
        }
        filtersList.clear();
        int size = 0;
        String imgPath;
        boolean allowedBool;
        String itemDesc;
        String itemType;
        int[] filterClothesColorsList = new int[19];
        int[] filterClothesWeatherList = new int[6];
        int[] filterClothesFormalityList = new int[5];

        size = mSharedPreference1.getInt("filter_size", 0);
        for (int i = 0; i < size; ++i) {
            imgPath = mSharedPreference1.getString("filter_image" + i, null);
            allowedBool = mSharedPreference1.getBoolean("filter_allowed" + i, false);
            itemDesc = mSharedPreference1.getString("filter_desc" + i, null);
            itemType = mSharedPreference1.getString("filter_type" + i, null);
            for (int k = 0; k < 19; ++k) {
                filterClothesColorsList[k] = mSharedPreference1.getInt("filter_colors" + i + "_" + k, 0);
            }
            for (int k = 0; k < 6; ++k) {
                filterClothesWeatherList[k] = mSharedPreference1.getInt("filter_weathers" + i + "_" + k, 0);
            }
            for (int k = 0; k < 5; ++k) {
                filterClothesFormalityList[k] = mSharedPreference1.getInt("filter_formalities" + i + "_" + k, 0);
            }
            Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, filterClothesColorsList,
                    filterClothesWeatherList, filterClothesFormalityList);
            filtersList.add(createdClothes);
        }
    }

    public void loadArray(Context mContext, String clothesType)
    {
        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(mContext);
        int size = 0;
        String imgPath;
        boolean allowedBool;
        String itemDesc;
        String itemType;
        int[] colorsList = new int[19];
        int[] weatherList = new int[6];
        int[] formalityList = new int[5];
        boolean match = false;
        switch (clothesType) {
            case "HEAD":
                headList.clear();
                size = mSharedPreference1.getInt("head_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("head_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("head_image" + i, null);
                        itemDesc = mSharedPreference1.getString("head_desc" + i, null);
                        itemType = mSharedPreference1.getString("head_type" + i, null);
                        for (int k = 0; k < 19; ++k) {
                            colorsList[k] = mSharedPreference1.getInt("head_colors" + i + "_" + k, 0);
                            if (colorsList[k] == 1 && filterColorsList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 6; ++k) {
                            weatherList[k] = mSharedPreference1.getInt("head_weathers" + i + "_" + k, 0);
                            if (weatherList[k] == 1 && filterWeatherList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 5; ++k) {
                            formalityList[k] = mSharedPreference1.getInt("head_formalities" + i + "_" + k, 0);
                            if (formalityList[k] == 1 && filterFormalityList[k] == 1) {
                                match = true;
                            }
                        }
                        if (match) {
                            Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                                    weatherList, formalityList);
                            headList.add(createdClothes);
                        }
                    }
                }
                break;

            case "NECK":
                neckList.clear();
                size = mSharedPreference1.getInt("neck_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("neck_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("neck_image" + i, null);
                        itemDesc = mSharedPreference1.getString("neck_desc" + i, null);
                        itemType = mSharedPreference1.getString("neck_type" + i, null);
                        for (int k = 0; k < 19; ++k) {
                            colorsList[k] = mSharedPreference1.getInt("neck_colors" + i + "_" + k, 0);
                            if (colorsList[k] == 1 && filterColorsList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 6; ++k) {
                            weatherList[k] = mSharedPreference1.getInt("neck_weathers" + i + "_" + k, 0);
                            if (weatherList[k] == 1 && filterWeatherList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 5; ++k) {
                            formalityList[k] = mSharedPreference1.getInt("neck_formalities" + i + "_" + k, 0);
                            if (formalityList[k] == 1 && filterFormalityList[k] == 1) {
                                match = true;
                            }
                        }
                        if (match) {
                            Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                                    weatherList, formalityList);
                            neckList.add(createdClothes);
                        }
                    }
                }
                break;

            case "SHIRTS":
                shirtList.clear();
                size = mSharedPreference1.getInt("shirt_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("shirt_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("shirt_image" + i, null);
                        itemDesc = mSharedPreference1.getString("shirt_desc" + i, null);
                        itemType = mSharedPreference1.getString("shirt_type" + i, null);
                        for (int k = 0; k < 19; ++k) {
                            colorsList[k] = mSharedPreference1.getInt("shirt_colors" + i + "_" + k, 0);
                            if (colorsList[k] == 1 && filterColorsList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 6; ++k) {
                            weatherList[k] = mSharedPreference1.getInt("shirt_weathers" + i + "_" + k, 0);
                            if (weatherList[k] == 1 && filterWeatherList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 5; ++k) {
                            formalityList[k] = mSharedPreference1.getInt("shirt_formalities" + i + "_" + k, 0);
                            if (formalityList[k] == 1 && filterFormalityList[k] == 1) {
                                match = true;
                            }
                        }
                        if (match) {
                            Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                                    weatherList, formalityList);
                            shirtList.add(createdClothes);
                        }
                    }
                }
                break;

            case "JACKETS":
                jacketList.clear();
                size = mSharedPreference1.getInt("jacket_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("jacket_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("jacket_image" + i, null);
                        itemDesc = mSharedPreference1.getString("jacket_desc" + i, null);
                        itemType = mSharedPreference1.getString("jacket_type" + i, null);
                        for (int k = 0; k < 19; ++k) {
                            colorsList[k] = mSharedPreference1.getInt("jacket_colors" + i + "_" + k, 0);
                            if (colorsList[k] == 1 && filterColorsList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 6; ++k) {
                            weatherList[k] = mSharedPreference1.getInt("jacket_weathers" + i + "_" + k, 0);
                            if (weatherList[k] == 1 && filterWeatherList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 5; ++k) {
                            formalityList[k] = mSharedPreference1.getInt("jacket_formalities" + i + "_" + k, 0);
                            if (formalityList[k] == 1 && filterFormalityList[k] == 1) {
                                match = true;
                            }
                        }
                        if (match) {
                            Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                                    weatherList, formalityList);
                            jacketList.add(createdClothes);
                        }
                    }
                }
                break;

            case "FULL BODY":
                fullbodyList.clear();
                size = mSharedPreference1.getInt("fullbody_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("fullbody_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("fullbody_image" + i, null);
                        itemDesc = mSharedPreference1.getString("fullbody_desc" + i, null);
                        itemType = mSharedPreference1.getString("fullbody_type" + i, null);
                        for (int k = 0; k < 19; ++k) {
                            colorsList[k] = mSharedPreference1.getInt("fullbody_colors" + i + "_" + k, 0);
                            if (colorsList[k] == 1 && filterColorsList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 6; ++k) {
                            weatherList[k] = mSharedPreference1.getInt("fullbody_weathers" + i + "_" + k, 0);
                            if (weatherList[k] == 1 && filterWeatherList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 5; ++k) {
                            formalityList[k] = mSharedPreference1.getInt("fullbody_formalities" + i + "_" + k, 0);
                            if (formalityList[k] == 1 && filterFormalityList[k] == 1) {
                                match = true;
                            }
                        }
                        if (match) {
                            Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                                    weatherList, formalityList);
                            fullbodyList.add(createdClothes);
                        }
                    }
                }
                break;

            case "PANTS":
                pantsList.clear();
                size = mSharedPreference1.getInt("pants_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("pants_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("pants_image" + i, null);
                        itemDesc = mSharedPreference1.getString("pants_desc" + i, null);
                        itemType = mSharedPreference1.getString("pants_type" + i, null);
                        for (int k = 0; k < 19; ++k) {
                            colorsList[k] = mSharedPreference1.getInt("pants_colors" + i + "_" + k, 0);
                            if (colorsList[k] == 1 && filterColorsList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 6; ++k) {
                            weatherList[k] = mSharedPreference1.getInt("pants_weathers" + i + "_" + k, 0);
                            if (weatherList[k] == 1 && filterWeatherList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 5; ++k) {
                            formalityList[k] = mSharedPreference1.getInt("pants_formalities" + i + "_" + k, 0);
                            if (formalityList[k] == 1 && filterFormalityList[k] == 1) {
                                match = true;
                            }
                        }
                        if (match) {
                            Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                                    weatherList, formalityList);
                            pantsList.add(createdClothes);
                        }
                    }
                }
                break;

            case "SOCKS":
                socksList.clear();
                size = mSharedPreference1.getInt("socks_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("socks_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("socks_image" + i, null);
                        itemDesc = mSharedPreference1.getString("socks_desc" + i, null);
                        itemType = mSharedPreference1.getString("socks_type" + i, null);
                        for (int k = 0; k < 19; ++k) {
                            colorsList[k] = mSharedPreference1.getInt("socks_colors" + i + "_" + k, 0);
                            if (colorsList[k] == 1 && filterColorsList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 6; ++k) {
                            weatherList[k] = mSharedPreference1.getInt("socks_weathers" + i + "_" + k, 0);
                            if (weatherList[k] == 1 && filterWeatherList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 5; ++k) {
                            formalityList[k] = mSharedPreference1.getInt("socks_formalities" + i + "_" + k, 0);
                            if (formalityList[k] == 1 && filterFormalityList[k] == 1) {
                                match = true;
                            }
                        }
                        if (match) {
                            Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                                    weatherList, formalityList);
                            socksList.add(createdClothes);
                        }
                    }
                }
                break;

            case "SHOES":
                shoesList.clear();
                size = mSharedPreference1.getInt("shoes_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("shoes_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("shoes_image" + i, null);
                        itemDesc = mSharedPreference1.getString("shoes_desc" + i, null);
                        itemType = mSharedPreference1.getString("shoes_type" + i, null);
                        for (int k = 0; k < 19; ++k) {
                            colorsList[k] = mSharedPreference1.getInt("shoes_colors" + i + "_" + k, 0);
                            if (colorsList[k] == 1 && filterColorsList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 6; ++k) {
                            weatherList[k] = mSharedPreference1.getInt("shoes_weathers" + i + "_" + k, 0);
                            if (weatherList[k] == 1 && filterWeatherList[k] == 1) {
                                match = true;
                            }
                        }
                        for (int k = 0; k < 5; ++k) {
                            formalityList[k] = mSharedPreference1.getInt("shoes_formalities" + i + "_" + k, 0);
                            if (formalityList[k] == 1 && filterFormalityList[k] == 1) {
                                match = true;
                            }
                        }
                        if (match) {
                            Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                                    weatherList, formalityList);
                            shoesList.add(createdClothes);
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    // generate list of clothes that match at least one of each filter type
    public void narrowArray(String clothesType) {
        boolean match = false;
        int matchCount = 0;
        switch (clothesType) {
            case "HEAD":
                for (int i = 0; i < headList.size(); ++i) {
                    for (int k = 0; k < 19; ++k) {
                        if (headList.get(i).getColors()[k] == 1 && filterColorsList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 6; ++k) {
                        if (headList.get(i).getWeathers()[k] == 1 && filterWeatherList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 5; ++k) {
                        if (headList.get(i).getFormalities()[k] == 1 && filterFormalityList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    if (matchCount == 3) {
                        match = true;
                    }
                    if (match) {
                        headList2.add(headList.get(i));
                    }
                }
                break;

            case "NECK":
                for (int i = 0; i < neckList.size(); ++i) {
                    for (int k = 0; k < 19; ++k) {
                        if (neckList.get(i).getColors()[k] == 1 && filterColorsList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 6; ++k) {
                        if (neckList.get(i).getWeathers()[k] == 1 && filterWeatherList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 5; ++k) {
                        if (neckList.get(i).getFormalities()[k] == 1 && filterFormalityList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    if (matchCount == 3) {
                        match = true;
                    }
                    if (match) {
                        neckList2.add(neckList.get(i));
                    }
                }
                break;

            case "SHIRTS":
                for (int i = 0; i < shirtList.size(); ++i) {
                    for (int k = 0; k < 19; ++k) {
                        if (shirtList.get(i).getColors()[k] == 1 && filterColorsList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 6; ++k) {
                        if (shirtList.get(i).getWeathers()[k] == 1 && filterWeatherList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 5; ++k) {
                        if (shirtList.get(i).getFormalities()[k] == 1 && filterFormalityList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    if (matchCount == 3) {
                        match = true;
                    }
                    if (match) {
                        shirtList2.add(shirtList.get(i));
                    }
                }
                break;

            case "JACKETS":
                for (int i = 0; i < jacketList.size(); ++i) {
                    for (int k = 0; k < 19; ++k) {
                        if (jacketList.get(i).getColors()[k] == 1 && filterColorsList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 6; ++k) {
                        if (jacketList.get(i).getWeathers()[k] == 1 && filterWeatherList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 5; ++k) {
                        if (jacketList.get(i).getFormalities()[k] == 1 && filterFormalityList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    if (matchCount == 3) {
                        match = true;
                    }
                    if (match) {
                        jacketList2.add(jacketList.get(i));
                    }
                }
                break;

            case "FULL BODY":
                for (int i = 0; i < fullbodyList.size(); ++i) {
                    for (int k = 0; k < 19; ++k) {
                        if (fullbodyList.get(i).getColors()[k] == 1 && filterColorsList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 6; ++k) {
                        if (fullbodyList.get(i).getWeathers()[k] == 1 && filterWeatherList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 5; ++k) {
                        if (fullbodyList.get(i).getFormalities()[k] == 1 && filterFormalityList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    if (matchCount == 3) {
                        match = true;
                    }
                    if (match) {
                        fullbodyList2.add(fullbodyList.get(i));
                    }
                }
                break;

            case "PANTS":
                for (int i = 0; i < pantsList.size(); ++i) {
                    for (int k = 0; k < 19; ++k) {
                        if (pantsList.get(i).getColors()[k] == 1 && filterColorsList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 6; ++k) {
                        if (pantsList.get(i).getWeathers()[k] == 1 && filterWeatherList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 5; ++k) {
                        if (pantsList.get(i).getFormalities()[k] == 1 && filterFormalityList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    if (matchCount == 3) {
                        match = true;
                    }
                    if (match) {
                        pantsList2.add(pantsList.get(i));
                    }
                }
                break;

            case "SOCKS":
                for (int i = 0; i < socksList.size(); ++i) {
                    for (int k = 0; k < 19; ++k) {
                        if (socksList.get(i).getColors()[k] == 1 && filterColorsList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 6; ++k) {
                        if (socksList.get(i).getWeathers()[k] == 1 && filterWeatherList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 5; ++k) {
                        if (socksList.get(i).getFormalities()[k] == 1 && filterFormalityList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    if (matchCount == 3) {
                        match = true;
                    }
                    if (match) {
                        socksList2.add(socksList.get(i));
                    }
                }
                break;

            case "SHOES":
                for (int i = 0; i < shoesList.size(); ++i) {
                    for (int k = 0; k < 19; ++k) {
                        if (shoesList.get(i).getColors()[k] == 1 && filterColorsList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 6; ++k) {
                        if (shoesList.get(i).getWeathers()[k] == 1 && filterWeatherList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    for (int k = 0; k < 5; ++k) {
                        if (shoesList.get(i).getFormalities()[k] == 1 && filterFormalityList[k] == 1) {
                            ++matchCount;
                            break;
                        }
                    }
                    if (matchCount == 3) {
                        match = true;
                    }
                    if (match) {
                        shoesList2.add(shoesList.get(i));
                    }
                }
                break;
        }
    }

    // generate list of clothes that contain colors matching color scheme
    // TODO: Should we check for violated filters?
    public ArrayList<Integer> getEligibleArray(int color1, int color2, int color3, ArrayList<Clothes> clothesArray) {
        ArrayList<Integer> eligibleList = new ArrayList<>();
        for (int i = 0; i < clothesArray.size(); ++i) {
            Clothes item = clothesArray.get(i);
            if (item.getColors()[color1] == 1 || item.getColors()[color2] == 1 || item.getColors()[color3] == 1) {
                eligibleList.add(i);
                if (item.getColors()[color1] == 1) {
                    hasColor1 = true;
                }
                if (item.getColors()[color2] == 1) {
                    hasColor2 = true;
                }
                if (item.getColors()[color3] == 1) {
                    hasColor3 = true;
                }
            }
        }
        return eligibleList;
    }

    // is a purely neutral colored item
    public boolean isNeutral(int[] colorArray) {
        boolean result = false;
        for (int i = 0; i < 7; ++i) {
            if (colorArray[i] == 1) {
                result = true;
            }
        }
        for (int i = 7; i < 19; ++i) {
            if (colorArray[i] == 1) {
                result = false;
            }
        }
        return result;
    }

    // has non-neutral colors that do not violate filters
    public boolean hasNonNeutralColors(int[] colorArray) {
        for (int i = 7; i < 19; ++i) {
            if (colorArray[i] == 1 && filterColorsList[i] == 1) {
                return true;
            }
        }
        return false;
    }

    public int getRandNonNeutralColor(int[] colorArray) {
        int randIndex = 0;
        Random rand = new Random();
        int counter = 0;
        // count non-neutral colors that are allowed by filters
        for (int i = 7; i < 19; ++i) {
            if (colorArray[i] == 1 && filterColorsList[i] == 1) {
                ++counter;
            }
        }
        int randNumber = 0;
        if (counter == 0) {
            randNumber = 1;
        }
        else {
            randNumber = rand.nextInt(counter) + 1; // nextInt takes > 0 only
        }
        counter = 0;
        for (int i = 7; i < 19; ++i) {
            if (colorArray[i] == 1 && filterColorsList[i] == 1) {
                ++counter;
            }
            if (counter == randNumber) {
                randIndex = i;
                break;
            }
        }
        return randIndex;
    }

    // get an item with the specified color from the given subset of a given category
    // full = 1 if looking in full list, full = 0 if looking in narrow list
    public Clothes getSpecific(int color, ArrayList<Integer> list, int category, int full) {
        Random rand = new Random();
        int randIndex = rand.nextInt(list.size());
        int counter = 0;
        if (full == 0) {
            while (true) {
                Clothes randItem = loadedList2.get(category).get(list.get(randIndex));
                if (randItem.getColors()[color] == 1) {
                    return randItem;
                } else {
                    ++randIndex;
                    ++counter;
                    if (randIndex >= list.size()) {
                        randIndex = 0;
                    }
                    if (counter == list.size()) { // fail safe; should never happen logically
                        return randItem;
                    }
                }
            }
        } else {
            while (true) {
                Clothes randItem = loadedList.get(category).get(list.get(randIndex));
                if (randItem.getColors()[color] == 1) {
                    return randItem;
                } else {
                    ++randIndex;
                    ++counter;
                    if (randIndex >= list.size()) {
                        randIndex = 0;
                    }
                    if (counter == list.size()) { // fail safe; should never happen logically
                        return randItem;
                    }
                }
            }
        }
    }

    public Map<Integer,Integer> orderMap(Map<Integer,Integer> typeMap) {
        Map<Integer,Integer> orderedTypeMap = new HashMap<>();
        for (Map.Entry<Integer,Integer> entry : typeMap.entrySet()) {
            orderedTypeMap.put(entry.getKey() + 1, entry.getValue());
        }
        return orderedTypeMap;
    }

    public int generateOutfit(int colorDiff, int colorDiff2, double colorLimit1, double colorLimit2, double colorLimit3, int slot) {
        int value = 0;
        int emptyCount = 0;
        color1 = -1;
        color2 = -1;
        color3 = -1;
        color1count = 0;
        color2count = 0;
        color3count = 0;
        color1List.clear();
        color2List.clear();
        color3List.clear();
        ArrayList<Integer> completedIndex = new ArrayList<>(8); // indices of completed categories
        ArrayList<Integer> incompleteIndex = new ArrayList<>(8); // indices of incomplete categories
        Random rand = new Random();
        // check which filters have pre-selected items
        for (int i = 0; i < filtersList.size(); ++i) {
            // if filter has clothes selected already, that category is completed
            if (filtersList.get(i).getType().equals("NONE")){
                // do nothing; list remains empty, do not add to completedIndex (messes up next section)
                ++emptyCount;
            } else if (filtersList.get(i).getType().equals("ALL")) {
                incompleteIndex.add(i);
            } else {
                completedIndex.add(i);
                clothesList.set(i,filtersList.get(i));
            }
        }

        // pick 1 random category from pre-selected filters
        int counter = 0;
        int randIndex;
        if (completedIndex.size() > 0) {
            randNum = rand.nextInt(completedIndex.size());
            while (color1 == -1 && counter != completedIndex.size()) {
                randIndex = completedIndex.get(randNum);
                if (hasNonNeutralColors(filtersList.get(randIndex).getColors())) {
                    color1 = getRandNonNeutralColor(filtersList.get(randIndex).getColors());
                    color2 = color1 + colorDiff;
                    if (color2 > 18) {
                        color2 = 6 + (color2 % 18);
                    }
                    color3 = color1 + colorDiff2;
                    if (color3 > 18) {
                        color3 = 6 + (color3 % 18);
                    }
                } else {
                    ++counter;
                    ++randNum;
                    if (randNum >= completedIndex.size()) {
                        randNum = 0;
                    }
                }
            }
        }
        for (int i = 0; i < completedIndex.size(); ++i) {
            int currIndex = completedIndex.get(i);
            if (filtersList.get(currIndex).getColors()[color1] == 1) {
                ++color1count;
            }
            if (filtersList.get(currIndex).getColors()[color2] == 1) {
                ++color2count;
            }
            if (filtersList.get(currIndex).getColors()[color3] == 1) {
                ++color3count;
            }
        }

        // go through non-pre-selected categories
        ArrayList<Clothes> fullList;
        ArrayList<Clothes> narrowList;
        if (incompleteIndex.size() > 0) {
            randNum = rand.nextInt(incompleteIndex.size());
        }
        // color scheme doesn't exist yet, keep trying until no categories left
        while (color1 == -1 && incompleteIndex.size() > 0) {
            randIndex = incompleteIndex.get(randNum); // index corresponding to clothes category
            fullList = new ArrayList<>(loadedList.get(randIndex));
            narrowList = new ArrayList<>(loadedList2.get(randIndex));
            int randNarrowIndex = -1;
            int randFullIndex = -1;
            int clothesCounter = 0;
            if (narrowList.size() > 0) { // we have items that match a filter of each type
                randNarrowIndex = rand.nextInt(narrowList.size());
            } else if (fullList.size() > 0) { // narrowList doesn't have items, but fullList does
                randFullIndex = rand.nextInt(fullList.size());
            } else { // no items in this category
                //completedIndex.add(incompleteIndex.get(randNum));
                incompleteIndex.remove(randNum);
                ++emptyCount;
                if (incompleteIndex.size() > 0) {
                    randNum = rand.nextInt(incompleteIndex.size());
                } else {
                    break;
                }
            }
            if (randNarrowIndex != -1) { // narrowList has items
                while (narrowList.size() > 0 && clothesCounter < narrowList.size()) {
                    if (hasNonNeutralColors(narrowList.get(randNarrowIndex).getColors())) {
                        color1 = getRandNonNeutralColor(narrowList.get(randNarrowIndex).getColors());
                        color2 = color1 + colorDiff;
                        if (color2 > 18) {
                            color2 = 6 + (color2 % 18);
                        }
                        color3 = color1 + colorDiff2;
                        if (color3 > 18) {
                            color3 = 6 + (color3 % 18);
                        }
                        ++color1count;
                        if (narrowList.get(randNarrowIndex).getColors()[color2] == 1) {
                            ++color2count;
                        }
                        if (narrowList.get(randNarrowIndex).getColors()[color3] == 1) {
                            ++color3count;
                        }
                        clothesList.set(randIndex, narrowList.get(randNarrowIndex));
                        completedIndex.add(incompleteIndex.get(randNum));
                        incompleteIndex.remove(randNum);
                        break;
                    } else if (clothesCounter < narrowList.size()) { // didn't find non-neutral color, go through clothes in this category
                        ++clothesCounter;
                        ++randNarrowIndex;
                        if (randNarrowIndex >= narrowList.size()) {
                            randNarrowIndex = 0;
                        }
                        if (clothesCounter >= narrowList.size()) { // no color set, choose any, move to the next category
                            clothesList.set(randIndex, narrowList.get(randNarrowIndex));
                            completedIndex.add(incompleteIndex.get(randNum));
                            incompleteIndex.remove(randNum);
                            if (incompleteIndex.size() > 0) {
                                randNum = rand.nextInt(incompleteIndex.size());
                            }
                        }
                    }
                }
            } else if (randFullIndex != -1) { // this category has no items matching each filter type, so pick any if available
                while (fullList.size() > 0) {
                    randFullIndex = rand.nextInt(fullList.size());
                    // if this filter-violating item has a non-violating non-neutral color
                    if (hasNonNeutralColors(fullList.get(randFullIndex).getColors())) {
                        color1 = getRandNonNeutralColor(fullList.get(randFullIndex).getColors());
                        color2 = color1 + colorDiff;
                        if (color2 > 18) {
                            color2 = 6 + (color2 % 18);
                        }
                        color3 = color1 + colorDiff2;
                        if (color3 > 18) {
                            color3 = 6 + (color3 % 18);
                        }
                        ++color1count;
                        if (fullList.get(randFullIndex).getColors()[color2] == 1) {
                            ++color2count;
                        }
                        if (fullList.get(randFullIndex).getColors()[color3] == 1) {
                            ++color3count;
                        }
                        clothesList.set(randIndex, fullList.get(randFullIndex));
                        completedIndex.add(incompleteIndex.get(randNum));
                        incompleteIndex.remove(randNum);
                        break;
                    } else if (clothesCounter < fullList.size()) { // didn't find non-neutral color, go through clothes in this category
                        ++clothesCounter;
                        ++randFullIndex;
                        if (randFullIndex >= fullList.size()) {
                            randFullIndex = 0;
                        }
                        if (clothesCounter >= fullList.size()) { // no color set, choose any, move to the next category
                            clothesList.set(randIndex, fullList.get(randFullIndex));
                            completedIndex.add(incompleteIndex.get(randNum));
                            incompleteIndex.remove(randNum);
                            if (incompleteIndex.size() > 0) {
                                randNum = rand.nextInt(incompleteIndex.size());
                            }
                        }
                    }
                }
            }
        }
        // color scheme exists, choose rest of clothes based on color scheme
        ArrayList<Integer> availableIndex = new ArrayList<>(incompleteIndex);
        fullTypeList.clear();
        typeCount.clear();
        if (availableIndex.size() > 0) {
            randNum = rand.nextInt(availableIndex.size());
        }
        for (int i = 0; i < incompleteIndex.size(); ++i) {
            category = availableIndex.get(randNum); // index corresponding to clothes category
            fullList = new ArrayList<>(loadedList.get(category));
            narrowList = new ArrayList<>(loadedList2.get(category));
            ArrayList<Integer> eligibleList = new ArrayList<>();
            hasColor1 = false;
            hasColor2 = false;
            hasColor3 = false;
            if (narrowList.size() > 0) {
                eligibleList = new ArrayList<>(getEligibleArray(color1, color2, color3, narrowList));
                fullTypeList.put(category, 0);
            } else if (fullList.size() > 0) {
                eligibleList = new ArrayList<>(getEligibleArray(color1, color2, color3, fullList));
                fullTypeList.put(category, 1);
            } else { // no items in this category
                ++emptyCount;
            }
            if (hasColor1) {
                color1List.put(category, eligibleList);
            }
            if (hasColor2) {
                color2List.put(category, eligibleList);
            }
            if (hasColor3) {
                color3List.put(category, eligibleList);
            }
            if (typeCount.containsKey(eligibleList.size())) {
                typeCount = orderMap(typeCount);
            }
            if (eligibleList.size() != 0) {
                typeCount.put(eligibleList.size(), category);
            }
            availableIndex.remove(randNum);
            if (availableIndex.size() > 0) {
                randNum = rand.nextInt(availableIndex.size());
            }
        }
        // largest color is main color, others are accents; if tied, use color1
        int maxColor = color1List.size() + color1count;
        int mainColor = color1;
        if (color2List.size() + color2count > maxColor) {
            maxColor = color2List.size() + color2count;
            mainColor = color2;
        }
        if (color3List.size() + color3count > maxColor) {
            mainColor = color3;
        }
        // calculate how many of each color should be assigned
        // use three variables as thresholds for each color
        int colorTotal = color1count + color2count + color3count;
        int color1Score = (int)((((float)color1count)/(colorTotal)) * completedIndex.size());
        int color2Score = (int)((((float)color2count)/(colorTotal)) * completedIndex.size());
        int color3Score = (int)((((float)color3count)/(colorTotal)) * completedIndex.size());
        colorLimit1 = colorLimit1 * (8 - emptyCount);
        colorLimit2 = colorLimit2 * (8 - emptyCount);
        colorLimit3 = colorLimit3 * (8 - emptyCount);
        ArrayList<Integer> list;
        Clothes item;
        for (Integer type : typeCount.values()) {
            fullList = new ArrayList<>(loadedList.get(type));
            narrowList = new ArrayList<>(loadedList2.get(type));
            ArrayList<Integer> neutralList = new ArrayList<>();
            int full = fullTypeList.get(type);
            if (mainColor == color1) {
                if (color3Score < colorLimit1 && color3List.get(type) != null) { // if there is item of category type with color3
                    list = color3List.get(type);
                    item = getSpecific(color3, list, type, full);
                    clothesList.set(type, item);
                    ++color3Score;
                } else if (color2Score < colorLimit2 && color2List.get(type) != null) { // if there is item of category type with color2
                    list = color2List.get(type);
                    item = getSpecific(color2, list, type, full);
                    clothesList.set(type, item);
                    ++color2Score;
                } else if (color1Score < colorLimit3 && color1List.get(type) != null) { // if there is item of category type with color1
                    list = color1List.get(type);
                    item = getSpecific(color1, list, type, full);
                    clothesList.set(type, item);
                    ++color1Score;
                } else if (narrowList.size() > 0) { // if neutral/random clothes in narrowList
                    for (int j = 0; j < narrowList.size(); ++j) {
                        if (isNeutral(narrowList.get(j).getColors())) {
                            neutralList.add(j);
                        }
                    }
                    if (neutralList.size() > 0) {
                        clothesList.set(type, narrowList.get(neutralList.get(rand.nextInt(neutralList.size()))));
                    } else {
                        clothesList.set(type, narrowList.get(rand.nextInt(narrowList.size())));
                    }
                } else if (fullList.size() > 0) { // if neutral/random clothes in fullList
                    for (int j = 0; j < fullList.size(); ++j) {
                        if (isNeutral(fullList.get(j).getColors())) {
                            neutralList.add(j);
                        }
                    }
                    if (neutralList.size() > 0) {
                        clothesList.set(type, fullList.get(neutralList.get(rand.nextInt(neutralList.size()))));
                    } else {
                        clothesList.set(type, fullList.get(rand.nextInt(fullList.size())));
                    }
                }
            } else if (mainColor == color2) {
                if (color1Score < colorLimit1 && color1List.get(type) != null) { // if there is item of category i with color1
                    list = color1List.get(type);
                    item = getSpecific(color1, list, type, full);
                    clothesList.set(type, item);
                    ++color1Score;
                } else if (color3Score < colorLimit2 && color3List.get(type) != null) { // if there is item of category i with color3
                    list = color3List.get(type);
                    item = getSpecific(color3, list, type, full);
                    clothesList.set(type, item);
                    ++color3Score;
                } else if (color2Score < colorLimit3 && color2List.get(type) != null) { // if there is item of category i with color2
                    list = color2List.get(type);
                    item = getSpecific(color2, list, type, full);
                    clothesList.set(type, item);
                    ++color2Score;
                } else if (narrowList.size() > 0) { // if neutral/random clothes in narrowList
                    for (int j = 0; j < narrowList.size(); ++j) {
                        if (isNeutral(narrowList.get(j).getColors())) {
                            neutralList.add(j);
                        }
                    }
                    if (neutralList.size() > 0) {
                        clothesList.set(type, narrowList.get(neutralList.get(rand.nextInt(neutralList.size()))));
                    } else {
                        clothesList.set(type, narrowList.get(rand.nextInt(narrowList.size())));
                    }
                } else if (fullList.size() > 0) { // if neutral/random clothes in fullList
                    for (int j = 0; j < fullList.size(); ++j) {
                        if (isNeutral(fullList.get(j).getColors())) {
                            neutralList.add(j);
                        }
                    }
                    if (neutralList.size() > 0) {
                        clothesList.set(type, fullList.get(neutralList.get(rand.nextInt(neutralList.size()))));
                    } else {
                        clothesList.set(type, fullList.get(rand.nextInt(fullList.size())));
                    }
                }
            } else {
                if (color1Score < colorLimit1 && color1List.get(type) != null) { // if there is item of category i with color1
                    list = color1List.get(type);
                    item = getSpecific(color1, list, type, full);
                    clothesList.set(type, item);
                    ++color1Score;
                } else if (color2Score < colorLimit2 && color2List.get(type) != null) { // if there is item of category i with color2
                    list = color2List.get(type);
                    item = getSpecific(color2, list, type, full);
                    clothesList.set(type, item);
                    ++color2Score;
                } else if (color3Score < colorLimit3 && color3List.get(type) != null) { // if there is item of category i with color3
                    list = color3List.get(type);
                    item = getSpecific(color3, list, type, full);
                    clothesList.set(type, item);
                    ++color3Score;
                } else if (narrowList.size() > 0) { // if neutral/random clothes in narrowList
                    for (int j = 0; j < narrowList.size(); ++j) {
                        if (isNeutral(narrowList.get(j).getColors())) {
                            neutralList.add(j);
                        }
                    }
                    if (neutralList.size() > 0) {
                        clothesList.set(type, narrowList.get(neutralList.get(rand.nextInt(neutralList.size()))));
                    } else {
                        clothesList.set(type, narrowList.get(rand.nextInt(narrowList.size())));
                    }
                } else if (fullList.size() > 0) { // if neutral/random clothes in fullList
                    for (int j = 0; j < fullList.size(); ++j) {
                        if (isNeutral(fullList.get(j).getColors())) {
                            neutralList.add(j);
                        }
                    }
                    if (neutralList.size() > 0) {
                        clothesList.set(type, fullList.get(neutralList.get(rand.nextInt(neutralList.size()))));
                    } else {
                        clothesList.set(type, fullList.get(rand.nextInt(fullList.size())));
                    }
                }
            }
        }
        ArrayList<Clothes> copyList = new ArrayList<>(clothesList);
        listArray.set(slot, copyList);
        return value;
    }

    public void loadArrayRandom(Context mContext, String clothesType)
    {
        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(mContext);
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
                headList.clear();
                size = mSharedPreference1.getInt("head_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("head_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("head_image" + i, null);
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
                        headList.add(createdClothes);
                    }
                }
                break;

            case "NECK":
                neckList.clear();
                size = mSharedPreference1.getInt("neck_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("neck_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("neck_image" + i, null);
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
                        neckList.add(createdClothes);
                    }
                }
                break;

            case "SHIRTS":
                shirtList.clear();
                size = mSharedPreference1.getInt("shirt_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("shirt_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("shirt_image" + i, null);
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
                        shirtList.add(createdClothes);
                    }
                }
                break;

            case "JACKETS":
                jacketList.clear();
                size = mSharedPreference1.getInt("jacket_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("jacket_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("jacket_image" + i, null);
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
                        jacketList.add(createdClothes);
                    }
                }
                break;

            case "FULL BODY":
                fullbodyList.clear();
                size = mSharedPreference1.getInt("fullbody_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("fullbody_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("fullbody_image" + i, null);
                        itemDesc = mSharedPreference1.getString("fullbody_desc" + i, null);
                        itemType = mSharedPreference1.getString("fullbody_type" + i, null);
                        for (int k = 0; k < 19; ++k) {
                            colorsList[k] = mSharedPreference1.getInt("fullbody_colors" + i + "_" + k, 0);
                        }
                        for (int k = 0; k < 6; ++k) {
                            weatherList[k] = mSharedPreference1.getInt("overall_weathers" + i + "_" + k, 0);
                        }
                        for (int k = 0; k < 5; ++k) {
                            formalityList[k] = mSharedPreference1.getInt("overall_formalities" + i + "_" + k, 0);
                        }
                        Clothes createdClothes = new Clothes(imgPath, allowedBool, itemDesc, itemType, colorsList,
                                weatherList, formalityList);
                        fullbodyList.add(createdClothes);
                    }
                }
                break;

            case "PANTS":
                pantsList.clear();
                size = mSharedPreference1.getInt("pants_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("pants_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("pants_image" + i, null);
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
                        pantsList.add(createdClothes);
                    }
                }
                break;

            case "SOCKS":
                socksList.clear();
                size = mSharedPreference1.getInt("socks_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("socks_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("socks_image" + i, null);
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
                        socksList.add(createdClothes);
                    }
                }
                break;

            case "SHOES":
                shoesList.clear();
                size = mSharedPreference1.getInt("shoes_size", 0);
                for (int i = 0; i < size; ++i) {
                    allowedBool = mSharedPreference1.getBoolean("shoes_allowed" + i, false);
                    if (allowedBool) {
                        imgPath = mSharedPreference1.getString("shoes_image" + i, null);
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
                        shoesList.add(createdClothes);
                    }
                }
                break;

            default:
                break;
        }
    }

    public String saveImage(Bitmap bitmap) {
        String fileName = "NONE"; //no .png or .jpg needed; make sure all descs are unique
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
}
