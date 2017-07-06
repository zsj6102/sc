package com.colpencil.secondhandcar.Views.Activities.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.secondhandcar.Bean.Response.Feedback;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.Present.Mine.FeedbackPresenter;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Views.Imples.Mine.FeedbackView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/12.
 * 意见反馈
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_feedback)
public class FeedbackActivity extends ColpencilActivity implements View.OnClickListener, FeedbackView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.text_commit)
    TextView tv_commit;

    @Bind(R.id.et_feed_task)
    EditText et_task;

    @Bind(R.id.et_phone_task)
    EditText et_phone;

    @Bind(R.id.tv_rigth)
    TextView tv_redord;

    private FeedbackPresenter presenter;

    @Override
    protected void initViews(View view) {
        tv_title.setText("意见反馈");

        ll_left.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        tv_redord.setOnClickListener(this);
        et_phone.setText(SharedPreferencesUtil.getInstance(this).getString("mobile"));
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new FeedbackPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    /**
     * 提交意见
     */
    private void commit(){
        if(TextStringUtils.isEmpty(et_task.getText().toString())){
            Toast.makeText(this, "请输入反馈意见", Toast.LENGTH_SHORT).show();
            return;
        } if(!TextStringUtils.isMobileNO(et_phone.getText().toString())){
           Toast.makeText(this, "输入的手机号码有误", Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.commit(SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getInt("member_id", 0), SharedPreferencesUtil.getInstance(CarApplication.getInstance()).getString("mobile"), et_task.getText().toString(),
                SharedPreferencesUtil.getInstance(this).getString("token"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.text_commit:
                commit();
                break;
            case R.id.tv_rigth: //意见记录
                Intent intent = new Intent(this, FeedbackRecordActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void commit(Result<Feedback> result) {
        Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, FeedbackRecordActivity.class));
        ColpencilFrame.getInstance().finishActivity(this);
    }

    @Override
    public void loadError(String message) {
        Toast.makeText(this, "提交失败", Toast.LENGTH_SHORT).show();
    }
}
