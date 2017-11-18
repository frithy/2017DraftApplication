package com.example.andrew.UFD2017;

import java.util.ArrayList;

/**
 * Created by Andrew-hotmail on 4/02/2017.
 */
public class Draft {
    public int myPick;
    public int currentPick;
    public ArrayList<OrderDrafted> draftPicks;
    public ArrayList<Player> playerArray;

    public Draft(int con_MyPick, int con_currentPick,ArrayList<Player> con_playerArray, ArrayList<OrderDrafted> con_draftPicks){

        this.myPick = con_MyPick;
        this.currentPick = con_currentPick;
        this.playerArray = con_playerArray;
        this.draftPicks = con_draftPicks;


    }

    public Draft(int con_MyPick, int con_currentPick,ArrayList<Player> con_playerArray){

        this.myPick = con_MyPick;
        this.currentPick = con_currentPick;
        this.playerArray = con_playerArray;
        this.draftPicks = new ArrayList<OrderDrafted>();


    }



}
