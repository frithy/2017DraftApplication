package com.example.andrew.UFD2017;

import java.util.ArrayList;

/**
 * Created by Andrew-hotmail on 5/02/2017.
 */
public class Team {

    public ArrayList<Player> onMyTeam = new ArrayList<Player>();
    public int Mid=0;
    public int Def=0;
    public int Fwd=0;
    public int Ruc=0;

    public Team (){}


    public Double getTeamValue() {

        Double value=0.0;

        Double highestValue =0.0;
        for(int i =0;i<onMyTeam.size();i++){

            value += onMyTeam.get(i).customValue;
            if(highestValue < onMyTeam.get(i).customValue){
                highestValue = onMyTeam.get(i).customValue;

            }
        }
        value = value + highestValue;
        return value;
    }

    public void recalcRequired(){
        int currentFwds = 0;
        int currentDefs=0;
        int currentMids=0;
        int currentRucs=0;
        for(int i =0; i < onMyTeam.size();i++){

            if(onMyTeam.get(i).position.toLowerCase().contains("fwd")){

                currentFwds++;
            }else if(onMyTeam.get(i).position.toLowerCase().contains("def")){
                currentDefs++;

            }else if (onMyTeam.get(i).position.toLowerCase().contains(("ruc"))){
                currentRucs++;
               }else{

                currentMids++;
            }


        }

        Mid=currentMids;
        Fwd=currentFwds;
        Def=currentDefs;
        Ruc=currentRucs;









    }

}
