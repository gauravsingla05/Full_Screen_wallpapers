package wallpaper.com.fullscreenstatus;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import wallpaper.com.fullscreenstatus.singers.Singers_Name;

public class Category_name extends AppCompatActivity {
    Toolbar toolbar;
    DatabaseReference mDatabse;
    RecyclerView recyclerView;
    Context ctx;
    ProgressDialog mProgressDialog;
    public static final String VIDEO_URL_KEY="key";
    public static final String VIDEO_TITLE_KEY="title_key";
    public static final String CAT_KEY = "cat_key";
    public static final String SINGER_KEY = "singer_key";
    String gettingcatdata,current_cat="love",finalCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_name);
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

                    Intent intent = new Intent(Category_name.this,Admin.class);

                    startActivity(intent);


                }


                return true;
            }
        });


        Intent getIn = getIntent();
        gettingcatdata = getIn.getStringExtra(CAT_KEY);

        //Toast.makeText(getApplicationContext(),gettingcatdata,Toast.LENGTH_LONG).show();




        recyclerView = (RecyclerView)findViewById(R.id.single_cat_list);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);

        recyclerView.setLayoutManager(gridLayoutManager);
        mDatabse = FirebaseDatabase.getInstance().getReference().child("wallpapers");

        mProgressDialog = new ProgressDialog(getApplicationContext());

        if (gettingcatdata.equals("0")){

           current_cat = "attitude";

        }
        else if(gettingcatdata.equals("1")){

           current_cat="business";


        }
        else if(gettingcatdata.equals("2")){


           current_cat="courage";


        }

        else if(gettingcatdata.equals("3")){

           current_cat="educational";



        }
        else if(gettingcatdata.equals("4")){

           current_cat="entrepreneurship";




        }
        else if(gettingcatdata.equals("5")){

           current_cat= "failure";



        }
        else if(gettingcatdata.equals("6")){

           current_cat= "fitness";



        }
        else if(gettingcatdata.equals("7")){
           current_cat=  "friendship";




        }
        else if(gettingcatdata.equals("8")){

            current_cat = "funny";


        }
        else if(gettingcatdata.equals("9")){

            current_cat = "happiness";


        }

        else if(gettingcatdata.equals("10")){

            current_cat = "inspirational";


        }

        else if(gettingcatdata.equals("11")){

            current_cat = "leadership";


        }

        else if(gettingcatdata.equals("12")){

            current_cat = "life";


        }

        else if(gettingcatdata.equals("13")){

            current_cat = "love";


        }

        else if(gettingcatdata.equals("14")){

            current_cat = "motivational";


        }
        else if(gettingcatdata.equals("15")){

            current_cat = "positive";


        }

        else if(gettingcatdata.equals("16")){

            current_cat = "relationship";


        }


        else if(gettingcatdata.equals("17")){

            current_cat = "success";


        }


        else if(gettingcatdata.equals("18")){

            current_cat = "travel";


        }

        else if(gettingcatdata.equals("19")){

            current_cat = "wisdom";


        }
        else if(gettingcatdata.equals("20")){

            current_cat = "other";


        }

    finalCat=current_cat.toLowerCase();

        Query categoryQuery = mDatabse.orderByChild("category").startAt(current_cat).endAt(current_cat+"\uf8ff");





















        FirebaseRecyclerAdapter<Video_detail, Tab1.VideoviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Video_detail, Tab1.VideoviewHolder>(

                Video_detail.class,
                R.layout.single_video,
                Tab1.VideoviewHolder.class,
                categoryQuery


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
