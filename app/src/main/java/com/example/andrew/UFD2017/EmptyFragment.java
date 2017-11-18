package com.example.andrew.UFD2017;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Andrew on 9/02/2016.
 */
public class EmptyFragment extends Fragment {
    UFApplication thisApp;
    public EmptyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisApp = (UFApplication) this.getActivity().getApplicationContext();
        View rootView = inflater.inflate(R.layout.fragment_empty, container, false);

        return rootView;
    }
}
