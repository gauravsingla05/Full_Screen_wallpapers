package wallpaper.com.fullscreenstatus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Admin extends AppCompatActivity {

    Button video,thumnail,submit;
    CheckBox trendng;
    String category_name= null;
    EditText title,singer;
    public static final int GALLERY_REQUEST=1;
    public static final int GALLERY_THUMB=2;
    Uri video_uri=null;

    StorageReference mStorageRef;
    ProgressDialog progressDialog;
    private android.widget.Spinner spinner1;
    DatabaseReference database;
    String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        video = (Button)findViewById(R.id.video_id);
        thumnail=(Button)findViewById(R.id.thumb_id);
        submit = (Button)findViewById(R.id.submit_id);
        title =(EditText)findViewById(R.id.title_id);
        singer=(EditText)findViewById(R.id.singer_id);
        trendng = (CheckBox)findViewById(R.id.trending_id);
        progressDialog = new ProgressDialog(this);
        spinner1 = (android.widget.Spinner) findViewById(R.id.spinner1);
        spinner1.setPrompt("Category");
        java.util.List<String> list = new java.util.ArrayList<String>();
        list.add("Attitude");
        list.add("Business");
        list.add("Courage");
        list.add("Educational");
        list.add("Entrepreneurship");
        list.add("Failure");
        list.add("Fitness");
        list.add("Friendship");
        list.add("Funny");
        list.add("Happiness");
        list.add("Inspirational");
        list.add("Leadership");
        list.add("Life");
        list.add("Love");
        list.add("Motivational");
        list.add("Positive");
        list.add("Relationship");
        list.add("Success");
        list.add("Travel");
        list.add("Wisdom");
        list.add("Other");



        android.widget.ArrayAdapter<String> dataAdapter = new android.widget.ArrayAdapter<String>(this,
		android.R.layout.simple_spinner_item, list);
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	    spinner1.setAdapter(dataAdapter);














       mStorageRef = FirebaseStorage.getInstance().getReference();
       database = FirebaseDatabase.getInstance().getReference().child("wallpapers");




        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent galleryIntentvideo = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntentvideo.setType("image/*");
                galleryIntentvideo.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                galleryIntentvideo.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(galleryIntentvideo,GALLERY_REQUEST);*/


                Intent galleryIntentvideo = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntentvideo.setType("image/*");
                startActivityForResult(galleryIntentvideo,GALLERY_REQUEST);


            }
        });













        submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
     startposting();
    }
});


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                   if( requestCode==GALLERY_REQUEST && resultCode==RESULT_OK ) {
                    video_uri = data.getData();



                   }






    }


    public void startposting(){
        final String saveTitle =title.getText().toString().trim();
        final String saveSinger=singer.getText().toString().toLowerCase().trim();

         if(!TextUtils.isEmpty(saveTitle) && video_uri!=null )
         {

         StorageReference video_path = mStorageRef.child("wallpapers").child(video_uri.getLastPathSegment());

         progressDialog.setMessage("Uploading...");
         progressDialog.show();
         category_name=  (String)spinner1.getSelectedItem();
         String trimedcategory = category_name.toLowerCase().trim();

         video_path.putFile(video_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                 progressDialog.dismiss();
                 Uri video_download= taskSnapshot.getDownloadUrl();
                 DatabaseReference new_post = database.push();
                 new_post.child("title").setValue(saveTitle);
                 new_post.child("video").setValue(video_download.toString());
                 new_post.child("category").setValue(trimedcategory);
                // new_post.child("singer").setValue(saveSinger);

                 if(trendng.isChecked()){
                     new_post.child("trending").setValue("1");
                 }
                 else {
                     new_post.child("trending").setValue("0");


                 }




                 Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();





             }
         });





         }

       else
         {

             Toast.makeText(getApplicationContext(),"FAIL",Toast.LENGTH_SHORT).show();


         }







    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }



}
