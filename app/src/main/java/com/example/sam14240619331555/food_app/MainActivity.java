package com.example.sam14240619331555.food_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.sam14240619331555.food_app.update;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public ListView map_listview,hot_listview;

    public static List<listview_item_location> map_list = new ArrayList<listview_item_location>();

    public static MyAdapter hot_adapter;
    private MyAdapter_location map_adapter;
    private ViewPager viewPager;//页卡内容

    private List<View> views;// Tab页面列表

    public  View view1,view2,view3,view5,view6;//各个页卡

    public ImageView drawerpc;

    public SlidingTabLayout tab;

    public ListView company_good_item_listview;

    public List<listview_company_good_item> company_good_item_list = new ArrayList<listview_company_good_item>();

    private  Adapter_company_good_item company_good_item_adapter;

    public static MainActivity instance = null;
    /*public static File file;
    public SharedPreferences setting;
    public String valuestring;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        instance = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, update.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerpc = (ImageView)findViewById(R.id.imageView);
        drawerpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, login.class);
                startActivity(intent);

            }
        });

        tab = (SlidingTabLayout) findViewById(R.id.tab);

        InitViewPager();
        InitLattice();

        /*file = new File("/data/data/com.example.sam14240619331555.food_app/shared_prefs","LoginInfo.xml");
        if(file.exists()){
            setting = getSharedPreferences("LoginInfo",0);
            valuestring = setting.getString("account","");
            if(!valuestring.equals("")){

            }
        }*/



    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void  InitLattice(){


        ImageView company_logo = (ImageView)view2.findViewById(R.id.company_logo);
        company_logo.setBackgroundDrawable(new BitmapDrawable(readBitMap(this.getApplicationContext(), R.drawable.company_logo1)));

        ImageButton company_info_bt = (ImageButton)view2.findViewById(R.id.company_info_bt);
        company_info_bt.setBackgroundDrawable(new BitmapDrawable(readBitMap(this.getApplicationContext(), R.drawable.pencil)));

        ImageButton company_menu_bt = (ImageButton)view2.findViewById(R.id.company_menu_bt);
        company_menu_bt.setBackgroundDrawable(new BitmapDrawable(readBitMap(this.getApplicationContext(), R.drawable.map)));

        ImageButton company_comment_bt = (ImageButton)view2.findViewById(R.id.company_comment_bt);
        company_comment_bt.setBackgroundDrawable(new BitmapDrawable(readBitMap(this.getApplicationContext(), R.drawable.bubble)));

        /*ArrayList<String> company_good_item_url  = new ArrayList<>();
        ArrayList<String> company_good_item_name  = new ArrayList<>();

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(company_good_item_url.get(0)).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            if (bitmap.getWidth() != 50 || bitmap.getHeight() != 50) {
                bitmap = resizebitmap(bitmap);
            }
        }

        company_good_item_list.add(new listview_company_good_item(bitmap , company_good_item_name.get(0)));

        company_good_item_listview = (ListView) view2.findViewById(R.id.company_good_item_listview);
        company_good_item_adapter = new  Adapter_company_good_item(MainActivity.this, company_good_item_list);
        company_good_item_listview.setAdapter (company_good_item_adapter);*/

    }

    public Bitmap resizebitmap(Bitmap bm){
        int width = bm.getWidth();

        int height = bm.getHeight();

        int newWidth = 50;

        int newHeight = 50;

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,

                true);
        return newbm;
    }



    public void slot_choose(View view){

        new SlotAsyncTask(this).execute();

    }


    private void InitViewPager() {
        map_list.clear();
        //hot_list.clear();
        //slot_list.clear();
        //today_list.clear();
        //lattice_list.clear();

        viewPager=(ViewPager) findViewById(R.id.vPager);
        views=new ArrayList<View>();
        LayoutInflater inflater=getLayoutInflater();

        view1=inflater.inflate(R.layout.layout1, null); //分類
        view2=inflater.inflate(R.layout.layout2, null); //格子趣
        view3=inflater.inflate(R.layout.layout3, null); //熱門餐廳
        view5=inflater.inflate(R.layout.layout5, null); //拉霸fun
        view6=inflater.inflate(R.layout.layout6, null); //今日推薦


        map_list.add(new listview_item_location("全部", 1));
        map_list.add(new listview_item_location("基隆市", 1));
        map_list.add(new listview_item_location("台北市", 1));
        map_list.add(new listview_item_location("新北市", 1));
        map_list.add(new listview_item_location("桃園市", 1));
        map_list.add(new listview_item_location("新竹縣", 1));
        map_list.add(new listview_item_location("新竹市", 1));
        map_list.add(new listview_item_location("苗栗縣", 1));
        map_list.add(new listview_item_location("台中市", 1));
        map_list.add(new listview_item_location("彰化縣", 1));
        map_list.add(new listview_item_location("雲林縣", 1));
        map_list.add(new listview_item_location("嘉義縣", 1));
        map_list.add(new listview_item_location("嘉義市", 1));
        map_list.add(new listview_item_location("台南市", 1));
        map_list.add(new listview_item_location("高雄市", 1));
        map_list.add(new listview_item_location("屏東縣", 1));
        map_list.add(new listview_item_location("台東縣", 1));
        map_list.add(new listview_item_location("花蓮縣", 1));
        map_list.add(new listview_item_location("宜蘭縣", 1));




        map_listview = (ListView) view1.findViewById(R.id.map_list);
        map_adapter = new MyAdapter_location(MainActivity.this, map_list);
        map_listview.setAdapter(map_adapter);



        hot_listview = (ListView) view3.findViewById(R.id.hot_list);
        hot_adapter = new MyAdapter(MainActivity.this, SplashScreenAsyncTask.hot_list);
        hot_listview.setAdapter(hot_adapter);
        hot_listview.setOnItemClickListener(hotListener);






        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view5);
        views.add(view6);

        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(2);

        tab.setViewPager(viewPager);


    }

    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        opt.inSampleSize = computeSampleSize(opt, -1, 128*128);  //计算出图片使用的inSampleSize
        opt.inJustDecodeBounds = false;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8 ) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    private ListView.OnItemClickListener hotListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {

            SharedPreferences item_pos = getSharedPreferences("position", 0);
            SharedPreferences.Editor editor_item = item_pos.edit();
            editor_item.putInt("position", position);
            editor_item.commit();

            Intent intent = new Intent();
            intent.setClass(MainActivity.this, item.class);
            startActivity(intent);
        }
    };






    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;
        String[] titles={"分類","格子趣","熱門餐廳","拉霸Fun","今日推薦"};
        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)   {
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return  mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }//pagerAdapter



}
