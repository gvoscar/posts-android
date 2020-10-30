package com.gvoscar.apps.postsapp.features.posts.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.gvoscar.apps.postsapp.R;
import com.gvoscar.apps.postsapp.pojos.Post;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private static final String TAG = PostsAdapter.class.getSimpleName();
    private List<Post> lista;
    private PostsAdapterListener listener;

    public PostsAdapter(PostsAdapterListener listener) {
        this.lista = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = lista.get(position);
        if (!post.isReaded()) {
            holder.imgReaded.setVisibility(View.VISIBLE);
        } else {
            holder.imgReaded.setVisibility(View.GONE);
        }
        holder.txtTitle.setText(String.valueOf(post.getTitle()));
        if (!post.isFavorite()) {
            holder.imgFavorite.setVisibility(View.GONE);
        } else {
            holder.imgFavorite.setVisibility(View.VISIBLE);
        }
        holder.txtBody.setText(String.valueOf(post.getBody()));
        holder.txtId.setText(String.valueOf(post.getId()));
        holder.setOnCategoriesListener(post, listener);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void addAll(List<Post> list) {
        lista.addAll(list);
        notifyDataSetChanged();
    }

    public void add(int index, Post post) {
        lista.add(index, post);
        notifyDataSetChanged();
    }

    public void add(Post post) {
        lista.add(post);
        notifyDataSetChanged();
    }

    public void remove(Post post) {
        lista.remove(post);
        notifyDataSetChanged();
    }

    public void remove(int pos) {
        lista.remove(pos);
        notifyDataSetChanged();
    }

    public void clear() {
        lista.clear();
        notifyDataSetChanged();
    }

    public Post getPost(int position) {
        return lista.get(position);
    }

    public int getLastItemPostId() {
        return lista.lastIndexOf(lista.get(lista.size() - 1));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item)
        LinearLayout item;
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.imgFavorite)
        ImageView imgFavorite;
        @BindView(R.id.imgReaded)
        ImageView imgReaded;
        @BindView(R.id.txtBody)
        TextView txtBody;
        @BindView(R.id.txtId)
        TextView txtId;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnCategoriesListener(Post post, PostsAdapterListener listener) {
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPostClickListener(post);
                }
            });
        }
    }
}
