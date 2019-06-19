package wallpaper.com.fullscreenstatus.singers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import wallpaper.com.fullscreenstatus.Admin;
import wallpaper.com.fullscreenstatus.Full_screen;
import wallpaper.com.fullscreenstatus.MainActivity;
import wallpaper.com.fullscreenstatus.R;
import wallpaper.com.fullscreenstatus.Tab1;
import wallpaper.com.fullscreenstatus.Video_detail;

public class Singers_Name extends AppCompatActivity {
    Toolbar toolbar;
    DatabaseReference mDatabse;
    RecyclerView recyclerView;
    Context ctx;
    ProgressDialog mProgressDialog;
    public static final String VIDEO_URL_KEY="key";
    public static final String VIDEO_TITLE_KEY="title_key";

    public static final String SINGER_KEY = "singer_key";
    String getting_data,current_singer="arijit singh";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singers__name);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Status Mafia");

        toolbar.inflateMenu(R.menu.menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.admin)
                {

                    Intent intent = new Intent(Singers_Name.this,Admin.class);

                    startActivity(intent);


                }


                return true;
            }
        });


       Intent getIn = getIntent();
       getting_data = getIn.getStringExtra(SINGER_KEY);

       //Toast.makeText(getApplicationContext(),getting_data,Toast.LENGTH_LONG).show();




        recyclerView = (RecyclerView)findViewById(R.id.single_singers_songs_list);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);

        recyclerView.setLayoutManager(gridLayoutManager);
        mDatabse = FirebaseDatabase.getInstance().getReference().child("videos");

        mProgressDialog = new ProgressDialog(getApplicationContext());

       if (getting_data.equals("0")){

           current_singer = "arijit singh";

       }
       else if(getting_data.equals("1")){

           current_singer="armaan malik";


       }
       else if(getting_data.equals("2")){


           current_singer="neha kakkar";


       }

       else if(getting_data.equals("3")){

           current_singer="shreya ghoshal";



       }
       else if(getting_data.equals("4")){

           current_singer="atif aslam";




       }
       else if(getting_data.equals("5")){

           current_singer= "palak muchhal";



       }
       else if(getting_data.equals("6")){

           current_singer= "sonu nigam";



       }
       else if(getting_data.equals("7")){
           current_singer=  "jubin nautiyal";




       }

       Query trending = mDatabse.orderByChild("singer").startAt(current_singer).endAt(current_singer+"\uf8ff");





















        FirebaseRecyclerAdapter<Video_detail, Tab1.VideoviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Video_detail, Tab1.VideoviewHolder>(

                Video_detail.class,
                R.layout.single_video,
                Tab1.VideoviewHolder.class,
                trending


        ) {
            @Override
            protected void populateViewHolder(Tab1.VideoviewHolder viewHolder, final Video_detail model, final int position) {



                // Toast.makeText(getActivity(),"MILGYA"+model.getSinger(),Toast.LENGTH_LONG).show();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setVideo(model.getVideo(), getApplicationContext());





                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Toast.makeText(getContext(),""+model.getVideo(),Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(getApplicationContext(),Full_screen.class);
                        intent.putExtra(VIDEO_URL_KEY,model.getVideo());
                        intent.putExtra(VIDEO_TITLE_KEY,model.getTitle());

                        startActivity(intent);






                    }
                });

            }

        };


        recyclerView.setAdapter(firebaseRecyclerAdapter);





















    }















}
