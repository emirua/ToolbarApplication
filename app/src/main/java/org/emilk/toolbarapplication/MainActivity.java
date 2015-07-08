package org.emilk.toolbarapplication;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Frag1.OnFragmentInteractionListener,Frag2.OnFragmentInteractionListener, Frag3.OnFragmentInteractionListener, Frag4.OnFragmentInteractionListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) openDynamicFragment();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar mtoolbar = getSupportActionBar();
        mtoolbar.setTitle("Toolbar as Actionbar");
        mtoolbar.setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(navView);
        drawerToggle = setupDrawerToggle();

    }

    private ActionBarDrawerToggle setupDrawerToggle() {

        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open,  R.string.drawer_close);

    }

    public void openDynamicFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_container, new Frag1() ).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Sync the drawerToggle after different actions
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (drawerToggle.onOptionsItemSelected(item)) {

            return true;

        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "No settings", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_search) {
            Toast.makeText(MainActivity.this, "Busca luego", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_something) {
            Toast.makeText(MainActivity.this, "Te escucho", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_play) {
            Toast.makeText(MainActivity.this, "Estoy haciendo algo", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override

                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        selectDrawerItem(menuItem);

                        return true;

                    }

                });

    }

    public void selectDrawerItem(MenuItem menuItem) {

        Fragment fragment = null;

        Class fragmentClass;

        switch (menuItem.getItemId()) {

            case R.id.action_invite:

                fragmentClass = Frag1.class;

                break;

            case R.id.action_share:

                fragmentClass = Frag2.class;

                break;

            case R.id.action_buy:

                fragmentClass = Frag3.class;

                break;
            case R.id.action_sell:

                fragmentClass = Frag4.class;

                break;

            default:

                fragmentClass = Frag1.class;

        }


        try {

            fragment = (Fragment) fragmentClass.newInstance();

        } catch (Exception e) {

            e.printStackTrace();

        }
        //Initialize transaction
        FragmentTransaction fT = getSupportFragmentManager().beginTransaction();

        fT.replace(R.id.frag_container, fragment).commit();

        menuItem.setChecked(true);

        setTitle(menuItem.getTitle());

        drawer.closeDrawers();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
