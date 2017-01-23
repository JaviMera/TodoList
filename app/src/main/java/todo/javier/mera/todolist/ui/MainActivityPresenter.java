package todo.javier.mera.todolist.ui;

import java.util.Map;

import todo.javier.mera.todolist.model.ItemBase;

/**
 * Created by javie on 12/17/2016.
 */

public class MainActivityPresenter {

    private MainActivityView mView;

    public MainActivityPresenter(MainActivityView view) {

        mView = view;
    }


    public void updateToolbarBackground(int color) {

        mView.updateToolbarBackground(color);
    }

    public void showFabButton() {


        mView.showFabButton();
    }

    public void toggleBackButton(boolean canDisplay) {

        mView.toggleBackButton(canDisplay);
    }

    public void setToolbarTitle(String title) {

        mView.setToolbarTitle(title);
    }

    public void setToolbar() {

        mView.setToolbar();
    }

    public void setFabVisibility(int visibility) {

        mView.setFabVisibility(visibility);
    }

    public void showSnackBar(String message, String action, Map<Integer, ItemBase> items) {

        mView.showSnackBar(message,action,items);
    }

    public void setIndicator(int resourceId) {

        mView.setIndicator(resourceId);
    }

}
