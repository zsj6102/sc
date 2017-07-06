package com.colpencil.secondhandcar.Views.Fragments.Personal.Release;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Base.BaseFragment;
import com.colpencil.secondhandcar.Bean.Response.CarType;
import com.colpencil.secondhandcar.Bean.Response.GoodsInfo;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Present.Sell.ReleaseTypePresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.ImagPagerUtil;
import com.colpencil.secondhandcar.Views.Adapter.Sell.SellGridAdapter;
import com.colpencil.secondhandcar.Views.Imples.Sell.ReleaseTypeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.weitu.mylibrary.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/18.
 * 发布车辆图片信息
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_release_picture)
public class ReleaseCarPictureFragment extends BaseFragment implements View.OnClickListener, ReleaseTypeView{

    @Bind(R.id.layout_ce)
    LinearLayout ll_ce;

    @Bind(R.id.rl_ce)
    RelativeLayout rl_ce;

    @Bind(R.id.iv_ce)
    ImageView iv_ce;

    @Bind(R.id.iv_ce_clear)
    ImageView iv_ce_clear;

    @Bind(R.id.et_ce)
    EditText et_ce;

    @Bind(R.id.layout_type)
    LinearLayout ll_type;

    @Bind(R.id.text_type)
    TextView tv_type;

    @Bind(R.id.layout_let)
    LinearLayout ll_let;

    @Bind(R.id.text_let)
    TextView tv_let;

    @Bind(R.id.layout_box)
    LinearLayout ll_box;

    @Bind(R.id.text_box)
    TextView tv_box;

    @Bind(R.id.layout_nation)
    LinearLayout ll_nation;

    @Bind(R.id.text_nation)
    TextView tv_nation;

    @Bind(R.id.layout_drive)
    LinearLayout ll_drive;

    @Bind(R.id.text_drive)
    TextView tv_drive;

    @Bind(R.id.et_q)
    EditText et_q;

    @Bind(R.id.layout_q)
    LinearLayout ll_q;

    @Bind(R.id.rl_q)
    RelativeLayout rl_q;

    @Bind(R.id.iv_q)
    ImageView iv_q;

    @Bind(R.id.iv_q_clear)
    ImageView iv_q_clear;

    @Bind(R.id.et_m)
    EditText et_m;

    @Bind(R.id.layout_m)
    LinearLayout ll_m;

    @Bind(R.id.rl_m)
    RelativeLayout rl_m;

    @Bind(R.id.iv_m)
    ImageView iv_m;

    @Bind(R.id.iv_m_clear)
    ImageView iv_m_clear;

    @Bind(R.id.et_qp)
    EditText et_qp;

    @Bind(R.id.layout_qp)
    LinearLayout ll_qp;

    @Bind(R.id.rl_qp)
    RelativeLayout rl_qp;

    @Bind(R.id.iv_qp)
    ImageView iv_qp;

    @Bind(R.id.iv_qp_clear)
    ImageView iv_qp_clear;

    @Bind(R.id.et_hp)
    EditText et_hp;

    @Bind(R.id.layout_hp)
    LinearLayout ll_hp;

    @Bind(R.id.rl_hp)
    RelativeLayout rl_hp;

    @Bind(R.id.iv_hp)
    ImageView iv_hp;

    @Bind(R.id.iv_hp_clear)
    ImageView iv_hp_clear;

    @Bind(R.id.et_zk)
    EditText et_zk;

    @Bind(R.id.layout_zk)
    LinearLayout ll_zk;

    @Bind(R.id.rl_zk)
    RelativeLayout rl_zk;

    @Bind(R.id.iv_zk)
    ImageView iv_zk;

    @Bind(R.id.iv_zk_clear)
    ImageView iv_zk_clear;

    @Bind(R.id.et_f)
    EditText et_f;

    @Bind(R.id.layout_f)
    LinearLayout ll_f;

    @Bind(R.id.rl_f)
    RelativeLayout rl_f;

