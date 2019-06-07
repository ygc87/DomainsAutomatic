package com.pearce.domainsautomatic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pearce.domainsautomatic.utils.AssetManagerUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.filter_domains, R.id.generate_config, R.id.generate_vhost})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_domains:
                AssetManagerUtils.filterDomains("domains.txt", this, "cn");
                break;
            case R.id.generate_config:
                AssetManagerUtils.generateConfig(this);
                break;
            case R.id.generate_vhost:
                AssetManagerUtils.generateVHost(this);
                break;
        }
    }
}
