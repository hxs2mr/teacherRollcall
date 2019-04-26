package com.gykj.rollcall.utils;

import com.gykj.mvvmlibrary.utils.KLog;
import com.gykj.rollcall.manager.CityRealmManager;
import com.gykj.rollcall.model.iview.IRealmListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * desc   : XML 解析工具类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/414:18
 * version: 1.0
 */
public class ParseXmlUtils {

    public static void parseXml(InputStream inputStream) throws Exception {
        // 得到工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 得到builder
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 打开文件的目录
        Document document = builder.parse(inputStream);
        // 得到目录的元素
        Element element = document.getDocumentElement();
        // 得到子节点
        NodeList list = element.getElementsByTagName("d");
        for(int i = 0;i< list.getLength();i++){
            final Element e = (Element) list.item(i);
            CityRealmManager.getManager().addCityRealm(e.getAttribute("d1"), e.getAttribute("d2"),
                    e.getAttribute("d3"), e.getAttribute("d4"), new IRealmListener() {
                        @Override
                        public void OnSuccess() {
                            KLog.d("lanzhu","添加城市成功");
                        }

                        @Override
                        public void onError(Throwable error) {
                            KLog.d("lanzhu","添加城市失败"+error.getMessage());
                        }
                    });
        }

    }
}
