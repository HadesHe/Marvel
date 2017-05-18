package com.avenging.hades.core.data.ui.list;

import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.core.data.ui.base.RemoteView;

import java.util.List;

/**
 * Created by Hades on 2017/5/17.
 */

public interface ListContract {

    interface ViewActions{

        void onInitialListRequested();
    }

    interface ListView extends RemoteView{

        void showCharacters(List<CharacterMarvel> responseResults);

        void showSearchedCharacter(List<CharacterMarvel> responseResults);
    }
}
