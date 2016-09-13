package com.chomptech.easyencryption;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
        ciphertext = (EditText)findViewById(R.id.editTextEncrypted);
        shift = (EditText)findViewById(R.id.editTextShifts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        plain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (plain.getText().toString().equals("Enter plaintext here")) {
                    plain.setText("");
                } else {

                }
            }
        });
        shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shift.getText().toString().equals("Enter # shifts here")) {
                    shift.setText("");
                } else {

                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /*
    String cipher(String msg, int shift){
    String s = "";
    int len = msg.length();
    for(int x = 0; x < len; x++){
        char c = (char)(msg.charAt(x) + shift);
        if (c > 'z')
            s += (char)(msg.charAt(x) - (26-shift));
        else
            s += (char)(msg.charAt(x) + shift);
    }
    return s;
}
     */
    public void encrypt(View view) {
        String plaint = plain.getText().toString();
        ciphertext.setText(cipher(plaint, Integer.valueOf(shift.getText().toString())));
    }
    public String cipher (String txt, int shift) {
        String temp = "";
        int strLength = txt.length();
        for (int i = 0; i < strLength; i++) {
            char c = (char)(txt.charAt(i) + shift);
            if (c > 'z')
                temp += (char)(txt.charAt(i) - (26-shift));
            else
                temp += (char)(txt.charAt(i) + shift);
        }
        return temp;
    }
}
