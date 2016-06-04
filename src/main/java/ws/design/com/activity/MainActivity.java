package ws.design.com.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ws.design.com.R;

public class MainActivity extends AppCompatActivity  {


    ListView list;


    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment myFragment = new HomeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container_body, new HomeFragment(), "one").commit();


        String[] web = MainActivity.this.getResources().getStringArray(R.array.nav_drawer_labels);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Integer[] imageId = {
                R.drawable.ic_home,
        };



       CustomList adapter1 = new
               CustomList(MainActivity.this, web, imageId);
        list =(ListView)findViewById(R.id.list);
        list.setAdapter(adapter1);
        list.bringToFront();
        mDrawerLayout.requestLayout();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Fragment myFragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                switch( position )
                {
                    case 0:
                        if(fragmentManager.findFragmentByTag("one") != null) {
                            //if the fragment exists, show it.
                            mDrawerLayout.closeDrawers();
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("one")).commit();
                        } else {
                            //if the fragment does not exist, add it to fragment manager.
                            mDrawerLayout.closeDrawers();
                            fragmentManager.beginTransaction().add(R.id.container_body, new HomeFragment(), "one").commit();
                        }
                        if(fragmentManager.findFragmentByTag("two") != null){
                            //if the other fragment is visible, hide it.
                            mDrawerLayout.closeDrawers();
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("two")).commit();
                        }
                        break;
                }

            }
        });
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

    }


}