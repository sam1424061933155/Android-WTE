package com.example.sam14240619331555.food_app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by sam14240619331555 on 2016/1/23.
 */
public class register extends Activity {

    WebView mWebView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.loadUrl("http://www.google.com");
    }
    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };


}