    @Bind(R.id.iv_f)
    ImageView iv_f;

    @Bind(R.id.iv_f_clear)
    ImageView iv_f_clear;

    @Bind(R.id.layout_more)
    LinearLayout ll_more;

    @Bind(R.id.layout_add)
    LinearLayout ll_add;

    @Bind(R.id.gv_more)
    GridView gv_more;

    @Bind(R.id.layout_fuel)
    LinearLayout ll_fuel;

    @Bind(R.id.text_fuel)
    TextView tv_fuel;

    @Bind(R.id.text_output)
    EditText et_output;

    private int type = 0;
    private ReleaseTypePresenter presenter;
    private SellGridAdapter adapter;
//    private List<TImage> pictures = new ArrayList<>();
    private List<String> picList = new ArrayList<>();
    private List<String> carTypes = new ArrayList<>();
    private List<String> lets = new ArrayList<>();
    private List<String> boxs = new ArrayList<>();
    private List<String> nations = new ArrayList<>();
    private List<String> drives = new ArrayList<>();
    private List<String> fuels = new ArrayList<>();
    private List<CarType> carTypeList = new ArrayList<>();
    private OptionsPickerView typeOptionView;
    private OptionsPickerView letOptionView;
    private OptionsPickerView boxOptionView;
    private OptionsPickerView nationOptionView;
    private OptionsPickerView driveOptionView;
    private OptionsPickerView fuleOptionView;
    private Observable<RxMsg> observable;
    private GoodsInfo goodsInfo;

