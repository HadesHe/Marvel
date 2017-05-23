package com.avenging.hades.core.data.ui.character;

import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.core.data.ui.base.RemoteView;

/**
 * Created by Hades on 2017/5/17.
 */

public interface CharacterContract {

    interface ViewActions{


        void onCharacterRequested(long id);
    }

    interface CharacterView extends RemoteView {

        void showCharacter(CharacterMarvel mCharacter);
    }

}
