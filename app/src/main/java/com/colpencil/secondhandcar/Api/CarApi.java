package com.colpencil.secondhandcar.Api;

import com.colpencil.secondhandcar.Bean.City;
import com.colpencil.secondhandcar.Bean.Response.ApplyRate;
import com.colpencil.secondhandcar.Bean.Response.BillDetails;
import com.colpencil.secondhandcar.Bean.Response.BillRecord;
import com.colpencil.secondhandcar.Bean.Response.BrandCar;
import com.colpencil.secondhandcar.Bean.Response.BrandList;
import com.colpencil.secondhandcar.Bean.Response.Browse;
import com.colpencil.secondhandcar.Bean.Response.CarInfo;
import com.colpencil.secondhandcar.Bean.Response.CarResult;
import com.colpencil.secondhandcar.Bean.Response.CarResultRes;
import com.colpencil.secondhandcar.Bean.Response.CarType;
import com.colpencil.secondhandcar.Bean.Response.Collection;
import com.colpencil.secondhandcar.Bean.Response.Feedback;
import com.colpencil.secondhandcar.Bean.Response.FriendRecommend;
import com.colpencil.secondhandcar.Bean.Response.GoodsInfo;
import com.colpencil.secondhandcar.Bean.Response.Home;
import com.colpencil.secondhandcar.Bean.Response.HotSearch;
import com.colpencil.secondhandcar.Bean.Response.Installment;
import com.colpencil.secondhandcar.Bean.Response.Login;
import com.colpencil.secondhandcar.Bean.Response.Logout;
import com.colpencil.secondhandcar.Bean.Response.Message;
import com.colpencil.secondhandcar.Bean.Response.MessageCount;
import com.colpencil.secondhandcar.Bean.Response.MessageInfo;
import com.colpencil.secondhandcar.Bean.Response.MineDepreciateNotice;
import com.colpencil.secondhandcar.Bean.Response.MineRemind;
import com.colpencil.secondhandcar.Bean.Response.MineSub;
import com.colpencil.secondhandcar.Bean.Response.MineSubscribe;
import com.colpencil.secondhandcar.Bean.Response.MsgPeriod;
import com.colpencil.secondhandcar.Bean.Response.Order;
import com.colpencil.secondhandcar.Bean.Response.PayResult;
import com.colpencil.secondhandcar.Bean.Response.PeriodBuyCar;
import com.colpencil.secondhandcar.Bean.Response.PeriodCar;
import com.colpencil.secondhandcar.Bean.Response.PersonInfo;
import com.colpencil.secondhandcar.Bean.Response.PersonalGroom;
import com.colpencil.secondhandcar.Bean.Response.Props;
import com.colpencil.secondhandcar.Bean.Response.Province;
import com.colpencil.secondhandcar.Bean.Response.Repayment;
import com.colpencil.secondhandcar.Bean.Response.Result_comment;
import com.colpencil.secondhandcar.Bean.Response.SellAdv;
import com.colpencil.secondhandcar.Bean.Response.SellApply;
import com.colpencil.secondhandcar.Bean.Response.SellCarRecord;
import com.colpencil.secondhandcar.Bean.Response.SendCode;
import com.colpencil.secondhandcar.Bean.Response.Subscribe;
import com.colpencil.secondhandcar.Bean.Response.SubscribeGood;
import com.colpencil.secondhandcar.Bean.Response.Url;
import com.colpencil.secondhandcar.Bean.Result;
import com.colpencil.secondhandcar.Bean.ResultInfo;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/5/2.
 * api
 */
public interface CarApi {
//
    /**
     * 登陆
     * @param params
     * @return
     */
    @POST("api/mobile/member/login.do")
    @FormUrlEncoded
    Observable<ResultInfo<Login>> login(@FieldMap()HashMap<String, String> params);

    /**
     * 发送验证码
     * @param mobile
     * @return
     */
    @POST("api/mobile/member/send-code.do")
    @FormUrlEncoded
    Observable<SendCode> sendCode(@Field("mobile") String mobile);

