package net.taptappun.taku.kobayashi.runtimepermissionchecker;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Build;

import java.util.ArrayList;

public class RuntimePermissionChecker {
    // Returns the settings permissions in the current application.
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

    // check the permission has already been accepted.
    public static boolean hasSelfPermission(Context context, String permission) {
        if(Build.VERSION.SDK_INT < 23) return true;
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    // request to show all permissions dialog, one by one.
    public static void requestAllPermissions(Activity activity, int requestCode){
        if(Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> requestPermissionNames = new ArrayList<String>();
            ArrayList<PermissionInfo> permissions = RuntimePermissionChecker.getSettingPermissions(activity);
            for(int i = 0;i < permissions.size();++i){
                PermissionInfo permission = permissions.get(i);
                if(RuntimePermissionChecker.isDangerousPermission(permission) && !RuntimePermissionChecker.hasSelfPermission(activity, permission.name)){
                    requestPermissionNames.add(permission.name);
                }
            }
            if(!requestPermissionNames.isEmpty()) {
                activity.requestPermissions(requestPermissionNames.toArray(new String[0]), requestCode);
            }
        }
    }

    // request to show the permissions dialog, you need to.
    public static void requestPermission(Activity activity, int requestCode, String permissionName){
        if(Build.VERSION.SDK_INT >= 23) {
            boolean existPermission = false;
            ArrayList<PermissionInfo> permissions = RuntimePermissionChecker.getSettingPermissions(activity);
            for(int i = 0;i < permissions.size();++i){
                PermissionInfo permission = permissions.get(i);
                if(RuntimePermissionChecker.isDangerousPermission(permission) && permission.name == permissionName){
                    existPermission = true;
                    break;
                }
            }
            if(existPermission && RuntimePermissionChecker.hasSelfPermission(activity, permissionName)) {
                activity.requestPermissions(new String[]{permissionName}, requestCode);
            }
        }
    }

    // check all permissions have been accepted.
    public static boolean existConfirmPermissions(Activity activity){
        if(Build.VERSION.SDK_INT >= 23) {
            ArrayList<PermissionInfo> permissions = RuntimePermissionChecker.getSettingPermissions(activity);
            boolean isRequestPermission = false;
            for(PermissionInfo permission : permissions){
                if(RuntimePermissionChecker.isDangerousPermission(permission) && !RuntimePermissionChecker.hasSelfPermission(activity, permission.name)){
                    isRequestPermission = true;
                    break;
                }
            }
            return isRequestPermission;
        }
        return true;
    }

    private static boolean isDangerousPermission(PermissionInfo permissionInfo){
        return (permissionInfo.protectionLevel & 0x01) == PermissionInfo.PROTECTION_DANGEROUS;
    }
}
