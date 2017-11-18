package com.example.andrew.UFD2017;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Spinner;
import java.text.DecimalFormat;
import java.util.*;


/**
 * Created by Andrew on 9/02/2016.
 */
public class PlayerFragment extends Fragment {
    UFApplication thisApp = null;
    ImageView iv;
    public int year;
    TextView nt;
    public Player thePlayer;
    TextView aveT;
    TextView gpT;

    TextView stdT;
    TextView predictedPickText;
    TextView projT;
    TextView availT;
    ImageView availI;
    TextView injuryT;
    TextView pickTaken;
    TextView totalScore;
    TextView togAverage;
    ImageView injuryImage;
    RatingBar rb;
    TextView conDets;
    TextView tvTOG;
    TextView relativeValueT;
    TextView perceivedValueT;
    Spinner spinner;
    Spinner injurySpinner;
    boolean firstCall = true;
    //FIX this and have run from the main class
    public PlayerFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisApp = (UFApplication) this.getActivity().getApplicationContext();
        View rootView = inflater.inflate(R.layout.fragment_player, container, false);


        totalScore = (TextView) rootView.findViewById(R.id.FP_Total);
        iv = (ImageView) rootView.findViewById(R.id.FP_playerPicture);
        thePlayer = thisApp.getPlayer(thisApp.currentPlayer);

        nt = (TextView) rootView.findViewById(R.id.FP_playerName);
        relativeValueT = (TextView) rootView.findViewById(R.id.FP_rValue);
        perceivedValueT = (TextView) rootView.findViewById(R.id.FP_pValue);
        aveT = (TextView) rootView.findViewById(R.id.FP_Average);
        availI = (ImageView) rootView.findViewById(R.id.fp_availabilityImage);
        availT = (TextView) rootView.findViewById(R.id.fp_availabilityStatus);

        gpT = (TextView) rootView.findViewById(R.id.FP_gamesPlayed);
        projT = (TextView) rootView.findViewById(R.id.FP_Projected);

        togAverage = (TextView) rootView.findViewById(R.id.FP_togaverage);





        pickTaken = (TextView) rootView.findViewById(R.id.FP_pickTaken);
        conDets = (TextView) rootView.findViewById(R.id.playercondets);
        RelativeLayout LL = (RelativeLayout) rootView.findViewById(R.id.playerFragment);
        LL.setOnTouchListener(new OnSwipeTouchListener(this.getActivity(), thisApp));
        rb = (RatingBar) rootView.findViewById(R.id.FP_starRating);
        predictedPickText = (TextView) rootView.findViewById(R.id.FP_predictedPick);
        injuryImage = (ImageView) rootView.findViewById(R.id.fp_injuryimage);

        injurySpinner = (Spinner)rootView.findViewById(R.id.injury_spinner);

        spinner = (Spinner)rootView.findViewById(R.id.year_spinner);

        List<String> injuryStatuses = new ArrayList<String>();

        injuryStatuses.add("Not Injured");
        injuryStatuses.add("< 2 games");
        injuryStatuses.add("< 5 games");
        injuryStatuses.add("< 10 games");
        injuryStatuses.add("< 15 games");
        injuryStatuses.add("< 20 games");

        ArrayAdapter<String> injuryAdapter = new ArrayAdapter<String>(thisApp,android.R.layout.simple_spinner_item, injuryStatuses);
        injuryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int pos = injuryAdapter.getPosition(thePlayer.injuredString);
        injurySpinner.setAdapter(injuryAdapter);
        injurySpinner.setSelection(pos,false);




        injurySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).toString() == "Not Injured"){
                    thePlayer.injured = false;
                    thePlayer.injuredString = parent.getItemAtPosition(position).toString();
                    int imageID = getResources().getIdentifier("tick", "drawable",   thisApp.getPackageName());
                    injuryImage.setImageResource(imageID);
                }else{
                    thePlayer.injured = true;
                    thePlayer.injuredString = parent.getItemAtPosition(position).toString();
                    int imageID = getResources().getIdentifier("injuryicon", "drawable",   thisApp.getPackageName());
                    injuryImage.setImageResource(imageID);
                }

                thisApp.savePlayerArray();
                thisApp.calcCustomValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<String> list = new ArrayList<String>();

        for(int i =0;i<thePlayer.seasons.size();i++){

            list.add(String.valueOf(thePlayer.seasons.get(i).year));

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(thisApp,android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                setPlayer(year,thePlayer);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        setPlayer(year,thePlayer);

        return rootView;
    }


    public void setPlayer(int year, Player currentPlayer){







        int id = thisApp.currentPlayer;
        MainActivity ma = (MainActivity) getActivity();


        DecimalFormat df = new DecimalFormat("#");
        //at.setText(df.format(currentPlayer.age) + " years old");
        nt.setText(currentPlayer.playerName);
        totalScore.setText(df.format((currentPlayer).getStats(year).total));
        predictedPickText.setText(String.valueOf(currentPlayer.predictedPick));
        perceivedValueT.setText(df.format(currentPlayer.perceivedValue));
        relativeValueT.setText(df.format(currentPlayer.customValue));
        togAverage.setText(df.format(currentPlayer.getStats(year).tog_average));
        if(currentPlayer.available == false){

           if(currentPlayer.onTeam){
               availT.setText("On Team");
               int imageID = getResources().getIdentifier("tick", "drawable",   thisApp.getPackageName());
               availI.setImageResource(imageID);

           }else{

               availT.setText("Unavailable");
               int imageID = getResources().getIdentifier("cross", "drawable",   thisApp.getPackageName());
               availI.setImageResource(imageID);


           }

        }else if(currentPlayer.available){

            availT.setText("Available");
            int imageID = getResources().getIdentifier("tick", "drawable",   thisApp.getPackageName());
            availI.setImageResource(imageID);


        }
        rb.setRating(4);


        String posString="";
        if(currentPlayer.position.length() == 3) {
            posString =(currentPlayer.position.replace("MID", "Midfielder").replace("FWD", "Forward").replace("DEF", "Defender").replace("RUC", "Ruck").replace("\"", ""));

            //posT.setText(currentPlayer.position.replace("MID", "Midfielder").replace("FWD", "Forward").replace("DEF", "Defender").replace("RUC", "Ruck").replace("\"", ""));
        }else{


            if(currentPlayer.position.contains("RUC")){
                if(posString.length() > 1){

                    posString+=" & Ruck";

                }else{

                    posString = "Ruck";

                }


            }
            if(currentPlayer.position.contains("DEF")){

                if(posString.length() > 1){

                    posString+=" & Defender";

                }else{

                    posString = "Defender";

                }
            }


            if(currentPlayer.position.contains("FWD")){
                if(posString.length() > 1){
                    posString+=" & Forward";
                }else{
                    posString = "Forward";
                }
            }
            if(currentPlayer.position.contains("MID")){
                if(posString.length() > 1){
                    posString+=" & Midfielder";
                }else{
                    posString = "Midfielder";
                }
            }

            //posT.setText(posString.replace("\"",""));

        }
        String condeets = "[AGE] years old, [POS], [CLUB]";
        String club = getClub(currentPlayer.club);
        conDets.setText(condeets.replace("[AGE]",df.format(currentPlayer.age)).replace("[POS]", posString).replace("[CLUB]",club));

        gpT.setText(String.valueOf(currentPlayer.getStats(year).games));
        aveT.setText(df.format(currentPlayer.getStats(year).average));


        projT.setText(df.format(currentPlayer.projectedScore));


        String s = "player" + String.valueOf(id);  // image name is needed without extention
        int rid = getResources().getIdentifier(s, "drawable",   thisApp.getPackageName());
        iv.setImageResource(rid);
        if(currentPlayer.injured) {
            int imageID = getResources().getIdentifier("injuryicon", "drawable",   thisApp.getPackageName());
            injuryImage.setImageResource(imageID);

        }else{

            int imageID = getResources().getIdentifier("tick", "drawable",   thisApp.getPackageName());
            injuryImage.setImageResource(imageID);

        }

        if(!currentPlayer.available){

        pickTaken.setText(String.valueOf(currentPlayer.draftPick));}
        else{
            pickTaken.setText("NA");


        }
        df = new DecimalFormat("#.00");



    }

    public String getClub(String setclub){

        return setclub;
       }












}