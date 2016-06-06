package com.ketecode.ghpaymentsdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MPowerWebsiteActivity extends AppCompatActivity {

    WebView mpowerWebsite;
    WebSettings mpowerWebSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpower_website);

        Intent webIntent = getIntent();
        String url = webIntent.getStringExtra("URL");
        Log.i("Browser Activity",url);
        mpowerWebsite = (WebView)findViewById(R.id.mpowerWebView);
        mpowerWebSettings = mpowerWebsite.getSettings();

        mpowerWebSettings.setJavaScriptEnabled(true);
        boolean fwd = mpowerWebsite.canGoForward();
        Log.i("Can go forward", "" + fwd);

        String summary = "<html><body><h3>Loading...</h3></body></html>";
        mpowerWebsite.loadData(summary, "text/html", null);

        mpowerWebsite.loadUrl(url);
        //mpowerWebsite.canGoBackOrForward(2);


    }

    public class LoadPage extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }
}