    /**
     * 城市列表
     * @return
     */
    @POST("api/mobile/regions/getCity.do")
    Observable<Result<City>> getCity();

    /**
     * 好车推荐
     * @param params
     * @return
     */
    @POST("api/mobile/goods/goodCar.do")
    @FormUrlEncoded
    Observable<Result<FriendRecommend>> getRecommend(@FieldMap() HashMap<String, String> params);

    /**
     * 提交反馈
     * @param params
     * @return
     */
    @POST("api/mobile/suggestion/add.do")
    @FormUrlEncoded
    Observable<Result<Feedback>> commitFeddback(@FieldMap()HashMap<String, String> params);

    /**
     * 意见反馈列表
     * @param memberId
     * @return
     */
    @POST("api/mobile/suggestion/list.do")
    @FormUrlEncoded
    Observable<Result<Feedback>> feedbackList(@Field("member_id") int memberId, @Field("token") String token);

    /**
     * 品牌车系
     * @param params
     * @return
     */
    @POST("api/mobile/goodscat/getSearchCat.do")
    @FormUrlEncoded
    Observable<ResultInfo<BrandList>> brandList(@FieldMap() HashMap<String, String> params);

    /**
     * 车辆类型
     * @param params
     * @return
     */
    @POST("api/mobile/goodscat/getCat.do")
    @FormUrlEncoded
    Observable<Result<BrandCar>> carList(@FieldMap() HashMap<String, String> params);

    /**
     * 卖车申请
     * @param params
     * @return
     */
    @Multipart
    @POST("api/mobile/store/apply-save.do")
    Observable<ResultInfo<SellApply>> applySell(@PartMap() HashMap<String, RequestBody> params);

    /**
     * 卖车申请数据
     * @return
     */
    @POST("api/mobile/store/apply.do")
    Observable<Result<SellAdv>> sellData();

    /**
     * 申请进度
     * @param params
     * @return
     */
    @POST("api/mobile/store/status.do")
    @FormUrlEncoded
    Observable<ResultInfo<ApplyRate>> applyRate(@FieldMap()HashMap<String, String> params);

    /**
     * 首页
     * @return
     */
    @POST("api/mobile/index/list.do")
    Observable<ResultInfo<Home>> homeInfo();

    /**
     * 车辆详情
     * @param params
     * @return
     */
    @POST("api/mobile/goods/carDetail.do")
    @FormUrlEncoded
    Observable<ResultInfo<CarInfo>> carInfo(@FieldMap()HashMap<String, String> params);

    /**
     * 热门搜索
     * @return
     */
    @POST("api/mobile/index/full-search.do")
    Observable<Result<HotSearch>> hotSearch();

    /**
     * 退出登录
     * @param memberId
     * @return
     */
    @POST("api/mobile/member/logout.do")
    @FormUrlEncoded
    Observable<Logout> logout(@Field("member_id") int memberId);

    /**
     * 关于银台
     * @return
     */
    @POST("api/mobile/about/list.do")
    Observable<ResultInfo<Url>> about();

    /**
     * 分期列表
     * @return
     */
    @POST("api/mobile/goods/getInstallment.do")
    Observable<Result<Installment>> installment();

    /**
     * 分期购车列表
     * @param goodsId
     * @return
     */
    @POST("api/mobile/goods/installmentBuy.do")
    @FormUrlEncoded
    Observable<ResultInfo<PeriodCar>> periodCar(@Field("goods_id") int goodsId);

    /**
     * 添加浏览记录
     * @param params
     * @return
     */
    @POST("api/mobile/record/add.do")
    @FormUrlEncoded
    Observable<Browse> addBrowse(@FieldMap() HashMap<String, String> params);

    /**
     * 浏览记录
     * @param params
     * @return
     */
    @POST("api/mobile/record/list.do")
    @FormUrlEncoded
    Observable<Result<PersonalGroom>> browseRecord(@FieldMap() HashMap<String, String> params);

