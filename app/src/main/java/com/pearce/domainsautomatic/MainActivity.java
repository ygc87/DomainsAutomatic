package com.pearce.domainsautomatic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pearce.domainsautomatic.utils.AssetManagerUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManagerUtils.filterDomains("domains.txt", this, "com|net");
    }
}
