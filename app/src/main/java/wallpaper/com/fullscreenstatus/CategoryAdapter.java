package wallpaper.com.fullscreenstatus;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import wallpaper.com.fullscreenstatus.singers.Singers_Name;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<Category_data> android;
    private Context context;
    public static final String CAT_KEY = "cat_key";
    public CategoryAdapter(Context context,ArrayList<Category_data> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item_style, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder viewHolder, int i) {

        viewHolder.cateName.setText(android.get(i).getCateName());
        RequestOptions options = new RequestOptions()

                .placeholder(R.drawable.loading)
                .error(R.drawable.loading);


        Picasso.get().load(android.get(i).getCate_image_url()).into(viewHolder.catImage);
        //Glide.with(context).load(android.get(i).getCate_image_url()).apply(options).into(viewHolder.catImage);

        viewHolder.catImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(context, Category_name.class);
                String val = String.valueOf(i);
                ii.putExtra(CAT_KEY,val);
                context.startActivity(ii);


            }
        });








    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView cateName;
        private ImageView catImage;
        public ViewHolder(View view) {
            super(view);

        cateName = (TextView)view.findViewById(R.id.cat_name);
            catImage = (ImageView) view.findViewById(R.id.cat_image_thumb);
        }
    }

}