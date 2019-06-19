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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Tab4 extends Fragment {
    EditText searchBox;
    Button searchButton;
    String searchedItem, trimmedSearchedItem;
    DatabaseReference mDatabse;
    RecyclerView recyclerView;
    Context ctx;
    ProgressDialog mProgressDialog;
    public static final String VIDEO_URL_KEY = "key";
    public static final String VIDEO_TITLE_KEY = "title_key";

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.Tab1 in you classes
        View v = inflater.inflate(R.layout.tab4, container, false);
        searchBox = (EditText) v.findViewById(R.id.searchBox);

        searchButton = (Button) v.findViewById(R.id.searchButton);
        recyclerView = (RecyclerView) v.findViewById(R.id.search_video_list);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        mDatabse = FirebaseDatabase.getInstance().getReference("videos");

        mProgressDialog = new ProgressDialog(getActivity());

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchedItem = searchBox.getText().toString();
                searchMethod(searchedItem);
            }
        });

        AdView mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);











        return v;
    }

    private void searchMethod(String s) {
        trimmedSearchedItem = s.toLowerCase().trim();

        Query searchQuery = mDatabse.orderByChild("title").startAt(trimmedSearchedItem).endAt(trimmedSearchedItem + "\uf8ff");
        FirebaseRecyclerAdapter<Video_detail, Tab1.VideoviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Video_detail, Tab1.VideoviewHolder>(

                Video_detail.class,
                R.layout.single_video,
                Tab1.VideoviewHolder.class,
                searchQuery


        ) {
            @Override
            protected void populateViewHolder(Tab1.VideoviewHolder viewHolder, Video_detail model, int position) {
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
            long interval = 5000;
            RequestOptions options = new RequestOptions().frame(interval).centerCrop().priority(Priority.HIGH);



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