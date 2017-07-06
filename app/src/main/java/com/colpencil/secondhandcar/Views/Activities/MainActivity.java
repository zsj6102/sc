package com.colpencil.secondhandcar.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.PermissionTool;
import com.colpencil.secondhandcar.Views.Fragments.BuyCar.BuyCarFragment;
import com.colpencil.secondhandcar.Views.Fragments.Home.HomeFragment;
import com.colpencil.secondhandcar.Views.Fragments.Personal.PersonalFragment;
import com.colpencil.secondhandcar.Views.Fragments.Personal.Release.ReleaseFragment;
import com.colpencil.secondhandcar.Views.Fragments.SellCar.ReleaseCarHtmlFragment;
import com.colpencil.secondhandcar.Views.Fragments.SellCar.SellCarFragment;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    @Bind(R.id.vp)
    ViewPager vp;

    @Bind(R.id.rb_group)
    RadioGroup rb_group;

    @Bind(R.id.rb_home)
    RadioButton rb_home;

    @Bind(R.id.rb_buy)
    RadioButton rb_buy;

    @Bind(R.id.rb_sell)
    RadioButton rb_sell;

    @Bind(R.id.rb_mine)
    RadioButton rb_mine;

    private ColpencilFrame colpencilFrame;

    private long mExitTime;
    private Fragment[] fragments;
    private HomeFragment homeFragment;
    private BuyCarFragment buyCarFragment;
    private SellCarFragment sellCarFragment;
    private ReleaseFragment releaseFragment;
    private PersonalFragment personalFragment;
    private ReleaseCarHtmlFragment releaseCarHtmlFragment;

    private Observable<RxBusMsg> observable;
    private Observable<RxBusMsg> getObservable;
    private MyViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        colpencilFrame = ColpencilFrame.getInstance();
        colpencilFrame.addActivity(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.title_color));
        homeFragment = new HomeFragment();
        buyCarFragment = new BuyCarFragment();
        sellCarFragment = new SellCarFragment();
        releaseFragment = new ReleaseFragment();
        personalFragment = new PersonalFragment();
        releaseCarHtmlFragment = new ReleaseCarHtmlFragment();
        fragments = new Fragment[]{homeFragment, buyCarFragment, sellCarFragment, releaseCarHtmlFragment, personalFragment};
        pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(pagerAdapter);
        vp.setOffscreenPageLimit(4);
        rb_group.setOnCheckedChangeListener(this);
        initBus();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionTool.getInstance().handlePermissionCallback(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PermissionTool.getInstance().handleSpecialPermissionCallback(this, requestCode, resultCode, data);
    }

    private void initBus(){
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                switch (rxBusMsg.getCarType()) {
                    case 0:
                        vp.setCurrentItem(1, false);
                        rb_buy.setChecked(true);
                        break;
                    case 1:
                        if(rxBusMsg.getChange() == 1){
                            RxMsg msg = new RxMsg();
                            msg.setCarId(rxBusMsg.getCarId());
                            RxBus.get().post("change", msg);
                        }
                        vp.setCurrentItem(2, false);
                        pagerAdapter.notifyDataSetChanged();
                        rb_sell.setChecked(true);
                        break;
                }
            }
        };
        observable.subscribe(subscriber);
        getObservable = RxBus.get().register("rxIsLogin", RxBusMsg.class);
        Subscriber<RxBusMsg> busMsgSubscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                if(rxBusMsg.getCarType() == 1){
                    vp.setCurrentItem(3, false);
                    pagerAdapter.notifyDataSetChanged();
                    rb_mine.setChecked(true);
                }
            }
        };
        getObservable.subscribe(busMsgSubscriber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
        RxBus.get().unregister("rxIsLogin", getObservable);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                vp.setCurrentItem(0, false);
                break;
            case R.id.rb_buy:
                vp.setCurrentItem(1, false);
                break;
            case R.id.rb_sell:
                vp.setCurrentItem(2, false);
                break;
            case R.id.rb_mine:
                vp.setCurrentItem(3, false);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                MobclickAgent.onKillProcess(this);
                colpencilFrame.finishAllActivity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        FragmentManager fm;

       public MyViewPagerAdapter(FragmentManager fm){
            super(fm);
           this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return fragments[position];
            } else if(position == 1){
                return fragments[position];
            } else if(position == 2){
                if(SharedPreferencesUtil.getInstance(MainActivity.this).getInt("is_store") == 1){
                    return fragments[position + 1];
                } else {
                    return fragments[position];
                }
            } else {
                return fragments[position + 1];
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //得到缓存的fragment
            Fragment fragment = (Fragment) super.instantiateItem(container,
                    position);
            //得到tag，这点很重要
            String fragmentTag = fragment.getTag();
            //如果这个fragment需要更新
            if(position == 2 && SharedPreferencesUtil.getInstance(MainActivity.this).getInt("is_store") == 1 && fragment != fragments[3]){
                FragmentTransaction ft = fm.beginTransaction();
                //移除旧的fragment
                ft.remove(fragment);
                //换成新的fragment
                fragment = fragments[position + 1];
                //添加新fragment时必须用前面获得的tag，这点很重要
                ft.add(container.getId(), fragment, fragmentTag);
                ft.attach(fragment);
                ft.commitNowAllowingStateLoss();
            } else if(position == 2 && SharedPreferencesUtil.getInstance(MainActivity.this).getInt("is_store") == 0 && fragment == fragments[3]){
                FragmentTransaction ft = fm.beginTransaction();
                //移除旧的fragment
                ft.remove(fragment);
                //换成新的fragment
                fragment = fragments[2];
                //添加新fragment时必须用前面获得的tag，这点很重要
                ft.add(container.getId(), fragment, fragmentTag);
                ft.attach(fragment);
                ft.commitNowAllowingStateLoss();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return fragments.length-1;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }
}
