package wallpaper.com.fullscreenstatus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import javax.xml.namespace.QName;

public class Tab3 extends Fragment {

    //Overriden method onCreateView
    DatabaseReference mDatabse;
    RecyclerView recyclerView;
    Context ctx;
    ProgressDialog mProgressDialog;
    public static final String VIDEO_URL_KEY="key";
    public static final String VIDEO_TITLE_KEY="title_key";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.Tab1 in you classes
        View view = inflater.inflate(R.layout.tab3, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.trending_video_list);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
     /*   LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setReverseLayout(true);
        linearLayout.setStackFromEnd(true);*/
        recyclerView.setLayoutManager(gridLayoutManager);
        mDatabse = FirebaseDatabase.getInstance().getReference().child("wallpapers");
        mProgressDialog = new ProgressDialog(getActivity());

        MobileAds.initialize(getActivity(), "ca-app-pub-7509171001087812~6069902991");
        com.google.android.gms.ads.AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);;






        return view;






    }

    @Override
    public void onStart() {
        super.onStart();
        Query trending = mDatabse.orderByChild("trending").startAt("1").endAt("1"+"\uf8ff");
        FirebaseRecyclerAdapter<Video_detail, Tab1.VideoviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Video_detail, Tab1.VideoviewHolder>(

                Video_detail.class,
                R.layout.trending_single_video,
                Tab1.VideoviewHolder.class,
                trending


        ) {
            @Override
            protected void populateViewHolder(Tab1.VideoviewHolder viewHolder, final Video_detail model, final int position) {



                     // Toast.makeText(getActivity(),"MILGYA"+model.getSinger(),Toast.LENGTH_LONG).show();
                      viewHolder.setTitle(model.getTitle());
                      viewHolder.setVideo(model.getVideo(), getContext());





                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Toast.makeText(getContext(),""+model.getVideo(),Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(getActivity(),Full_screen.class);
                        intent.putExtra(VIDEO_URL_KEY,model.getVideo());
                        intent.putExtra(VIDEO_TITLE_KEY,model.getTitle());

                        startActivity(intent);






                    }
                });

            }

        };


        recyclerView.setAdapter(firebaseRecyclerAdapter);








    }




    public static class VideoviewHolder extends RecyclerView.ViewHolder {
        View mView;

        public VideoviewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;

        }

        public void setTitle(String title_of_video){

            TextView video_title = (TextView)mView.findViewById(R.id.title_video_id);
            video_title.setText(title_of_video);

        }

        public void setVideo(String video_URl, Context context){

            ImageView videoView = (ImageView) mView.findViewById(R.id.video_view_id);
            final ProgressBar progressBar = (ProgressBar)mView.findViewById(R.id.image_loading_progress);

            RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.trendingloading);


            Glide.with(context).asBitmap().load(video_URl).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).apply(options).into(videoView);



        }






    }











}