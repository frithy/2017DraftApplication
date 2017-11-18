package com.example.andrew.UFD2017;

import android.app.ActivityManager;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Andrew on 10/02/2016.
 */
public class UFApplication extends Application {

    public ArrayList<Player> playerArray = null;
    public int currentPlayer = 732;
    public ArrayList<OrderDrafted> draftArray;
    public MainActivity MA;
    public int pick = 1;
    public int myPick=9;
    public int count=0;
    public PotentialFuture currentExpectedPicks;

    public UFApplication(){

        this.playerArray = new ArrayList<Player>();
        this.currentExpectedPicks = new PotentialFuture(new Team(),new ArrayList<Player>(),this);
    }



    public Player getPlayer(int playerID){
       int x =0;
       try{
        for(int i = 0; i < playerArray.size();i++){
            x=i;
            if(playerArray.get(i).ID == playerID){
                playerArray.toString();
                return playerArray.get(i);

            }

        }}
       catch(Exception e){

            e.printStackTrace();

       }


        return playerArray.get(0);

    }

    public int getLowerID(int playerID){
        ArrayList<Integer> IDList = new ArrayList<Integer>();
        int lowestHighest =0;
        for(int i=0;i < playerArray.size();i++){

            IDList.add(playerArray.get(i).ID);

        }



        for(int i =0; i < playerArray.size();i++){

            if(playerArray.get(i).ID < playerID && playerArray.get(i).ID > lowestHighest){
               lowestHighest= playerArray.get(i).ID;

            }
        }

        if(lowestHighest ==0){

            return getHighestID();

        }else{

            Player p = getPlayer(lowestHighest);
            if(p != null){

                return lowestHighest;

            }else{

                return 1000;
            }
        }

    }

    public int getHigherID(int playerID){
        ArrayList<Integer> IDList = new ArrayList<Integer>();
        int highestLowest =9999;//gawd
        for(int i=0;i < playerArray.size();i++){

            IDList.add(playerArray.get(i).ID);

        }



        for(int i =0; i < playerArray.size();i++){

            if(playerArray.get(i).ID > playerID && playerArray.get(i).ID < highestLowest){
                highestLowest= playerArray.get(i).ID;

            }
        }

        if(highestLowest ==9999){

            return getLowestID();

        }else{

            Player p = getPlayer(highestLowest);
            if(p != null){

                return highestLowest;

            }else{

                return 1000;
            }
        }


    }


    public int getLowestID(){
        int lowest =10000;

        for(int i = 0; i < playerArray.size();i++){

            if(playerArray.get(i).ID < lowest){
                lowest = playerArray.get(i).ID;

            }

        }

        return lowest;

    }
    public int getHighestID(){
        int highest =0;

        for(int i = 0; i < playerArray.size();i++){

            if(playerArray.get(i).ID > highest){
                highest = playerArray.get(i).ID;

            }

        }

        return highest;

    }



    public void savePlayerArray(){
        try {

            File file = new File(getFilesDir(),"newplayerarray.txt");


            FileOutputStream fileOutputStream = new FileOutputStream(file);
            String outString = "";

            for (int i = 0; i < playerArray.size(); i++) {
                outString += (playerArray.get(i).playerString()+"\n");


            }
            fileOutputStream.write(outString.getBytes());
            fileOutputStream.close();

        }catch(Exception e){


        }
        saveInjuryString();
        saveDraftPick();
    }



    public void saveDraftPick(){
        try {

            File file = new File(getFilesDir(),"draftpick.txt");


            FileOutputStream fileOutputStream = new FileOutputStream(file);
            String outString = "";
            outString = String.valueOf(myPick);

            fileOutputStream.write(outString.getBytes());
            fileOutputStream.close();

        }catch(Exception e){


        }


    }

    public void loadDraftPick(){

        try{
            File injuryfile = new File(getFilesDir(),"draftpick.txt");
            if(injuryfile.length() > 0) {
                FileInputStream fis = new FileInputStream(injuryfile);
                DataInputStream in = new DataInputStream(fis);
                InputStreamReader ir  = new InputStreamReader(in);
                Scanner scan = new Scanner(ir);
                myPick = scan.nextInt();
            }}catch(Exception exception){


        }




    }

