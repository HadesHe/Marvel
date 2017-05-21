package com.avenging.hades.mobile.character;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.mobile.R;
import com.avenging.hades.mobile.base.BaseActivity;

public class CharacterActivity extends BaseActivity {

    private static final String EXTRA_CHARACTER_MARVEL = "extra_character_marvel";

    public static Intent newStartIntent(Context context, CharacterMarvel characterMarvel) {
        Intent intent=new Intent(context,CharacterActivity.class);
        intent.putExtra(EXTRA_CHARACTER_MARVEL,characterMarvel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CharacterMarvel characterMarvel=getIntent().getParcelableExtra(EXTRA_CHARACTER_MARVEL);
        if(characterMarvel==null){
            throw new IllegalArgumentException("CharacterActivity requires a characterMarvel instance");
        }

        setContentView(R.layout.activity_character);
        supportPostponeEnterTransition();
        if(savedInstanceState==null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.character_container,CharacterFragment.newInstance(characterMarvel))
                    .commit();
        }


    }
}
