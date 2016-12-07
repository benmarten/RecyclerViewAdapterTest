package com.leanplum.recyclerviewadaptertest;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RunWith(RobolectricTestRunner.class)
@Config(
    constants = BuildConfig.class,
    sdk = 21
)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "org.json.*", "org.powermock.*",
    "android.*"})
public class AdapterSwitcherTest extends TestCase {
  @Test
  public void testSwizzleRecyclerViewAdapter() throws PackageManager.NameNotFoundException {
    RecyclerView recyclerView = new RecyclerView(RuntimeEnvironment.application);
    OriginalRecyclerViewAdapter adapter = new OriginalRecyclerViewAdapter();
    recyclerView.setAdapter(adapter);
    AdapterSwitcher.swizzleRecyclerViewAdapter(
        new FakeActivity(RuntimeEnvironment.application), recyclerView);
  }

  @Test
  public void testClassLoadingStrategy() throws NoSuchMethodException, InvocationTargetException,
      IllegalAccessException {
    Method getClassLoadingStrategyMethod =
        AdapterSwitcher.class.getDeclaredMethod("getClassLoadingStrategy",
            Activity.class);
    getClassLoadingStrategyMethod.setAccessible(true);
    Object result = getClassLoadingStrategyMethod.invoke(AdapterSwitcher.class,
        new FakeActivity(RuntimeEnvironment.application));
    assertNotNull(result);
  }
}