    public void saveInjuryString(){
        try {

            File file = new File(getFilesDir(),"injuryrecords.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            String outString = "";

            for (int i = 0; i < playerArray.size(); i++) {
                if(playerArray.get(i).injured) {
                    outString += (playerArray.get(i).getInjuryString() + "\n");
                }

            }
            fileOutputStream.write(outString.getBytes());
            fileOutputStream.close();

        }catch(Exception e){


        }
        saveDraftPick();
    }




    public int pickedPlayers(ArrayList<Player> tempArray){

        int picked = 0;
        for(int i = 0; i < tempArray.size();i++){


            if(tempArray.get(i).available == false){

                picked++;

            }

        }

        return picked;
    }




    public ArrayList<Player> populatePlayerList(String csvPath){
        ArrayList<Player> returnArray = new ArrayList<Player>();
        ArrayList<Season> seasonArray = new ArrayList<Season>();
        boolean importFailed = false;
        boolean preloaded = false;
        InputStreamReader ir = null;
        try{

           File file = new File(getFilesDir(),"newplayerarray.txt");

            if(file.length() > 0) {
                FileInputStream fis = new FileInputStream(file);
                DataInputStream in = new DataInputStream(fis);
                ir = new InputStreamReader(in);
                preloaded = true;

            }else{
                importFailed =true;
                InputStreamReader injuryStream = new InputStreamReader(getAssets().open("injuries.txt"));
                ir = new InputStreamReader(getAssets().open(csvPath));

            }

            Scanner scan = new Scanner(ir);
            String line = "";
            while ((line = scan.nextLine()) != null) {

                if(line.contains("Name")){


                }else{

                    String[] items = line.split(",");


                if(!preloaded){
                    if(items.length == 6){
                        //this is for new players, not for a previously saved file
                        //(String con_playerName, int con_ID, String con_Club, String con_Position,
                        //double con_Age,double con_projectedScore){
                        Player outplayer = new Player(items[0],Integer.parseInt(items[1]),items[2],items[3],Double.parseDouble(items[4]),Double.parseDouble(items[5]));
                        returnArray.add(outplayer);

                    }else if(items.length== 7){
                        Player outplayer = new Player(items[0],Integer.parseInt(items[1]),items[2],(items[3]+items[4]),Double.parseDouble(items[5]),Double.parseDouble(items[6]));
                        returnArray.add(outplayer);


                    }

                    //String con_playerName,int con_ID, String con_Position, String con_Club,int con_gamesPlayed,int con_Age,double con_projectedScore,double con_relativeValue
                }else{
                   // boolean con_available, boolean con_onTeam, int con_draftpick, int con_predictedPick
                    if(items.length == 10){
                        Player outplayer = new Player(items[0],Integer.parseInt(items[1]),items[2],items[3],Double.parseDouble(items[4]),Double.parseDouble(items[5]),Boolean.parseBoolean(items[6]),Boolean.parseBoolean(items[7]),Integer.valueOf(items[8]),Integer.parseInt(items[9]));
                        returnArray.add(outplayer);

                    }else if(items.length== 11){
                        Player outplayer = new Player(items[0],Integer.parseInt(items[1]),items[2],(items[3]+items[4]),Double.parseDouble(items[5]),Double.parseDouble(items[6]),Boolean.parseBoolean(items[7]),Boolean.parseBoolean(items[8]),Integer.valueOf(items[9]),Integer.parseInt(items[10]));
                        returnArray.add(outplayer);


                    }

                }}


            }

        }catch (Exception e){

            e.printStackTrace();
        }
        InputStreamReader sir = null;
        try{
           InputStream in = getAssets().open( "Seasons.csv" );
            sir = new InputStreamReader(in);
            BufferedReader bre = new BufferedReader(sir);
            Scanner scan = new Scanner(in);

            String line = "";
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                String[] items = line.split(",");
                //int con_year, int con_id,double con_average, int con_games, double con_tog_average, int con_tog_games
                Season outSeason = new Season(Integer.parseInt(items[0]),Integer.parseInt(items[1]),Double.parseDouble(items[2]),Integer.parseInt(items[3]),Double.parseDouble(items[4]),Integer.parseInt(items[5]));
                seasonArray.add(outSeason);
            }
        }catch (Exception e){

            e.printStackTrace();
        }

        for(int i =0;i < seasonArray.size();i++){

            for(int j =0;j < returnArray.size();j++){

                if(seasonArray.get(i).ID == returnArray.get(j).ID){

                    returnArray.get(j).seasons.add(seasonArray.get(i));

                }

            }



        }
            updateInjury(returnArray);
            loadDraftPick();
            return returnArray;
    }


