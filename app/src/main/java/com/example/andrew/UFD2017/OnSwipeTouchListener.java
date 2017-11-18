package com.example.andrew.UFD2017;

/**
 * Created by Andrew on 12/02/2016.
 */
import android.app.FragmentManager;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


public class OnSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector;
    public UFApplication thisApp;
    public MainActivity MA;
    public OnSwipeTouchListener (Context ctx, UFApplication thisApp_con){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
        this.thisApp = thisApp_con;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        public UFApplication thisApp;
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    result = true;
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                }
                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {




            thisApp.currentPlayer= thisApp.getLowerID(thisApp.currentPlayer);
            FragmentManager fm = thisApp.MA.getFragmentManager();

            fm.beginTransaction().setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left).replace(R.id.container, (new EmptyFragment())).commit();
            fm.beginTransaction().setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left).replace(R.id.container, (new PlayerFragment())).commit();




    }

    public void onSwipeLeft() {


            thisApp.currentPlayer= thisApp.getHigherID(thisApp.currentPlayer);

            FragmentManager fm = thisApp.MA.getFragmentManager();

            fm.beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right).replace(R.id.container, (new EmptyFragment())).commit();
            fm.beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right).replace(R.id.container, (new PlayerFragment())).commit();


    }

    public void onSwipeTop() {
        FragmentManager fm = thisApp.MA.getFragmentManager();
       if(thisApp.getPlayer(thisApp.currentPlayer).available){

           thisApp.setOnTeam();


           //this.thisApp.currentPlayer = this.thisApp.mostValuedPlayer();
           fm.beginTransaction().replace(R.id.container, (new EmptyFragment())).commit();
           fm.beginTransaction().replace(R.id.container, (new PlayerFragment())).commit();


    }else{

           thisApp.undraft(thisApp.currentPlayer);

           fm.beginTransaction().replace(R.id.container, (new EmptyFragment())).commit();
           fm.beginTransaction().replace(R.id.container, (new PlayerFragment())).commit();
       }


    }

    public void onSwipeBottom() {
        FragmentManager fm = thisApp.MA.getFragmentManager();

        if(thisApp.getPlayer(thisApp.currentPlayer).available){
            thisApp.setUnavailable();

            //this.thisApp.currentPlayer = this.thisApp.mostValuedPlayer();
            fm.beginTransaction().replace(R.id.container, (new EmptyFragment())).commit();
            fm.beginTransaction().replace(R.id.container, (new PlayerFragment())).commit();


        }else{

            thisApp.undraft(thisApp.currentPlayer);
            fm.beginTransaction().replace(R.id.container, (new EmptyFragment())).commit();
            fm.beginTransaction().replace(R.id.container, (new PlayerFragment())).commit();




        }

    }
}