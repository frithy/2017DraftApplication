package com.example.andrew.UFD2017;


import java.util.ArrayList;

/**
 * Created by Andrew on 9/03/2015.
 */
public class Player {

    public String playerName;
    public int ID;
    public String position;
    public String club;
    public int gamesPlayed;
    public double age;
    public double projectedScore;
    public int predictedPick=9999;
    public int myPredictedPick=9999;
    public int draftPick = 9999;
    public boolean available;
    public boolean onTeam;
    public boolean injured;
    public String injuredString;
    public double customValue=0.0;
    public double perceivedValue=0.0;
    public ArrayList<Season> seasons = new ArrayList<Season>();


    public Player(String con_playerName, int con_ID, String con_Club, String con_Position,
                  double con_Age, double con_projectedScore,boolean con_available, boolean con_onTeam, int con_draftpick, int con_predictedPick) {

        this.playerName = con_playerName;
        this.ID = con_ID;
        this.position = con_Position;
        this.club = con_Club;
        this.age = con_Age;
        this.projectedScore = con_projectedScore;
        this.available = con_available;
        this.onTeam = con_onTeam;
        this.draftPick=con_draftpick;
        this.predictedPick=con_predictedPick;

    }





    public Player(String con_playerName, int con_ID, String con_Club, String con_Position,
                  double con_Age,double con_projectedScore){

            this.playerName = con_playerName;
            this.ID = con_ID;
            this.club = con_Club;
            this.position = con_Position;
            this.age = con_Age;
            this.projectedScore = con_projectedScore;
            this.available = true;
            this.onTeam = false;
            this.draftPick=9999;
            this.predictedPick=9999;
            this.injuredString = "Not Injured";
            this.injured = false;

            }
    public Player(Player con_this){
        this.playerName = con_this.playerName;
        this.ID = con_this.ID;
        this.club = con_this.club;
        this.position = con_this.position;
        this.age = con_this.age;
        this.projectedScore = con_this.projectedScore;
        this.available = con_this.available;
        this.onTeam = con_this.onTeam;
        this.draftPick=con_this.draftPick;
        this.predictedPick=con_this.predictedPick;
        this.customValue = con_this.customValue;
        this.perceivedValue = con_this.perceivedValue;
        this.seasons = con_this.seasons;
    }

//String con_playerName, int con_ID, String con_Club, String con_Position,
//double con_Age, double con_projectedScore,boolean con_available, boolean con_onTeam, int con_draftpick, int con_predictedPick
    public String playerString() {
        String outString = "";
        outString = (playerName + "," + String.valueOf(ID) + "," + club + "," + position + "," +
                String.valueOf(age) + "," + String.valueOf(projectedScore)
                + "," + String.valueOf(available) + "," + String.valueOf(onTeam) + ","+String.valueOf(draftPick)+","+String.valueOf(predictedPick));

        return outString;
    }


    public Season getStats (int year){

        for(int i =0; i < seasons.size();i++){

            if(seasons.get(i).year == year){

                return seasons.get(i);

            }


        }


        return seasons.get(0);

    }


    public void getPerceivedValue(){
        int year=0;
        for(int i =0;i < seasons.size();i++){

            if(seasons.get(i).year  > year){

                year = seasons.get(i).year;
            }

        }

        Season recentSeason = getStats(year);
        boolean missedAYear = false;
        if(year < 2016){

            missedAYear=true;

        }

        double value = 0;

        value = projectedScore;
        int gameValue=0;

        if(recentSeason.games < 10 && !missedAYear){

            Season yearBefore = getStats(year-1);
            missedAYear =true;
            if(yearBefore != null){
                gamesPlayed = yearBefore.games;
            }

        }else{

            gamesPlayed = recentSeason.games;
        }

        int injuryRemoval=0;
        if(injured){

            if(injuredString != "Not Selected"){
                injuryRemoval = Integer.parseInt(injuredString.split(" ")[1]);
            }

        }
        if((gamesPlayed-injuryRemoval) > 15){
            gameValue=15;

        }else{
            gameValue=gamesPlayed-injuryRemoval;

        }

        value = value*gameValue;

        if(missedAYear==true){

            value = value *.95;
        }


        this.perceivedValue=value;
    }

    public void getCustomValue(){
        int year=0;
        for(int i =0;i < seasons.size();i++){

            if(seasons.get(i).year  > year){

                year = seasons.get(i).year;
            }

        }

        Season recentSeason = getStats(year);
        boolean missedAYear = false;
        if(year < 2016){

            missedAYear=true;

        }

        double value = 0;

        value = (projectedScore);
        int gameValue=0;

        if(recentSeason.games < 10 && !missedAYear){

            Season yearBefore = getStats(year-1);
            missedAYear =true;
            if(yearBefore != null){
                gamesPlayed = yearBefore.games;
            }

        }else{

            gamesPlayed = recentSeason.games;
        }

        int injuryRemoval=0;
        if(injured){

            if(injuredString != "Not Selected"){
                injuryRemoval = Integer.parseInt(injuredString.split(" ")[1]);
            }

        }
        if((gamesPlayed-injuryRemoval) > 18){
            gameValue=18;

        }else{
            gameValue=gamesPlayed-injuryRemoval;

        }

        value = value*gameValue;

        if(missedAYear==true || age > 30){
            value = value * .95;

        }



        this.customValue= value;
    }

    public String getInjuryString(){
        String outString = "";
        if(injured){
            outString = (String.valueOf(ID)+","+injuredString);
            }


        return outString;
    }


}


