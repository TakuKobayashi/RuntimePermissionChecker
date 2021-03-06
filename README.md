# What is This
This library is the simplest to implement the Android RuntimePermission.

The Android RuntimePermission is that the application request the permissions to user when if the application is used the particular feature.

[For details](https://developer.android.com/training/permissions/requesting.html)

Android version 6.0 Marshmallow (API Level 23) and later should be shown the dialog to use the permissions.

# Download
Add the dependency in your android project's module.
```build.gradle
dependencies {
    implementation 'net.taptappun.taku.kobayashi:runtimepermissionchecker:1.1.1'
}
```

# Usage
For example, add the following line to the Activity.
```MainActivity.java
public class MainActivity extends Activity {
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        RuntimePermissionChecker.requestAllPermissions(this, REQUEST_CODE);
    }
}
```
This will automatically display the dialog if you add permissions that the user needs to display the dialog.

if you want to execute the feature when the all permissions have been permitted, you add the following.
```MainActivity.java
public class MainActivity extends Activity {
    private static final int REQUEST_CODE = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode != REQUEST_CODE)
            return;
        if(!RuntimePermissionChecker.existConfirmPermissions(this)){
            // write features you want to execute.
        }
    }
}
```

Also, when under Android version 6.0 or the user has already accepted permissions, you can do just the same way.

If you want to individually request to the permission, try like this.
```MainActivity.java
public class MainActivity extends Activity {
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        RuntimePermissionChecker.requestPermission(this, REQUEST_CODE, Manifest.permission.CAMERA);
    }
}
```
In this case it requests to camera permission.

# Others

I wrote a more detailed article, However it is in Japanese.

https://qiita.com/taptappun/items/7fae4317d751245b2089