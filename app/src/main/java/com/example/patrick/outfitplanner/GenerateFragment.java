package com.example.patrick.outfitplanner;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Patrick on 2/25/2017.
 */

public class GenerateFragment extends Fragment {
    ImageView hatPic;
    ImageView scarfPic;
    ImageView shirtPic;
    ImageView jacketPic;
    ImageView overallsPic;
    ImageView pantsPic;
    ImageView socksPic;
    ImageView shoesPic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*
         * Inflate the layout for this fragment
         */
        return inflater.inflate(R.layout.generate_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
