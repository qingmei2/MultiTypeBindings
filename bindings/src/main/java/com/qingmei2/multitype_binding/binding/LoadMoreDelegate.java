package com.qingmei2.multitype_binding.binding;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class LoadMoreDelegate {

    private final OnLoadMore loadMoreSubject;

    public LoadMoreDelegate(OnLoadMore loadMoreSubject) {
        this.loadMoreSubject = loadMoreSubject;
    }


    /**
     * Should be called after recyclerView setup with its layoutManager and adapter
     *
     * @param recyclerView the RecyclerView
     */
    public void attach(RecyclerView recyclerView) {
        final LinearLayoutManager layoutManager
                = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(
                new EndlessScrollListener(layoutManager, loadMoreSubject));
    }


    private static class EndlessScrollListener extends RecyclerView.OnScrollListener {

        private static final int VISIBLE_THRESHOLD = 6;
        private final LinearLayoutManager layoutManager;
        private final OnLoadMore loadMoreSubject;

        private EndlessScrollListener(LinearLayoutManager layoutManager, OnLoadMore loadMoreSubject) {
            this.layoutManager = layoutManager;
            this.loadMoreSubject = loadMoreSubject;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy < 0) return;

            final int itemCount = layoutManager.getItemCount();
            final int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();
            final boolean isBottom = (lastVisiblePosition >= itemCount - VISIBLE_THRESHOLD);
            if (isBottom) {
                loadMoreSubject.onLoadMore();
            }
        }
    }

}
