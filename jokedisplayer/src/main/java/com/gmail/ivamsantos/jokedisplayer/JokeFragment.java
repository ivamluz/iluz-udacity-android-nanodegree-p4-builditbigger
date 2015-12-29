package com.gmail.ivamsantos.jokedisplayer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JokeFragment extends Fragment {
    private static final String ARG_JOKE = "joke";

    private String mJoke;

    public static JokeFragment newInstance(String joke) {
        Bundle args = new Bundle();
        args.putString(ARG_JOKE, joke);

        JokeFragment fragment = new JokeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mJoke = getArguments().getString(ARG_JOKE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_joke, container, false);
        TextView txtJoke = (TextView) rootView.findViewById(R.id.joke);
        txtJoke.setText(mJoke);

        return rootView;
    }
}
