package com.example.sam14240619331555.food_app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.AlertDialogWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sam14240619331555 on 2016/1/28.
 */
public class SplashScreenAsyncTask extends AsyncTask<URL, Integer, Map> {

    // String for LogCat documentation
    private final static String TAG = "SplashScreen-AsyncTask";
    private Activity mParentActivity;


    public static List<listview_item> hot_list = new ArrayList<listview_item>();

    public String[] class_type = {"中式", "西式","異國"};
    private String[] class_lifecycle = {"早餐","午餐","早午餐", "下午茶","晚餐","宵夜", "點心"};


    private ProgressBar spinTask;
    boolean net_state=false;

    Map map = new HashMap();
    int total_place=0;
    public static List<String> rest_name = new ArrayList<String>();

    public static ArrayList<String> rest_addr = new ArrayList<>();
    public static ArrayList<String> rest_lifecycle = new ArrayList<>();
    public static ArrayList<String> rest_drink = new ArrayList<>();
    public static ArrayList<String> rest_star = new ArrayList<>();
    public static ArrayList<String> rest_mv = new ArrayList<>();
    public static ArrayList<String> rest_type = new ArrayList<>();
    public static ArrayList<ArrayList<String>> rest_url  = new ArrayList<>();
    public static ArrayList<String> rest_comm = new ArrayList<>();
    public static ArrayList<String> rest_px = new ArrayList<>();
    public static ArrayList<String> rest_py = new ArrayList<>();

    public static File file;
    public SharedPreferences setting;
    public String valuestring;



    public SplashScreenAsyncTask(Activity parentActivity, ProgressBar bar) {
        // TODO Auto-generated constructor stub
        super();
        Log.i(TAG, "SplashScreenAsyncTask().");
        mParentActivity = parentActivity;
        spinTask = bar;
    }

    @Override
    protected Map doInBackground(URL... params) {
        // TODO Auto-generated method stub

        ConnectivityManager cm = (ConnectivityManager) mParentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected() ){
            net_state=true;
        }

        if(net_state){
            map = update.HTTPGetQuery("http://wteserver.me:8080/WTE/all.jsp");

            try {
                int progress = 0;
                JSONObject rest_con = new JSONObject(map.get("content").toString());
                JSONArray rest = rest_con.getJSONArray("restaurant");
                Log.v("rest", String.valueOf(rest.length()));
                Log.v("rest",rest.toString());

                total_place = rest.length();

                if (rest.length() > 0) {
                    getDetail(rest_name, rest, "restaurant");
                    progress += rest.length();
                    publishProgress((int) percent(progress, total_place));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        Log.i(TAG, "onPreExecute().");
        spinTask.setVisibility(View.VISIBLE);

        rest_name = new ArrayList<>();
        rest_addr = new ArrayList<>();
        rest_lifecycle = new ArrayList<>();
        rest_drink = new ArrayList<>();
        rest_star = new ArrayList<>();
        rest_mv = new ArrayList<>();
        rest_type = new ArrayList<>();
        rest_url = new ArrayList<>();
        rest_comm = new ArrayList<>();
        rest_px = new ArrayList<>();
        rest_py = new ArrayList<>();

        hot_list.clear();


    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Map map) {
        // TODO Auto-generated method stub
        super.onPostExecute(map);
        Log.i(TAG, "onPostExecute().");
        //spinTask.setVisibility(View.GONE);





        if(net_state==true){
            if(rest_name.size()==0){
                //new SplashScreenAsyncTask(mParentActivity,spinTask).execute();
                mParentActivity.finish();
                Log.v("splah", "after");
                Intent intent = new Intent();
                intent.setClass(mParentActivity, MainActivity.class);
                mParentActivity.startActivity(intent);
            }else{

                // check login state
                file = new File("/data/data/com.example.sam14240619331555.food_app/shared_prefs","LoginInfo.xml");

                if(file.exists()){
                    Log.v("log", "have file");
                    setting = mParentActivity.getSharedPreferences("LoginInfo", 0);
                    valuestring = setting.getString("account","");
                    if(!valuestring.equals("")){
                        Log.v("log", "login in file");
                        new AlertDialogWrapper.Builder(mParentActivity)
                                .setTitle("已登入")
                                .setMessage("已登入")
                                .show();
                    }
                }


                for (int i = 0; i < rest_name.size(); i++) {
                    final String title = rest_name.get(i);
                    final String star = rest_star.get(i);
                    final String type = class_type[Integer.parseInt(rest_mv.get(i))];
                    final String lifecycle = class_lifecycle[Integer.parseInt(rest_lifecycle.get(i))];

                    hot_list.add(new listview_item(star, title, type,lifecycle));
                    Log.v("hot",title +" "+ star +" "+ type+" "+ lifecycle);
                }

                mParentActivity.finish();
                Log.v("splah", "after");
                Intent intent = new Intent();
                intent.setClass(mParentActivity, MainActivity.class);
                mParentActivity.startActivity(intent);
            }
        } //net_state is true
        else{
            new AlertDialogWrapper.Builder(mParentActivity)
                    .setTitle("提醒")
                    .setMessage("請開啟網路功能")
                    .setNegativeButton("了解", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mParentActivity.startActivity(new Intent(Settings.ACTION_SETTINGS));
                            mParentActivity.finish();
                        }
                    }).show();
        }


    }

