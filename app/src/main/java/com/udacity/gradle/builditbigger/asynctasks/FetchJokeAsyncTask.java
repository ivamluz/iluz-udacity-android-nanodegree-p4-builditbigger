package com.udacity.gradle.builditbigger.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.gmail.ivamsantos.jokestelling.backend.jokesApi.JokesApi;
import com.gmail.ivamsantos.jokestelling.backend.jokesApi.model.Joke;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class FetchJokeAsyncTask extends AsyncTask<Void, Void, Joke> {
    private String LOG_TAG = FetchJokeAsyncTask.class.getSimpleName();

    private JokesApi mJokesApiService = null;

    private FetchJokeAsyncTaskListener mListener;

    public FetchJokeAsyncTask(FetchJokeAsyncTaskListener listener) {
        super();
        this.mListener = listener;
    }

    @Override
    protected Joke doInBackground(Void... params) {
        if (mJokesApiService == null) {  // Only do this once
            JokesApi.Builder builder = new JokesApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver4
                    // https://discussions.udacity.com/t/following-googles-github-tutorial-for-implementing-the-backend-i-get-a-could-not-connect-to-10-0-2-2-port-8080-toast/35549/2?u=ivam_luz
                    // - 10.0.3.2 is localhost's IP address in Genymotion emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            mJokesApiService = builder.build();
        }

        try {
            return mJokesApiService.tellJoke().execute();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Joke joke) {
        if (joke != null) {
            mListener.onSuccess(joke);
        } else {
            mListener.onError();
        }
    }

    public interface FetchJokeAsyncTaskListener {
        void onSuccess(Joke joke);

        void onError();
    }
}