    public void updateInjury(ArrayList<Player> returnArray){
        try{
            File injuryfile = new File(getFilesDir(),"injuryrecords.txt");
            if(injuryfile.length() > 0) {
                FileInputStream fis = new FileInputStream(injuryfile);
                DataInputStream in = new DataInputStream(fis);
                InputStreamReader ir  = new InputStreamReader(in);
                Scanner scan = new Scanner(ir);
                String line = "";
                while ((line = scan.nextLine()) != null) {
                    String[] items = line.split(",");

                    if(items.length > 1) {
                      for (Player returnPlayer : returnArray){
                        if (Integer.valueOf(items[0]).intValue() == returnPlayer.ID) {
                            returnPlayer.injured = true;
                            returnPlayer.injuredString = items[1];

                        }
                      }}



                }
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }




    }
    public void setUnavailable(){
        Player draftee = getPlayer(currentPlayer);
        draftee.available = false;
        draftee.draftPick = pick;
        pick++;
        updateProjections(playerArray);
        Toast.makeText(MA,((draftee.playerName)+" set to unavailable"),Toast.LENGTH_SHORT).show();

        this.savePlayerArray();
    }

    public void setUnavailable(int target){
        Player draftee = getPlayer(target);
        draftee.available = false;
        draftee.draftPick = pick;
        pick++;
        updateProjections(playerArray);
        Toast.makeText(MA,((draftee.playerName)+" set to unavailable"),Toast.LENGTH_SHORT).show();
        this.savePlayerArray();

    }




    public void setOnTeam(){
        Player draftee = getPlayer(currentPlayer);
        draftee.available = false;
        draftee.onTeam = true;
        draftee.draftPick = pick;
        pick++;
        updateProjections(playerArray);
        Toast.makeText(MA,((draftee.playerName)+" added to team"),Toast.LENGTH_SHORT).show();
        this.savePlayerArray();
    }



    public void undraft(int PlayerID){
        Player fixer = getPlayer(PlayerID);
        if(!fixer.available) {
            int pickedAt = fixer.draftPick;
            pick--;
            for (int i = 0; i < playerArray.size(); i++) {
                if (playerArray.get(i).draftPick > pickedAt && playerArray.get(i).draftPick != 9999) {
                    playerArray.get(i).draftPick = playerArray.get(i).draftPick-1;
                }else if(playerArray.get(i).draftPick == pickedAt){
                    playerArray.get(i).draftPick = 9999;
                    playerArray.get(i).available=true;
                    playerArray.get(i).onTeam=false;
                }
            }

            updateProjections(playerArray);
            Toast.makeText(MA, ((fixer.playerName) + " Reset availability"), Toast.LENGTH_SHORT).show();

            this.savePlayerArray();
        }
    }



    public ArrayList<Player> projectDraft(ArrayList<Player> tempArray){


        for(int i =0;i<tempArray.size();i++){
            tempArray.get(i).getPerceivedValue();

        }

        try {
            InputStreamReader ir = new InputStreamReader(getAssets().open("DraftOrder.csv"));
            Scanner draftOrderScanner = new Scanner(ir);
            String line="";
            while ((line = draftOrderScanner.nextLine()) != null) {
                String[] items = line.split(",");
                String position =items[1];
                int draftPick= Integer.parseInt(items[2]);
                int picked = pickedPlayers(tempArray);
                if(draftPick>pickedPlayers(tempArray) && draftPick < 309){
                int ID =0;
                double value =0;
                for (int i = 0; i < tempArray.size(); i++) {

                    if(tempArray.get(i).position.contains(position)){
                        if(tempArray.get(i).available && tempArray.get(i).predictedPick == 9999){
                            if(tempArray.get(i).perceivedValue>value){
                                value = tempArray.get(i).perceivedValue;
                                ID = tempArray.get(i).ID;


                            }
                        }

                    }

                }

                for(int i =0; i < tempArray.size();i++){
                    if(tempArray.get(i).ID == ID){
                        tempArray.get(i).predictedPick = draftPick;

                    }


                }


            }


            }



            }catch(Exception e){


        }

        return tempArray;
    }

    public ArrayList<Player> updateProjections(ArrayList<Player> tempArray){


        for(int i =0; i <tempArray.size();i++){
            tempArray.get(i).predictedPick = tempArray.get(i).draftPick;

        }
        int highest=0;
        for(int i =0; i <tempArray.size();i++){
            if(!tempArray.get(i).available){
                if(tempArray.get(i).predictedPick>highest){
                    highest = tempArray.get(i).predictedPick;

                }
            }


        }
        projectDraft(tempArray);

        return tempArray;


    }


    public void sortArray(){

        Collections.sort(playerArray, new Comparator<Player>() {
            @Override
            public int compare(Player lhs, Player rhs) {
                return Double.compare(lhs.customValue, rhs.customValue);

            }
        });

    }

    public void calcCustomValue(){

        for(int i =0;i<playerArray.size();i++){

            playerArray.get(i).getCustomValue();

        }


    }

    public Player getBestOption(){
        currentExpectedPicks = new PotentialFuture(new Team(),new ArrayList<Player>(),this);
        Team currentTeam = new Team();
        int currentPick = pick;

        for(int i =0;i<playerArray.size();i++){

            if(playerArray.get(i).onTeam){

                currentTeam.onMyTeam.add(playerArray.get(i));

                     }

        }
        currentTeam.recalcRequired();
        Player returnedPlayer = null;
        // loop through and check for already assigned players, getting the expected best players in each position which will be available
        currentTeam.onMyTeam.trimToSize();
        int currentCount = currentTeam.onMyTeam.size();
        int requiredRucks;
        int requiredFwds;
        int requiredMids;
        int requiredDefs;

       if(currentCount < 8){
            requiredRucks=1;
            requiredFwds=2;
            requiredMids=3;
            requiredDefs=2;
        }else if(currentCount < 18){
            requiredRucks=1;
            requiredFwds=5;
            requiredMids=7;
            requiredDefs=5;

        }else{
            requiredRucks=2;
            requiredFwds=6;
            requiredMids=8;
            requiredDefs=6;
        }

        ArrayList<PotentialFuture> futures = new ArrayList<PotentialFuture>();

        if(requiredRucks > currentTeam.Ruc){
            PotentialFuture RF = new PotentialFuture(currentTeam,playerArray,this);
            Player add = getBestInRole("RUC",RF.potentialPlayerArray);
            add.available = false;
            add.draftPick = pickedPlayers(playerArray)+1;
            add.onTeam = true;
            RF.currentTeam.onMyTeam.add(add);
            RF.startingPlayer=add;
            futures.add(RF);
            RF.run();
        }

        if(requiredDefs > currentTeam.Def){
            PotentialFuture DF = new PotentialFuture(currentTeam,playerArray,this);
            Player add = getBestInRole("DEF",DF.potentialPlayerArray);
            add.available = false;
            add.draftPick = pickedPlayers(playerArray)+1;
            add.onTeam = true;
            DF.currentTeam.onMyTeam.add(add);
            DF.startingPlayer=add;
            futures.add(DF);
            DF.run();
        }

        if(requiredFwds > currentTeam.Fwd ){
            PotentialFuture FF = new PotentialFuture(currentTeam,playerArray,this);
            Player add = getBestInRole("FWD",FF.potentialPlayerArray);
            add.available = false;
            add.draftPick = pickedPlayers(playerArray)+1;
            add.onTeam = true;
            FF.currentTeam.onMyTeam.add(add);
            FF.startingPlayer=add;
            futures.add(FF);
            FF.run();
        }
        if(requiredMids > currentTeam.Mid){
            PotentialFuture MF = new PotentialFuture(currentTeam,playerArray,this);
            Player add = getBestInRole("MID",MF.potentialPlayerArray);
            add.available = false;
            add.draftPick = pickedPlayers(playerArray)+1;
            add.onTeam = true;
            MF.currentTeam.onMyTeam.add(add);
            MF.startingPlayer=add;
            futures.add(MF);
            MF.run();
        }



        boolean complete=false;

        while(!complete){
            boolean currentState = true;

            for(PotentialFuture PF : futures){
                if(!PF.complete){
                    currentState = false;
                }
            }
            complete =currentState;
          }

       double highest =0.0;


       for(PotentialFuture PF : futures){
            if(PF.highestValue > highest){

                highest = PF.highestValue;
                returnedPlayer = PF.startingPlayer;
            }

       }


        return returnedPlayer;
    }


    public Player getBestInRole(String position,ArrayList<Player> tempArrayList){

        Double value=0.0;
        int ID=0;
        Player returnPlayer=null;
        for(int i =0;i < tempArrayList.size();i++){

            if(tempArrayList.get(i).position.contains(position) && tempArrayList.get(i).available){

                if(tempArrayList.get(i).customValue > value){
                    value = tempArrayList.get(i).customValue;
                    ID = tempArrayList.get(i).ID;
                    returnPlayer = tempArrayList.get(i);
                }

            }

        }

        return returnPlayer;
    }


     public PotentialFuture getResultingTeam(PotentialFuture future, int depth){
        depth++;
        future.currentTeam.onMyTeam.trimToSize();
        int currentCount = future.currentTeam.onMyTeam.size();



        int betweenPicks;
        count++;


         if((currentCount & 1) == 0) {

             betweenPicks = ((16-myPick)*2);


         }else{
             betweenPicks = ((myPick-1)*2);
         }


         int requiredRucks;
         int requiredFwds;
         int requiredMids;
         int requiredDefs;

         if(currentCount < 8){
             requiredRucks=1;
             requiredFwds=2;
             requiredMids=3;
             requiredDefs=2;
         }else if(currentCount <18){
             requiredRucks=1;
             requiredFwds=5;
             requiredMids=7;
             requiredDefs=5;

         }else{
             requiredRucks=2;
             requiredFwds=6;
             requiredMids=8;
             requiredDefs=6;
         }



         if(depth < 8) {
            future.currentTeam.recalcRequired();


            Player Forward = getBestInRole("FWD", future.potentialPlayerArray);
            Player Defender = getBestInRole("DEF", future.potentialPlayerArray);
            Player Ruck = getBestInRole("RUC", future.potentialPlayerArray);
            Player Midfielder = getBestInRole("MID", future.potentialPlayerArray);


             if (requiredFwds > future.currentTeam.Fwd) {

                future.currentTeam.onMyTeam.add(Forward);
                Forward.available = false;
                future.currentPick= pickedPlayers(future.potentialPlayerArray);
                Forward.onTeam = true;
                Forward.draftPick = future.currentPick;


                future.updateAvailable(betweenPicks);
                future = getResultingTeam(future,depth);
                 if (future.currentTeam.getTeamValue() >= future.highestValue) {
                     future.highestValue = future.currentTeam.getTeamValue();
                 }
                if (future.currentTeam.getTeamValue() > currentExpectedPicks.highestValue) {
                    currentExpectedPicks = new PotentialFuture(future);

                }



                future.resetAvailable(betweenPicks);
                future.currentTeam.onMyTeam.remove(Forward);
                Forward.onTeam = false;
                Forward.available = true;
                Forward.draftPick = 9999;

            }

          future.currentTeam.recalcRequired();

            if (requiredDefs > future.currentTeam.Def) {
                future.currentTeam.onMyTeam.add(Defender);

                Defender.available = false;
                future.currentPick= pickedPlayers(future.potentialPlayerArray);
                Defender.draftPick = future.currentPick;

                Defender.onTeam = true;
                future.updateAvailable(betweenPicks);
                future = getResultingTeam(future,depth);
                if (future.currentTeam.getTeamValue() >= future.highestValue) {
                    future.highestValue = future.currentTeam.getTeamValue();
                }
                if (future.currentTeam.getTeamValue() > currentExpectedPicks.highestValue) {
                    currentExpectedPicks = new PotentialFuture(future);

                }


                future.resetAvailable(betweenPicks);

                future.currentTeam.onMyTeam.remove(Defender);
                Defender.onTeam = false;
                Defender.available = true;
                Defender.draftPick = 9999;

            }
          future.currentTeam.recalcRequired();

          if (requiredRucks > future.currentTeam.Ruc) {
                future.currentTeam.onMyTeam.add(Ruck);

                Ruck.available = false;
              future.currentPick= pickedPlayers(future.potentialPlayerArray);
                Ruck.draftPick = future.currentPick;
                Ruck.onTeam = true;


               future.updateAvailable(betweenPicks);
               future = getResultingTeam(future,depth);
              if (future.currentTeam.getTeamValue() >= future.highestValue) {
                  future.highestValue = future.currentTeam.getTeamValue();
              }
              if (future.currentTeam.getTeamValue() > currentExpectedPicks.highestValue) {
                  currentExpectedPicks = new PotentialFuture(future);

              }


                future.resetAvailable(betweenPicks);
                future.currentTeam.onMyTeam.remove(Ruck);
                Ruck.onTeam = false;
                Ruck.available = true;
                Ruck.draftPick = 9999;

            }
          future.currentTeam.recalcRequired();

          if (requiredMids > future.currentTeam.Mid) {
                future.currentTeam.onMyTeam.add(Midfielder);

                Midfielder.available = false;
                 future.currentPick= pickedPlayers(future.potentialPlayerArray);
                Midfielder.draftPick = future.currentPick;
                Midfielder.onTeam = true;
                future.updateAvailable(betweenPicks);
                future = getResultingTeam(future,depth);
              if (future.currentTeam.getTeamValue() >= future.highestValue) {
                  future.highestValue = future.currentTeam.getTeamValue();
              }
              if (future.currentTeam.getTeamValue() > currentExpectedPicks.highestValue) {
                  currentExpectedPicks = new PotentialFuture(future);

              }


              future.resetAvailable(betweenPicks);
                future.currentTeam.onMyTeam.remove(Midfielder);

                Midfielder.onTeam = false;
                Midfielder.available = true;
                Midfielder.draftPick = 9999;



            }



        }
         future.currentTeam.recalcRequired();

        return future;
    }




    }
