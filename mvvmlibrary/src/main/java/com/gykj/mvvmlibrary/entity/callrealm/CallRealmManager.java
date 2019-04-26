package com.gykj.mvvmlibrary.entity.callrealm;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Data on :2019/4/18 0018
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class CallRealmManager {

    private CallRealmManager(){};

    private static  class  CallHolder{
        private static CallRealmManager callRealmManager = new CallRealmManager();
    }
    public static CallRealmManager getInstance()
    {
        return  CallHolder.callRealmManager;
    }

    /**
     * 添加点名规则到数据库
     * @param rollCallRuleId
     * @param status
     * @param startdate
     * @param enddate
     * @param starttime
     * @param endtime
     * @param rollType
     * @param weekList
     * @param listener
     */
    public void addCallRealm(int rollCallRuleId, int status, String startdate, String enddate,
                             String starttime, String endtime, int rollType, String weekList, IAddRealmListener listener){

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CallRealm callRealm = new CallRealm();
                callRealm.setRollCallRuleId(rollCallRuleId);
                callRealm.setStatus(status);
                callRealm.setStartdate(startdate);
                callRealm.setEnddate(enddate);
                callRealm.setStarttime(starttime);
                callRealm.setEndtime(endtime);
                callRealm.setRollType(rollType);
                callRealm.setWeekList(weekList);
                callRealm.setIsruning(0);
                callRealm.setRollCallId(0);
                realm.copyToRealm(callRealm);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.success();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                listener.error(error);
            }
        });
    }

    /**
     * 更新点名规则
     * @param rollCallRuleId
     * @param status  0:表示关闭此次点名  1:表示开启此次点名
     * @param startdate  开始的日期
     * @param enddate    结束的日期
     * @param starttime  开始的时间
     * @param endtime    结束的时间
     * @param rollType
     * @param weekList
     * @param listener
     */
    public void updateCallRealm(int rollCallRuleId, int status, String startdate, String enddate,
                                String starttime, String endtime, int rollType, String weekList, IAddRealmListener listener){

            Realm realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                        CallRealm callRealm = realm.where(CallRealm.class).equalTo("rollCallRuleId",rollCallRuleId).findFirst();
                        if(callRealm!=null)
                        {
                            callRealm.setStatus(status);
                            callRealm.setStartdate(startdate);
                            callRealm.setEnddate(enddate);
                            callRealm.setStarttime(starttime);
                            callRealm.setEndtime(endtime);
                            callRealm.setRollType(rollType);
                            callRealm.setWeekList(weekList);
                            callRealm.setRollCallId(status);
                        }

                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    listener.success();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    listener.error(error);
                }
            });
    }


    /**
     * 改变点名规则的状态开关
     */
    public void changeStatusCallRealm(int rollCallRuleId, int status,IAddRealmListener listener){

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CallRealm callRealm = realm.where(CallRealm.class).equalTo("rollCallRuleId",rollCallRuleId).findFirst();
                if(callRealm!=null)
                {
                    callRealm.setStatus(status);
                    callRealm.setIsruning(0);
                }

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.success();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                listener.error(error);
            }
        });
    }

    /**
     * 删除点名规则
     * @param rollCallRuleId
     * @param flage
     * @param listener
     */
 public void  deleteCallRealm(int rollCallRuleId, int flage , IAddRealmListener listener )
 {
      Realm realm = Realm.getDefaultInstance();
      realm.executeTransactionAsync(new Realm.Transaction() {
         @Override
         public void execute(Realm realm) {
             if(flage ==0) //表示删除某一个
             {
                 CallRealm callRealm  =realm.where(CallRealm.class).equalTo("rollCallRuleId",rollCallRuleId).findFirst();
                 if(callRealm!=null)
                 {
                     callRealm.deleteFromRealm();
                 }

             }else {    //表示删除全部
                 RealmResults<CallRealm> callRealm  =realm.where(CallRealm.class).equalTo("rollCallRuleId",rollCallRuleId).findAll();
                 if(callRealm!=null)
                 {
                     callRealm.deleteAllFromRealm();
                 }
             }
         }
     }, new Realm.Transaction.OnSuccess() {
         @Override
         public void onSuccess() {
            listener.success();
         }
     }, new Realm.Transaction.OnError() {
         @Override
         public void onError(Throwable error) {
            listener.error(error);
         }
     });
 }

    /**
     * 获取点名规则里面的statu
     */
    public void  selectStatus(IRealmSelectListener listener)
    {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    //查找该点名规则是否启用
                    RealmResults<CallRealm> callRealms = realm.where(CallRealm.class).equalTo("status", 1).findAll();
                    if (callRealms != null) {
                        listener.Success(callRealms);
                    } else {
                        listener.Error("无数据!");
                    }
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {

                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    listener.Error(error.getMessage());
                }
            });
    }
    /**
     * 修改是否正在点名的状态
     */
    public void updateIsRunning(int id,int isruning,int callid)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CallRealm callRealm = realm.where(CallRealm.class).equalTo("rollCallRuleId",id).findFirst();
                if(callRealm!=null)
                {
                    Log.d("HXS","修改了运行状态"+isruning);
                    callRealm.setIsruning(isruning);
                    callRealm.setRollCallId(callid);
                }

            }
        });
    }

}