    /**
     * 删除浏览记录
     * @param params
     * @return
     */
    @POST("api/mobile/record/delete.do")
    @FormUrlEncoded
    Observable<Browse> deleteBrowse(@FieldMap() HashMap<String, String> params);

    /**
     * 清空浏览记录
     * @param params
     * @return
     */
    @POST("api/mobile/record/clean.do")
    @FormUrlEncoded
    Observable<Browse> cleanBrowse(@FieldMap() HashMap<String, String> params);

    /**
     * 添加收藏记录
     * @param params
     * @return
     */
    @POST("api/mobile/collection/add.do")
    @FormUrlEncoded
    Observable<Collection> addCollection(@FieldMap()HashMap<String, String> params);

    /**
     * 收藏记录
     * @param params
     * @return
     */
    @POST("api/mobile/collection/list.do")
    @FormUrlEncoded
    Observable<Result<PersonalGroom>> collectionRecord(@FieldMap()HashMap<String, String> params);

    /**
     * 删除收藏
     * @param params
     * @return
     */
    @POST("api/mobile/collection/delete.do")
    @FormUrlEncoded
    Observable<Collection> deleteCollection(@FieldMap()HashMap<String, String> params);

    /**
     * 清空收藏
     * @param params
     * @return
     */
    @POST("api/mobile/collection/clean.do")
    @FormUrlEncoded
    Observable<Collection> cleanCollection(@FieldMap() HashMap<String, String> params);

    /**
     * 首页订阅数量
     * @param params
     * @return
     */
    @POST("api/mobile/subscribe/getNum.do")
    @FormUrlEncoded
    Observable<Subscribe> homeSubscribeNum(@FieldMap()HashMap<String, String> params);

    /**
     * 首页紧急消息
     * @return
     */
    @POST("api/mobile/index/popup.do")
    Observable<ResultInfo<MessageInfo>> popup();

    /**
     * 我的订阅
     * @param params
     * @return
     */
    @POST("api/mobile/subscribe/mySubscribe.do")
    @FormUrlEncoded
    Observable<Result<MineSubscribe>> mineSubscribe(@FieldMap()HashMap<String, String> params);

    /**
     * 添加订阅
     * @param params
     * @return
     */
    @POST("api/mobile/subscribe/add.do")
    @FormUrlEncoded
    Observable<MineSub> addSubscribe(@FieldMap()HashMap<String, String> params);

    /**
     * 删除订阅
     * @param params
     * @return
     */
    @POST("api/mobile/subscribe/delete.do")
    @FormUrlEncoded
    Observable<MineSub> deleteSubscribe(@FieldMap()HashMap<String, String> params);

    /**
     * 清空订阅
     * @param params
     * @return
     */
    @POST("api/mobile/subscribe/clean.do")
    @FormUrlEncoded
    Observable<Result_comment> cleanSubscribe(@FieldMap()HashMap<String, String> params);

    /**
     * 订阅列表
     * @param params
     * @return
     */
    @POST("api/mobile/subscribe/list.do")
    @FormUrlEncoded
    Observable<Result<SubscribeGood>> subscribeRecord(@FieldMap()HashMap<String, String> params);

    /**
     * 车辆类型
     * @return
     */
    @POST("api/mobile/goodscat/getType.do")
    Observable<Result<CarType>> carType();

    /**
     * 获取属性
     * @param typeId
     * @return
     */
    @POST("api/mobile/goods/getProps.do")
    @FormUrlEncoded
    Observable<ResultInfo<Props>> getProps(@Field("type_id") int typeId);

    /**
     * 广告点击次数
     * @param id
     * @return
     */
    @POST("api/mobile/index/adv-count.do")
    @FormUrlEncoded
    Observable<Result_comment> advCount(@Field("aid") int id);

    /**
     * 添加降价提醒
     * @param params
     * @return
     */
    @POST("api/mobile/reducePrice/add.do")
    @FormUrlEncoded
    Observable<Result_comment> addReducePrice(@FieldMap()HashMap<String, String> params);

