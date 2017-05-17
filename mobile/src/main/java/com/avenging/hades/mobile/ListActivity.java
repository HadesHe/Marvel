package com.avenging.hades.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.avenging.hades.mobile.base.BaseActivity;
import com.avenging.hades.mobile.list.ListFragment;

public class ListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if(savedInstanceState==null){
            getSupportFragmentManager()
                    .beginTransaction().replace(R.id.list_container, ListFragment.newInstance())
                    .commit();
        }
    }
}
