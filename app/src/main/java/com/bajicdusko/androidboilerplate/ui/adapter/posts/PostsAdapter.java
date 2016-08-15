package com.bajicdusko.androidboilerplate.ui.adapter.posts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bajicdusko.androidboilerplate.core.rest.model.users.PostModel;
import com.bajicdusko.androidboilerplate.ui.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Bajic Dusko (www.bajicdusko.com) on 15-Aug-16.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsViewHolder> {

    private ArrayList<PostModel> posts = new ArrayList<>();
    private BaseActivity baseActivity;

    public PostsAdapter(ArrayList<PostModel> posts, BaseActivity baseActivity) {
        this.posts = posts;
        this.baseActivity = baseActivity;
    }

    public void onRefresh() {
        this.posts.clear();
        notifyDataSetChanged();
    }

    public void onDataChanged(ArrayList<PostModel> postModels) {
        this.posts.addAll(postModels);
        notifyDataSetChanged();
    }

    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostsViewHolder(LayoutInflater.from(baseActivity).inflate(PostsViewHolder.LAYOUT, parent, false));
    }

    @Override
    public void onBindViewHolder(PostsViewHolder holder, int position) {
        holder.setPostModel(posts.get(position));
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
