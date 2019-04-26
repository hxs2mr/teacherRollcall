package com.gykj.rollcall.model.service;

import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.utils.Contract;
import com.gykj.rollcall.entity.StartRecordBean;
import com.gykj.rollcall.entity.TokenEntity;
import com.gykj.rollcall.model.BorrowBean;
import com.gykj.rollcall.model.DormitoryBean;
import com.gykj.rollcall.model.EndDormitorBean;
import com.gykj.rollcall.model.EndUserBean;
import com.gykj.rollcall.model.LossBean;
import com.gykj.rollcall.model.NoticeAddBean;
import com.gykj.rollcall.model.NoticeBean;
import com.gykj.rollcall.model.PoliceBean;
import com.gykj.rollcall.model.RollCallIdBean;
import com.gykj.rollcall.model.RollPageBean;
import com.gykj.rollcall.model.StartCallBean;
import com.gykj.rollcall.model.WarringBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * desc   : 点名系统后台接口
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2414:48
 * version: 1.0
 */
public interface RollCallService {

    //登录接口
    @GET("auth/oauth/token")
    Observable<TokenEntity> login(@QueryMap Map<String,String> map);


    //设备设备序列号
    @GET("dormitory/userbuildingrelation/getInfoBySerial/{serial}")
    Observable<BaseEntity<Integer>> articleborrow(@Path("serial") int id , @Header("Authorization")String Authorization);



    //发布通知
    @POST("dormitory/publicnotice")
    Observable<BaseEntity<Boolean>> addnotice(@Body RequestBody body,@Header("Authorization")String Authorization);

    //获取通知列表
    @GET("dormitory/publicnotice/page")
    Observable<BaseEntity<NoticeBean>> notice(@QueryMap Map<String,String> map,@Header("Authorization")String Authorization);


    //删除通知
    @DELETE("dormitory/publicnotice/{id}")
    Observable<BaseEntity<String>>  delnotice(@Path("id") int id,@Header("Authorization")String Authorization);

    //修改通知
     @PUT("dormitory/publicnotice")
    Observable<BaseEntity<String>> changenotice(@Body  RequestBody  map,@Header("Authorization")String Authorization);


     //获取点名宿舍
     @GET("dormitory/dormitory/list")
     Observable<BaseEntity<List<DormitoryBean>>> dormitory(@QueryMap Map<String,String> map, @Header("Authorization")String Authorization);


      //发布点名 单次点名  定点点名
      @POST("dormitory/rollcallrule")
      Observable<BaseEntity<Integer>> rollcallrule(@Body RequestBody body,@Header("Authorization")String Authorization);

      //获取点名的规则
     @GET("dormitory/rollcallrule/page")
     Observable<BaseEntity<RollPageBean>> rollpage(@QueryMap Map<String,String> map, @Header("Authorization")String Authorization);

    //编辑点名的规则
    @PUT("dormitory/rollcallrule")
    Observable<BaseEntity<String>> changeroll(@Body RequestBody body, @Header("Authorization")String Authorization);


    //删除某条点名规则
    @DELETE("dormitory/rollcallrule/{id}")
    Observable<BaseEntity<String>> deleteroll(@Path("id") int id, @Header("Authorization")String Authorization);


    /**物品借用模块**/
    //获取物品借用列表
    @GET("dormitory/articleborrow/page")
    Observable<BaseEntity<BorrowBean>> borrow(@QueryMap Map<String,String> map, @Header("Authorization")String Authorization);

    //修改接口
    @PUT("dormitory/articleborrow")
    Observable<BaseEntity<String>> changeborrow(@Body RequestBody body, @Header("Authorization")String Authorization);

    //删除接口
    @DELETE("dormitory/articleborrow/{id}")
    Observable<BaseEntity<String>> deleteborrow(@Path("id") int id, @Header("Authorization")String Authorization);

    //归还接口
    @PUT("dormitory/articleborrow")
    Observable<BaseEntity<String>> returnborrow(@Body RequestBody nody, @Header("Authorization")String Authorization);


    //搜素接口
    @GET("dormitory/articleborrow/page")
    Observable<BaseEntity<BorrowBean.RecordsBean>> searchborrow(@QueryMap Map<String,String> map, @Header("Authorization")String Authorization);

    //借用登记接口
    @POST("/dormitory/articleborrow")
    Observable<BaseEntity<String>> addborrow(@Body RequestBody body, @Header("Authorization")String Authorization);

    //物品报废列表接口
    @GET("dormitory/reportloss/page")
    Observable<BaseEntity<LossBean>> loss(@QueryMap Map<String,String> map, @Header("Authorization")String Authorization);

    //修改处理状态
    @PUT("dormitory/reportloss")
    Observable<BaseEntity<String>> reportloss(@Body RequestBody nody, @Header("Authorization")String Authorization);


    //报警记录列表
    @GET("dormitory/fbiwarning/page")
    Observable<BaseEntity<PoliceBean>> warrpage(@QueryMap Map<String,String> map, @Header("Authorization")String Authorization);


    //发起点名
    @GET("dormitory/rollcall/launchCall/{rollCallRuleId}")
    Observable<BaseEntity<StartCallBean>> rollCallRuleId(@Path("rollCallRuleId") int id, @Header("Authorization")String Authorization);


    //修改点名规则status
    @PUT("dormitory/rollcallrule")
    Observable<BaseEntity<Object>> changestatusroll(@Body RequestBody body, @Header("Authorization")String Authorization);

    //获取正在点名时候RollcallID
    @GET("dormitory/rollcall/page")
    Observable<BaseEntity<RollCallIdBean>> rollCallIdPage(@QueryMap Map<String,String> map, @Header("Authorization")String Authorization);

    //根据RollcallID获取当前点名的详情
    @GET("dormitory/rollcall/callDetail/{rollCallId}")
    Observable<BaseEntity<StartRecordBean>> rollCallDetail(@Path("rollCallId") int rollCallId, @Header("Authorization")String Authorization);


    //结束此次点名

    @GET("dormitory/rollcall/endCall/{rollCallRuleId}")
    Observable<BaseEntity<String>> endCall(@Path("rollCallRuleId") int rollCallRuleId, @Header("Authorization")String Authorization);


    /**
     * 签到统计
     * 个人未签到top10
     * @param rollCallRuleId
     * @param Authorization
     * @return
     */
    @GET("dormitory/rollcallrecord/userUnSignTop/{buildingId}")
    Observable<BaseEntity<List<EndUserBean>>> userUnSignTop(@Path("buildingId") int rollCallRuleId, @Header("Authorization")String Authorization);

    //宿舍未到总数TOP10
    @GET("dormitory/rollcallrecord/dormitoryUnSignNumTop/{buildingId}")
    Observable<BaseEntity<List<EndDormitorBean>>> dormitoryUnSignNumTop(@Path("buildingId") int rollCallRuleId, @Header("Authorization")String Authorization);


    //未到率TOP10
    @GET("dormitory/rollcallrecord/unSignPrtTop/{buildingId}")
    Observable<BaseEntity<Object>> unSignPrtTop(@Path("buildingId") int rollCallRuleId, @Header("Authorization")String Authorization);


    //历史签到
    @GET("dormitory/ollcallrecord/rollHistory")
    Observable<BaseEntity<Object>> rollHistory(@QueryMap Map<String,String> map, @Header("Authorization")String Authorization);
}
