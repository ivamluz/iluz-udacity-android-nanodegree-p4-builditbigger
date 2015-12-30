package com.udacity.gradle.builditbigger.asynctasks;

import android.os.ConditionVariable;
import android.test.AndroidTestCase;

import com.gmail.ivamsantos.jokestelling.backend.jokesApi.model.Joke;

/**
 * Created by iluz on 30/12/15.
 */
public class FetchJokeAsyncTaskTest extends AndroidTestCase implements FetchJokeAsyncTask.FetchJokeAsyncTaskListener {
    private FetchJokeAsyncTask mFetchJokeAsyncTask;

    // http://developer.android.com/reference/android/os/ConditionVariable.html
    // http://stackoverflow.com/a/25543252
    private ConditionVariable mBlocker;

    @Override
    public void setUp() {
        mFetchJokeAsyncTask = new FetchJokeAsyncTask(this);
        mBlocker = new ConditionVariable();
    }

    public void testFetchRandomJokeAsyncly() {
        mFetchJokeAsyncTask.execute();
        mBlocker.block();
    }

    @Override
    public void onSuccess(Joke joke) {
        assertFalse(joke.getText().isEmpty());
        mBlocker.open();
    }

    @Override
    public void onError() {
        fail("Failed to fetch joke asyncly.");
        mBlocker.open();
    }
}
