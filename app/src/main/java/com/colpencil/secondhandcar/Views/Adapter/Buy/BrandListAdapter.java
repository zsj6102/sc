package com.colpencil.secondhandcar.Views.Adapter.Buy;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Bean.Response.BrandCar;
import com.colpencil.secondhandcar.Bean.Response.BrandList;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.BrandComparator;
import com.colpencil.secondhandcar.Tools.CharacterParser;
import com.colpencil.secondhandcar.Tools.PinyinUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */
public class BrandListAdapter extends BaseAdapter {

    private static final int VIEW_TYPE_COUNT = 2;
    private Context mContext;
    private BrandList carList;
    private brandListener brandListener;
    private LayoutInflater inflater;
    private BrandComparator brandComparator;
    private CharacterParser characterParser;
    public List<BrandCar> mSortList;

    public BrandListAdapter(Context context, BrandList cars){
        this.mContext = context;
        this.carList = cars;
        this.inflater = LayoutInflater.from(mContext);
        brandComparator = new BrandComparator();
        characterParser = new CharacterParser();
    }

    /**
     * 填充排序数据
     */
    public void fillData(BrandList list){
        if(list != null){
            this.carList = list;
        }
        try{
            mSortList = new ArrayList<>();
            for(int i = 0; i < carList.getCat_list().size(); i++){
                BrandCar sortModel = new BrandCar();
                sortModel.setCat_id(carList.getCat_list().get(i).getCat_id());
                sortModel.setCat_name(carList.getCat_list().get(i).getCat_name());
                sortModel.setPic(carList.getCat_list().get(i).getPic());
                //汉字转换成拼音
                String pinyin = characterParser.getSelling(carList.getCat_list().get(i).getCat_name());
                String sortString = pinyin.substring(0, 1).toUpperCase();

                // 正则表达式，判断首字母是否是英文字母
                if(sortString.matches("[A-Z]")){
                    sortModel.setPinYin(sortString.toUpperCase());
                }else{
                    sortModel.setPinYin("#");
                }

                mSortList.add(sortModel);
            }
            Collections.sort(mSortList, brandComparator);
            for(int i = 0; i < carList.getHot_list().size(); i++){
                mSortList.add(i,new BrandCar());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position < carList.getHot_list().size() ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getCount() {
        return mSortList == null ? 0: mSortList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSortList == null ? null : mSortList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int viewType = getItemViewType(position);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_brand_classify, parent, false);
            holder = new ViewHolder();
            holder.tv_group = (TextView) convertView.findViewById(R.id.text_group);
            holder.tv_name = (TextView) convertView.findViewById(R.id.text_brand_name);
            holder.iv_car = (ImageView) convertView.findViewById(R.id.img_brand);
            holder.ll = (LinearLayout) convertView.findViewById(R.id.layout_hot);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (viewType) {
            case 0:
                String brandName = carList.getHot_list().get(position).getCat_name();
                final int carId = carList.getHot_list().get(position).getCat_id();
                holder.tv_name.setText(brandName);
                Glide.with(mContext)
                        .load(carList.getHot_list().get(position).getPic())
                        .into(holder.iv_car);
                String currentLetter = PinyinUtils.getFirstBrandLetter(carList.getHot_list().get(position).getPinYin());
                String previousLetter = position >= 1 ? PinyinUtils.getFirstBrandLetter(carList.getHot_list().get(position - 1).getPinYin()) : "";
                if(!TextUtils.equals(currentLetter, previousLetter)){
                    holder.tv_group.setVisibility(View.VISIBLE);
                    holder.tv_group.setText(currentLetter);
                } else {
                    holder.tv_group.setVisibility(View.GONE);
                }
                holder.ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(brandListener != null){
                            brandListener.onBrandClick(carId, position);
                        }
                    }
                });
                break;
            case 1:
                if(position >= carList.getHot_list().size()){
                    String name = mSortList.get(position).getCat_name();
                    final int catId = mSortList.get(position).getCat_id();
                    holder.tv_name.setText(name);
                    Glide.with(mContext)
                            .load(mSortList.get(position).getPic())
                            .into(holder.iv_car);
                    String current = PinyinUtils.getFirstLetter(mSortList.get(position).getPinYin());
                    String previous = position >= 1 ? PinyinUtils.getFirstLetter(mSortList.get(position - 1).getPinYin()) : "";
                    if(!TextUtils.equals(current, previous)){
                        holder.tv_group.setVisibility(View.VISIBLE);
                        holder.tv_group.setText(current);
                    } else {
                        holder.tv_group.setVisibility(View.GONE);
                    }
                    holder.ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(brandListener != null){
                                brandListener.onBrandClick(catId, position);
                            }
                        }
                    });
                }
                break;
        }
        return convertView;
    }

    public static class ViewHolder{
        TextView tv_group;
        ImageView iv_car;
        TextView tv_name;
        LinearLayout ll;
    }

    public void setBrandClickListener(brandListener brandClickListener){
        this.brandListener = brandClickListener;
    }

    public interface brandListener{
        void onBrandClick(int catId, int position);
    }
}
