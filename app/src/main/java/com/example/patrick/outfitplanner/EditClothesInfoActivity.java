package com.example.patrick.outfitplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import static android.widget.TextView.BufferType.EDITABLE;

/**
 * Created by Patrick on 12/27/2016.
 */

public class EditClothesInfoActivity extends AppCompatActivity {
    String msg = "Edit : ";
    Button addImageButton;
    Button cancelButton;
    Button deleteButton;
    Button finishButton;
    String clothesType;
    ArrayList<Clothes> clothesList;
    String img;
    boolean allow = true;
    String desc;
    int[] colorsList = new int[19];
    int[] weatherList = new int[6];
    int[] formalityList = new int[5];
    ArrayList<CheckBox> checkboxColorList = new ArrayList<>(19);
    ArrayList<CheckBox> checkboxWeatherList = new ArrayList<>(6);
    CheckBox allowBox;
    ArrayList<CheckBox> checkboxFormalityList = new ArrayList<>(5);
    int position = -1;
    boolean editMode = false;
    ImageView clothesImg;
    EditText nameBox;
    boolean confirm = false;
    boolean delete = false;
    private final static int SELECT_PHOTO = 12345;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editclothesinfo);

        Bundle bundle = getIntent().getExtras();
        clothesType = bundle.getString("type");
        clothesList = (ArrayList<Clothes>)bundle.getSerializable("list");
        position = bundle.getInt("id");
        loadArray(this);

        for (int i = 0; i < 19; ++i) {
            checkboxColorList.add((CheckBox)findViewById(R.id.blackbox));
            colorsList[i] = 0;
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
        /*for (int i = 1; i < checkboxColorList.size(); ++i) {
            if (checkboxColorList.get(i).isChecked()) {
                colorsList[i] = 1;
            }
        }*/

        for (int i = 0; i < 6; ++i) {
            checkboxWeatherList.add((CheckBox)findViewById(R.id.hotbox));
            weatherList[i] = 0;
        }
        checkboxWeatherList.add(0, (CheckBox)findViewById(R.id.hotbox));
        checkboxWeatherList.add(1, (CheckBox)findViewById(R.id.warmbox));
        checkboxWeatherList.add(2, (CheckBox)findViewById(R.id.coldbox));
        checkboxWeatherList.add(3, (CheckBox)findViewById(R.id.rainybox));
        checkboxWeatherList.add(4, (CheckBox)findViewById(R.id.snowybox));
        /*for (int i = 1; i < checkboxWeatherList.size(); ++i) {
            if (checkboxWeatherList.get(i).isChecked()) {
                weatherList[i] = 1;
            }
        }*/

        for (int i = 0; i < 5; ++i) {
            checkboxFormalityList.add((CheckBox)findViewById(R.id.formalbox));
            formalityList[i] = 0;
        }
        checkboxFormalityList.add(0, (CheckBox)findViewById(R.id.formalbox));
        checkboxFormalityList.add(1, (CheckBox)findViewById(R.id.semiformalbox));
        checkboxFormalityList.add(2, (CheckBox)findViewById(R.id.informalbox));
        checkboxFormalityList.add(3, (CheckBox)findViewById(R.id.casualbox));
        /*for (int i = 1; i < checkboxFormalityList.size(); ++i) {
            if (checkboxFormalityList.get(i).isChecked()) {
                formalityList[i] = 1;
            }
        }*/

        clothesImg = (ImageView)findViewById(R.id.image);
        allowBox = (CheckBox)findViewById(R.id.allowbox);
        allowBox.setChecked(true);
        nameBox = (EditText) findViewById(R.id.namebox);
        if (position != -1) {
            editMode = true;
            img = clothesList.get(position).getImage();
            File sd = Environment.getExternalStorageDirectory();
            File image = new File(sd+img);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            //Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
            String imagePath = clothesList.get(position).getImage();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput(imagePath));
                clothesImg.setImageBitmap(bitmap);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            //bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
            allow = clothesList.get(position).getAllowed();
            if (!allow) {
                allowBox.setChecked(false);
            }
            desc = clothesList.get(position).getDesc();
            nameBox.setText(desc, EDITABLE);
            Log.d(desc, "Checking for position");
            Log.d(msg, "Checking for position");
            for (int i = 0; i < 19; ++i) {
                if (clothesList.get(position).getColors()[i] == 1) {
                    checkboxColorList.get(i).setChecked(true);
                    colorsList[i] = 1;
                }
            }
            for (int i = 0; i < 6; ++i) {
                if (clothesList.get(position).getWeathers()[i] == 1) {
                    checkboxWeatherList.get(i).setChecked(true);
                    weatherList[i] = 1;
                }
            }
            for (int i = 0; i < 5; ++i) {
                if (clothesList.get(position).getFormalities()[i] == 1) {
                    checkboxFormalityList.get(i).setChecked(true);
                    formalityList[i] = 1;
                }
            }
        }

        //clothesImg.setDrawingCacheEnabled(false);

        addImageButton = (Button)findViewById(R.id.addimagebutton);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //photoPickerIntent.setType("image/*");
                //startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO);
            }
        });



        cancelButton = (Button)findViewById(R.id.cancelbutton);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(EditClothesInfoActivity.this, ClosetActivity.class);
                in.putExtra("type", clothesType);
                in.putExtra("list", clothesList);
                startActivity(in);
            }
        });

        deleteButton = (Button)findViewById(R.id.deletebutton);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (editMode) {
                    delete = true;
                }
                Intent in = new Intent(EditClothesInfoActivity.this, ClosetActivity.class);
                in.putExtra("type", clothesType);
                in.putExtra("list", clothesList);
                startActivity(in);
            }
        });

        finishButton = (Button)findViewById(R.id.finishbutton);
        finishButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*Clothes createdClothes = new Clothes(img, allow, desc, clothesType, colorsList,
                        weatherList, formalityList);
                if (editMode) {
                    clothesList.set(position, createdClothes);
                    Log.d("Editing", "Item edited");
                }
                else {
                    clothesList.add(createdClothes);
                    Log.d("Creating", "Item created");
                }*/
                confirm = true;
                Intent in = new Intent(EditClothesInfoActivity.this, ClosetActivity.class);
                in.putExtra("type", clothesType);
                in.putExtra("list", clothesList);
                //saveArray();
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
        if (confirm) {
            desc = nameBox.getText().toString();
            clothesImg.setDrawingCacheEnabled(true);
            clothesImg.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            clothesImg.layout(0, 0,
                    clothesImg.getMeasuredWidth(), clothesImg.getMeasuredHeight());
            clothesImg.buildDrawingCache(true);
            Log.d(String.valueOf(position), "Checking for position");
            //img = saveImage(Bitmap.createBitmap(clothesImg.getDrawingCache()));
            clothesImg.setDrawingCacheEnabled(false);
            img = saveImage(((BitmapDrawable)clothesImg.getDrawable()).getBitmap());
            if (allowBox.isChecked()) {
                allow = true;
            }
            else {
                allow = false;
            }
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
            Clothes createdClothes = new Clothes(img, allow, desc, clothesType, colorsList,
                    weatherList, formalityList);
            if (editMode) {
                clothesList.set(position, createdClothes);
                Log.d("Editing", "Item edited");
            } else {
                clothesList.add(createdClothes);
                Log.d("Creating", "Item created");
            }
            saveArray();
        }
        else if (delete) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor mEdit1 = sp.edit();
            switch (clothesType) {
                case "HEAD":
                    mEdit1.remove("head_image" + position);
                    mEdit1.remove("head_allowed" + position);
                    mEdit1.remove("head_desc" + position);
                    mEdit1.remove("head_type" + position);
                    mEdit1.remove("head_color_size" + position);
                    for (int k = 0; k < clothesList.get(position).getColors().length; ++k) {
                        mEdit1.remove("head_colors" + position + "_" + k);
                    }
                    mEdit1.remove("head_weather_size" + position);
                    for (int k = 0; k < clothesList.get(position).getWeathers().length; ++k) {
                        mEdit1.remove("head_weathers" + position + "_" + k);
                    }
                    mEdit1.remove("head_formality_size" + position);
                    for (int k = 0; k < clothesList.get(position).getFormalities().length; ++k) {
                        mEdit1.remove("head_formalities" + position + "_" + k);
                    }
                    break;

                case "NECK":
                    mEdit1.remove("neck_image" + position);
                    mEdit1.remove("neck_allowed" + position);
                    mEdit1.remove("neck_desc" + position);
                    mEdit1.remove("neck_type" + position);
                    mEdit1.remove("neck_color_size" + position);
                    for (int k = 0; k < clothesList.get(position).getColors().length; ++k) {
                        mEdit1.remove("neck_colors" + position + "_" + k);
                    }
                    mEdit1.remove("neck_weather_size" + position);
                    for (int k = 0; k < clothesList.get(position).getWeathers().length; ++k) {
                        mEdit1.remove("neck_weathers" + position + "_" + k);
                    }
                    mEdit1.remove("neck_formality_size" + position);
                    for (int k = 0; k < clothesList.get(position).getFormalities().length; ++k) {
                        mEdit1.remove("neck_formalities" + position + "_" + k);
                    }
                    break;

                case "SHIRTS":
                    mEdit1.remove("shirt_image" + position);
                    mEdit1.remove("shirt_allowed" + position);
                    mEdit1.remove("shirt_desc" + position);
                    mEdit1.remove("shirt_type" + position);
                    mEdit1.remove("shirt_color_size" + position);
                    for (int k = 0; k < clothesList.get(position).getColors().length; ++k) {
                        mEdit1.remove("shirt_colors" + position + "_" + k);
                    }
                    mEdit1.remove("shirt_weather_size" + position);
                    for (int k = 0; k < clothesList.get(position).getWeathers().length; ++k) {
                        mEdit1.remove("shirt_weathers" + position + "_" + k);
                    }
                    mEdit1.remove("shirt_formality_size" + position);
                    for (int k = 0; k < clothesList.get(position).getFormalities().length; ++k) {
                        mEdit1.remove("shirt_formalities" + position + "_" + k);
                    }
                    break;

                case "JACKETS":
                    mEdit1.remove("jacket_image" + position);
                    mEdit1.remove("jacket_allowed" + position);
                    mEdit1.remove("jacket_desc" + position);
                    mEdit1.remove("jacket_type" + position);
                    mEdit1.remove("jacket_color_size" + position);
                    for (int k = 0; k < clothesList.get(position).getColors().length; ++k) {
                        mEdit1.remove("jacket_colors" + position + "_" + k);
                    }
                    mEdit1.remove("jacket_weather_size" + position);
                    for (int k = 0; k < clothesList.get(position).getWeathers().length; ++k) {
                        mEdit1.remove("jacket_weathers" + position + "_" + k);
                    }
                    mEdit1.remove("jacket_formality_size" + position);
                    for (int k = 0; k < clothesList.get(position).getFormalities().length; ++k) {
                        mEdit1.remove("jacket_formalities" + position + "_" + k);
                    }
                    break;

                case "FULL BODY":
                    mEdit1.remove("fullbody_image" + position);
                    mEdit1.remove("fullbody_allowed" + position);
                    mEdit1.remove("fullbody_desc" + position);
                    mEdit1.remove("fullbody_type" + position);
                    mEdit1.remove("fullbody_color_size" + position);
                    for (int k = 0; k < clothesList.get(position).getColors().length; ++k) {
                        mEdit1.remove("fullbody_colors" + position + "_" + k);
                    }
                    mEdit1.remove("fullbody_weather_size" + position);
                    for (int k = 0; k < clothesList.get(position).getWeathers().length; ++k) {
                        mEdit1.remove("fullbody_weathers" + position + "_" + k);
                    }
                    mEdit1.remove("fullbody_formality_size" + position);
                    for (int k = 0; k < clothesList.get(position).getFormalities().length; ++k) {
                        mEdit1.remove("fullbody_formalities" + position + "_" + k);
                    }
                    break;

                case "PANTS":
                    mEdit1.remove("pants_image" + position);
                    mEdit1.remove("pants_allowed" + position);
                    mEdit1.remove("pants_desc" + position);
                    mEdit1.remove("pants_type" + position);
                    mEdit1.remove("pants_color_size" + position);
                    for (int k = 0; k < clothesList.get(position).getColors().length; ++k) {
                        mEdit1.remove("pants_colors" + position + "_" + k);
                    }
                    mEdit1.remove("pants_weather_size" + position);
                    for (int k = 0; k < clothesList.get(position).getWeathers().length; ++k) {
                        mEdit1.remove("pants_weathers" + position + "_" + k);
                    }
                    mEdit1.remove("pants_formality_size" + position);
                    for (int k = 0; k < clothesList.get(position).getFormalities().length; ++k) {
                        mEdit1.remove("pants_formalities" + position + "_" + k);
                    }
                    break;

                case "SOCKS":
                    mEdit1.remove("socks_image" + position);
                    mEdit1.remove("socks_allowed" + position);
                    mEdit1.remove("socks_desc" + position);
                    mEdit1.remove("socks_type" + position);
                    mEdit1.remove("socks_color_size" + position);
                    for (int k = 0; k < clothesList.get(position).getColors().length; ++k) {
                        mEdit1.remove("socks_colors" + position + "_" + k);
                    }
                    mEdit1.remove("socks_weather_size" + position);
                    for (int k = 0; k < clothesList.get(position).getWeathers().length; ++k) {
                        mEdit1.remove("socks_weathers" + position + "_" + k);
                    }
                    mEdit1.remove("socks_formality_size" + position);
                    for (int k = 0; k < clothesList.get(position).getFormalities().length; ++k) {
                        mEdit1.remove("socks_formalities" + position + "_" + k);
                    }
                    break;

                case "SHOES":
                    mEdit1.remove("shoes_image" + position);
                    mEdit1.remove("shoes_allowed" + position);
                    mEdit1.remove("shoes_desc" + position);
                    mEdit1.remove("shoes_type" + position);
                    mEdit1.remove("shoes_color_size" + position);
                    for (int k = 0; k < clothesList.get(position).getColors().length; ++k) {
                        mEdit1.remove("shoes_colors" + position + "_" + k);
                    }
                    mEdit1.remove("shoes_weather_size" + position);
                    for (int k = 0; k < clothesList.get(position).getWeathers().length; ++k) {
                        mEdit1.remove("shoes_weathers" + position + "_" + k);
                    }
                    mEdit1.remove("shoes_formality_size" + position);
                    for (int k = 0; k < clothesList.get(position).getFormalities().length; ++k) {
                        mEdit1.remove("shoes_formalities" + position + "_" + k);
                    }
                    break;
            }

            mEdit1.commit();
            clothesList.remove(position);
            saveArray();
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
        Intent in = new Intent(EditClothesInfoActivity.this, ClosetActivity.class);
        in.putExtra("type", clothesType);
        in.putExtra("list", clothesList);
        startActivity(in);
    }

    public String saveImage(Bitmap bitmap) {
        String fileName = clothesType + this.desc; //no .png or .jpg needed; make sure all descs are unique
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                //ImageView imageView = (ImageView) findViewById(R.id.image);
                clothesImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean saveArray()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();
        Log.d("Save progress", "Attempting to save");
        Log.d(clothesType, "Checking type being saved");

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
                    for (int k = 0; k < 19; ++k) {
                        mEdit1.remove("head_colors" + i + "_" + k);
                        mEdit1.putInt("head_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("head_weather_size" + i);
                    mEdit1.putInt("head_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < 6; ++k) {
                        mEdit1.remove("head_weathers" + i + "_" + k);
                        mEdit1.putInt("head_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("head_formality_size" + i);
                    mEdit1.putInt("head_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < 5; ++k) {
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
                    for (int k = 0; k < 19; ++k) {
                        mEdit1.remove("neck_colors" + i + "_" + k);
                        mEdit1.putInt("neck_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("neck_weather_size" + i);
                    mEdit1.putInt("neck_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < 6; ++k) {
                        mEdit1.remove("neck_weathers" + i + "_" + k);
                        mEdit1.putInt("neck_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("neck_formality_size" + i);
                    mEdit1.putInt("neck_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < 5; ++k) {
                        mEdit1.remove("neck_formalities" + i + "_" + k);
                        mEdit1.putInt("neck_formalities" + i + "_" + k, clothesList.get(i).getFormalities()[k]);
                    }
                }
                break;

            case "SHIRTS":
                mEdit1.remove("shirt_size");
                mEdit1.putInt("shirt_size", clothesList.size());
                Log.d(String.valueOf(clothesList.size()), "Checking size of shirt array");
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
                    for (int k = 0; k < 19; ++k) {
                        mEdit1.remove("shirt_colors" + i + "_" + k);
                        mEdit1.putInt("shirt_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("shirt_weather_size" + i);
                    mEdit1.putInt("shirt_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < 6; ++k) {
                        mEdit1.remove("shirt_weathers" + i + "_" + k);
                        mEdit1.putInt("shirt_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("shirt_formality_size" + i);
                    mEdit1.putInt("shirt_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < 5; ++k) {
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
                    for (int k = 0; k < 19; ++k) {
                        mEdit1.remove("jacket_colors" + i + "_" + k);
                        mEdit1.putInt("jacket_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("jacket_weather_size" + i);
                    mEdit1.putInt("jacket_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < 6; ++k) {
                        mEdit1.remove("jacket_weathers" + i + "_" + k);
                        mEdit1.putInt("jacket_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("jacket_formality_size" + i);
                    mEdit1.putInt("jacket_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < 5; ++k) {
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
                    for (int k = 0; k < 19; ++k) {
                        mEdit1.remove("fullbody_colors" + i + "_" + k);
                        mEdit1.putInt("fullbody_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("fullbody_weather_size" + i);
                    mEdit1.putInt("fullbody_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < 6; ++k) {
                        mEdit1.remove("fullbody_weathers" + i + "_" + k);
                        mEdit1.putInt("fullbody_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("fullbody_formality_size" + i);
                    mEdit1.putInt("fullbody_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < 5; ++k) {
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
                    for (int k = 0; k < 19; ++k) {
                        mEdit1.remove("pants_colors" + i + "_" + k);
                        mEdit1.putInt("pants_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("pants_weather_size" + i);
                    mEdit1.putInt("pants_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < 6; ++k) {
                        mEdit1.remove("pants_weathers" + i + "_" + k);
                        mEdit1.putInt("pants_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("pants_formality_size" + i);
                    mEdit1.putInt("pants_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < 5; ++k) {
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
                    for (int k = 0; k < 19; ++k) {
                        mEdit1.remove("socks_colors" + i + "_" + k);
                        mEdit1.putInt("socks_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("socks_weather_size" + i);
                    mEdit1.putInt("socks_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < 6; ++k) {
                        mEdit1.remove("socks_weathers" + i + "_" + k);
                        mEdit1.putInt("socks_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("socks_formality_size" + i);
                    mEdit1.putInt("socks_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < 5; ++k) {
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
                    for (int k = 0; k < 19; ++k) {
                        mEdit1.remove("shoes_colors" + i + "_" + k);
                        mEdit1.putInt("shoes_colors" + i + "_" + k, clothesList.get(i).getColors()[k]);
                    }
                    mEdit1.remove("shoes_weather_size" + i);
                    mEdit1.putInt("shoes_weather_size" + i, clothesList.get(i).getWeathers().length);
                    for (int k = 0; k < 6; ++k) {
                        mEdit1.remove("shoes_weathers" + i + "_" + k);
                        mEdit1.putInt("shoes_weathers" + i + "_" + k, clothesList.get(i).getWeathers()[k]);
                    }
                    mEdit1.remove("shoes_formality_size" + i);
                    mEdit1.putInt("shoes_formality_size" + i, clothesList.get(i).getFormalities().length);
                    for (int k = 0; k < 5; ++k) {
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
        /*SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
        sKey.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            sKey.add(mSharedPreference1.getString("Status_" + i, null));
        }*/
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
        clothesList.clear();
        int size = 0;
        String imgPath;
        boolean allowedBool;
        String itemDesc;
        String itemType;
        int[] colorsList = new int[19];
        int[] weatherList = new int[6];
        int[] formalityList = new int[5];
        clothesList.clear();
        switch (clothesType) {
            case "HEAD":
                size = mSharedPreference1.getInt("head_size", 0);
                Log.d(String.valueOf(size), "Hats loaded");
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
