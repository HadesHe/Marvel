package com.avenging.hades.mobile.list;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.avenging.hades.mobile.R;
import com.avenging.hades.mobile.base.BaseActivity;
import com.avenging.hades.mobile.list.ListFragment;

public class ListActivity extends BaseActivity {

    private static final String KEY_MARVEL_COPY_RIGHT ="key_marvel_copy_right" ;

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

        showCopyRightSnackbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showCopyRightSnackbar() {
        final SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean(KEY_MARVEL_COPY_RIGHT,false)) {
            Log.i("ListActivity","User already knows");
            return ;
        }

        final Snackbar copyrightSnackbar=Snackbar.make(findViewById(R.id.list_container),
                getString(R.string.marvel_copyright_notice),Snackbar.LENGTH_INDEFINITE);
        copyrightSnackbar.setAction(R.string.action_acknowledge, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putBoolean(KEY_MARVEL_COPY_RIGHT,true).apply();
                copyrightSnackbar.dismiss();
            }
        });
        copyrightSnackbar.show();

    }
}
