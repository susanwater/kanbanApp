package com.fred.andon.web2;


import android.app.Application;
import android.util.Log;

import com.umeng.commonsdk.UMConfigure;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        //UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE, null);
        // 初始化SDK
        UMConfigure.init(this, "5cbdaf8a570df39586000f9e", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
    }


}
