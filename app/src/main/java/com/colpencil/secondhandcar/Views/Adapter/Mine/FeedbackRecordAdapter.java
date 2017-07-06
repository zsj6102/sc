package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.Feedback;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.colpencil.secondhandcar.Views.Activities.Personal.FeedbackRecordActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;

import java.util.HashMap;
import java.util.List;
import static com.colpencil.secondhandcar.Tools.StringUtils.format;

/**
 * Created by Administrator on 2017/5/8.
 *
 */
public class FeedbackRecordAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<Feedback> mDatas;
    HashMap<Integer, View> localAppMap = new HashMap<Integer, View>();
    HashMap<Integer,ViewHolder> holderMap = new HashMap<>();
    public FeedbackRecordAdapter(Context context, List<Feedback> list ){

        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = list;

    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public  Feedback getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        final ViewHolder holder;
        //如果只是单纯的复用会出现bug,
        if(localAppMap.get(position) == null){

            convertView = mInflater.inflate(R.layout.item_feedback_record,null);
            holder = new ViewHolder(convertView);
            localAppMap.put(position,convertView);
            convertView.setTag(holder);

        }else{
            convertView = localAppMap.get(position);
            holder =  (ViewHolder) convertView.getTag();

        }

        if(mDatas.get(position).getState() == 0){

            holder.text_state.setText("未处理");
            Glide.with(mContext)
                    .load(R.mipmap.remind)
                    .into(holder.img_state);
            holder.text_detail.setVisibility(View.INVISIBLE);
        }else{
            if( holder.text_state.getTag()!=null){
                if((Integer)holder.text_state.getTag() == 0){
                    holder.text_state.setText("我");
                }else{
                    holder.text_state.setText("已处理");
                }
            }else {
                holder.text_state.setText("已处理");
            }
            if(holder.text_detail.getTag()!=null){
                if((Integer)holder.text_detail.getTag() == 0){
                    holder.text_detail.setVisibility(View.GONE);
                }else{
                    holder.text_detail.setVisibility(View.VISIBLE);
                }
            }else{
                holder.text_detail.setVisibility(View.VISIBLE);
            }
            Glide.with(mContext)
                    .load(R.mipmap.apply_success)
                    .into(holder.img_state);

            holder.text_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.img_state.setVisibility(View.GONE);
                    holder.text_detail.setVisibility(View.GONE);
                    holder.text_detail.setTag(0);
                    holder.text_state.setText("我");
                    holder.text_state.setTag(0);
                    holder.layout_detail.setVisibility(View.VISIBLE);
                    SpannableString spannableString = new SpannableString("回复内容："+mDatas.get(position).getReply_content());
                    holder.text_reply_time.setText(StringUtils.format(mDatas.get(position).getReply_time()));
                    spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_dark33)), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.text_reply_content.setText(spannableString);
                    holder.text_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.img_state.setVisibility(View.VISIBLE);
                            holder.text_detail.setTag(1);
                            holder.text_state.setTag(1);
                            holder.text_state.setText("已处理");
                            holder.text_detail.setVisibility(View.VISIBLE);
                            holder.layout_detail.setVisibility(View.GONE);
                        }
                    });
                    holder.text_ask.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ColpencilFrame.getInstance().finishActivity(FeedbackRecordActivity.class);
                        }
                    });
                }
            });
        }
        SpannableString ss = new SpannableString("意见反馈内容：" + mDatas.get(position).getContent());
        ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_dark33)), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
       holder.text_content.setText(ss);
        holder.text_commit_time.setText(format(mDatas.get(position).getSubmit_time()));
        return convertView;
    }
      final static class ViewHolder{
        public TextView text_state;
        public ImageView img_state;
        public TextView text_detail;
        public LinearLayout layout_detail;
        public TextView text_reply_time;
        public TextView text_close;
        public TextView text_ask;
        public TextView text_reply_content;
        public TextView text_content;
        public TextView text_commit_time;
          public ViewHolder(View convertView){
              text_state = (TextView)convertView.findViewById(R.id.text_state);
              img_state = (ImageView)convertView.findViewById(R.id.img_state);
              layout_detail = (LinearLayout)convertView.findViewById(R.id.layout_detail);
              text_ask = (TextView)convertView.findViewById(R.id.text_ask);
              text_close = (TextView)convertView.findViewById(R.id.text_close);
              text_detail = (TextView)convertView.findViewById(R.id.text_detail);
              text_reply_time = (TextView)convertView.findViewById(R.id.text_reply_time);
              text_reply_content = (TextView)convertView.findViewById(R.id.text__reply_content);
              text_content = (TextView)convertView.findViewById(R.id.text_content);
              text_commit_time = (TextView)convertView.findViewById(R.id.text_commit_time);
          }
    }

}
