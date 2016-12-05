package com.testin.bugout;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.qamaster.android.util.DeviceShakenListener;
import com.testin.agent.Bugout;
import com.testin.agent.BugoutConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ListActivity extends Activity {

    private ListView mListview;

    private DeviceShakenListener mDeviceShakenListener;

    private String permission = Manifest.permission.READ_PHONE_STATE;

    private int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermission();
        }
        initBugout();
        mDeviceShakenListener = new DeviceShakenListener(this);
        mListview = (ListView) findViewById(R.id.listview);
        mListview.setAdapter(new BugoutAdapter(this));
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 2) {
                    invokePrivateMothod();
                } else if (position == 2) {
                    Integer.valueOf("test bugout");
                }
            }
        });


    }

    private void initBugout() {
        BugoutConfig config = new BugoutConfig.Builder(this)
                .withAppKey("0a666da5fad59068005aff122cee68ab")     // 您的应用的项目 Key,如果已经在 Manifest 中配置则此处可略
                .withAppChannel("test")     // 发布应用的渠道,如果已经在 Manifest 中配置则此处可略
//                    .withUserInfo(userinfo)    // 用户信息-崩溃分析根据用户记录崩溃信息
                .withDebugModel(true)    // 输出更多SDK的debug信息
                .withErrorActivity(true)    // 发生崩溃时采集Activity信息
                .withCollectNDKCrash(true) //  收集NDK崩溃信息
                .withOpenCrash(true)    // 收集崩溃信息开关
                .withOpenEx(true)     // 是否收集异常信息
                .withReportOnlyWifi(true)    // 仅在 WiFi 下上报崩溃信息
                .withReportOnBack(true)    // 当APP在后台运行时,是否采集信息
                .withQAMaster(true)    // 是否收集摇一摇反馈
                .withCloseOption(true)   // 是否在摇一摇菜单展示‘关闭摇一摇选项’
                .withLogCat(true)  // 是否系统操作信息
                .build();
        Bugout.init(config);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugout.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugout.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugout.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            //第一次被拒绝后，第二次访问时，向用户说明为什么需要此权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Toast.makeText(this, "开启电话管理功能，才可使用崩溃崩溃异常上报", Toast.LENGTH_SHORT).show();
            }
            //若权限没有开启，则请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{permission}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //请求权限成功,做相应的事情
               initBugout();
            } else {
                //请求失败则提醒用户
                Toast.makeText(this, "请求权限失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void invokePrivateMothod() {
        try {
            Class cls = mDeviceShakenListener.getClass();
            Method method = cls.getDeclaredMethod("onDeviceShaken");
            method.setAccessible(true);
            method.invoke(mDeviceShakenListener);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
