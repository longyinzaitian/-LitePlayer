package org.loader.liteplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.loader.liteplayer.R;
import org.loader.liteplayer.fragment.SongListFragment;

/**
 * @author longyinzaitian
 * @date 2018/1/7.
 */
public class SongListActivity extends BaseActivity{
    @Override
    protected int getLayoutId() {
        return R.layout.activity_song_list;
    }

    @Override
    protected void bindView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void loadData() {
        Intent intent = getIntent();
        if (intent == null){
            return;
        }
        int type = intent.getIntExtra("id", 0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment songListFragment = new SongListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", type);
        songListFragment.setArguments(bundle);

        fragmentManager.beginTransaction().add(R.id.activity_song_list_container,
                songListFragment).commitAllowingStateLoss();
        findViewById(R.id.activity_song_list_container);
    }

    @Override
    protected void clearData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
