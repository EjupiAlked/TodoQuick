package com.ejupialked.todoapp.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ejupialked.todoapp.R;

import butterknife.ButterKnife;

/**
 * <p>
 * BaseActivity contains some modifications to the native AppCompatActivity.
 * Mainly, it use ButterKnife for view binding and it automatically check if
 * toolbar exists.
 * </p>
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setupToolbar();
        bindViews();
        initView();
    }

    /**
     * Use this method to initialize view components. This method is called after {@link
     * BaseActivity#bindViews()}
     */
    public void initView() {
    }


    /**
     * Its common use a toolbar within activity, if it exists in the
     * layout this will be configured
     */
    public void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }


    public void initBackButtonToolbar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public Toolbar getmToolbar() {
        return mToolbar;
    }

    /**
     * Every object annotated with {@link butterknife} its gonna injected trough butterknife
     */
    private void bindViews() {
        ButterKnife.bind(this);
    }

    /**
     * @return The layout id that's gonna be the activity view.
     */
    protected abstract int getLayoutId();

}
