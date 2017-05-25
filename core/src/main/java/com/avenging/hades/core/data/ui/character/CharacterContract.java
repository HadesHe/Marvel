package com.avenging.hades.core.data.ui.character;

import com.avenging.hades.core.data.model.CharacterMarvel;
import com.avenging.hades.core.data.model.Comic;
import com.avenging.hades.core.data.ui.base.RemoteView;

import java.util.List;

/**
 * Created by Hades on 2017/5/17.
 */

public interface CharacterContract {

    interface ViewActions{


        void onCharacterRequested(long id);

        void onCharacterComicsRequested(long id, int size);

        void onCharacterSeriesRequested(long id, int size);

        void onCharacterStoriesRequested(long id, int size);

        void onCharacterEventRequested(long id, int size);
    }

    interface CharacterView extends RemoteView {

        void showCharacter(CharacterMarvel mCharacter);

        void showComicList(List<Comic> results);

        void showSeriesList(List<Comic> results);

        void showStoriesList(List<Comic> results);

        void showEventsList(List<Comic> results);
    }

}
