package com.gvoscar.apps.postsapp.features.posts.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gvoscar.apps.postsapp.R;
import com.gvoscar.apps.postsapp.features.posts.adapters.PostsAdapter;
import com.gvoscar.apps.postsapp.features.posts.adapters.PostsAdapterListener;
import com.gvoscar.apps.postsapp.pojos.Post;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PostsFragment extends Fragment implements PostsAdapterListener {
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
    @BindView(R.id.framgeVehicles)
    FrameLayout framgeVehicles;

    private PostsAdapter mAdapter;
    // private PostsPresenter mPresenter;
    // private CarShopApp mSession;

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        // this.mPresenter.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        // this.mPresenter.getData();
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

        // this.mPresenter = new VehiclesPresenterImpl(this);
        // this.mPresenter.onCreate();

        this.mAdapter = new PostsAdapter(this);
        // this.mSession = (CarShopApp) ((getActivity() != null) ? getActivity().getApplication() : null);

        shimmer.startShimmer();

        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.mAdapter);

        return view;
    }

    @Override
    public void onPostClickListener(Post post) {

    }
}