    public void getDetail(List<String> rest_name, JSONArray array, String rest_Class) throws JSONException {

        for (int a = 0; a < array.length(); a++) {
            if (array.getJSONObject(a).getJSONObject("information").get("name").toString().isEmpty()) {
                rest_name.add(null);
            } else {
                rest_name.add(array.getJSONObject(a).getJSONObject("information").get("name").toString());
            }

            if (array.getJSONObject(a).getJSONObject("information").isNull("address")) {
                rest_addr.add(null);
            } else if (array.getJSONObject(a).getJSONObject("information").get("address").toString().isEmpty()) {
                rest_addr.add(null);
            } else {
                rest_addr.add(array.getJSONObject(a).getJSONObject("information").get("address").toString());
            }

            if (array.getJSONObject(a).getJSONObject("information").isNull("lifecycle")) {
                rest_lifecycle.add(null);
            } else if (array.getJSONObject(a).getJSONObject("information").get("lifecycle").toString().isEmpty()) {
                rest_lifecycle.add(null);
            } else {
                rest_lifecycle.add(array.getJSONObject(a).getJSONObject("information").get("lifecycle").toString());
            }

            if (array.getJSONObject(a).getJSONObject("information").isNull("drink")) {
                rest_drink.add(null);
            } else if (array.getJSONObject(a).getJSONObject("information").get("drink").toString().isEmpty()) {
                rest_drink.add(null);
            } else {
                rest_drink.add(array.getJSONObject(a).getJSONObject("information").get("drink").toString());
            }

            if (array.getJSONObject(a).getJSONObject("information").isNull("star")) {
                rest_star.add(null);
            } else if (array.getJSONObject(a).getJSONObject("information").get("star").toString().isEmpty()) {
                rest_star.add(null);
            } else {
                rest_star.add(array.getJSONObject(a).getJSONObject("information").get("star").toString());
            }

            if (array.getJSONObject(a).getJSONObject("information").isNull("mv")) {
                rest_mv.add(null);
            } else if (array.getJSONObject(a).getJSONObject("information").get("mv").toString().isEmpty()) {
                rest_mv.add(null);
            } else {
                rest_mv.add(array.getJSONObject(a).getJSONObject("information").get("mv").toString());
            }

            if (array.getJSONObject(a).getJSONObject("information").isNull("type")) {
                rest_type.add(null);
            } else if (array.getJSONObject(a).getJSONObject("information").get("type").toString().isEmpty()) {
                rest_type.add(null);
            } else {
                rest_type.add(array.getJSONObject(a).getJSONObject("information").get("type").toString());
            }

            if (array.getJSONObject(a).getJSONObject("information").isNull("url")) {
                rest_url.add(null);
            } else if (array.getJSONObject(a).getJSONObject("information").get("url").toString().isEmpty()) {
                rest_url.add(null);
            } else {
                ArrayList<String> m  =new ArrayList<>();
                JSONArray u = array.getJSONObject(a).getJSONObject("information").getJSONArray("url");
                for(int i = 0;i<u.length();i++){
                    m.add(u.getString(i));
                    Log.v("urll", i + "url : " + u.getString(i));
                }
                rest_url.add(m);
            }

            if (array.getJSONObject(a).getJSONObject("information").isNull("px")) {
                rest_px.add(null);
            } else if (array.getJSONObject(a).getJSONObject("information").get("px").toString().isEmpty()) {
                rest_px.add(null);
            } else {
                rest_px.add(array.getJSONObject(a).getJSONObject("information").get("px").toString());
            }

            if (array.getJSONObject(a).getJSONObject("information").isNull("py")) {
                rest_py.add(null);
            } else if (array.getJSONObject(a).getJSONObject("information").get("py").toString().isEmpty()) {
                rest_py.add(null);
            } else {
                rest_py.add(array.getJSONObject(a).getJSONObject("information").get("py").toString());
            }

            if (array.getJSONObject(a).getJSONArray("comment").getJSONObject(0).isNull("comment")) {
                rest_comm.add(null);
            } else if (array.getJSONObject(a).getJSONArray("comment").getJSONObject(0).get("comment").toString().isEmpty()) {
                rest_comm.add(null);
            } else {
                rest_comm.add(array.getJSONObject(a).getJSONArray("comment").getJSONObject(0).get("comment").toString());

            }

        }
    }

    private double percent(int numerator, int denominator) throws ArithmeticException {
        if(denominator == 0)
            throw new ArithmeticException("分母為 0 !!");
        else {
            double percent = (double)numerator/(double)denominator*10000;
            percent = Math.floor(percent + 0.5);

            return percent/100;
        }
    }



}
