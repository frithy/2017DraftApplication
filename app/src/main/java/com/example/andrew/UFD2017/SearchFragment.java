package com.example.andrew.UFD2017;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Andrew on 9/02/2016.
 */
public class SearchFragment extends Fragment {
    UFApplication thisApp;
    SearchView searchView;
    ListView resultList;
    public CustomListAdapter playerArrayAdapter;
    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisApp = (UFApplication) this.getActivity().getApplicationContext();
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = (SearchView) rootView.findViewById(R.id.fsearch_searchbar);
        resultList = (ListView)rootView.findViewById(R.id.playerSearchResults);
        refreshSearch(thisApp.playerArray);




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            public boolean onQueryTextSubmit(String query) {
                ArrayList<Player> resultArray = new ArrayList<Player>();
                if(query.contains("*")){

                  resultArray = thisApp.playerArray;

                }else{
                for (int i = 0; i < thisApp.playerArray.size(); i++) {

                    if (thisApp.playerArray.get(i).playerName.toLowerCase().contains(query.toLowerCase())) {

                        resultArray.add(thisApp.playerArray.get(i));
                    }

                }}

                if(resultArray.size() >= 1) {

                    refreshSearch(resultArray);
                }
                    return false;
            }

            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });




            return rootView;
    }


        public void refreshSearch(ArrayList<Player> resultingPlayers){
            final Player[] temp = new Player[resultingPlayers.size()];//.toArray((new Player[thisApp.playerArray.size()]));
            String[] itemname = new String[resultingPlayers.size()];

            Collections.sort(resultingPlayers, new Comparator<Player>() {
                @Override
                public int compare(Player lhs, Player rhs) {
                    return lhs.predictedPick-rhs.predictedPick;
                }
            });


            resultingPlayers.toArray(temp);
            itemname[0] = "blah";

            playerArrayAdapter = new CustomListAdapter(this.getActivity(),temp, itemname);


            resultList.setAdapter(playerArrayAdapter);
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
                        playerArrayAdapter.notifyDataSetChanged();

                    }else{

                        thisApp.undraft(thisApp.currentPlayer);

                        view.setBackgroundResource(R.drawable.roundedlayout_white);
                        playerArrayAdapter.notifyDataSetChanged();


                    }


                    return true;
                }
            });
    }

}
