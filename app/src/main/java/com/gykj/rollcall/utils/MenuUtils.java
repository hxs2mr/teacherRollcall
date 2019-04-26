package com.gykj.rollcall.utils;

import android.content.Context;

import com.gykj.rollcall.R;
import com.gykj.rollcall.entity.MainMenuInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * desc   : 主界面菜单栏工具类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2610:13
 * version: 1.0
 */
public class MenuUtils {



    public static List<MainMenuInfo> getMenuList(Context context){
        List<MainMenuInfo> menuList = new ArrayList<>();

        MainMenuInfo notice  = new MainMenuInfo();
        notice.setId(1);
        notice.setMenu_icon(R.mipmap.icon_menu_notice);
        notice.setMenu_title(context.getString(R.string.titie_notice));
        menuList.add(notice);

        MainMenuInfo call = new MainMenuInfo();
        call.setId(2);
        call.setMenu_icon(R.mipmap.icon_menu_call);
        call.setMenu_title(context.getString(R.string.titie_call));
        menuList.add(call);


        MainMenuInfo borrow  = new MainMenuInfo();
        borrow.setId(3);
        borrow.setMenu_icon(R.mipmap.icon_menu_borrow);
        borrow.setMenu_title(context.getString(R.string.titie_borrow));
        menuList.add(borrow);


        MainMenuInfo loss  = new MainMenuInfo();
        loss.setId(4);
        loss.setMenu_icon(R.mipmap.icon_menu_loss);
        loss.setMenu_title(context.getString(R.string.titie_loss));
        menuList.add(loss);

        MainMenuInfo door  = new MainMenuInfo();
        door.setId(5);
        door.setMenu_icon(R.mipmap.icon_menu_door);
        door.setMenu_title(context.getString(R.string.titie_door));
        menuList.add(door);

        MainMenuInfo analyse  = new MainMenuInfo();
        analyse.setId(6);
        analyse.setMenu_icon(R.mipmap.icon_menu_analyse);
        analyse.setMenu_title(context.getString(R.string.titie_analyse));
        menuList.add(analyse);

        MainMenuInfo police  = new MainMenuInfo();
        police.setId(7);
        police.setMenu_icon(R.mipmap.icon_waring_jilu);
        police.setMenu_title("报警记录");
        menuList.add(police);

        MainMenuInfo setting  = new MainMenuInfo();
        setting.setId(8);
        setting.setMenu_icon(R.mipmap.icon_menu_setting);
        setting.setMenu_title(context.getString(R.string.titie_setting));
        menuList.add(setting);

        return menuList;
    }
}
