package com.example.andrew.UFD2017;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Andrew on 14/02/2016.
 */
public class TeamViewListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final Player[] playerListArray;

    public TeamViewListAdapter(Activity context, Player[] playerListArray_con, String[] itemname ) {
        super(context, R.layout.list_rows,itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.playerListArray = playerListArray_con;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_rows, null,true);

        TextView txtName = (TextView) rowView.findViewById(R.id.lv_playername);
        ImageView imgPlayer = (ImageView) rowView.findViewById(R.id.lv_playerPic);
        TextView txtClub = (TextView) rowView.findViewById(R.id.lv_teamPicked);
        TextView txtAvg = (TextView) rowView.findViewById(R.id.lv_average);
        TextView txtGames = (TextView) rowView.findViewById(R.id.lv_games);
        TextView txtPos = (TextView) rowView.findViewById(R.id.lv_position);
        DecimalFormat df = new DecimalFormat("#.0");
        txtName.setText(playerListArray[position].playerName);

        txtAvg.setText(df.format(playerListArray[position].getStats(2016).average));
        txtGames.setText(String.valueOf(playerListArray[position].gamesPlayed + " games"));
        Player currentPlayer = playerListArray[position];
        String posString = "";
        if (currentPlayer.position.length() == 3) {
            posString = (currentPlayer.position.replace("MID", "Midfielder").replace("FWD", "Forward").replace("DEF", "Defender").replace("RUC", "Ruck").replace("\"", ""));

            //posT.setText(currentPlayer.position.replace("MID", "Midfielder").replace("FWD", "Forward").replace("DEF", "Defender").replace("RUC", "Ruck").replace("\"", ""));
        } else {


            if (currentPlayer.position.contains("RUC")) {
                if (posString.length() > 1) {

                    posString += " & Ruck";

                } else {

                    posString = "Ruck";

                }


            }
            if (currentPlayer.position.contains("DEF")) {

                if (posString.length() > 1) {

                    posString += " & Defender";

                } else {

                    posString = "Defender";

                }
            }


            if (currentPlayer.position.contains("FWD")) {
                if (posString.length() > 1) {

                    posString += " & Forward";

                } else {

                    posString = "Forward";

                }


            }
            if (currentPlayer.position.contains("MID")) {
                if (posString.length() > 1) {

                    posString += " & Midfielder";

                } else {

                    posString = "Midfielder";

                }


            }

            //posT.setText(posString.replace("\"",""));

        }


        txtPos.setText(posString);

        String s = "player" + String.valueOf(playerListArray[position].ID);  // image name is needed without extention
        int rid = context.getResources().getIdentifier(s, "drawable", "com.example.andrew.UFD2017");
        imgPlayer.setImageResource(rid);
        if(playerListArray[position].available) {

        }else{

            if(playerListArray[position].onTeam){



            }else{



            }

        }




        return rowView;

    };


    public String getClub(String setclub) {

        String outString = "";
        switch (setclub) {
            case "ADL":
                outString = "Adelaide";
                break;
            case "BRI":
                outString = "Brisbane";
                break;
            case "CAR":
                outString = "Carlton";
                break;
            case "COL":
                outString = "Collingwood";
                break;
            case "ESS":
                outString = "Essendon";
                break;
            case "FRE":
                outString = "Fremantle";
                break;
            case "GCS":
                outString = "Gold Coast";
                break;
            case "GEE":
                outString = "Geelong";
                break;
            case "GWS":
                outString = "Western Sydney";
                break;
            case "HAW":
                outString = "Hawthorne";
                break;
            case "MEL":
                outString = "Melbourne";
                break;
            case "NTM":
                outString = "North Melbourne";
                break;
            case "PAP":
                outString = "Port Adelaide";
                break;
            case "RIC":
                outString = "Richmond";
                break;
            case "STK":
                outString = "St. Kilda";
                break;
            case "SYD":
                outString = "Sydney";
                break;
            case "WBD":
                outString = "Western Bulldogs";
                break;
            case "WCE":
                outString = "West Coast";
                break;
            default:
                outString = "Default";
        }
        return outString;
    }
}