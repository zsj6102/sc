package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.colpencil.secondhandcar.Bean.Response.MineSubscribe;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Ui.FlowTag.FlowTagLayout;
import com.colpencil.secondhandcar.Views.Activities.Buy.CarDetailsActivity;
import com.colpencil.secondhandcar.Views.Activities.Personal.SubscribeDetailsActivity;
import com.colpencil.secondhandcar.Views.Adapter.FlowTag.TagAdapter;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 * 我的订阅适配器
 */
public class MineSubscribeAdapter extends SuperAdapter<MineSubscribe> {

    private MineSubscribeCarAdapter subscribeCarAdapter;
//    private MineSubscribeTagAdapter tagAdapter;
    private TagAdapter tagAdapetr;
    private LinearLayoutManager linearLayoutManager;
    private clickListener clickListener;

    public MineSubscribeAdapter(Context context, List<MineSubscribe> list, int layoutId){
        super(context, list, layoutId);
    }

    public void setListener(clickListener listener){
        this.clickListener = listener;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, final MineSubscribe item) {
        if(item.getNew_num() == 0){
            holder.setText(R.id.text_num, "暂无更新");
        } else {
            holder.setText(R.id.text_num, "新上"+item.getNew_num()+"辆");
        }
        LinearLayout layout = holder.getView(R.id.layout_enter);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SubscribeDetailsActivity.class);
                intent.putExtra("id", item.getId());
                intent.putStringArrayListExtra("tagList", item.getTypeCatList());
                mContext.startActivity(intent);
            }
        });
        ListView carListView = holder.getView(R.id.cl_car);
        FlowTagLayout flowTagLayout = holder.getView(R.id.rv_tag);
        subscribeCarAdapter = new MineSubscribeCarAdapter(mContext, item.getGoodsList(), R.layout.item_child_subscribe_car);
        carListView.setAdapter(subscribeCarAdapter);
        carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, CarDetailsActivity.class);
                intent.putExtra("carId", item.getGoodsList().get(position).getGoods_id());
                mContext.startActivity(intent);
            }
        });
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        tagRecycleView.setLayoutManager(linearLayoutManager);
//        tagAdapter = new MineSubscribeTagAdapter(mContext, item.getTypeCatList());
//        tagRecycleView.setAdapter(tagAdapter);
        tagAdapetr = new TagAdapter(mContext);
        flowTagLayout.setAdapter(tagAdapetr);
        tagAdapetr.onlyAddAll(item.getTypeCatList());
        holder.getView(R.id.text_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null){
                    clickListener.onClick(layoutPosition);
                }
            }
        });
    }

    public interface clickListener{
        void onClick(int position);
    }
}
