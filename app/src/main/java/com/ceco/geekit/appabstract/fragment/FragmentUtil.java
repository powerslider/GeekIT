package com.ceco.geekit.appabstract.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 11 Oct 2015
 */
public class FragmentUtil {

    public static void initFragment(int container, Fragment frag, FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(container, frag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    public static void swapFragment(int container1, int container2, FragmentManager fm) {
        Fragment f1 = fm.findFragmentById(container1);
        Fragment f2 = fm.findFragmentById(container2);

        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(f1);
        ft.remove(f2);
        ft.commit();
        fm.executePendingTransactions();

        ft = fm.beginTransaction();
        ft.add(container1, f2);
        ft.add(container2, f1);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        ft.commit();
    }

    public static void removeFragment(Fragment f, FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(f);
        ft.commit();
    }

    public static void removeFragment(int container, FragmentManager fm) {
        Fragment f = fm.findFragmentById(container);
        removeFragment(f, fm);
    }

    public static void replaceFragment(int container, Fragment fragment, FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(container, fragment);
        // Replace whatever is in the fragment container view with this fragment,
        // and add the transaction to the backStack so the user can navigate back
        ft.addToBackStack(null);
        ft.commit();
    }
}
