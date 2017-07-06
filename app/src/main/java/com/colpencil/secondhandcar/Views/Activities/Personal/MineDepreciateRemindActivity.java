package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.MineRemind;
import com.colpencil.secondhandcar.Bean.Response.ReducePriceCar;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.ResultInfo;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Mine.ReducePricePresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Ui.AlertDialog;
import com.colpencil.secondhandcar.Ui.FullyLinearLayoutManager;
import com.colpencil.secondhandcar.Views.Activities.Buy.CarDetailsActivity;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Adapter.Mine.MineRemindAdapter;
import com.colpencil.secondhandcar.Views.Adapter.Mine.SoldRemindAdapter;
import com.colpencil.secondhandcar.Views.Imples.Mine.ReducePriceView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/12.
 * 我的降价提醒
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_depreciate_remind)
public class MineDepreciateRemindActivity extends ColpencilActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener, ReducePriceView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_rigth)
    RelativeLayout rl_right;

    @Bind(R.id.tv_rigth)
    ImageView iv_right;

    @Bind(R.id.swipe_remind)
    SwipeRefreshLayout sp_remind;

    @Bind(R.id.list_remind)
    SwipeMenuRecyclerView lv_remind;

    @Bind(R.id.list_undercarriage)
    SwipeMenuRecyclerView lv_undercarriage;

    @Bind(R.id.empty)
    LinearLayout empty;

    @Bind(R.id.sc)
    ScrollView sv;

    @Bind(R.id.text_)
    TextView tv_;

    private MineRemindAdapter remindAdapter;
    private SoldRemindAdapter soldRemindAdapter;
    private List<ReducePriceCar> mineReminds;
    private List<ReducePriceCar> soldReminds;
    private ReducePricePresenter presenter;
    private Intent intent;
    private int currentPosition;
    private int flag = 0;
    private FullyLinearLayoutManager linearLayoutManager;
    private FullyLinearLayoutManager layoutManager;

    @Override
    protected void initViews(View view) {
        tv_title.setText("我的降价提醒");
        showLoading("加载中");
        iv_right.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
        sv.setVisibility(View.GONE);
        tv_.setVisibility(View.GONE);
        sp_remind.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        sp_remind.setOnRefreshListener(this);

        mineReminds = new ArrayList<>();
        soldReminds = new ArrayList<>();

        remindAdapter = new MineRemindAdapter(this, mineReminds);
        soldRemindAdapter = new SoldRemindAdapter(this, soldReminds);
        linearLayoutManager = new FullyLinearLayoutManager(this);
        layoutManager = new FullyLinearLayoutManager(this);
        lv_remind.setLayoutManager(linearLayoutManager);
        lv_undercarriage.setLayoutManager(layoutManager);
        lv_remind.setHasFixedSize(true);
        lv_undercarriage.setHasFixedSize(true);
        lv_remind.setItemAnimator(new DefaultItemAnimator());
        lv_undercarriage.setItemAnimator(new DefaultItemAnimator());
        lv_remind.addItemDecoration(new ListViewDecoration());
        lv_undercarriage.addItemDecoration(new ListViewDecoration());
//        lv_remind.setSwipeMenuCreator(swipeMenuCreator);
//        lv_undercarriage.setSwipeMenuCreator(swipeMenuCreator);
        lv_remind.setSwipeMenuItemClickListener(menuItemClickListener);
        lv_undercarriage.setSwipeMenuItemClickListener(menuSoldItemClickListener);
        lv_remind.setAdapter(remindAdapter);
        remindAdapter.setOnItemClickListener(onItemClickListener);
        lv_undercarriage.setAdapter(soldRemindAdapter);
        soldRemindAdapter.setOnItemClickListener(onSoldItemClickListener);
        sv.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                sp_remind.setEnabled(sv.getScrollY() == 0);
            }
        });

        loadReducePriceRecord();
        ll_left.setOnClickListener(this);
        rl_right.setOnClickListener(this);
    }

    private void loadReducePriceRecord(){
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        presenter.reducePriceRecord(params);
    }

    private OnItemClickListener onSoldItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            intent = new Intent(MineDepreciateRemindActivity.this, CarDetailsActivity.class);
            intent.putExtra("carId", soldReminds.get(position).getGoods_id());
            startActivity(intent);
        }

        @Override
        public void onItemLongClick(View view, final int position) {
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            intent = new Intent(MineDepreciateRemindActivity.this, CarDetailsActivity.class);
            intent.putExtra("carId", mineReminds.get(position).getGoods_id());
            startActivity(intent);
        }

        @Override
        public void onItemLongClick(View view, final int position) {
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
         *                        #RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
            }

            // TODO 推荐调用Adapter.notifyItemRemoved(position)，也可以Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                flag = 1;
                currentPosition = adapterPosition;
                HashMap<String, String> params = new HashMap<>();
                params.put("member_id", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getInt("member_id")+"");
                params.put("token", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("token"));
                params.put("id", mineReminds.get(currentPosition).getId()+"");
                presenter.deleteReduce(params);
            }
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuSoldItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
         *                        #RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
            }

            // TODO 推荐调用Adapter.notifyItemRemoved(position)，也可以Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                flag = 2;
                currentPosition = adapterPosition;
                HashMap<String, String> params = new HashMap<>();
                params.put("member_id", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getInt("member_id")+"");
                params.put("token", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("token"));
                params.put("id", soldReminds.get(currentPosition).getId()+"");
                presenter.deleteReduce(params);
            }
        }
    };

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_width);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(MineDepreciateRemindActivity.this)
                        .setBackgroundDrawable(R.color.text_red)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }
        }
    };

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ReducePricePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_rigth: //清空
                if(mineReminds.size() > 0 || soldReminds.size() > 0){
                    new AlertDialog(this)
                            .builder()
                            .setCancelable(false)
                            .setMsg("确定清空所有降价提醒")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("member_id", SharedPreferencesUtil.getInstance(MineDepreciateRemindActivity.this).getInt("member_id")+"");
                                    params.put("token", SharedPreferencesUtil.getInstance(MineDepreciateRemindActivity.this).getString("token"));
                                    presenter.cleanRecord(params);
                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadReducePriceRecord();
        sp_remind.setRefreshing(false);
    }

    @Override
    public void reducePriceRecord(ResultInfo<MineRemind> result) {
        hideLoading();
        if(result.getData() != null && result.getData().getGoodsList() != null && result.getData().getSoldList() != null){
            if(result.getData().getGoodsList().size() > 0 && result.getData().getSoldList().size() > 0){
                sv.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
                tv_.setVisibility(View.VISIBLE);
                mineReminds.clear();
                soldReminds.clear();
                mineReminds.addAll(result.getData().getGoodsList());
                soldReminds.addAll(result.getData().getSoldList());
                remindAdapter.notifyDataSetChanged();
                soldRemindAdapter.notifyDataSetChanged();
            } else if(result.getData().getGoodsList().size() > 0 && result.getData().getSoldList().size() <= 0){
                sv.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
                lv_undercarriage.setVisibility(View.GONE);
                tv_.setVisibility(View.GONE);
                mineReminds.clear();
                mineReminds.addAll(result.getData().getGoodsList());
                remindAdapter.notifyDataSetChanged();
            } else if(result.getData().getGoodsList().size() <= 0 && result.getData().getSoldList().size() > 0){
                sv.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
                lv_remind.setVisibility(View.GONE);
                tv_.setVisibility(View.VISIBLE);
                soldReminds.clear();
                soldReminds.addAll(result.getData().getSoldList());
                soldRemindAdapter.notifyDataSetChanged();
            } else if(result.getData().getGoodsList().size() <= 0 && result.getData().getSoldList().size() <= 0){
                empty.setVisibility(View.VISIBLE);
                sv.setVisibility(View.GONE);
            }
        } else {
            empty.setVisibility(View.VISIBLE);
            sv.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadError(ResultInfo<MineRemind> remindResultInfo) {
        hideLoading();
        if(remindResultInfo.getCode() == -1){
            Toast.makeText(CarApplication.getInstance(), "身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("member_id", 0);
            SharedPreferencesUtil.getInstance(this).setString("isCommit", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("token", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("mobile", " ");
            RxBusMsg msg = new RxBusMsg();
            msg.setCarType(1);
            msg.setLogin(false);
            RxBus.get().post("rxIsLogin", msg);
            ColpencilFrame.getInstance().finishActivity(this);
            startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
        } else {
            empty.setVisibility(View.VISIBLE);
            sv.setVisibility(View.GONE);}
    }

    @Override
    public void deleteReduce(Result_comment result_comment) {
        Toast.makeText(this, result_comment.getMessage(), Toast.LENGTH_SHORT).show();
        if(flag == 1){
            mineReminds.remove(currentPosition);
            remindAdapter.notifyItemRemoved(currentPosition);
        } else if(flag == 2){
            soldReminds.remove(currentPosition);
            soldRemindAdapter.notifyItemRemoved(currentPosition);
        }
        onRefresh();
    }

    @Override
    public void deleteError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cleanRecord(Result_comment result_comment) {
        Toast.makeText(this, result_comment.getMessage(), Toast.LENGTH_SHORT).show();
        empty.setVisibility(View.VISIBLE);
        sv.setVisibility(View.GONE);
        mineReminds.clear();
        soldReminds.clear();
    }

    @Override
    public void cleanError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
