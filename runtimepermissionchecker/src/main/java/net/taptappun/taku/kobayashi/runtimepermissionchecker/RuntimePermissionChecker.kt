package net.taptappun.taku.kobayashi.runtimepermissionchecker

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.os.Build
import java.util.ArrayList

object RuntimePermissionChecker {
    // Returns the settings permissions in the current application.
    fun getSettingPermissions(context: Context): ArrayList<PermissionInfo> {
        val list = ArrayList<PermissionInfo>()
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
            if (packageInfo!!.requestedPermissions != null) {
                for (permission in packageInfo.requestedPermissions) {
                    list.add(context.packageManager.getPermissionInfo(permission, PackageManager.GET_META_DATA))
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return list
    }

    // check the permission has already been accepted.
    fun hasSelfPermission(context: Context, permission: String): Boolean {
        return if (Build.VERSION.SDK_INT < 23) true else context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    // request to show all permissions dialog, one by one.
    fun requestAllPermissions(activity: Activity, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= 23) {
            val requestPermissionNames = ArrayList<String>()
            val permissions = RuntimePermissionChecker.getSettingPermissions(activity)
            for (i in permissions.indices) {
                val permission = permissions[i]
                if (RuntimePermissionChecker.isDangerousPermission(permission) && !RuntimePermissionChecker.hasSelfPermission(activity, permission.name)) {
                    requestPermissionNames.add(permission.name)
                }
            }
            if (!requestPermissionNames.isEmpty()) {
                activity.requestPermissions(requestPermissionNames.toTypedArray(), requestCode)
            }
        }
    }

    // request to show the permissions dialog, you need to.
    fun requestPermission(activity: Activity, requestCode: Int, permissionName: String) {
        if (Build.VERSION.SDK_INT >= 23) {
            var existPermission = false
            val permissions = RuntimePermissionChecker.getSettingPermissions(activity)
            for (i in permissions.indices) {
                val permission = permissions[i]
                if (RuntimePermissionChecker.isDangerousPermission(permission) && permission.name == permissionName) {
                    existPermission = true
                    break
                }
            }
            if (existPermission && !RuntimePermissionChecker.hasSelfPermission(activity, permissionName)) {
                activity.requestPermissions(arrayOf(permissionName), requestCode)
            }
        }
    }

    // check all permissions have been accepted.
    fun existConfirmPermissions(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            val permissions = RuntimePermissionChecker.getSettingPermissions(activity)
            var isRequestPermission = false
            for (permission in permissions) {
                if (RuntimePermissionChecker.isDangerousPermission(permission) && !RuntimePermissionChecker.hasSelfPermission(activity, permission.name)) {
                    isRequestPermission = true
                    break
                }
            }
            return isRequestPermission
        }
        return true
    }

    private fun isDangerousPermission(permissionInfo: PermissionInfo): Boolean {
        return permissionInfo.protectionLevel and 0x01 == PermissionInfo.PROTECTION_DANGEROUS
    }
}