package todo.javier.mera.todolist.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

/**
 * Created by javie on 12/3/2016.
 */

public class FragmentHelper {

    private FragmentManager mManager;

    public FragmentHelper(FragmentManager manager) {

        mManager = manager;
    }

    public void replace(int containerId, Fragment fragment) {

        mManager
            .beginTransaction()
            .replace(containerId, fragment, "fragment_recycler")
            .commit();
    }

    public void replaceWithBackStack(int containerId, Fragment fragment, String name) {

        mManager
            .beginTransaction()
            .replace(containerId, fragment, "fragment_recycler")
            .addToBackStack(name)
            .commit();
    }

    public Fragment findFragment(String tag) {

        return mManager.findFragmentByTag(tag);
    }
}