package com.gykj.rollcall.model.api;

import android.support.v4.util.ArrayMap;
import android.util.Base64;
import android.util.Log;

import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.gykj.mvvmlibrary.entity.BaseEntity;
import com.gykj.mvvmlibrary.utils.AESHelper;
import com.gykj.mvvmlibrary.utils.Contract;
import com.gykj.mvvmlibrary.utils.JsonUtils;
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
import com.gykj.rollcall.model.service.RollCallService;
import com.gykj.rollcall.utils.RollCallClient;
import com.gykj.rollcall.utils.SharedPreferencedUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.PUT;

import static com.gykj.mvvmlibrary.utils.AESHelper.hexStrToByte;

/**
 * desc   :
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2414:39
 * version: 1.0
 */
public class RollCallApi {

    RollCallService mService;
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");

    private RollCallApi(){
        mService = RollCallClient.getInstance().create(RollCallService.class);
    }

    private static class SingletonHolder {
        private static RollCallApi INSTANCE = new RollCallApi();
    }

    public static RollCallApi getInstance() {
        return RollCallApi.SingletonHolder.INSTANCE;
    }

    /**登录接口**/
    public Observable<TokenEntity> login(String account,String password) {
        String key = "pigxpigxpigxpigx";

        AES aes = new AES(Mode.CBC, Padding.NoPadding,
                new SecretKeySpec(key.getBytes(), "AES"),
                    new IvParameterSpec(key.getBytes()));

         byte[] data = AESHelper.byteToHex(password);
         String new_passwrd = aes.encryptBase64(data);
         Log.d("AES",new_passwrd);

         //String ars_password = AESHelper.enctrypt(password,key);

        //String ars_password = aes.encrypt(password.getBytes());
        //System.out.println("AES加密："+ ars_password);
       // System.out.println("AES加密1："+ ars_password1);

     /*   String m = aes.decryptStrFromBase64(ars_password);
        System.out.println("AES解密："+ m);*/

   //     String m1 = AESHelper.decrypt(ars_password1,key);
    //    System.out.println("AES解密1："+ m1);

        Map<String,String> map = new ArrayMap<>();
        map.put("username",account);
        map.put("password", new_passwrd);//AES加密
       // map.put("code","ppxg");
        map.put("grant_type","password");
        map.put("scope","server");

        return mService.login(map);
    }

    /**获取设备bindid**/
    public  Observable<BaseEntity<Integer>> articleborrow(int id,String header){
        return mService.articleborrow(id,header);
    }

