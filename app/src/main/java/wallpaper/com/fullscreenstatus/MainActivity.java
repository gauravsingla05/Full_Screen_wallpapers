package wallpaper.com.fullscreenstatus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    //This is our tablayout
    private TabLayout tabLayout;
    private TabAdapter adapter;
    Button button;
    Toolbar toolbar;DrawerLayout dLayout;
    //This is our viewPager
    private ViewPager viewPager;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding toolbar to the activity
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        toolbar.inflateMenu(R.menu.menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.admin) {

                    Intent intent = new Intent(MainActivity.this, Admin.class);

                    startActivity(intent);

                } else if (item.getItemId() == R.id.privacy) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://techyunk.com/privacy-policy/"));
                    startActivity(browserIntent);

                }

                return true;
            }
        });


        setNavigationDrawer();
        MobileAds.initialize(this, "YOUR_ADMOB_APP_ID");


        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);


        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.category_ic));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.trending_ic));
       /* tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.fire));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.search_ic));*/
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);


        //Adding onTabSelectedListener to swipe views
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

//Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);


    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }






    private void setNavigationDrawer() {
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout); // initiate a DrawerLayout
        NavigationView navView = (NavigationView) findViewById(R.id.navigation); // initiate a Navigation View
// implement setNavigationItemSelectedListener event on NavigationView
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int itemId = menuItem.getItemId(); // get selected menu item's id
// check selected menu item's id and replace a Fragment Accordingly
                if (itemId == R.id.first) {

                } else if (itemId == R.id.second) {

                } else if (itemId == R.id.third) {

                }
// display a toast message with menu item's title
                dLayout.closeDrawers(); // close the all open Drawer Views
                return true;
            }

        });
    }




    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}