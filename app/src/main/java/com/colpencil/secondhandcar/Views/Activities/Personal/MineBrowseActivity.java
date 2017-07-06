package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.Browse;
import com.colpencil.secondhandcar.Bean.Response.PersonalGroom;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Mine.BrowseRecordPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Ui.AlertDialog;
import com.colpencil.secondhandcar.Views.Activities.Buy.CarDetailsActivity;
import com.colpencil.secondhandcar.Views.Activities.Welcome.LoginActivity;
import com.colpencil.secondhandcar.Views.Adapter.Mine.MenuAdapter;
import com.colpencil.secondhandcar.Views.Imples.Mine.BrowseView;
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
 * Created by Administrator on 2017/4/11.
 * 浏览记录
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_browse_record)
public class MineBrowseActivity extends ColpencilActivity implements View.OnClickListener, BrowseView, SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_rigth)
    ImageView iv_right;

    @Bind(R.id.smr_browse)
    SwipeMenuRecyclerView smr_browse;

    @Bind(R.id.empty)
    LinearLayout empty;

    @Bind(R.id.ll_rigth)
    RelativeLayout rl_right;

    @Bind(R.id.swipe)
    SwipeRefreshLayout sp;

    private List<PersonalGroom> mDatas = new ArrayList<>();
    private MenuAdapter adapter;
    private BrowseRecordPresenter presenter;
    private int pageNo = 1;
    private int pageSize = 10;
    private int currentPosition;

    @Override
    protected void initViews(View view) {
        tv_title.setText("浏览记录");
        showLoading("加载中");
        sp.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        sp.setOnRefreshListener(this);
        iv_right.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);

        smr_browse.setLayoutManager(new LinearLayoutManager(this));
        smr_browse.setHasFixedSize(true);
        smr_browse.setItemAnimator(new DefaultItemAnimator());
        smr_browse.addItemDecoration(new ListViewDecoration());

        smr_browse.addOnScrollListener(mOnScrollListener);
        smr_browse.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        smr_browse.setSwipeMenuItemClickListener(menuItemClickListener);

        adapter = new MenuAdapter(this,mDatas);
        adapter.setOnItemClickListener(onItemClickListener);
        smr_browse.setAdapter(adapter);
        loadData();
        ll_left.setOnClickListener(this);
        rl_right.setOnClickListener(this);

    }


    private void loadData(){
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id")+"");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        params.put("pageNo", pageNo+"");
        params.put("pageSize", pageSize+"");
        presenter.browseRecord(params);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new BrowseRecordPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_rigth: //清空
                if(mDatas.size() > 0 && mDatas != null){
                    new AlertDialog(this)
                            .builder()
                            .setCancelable(false)
                            .setMsg("确定清空所有浏览记录")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("member_id", SharedPreferencesUtil.getInstance(MineBrowseActivity.this).getInt("member_id")+"");
                                    params.put("token", SharedPreferencesUtil.getInstance(MineBrowseActivity.this).getString("token"));
                                    presenter.cleanBrowse(params);
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

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {// 手指不能向上滑动了
                // TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。
                pageNo++;
                loadData();
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
                SwipeMenuItem deleteItem = new SwipeMenuItem(MineBrowseActivity.this)
                        .setBackgroundDrawable(R.color.text_red)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(MineBrowseActivity.this, CarDetailsActivity.class);
            intent.putExtra("carId", mDatas.get(position).getGoods_id());
            startActivity(intent);
        }

        @Override
        public void onItemLongClick(View view, int position) {

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
                currentPosition = adapterPosition;
                HashMap<String, String> params = new HashMap<>();
                params.put("member_id", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getInt("member_id")+"");
                params.put("token", SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("token"));
                params.put("id", mDatas.get(adapterPosition).getId()+"");
                presenter.deleteBrowse(params);
            }
        }
    };

    @Override
    public void browseRecord(Result<PersonalGroom> result) {
        hideLoading();
        if(result.getData() != null && result.getData().size() > 0){
            empty.setVisibility(View.GONE);
            smr_browse.setVisibility(View.VISIBLE);
            mDatas.clear();
            mDatas.addAll(result.getData());
            adapter.notifyDataSetChanged();
        } else {
            smr_browse.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadMore(Result<PersonalGroom> result) {
        hideLoading();
        if(result.getData().size() == 0){

        } else {
            mDatas.addAll(result.getData());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadError(Result<PersonalGroom> result) {
        hideLoading();
        if(result.getCode() == -1){
            Toast.makeText(CarApplication.getInstance(), "身份已过期，请重新登录", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setBoolean("isLogin", false);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("member_id", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("token", " ");
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setInt("is_store", 0);
            SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setString("mobile", " ");
            SharedPreferencesUtil.getInstance(this).setString("isCommit", " ");
            RxBusMsg msg = new RxBusMsg();
            msg.setCarType(1);
            msg.setLogin(false);
            RxBus.get().post("rxIsLogin", msg);
            ColpencilFrame.getInstance().finishActivity(this);
            startActivity(new Intent(CarApplication.getInstance(), LoginActivity.class));
        } else {
            smr_browse.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void deleteBrowse(Browse browse) {
        Toast.makeText(this, browse.getMessage(), Toast.LENGTH_SHORT).show();
        mDatas.remove(currentPosition);
        adapter.notifyItemRemoved(currentPosition);
        onRefresh();
    }

    @Override
    public void deleteError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cleanBrowse(Browse browse) {
        Toast.makeText(this, browse.getMessage(), Toast.LENGTH_SHORT).show();
        smr_browse.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
        mDatas.clear();
    }

    @Override
    public void cleanError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        loadData();
        sp.setRefreshing(false);
    }
}