    /**
     * 删除降价提醒
     * @param params
     * @return
     */
    @POST("api/mobile/reducePrice/delete.do")
    @FormUrlEncoded
    Observable<Result_comment> deleteReduce(@FieldMap()HashMap<String, String> params);

    /**
     * 降价提醒记录
     * @param params
     * @return
     */
    @POST("api/mobile/reducePrice/list.do")
    @FormUrlEncoded
    Observable<ResultInfo<MineRemind>> redecuPriceRecord(@FieldMap()HashMap<String, String> params);

    /**
     * 清空降价记录
     * @param params
     * @return
     */
    @POST("api/mobile/reducePrice/clean.do")
    @FormUrlEncoded
    Observable<Result_comment> cleanReduceRecord(@FieldMap()HashMap<String, String> params);

    /**
     * 消息中心
     * @param params
     * @return
     */
    @POST("api/mobile/message-center/list.do")
    @FormUrlEncoded
    Observable<ResultInfo<Message>> getMessage(@FieldMap()HashMap<String, String> params);

    /**
     * 系统消息列表
     * @param params
     * @return
     */
    @POST("api/mobile/message-center/sys-list.do")
    @FormUrlEncoded
    Observable<Result<MessageInfo>> systemMessageRecord(@FieldMap()HashMap<String, String> params);

    /**
     * 消息详情
     * @param id
     * @return
     */
    @POST("api/mobile/message-center/sys-info.do")
    @FormUrlEncoded
    Observable<ResultInfo<Url>> messageContent(@Field("id") int id);

    /**
     * 分期购车消息列表
     * @param params
     * @return
     */
    @POST("api/mobile/message-center/ins_list.do")
    @FormUrlEncoded
    Observable<Result<MsgPeriod>> insMsgRecord(@FieldMap()HashMap<String, String> params);

    /**
     * 降价通知列表
     * @param params
     * @return
     */
    @POST("api/mobile/message-center/reduce_list.do")
    @FormUrlEncoded
    Observable<Result<MineDepreciateNotice>> reduceRocord(@FieldMap()HashMap<String, String> params);

    /**
     * 服务条款
     * @return
     */
    @POST("api/mobile/member/term.do")
    Observable<ResultInfo<Url>> term();

    /**
     * 搜索车辆结果
     * @param params
     * @return
     */
    @POST("api/mobile/goods/list.do")
    @FormUrlEncoded
    Observable<ResultInfo<CarResultRes>> carResult(@FieldMap()HashMap<String, String> params);

    /**
     * 发布商品
     * @param params
     * @return
     */
    @POST("api/mobile/goods/addGoods.do")
    @Multipart
    Observable<Result_comment> addGoods(@PartMap()HashMap<String, RequestBody> params);

    /**
     * 修改商品
     * @param params
     * @return
     */
    @POST("api/mobile/goods/editGoods.do")
    @Multipart
    Observable<Result_comment> editGoods(@PartMap()HashMap<String, RequestBody> params);

    /**
     * 根据城市名获取id
     * @param cityName
     * @return
     */
    @POST("api/mobile/regions/getByName.do")
    @FormUrlEncoded
    Observable<ResultInfo<Province>> getCityId(@Field("city_name") String cityName);

    /**
     * 获取修改的商品信息
     * @param params
     * @return
     */
    @POST("api/mobile/goods/edit.do")
    @FormUrlEncoded
    Observable<ResultInfo<GoodsInfo>> getGoodsInfo(@FieldMap()HashMap<String, String> params);

    /**
     * 卖家商品列表
     * @param params
     * @return
     */
    @POST("api/mobile/goods/store_goods.do")
    @FormUrlEncoded
    Observable<Result<SellCarRecord>> storeGoods(@FieldMap()HashMap<String, String> params);

    /**
     * 商品上下架
     * @param params
     * @return
     */
    @POST("api/mobile/goods/shelves.do")
    @FormUrlEncoded
    Observable<Result_comment> shelves(@FieldMap()HashMap<String, String> params);

