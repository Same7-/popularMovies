package com.udacity.same7.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity implements
        MainFragment.OnMainFragmentInteractionListener,
        DetailFragment.OnDetailFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title);
        setSupportActionBar(toolbar);

        View towPaneView = findViewById(R.id.two_pane);
        boolean isDualPane = towPaneView != null && towPaneView.getVisibility() == View.VISIBLE;
        if(savedInstanceState==null&&!isDualPane) {
            MainFragment mainFragment = new MainFragment();
            getFragmentManager().beginTransaction().add(R.id.main_frame, mainFragment).commit();

        }

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }


    @Override
    public void onFragmentInteraction(Bundle bundle) {

    }
}
