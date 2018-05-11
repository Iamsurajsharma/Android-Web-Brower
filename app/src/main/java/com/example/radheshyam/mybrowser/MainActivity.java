package com.example.radheshyam.mybrowser;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    ImageButton UrlgoButton;
    String Add;
    View blufilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.urleditText);
        UrlgoButton = (ImageButton) findViewById(R.id.gobutton);
       blufilter = (View) findViewById(R.id.blueView);
        Add = editText.getText().toString();

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    OnClicksendUrl(null);
                    return true;
                }
                return false;
            }
        });
    }

    public void OnClicksendUrl(View view) {

        Intent sendurlIntent = new Intent(this, brows.class);

        sendurlIntent.putExtra("Edit", editText.getText().toString());

        startActivity(sendurlIntent);
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
        switch (item.getItemId())
        { case R.id.aboutUs:

                   Intent StartIntent = new Intent(this, about.class);
                   startActivity(StartIntent);
                    return true;

            case R.id.translator:
                Intent StartIntenttranslator = new Intent(this, translator.class);
                startActivity(StartIntenttranslator);
                return true;

            case R.id.exit:
                onBackPressed();
                return true;

            case R.id.bluelight1:
                blufilter.setVisibility(View.VISIBLE);
                editText.setTextColor(getResources().getColor(R.color.colorAccent));
                return true;

            case R.id.bluelight2:

                editText.setTextColor(getResources().getColor(R.color.colorAccent));
                blufilter.setVisibility(View.INVISIBLE);
                return true;


            case R.id.settings:

                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                return true;

            case R.id.history:
                Intent Starthistory= new Intent(this, history.class);
                startActivity(Starthistory);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)


                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}


