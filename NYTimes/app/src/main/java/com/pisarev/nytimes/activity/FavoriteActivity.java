package com.pisarev.nytimes.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;

import com.pisarev.nytimes.Const;
import com.pisarev.nytimes.SQLite.MyDataBase;
import com.pisarev.nytimes.R;
import com.pisarev.nytimes.adapter.ResultListAdapter;
import com.pisarev.nytimes.model.Result;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private ArrayList<Result> favoriteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_favorite );
        dataBase = new MyDataBase( this );
        favoriteList = dataBase.getResultList();

        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
        recyclerView.setLayoutManager( new LinearLayoutManager( FavoriteActivity.this ) );
        recyclerView.setAdapter( new ResultListAdapter( FavoriteActivity.this, favoriteList, true ) );

        new ItemTouchHelper( new ItemTouchHelper.SimpleCallback( 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                dataBase.deleteItemResult( favoriteList.get( viewHolder.getAdapterPosition() ).getTitle() );
                favoriteList.remove( viewHolder.getAdapterPosition() );
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        } ).attachToRecyclerView( recyclerView );
        Const.isFavorites = true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            Const.isFavorites = false;
        return super.onKeyDown( keyCode, event );
    }
}
