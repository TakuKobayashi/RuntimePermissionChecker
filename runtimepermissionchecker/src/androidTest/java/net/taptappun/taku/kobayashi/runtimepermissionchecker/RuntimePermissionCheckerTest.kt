package net.taptappun.taku.kobayashi.runtimepermissionchecker

import android.app.Activity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RuntimePermissionCheckerTest {
    @Rule
    var activityTestRule = ActivityTestRule(Activity::class.java, false, false)

    @Test
    fun getSettingPermissionsTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().context
        Assert.assertEquals(RuntimePermissionChecker.getSettingPermissions(appContext), 1)
    }
}
