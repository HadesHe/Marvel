package com.avenging.hades.core.data.ui.character;

import com.avenging.hades.core.data.DataManager;
import com.avenging.hades.core.data.ui.base.BasePresenter;

/**
 * Created by Hades on 2017/5/17.
 */

public class CharacterPresenter extends BasePresenter<CharacterContract.CharacterView> implements CharacterContract.ViewActions {
    private final DataManager mDataManager;

    public CharacterPresenter(DataManager instance) {
        mDataManager=instance;
    }

}
