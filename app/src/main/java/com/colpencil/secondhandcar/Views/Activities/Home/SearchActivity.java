package com.colpencil.secondhandcar.Views.Activities.Home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.Bean.Response.HotSearch;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Home.HotSearchPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Ui.FlowTag.FlowTagLayout;
import com.colpencil.secondhandcar.Ui.FlowTag.OnTagClickListener;
import com.colpencil.secondhandcar.Views.Adapter.FlowTag.TagAdapter;
import com.colpencil.secondhandcar.Views.Adapter.SearchHotAdapter;
import com.colpencil.secondhandcar.Views.Imples.Home.HotSearchView;
import com.google.gson.reflect.TypeToken;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/1.
 * 搜索
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_search)
public class SearchActivity extends ColpencilActivity implements View.OnClickListener, HotSearchView{

    @Bind(R.id.fl_history)
    FlowTagLayout fl_history;

    @Bind(R.id.grid_hot)
    GridView gv_hot;

    @Bind(R.id.img_clear)
    ImageView img_clear;

    @Bind(R.id.text_cancel)
    TextView tv_cancel;

    @Bind(R.id.et_search)
    EditText et_search;

    @Bind(R.id.rl_history)
    RelativeLayout rl_history;

    @Bind(R.id.rl_hot)
    RelativeLayout rl_hot;

    @Bind(R.id.view)
    View view_line;

    private List<String> lists = new ArrayList<>();
    private List<HotSearch> hotList;

    private SearchHotAdapter hotAdapter;
    private TagAdapter<String> historyAdapter;
    private HotSearchPresenter presenter;

    @Override
    protected void initViews(View view) {
        lists = SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getDataList("history", new TypeToken<List<String>>(){}.getType());
        hotList = new ArrayList<>();
        if(lists.size() > 0 && lists != null){
            rl_history.setVisibility(View.VISIBLE);
            fl_history.setVisibility(View.VISIBLE);
            view_line.setVisibility(View.VISIBLE);
        } else {
            rl_history.setVisibility(View.GONE);
            fl_history.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);
        }

        historyAdapter = new TagAdapter<>(this);
        fl_history.setAdapter(historyAdapter);
        historyAdapter.onlyAddAll(lists);
        fl_history.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                RxBusMsg msg = new RxBusMsg();
                msg.setKeyword(lists.get(position));
                msg.setCarType(0);
                msg.setType(4);
                RxBus.get().post("rxBusMsg", msg);
                ColpencilFrame.getInstance().finishActivity(SearchActivity.this);
            }
        });

        hotAdapter = new SearchHotAdapter(this, hotList, R.layout.item_search_hot);
        gv_hot.setAdapter(hotAdapter);
        gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RxBusMsg msg = new RxBusMsg();
                msg.setKeyword(hotList.get(position).getKey_name());
                msg.setCarType(0);
                msg.setCarId(hotList.get(position).getCat_id());
                msg.setType(3);
                RxBus.get().post("rxBusMsg", msg);
                ColpencilFrame.getInstance().finishActivity(SearchActivity.this);
            }
        });

        img_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lists.clear();
                SharedPreferencesUtil.getInstance(CarApplication.getInstance()).remove("history");
                rl_history.setVisibility(View.GONE);
                fl_history.setVisibility(View.GONE);
                view_line.setVisibility(View.GONE);
            }
        });

        presenter.hotBrand();
        tv_cancel.setOnClickListener(this);
        initEditView();
    }

    /**
     * 初始化编辑框
     */
    private void initEditView(){
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    lists.add(0, v.getText().toString());
                    if(lists.size() > 9){
                        lists.remove(9);
                    }
                    SharedPreferencesUtil.getInstance(CarApplication.getInstance()).setDataList("history", lists);
                    RxBusMsg msg = new RxBusMsg();
                    msg.setKeyword(v.getText().toString());
                    msg.setCarType(0);
                    msg.setType(4);
                    RxBus.get().post("rxBusMsg", msg);
                    ColpencilFrame.getInstance().finishActivity(SearchActivity.this);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new HotSearchPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_cancel:
                finish();
                break;
        }
    }

    @Override
    public void hotBrand(Result<HotSearch> result) {
        if(result.getData() != null && result.getData().size() > 0){
            gv_hot.setVisibility(View.VISIBLE);
            rl_hot.setVisibility(View.VISIBLE);
            hotList.clear();
            hotList.addAll(result.getData());
            hotAdapter.notifyDataSetChanged();
        } else {
            rl_hot.setVisibility(View.GONE);
            gv_hot.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadError(String message) {
        rl_hot.setVisibility(View.GONE);
        gv_hot.setVisibility(View.GONE);
    }
}
