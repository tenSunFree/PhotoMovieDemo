package com.home.photomoviedemo.widget;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.photomoviedemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangwei on 2018/9/9.
 */
public class MovieFilterView extends FrameLayout {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<FilterItem> mItemList = new ArrayList<FilterItem>();
    private FilterCallback mCallback;
    private int mCheckIndex =0;

    public MovieFilterView(@NonNull Context context) {
        super(context);
    }

    public MovieFilterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MovieFilterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setItemList(List<FilterItem> itemList) {
        if(itemList==null){
            return;
        }
        mItemList.clear();
        this.mItemList.addAll(itemList);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setFilterCallback(FilterCallback callback) {
        mCallback = callback;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_filter_item, parent, false);
                return new RecyclerView.ViewHolder(view) {

                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                ImageView img = holder.itemView.findViewById(R.id.filter_img);
                TextView txt = holder.itemView.findViewById(R.id.filter_txt);
                ImageView checkImg = holder.itemView.findViewById(R.id.filter_check);

                final FilterItem item = mItemList.get(position);
                img.setImageResource(item.imgRes);
                txt.setText(item.name);
                checkImg.setVisibility(mCheckIndex==position? View.VISIBLE: View.GONE);
                holder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCheckIndex = position;
                        if (mCallback != null) {
                            mCallback.onFilterSelect(item);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return mItemList.size();
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.demo_menu_item_space)));
    }

    public void show() {
        int height = getResources().getDimensionPixelSize(R.dimen.demo_menu_height);
        setTranslationY(height);
        setAlpha(0);
        setVisibility(View.VISIBLE);
        animate().setDuration(400).alpha(1f).translationY(0).setListener(null).start();

    }

    public void hide() {
        animate().setDuration(400).alpha(0f).translationY(getHeight()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();

    }

    public static interface FilterCallback {
        void onFilterSelect(FilterItem item);
    }
}
