package com.example.andrew.UFD2017;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


/**
 * Created by Andrew on 10/02/2016.
 */
public class SettingsFragment extends Fragment {
    UFApplication thisApp;
    NumberPicker numberPicker;
    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisApp = (UFApplication) this.getActivity().getApplicationContext();
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        numberPicker = (NumberPicker) rootView.findViewById(R.id.numberPicker);
        final TextView textView = (TextView) rootView.findViewById(R.id.testingID);
        textView.setText("DraftPick: "+String.valueOf( thisApp.myPick));
        numberPicker.setValue(10);
        numberPicker.setMaxValue(14);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //textView.setText(String.valueOf(newVal));
            }
        });
        Button commitButton = (Button) rootView.findViewById(R.id.commitButtonID);
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisApp.myPick = numberPicker.getValue();
                textView.setText("DraftPick: "+String.valueOf( thisApp.myPick));
                thisApp.saveDraftPick();
            }
        });
        return rootView;
    }

}
