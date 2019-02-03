package com.pisarev.nytimes.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pisarev.nytimes.Const;
import com.pisarev.nytimes.R;
import com.pisarev.nytimes.SQLite.MyDataBase;
import com.pisarev.nytimes.activity.DetailedActivity;
import com.pisarev.nytimes.model.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ResultListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static boolean isTouch;
    private ArrayList<Result> results;
    private Context context;
    private MyDataBase dataBase;
    private boolean isFavorite;

    public ResultListAdapter(Context context, ArrayList<Result> results) {
        this.results = results;
        this.context = context;
    }

    public ResultListAdapter(Context context, ArrayList<Result> results, boolean isFavorite) {
        this.results = results;
        this.context = context;
        this.isFavorite = isFavorite;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_row_view, parent, false );
        return new ViewHolderHotel( v );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderHotel holderHotel = (ViewHolderHotel) holder;
        final Result result = results.get( position );
        dataBase = new MyDataBase( context );

        holderHotel.title.setText( result.getTitle() );
        holderHotel.section.setText( result.getSection() );
        if (!isFavorite) {
            Picasso.with( context )
                    .load( result.getMedia().get( 0 ).getMediaMetadata().get( 2 ).getUrl() )
                    .placeholder( R.mipmap.ic_launcher )
                    .error( R.mipmap.ic_launcher )
                    .into( holderHotel.imageView );
        } else {
            holderHotel.imageView.setImageBitmap( dataBase.convertToBitmap( result.getImageString() ) );
        }
        holderHotel.cardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTouch = true;
                Intent intent = new Intent( context, DetailedActivity.class )
                        .putExtra( Const.RESULT, result )
                        .putExtra( Const.POSITION, position );
                context.startActivity( intent );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ViewHolderHotel extends RecyclerView.ViewHolder {

        public TextView title, section;
        public ImageView imageView;
        public LinearLayout linearLayout;
        public CardView cardView;

        public ViewHolderHotel(View v) {
            super( v );
            section = (TextView) v.findViewById( R.id.textViewSection );
            title = (TextView) v.findViewById( R.id.textViewTitle );
            imageView = (ImageView) v.findViewById( R.id.imageView );
            linearLayout = (LinearLayout) v.findViewById( R.id.linearLayout );
            cardView = (CardView) v.findViewById( R.id.cardView );
        }

    }
}
