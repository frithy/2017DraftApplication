package com.example.andrew.UFD2017;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Andrew-hotmail on 5/02/2017.
 */
public class PotentialFuture implements Runnable{
    public Team currentTeam;
    public ArrayList<Player> potentialPlayerArray;
    public ArrayList<Player> recentlyChanged = new ArrayList<Player>();
    public Player selectionOption;
    public Player startingPlayer;
    public double highestValue =0;
    public int currentPick=0;
    public boolean complete = false;
    public UFApplication thisApp;

    public PotentialFuture(Team con_currentTeam, ArrayList<Player> con_potentialPlayerArray,UFApplication con_thisApp){
        this.currentTeam = cloneTeam(con_currentTeam);
        this.potentialPlayerArray= clonePlayerList(con_potentialPlayerArray);
        this.thisApp = con_thisApp;

    }

    public PotentialFuture(PotentialFuture con_this){
        this.currentTeam = cloneTeam(con_this.currentTeam);
        if(con_this.selectionOption != null) {
            this.selectionOption = new Player(con_this.selectionOption);
        }
        this.potentialPlayerArray = clonePlayerList(con_this.potentialPlayerArray);
        this.startingPlayer = con_this.startingPlayer;
        this.highestValue = con_this.highestValue;

    }

    public void setSelectionOption(Player inObj){
        this.selectionOption = new Player(inObj);


    }
    public void updateAvailable(int betweenpicks){

        while(betweenpicks != 0){

            int lowest =500;
            int spot = 0;
            for(int i =0; i < potentialPlayerArray.size();i++){

                if(potentialPlayerArray.get(i).predictedPick < lowest && potentialPlayerArray.get(i).available){
                    lowest = potentialPlayerArray.get(i).predictedPick;
                    spot = i;
                }

            }




                potentialPlayerArray.get(spot).available = false;
                potentialPlayerArray.get(spot).draftPick =thisApp.pickedPlayers(potentialPlayerArray);


            betweenpicks--;
        }



    }



    public void resetAvailable(int betweenpicks){

        while(betweenpicks > 0){

            int highest =0;
            int spot = 0;
            for(int i =0; i < potentialPlayerArray.size();i++){

                if(potentialPlayerArray.get(i).draftPick > highest && (!potentialPlayerArray.get(i).available)){
                    highest = potentialPlayerArray.get(i).draftPick;
                    spot = i;
                }

            }




            potentialPlayerArray.get(spot).available = true;
            potentialPlayerArray.get(spot).draftPick = 9999;



            betweenpicks--;
        }

    }

    public static ArrayList<Player> clonePlayerList(ArrayList<Player> list) {
        ArrayList<Player> returnArray = new ArrayList<Player>(list.size());
        for (Player item : list) {
            returnArray.add(new Player(item));
        }
        return returnArray;
    }

    public static Team cloneTeam(Team team) {
        Team returnTeam = new Team();
        returnTeam.Fwd = team.Fwd;
        returnTeam.Def = team.Def;
        returnTeam.Mid = team.Mid;
        returnTeam.Ruc = team.Ruc;
        returnTeam.onMyTeam = clonePlayerList(team.onMyTeam);
        return returnTeam;
    }

    @Override
    public void run() {
        int currentCount = currentTeam.onMyTeam.size();
        int betweenPicks=0;

        if((currentCount & 1) == 0) {
            betweenPicks = ((thisApp.myPick-1)*2);

        }else{

            betweenPicks = ((14-thisApp.myPick)*2);
        }



        updateAvailable(betweenPicks);
        thisApp.getResultingTeam(this,0);
        complete=true;
    }
}
