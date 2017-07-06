package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.secondhandcar.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.KeyBoardUtils;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/12.
 * 我的优惠券
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_coupon)
public class MineCouponActivity extends ColpencilActivity implements View.OnClickListener{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.et_coupon)
    EditText et_coupon;

    @Bind(R.id.text_commit)
    TextView tv_commit;

    @Override
    protected void initViews(View view) {
        tv_title.setText("我的优惠券");

        ll_left.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                KeyBoardUtils.closeKeybord(et_coupon, this);
                finish();
                break;
            case R.id.text_commit:
                break;
        }
    }
}
