package net.taptappun.taku.kobayashi.runtimepermissionchecker;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class RuntimePermissionCheckerTest{
    @Rule
    public ActivityTestRule<Activity> activityTestRule = new ActivityTestRule<>(Activity.class, false, false);

    @Test
    public void getSettingPermissionsTest() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Assert.assertEquals(RuntimePermissionChecker.getSettingPermissions(appContext), 1);
   }
}