    /**
     * 结算记录
     * @param params
     * @return
     */
    @POST("api/mobile/carOrder/store_settlement.do")
    @FormUrlEncoded
    Observable<Result<BillRecord>> billRecord(@FieldMap()HashMap<String, String> params);

    /**
     * 结算详情
     * @param params
     * @return
     */
    @POST("api/mobile/carOrder/settlement_details.do")
    @FormUrlEncoded
    Observable<ResultInfo<BillDetails>> billDetails(@FieldMap()HashMap<String, String> params);

    /**
     * 分期账单列表
     * @param params
     * @return
     */
    @POST("api/mobile/payBill/list.do")
    @FormUrlEncoded
    Observable<Result<Repayment>> repayment(@FieldMap()HashMap<String, String> params);

    /**
     * 卖家订单列表
     * @param params
     * @return
     */
    @POST("api/mobile/carOrder/store_order.do")
    @FormUrlEncoded
    Observable<Result<Order>> storeOrder(@FieldMap()HashMap<String, String> params);

    /**
     * 添加订单
     * @param params
     * @return
     */
    @POST("api/mobile/carOrder/create.do")
    @FormUrlEncoded
    Observable<Result_comment> addOrder(@FieldMap()HashMap<String, String> params);

    /**
     * 预约记录
     * @param params
     * @return
     */
    @POST("api/mobile/carOrder/order_list.do")
    @FormUrlEncoded
    Observable<Result<Order>> orderRecord(@FieldMap()HashMap<String, String> params);

    /**
     * 转分期
     * @param params
     * @return
     */
    @POST("api/mobile/carOrder/goInstallment.do")
    @FormUrlEncoded
    Observable<ResultInfo<PeriodBuyCar>> goInstallment(@FieldMap()HashMap<String, String> params);

    /**
     * 取消订单
     * @param params
     * @return
     */
    @POST("api/mobile/carOrder/cancel.do")
    @FormUrlEncoded
    Observable<Result_comment> cancel(@FieldMap()HashMap<String, String> params);

    /**
     * 支付
     */
    @POST("api/mobile/icbc/pay.do")
    @FormUrlEncoded
    Observable<PayResult> pay(@FieldMap()HashMap<String,String> params);


    /**
     * 交易失败
     * @param params
     * @return
     */
    @POST("api/mobile/carOrder/failure.do")
    @FormUrlEncoded
    Observable<Result_comment> failure(@FieldMap()HashMap<String, String> params);

    /**
     * 创建分期账单
     * @param params
     * @return
     */
    @POST("api/mobile/carOrder/create_installment.do")
    @FormUrlEncoded
    Observable<Result_comment> createInstallment(@FieldMap()HashMap<String, String> params);

    /**
     * 个人信息
     * @param params
     * @return
     */
    @POST("api/mobile/self/info.do")
    @FormUrlEncoded
    Observable<ResultInfo<PersonInfo>> personInfo(@FieldMap()HashMap<String, String> params);

    /**
     * 修改个人信息
     * @param params
     * @return
     */
    @POST("api/mobile/self/edit.do")
    @FormUrlEncoded
    Observable<Result_comment> changeInfo(@FieldMap()HashMap<String, String> params);

    /**
     * 添加商品
     * @return
     */
    @POST("api/mobile/front_html/addGoods.do")
    @FormUrlEncoded
    Observable<ResultInfo<Url>> htmlUrl(@FieldMap()HashMap<String, String> params);

    /**
     * 修改商品
     * @param params
     * @return
     */
    @POST("api/mobile/front_html/editGoods.do")
    @FormUrlEncoded
    Observable<ResultInfo<Url>> editHtmlUrl(@FieldMap()HashMap<String, String> params);

    /**
     * 获取所有消息和本地做判断
     * @param params
     * @return
     */
    @POST("api/mobile/message-center/has-message.do")
    @FormUrlEncoded
    Observable<Result<MessageCount>> getMessageCount(@FieldMap()HashMap<String, String> params);
}
