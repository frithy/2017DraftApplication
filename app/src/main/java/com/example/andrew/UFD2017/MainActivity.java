package com.example.andrew.UFD2017;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.content.*;
import android.widget.Button;
import java.io.File;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    public CharSequence mTitle;
    final Context context = this;
    public UFApplication thisApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thisApp = (UFApplication) getApplicationContext();
        thisApp.MA = this;
        thisApp.playerArray =thisApp.populatePlayerList("PlayerDB.csv");
        thisApp.pick = thisApp.pickedPlayers(thisApp.playerArray)+1;
        thisApp.calcCustomValue();
        thisApp.updateProjections(thisApp.playerArray);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));





    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }
    public void onDestroy(){
        thisApp.savePlayerArray();
        super.onDestroy();
    }
    public void onSectionAttached(int number) {
        View rootView = null;
        FragmentManager fragmentManager = null;
        Fragment fragment = null;
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                fragment = new PlayerFragment();

                 break;
            case 3:
                mTitle = getString(R.string.title_section3);
                fragment = new SearchFragment();
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                fragment = new TeamFragment();
                break;
            case 5:
                mTitle = "Draft Order";
                fragment = new DraftFragment();
                break;
            case 6:
                mTitle = "Pre-Draft Ranking";
                fragment = new PreDraftFragment();
                break;
            case 7:
                mTitle = "Predicted Team";
                fragment = new PredictedTeam();
                break;
            case 8:
                mTitle = "Injured Players";
                fragment = new InjuredPlayers();
        }

        if(fragment!=null) {

            fragmentManager = getFragmentManager();

            fragmentManager.beginTransaction().setCustomAnimations(R.animator.slide_in_right,R.animator.slide_out_left).replace(R.id.container, fragment).commit();
        }


    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SettingsFragment fragment = new SettingsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public UFApplication thisApp;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ImageButton reloadButton = (ImageButton) rootView.findViewById(R.id.mp_Reload);

            reloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    thisApp.playerArray.clear();
                    try{
                        File file = new File(getActivity().getFilesDir(),"newplayerarray.txt");
                        file.delete();
                    }catch(Exception e){

                    }

                    thisApp.playerArray =thisApp.populatePlayerList("PlayerDB.csv");
                    thisApp.calcCustomValue();
                    thisApp.updateProjections(thisApp.playerArray);

                    String out = ("Database Reloaded: "+String.valueOf(thisApp.playerArray.size())+" Players loaded");
                    Toast.makeText(getActivity(), out, Toast.LENGTH_SHORT).show();
                    thisApp.pick =1;
                    //thisApp.preRankPlayers();

                }
            });


            ImageButton playerButton = (ImageButton) rootView.findViewById(R.id.mp_playerprofile);

            playerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayerFragment fragment = new PlayerFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                }
            });
            ImageButton teamButton = (ImageButton) rootView.findViewById(R.id.mp_team);

            teamButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog progress=new ProgressDialog(getActivity());
                    progress.setMessage("Finding a Star");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                    progress.setIndeterminate(false);
                    progress.setProgress(0);
                    progress.setMax(100);
                    progress.show();
                    thisApp.currentPlayer= thisApp.getBestOption().ID;
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = new PlayerFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_right).replace(R.id.container, fragment).commit();
                    Toast.makeText(getActivity(), "Getting a star.", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                    /*
                    TeamFragment fragment = new TeamFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();*/
                }
            });

            ImageButton searchButton = (ImageButton) rootView.findViewById(R.id.mp_search);

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchFragment fragment = new SearchFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                }
            });



            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));

            thisApp = (UFApplication) this.getActivity().getApplicationContext();


        }
    }



}



