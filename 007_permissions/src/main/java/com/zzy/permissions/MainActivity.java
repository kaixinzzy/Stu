package com.zzy.permissions;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 权限申请
 * Build - Make Project 后才会自动生成辅助类
 * 辅助类位置 build/generated/ap_generated_sources/debug/out/com.zzy.permissions/MainActivityPermissionsDispatcher
 */
// *必须注解*
@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 请求权限
        MainActivityPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 请求权限结果分发处理
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    // 权限请求前被调用，用来给用户解释为什么要请求该权限
    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.BLUETOOTH})
    public void showRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("使用此功能需要CAMERA和BLUETOOTH权限，下一步将继续请求权限")
                .setPositiveButton("下一步", (dialog, which) -> {
                    //继续执行请求
                    request.proceed();
                }).setNegativeButton("取消", (dialog, which) -> {
            //取消执行请求
            request.cancel();
        }).show();
    }

    // 用户同意授权后，回调此方法 *必须注解*
    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.BLUETOOTH})
    public void needsPermission() {
        Log.d(TAG, "同意授权");
    }

    // 用户拒绝授权（未勾选不在询问），回调此方法
    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.BLUETOOTH})
    public void onPermissionDenied() {
        Log.d(TAG, "已拒绝一个或以上权限");
    }

    // 用户拒绝授权（并勾选不在询问），回调此方法
    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.BLUETOOTH})
    public void onNeverAskAgain() {
        Log.d(TAG, "已拒绝一个或以上权限，并不再询问");
    }



}