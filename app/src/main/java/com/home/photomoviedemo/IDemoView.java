package com.home.photomoviedemo;

import android.app.Activity;

import com.hw.photomovie.render.GLTextureView;
import com.home.photomoviedemo.widget.FilterItem;
import com.home.photomoviedemo.widget.TransferItem;

import java.util.List;

/**
 * Created by huangwei on 2018/9/9.
 */
public interface IDemoView {
    public GLTextureView getGLView();
    void setFilters(List<FilterItem> filters);
    Activity getActivity();

    void setTransfers(List<TransferItem> items);
}
