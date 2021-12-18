package com.group22.news_management.view.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.group22.news_management.utils.HTMLParser;
import com.group22.newsmanagerment.R;

import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity {

    private BottomAppBar bottomAppBar;
    private WebView webView1;
    private Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initViews();
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        long newsId = intent.getLongExtra("id", 0);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setAllowContentAccess(true);
//        webView.getSettings().setAppCacheEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setAllowFileAccessFromFileURLs(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("file:///android_asset/news.html");
        HTMLParser htmlParser = new HTMLParser(this);
        htmlParser.execute(link);
        bottomAppBarEventOnItemClick(newsId);
    }

    private void bottomAppBarEventOnItemClick(long newsId) {
        bottomAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.icon_bookmark:{
                    Toast.makeText(NewsDetailActivity.this, "icon_bookmark", Toast.LENGTH_SHORT).show();
                    break;
                }
                case R.id.icon_chat:{
                    Intent intent = new Intent(NewsDetailActivity.this, CommentActivity.class);
                    intent.putExtra("newsId", newsId);
                    startActivity(intent);
                    break;
                }
                case R.id.icon_font_size:{
                    Toast.makeText(NewsDetailActivity.this, "icon_font_size", Toast.LENGTH_SHORT).show();
                   /* webView1 = (WebView) findViewById(R.id.webView);
                    spinner1 = (Spinner) findViewById(R.id.icon_font_size);
                    ArrayList<String> arraySize = new ArrayList<String>();
                    arraySize.add("To");
                    arraySize.add("Vua");
                    arraySize.add("Nho");
                    ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySize);
                    spinner1.setAdapter(arrayAdapter);
                    final WebSettings webSettings = webView1.getSettings();
                    //webSettings.setTextSize(WebSettings.TextSize.SMALLEST);
                    Resources res = getResources();
                    float fontSize = res.getInteger(R.integer.size_To);
                    webSettings.setDefaultFontSize((int) fontSize);*/
                    break;
                }
            }
            return false;
        });
    }

    private void initViews() {
        bottomAppBar = findViewById(R.id.bottomAppBar);
    }

}