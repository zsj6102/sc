package com.colpencil.secondhandcar.Views.Fragments.BuyCar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.BrandCar;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.RxBrandMsg;
import com.colpencil.secondhandcar.Bean.RxBusMsg;
import com.colpencil.secondhandcar.Present.Buy.CarPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Activities.Buy.BrandClassifyActivity;
import com.colpencil.secondhandcar.Views.Activities.Sell.BrandNameActivity;
import com.colpencil.secondhandcar.Views.Adapter.Buy.BrandTwoCarAdapter;
import com.colpencil.secondhandcar.Views.Imples.Buy.CarView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/28.
 * 品牌二级菜单
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_brand_car)
public class BrandCarFragment extends ColpencilFragment implements CarView{

    @Bind(R.id.list_brand)
    ListView lv_two;

    private BrandTwoCarAdapter adapter;
    private List<BrandCar> list = new ArrayList<>();
    private static int cat_id = 0;
    private CarPresenter presenter;
    private int type;

    public static BrandCarFragment newsInstance(int catId, int type){
        Bundle bundle = new Bundle();
        BrandCarFragment fragment = new BrandCarFragment();
        bundle.putInt("catId", catId);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        cat_id = getArguments().getInt("catId");
        type = getArguments().getInt("type");
        adapter = new BrandTwoCarAdapter(getActivity(), list, R.layout.item_brand_two_car);
        lv_two.setAdapter(adapter);
        lv_two.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int catId = list.get(position).getCat_id();
                String catName = list.get(position).getCat_name();
                if(type == 0){
                    RxBusMsg msg = new RxBusMsg();
                    msg.setCarId(catId);
                    msg.setKeyword(catName);
                    msg.setType(3);
                    RxBus.get().post("rxBusMsg",msg);
                    ColpencilFrame.getInstance().finishActivity(BrandClassifyActivity.class);
                } else if(type == 1){
                    RxBrandMsg msg = new RxBrandMsg();
                    msg.setCatId(catId);
                    msg.setCatName(catName);
                    RxBus.get().post("addSub",msg);
                    ColpencilFrame.getInstance().finishActivity(BrandClassifyActivity.class);
                } else if(type == 2){
                    Intent intent = new Intent(getActivity(), BrandNameActivity.class);
                    intent.putExtra("catId", catId);
                    startActivity(intent);
//                    ColpencilFrame.getInstance().finishActivity(getActivity());
                }
            }
        });
        HashMap<String, String> params = new HashMap<>();
        params.put("cat_id", cat_id+"");
        presenter.carList(params);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CarPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void carList(Result<BrandCar> result) {
        list.clear();
        list.addAll(result.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
