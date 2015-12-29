/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.gmail.ivamsantos.jokestelling.backend;

import com.gmail.ivamsantos.JokesProvider;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

@Api(
        name = "jokesApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.jokestelling.ivamsantos.gmail.com",
                ownerName = "backend.jokestelling.ivamsantos.gmail.com",
                packagePath = ""
        )
)
public class JokesEndpoint {
    private JokesProvider mJokesProvider;

    public JokesEndpoint() {
        mJokesProvider = new JokesProvider();
    }

    @ApiMethod(name = "tellJoke")
    public Joke tellJoke() {
        String jokeText = mJokesProvider.getJoke();

        Joke response = new Joke();
        response.setText(jokeText);

        return response;
    }

}
