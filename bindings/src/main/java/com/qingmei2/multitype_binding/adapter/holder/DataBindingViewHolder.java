package com.qingmei2.multitype_binding.adapter.holder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class DataBindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T dataBinding;

    public DataBindingViewHolder(T binding) {
        super(binding.getRoot());

        dataBinding = binding;
    }
}
