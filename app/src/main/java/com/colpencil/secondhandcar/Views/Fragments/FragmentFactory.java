package com.colpencil.secondhandcar.Views.Fragments;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.colpencil.secondhandcar.Views.Fragments.BuyCar.BuyCarFragment;
import com.colpencil.secondhandcar.Views.Fragments.Home.HomeFragment;
import com.colpencil.secondhandcar.Views.Fragments.Personal.PersonalFragment;
import com.colpencil.secondhandcar.Views.Fragments.SellCar.SellCarFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/22.
 */

public class FragmentFactory {

    private static Map<Integer, Fragment> map = new HashMap<>();

    public static Fragment createFragment(int position, AppCompatActivity activity){
        Fragment fragment = null;
        if(fragment == null){
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new BuyCarFragment();
                    break;
                case 2:
                    fragment = new SellCarFragment();
                    break;
                case 3:
                    fragment = new PersonalFragment();
                    break;
            }
        }
        return fragment;
    }
}
