package com.example.sam14240619331555.food_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class item extends AppCompatActivity {
    TextView title;
    public static  Gallery gallery;
    public String[] class_mv = {"中式", "西式","異國"};
    private String[] class_lifecycle = {"早餐","午餐","早午餐", "下午茶","晚餐","宵夜", "點心"};
    public String[] class_type = {"葷食", "素食"};
    public String[] class_drink = {"有免費提供飲料", "無免費提供飲料"};

    public ListView item_listview,comm_listview;
    public static List<String> item_list = new ArrayList<String>();
    public static List<listview_comm> comm_list = new ArrayList<listview_comm>();
    private ArrayAdapter<String> adapter ;
    private Adapter_comm comm_adapter;
    RatingBar star;
   // ImageView item_image,discount_image,info_image,comment_image;
    public int pos;

    private ViewPager item_vPager;//页卡内容
    private List<View> views;// Tab页面列表
    public  View view1,view2,view3;//各个页卡
    public Button bt1,bt2,bt3;
    public ProgressDialog cover_dialog;
    public ImageButton discount_bt,info_bt,comment_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        SharedPreferences item_pos = getSharedPreferences("position", 0);
        pos = item_pos.getInt("position", -1);


        title = (TextView)findViewById(R.id.des_title);
        star= (RatingBar)findViewById(R.id.des_star);
        //item_image =(ImageView)findViewById(R.id.item_image);



        cover_dialog = ProgressDialog.show(item.this, "資料加載中", "請等待", true);

        Context context = ImgurSampleApplication.getAppContext();
        gallery = (Gallery) findViewById(R.id.menu_gallery);
        ArrayList<String> list = SplashScreenAsyncTask.rest_url.get(pos);
        PageAdapter pageAdapter=new PageAdapter(context, list);
        gallery.setAdapter(pageAdapter);
        gallery.setOnItemClickListener(clickListener);

        InitViewPager();
        show_text();
        InitMap();

        discount_bt =(ImageButton) findViewById(R.id.discount_image);
        info_bt =(ImageButton) findViewById(R.id.info_image);
        comment_bt =(ImageButton) findViewById(R.id.comment_image);

        discount_bt.setBackgroundDrawable(new BitmapDrawable(MainActivity.readBitMap(this.getApplicationContext(), R.drawable.money)));
        info_bt.setBackgroundDrawable(new BitmapDrawable(MainActivity.readBitMap(this.getApplicationContext(), R.drawable.map)));
        comment_bt.setBackgroundDrawable(new BitmapDrawable(MainActivity.readBitMap(this.getApplicationContext(), R.drawable.bubble)));

        new Thread(new Runnable(){
            @Override
            public void run() {
                try{

                    Thread.sleep(2000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    cover_dialog.dismiss();
                }
            }
        }).start();




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent();
                intent.setClass(item.this, response.class);
                startActivity(intent);
            }
        });



        discount_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_vPager.setCurrentItem(0);
            }
        });

        info_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_vPager.setCurrentItem(1);
            }
        });
        comment_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_vPager.setCurrentItem(2);
            }
        });


    }

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.setClass(item.this, gallery_click.class);
            intent .putExtra("position",position);
            startActivity(intent);
        }
    };

    public void show_text(){


        item_list.clear();
        comm_list.clear();


        title.setText(SplashScreenAsyncTask.rest_name.get(pos));
        star.setRating(Float.valueOf(SplashScreenAsyncTask.rest_star.get(pos)));
        star.setIsIndicator(true);

        Log.v("item", "mv" + SplashScreenAsyncTask.rest_mv.get(pos) + " " + class_mv[Integer.parseInt(SplashScreenAsyncTask.rest_mv.get(pos))]);
        Log.v("item", "type" + SplashScreenAsyncTask.rest_type.get(pos));
        Log.v("item", "type"+ class_type[Integer.parseInt(SplashScreenAsyncTask.rest_type.get(pos))]);

        Log.v("item", "lifecycle"+ SplashScreenAsyncTask.rest_lifecycle.get(pos) +" " +class_lifecycle[Integer.parseInt(SplashScreenAsyncTask.rest_lifecycle.get(pos))]);
        Log.v("item", "drink"+ SplashScreenAsyncTask.rest_drink.get(pos) +" " +class_drink[Integer.parseInt(SplashScreenAsyncTask.rest_drink.get(pos))]);


        String type = "餐廳類型   "+class_mv[Integer.parseInt(SplashScreenAsyncTask.rest_mv.get(pos))]+"/"+class_type[Integer.parseInt(SplashScreenAsyncTask.rest_type.get(pos))];
        String lifecycle = "用餐類型   "+class_lifecycle[Integer.parseInt(SplashScreenAsyncTask.rest_lifecycle.get(pos))];
        String address ="餐廳地址   "+SplashScreenAsyncTask.rest_addr.get(pos);
        String drink = "提供飲料   "+class_drink[Integer.parseInt(SplashScreenAsyncTask.rest_drink.get(pos))];

        item_list.add(type);
        item_list.add(lifecycle);
        item_list.add(address);
        item_list.add(drink);

        comm_list.add(new listview_comm("1",SplashScreenAsyncTask.rest_comm.get(pos)));//?


        int layoutId = android.R.layout.simple_list_item_1;

        adapter = new ArrayAdapter<String>(this, layoutId, item_list);
        item_listview = (ListView) view2.findViewById(R.id.itemDescript_listview);
        item_listview.setAdapter(adapter);

        comm_listview = (ListView) view3.findViewById(R.id.itemComment_listview);
        comm_adapter = new Adapter_comm(item.this, comm_list);
        comm_listview.setAdapter(comm_adapter);


    }
    private void InitViewPager() {


        item_vPager=(ViewPager) findViewById(R.id.item_vPager);
        views=new ArrayList<View>();
        LayoutInflater inflater=getLayoutInflater();
        view1=inflater.inflate(R.layout.item_layout1, null); //優惠
        view2=inflater.inflate(R.layout.item_layout2, null); //資訊
        view3=inflater.inflate(R.layout.item_layout3, null); //評論

        views.add(view1);
        views.add(view2);
        views.add(view3);

        item_vPager.setAdapter(new item_ViewPagerAdapter(views));
        item_vPager.setCurrentItem(1);



    }

    public double latitude,longitude;
    private GoogleMap map;
    static LatLng place;
    public void InitMap(){
        String addressString= SplashScreenAsyncTask.rest_addr.get(pos);

        Geocoder geoCoder = new Geocoder(this.getApplicationContext(), Locale.getDefault());
        try{
            List<Address> addressLocation = geoCoder.getFromLocationName(addressString, 1);
            latitude = addressLocation.get(0).getLatitude();
            longitude = addressLocation.get(0).getLongitude();
        }catch (IOException e){
            e.printStackTrace();
        }
        place = new LatLng(latitude, longitude);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        Marker nkut = map.addMarker(new MarkerOptions().position(place));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 14));
    }

    public class item_ViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public item_ViewPagerAdapter(List<View> mListViews) {
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


    }//pagerAdapter

}
