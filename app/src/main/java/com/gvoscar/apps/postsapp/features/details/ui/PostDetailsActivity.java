package com.gvoscar.apps.postsapp.features.details.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gvoscar.apps.postsapp.App;
import com.gvoscar.apps.postsapp.R;
import com.gvoscar.apps.postsapp.features.details.presenters.PostDetailsPresenter;
import com.gvoscar.apps.postsapp.features.details.presenters.PostDetailsPresenterImpl;
import com.gvoscar.apps.postsapp.pojos.Post;
import com.gvoscar.apps.postsapp.pojos.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetailsActivity extends AppCompatActivity implements PostDetailsView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgStar0)
    ImageView imgStar0;
    @BindView(R.id.imgStar1)
    ImageView imgStar1;
    @BindView(R.id.txtBody)
    TextView txtBody;
    @BindView(R.id.item)
    LinearLayout item;
    @BindView(R.id.imageUser)
    CircleImageView imageUser;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtUserName)
    TextView txtUserName;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.txtWebsite)
    TextView txtWebsite;
    @BindView(R.id.txtCompany)
    TextView txtCompany;
    @BindView(R.id.txtCatch)
    TextView txtCatch;
    @BindView(R.id.txtAddresLine)
    TextView txtAddresLine;
    @BindView(R.id.txtGeo)
    TextView txtGeo;
    @BindView(R.id.containerPostDetails)
    ConstraintLayout containerPostDetails;
    @BindView(R.id.txtId)
    TextView txtId;

    private Post post;
    private App app;
    private PostDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        ButterKnife.bind(this);

        presenter = new PostDetailsPresenterImpl(this);
        presenter.onCreate();

        app = (App) getApplication();
        // post = (Post) savedInstanceState.getSerializable("post");
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras().getBundle("post");
            if (bundle != null) {
                post = (Post) bundle.getSerializable("post");
            }
        }

        if (post == null) {
            post = app.getPost();
        }

        setToolbar();
        loadPost();


    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public void setToolbar() {
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.vc_back);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void loadPost() {
        if (post != null) {
            txtTitle.setText(post.getTitle());
            txtBody.setText(post.getBody());
            txtId.setText(String.valueOf(post.getId()));
            if (post.isFavorite()) {
                imgStar0.setVisibility(View.GONE);
                imgStar1.setVisibility(View.VISIBLE);
            } else {
                imgStar0.setVisibility(View.VISIBLE);
                imgStar1.setVisibility(View.GONE);
            }

            presenter.setRead(String.valueOf(post.getId()), true);
            presenter.getData(String.valueOf(post.getUserId()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.imgStar0)
    public void onClickImgStar0() {
        imgStar0.setVisibility(View.GONE);
        imgStar1.setVisibility(View.VISIBLE);
        presenter.setFavorite(String.valueOf(post.getId()), true);
    }

    @OnClick(R.id.imgStar1)
    public void onClickImgStar1() {
        imgStar0.setVisibility(View.VISIBLE);
        imgStar1.setVisibility(View.GONE);
        presenter.setFavorite(String.valueOf(post.getId()), false);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onSuccess(User user) {
        txtName.setText(user.getName());
        txtEmail.setText(user.getEmail());
        txtUserName.setText(user.getUsername());

        txtPhone.setText(user.getPhone());
        txtWebsite.setText(user.getWebsite());

        txtCompany.setText(user.getCompany().getName());
        txtCatch.setText(user.getCompany().getCatchPhrase());

        txtAddresLine.setText(user.getAddress().toString());
        txtGeo.setText(user.getAddress().getGeo().toString());
    }
}