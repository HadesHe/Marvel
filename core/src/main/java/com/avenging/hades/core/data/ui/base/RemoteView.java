package com.avenging.hades.core.data.ui.base;

/**
 * Created by Hades on 2017/5/17.
 */
public interface RemoteView {

    void showProgress();

    void hideProgress();

    void showUnauthorizedError();

    void showEmpty();

    void showError(String errorMessage);

    void showMessageLayout(boolean show);
}
