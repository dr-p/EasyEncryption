package com.chomptech.easyencryption;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IntegerRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private EditText plain;
    private EditText ciphertext;
    private EditText shift;
    private Button encryptButton;
    private ArrayList<String> mApps;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
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
        encryptButton = (Button)findViewById(R.id.buttonEncrypt);


        ciphertext = (EditText)findViewById(R.id.editTextEncrypted);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        mApps = new ArrayList<>();
        mApps.add(getString(R.string.easyaudio));
        mApps.add(getString(R.string.easynote));
        mApps.add(getString(R.string.easytip));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlay);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mApps));

        // To be implemented custom onclick listener class
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

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
    public void encryptSubstitutionShift(View view) {
        //
        // int negNums = 0;
        String key = shift.getText().toString();
        /*
        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) == '-') {
                negNums =+ 1;
            }
        }
        if (negNums > 1) {
            key.replaceAll("-", "");
            key = "-" + key;
        }*/
        if (key.contains(".")) {
            key = key.replaceAll(".", "");
        }
        if (key.contains("-")) {
            key = key.replaceAll("-", "");
            key = "-" + key;
        }
        shift.setText(key);
        //if (encryptButton.getText().toString().equals("Encrypt Message")) {
            //plain.setText(plain.getText().toString().replaceAll("[^a-zA-z ]", "").toLowerCase());
            //ciphertext.setText(cipher(plain.getText().toString(), Integer.valueOf(key)));
        //} else {
            ciphertext.setText(cipher(plain.getText().toString(), Integer.valueOf(key)));
        //}
    }
    public String cipher (String txt, int shift) {
        char[] tempBuffer = txt.toCharArray();

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
        //encryptButton.setText("Encrypt Message");

    }
    public void swapTexts(MenuItem menuItem) {
        if (!plain.getText().toString().equals("") && !shift.getText().toString().equals("")) {
            String temp = ciphertext.getText().toString();
            ciphertext.setText(plain.getText().toString());
            plain.setText(temp);
            shift.setText(String.valueOf(-Integer.valueOf(shift.getText().toString())));
            /*
            if (encryptButton.getText().toString().equals("Encrypt Message")) {
                encryptButton.setText("Decrypt Message");
            } else {
                encryptButton.setText("Encrypt Message");
            }*/
        }
    }
}
