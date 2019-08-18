package net.taptappun.taku.kobayashi.runtimepermissionchecker;

import android.app.Activity;
import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
