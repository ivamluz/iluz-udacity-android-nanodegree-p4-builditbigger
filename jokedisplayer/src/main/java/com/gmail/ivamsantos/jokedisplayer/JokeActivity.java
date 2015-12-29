package com.gmail.ivamsantos.jokedisplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class JokeActivity extends AppCompatActivity {
    private static final String EXTRA_JOKE = "com.gmail.ivamsantos.jokedisplayer.joke";

    public static Intent newIntent(Context packageContext, String joke) {
        Intent intent = new Intent(packageContext, JokeActivity.class);
        intent.putExtra(EXTRA_JOKE, joke);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        createFragment();
    }

    private void createFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            String joke = getIntent().getStringExtra(EXTRA_JOKE);
            fragment = JokeFragment.newInstance(joke);
            fm.beginTransaction().add(R.id.fragment_container, fragment, "tag").commit();
        }
    }
}
