package net.taptappun.taku.kobayashi.runtimepermissionchecker;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Build;

import java.util.ArrayList;

public class RuntimePermissionChecker {
    public static ArrayList<PermissionInfo> getSettingPermissions(Context context){
        ArrayList<PermissionInfo> list = new ArrayList<PermissionInfo>();
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            if(packageInfo.requestedPermissions != null){
                for(String permission : packageInfo.requestedPermissions){
                    list.add(context.getPackageManager().getPermissionInfo(permission, PackageManager.GET_META_DATA));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean hasSelfPermission(Context context, String permission) {
        if(Build.VERSION.SDK_INT < 23) return true;
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(Activity activity, int requestCode){
        if(Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> requestPermissionNames = new ArrayList<String>();
            ArrayList<PermissionInfo> permissions = RuntimePermissionChecker.getSettingPermissions(activity);
            for(PermissionInfo permission : permissions){
                if(permission.protectionLevel == PermissionInfo.PROTECTION_DANGEROUS && !RuntimePermissionChecker.hasSelfPermission(activity, permission.name)){
                    requestPermissionNames.add(permission.name);
                }
            }
            if(!requestPermissionNames.isEmpty()) {
                activity.requestPermissions(requestPermissionNames.toArray(new String[0]), requestCode);
            }
        }
    }

    public static boolean existConfirmPermissions(Activity activity){
        if(Build.VERSION.SDK_INT >= 23) {
            ArrayList<PermissionInfo> permissions = RuntimePermissionChecker.getSettingPermissions(activity);
            boolean isRequestPermission = false;
            for(PermissionInfo permission : permissions){
                if(permission.protectionLevel == PermissionInfo.PROTECTION_DANGEROUS && !RuntimePermissionChecker.hasSelfPermission(activity, permission.name)){
                    isRequestPermission = true;
                    break;
                }
            }
            return isRequestPermission;
        }
        return true;
    }
}
