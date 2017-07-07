package com.colpencil.secondhandcar.Views.Adapter.Mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.colpencil.secondhandcar.Api.CarApi;
import com.colpencil.secondhandcar.Bean.Response.Order;
import com.colpencil.secondhandcar.Bean.Response.PayResult;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.RxMsg;
import com.colpencil.secondhandcar.Config.UrlConfig;
import com.colpencil.secondhandcar.Overall.CarApplication;
import com.colpencil.secondhandcar.R;
import com.colpencil.secondhandcar.Tools.StringUtils;
import com.colpencil.secondhandcar.Ui.AlertDialog;
import com.colpencil.secondhandcar.Views.Activities.Buy.Period.PeriodPayCarActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/10.
 * 我的分期适配器
 */
public class MinePeriodAdapter extends SuperAdapter<Order> {

    private Intent intent;

    public MinePeriodAdapter(Context context, List<Order> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final Order item) {
        holder.setText(R.id.text_number, item.getSn()); //订单编号
        holder.setText(R.id.text_date, StringUtils.formatTime(item.getCreate_time())); //下单时间
        Glide.with(mContext).load(item.getPic()).into((ImageView) holder.getView(R.id.img_car));
        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.tv_ma, item.getPrice() + "万");
        holder.setText(R.id.text_year, StringUtils.formatYear(item.getGoods_time()));
        holder.setText(R.id.tv_statue, item.getMileage() + "万公里");
        holder.setText(R.id.text_address, item.getCity_name());
        holder.setText(R.id.text_money, item.getOrder_amount() + "元");
        if (item.getInstallment() == 1) {
            holder.getView(R.id.img_sale).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.img_sale).setVisibility(View.GONE);
        }
        //支付状态
        if (item.getPay_status() == 0) {
            holder.setText(R.id.text_pay_state, "未付款");
        } else if (item.getPay_status() == 1) {
            holder.setText(R.id.text_pay_state, "已付款");
        } else if (item.getPay_status() == 2) {
            holder.setText(R.id.text_pay_state, "待退款");
        } else if (item.getPay_status() == 3) {
            holder.setText(R.id.text_pay_state, "已退款");
        }
        //订单状态
        switch (item.getStatus()) {
            case 0: //待付款
                holder.setText(R.id.text_state, "待付款");
                holder.getView(R.id.rl_state).setVisibility(View.VISIBLE);
                holder.getView(R.id.rl_statue).setVisibility(View.GONE);
                //点击取消
                holder.getView(R.id.text_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog(mContext).builder().setCancelable(false).setMsg("确认取消订单").setPositiveButton("确定", new View.OnClickListener() {
                            @Override

                            public void onClick(View v) {
                                Toast.makeText(mContext, "2", Toast.LENGTH_SHORT).show();
                                HashMap<String, String> params = new HashMap<>();
                                params.put("member_id", SharedPreferencesUtil.getInstance(mContext).getInt("member_id") + "");
                                params.put("token", SharedPreferencesUtil.getInstance(mContext).getString("token"));
                                params.put("order_id", item.getOrder_id() + "");
                                RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_).createApi(CarApi.class).cancel(params).subscribeOn(Schedulers.io()).map(new Func1<Result_comment, Result_comment>() {
                                    @Override
                                    public Result_comment call(Result_comment result_comment) {
                                        return result_comment;
                                    }
                                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Result_comment>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Result_comment result_comment) {
                                        if (result_comment.getCode() == 1) {
                                            RxMsg msg = new RxMsg();
                                            RxBus.get().post("orderState", msg);
                                        } else {
                                            Toast.makeText(mContext, result_comment.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                    }
                });
                //点击支付
                holder.getView(R.id.text_pay).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "1", Toast.LENGTH_SHORT).show();
                        HashMap<String, String> params = new HashMap<>();
                        params.put("member_id", SharedPreferencesUtil.getInstance(mContext).getInt("member_id") + "");
                        params.put("token", SharedPreferencesUtil.getInstance(mContext).getString("token"));
                        params.put("order_id", item.getOrder_id() + "");
                        //BASE 支付调试地址
                        RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE).createApi(CarApi.class).pay(params).subscribeOn(Schedulers.io()).map(new Func1<PayResult, PayResult>() {
                            @Override
                            public PayResult call(PayResult payResult) {
                                return payResult;
                            }
                        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<PayResult>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(PayResult payResult) {
                                    Toast.makeText(mContext, payResult.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            case 1://待看车
                holder.setText(R.id.text_state, "待看车");
                holder.getView(R.id.rl_state).setVisibility(View.GONE);
                holder.getView(R.id.rl_statue).setVisibility(View.GONE);
                break;
            case 2://商谈中
                holder.setText(R.id.text_state, "商谈中");
                holder.getView(R.id.rl_statue).setVisibility(View.VISIBLE);
                holder.getView(R.id.rl_state).setVisibility(View.GONE);
                if (item.getInstallment() == 1) {
                    holder.getView(R.id.text_period).setVisibility(View.VISIBLE);
                    //转分期
                    holder.getView(R.id.text_period).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(mContext, PeriodPayCarActivity.class);
                            intent.putExtra("order_id", item.getOrder_id());
                            mContext.startActivity(intent);
                        }
                    });
                } else {
                    holder.getView(R.id.text_period).setVisibility(View.GONE);
                }
                //交易失败
                holder.getView(R.id.text_faile).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog(mContext).builder().setCancelable(false).setMsg("确认交易失败").setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("member_id", SharedPreferencesUtil.getInstance(mContext).getInt("member_id") + "");
                                params.put("token", SharedPreferencesUtil.getInstance(mContext).getString("token"));
                                params.put("order_id", item.getOrder_id() + "");
                                RetrofitManager.getInstance(1, CarApplication.getInstance(), UrlConfig.BASE_HOST_).createApi(CarApi.class).failure(params).subscribeOn(Schedulers.io()).map(new Func1<Result_comment, Result_comment>() {
                                    @Override
                                    public Result_comment call(Result_comment result_comment) {
                                        return result_comment;
                                    }
                                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Result_comment>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Result_comment result_comment) {
                                        if (result_comment.getCode() == 1) {
                                            RxMsg msg = new RxMsg();
                                            RxBus.get().post("orderState", msg);
                                        } else {
                                            Toast.makeText(mContext, result_comment.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                    }
                });
                break;
            case 3://已完成
                holder.setText(R.id.text_state, "已完成");
                holder.getView(R.id.rl_state).setVisibility(View.GONE);
                holder.getView(R.id.rl_statue).setVisibility(View.GONE);
                break;
            case 4://交易失败
                holder.setText(R.id.text_state, "交易失败");
                holder.getView(R.id.rl_state).setVisibility(View.GONE);
                holder.getView(R.id.rl_statue).setVisibility(View.GONE);
                break;
            case 5://取消订单
                holder.setText(R.id.text_state, "取消订单");
                holder.getView(R.id.rl_state).setVisibility(View.GONE);
                holder.getView(R.id.rl_statue).setVisibility(View.GONE);
                break;
            case 6://转分期
                holder.setText(R.id.text_state, "转分期");
                holder.getView(R.id.rl_state).setVisibility(View.GONE);
                holder.getView(R.id.rl_statue).setVisibility(View.GONE);
                break;
        }
    }
}
