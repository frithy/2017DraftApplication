package com.example.andrew.UFD2017;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Andrew on 10/02/2016.
 */
public class DraftFragment extends Fragment {
    UFApplication thisApp;
    ListView resultList;
    public DraftFragment(){};
    DraftListAdapter draftListAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisApp = (UFApplication) this.getActivity().getApplicationContext();
        View rootView = inflater.inflate(R.layout.fragment_draftorder, container, false);

        resultList = (ListView)rootView.findViewById(R.id.draftOrder);
        ArrayList<Player> onTeam = new ArrayList<Player>();


        for(int i = 0; i < thisApp.playerArray.size();i++){

            if(thisApp.playerArray.get(i).available == false){

                onTeam.add(thisApp.playerArray.get(i));

            }


        }

        if(onTeam.size() > 0){

            refreshSearch(onTeam);

        }


        return rootView;
    }



    public void refreshSearch(ArrayList<Player> resultingPlayers){

        final Player[] temp = new Player[resultingPlayers.size()];//.toArray((new Player[thisApp.playerArray.size()]));
        String[] itemname = new String[resultingPlayers.size()];


        Collections.sort(resultingPlayers,new Comparator<Player>() {
            @Override
            public int compare(Player lhs, Player rhs) {
                return lhs.draftPick - rhs.draftPick;
            }
        });
        resultingPlayers.toArray(temp);
        draftListAdapter = new DraftListAdapter(this.getActivity(),temp, itemname,true);


        resultList.setAdapter(draftListAdapter);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                thisApp.currentPlayer = temp[position].ID;
                Fragment fragment = new PlayerFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right).replace(R.id.container, fragment).commit();



            }


        });

        resultList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                thisApp.currentPlayer = temp[position].ID;
                if(thisApp.getPlayer(thisApp.currentPlayer).available){
                    thisApp.setUnavailable(thisApp.currentPlayer);
                    view.setBackgroundResource(R.drawable.roundedlayout);
                    draftListAdapter.notifyDataSetChanged();

                }else{

                    thisApp.undraft(thisApp.currentPlayer);

                    view.setBackgroundResource(R.drawable.roundedlayout_white);
                    draftListAdapter.notifyDataSetChanged();


                }


                return true;
            }
        });

    }


}
