package com.pisarev.nytimes.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.pisarev.nytimes.Const;
import com.pisarev.nytimes.R;
import com.pisarev.nytimes.adapter.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager() );
        mViewPager = (ViewPager) findViewById( R.id.container );
        mViewPager.setAdapter( mSectionsPagerAdapter );
        TabLayout tabLayout = (TabLayout) findViewById( R.id.tabs );
        for (int i = 0; i < Const.SECTION.length; i++) {
            tabLayout.addTab( tabLayout.newTab().setText( Const.SECTION[i] ) );
        }
        mViewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );
        tabLayout.addOnTabSelectedListener( new TabLayout.ViewPagerOnTabSelectedListener( mViewPager ) );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.favorites) {
            Intent intent = new Intent( MainActivity.this, FavoriteActivity.class );
            MainActivity.this.startActivity( intent );
            return true;
        }
        if (id == R.id.exit) {
            System.exit( 0 );
            return true;
        }
        return super.onOptionsItemSelected( item );
    }
}
