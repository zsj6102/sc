package com.colpencil.secondhandcar.Views.Adapter.ChooseCar;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.R;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 *
 */
public class DisplacementAdapter extends BaseAdapter {

    private int selectorPosition = -1;

    private Activity mActivity;
    private List<String> carLists;
    private LayoutInflater inflater;

    public DisplacementAdapter(Activity activity, List<String> list){
        mActivity = activity;
        carLists = list;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return carLists.size();
    }

    @Override
    public Object getItem(int position) {
        return carLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_choose_car, null);
            holder = new ViewHolder();
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.ct_car);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CheckBox checkBox = holder.checkBox;
        Drawable drawable = mActivity.getResources().getDrawable(R.drawable.drawable_choose_car);
        drawable.setBounds(0, 0, 40, 40);
        holder.checkBox.setCompoundDrawables(null, null, drawable, null);
        holder.checkBox.setText(carLists.get(position));
        holder.checkBox.setId(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    if(selectorPosition != -1){
                        CheckBox tempCheckBox = (CheckBox) parent.findViewById(selectorPosition);
                        if(tempCheckBox != null){
                            tempCheckBox.setChecked(false);
                        }
                    }
                    selectorPosition = position;
                }else{
                    selectorPosition = -1;
                }
                RxMsg msg = new RxMsg();
                RxBus.get().post("search", msg);
            }
        });

        if(selectorPosition == position){
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        return convertView;
    }

    public int getSelection(){
        return selectorPosition;
    }

    public class ViewHolder{
        public CheckBox checkBox;
    }
}
