package wallpaper.com.fullscreenstatus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tab2 extends Fragment {

    DatabaseReference mDatabse;
    RecyclerView mRecyclerView,cateRecyclerview;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<String> alName;
    ArrayList<Integer> alImage;
    String catNames[] = {
            "Attitude",
            "Business",
        "Courage",
        "Educational",
        "Entrepreneurship",
        "Failure",
        "Fitness",
        "Friendship",
        "Funny",
        "Happiness",
        "Inspirational",
        "Leadership",
        "Life",
        "Love",
        "Motivational",
        "Positive",
        "Relationship",
        "Success",
        "Travel",
        "Wisdom",
        "Other"
    };
    String cat_image_urls[] = {
            "https://whatsapp-status.live/wp-content/uploads/2019/05/attitude.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/bussiness.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/courage.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/educational.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/enter.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/failure.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/fit.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/frnd.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/funny.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/happyness.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/inspiration.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/leadership.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/life.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/love.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/motivate.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/positivr.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/relation.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/success.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/travrl.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/wisdom.jpeg",
            "https://whatsapp-status.live/wp-content/uploads/2019/05/other.jpeg",

    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.Tab1 in you classes


        View v = inflater.inflate(R.layout.tab2, container, false);
        alName = new ArrayList<>(Arrays.asList("Arijit", "Armaan Malik", "Neha Kakkar", "Shreya Ghoshal", "Atif Aslam", "Palak Muchhal", "Sonu Nigam"));
        alImage = new ArrayList<>(Arrays.asList(R.drawable.arijit, R.drawable.armaan, R.drawable.neha, R.drawable.shreya, R.drawable.atif, R.drawable.palak, R.drawable.sonu));


        MobileAds.initialize(getActivity(), "ca-app-pub-7509171001087812~6069902991");
        com.google.android.gms.ads.AdView mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);;




        // Calling the RecyclerView
        mRecyclerView = (RecyclerView)v.findViewById(R.id.horizontal_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        cateRecyclerview = (RecyclerView)v.findViewById(R.id.vertical_recyclerview);
        cateRecyclerview.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        cateRecyclerview.setLayoutManager(gridLayoutManager);

        mDatabse = FirebaseDatabase.getInstance().getReference().child("videos");
        // The number of Columns
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Category_data> catname  = prepareData();
        CategoryAdapter adapter = new CategoryAdapter(getActivity(),catname);


        mAdapter = new HLVAdapter(getActivity(), alName, alImage);
        mRecyclerView.setAdapter(mAdapter);

        cateRecyclerview.setAdapter(adapter);




        return v;
    }

    private ArrayList<Category_data> prepareData(){

        ArrayList<Category_data> android_version = new ArrayList<>();
        for(int i=0;i<catNames.length;i++){
            Category_data category_data = new Category_data();
            category_data.setCateName(catNames[i]);
            category_data.setCate_image_url(cat_image_urls[i]);
            android_version.add(category_data);
        }
        return android_version;
    }
}