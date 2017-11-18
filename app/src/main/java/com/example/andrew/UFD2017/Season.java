package com.example.andrew.UFD2017;

/**
 * Created by Andrew-hotmail on 30/01/2017.
 */
public class Season {

    public int year;
    public int ID;
    public double average;
    public int games;
    public double tog_average;
    public int tog_games;
    public double total;


    public Season (int con_year, int con_id,double con_average, int con_games, double con_tog_average, int con_tog_games){

        this.year = con_year;
        this.ID = con_id;
        this.average = con_average;
        this.games = con_games;
        this.tog_average = con_tog_average;
        this.tog_games = con_tog_games;
        this.total = con_games*con_average;
    }


}
