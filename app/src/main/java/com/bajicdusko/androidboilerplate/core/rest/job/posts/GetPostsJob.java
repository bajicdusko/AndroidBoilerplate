package com.bajicdusko.androidboilerplate.core.rest.job.posts;

import com.bajicdusko.androidboilerplate.core.rest.ApiConstants;
import com.bajicdusko.androidboilerplate.core.rest.exception.ApiException;
import com.bajicdusko.androidboilerplate.core.rest.job.BaseJob;
import com.bajicdusko.androidboilerplate.core.rest.job.posts.event.OnGetPostsEvent;
import com.bajicdusko.androidboilerplate.core.rest.model.posts.PostModel;
import com.bajicdusko.androidboilerplate.core.rest.services.base.APICallback;
import com.bajicdusko.androidboilerplate.core.rest.services.post.PostsService;
import com.squareup.otto.Bus;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Bajic Dusko (www.bajicdusko.com) on 15-Aug-16.
 */

public class GetPostsJob extends BaseJob {

    @Inject
    PostsService postsService;
    @Inject
    Bus bus;

    private final int page;
    private final int perPage;
    private final long categoryId;

    public GetPostsJob(long categoryId, int page, int perPage) {
        super();
        this.page = page;
        this.categoryId = categoryId;
        this.perPage = perPage;
    }

    @Override
    public void onAdded() {
        bus.post(OnGetPostsEvent.eventInProgress());
    }

    @Override
    public void onRun() throws Throwable {
        int tempPage = page < ApiConstants.DEFAULT_PAGE ? ApiConstants.DEFAULT_PAGE : page;
        postsService.getPosts(categoryId, tempPage, perPage, new APICallback<ArrayList<PostModel>>() {
            @Override
            public void onSuccess(ArrayList<PostModel> response) {
                bus.post(OnGetPostsEvent.finished(response, page, categoryId));
            }

            @Override
            public void onFailure(ApiException ex) {
                bus.post(OnGetPostsEvent.error(ex));
            }
        });
    }

    @Override
    protected void onCancel() {
        bus.post(OnGetPostsEvent.cancel());
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}