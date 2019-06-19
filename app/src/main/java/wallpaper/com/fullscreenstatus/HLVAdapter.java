package wallpaper.com.fullscreenstatus;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import wallpaper.com.fullscreenstatus.singers.Singers_Name;


public class HLVAdapter extends RecyclerView.Adapter<HLVAdapter.ViewHolder> {
    public static final String SINGER_KEY = "singer_key";
    ArrayList<String> alName;
    ArrayList<Integer> alImage;
    Context context;
    private static final String APP_ID ="ca-app-pub-7509171001087812~9253541550" ;
    private static final String AD_UNIT_ID ="ca-app-pub-7509171001087812/3697962545" ;


    public HLVAdapter(Context context, ArrayList<String> alName, ArrayList<Integer> alImage) {
        super();
        this.context = context;
        this.alName = alName;
        this.alImage = alImage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singer_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvSpecies.setText(alName.get(i));
        viewHolder.imgThumbnail.setImageResource(alImage.get(i));

        InterstitialAd    mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(AD_UNIT_ID);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                  //  Toast.makeText(context, "#" + position + " - " + alName.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                   Intent ii = new Intent(context, Singers_Name.class);
                String val = String.valueOf(i);
                ii.putExtra(SINGER_KEY,val);
                context.startActivity(ii);
                 mInterstitialAd.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return alName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public CircleImageView imgThumbnail;
        public TextView tvSpecies;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (CircleImageView) itemView.findViewById(R.id.img_thumbnail);
            tvSpecies = (TextView) itemView.findViewById(R.id.singer_names);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

}