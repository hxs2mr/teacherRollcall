package com.gykj.rollcall.utils;

import com.gykj.mvvmlibrary.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * desc   : Fragment工厂类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2712:14
 * version: 1.0
 */
public class FragmentFactory {

    private static Map<String, BaseFragment> fragmentList = new HashMap<>();

    /**
     * 根据Class创建Fragment
     *
     * @param clazz the Fragment of create
     * @return
     */
    public static BaseFragment createFragment(Class<?> clazz, boolean isObtain) {
        BaseFragment resultFragment = null;
        String className = clazz.getName();
        if (fragmentList.containsKey(className)) {
            resultFragment = fragmentList.get(className);
        } else {
            try {
                try {
                    resultFragment = (BaseFragment) Class.forName(className).newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (isObtain)
                fragmentList.put(className, resultFragment);
        }

        return resultFragment;
    }

    public static BaseFragment createFragment(Class<?> clazz) {
        return createFragment(clazz, true);
    }

    public static List<BaseFragment> getFragments() {
        Iterator<BaseFragment> ita = fragmentList.values().iterator();
        List<BaseFragment> list = new ArrayList<>();
        while (ita.hasNext()) {
            list.add(ita.next());
        }
        return list;
    }


}
