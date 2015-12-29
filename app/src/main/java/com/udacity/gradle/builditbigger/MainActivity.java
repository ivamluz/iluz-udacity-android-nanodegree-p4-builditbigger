package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gmail.ivamsantos.jokedisplayer.JokeActivity;
import com.gmail.ivamsantos.jokestelling.backend.jokesApi.JokesApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        new FetchJokeAsyncTask().execute();
    }

    class FetchJokeAsyncTask extends AsyncTask<Void, Void, String> {
        private JokesApi jokesApiService = null;
        private Context context;

        @Override
        protected String doInBackground(Void... params) {
            if (jokesApiService == null) {  // Only do this once
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

                jokesApiService = builder.build();
            }

            try {
                return jokesApiService.tellJoke().execute().getText();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String joke) {
            Intent intent = JokeActivity.newIntent(MainActivity.this, joke);
            startActivity(intent);
        }
    }
}
