# What is This
This library is the simplest to implement the Android RuntimePermission. 
The Android RuntimePermission is that the application request the permissions to user when if the application is used the particular feature. 
[The Android RuntimePermission](https://developer.android.com/training/permissions/requesting.html, "For details")
Android version 6.0 (marshmallow) and later should be shown the dialog to use the permissions.

# Download
Add it in your root build.gradle at the end of repositories
```build.gradle
allprojects {
  repositories {
  ...
  maven { url 'https://jitpack.io' }
  }
}
```
And add the dependency in your android project's module.
```build.gradle
dependencies {
  compile 'com.github.TakuKobayashi:RuntimePermissionChecker:v1.0.0'
}
```

# Usage
For example, add the following line to the Activity.
```
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RuntimePermissionChecker.requestAllPermissions(this, REQUEST_CODE);
    }
}
```
This will automatically display the dialog if you add permissions that the user needs to display the dialog.
