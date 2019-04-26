package com.gykj.rollcall.mq;


import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.gykj.mvvmlibrary.bus.RxBus;
import com.gykj.mvvmlibrary.entity.MQRespose;
import com.gykj.mvvmlibrary.utils.Contract;
import com.gykj.mvvmlibrary.utils.ThreadManager;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.app.RollCallApplication;
import com.gykj.rollcall.model.WarringBean;
import com.gykj.rollcall.widget.PoliceDialog;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


import java.io.IOException;

/**
 * desc   : RabbiMq引擎类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/9/49:57
 * version: 1.0
 */
public class RabbiMqEngine {

    private ConnectionFactory factory;

    private Handler handler = new Handler();
    private Connection connection;

    byte[] group1_byte = new byte[20];
    byte[] veinsId = new byte[250];
    private  PoliceDialog policeDialog;
    private RabbiMqEngine(){
        // 声明ConnectionFactory对象
        factory = new ConnectionFactory();
    }

    private static class RabbiMqEngineHolder{
        private static RabbiMqEngine instance = new RabbiMqEngine();
    }

    public static RabbiMqEngine getRabbiMqEngine(){
        return RabbiMqEngineHolder.instance;
    }

    public void setUpConnectionFactory(){
        factory.setHost(Contract.MQ_HOST);//主机地址：
        factory.setPort(Contract.MQ_PORT);// 端口号
        factory.setUsername(Contract.MQ_USERNAME);// 用户名
        factory.setPassword(Contract.MQ_PASSWORD);// 密码
        factory.setAutomaticRecoveryEnabled(true);// 设置连接恢复
    }
    public void sendMessage(final boolean face_init, final int flage, final int pagesize, final int pageindex){
        ThreadManager.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                Connection connection = null;
                try {
                        //创建连接
                        connection = factory.newConnection();

                        //创建通道
                        Channel channel = connection.createChannel();

                        //2.指定一个队列
                        channel.queueDeclare(Contract.QUEUE_NAME+"123456", true, false, false, null);// 声明共享队列
                        //人脸
                        if(face_init){
                            MQRespose entity = new MQRespose();

                            entity.setDevieceType(2);
                            entity.setDeviceId("123456");
                            switch (flage)
                            {
                                case 0:
                                    entity.setMethod("SYN_INIT");
                                    entity.setEvent("DORMITORY_EQUIPMENT");
                                    break;
                            }


                         //System.out.println("HXS传的数据:"+JSON.toJSONString(entity));
                            //3.往队列中发出一条消息
                            channel.basicPublish("cashierTopicExchange", "topic.dormitory.server", null, JSON.toJSONString(entity).getBytes());
                        }
                       //4.关闭频道和连接
                        channel.close();
                        connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 销毁连接
     */
    public void destoryConnect(){
        if(null != connection){
            try {
                connection.abort();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                connection = null;
            }
        }
    }

    public void connect(final String deviceId, Context context){
        ThreadManager.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    connection = factory.newConnection();

                    //创建通道
                     final Channel channel = connection.createChannel();

                    //命名一个队列
                    String queueName = Contract.QUEUE_NAME+deviceId;

                    // 声明队列（持久的、非独占的、连接断开后队列不会自动删除）
                    AMQP.Queue.DeclareOk q = channel.queueDeclare(queueName, true, false, false, null);// 声明共享队列

                    // 根据路由键将队列绑定到交换机上（需要知道交换机名称和路由键名称）
                    channel.queueBind(q.getQueue(), Contract.MQ_EXCHANGE_CAR, Contract.QUEUE_NAME+deviceId);
                    // 创建消费者获取rabbitMQ上的消息。每当获取到一条消息后，就会回调handleDelivery（）方法，
                    // 该方法可以获取到消息数据并进行相应处理
                    Consumer consumer = new DefaultConsumer(channel){
                        @Override
                        public void handleDelivery(String consumerTag, final Envelope envelope, AMQP.BasicProperties properties, final byte[] body) throws IOException {
                            super.handleDelivery(consumerTag, envelope, properties, body);
                            // 通过geiBody方法获取消息中的数据
                            // 发消息通知UI更新
                          //  Logger.e("HXS","MQ链接成功!");
                            //
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                   System.out.println("HXS 报警:"+new String(body));

                                }
                            });
                             MQRespose entity = JSON.parseObject(body,MQRespose.class);  //获取到的消息进行转换

                            String method = "";
                            if(null != entity){
                                method = entity.getEvent()+entity.getMethod();
                            }
                            //添加  跟新  删除
                            switch (method){
                                case "DORMITORY_WARNINGINSERT":
                                    WarringBean warringBean = JSON.parseObject(JSON.toJSONString(entity.getData()), WarringBean.class);
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(policeDialog==null)
                                            {
                                                policeDialog = new PoliceDialog(context);
                                            }
                                            if(policeDialog.isShowing())
                                            {
                                                policeDialog.initdata(warringBean.getDormitoryName(),warringBean.getGmtCreate());
                                            } else {
                                                if(!policeDialog.isShowing())
                                                {
                                                    policeDialog.show();
                                                }
                                                policeDialog.initdata(warringBean.getDormitoryName(),warringBean.getGmtCreate());
                                            }
                                              // callBack.success(PGINIT);
                                           // ToastUtils.showShort("来了!");
                                          //  RxBus.getDefault().post(warringBean);
                                        }
                                    });
                                    break;
                            }
                                channel.basicAck(envelope.getDeliveryTag(),false);
                        }
                    };
                    channel.basicConsume(q.getQueue(), false, consumer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

/*

    private void insertFace(final FeatureEntity entity, final Channel channel, final Envelope envelope){


    }

    private void updateFace(final FeatureEntity entity, final Channel channel, final Envelope envelope){



    }
    private void deleteFace(final FeatureEntity entity, final Channel channel, final Envelope envelope){


    }
    private void insertFv(FvEntity entity, final Channel channel, final Envelope envelope){

    }
    private void updateFv(final FvEntity entity, final Channel channel, final Envelope envelope){


    }
    private void deleteFv(FvEntity entity, final Channel channel, final Envelope envelope){

    }
*/


}
