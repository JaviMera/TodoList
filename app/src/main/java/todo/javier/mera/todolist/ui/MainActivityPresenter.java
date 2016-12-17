package todo.javier.mera.todolist.ui;

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
}
