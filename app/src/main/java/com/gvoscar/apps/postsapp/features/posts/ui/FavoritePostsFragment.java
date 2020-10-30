package com.gvoscar.apps.postsapp.features.posts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gvoscar.apps.postsapp.App;
import com.gvoscar.apps.postsapp.R;
import com.gvoscar.apps.postsapp.features.details.ui.PostDetailsActivity;
import com.gvoscar.apps.postsapp.features.posts.adapters.PostsAdapter;
import com.gvoscar.apps.postsapp.features.posts.adapters.PostsAdapterListener;
import com.gvoscar.apps.postsapp.features.posts.presenters.FavoritePostsPresenter;
import com.gvoscar.apps.postsapp.features.posts.presenters.FavoritePostsPresenterImpl;
import com.gvoscar.apps.postsapp.features.posts.presenters.PostsPresenterImpl;
import com.gvoscar.apps.postsapp.pojos.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritePostsFragment extends Fragment implements PostsAdapterListener, PostsView {
    private static final String TAG = FavoritePostsFragment.class.getSimpleName();
    @BindView(R.id.txtNotFound)
    TextView txtNotFound;
    @BindView(R.id.shimmer)
    ShimmerFrameLayout shimmer;
    @BindView(R.id.containerLoader)
    ConstraintLayout containerLoader;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.containerInfo)
    ConstraintLayout containerInfo;
    @BindView(R.id.frameFavoritePosts)
    FrameLayout frameFavoritePosts;

    private App app;
    private PostsAdapter mAdapter;
    private FavoritePostsPresenter mPresenter;
    // private CarShopApp mSession;

    public FavoritePostsFragment() {

        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_posts, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView()");

        app = (App) getActivity().getApplication();

        this.mPresenter = new FavoritePostsPresenterImpl(this);
        this.mPresenter.onCreate();


        this.mAdapter = new PostsAdapter(this);
        shimmer.startShimmer();

        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.mAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        this.mPresenter.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onNotFound(String message) {
        mAdapter.clear();
        shimmer.stopShimmer();
        containerLoader.setVisibility(View.GONE);
        containerInfo.setVisibility(View.VISIBLE);
        txtNotFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDataLoaded(List<Post> list) {
        Log.d(TAG, "Cantidad de datos:    " + list.size());
        mAdapter.clear();
        mAdapter.addAll(list);
        shimmer.stopShimmer();
        txtNotFound.setVisibility(View.GONE);

        containerLoader.setVisibility(View.GONE);
        containerInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostClickListener(Post post) {
        Log.d(TAG, "POST ID: " + post.getId());
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        app.setPost(post);

        Intent intent = new Intent(getActivity(), PostDetailsActivity.class);
        intent.putExtra("post", bundle);
        getActivity().startActivity(intent);
    }
}