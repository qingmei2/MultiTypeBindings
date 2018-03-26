package com.qingmei2.multitype_binding.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.qingmei2.multitype_binding.adapter.binder.DataBindingItemViewBinder;
import com.qingmei2.multitype_binding.function.Function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

public class RecyclerViewBindingAdapter {

    public static class BindableVariables extends BaseObservable {
        @Bindable
        public Object data;
    }

    @BindingAdapter({"itemLayout", "onBindItem"})
    public static void setAdapter(RecyclerView view, int resId, DataBindingItemViewBinder.OnBindItem onBindItem) {
        final MultiTypeAdapter adapter = getOrCreateAdapter(view);
        adapter.register(Object.class, new DataBindingItemViewBinder(resId, onBindItem));
    }

    private static MultiTypeAdapter getOrCreateAdapter(RecyclerView view) {
        if (view.getAdapter() instanceof MultiTypeAdapter) {
            return (MultiTypeAdapter) view.getAdapter();
        } else {
            final MultiTypeAdapter adapter = new MultiTypeAdapter();
            view.setAdapter(adapter);
            return adapter;
        }
    }

    @BindingAdapter({"linkers", "onBindItem"})
    public static void setAdapter(RecyclerView view, List<Linker> linkers, DataBindingItemViewBinder.OnBindItem onBindItem) {
        final MultiTypeAdapter adapter = getOrCreateAdapter(view);
        final ArrayList<ItemViewBinder> binders = new ArrayList<>();

        for (Linker linker : linkers) {
            binders.add(new DataBindingItemViewBinder(linker.getLayoutId(), onBindItem));
        }
        int size = binders.size();
        ItemViewBinder[] array = binders.toArray(new ItemViewBinder[size]);

        adapter.register(Object.class)
                .to(array)
                .withLinker(o -> {
                    for (int i = 0; i < linkers.size(); i++) {
                        Function<Object, Boolean> matcher = linkers.get(i).getMatcher();
                        if (matcher.apply(o)) {
                            return i;
                        }
                    }
                    return 0;
                });
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView view, List items) {
        final MultiTypeAdapter adapter = getOrCreateAdapter(view);
        adapter.setItems(items == null ? Collections.emptyList() : items);
        adapter.notifyDataSetChanged();
    }
}
