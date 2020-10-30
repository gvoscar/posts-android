package com.gvoscar.apps.postsapp.features.posts.ui;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.gvoscar.apps.postsapp.App;
import com.gvoscar.apps.postsapp.R;
import com.gvoscar.apps.postsapp.features.details.ui.PostDetailsActivity;
import com.gvoscar.apps.postsapp.features.posts.adapters.PostsAdapter;
import com.gvoscar.apps.postsapp.features.posts.adapters.PostsAdapterListener;
import com.gvoscar.apps.postsapp.features.posts.presenters.PostsPresenter;
import com.gvoscar.apps.postsapp.features.posts.presenters.PostsPresenterImpl;
import com.gvoscar.apps.postsapp.pojos.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class PostsFragment extends Fragment implements PostsAdapterListener, PostsView {
    private static final String TAG = PostsFragment.class.getSimpleName();
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
    @BindView(R.id.framgePosts)
    FrameLayout framgePosts;

    private App app;
    private PostsAdapter mAdapter;
    private PostsPresenter mPresenter;
    public static PostsFragment instance;
    // private CarShopApp mSession;

    public PostsFragment() {

        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        this.mPresenter.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        //this.mPresenter.getData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView()");

        app = (App) getActivity().getApplication();
        instance = this;
        this.mPresenter = new PostsPresenterImpl(getActivity(), getContext(), this);
        this.mPresenter.onCreate();


        this.mAdapter = new PostsAdapter(this);
        // this.mSession = (CarShopApp) ((getActivity() != null) ? getActivity().getApplication() : null);

        shimmer.startShimmer();

        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this.recyclerView);
        this.recyclerView.setAdapter(this.mAdapter);

        this.mPresenter.getData();

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
    public void onPostClickListener(Post post) {
        Log.d(TAG, "POST ID: " + post.getId());
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        app.setPost(post);

        Intent intent = new Intent(getActivity(), PostDetailsActivity.class);
        intent.putExtra("post", bundle);
        getActivity().startActivity(intent);
    }

    @Override
    public void onNotFound(String message) {
        mAdapter.clear();
        shimmer.stopShimmer();
        containerLoader.setVisibility(View.GONE);
        containerInfo.setVisibility(View.VISIBLE);
        txtNotFound.setVisibility(View.VISIBLE);
        //showMessage(message);
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

    public void showMessage(String message) {
        Snackbar snackbar = Snackbar.make(framgePosts, message, 5600);
        View sbView = snackbar.getView();
        //sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBarRed));
        snackbar.show();
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    Post post = mAdapter.getPost(position);
                    mAdapter.remove(position);
                    mPresenter.removeById(String.valueOf(post.getId()));
                    Snackbar.make(framgePosts, "Se borro post #" + post.getId(), Snackbar.LENGTH_LONG)
                            .setAction("Deshacer", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mAdapter.add(position, post);
                                }
                            });
                    break;
                case ItemTouchHelper.RIGHT:
                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBarRed))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public static void romoveAll() {
        try {
            instance.mAdapter.clear();
            instance.mPresenter.removeAll();
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
    }
}