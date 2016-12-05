package com.leanplum.recyclerviewadaptertest;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

class OriginalRecyclerViewAdapter extends RecyclerView.Adapter {
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Log.d("LP", "OriginalAdapter.onCreateViewHolder called.");
    return null;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Log.d("LP", "OriginalAdapter.onBindViewHolder called.");
  }

  @Override
  public int getItemCount() {
    Log.d("LP", "OriginalAdapter.getItemCount called.");
    return 0;
  }
}
