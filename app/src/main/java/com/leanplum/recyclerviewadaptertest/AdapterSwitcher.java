package com.leanplum.recyclerviewadaptertest;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.android.AndroidClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;

import java.io.File;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class AdapterSwitcher {
  static void swizzleRecyclerViewAdapter(Activity activity, RecyclerView recyclerView) {
    RecyclerView.Adapter existingAdapter = recyclerView.getAdapter();
    if (existingAdapter == null ||
        (existingAdapter.getClass().toString().contains("$ByteBuddy$"))) {
      return;
    }

    ClassLoadingStrategy classLoadingStrategy = getClassLoadingStrategy(activity);
    if (classLoadingStrategy == null) {
      Log.e("Leanplum", "Can not create an AndroidClassLoadingStrategy.");
      return;
    }

    RecyclerView.Adapter proxyAdapter = null;
    AdapterInterceptor interceptor = new AdapterInterceptor(existingAdapter);
    Class<?> proxyAdapterClass = new ByteBuddy(ClassFileVersion.JAVA_V6)
        .subclass(existingAdapter.getClass())
        .method(named("onBindViewHolder"))
        .intercept(MethodDelegation.to(interceptor))
        .make()
        .load(activity.getClass().getClassLoader(), classLoadingStrategy)
        .getLoaded();
    try {
      proxyAdapter = (RecyclerView.Adapter) new UnsafeLoader().allocateIdenticalInstance(proxyAdapterClass, existingAdapter);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (proxyAdapter != null) {
      recyclerView.setAdapter(proxyAdapter);
    }
  }

  private static ClassLoadingStrategy getClassLoadingStrategy(Activity activity) {
    File file = activity.getDir("leanplumClassDir", Context.MODE_PRIVATE);
    return new AndroidClassLoadingStrategy(file);
  }
}
