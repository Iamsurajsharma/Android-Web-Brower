package com.example.radheshyam.mybrowser;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.radheshyam.mybrowser.R.id.history;
import static com.example.radheshyam.mybrowser.R.id.webView;
import static com.example.radheshyam.mybrowser.R.id.wrap_content;

/**
 * Created by Radhe Shyam on 10/18/2017.
 */


public class brows extends Activity implements View.OnClickListener {
    public EditText idbrow;
    public String Url, historyUrl;
    public WebView Web;
    ImageView ic_google, ic_URl;
    ImageButton go, refreshBut, backBut, forwardBut;

    databaseAdapter databaseAdapter;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brows);

        databaseAdapter = new databaseAdapter(this);

        go = (ImageButton) findViewById(R.id.brobutton);
        refreshBut = (ImageButton) findViewById(R.id.refresh);
        backBut = (ImageButton) findViewById(R.id.back);
        forwardBut = (ImageButton) findViewById(R.id.forw);

        ImageView icon = new ImageView(this); // Create an icon
        // icon.setImageDrawable(R.drawable.cross);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.cross));
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        ImageView history = new ImageView(this); // Create an icon
        // icon.setImageDrawable(R.drawable.cross);
        history.setImageDrawable(getResources().getDrawable(R.drawable.clock));

        ImageView cache = new ImageView(this); // Create an icon
        // icon.setImageDrawable(R.drawable.cross);
        cache.setImageDrawable(getResources().getDrawable(R.drawable.broom));


        FloatingActionButton.LayoutParams params = new FloatingActionButton.LayoutParams(130, 130);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        SubActionButton history_button = itemBuilder.setContentView(history).build();
        history_button.setId(R.id.historyButton);
        history_button.setLayoutParams(params);

        SubActionButton.Builder itemBuilder2 = new SubActionButton.Builder(this);
        SubActionButton cache_button = itemBuilder2.setContentView(cache).build();
        cache_button.setId(R.id.cacheButton);
        cache_button.setLayoutParams(params);

        history_button.setOnClickListener(this);
        cache_button.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(cache_button)
                .addSubActionView(history_button)
                // ...
                .attachTo(actionButton)
                .build();


        idbrow = (EditText) findViewById(R.id.editTextBrows);

        Web = (WebView) findViewById(webView);


        Web.getSettings().setJavaScriptEnabled(true);
        Web.getSettings().setLoadsImagesAutomatically(true);
        Web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        Web.setWebViewClient(new WebViewClient());
        Web.getSettings().setBuiltInZoomControls(true);
        Web.getSettings().setDisplayZoomControls(false);
        Web.getSettings().setLoadWithOverviewMode(true);
        Web.getSettings().setUseWideViewPort(true);


        ic_google = (ImageView) findViewById(R.id.icongoogle);
        ic_URl = (ImageView) findViewById(R.id.iconUrl);

        Intent intent = getIntent();
        Url = intent.getExtras().getString("Edit");
        idbrow.append("" + Url);
        go.setOnClickListener(brows.this);


        if (Url.isEmpty()) {
            alert_nothing();
        } else
            urlchecker(Url);

        idbrow.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    idbrow = (EditText) findViewById(R.id.editTextBrows);
                    Url = idbrow.getText().toString();
                    Web = (WebView) findViewById(webView);
                    if (Url.isEmpty()) {
                        alert_nothing();
                    } else
                        urlchecker(Url);
                    return true;
                }
                return false;
            }
        });


    }

    public void historyfn(String hisUrl) {


        WebBackForwardList mWebBackForwardList = Web.copyBackForwardList();
        if (mWebBackForwardList.getCurrentIndex() > 0)
            historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex() - 1).getUrl();
       if(historyUrl!=null) {
            inserting_history(historyUrl);
        }
        Toast.makeText(brows.this, historyUrl, Toast.LENGTH_SHORT).show();
    }

    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    public void urlchecker(String Url) {
        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(Url);//replace with string to compare

        historyfn(Url);

        if (m.find()) {

            myInternet(Url);
        } else {
            gSearch(Url);
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.brobutton:
                idbrow = (EditText) findViewById(R.id.editTextBrows);
                Url = idbrow.getText().toString();
                Web = (WebView) findViewById(webView);
                if (Url.isEmpty()) {
                    alert_nothing();
                } else
                    urlchecker(Url);
                break;
            case R.id.historyButton:

                Toast.makeText(brows.this, "History is cleared now", Toast.LENGTH_SHORT).show();
                Web.clearHistory();
                break;

            case R.id.cacheButton:
                Web.clearCache(true);
                Toast.makeText(brows.this, "Cache memory is cleared now", Toast.LENGTH_SHORT).show();
                break;


        }


    }

    @Override
    public void onBackPressed() {
        if (Web.canGoBack()) {
            Web.goBack();
        } else super.onBackPressed();
    }


    public void OnclickRefresh(View view) {
        Web.reload();
    }

    public void OnlclickBack(View view) {
        if (Web.canGoBack()) {
            Web.goBack();
        } else {
            super.onBackPressed();
        }

    }

    public void Onclickforwd(View view) {

        if (Web.canGoForward()) {
            Web.goForward();
        }
    }


    public void myInternet(String Url) {

        ic_google.setVisibility(View.INVISIBLE);
        ic_URl.setVisibility(View.VISIBLE);

        Web.getSettings().setLoadWithOverviewMode(true);
        Web.getSettings().setUseWideViewPort(true);
        Web.getSettings().setJavaScriptEnabled(true);
        Web.setWebViewClient(new WebViewClient());
        try {
            Web.loadUrl("https://" + Url);
        } catch (Exception e) {
            e.printStackTrace();


        }

    }

    public void gSearch(String Url) {

        ic_google.setVisibility(View.VISIBLE);
        ic_URl.setVisibility(View.INVISIBLE);

        Web.getSettings().setLoadWithOverviewMode(true);
        Web.getSettings().setUseWideViewPort(true);
        Web.getSettings().setJavaScriptEnabled(true);
        Web.setWebViewClient(new WebViewClient());
        try {
            Web.loadUrl("https://www.google.co.in/search?q=" + Url + "&oq=" + Url + "&aqs=chrome..69i57j69i60l5.13340j0j4&sourceid=chrome&ie=UTF-8");

        } catch (Exception e) {
            e.printStackTrace();


        }

    }

    private void alert_nothing() {
        AlertDialog.Builder alert = new AlertDialog.Builder(brows.this);

        alert.setMessage("ERROR\nSearch or type Url");
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    public void inserting_history(String historyUrl) {

        long id = databaseAdapter.insertdata(historyUrl);
        if (id < 0) {
            Toast.makeText(brows.this, "Unsuccess", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(brows.this, "success", Toast.LENGTH_SHORT).show();

        }
    }
}