    /**发布通知接口**/
    public Observable<BaseEntity<Boolean>> addnotice(String title,String desc,String imgUrl)
    {
        Map<String,String> map = new ArrayMap<>();
        map.put("noticeTitle",title);
        map.put("noticeImgUrl",imgUrl);
        map.put("noticeDesc",desc);
        map.put("buildingId", SharedPreferencedUtils.getStr(Contract.buildingId,""));
        map.put("userId",SharedPreferencedUtils.getStr(Contract.USER_ID));
        //RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,JsonUtils.map2json(map));
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, JsonUtils.map2json(map));
        return  mService.addnotice(body,Contract.Authorization);
    }

    /**获取通知列表*/
    public Observable<BaseEntity<NoticeBean>> notice(int  PageIndex,int PageSize){
        System.out.println("Contract.buildingId ::"+SharedPreferencedUtils.getStr(Contract.buildingId));
        Map<String,String> map = new ArrayMap<>();
        map.put("current",PageIndex+"");
        map.put("size",PageSize+"");
        map.put("buildingId",SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        map.put("status","1");
        //RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,JsonUtils.map2json(map));
        return  mService.notice(map,Contract.Authorization);
    }

    /**删除通知**/
    public Observable<BaseEntity<String>> delnotice(int id)
    {
        return  mService.delnotice(id,Contract.Authorization);
    }

    /**修改通知*/
    public Observable<BaseEntity<String>> changenotice(int id,String title,String imgUrl,String desc)
    {
        Map<String,String> map = new ArrayMap<>();
        map.put("id",id+"");
        map.put("noticeTitle",title);
        map.put("noticeImgUrl",imgUrl);
        map.put("noticeDesc",desc);
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,JsonUtils.map2json(map));
        return mService.changenotice(body,Contract.Authorization);
    }

    /**获取点名所需宿舍**/
    public Observable<BaseEntity<List<DormitoryBean>>>  dormitory(){
        Map<String,String> map = new ArrayMap<>();
        map.put("buildingId",SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
       // map.put("status（可选",title);
        return mService.dormitory(map,Contract.Authorization);
    }
    /**发起点名**/
    public Observable<BaseEntity<Integer>> rollcallrule(int rolltype,String startTime,String endTime,
                                String singleStartDate,String singleEndDate,String weekList,String dormitoryList)
    {
        Map<String,String> map = new ArrayMap<>();
        map.put("buildingId",SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        map.put("rollType",rolltype+"");

        map.put("startTime",startTime);
        map.put("endTime",endTime);
        if(rolltype ==0)
        {
            map.put("singleStartDate",singleStartDate);
            map.put("singleEndDate",singleEndDate);
        }else {
            map.put("weekList",weekList);//定点点名的星期选择 weekList
        }
        //点名的宿舍选择
        map.put("dormitoryList",dormitoryList);//（全部-1）
        // map.put("status（可选",title);
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,JsonUtils.map2json(map));
        return mService.rollcallrule(body,Contract.Authorization);
    }

    /**
     *获取点名规则
     */
    public Observable<BaseEntity<RollPageBean>>  rollpage1(int PageIndex,int PageSize){
        Map<String,String> map = new ArrayMap<>();
        map.put("buildingId",SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        map.put("current",PageIndex+"");
        map.put("size",PageSize+"");
      //    map.put("status",1+"");
        return mService.rollpage(map,Contract.Authorization);
    }


    /**
     *修改点名规则
     */
    public Observable<BaseEntity<String>>  changeroll(int rolltype,int id,String startTime,String endTime,
                                                      String singleStartDate,String singleEndDate,String weekList,String dormitoryList){
        Map<String,String> map = new ArrayMap<>();
        map.put("id",id+"");
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        if(rolltype ==0)
        {
            map.put("singleStartDate",singleStartDate);
            map.put("singleEndDate",singleEndDate);
        }else {
            map.put("weekList",weekList);//定点点名的星期选择 weekList
        }
        //点名的宿舍选择
        map.put("dormitoryList",dormitoryList);//（全部-1）
        // map.put("status（可选",title);
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,JsonUtils.map2json(map));
        return mService.changeroll(body,Contract.Authorization);
    }

    /**
     * 删除某一个点名规则
     */
    public Observable<BaseEntity<String>> deleteroll(int id)
    {
        return  mService.deleteroll(id,Contract.Authorization);
    }

    /**
     * 获取物品借用列表
     */
    public Observable<BaseEntity<BorrowBean>> borrow(int PageIndex,int PageSize)
    {
        Map<String,String> map = new ArrayMap<>();
        map.put("buildingId",SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        map.put("current",PageIndex+"");
        map.put("size",PageSize+"");
      //  map.put("status",1+"");
        return mService.borrow(map,Contract.Authorization);
    }
    /**
     * 修改某条物品借用
     */

    public  Observable<BaseEntity<String>> changeborrow(String id,String articleName,String articleNum){

        Map<String,String> map = new ArrayMap<>();
        map.put("id",id);
        map.put("buildingId",SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        map.put("articleName",articleName);
        map.put("articleNum",articleNum);
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,JsonUtils.map2json(map));
        return mService.changeborrow(body,Contract.Authorization);
    }

    /**
     * 删除某条物品借用
     */
    public Observable<BaseEntity<String>> deleteborrow(int id)
    {
        return  mService.deleteborrow(id,Contract.Authorization);
    }

    /**
     * 归还物品接口
     */
    public Observable<BaseEntity<String>> returnborrow(int id  ,int status)
    {
        Map<String,String> map = new ArrayMap<>();
        map.put("id",id+"");
        map.put("status",status+"");
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,JsonUtils.map2json(map));
        return mService.returnborrow(body,Contract.Authorization);
    }
    /**
     * 搜素物品接口  根据学生姓名和学号
     */

    public  Observable<BaseEntity<BorrowBean.RecordsBean>> searchborrow(String patten)
    {
        Map<String,String> map = new ArrayMap<>();
        map.put("buildingId",SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        map.put("patten",patten);
        return mService.searchborrow(map,Contract.Authorization);
    }

    /***
     * 借用登记接口
     */
    public  Observable<BaseEntity<String>> addborrow(String userNum,String userName,String  userId,String dormitoryId,String articleName,String articleNum)
    {
        Map<String,String> map = new ArrayMap<>();
        map.put("buildingId",SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        map.put("userNum",userNum);
        map.put("userName",userName);
        map.put("userId",userId);
        map.put("dormitoryId",dormitoryId);
        map.put("articleName",articleName);
        map.put("articleNum","1");
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,JsonUtils.map2json(map));
        return mService.addborrow(body,Contract.Authorization);
    }

    /**
     * 物品报废列表接口
     */
    public  Observable<BaseEntity<LossBean>> loss(int PageIndex,int PageSize)
    {
        Map<String,String> map = new ArrayMap<>();
        map.put("buildingId",SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        map.put("current",PageIndex+"");
        map.put("size",PageSize+"");
        return mService.loss(map,Contract.Authorization);
    }

    /**
     * 修改处理状态
     */
    public Observable<BaseEntity<String>> reportloss(int id  ,int status)
    {
        Map<String,String> map = new ArrayMap<>();
        map.put("id",id+"");
        map.put("status",status+"");
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,JsonUtils.map2json(map));
        return mService.reportloss(body,Contract.Authorization);
    }


    /**
     * 报警记录
     */
    public Observable<BaseEntity<PoliceBean>> warrpage(int PageIndex, int PageSize)
    {
        Map<String,String> map = new ArrayMap<>();
        map.put("buildingId",SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        map.put("current",PageIndex+"");
        map.put("size",PageSize+"");
        return mService.warrpage(map,Contract.Authorization);
    }

    /**
     * 根据点名规则id发起点名
     */
    public  Observable<BaseEntity<StartCallBean>> rollCallRuleId(int id){
        return mService.rollCallRuleId(id,Contract.Authorization);
    }
    /**
     * 修改点名规则状态
     */
    public Observable<BaseEntity<Object>> changestatusroll(int id  ,int status)
    {
        Map<String,String> map = new ArrayMap<>();
        map.put("id",id+"");
        map.put("status",status+"");
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,JsonUtils.map2json(map));
        return mService.changestatusroll(body,Contract.Authorization);
    }


    /**
     *获取正在点名的RollcallID LIST
     * @param status 0:正在进行的点名   -1：已结束
     * @return
     */
    public Observable<BaseEntity<RollCallIdBean>> rollCallIdPage(int status)
    {
        Map<String,String> map = new ArrayMap<>();
        map.put("buildingId",SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        map.put("status",status+"");
        return mService.rollCallIdPage(map,Contract.Authorization);
    }


    /**
     * 获取某一个正在点名详情
     * @param RollcallID
     * @return
     */
    public  Observable<BaseEntity<StartRecordBean>> rollCallDetail(int RollcallID){

        return mService.rollCallDetail(RollcallID,Contract.Authorization);
    }

    /**
     * 时间到   结束某一次点名
     * @param RollcallID
     * @return
     */
    public  Observable<BaseEntity<String>> endCall(int RollcallID){
        return mService.endCall(RollcallID,Contract.Authorization);
    }

    /**
     * 获取个人未签到TOP10
     * @return
     */
    public Observable<BaseEntity<List<EndUserBean>>> userUnSignTop(){
        int buildid = Integer.parseInt(SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        return mService.userUnSignTop(buildid,Contract.Authorization);
    }

    /**
     * 宿舍未到TOP10
     * @return
     */
    public Observable<BaseEntity<List<EndDormitorBean>>> dormitoryUnSignNumTop(){
        int buildid = Integer.parseInt(SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        return mService.dormitoryUnSignNumTop(buildid,Contract.Authorization);
    }


    /**
     * 未到率TOP10
     * @return
     */

    public  Observable<BaseEntity<Object>> unSignPrtTop(){
        int buildid = Integer.parseInt(SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        return mService.unSignPrtTop(buildid,Contract.Authorization);
    }

    /**
     * 历史签到人数
     * @return
     */

    public  Observable<BaseEntity<Object>> rollHistory(String   startTime ,String endtime){
        Map<String,String> map = new ArrayMap<>();
        map.put("buildingId",SharedPreferencedUtils.getStr(Contract.buildingId,"1"));
        map.put("startTime",startTime);
        map.put("endTime",endtime);
        return mService.rollHistory(map,Contract.Authorization);
    }
}
