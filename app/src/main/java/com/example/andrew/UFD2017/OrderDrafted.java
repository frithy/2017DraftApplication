package com.example.andrew.UFD2017;

/**
 * Created by Andrew on 13/02/2016.
 */
public class OrderDrafted
{

    public int playerID;
    public int picktaken;
    public String position;
    public int drafter;


    public OrderDrafted(int playerID_con, int pickTaken_con, String position_con, int con_drafter){

        this.playerID = playerID_con;
        this.picktaken = pickTaken_con;
        this.position = position_con;
        this.drafter = con_drafter;
       }



}
