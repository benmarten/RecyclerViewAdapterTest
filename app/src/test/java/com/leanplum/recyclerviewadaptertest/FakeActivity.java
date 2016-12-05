package com.leanplum.recyclerviewadaptertest;

import android.app.Activity;
import android.content.Context;

import java.io.File;

class FakeActivity extends Activity {
  Context context;
  boolean isFinishing = false;

  public FakeActivity() {
  }

  public FakeActivity(Context context) {
    this.context = context;
  }

  @Override
  public boolean isFinishing() {
    return isFinishing;
  }

  public void setFinishing(boolean finishing) {
    isFinishing = finishing;
  }

  public File getDir(String name, int mode) {
    File dir = new File(context.getFilesDir().getPath() + '/' + name);
    if (dir.mkdir()) {
      return dir;
    } else {
      return null;
    }
  }
}
