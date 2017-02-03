package todo.javier.mera.todolist.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

import todo.javier.mera.todolist.R;

/**
 * Created by javie on 12/3/2016.
 */

class FragmentHelper {

    private FragmentManager mManager;

    FragmentHelper(FragmentManager manager) {

        mManager = manager;
    }

    void replace(int containerId, Fragment fragment, String tag) {

        mManager
            .beginTransaction()
            .replace(containerId, fragment, tag)
            .commit();
    }

    void replaceWithBackStack(int containerId, Fragment fragment, String tag, String name) {

        mManager
            .beginTransaction()
            .replace(containerId, fragment, tag)
            .addToBackStack(name)
            .commit();
    }

    Fragment findFragment(String tag) {

        return mManager.findFragmentByTag(tag);
    }
}