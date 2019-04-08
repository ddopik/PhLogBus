package com.example.ddopik.phlogbusiness.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class PagingController {

    private RecyclerView recyclerView;
    private boolean isScroll;

    /**
     * Set scrolling threshold here (for now i'm assuming 10 item in one page)
     */
    private int PAGE_SIZE = 10;


    public PagingController(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        initListeners();
    }

    public PagingController(RecyclerView recyclerView, int pageThreshold) {
        this.recyclerView = recyclerView;
        this.PAGE_SIZE = pageThreshold;
        initListeners();
    }

    private void initListeners() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (dy > 0) {
                    // Scrolling up
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                    if (!isLoading() && !isLastPage()) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                            loadMoreItems();
                        }
                    }

                } else {

                    // Scrolling down
                }
            }
        });
    }


    protected abstract void loadMoreItems();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();


}