    @Override
    protected void initViews(View view) {
        presenter.getType();
        adapter = new SellGridAdapter(getActivity(), picList, R.layout.item_gridview_sell);
        gv_more.setAdapter(adapter);
        gv_more.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImagPagerUtil imagPagerUtil = new ImagPagerUtil(getActivity(), picList);
                imagPagerUtil.show();
            }
        });
        getEditData();
        initOptionView();
        initListener();
        initBus();
    }

    public void setPictureInfo(GoodsInfo goodsInfo){
        this.goodsInfo = goodsInfo;
        getGoodsInfo();
    }

    /**
     * 初始化event
     */
    private void initBus(){
        observable = RxBus.get().register("release", RxMsg.class);
        Subscriber<RxMsg> subscriber = new Subscriber<RxMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxMsg rxMsg) {
                cleanData();
            }
        };
        observable.subscribe(subscriber);
    }

    /**
     * 清空数据
     */
    private void cleanData(){
        tv_type.setText("请选择");
        tv_let.setText("请选择");
        tv_box.setText("请选择");
        tv_nation.setText("请选择");
        tv_drive.setText("请选择");
        tv_fuel.setText("请选择");
        et_output.setText("");
        et_ce.setText("");
        et_q.setText("");
        et_m.setText("");
        et_qp.setText("");
        et_hp.setText("");
        et_zk.setText("");
        et_f.setText("");
        ll_ce.setVisibility(View.VISIBLE);
        rl_ce.setVisibility(View.GONE);
        ll_q.setVisibility(View.VISIBLE);
        rl_q.setVisibility(View.GONE);
        ll_m.setVisibility(View.VISIBLE);
        rl_m.setVisibility(View.GONE);
        ll_qp.setVisibility(View.VISIBLE);
        rl_qp.setVisibility(View.GONE);
        ll_hp.setVisibility(View.VISIBLE);
        rl_hp.setVisibility(View.GONE);
        ll_zk.setVisibility(View.VISIBLE);
        rl_zk.setVisibility(View.GONE);
        ll_f.setVisibility(View.VISIBLE);
        rl_f.setVisibility(View.GONE);
        ll_more.setVisibility(View.VISIBLE);
        gv_more.setVisibility(View.GONE);
    }

    /**
     * 获取商品信息
     */
    private void getGoodsInfo(){
        tv_type.setText(goodsInfo.getType_name());
        if(goodsInfo.getEmissions() == 1){
            tv_let.setText("国二");
        } else if(goodsInfo.getEmissions() == 2){
            tv_let.setText("国三");
        } else if(goodsInfo.getEmissions() == 3){
            tv_let.setText("国四");
        } else if(goodsInfo.getEmissions() == 4){
            tv_let.setText("国五");
        }
        if(goodsInfo.getTransmission() == 1){
            tv_box.setText("自动");
        } else if(goodsInfo.getTransmission() == 0){
            tv_box.setText("手动");
        }
        if(goodsInfo.getCountry() == 1){
            tv_nation.setText("德系");
        } else if(goodsInfo.getCountry() == 2){
            tv_nation.setText("日系");
        } else if(goodsInfo.getCountry() == 3){
            tv_nation.setText("美系");
        } else if(goodsInfo.getCountry() == 4){
            tv_nation.setText("法系");
        } else if(goodsInfo.getCountry() == 5){
            tv_nation.setText("韩系");
        } else if(goodsInfo.getCountry() == 6){
            tv_nation.setText("国产");
        } else if(goodsInfo.getCountry() == 7){
            tv_nation.setText("其他");
        }
        if(goodsInfo.getDrive() == 1){
            tv_drive.setText("两驱");
        } else if(goodsInfo.getDrive() == 2){
            tv_drive.setText("四驱");
        }
        if(goodsInfo.getFuel() == 1){
            tv_fuel.setText("汽油");
        } else if(goodsInfo.getFuel() == 2){
            tv_fuel.setText("柴油");
        } else if(goodsInfo.getFuel() == 3){
            tv_fuel.setText("电动");
        } else if(goodsInfo.getFuel() == 4){
            tv_fuel.setText("油电混合");
        }
        et_output.setText(goodsInfo.getDisplacement()+"");
        if(goodsInfo.getPicList() != null && goodsInfo.getPicList().size() > 0){
            if(goodsInfo.getPicList().get(0).getRemark() != null){
                et_ce.setText(goodsInfo.getPicList().get(0).getRemark());
            }
            if(goodsInfo.getPicList().get(0).getPic() != null){
                ll_ce.setVisibility(View.GONE);
                rl_ce.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(goodsInfo.getPicList().get(0).getPic())
                        .into(iv_ce);
            } else {
                ll_ce.setVisibility(View.VISIBLE);
                rl_ce.setVisibility(View.GONE);
            }
            if(goodsInfo.getPicList().get(1).getRemark() != null){
                et_q.setText(goodsInfo.getPicList().get(1).getRemark());
            }
            if(goodsInfo.getPicList().get(1).getPic() != null){
                ll_q.setVisibility(View.GONE);
                rl_q.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(goodsInfo.getPicList().get(1).getPic())
                        .into(iv_q);
            } else {
                ll_q.setVisibility(View.VISIBLE);
                rl_q.setVisibility(View.GONE);
            }
            if(goodsInfo.getPicList().get(2).getRemark() != null){
                et_m.setText(goodsInfo.getPicList().get(2).getRemark());
            }
            if(goodsInfo.getPicList().get(2).getPic() != null){
                ll_m.setVisibility(View.GONE);
                rl_m.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(goodsInfo.getPicList().get(3).getPic())
                        .into(iv_m);
            } else {
                ll_m.setVisibility(View.VISIBLE);
                rl_m.setVisibility(View.GONE);
            }
            if(goodsInfo.getPicList().get(3).getRemark() != null){
                et_qp.setText(goodsInfo.getPicList().get(3).getRemark());
            }
            if(goodsInfo.getPicList().get(3).getPic() != null){
                ll_qp.setVisibility(View.GONE);
                rl_qp.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(goodsInfo.getPicList().get(3).getPic())
                        .into(iv_qp);
            } else {
                ll_qp.setVisibility(View.VISIBLE);
                rl_qp.setVisibility(View.GONE);
            }
            if(goodsInfo.getPicList().get(4).getRemark() != null){
                et_hp.setText(goodsInfo.getPicList().get(4).getRemark());
            }
            if(goodsInfo.getPicList().get(4).getPic() != null){
                ll_hp.setVisibility(View.GONE);
                rl_hp.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(goodsInfo.getPicList().get(4).getPic())
                        .into(iv_hp);
            } else {
                ll_hp.setVisibility(View.VISIBLE);
                rl_hp.setVisibility(View.GONE);
            }
            if(goodsInfo.getPicList().get(5).getRemark() != null){
                et_zk.setText(goodsInfo.getPicList().get(5).getRemark());
            }
            if(goodsInfo.getPicList().get(5).getPic() != null){
                ll_zk.setVisibility(View.GONE);
                rl_zk.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(goodsInfo.getPicList().get(5).getPic())
                        .into(iv_zk);
            } else {
                ll_zk.setVisibility(View.VISIBLE);
                rl_zk.setVisibility(View.GONE);
            }
            if(goodsInfo.getPicList().get(6).getRemark() != null){
                et_f.setText(goodsInfo.getPicList().get(6).getRemark());
            }
            if(goodsInfo.getPicList().get(6).getPic() != null){
                ll_f.setVisibility(View.GONE);
                rl_f.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(goodsInfo.getPicList().get(6).getPic())
                        .into(iv_f);
            } else {
                ll_f.setVisibility(View.VISIBLE);
                rl_f.setVisibility(View.GONE);
            }
            if(goodsInfo.getPicList().get(7).getOther_pic() != null && goodsInfo.getPicList().get(7).getOther_pic().size() > 0){
                ll_more.setVisibility(View.GONE);
                gv_more.setVisibility(View.VISIBLE);
                picList.clear();
                picList.addAll(goodsInfo.getPicList().get(7).getOther_pic());
                adapter.notifyDataSetChanged();
            } else {
                ll_more.setVisibility(View.VISIBLE);
                gv_more.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取编辑框数据
     */
    private void getEditData(){
        et_output.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("output", s.toString());
            }
        });
        et_ce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("zC", s.toString());
            }
        });
        et_q.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("zQ", s.toString());
            }
        });
        et_m.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("cM", s.toString());
            }
        });
        et_qp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("qP", s.toString());
            }
        });
        et_hp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("hP", s.toString());
            }
        });
        et_zk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("zK", s.toString());
            }
        });
        et_f.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("fD", s.toString());
            }
        });
    }

    /**
     * 初始化OptionView
     */
    private void initOptionView(){
        lets.add("国五");
        lets.add("国四");
        lets.add("国三");
        lets.add("国二");
        boxs.add("自动");
        boxs.add("手动");
        nations.add("国产");
        nations.add("德系");
        nations.add("日系");
        nations.add("美系");
        nations.add("韩系");
        nations.add("法系");
        drives.add("四驱");
        drives.add("两驱");
        fuels.add("汽油");
        fuels.add("柴油");
        fuels.add("电动");
        fuels.add("油电混合");
        typeOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setInt("carType", carTypeList.get(options1).getType_id());
                tv_type.setText(carTypes.get(options1));
            }
        }).build();
        typeOptionView.setPicker(carTypes);
        letOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("let", lets.get(options1));
                tv_let.setText(lets.get(options1));
            }
        }).build();
        letOptionView.setPicker(lets);
        boxOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("box", boxs.get(options1));
                tv_box.setText(boxs.get(options1));
            }
        }).build();
        boxOptionView.setPicker(boxs);
        nationOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("nation", nations.get(options1));
                tv_nation.setText(nations.get(options1));
            }
        }).build();
        nationOptionView.setPicker(nations);
        driveOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("drive", drives.get(options1));
                tv_drive.setText(drives.get(options1));
            }
        }).build();
        driveOptionView.setPicker(drives);
        fuleOptionView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SharedPreferencesUtil.getInstance(getActivity()).setString("fuel", fuels.get(options1));
                tv_fuel.setText(fuels.get(options1));
            }
        }).build();
        fuleOptionView.setPicker(fuels);
    }

    /**
     * 初始化listener
     */
    private void initListener(){
        ll_ce.setOnClickListener(this);
        ll_q.setOnClickListener(this);
        ll_m.setOnClickListener(this);
        ll_qp.setOnClickListener(this);
        ll_hp.setOnClickListener(this);
        ll_zk.setOnClickListener(this);
        ll_f.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        ll_type.setOnClickListener(this);
        ll_let.setOnClickListener(this);
        ll_box.setOnClickListener(this);
        ll_nation.setOnClickListener(this);
        ll_drive.setOnClickListener(this);
        ll_fuel.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ReleaseTypePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_ce: //正侧
                openSelect(true, 1);
                type = 0;
                break;
            case R.id.layout_q: //正前
                openSelect(true, 1);
                type = 1;
                break;
            case R.id.layout_m: //车门
                openSelect(true, 1);
                type = 2;
                break;
            case R.id.layout_qp: //前排
                openSelect(true, 1);
                type = 3;
                break;
            case R.id.layout_hp: //后排
                openSelect(true, 1);
                type = 4;
                break;
            case  R.id.layout_zk: //中控
                openSelect(true, 1);
                type = 5;
                break;
            case R.id.layout_f: //发动机舱
                openSelect(true, 1);
                type = 6;
                break;
            case R.id.layout_add: //添加更多
                openSelect(true, 10);
                type = 7;
                break;
            case R.id.layout_type: //车辆类型
                typeOptionView.show();
                break;
            case R.id.layout_let: //排放标准
                letOptionView.show();
                break;
            case R.id.layout_box: //变速箱
                boxOptionView.show();
                break;
            case R.id.layout_nation: //国别
                nationOptionView.show();
                break;
            case R.id.layout_drive: //驱动
                driveOptionView.show();
                break;
            case R.id.layout_fuel: //燃料类型
                fuleOptionView.show();
                break;
        }
    }

    @Override
    public void takeSuccess(final TResult result) {
        super.takeSuccess(result);
        switch (type) {
            case 0:
                SharedPreferencesUtil.getInstance(getActivity()).setString("zCPic", result.getImage().getCompressPath());
                final List<String> ce = new ArrayList<>();
                ll_ce.setVisibility(View.GONE);
                rl_ce.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(result.getImages().get(0).getCompressPath())
                        .into(iv_ce);
                ce.add(result.getImages().get(0).getCompressPath());
                rl_ce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagPagerUtil imagPagerUtil = new ImagPagerUtil(getActivity(), ce);
                        imagPagerUtil.show();
                    }
                });
                iv_ce_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rl_ce.setVisibility(View.GONE);
                        ll_ce.setVisibility(View.VISIBLE);
                    }
                });
                break;
            case 1:
                SharedPreferencesUtil.getInstance(getActivity()).setString("zQPic", result.getImage().getCompressPath());
                final List<String> q = new ArrayList<>();
                ll_q.setVisibility(View.GONE);
                rl_q.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(result.getImages().get(0).getCompressPath())
                        .into(iv_q);
                q.add(result.getImages().get(0).getCompressPath());
                rl_q.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagPagerUtil imagPagerUtil = new ImagPagerUtil(getActivity(), q);
                        imagPagerUtil.show();
                    }
                });
                iv_q_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rl_q.setVisibility(View.GONE);
                        ll_q.setVisibility(View.VISIBLE);
                    }
                });
                break;
            case 2:
                SharedPreferencesUtil.getInstance(getActivity()).setString("cMPic", result.getImage().getCompressPath());
                final List<String> m = new ArrayList<>();
                ll_m.setVisibility(View.GONE);
                rl_m.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(result.getImages().get(0).getCompressPath())
                        .into(iv_m);
                m.add(result.getImages().get(0).getCompressPath());
                rl_m.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagPagerUtil imagPagerUtil = new ImagPagerUtil(getActivity(), m);
                        imagPagerUtil.show();
                    }
                });
                iv_m_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rl_m.setVisibility(View.GONE);
                        ll_m.setVisibility(View.VISIBLE);
                    }
                });
                break;
            case 3:
                SharedPreferencesUtil.getInstance(getActivity()).setString("qPPic", result.getImage().getCompressPath());
                final List<String> qp = new ArrayList<>();
                ll_qp.setVisibility(View.GONE);
                rl_qp.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(result.getImages().get(0).getCompressPath())
                        .into(iv_qp);
                qp.add(result.getImages().get(0).getCompressPath());
                rl_qp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagPagerUtil imagPagerUtil = new ImagPagerUtil(getActivity(), qp);
                        imagPagerUtil.show();
                    }
                });
                iv_qp_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll_qp.setVisibility(View.VISIBLE);
                        rl_qp.setVisibility(View.GONE);
                    }
                });
                break;
            case 4:
                SharedPreferencesUtil.getInstance(getActivity()).setString("hPPic", result.getImage().getCompressPath());
                final List<String> hp = new ArrayList<>();
                ll_hp.setVisibility(View.GONE);
                rl_hp.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(result.getImages().get(0).getCompressPath())
                        .into(iv_hp);
                hp.add(result.getImages().get(0).getCompressPath());
                rl_hp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagPagerUtil imagPagerUtil = new ImagPagerUtil(getActivity(), hp);
                        imagPagerUtil.show();
                    }
                });
                iv_hp_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rl_hp.setVisibility(View.GONE);
                        ll_hp.setVisibility(View.VISIBLE);
                    }
                });
                break;
            case 5:
                SharedPreferencesUtil.getInstance(getActivity()).setString("zKPic", result.getImage().getCompressPath());
                final List<String> zk = new ArrayList<>();
                ll_zk.setVisibility(View.GONE);
                rl_zk.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(result.getImages().get(0).getCompressPath())
                        .into(iv_zk);
                zk.add(result.getImage().getCompressPath());
                rl_zk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagPagerUtil imagPagerUtil = new ImagPagerUtil(getActivity(), zk);
                        imagPagerUtil.show();
                    }
                });
                iv_zk_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll_zk.setVisibility(View.VISIBLE);
                        rl_zk.setVisibility(View.GONE);
                    }
                });
                break;
            case 6:
                SharedPreferencesUtil.getInstance(getActivity()).setString("fDPic", result.getImage().getCompressPath());
                final List<String> f = new ArrayList<>();
                ll_f.setVisibility(View.GONE);
                rl_f.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(result.getImages().get(0).getCompressPath())
                        .into(iv_f);
                f.add(result.getImages().get(0).getCompressPath());
                rl_f.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagPagerUtil imagPagerUtil = new ImagPagerUtil(getActivity(), f);
                        imagPagerUtil.show();
                    }
                });
                iv_f_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rl_f.setVisibility(View.GONE);
                        ll_f.setVisibility(View.VISIBLE);
                    }
                });
                break;
            case 7:
                ll_more.setVisibility(View.GONE);
                gv_more.setVisibility(View.VISIBLE);
                picList.clear();
                for(TImage image: result.getImages()){
                    picList.add(image.getCompressPath());
                }
                adapter.notifyDataSetChanged();
                SharedPreferencesUtil.getInstance(getActivity()).setDataList("more", picList);
                adapter.setListener(new SellGridAdapter.OnClickListener() {
                    @Override
                    public void click(int position) {
                        picList.remove(position);
                        adapter.notifyDataSetChanged();
                        if(picList.size() == 0 || picList == null){
                            ll_more.setVisibility(View.VISIBLE);
                            gv_more.setVisibility(View.GONE);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void getType(Result<CarType> resultInfo) {
        carTypes.clear();
        carTypeList.clear();
        carTypeList.addAll(resultInfo.getData());
        for(CarType carType: resultInfo.getData()){
            carTypes.add(carType.getType_name());
        }
    }

    @Override
    public void getError(String message) {

    }
}
