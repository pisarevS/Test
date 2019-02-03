package com.pisarev.nytimes.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pisarev.nytimes.Const;
import com.pisarev.nytimes.R;
import com.pisarev.nytimes.SQLite.MyDataBase;
import com.pisarev.nytimes.model.Result;
import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {

    private TextView textTitle, textAbstract;
    private ImageView imageView, favorite;
    private Result result;
    private boolean isTouchFavorite;
    private MyDataBase dataBase;
    private int position;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detailed );
        dataBase = new MyDataBase( this );
        position = getIntent().getExtras().getInt( Const.POSITION );
        result = getIntent().getExtras().getParcelable( Const.RESULT );

        textTitle = (TextView) findViewById( R.id.textTitle );
        textAbstract = (TextView) findViewById( R.id.textAbstract );
        imageView = (ImageView) findViewById( R.id.imageView );

        if (!Const.isFavorites) {
            result = getIntent().getExtras().getParcelable( Const.RESULT );
            favorite = (ImageView) findViewById( R.id.favorites );
            favorite.setImageResource( R.drawable.ic_favorite_border_black_24dp );
            favorite.setOnTouchListener( new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (!isTouchFavorite) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                favorite.setImageResource( R.drawable.ic_favorite_black_24dp );
                                isTouchFavorite = true;
                                dataBase.addResult( result );
                                Toast toast=Toast.makeText(getApplicationContext() ,"Add to Favorites",Toast.LENGTH_SHORT );
                                toast.show();
                                break;
                        }
                    }
                    return true;
                }
            } );
            Picasso.with( DetailedActivity.this )
                    .load( result.getMedia().get( 0 ).getMediaMetadata().get( 2 ).getUrl() )
                    .placeholder( R.drawable.ic_launcher_foreground )
                    .error( R.drawable.ic_launcher_foreground )
                    .into( imageView );
        } else {
            result = dataBase.getResultList().get( position );
            imageView.setImageBitmap( dataBase.convertToBitmap( result.getImageString() ) );
        }
        textTitle.setText( result.getTitle() );
        textAbstract.setText( result.getAbstract() );
    }
}
