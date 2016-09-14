package com.chomptech.easyencryption;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity {
    private EditText plain;
    private EditText ciphertext;
    private EditText shift;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initialize Admob */
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-9999083812241050~1032266036");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        plain = (EditText)findViewById(R.id.editTextPlaintext);
        shift = (EditText)findViewById(R.id.editTextShifts);


        ciphertext = (EditText)findViewById(R.id.editTextEncrypted);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (sharedText != null) {
                plain.setText(sharedText);
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void encrypt(View view) {
        String plaint = plain.getText().toString();
        ciphertext.setText(cipher(plaint, Integer.valueOf(shift.getText().toString())));
    }
    public String cipher (String txt, int shift) {
        char[] tempBuffer = txt.toLowerCase().toCharArray();

        for (int i = 0; i < tempBuffer.length; i++) {
            char temp = tempBuffer[i];
            temp = (char) (temp + shift);
            if (temp - shift == ' ') {
                temp = '*';
            } else if (temp - shift == '*') {
                temp = ' ';
            } else {
                if (temp > 'z') {
                    temp = (char) (temp - 26);
                } else if (temp < 'a') {
                    temp = (char) (temp + 26);
                }
            }
            tempBuffer[i] = temp;
        }
        return new String(tempBuffer);
    }
    public void shareCipher(MenuItem menuItem) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, ciphertext.getText().toString());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share note to..."));
    }
    public void clearText(MenuItem menuItem) {
        shift.setText("");
        plain.setText("");
        ciphertext.setText("Encrypted message will appear here");
    }
}
