package com.leanplum.recyclerviewadaptertest;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

class AdapterInterceptor extends RecyclerView.Adapter {
  private RecyclerView.Adapter originalAdapter;

  AdapterInterceptor(RecyclerView.Adapter originalAdapter) {
    this.originalAdapter = originalAdapter;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (this.originalAdapter != null) {
      return this.originalAdapter.onCreateViewHolder(parent, viewType);
    }
    return null;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Log.d("Leanplum", "Intercepted onBindViewHolder.");

    if (originalAdapter != null) {
      originalAdapter.onBindViewHolder(holder, position);
    }
  }

  @Override
  public int getItemCount() {
    if (this.originalAdapter != null) {
      return this.originalAdapter.getItemCount();
    }
    return 0;
  }
}
