package com.example.sam14240619331555.food_app;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by sam14240619331555 on 2016/1/20.
 */
public class update extends FragmentActivity {

    public String r_name,r_time,r_addr,r_menu,r_class1,r_class2,r_class3,r_class4,r_star="0",r_push;
    public ImageView up;
    Map map = new HashMap();
    static List<EditText> et_list = new ArrayList<EditText>();

    List<Integer> et_id = new ArrayList<Integer>();
    public int et_num=0;
    String menu="";
    public ProgressDialog dialog;
    public boolean loc_state=false;

    /*拍照的照片存储位置*/
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private File mCurrentPhotoFile;//照相机拍照得到的图片
    private static final int CAMERA_WITH_DATA = 3023;
    private static final int PHOTO_PICKED_WITH_DATA = 3021;

    EditText et1;
    EditText et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_upload);

        LocationManager status = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            loc_state=true;
        }

        up = (ImageView) findViewById(R.id.update);
        SimpleDateFormat time_format = new  SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ); //format
        Calendar catch_time = Calendar.getInstance(); //time
        Date date = catch_time.getTime();
        timeEditText = (EditText)findViewById(R.id.data_time);
        timeEditText.setText(time_format.format(date));
        addrEditText = (EditText)findViewById(R.id.data_addr);
        if(loc_state==true){
            locationServiceInitial();
        }
        else{
            new AlertDialogWrapper.Builder(update.this)
                    .setTitle("提醒")
                    .setMessage("請開啟定位功能")
                    .setNegativeButton("了解", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            finish();
                        }
                    }).show();
        }

        copyEditInitial();


        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        updatedata();

    }



    private LocationManager lms;
    public Double longitude;
    public Double latitude;
    public static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static final int INITIAL_REQUEST=1337;
    public static final int LOCATION_REQUEST=INITIAL_REQUEST+3;
    public static final int REQUEST_CODE_IMAGE = 0;

    private void locationServiceInitial() {
        if (canAccessLocation()) {
            lms = (LocationManager) getSystemService(LOCATION_SERVICE);    //取得系統定位服務
            Location location = lms.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);    //使用GPS定位座標
            getLocation(location);
        }
        else {
            requestPermissions(LOCATION_PERMS,LOCATION_REQUEST);
        }


    }

    public String returnAddress="";

    private void getLocation(Location location) {    //將定位資訊顯示在畫面中
        List<Address> lstAddress = new ArrayList<Address>();
        Log.v("addr","2");
        if (location != null) {
            longitude = location.getLongitude() ;  //取得經度
            latitude =  location.getLatitude();
            Log.v("addr",longitude+"  "+ latitude);
            Log.v("addr","3");
        } else {
            Toast.makeText(this, "無法定位座標", Toast.LENGTH_LONG).show();
        }
        Geocoder gc = new Geocoder(this, Locale.TRADITIONAL_CHINESE);
        try{
            lstAddress = gc.getFromLocation(latitude, longitude, 1);
            Log.v("addr",lstAddress.toString());
            returnAddress=lstAddress.get(0).getAddressLine(0);
            addrEditText.setText(returnAddress.substring(3, returnAddress.length()));
        }
        catch (IOException e){
            Log.v("addr","shit");
            Toast.makeText(this, "無法定位座標", Toast.LENGTH_LONG).show();
            addrEditText.setText("");
            e.printStackTrace();

        }


        Log.v("addr",returnAddress);
    }

    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED== ContextCompat.checkSelfPermission(this,perm));
    }

    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch(requestCode) {

            case LOCATION_REQUEST:
                if (canAccessLocation()) {
                    lms = (LocationManager) getSystemService(LOCATION_SERVICE);    //取得系統定位服務
                    Location location = lms.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);    //使用GPS定位座標
                    getLocation(location);
                }
                else {

                }
                break;
        }
    }


    //全形轉半形,空格換行處理
    public static String changeright(String str){
        for(char c:str.toCharArray()){
            str = str.replaceAll("　", " ");
            if((int)c >= 65281 && (int)c <= 65374){
                str = str.replace(c, (char)(((int)c)-65248));
            }
        }
        str= str.replaceAll(" ", "_");
        str=str.replaceAll("\n","@");
        return str;
    }
    public void upload() {

        r_name=nameEditText.getText().toString();
        r_time=timeEditText.getText().toString().replaceAll(" ", "_");
        r_addr=addrEditText.getText().toString();
        r_menu=menuEditText.getText().toString();
        /*if(et_num>0){
            for(int i=0;i<et_num;i++){
                if(et_list.get(i).getText().toString().equals("")){
                    r_menu="";
                    break;
                }
            }
        }
        Log.v("menu",menu);*/
        r_push=pushEditText.getText().toString();
        if(r_name.equals("")){
            new AlertDialogWrapper.Builder(update.this)
                    .setTitle("提醒")
                    .setMessage("請填店名")   .show();
        }

        else if(r_addr.equals("")){
            new AlertDialogWrapper.Builder(update.this)
                    .setTitle("提醒")
                    .setMessage("請填地址")   .show();
        }

        else if(r_menu.equals("")){
            new AlertDialogWrapper.Builder(update.this)
                    .setTitle("提醒")
                    .setMessage("請填菜單")   .show();
        }

        else if(r_push.equals("")){
            new AlertDialogWrapper.Builder(update.this)
                    .setTitle("提醒")
                    .setMessage("請填推文")   .show();
        }

        else if(r_star.equals("0")){
            new AlertDialogWrapper.Builder(update.this)
                    .setTitle("提醒")
                    .setMessage("請填評價") .show();
        }

        else if(r_class1.equals("-1")||r_class2.equals("-1")||r_class3.equals("-1")||r_class4.equals("-1")){
            new AlertDialogWrapper.Builder(update.this)
                    .setTitle("提醒")
                    .setMessage("請填餐廳類別") .show();
        } else {
            r_name="";
            r_time="";
            r_addr="";
            r_menu="";
            r_push="";

            r_name=nameEditText.getText().toString();
            r_time=timeEditText.getText().toString().replaceAll(" ", "_");
            r_addr=addrEditText.getText().toString();
            r_menu=menuEditText.getText().toString();
            if(et_num>0){
                for(EditText et : et_list){
                    if(!et.getText().toString().equals("")){
                        menu=menu+"@"+et.getText().toString();
                    }
                }
            }
            Log.v("menu",menu);
            r_menu=r_menu+menu;
            r_push=pushEditText.getText().toString();
            r_push=changeright(r_push);//全形轉半形,空格換行處理

            StrictMode.ThreadPolicy policy= new  StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            dialog = ProgressDialog.show(update.this, "資料上傳", "請等待", true);


            new Thread(new Runnable(){
                @Override
                public void run() {
                    try{

                        map = HTTPGetQuery("http://wteserver.me:8080/WTE/upload_raw_data.jsp?" + "name=" + r_name + "&address=" + r_addr
                        + "&time=" + r_time + "&comment=" + r_push + "&url=" + r_menu + "&star=" + r_star + "&type=" + r_class1 + "&mv=" + r_class2
                        + "&drink=" + r_class3 + "&lifecycle=" + r_class4 + "&uid=1");


                        Log.v("http", "http://140.120.13.89:8080/WTE/upload_raw_data.jsp?" + "name=" + r_name + "&address=" + r_addr
                                + "&time=" + r_time + "&comment=" + r_push + "&url=" + r_menu + "&star=" + r_star + "&type=" + r_class1 + "&mv=" + r_class2
                                + "&drink=" + r_class3 + "&lifecycle=" + r_class4 + "&uid=1");
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    finally{
                        dialog.dismiss();
                        myhandler.sendMessage(myhandler.obtainMessage());
                    }
                }
            }).start();

        }


    }

    Handler myhandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            new AlertDialogWrapper.Builder(update.this)
                    .setTitle("提醒")
                    .setMessage("上傳成功")
                    .setNegativeButton("了解", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }
    };

    static CookieSpecFactory csf = new CookieSpecFactory() {
        public CookieSpec newInstance(HttpParams params) {
            return new BrowserCompatSpec() {
                @Override
                public void validate(Cookie cookie, CookieOrigin origin)
                        throws MalformedCookieException {
                    // Oh, I am easy
                }
            };
        }
    };

    public static  Map<String, String> HTTPGetQuery(String hostURL) {
        // Declare a content string prepared for returning.
        String content = "";
        // Have an HTTP client to connect to the web service.
        DefaultHttpClient httpClient = new DefaultHttpClient();
        // Have an HTTP response container.
        HttpResponse httpResponse = null;
        // Have map container to store the information.
        Map<String, String> map = new HashMap<String, String>();

        httpClient.getCookieSpecs().register("easy", csf);
        httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 2000);

        // This try & catch is prepared for the IO exception in case.
        try {
            // Have a post method class with the query URL.
            HttpGet httpQuery = new HttpGet(hostURL);
            // The HTTP client do the query and have the string type response.
            httpResponse = httpClient.execute(httpQuery);

            // Read the HTTP headers and into content.
            //for (Header header : httpResponse.getAllHeaders()) {
            //     content += "\n" + header.toString();
            //}
            // Read the HTTP response content as an encoded string.
            content += EntityUtils.toString(httpResponse.getEntity());
        }

        // Catch the HTTP exception.
        catch (ClientProtocolException ex) {
            content = "ClientProtocolException:" + ex.getMessage();
        }
        // Catch the any IO exception.
        catch (IOException ex) {
            content = "IOException:" + ex.getMessage();
        }
        // The HTTP connection must be closed any way.
        finally {
            httpClient.getConnectionManager().shutdown();
        }


        // Check the HTTP connection is executed or not.
        if (httpResponse != null) {
            // Put the status code with status key.
            map.put("status", Integer.toString(httpResponse.getStatusLine().getStatusCode()));
            // Put the response content with content key
            map.put("content", content);
        } else {
            // Put the dummy with status key.
            map.put("status", "");
            // Put the dummy with content key
            map.put("content", "");
        }

        // Return result.
        return map;
    }
    public Spinner spinner1,spinner2,spinner3,spinner4;
    public ArrayAdapter adapter1, adapter2, adapter3, adapter4;
    private RatingBar ratingBar;
    public EditText menuEditText,nameEditText,timeEditText,addrEditText,pushEditText ;


    private String[] data1 = {"請選擇葷素食","葷食", "素食"};
    private String[] data2 = {"請選擇中西式","中式", "西式","其他"};
    private String[] data3 = {"請選擇飲料提供與否","有提供飲料", "無提供飲料"};
    private String[] data4 = {"請選擇用餐類型","早餐","午餐","早午餐", "下午茶","晚餐","宵夜", "點心"};

    public void updatedata(){

        nameEditText =  (EditText)findViewById(R.id.data_name);
        pushEditText = (EditText)findViewById(R.id.data_push);


        spinner1 = (Spinner)findViewById(R.id.data_class1);
        adapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,data1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "您選擇" + adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                r_class1=String.valueOf(position-1);
            }

            public void onNothingSelected(AdapterView arg0) {
                //Toast.makeText(MainActivity.this, "您沒有選擇任何項目", Toast.LENGTH_LONG).show();
            }
        });

        spinner2 = (Spinner)findViewById(R.id.data_class2);
        adapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,data2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "您選擇" + adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                r_class2=String.valueOf(position - 1);

            }

            public void onNothingSelected(AdapterView arg0) {
                //Toast.makeText(MainActivity.this, "您沒有選擇任何項目", Toast.LENGTH_LONG).show();
            }
        });

        spinner3 = (Spinner)findViewById(R.id.data_class3);
        adapter3 = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,data3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "您選擇" + adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                r_class3=String.valueOf(position - 1);
            }

            public void onNothingSelected(AdapterView arg0) {
                //Toast.makeText(MainActivity.this, "您沒有選擇任何項目", Toast.LENGTH_LONG).show();
            }
        });

        spinner4 = (Spinner)findViewById(R.id.data_class4);
        adapter4 = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,data4);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        spinner4.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "您選擇" + adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                r_class4=String.valueOf(position - 1);
            }

            public void onNothingSelected(AdapterView arg0) {
                //Toast.makeText(MainActivity.this, "您沒有選擇任何項目", Toast.LENGTH_LONG).show();
            }
        });

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,  boolean fromUser) {
                //ratingValue.setText(String.valueOf(rating));
                r_star=String.valueOf((int)(rating));
                Log.v("star",r_star);
            }
        });

        menuEditText= (EditText) findViewById(R.id.choose_image_button);

        menuEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Always use a TextKeyListener when clearing a TextView to prevent android
                    // warnings in the log
                    // TextKeyListener.clear((menuEditText).getText());
                    //pickImage();
                    new AlertDialogWrapper.Builder(update.this)
                            .setTitle("選取照片")
                            .setNegativeButton("相機", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    /*Intent picture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(picture, REQUEST_CODE_IMAGE);*/
                                    doTakePhoto();
                                }
                            })
                            .setPositiveButton("相簿", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    pickImage();
                                    dialog.dismiss();
                                }
                            })
                            .setNeutralButton("新增", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    et_num=et_num+1;
                                    copyEdit();
                                }
                            }).show();
                }
            }
        });


    }//updatedata


    public void copyEditInitial(){
        et_list.clear();

        et1 = (EditText)findViewById(R.id.et1);
        et2 =  (EditText)findViewById(R.id.et2);

        et_list.add(et1);
        et_list.add(et2);


    }

    //public int count=0;
    //RelativeLayout.LayoutParams  et_layout ;
    TextView tv ;
    RelativeLayout.LayoutParams params;



    public void copyEdit(){
        tv =(TextView)findViewById(R.id.choose_image_upload_status);
        Log.v("et_num",String.valueOf(et_num) + " "+ String.valueOf(et_list.size()));
        if(et_num<=et_list.size()){
            et_list.get(et_num-1).setVisibility(View.VISIBLE);
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, et_list.get(et_num-1).getId());
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            tv.setLayoutParams(params);
            et_list.get(et_num - 1).setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {

                        new AlertDialogWrapper.Builder(update.this)
                                .setTitle("選取照片")
                                .setNegativeButton("相機", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                       /*Intent picture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(picture, REQUEST_CODE_IMAGE);*/


                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("相簿", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        pickImage();

                                        dialog.dismiss();
                                    }
                                })
                                .setNeutralButton("新增", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        et_num = et_num + 1;
                                        copyEdit();
                                    }
                                }).show();
                    }
                }
            });
        }

    }

    protected void doTakePhoto() {
        try {
            // Launch camera to take photo for selected contact
            PHOTO_DIR.mkdirs();// 创建照片的存储目录
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
            Log.v("intent", "shit star");
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            Log.v("intent","shit 2");
            Log.v("intent",intent.toString());
            startActivityForResult(intent, CAMERA_WITH_DATA);
            Log.v("intent", "shit 1");
            //startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        Log.v("intent","shit after");
        Log.v("intent",intent.toString());

        return intent;
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date) + ".jpg";
    }


    /**
     *pickimage
     * **/
    private static final int REQ_CODE_PICK_IMAGE = 1;
    public void pickImage() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQ_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case REQ_CODE_PICK_IMAGE: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    Log.v("selectedImage", selectedImage.toString());
                    getChooseImageFragment().setImage(selectedImage);


                    break;
                }
            }
            case  REQUEST_CODE_IMAGE :{
                if (resultCode == RESULT_OK) { //使用者按下確定

                    //取得圖片位址
                    Uri uri = imageReturnedIntent.getData();
                    getChooseImageFragment().setImage(uri);


                    break;
                }//result ok
            }//REQUEST_CODE_IMAGE
            case CAMERA_WITH_DATA: {// 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                Log.v("intent","shit 3");
                Log.v("intent", imageReturnedIntent.toString());
               /* Uri uri = imageReturnedIntent.getData();
                getChooseImageFragment().setImage(uri);*/
                doCropPhoto(mCurrentPhotoFile);
                //pickImage();
                break;
            }
            case PHOTO_PICKED_WITH_DATA: {// 调用Gallery返回的
                //final Bitmap photo = imageReturnedIntent.getParcelableExtra("data");
                final Bitmap photo =imageReturnedIntent.getParcelableExtra("data");
                Log.v("photo",photo.toString());
                // 下面就是显示照片了
                System.out.println(photo);


                break;
            }

        }
    }

    protected void doCropPhoto(File f) {
        try {
            // 启动gallery去剪辑这个照片
            final Intent intent = getCropImageIntent(Uri.fromFile(f));
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 80);
        intent.putExtra("outputY", 80);
        intent.putExtra("return-data", true);
        return intent;
    }

    private ChooseImageFragment getChooseImageFragment() {
        return (ChooseImageFragment) getSupportFragmentManager().findFragmentById(R.id.choose_image_fragment);
    }


}