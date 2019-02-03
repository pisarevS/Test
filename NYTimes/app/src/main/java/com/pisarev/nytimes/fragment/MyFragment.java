package com.pisarev.nytimes.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pisarev.nytimes.Const;
import com.pisarev.nytimes.R;
import com.pisarev.nytimes.adapter.ResultListAdapter;
import com.pisarev.nytimes.model.Result;
import com.pisarev.nytimes.model.ResultList;
import com.pisarev.nytimes.retrofit.RetroClient;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyFragment extends Fragment {

    private static ArrayList[] list = new ArrayList[Const.SECTION.length];
    private static int numberTab;
    private boolean[] isLoading = new boolean[Const.SECTION.length];
    private RecyclerView recyclerView;
    private Subscription[] subscription = new Subscription[Const.SECTION.length];
    private SwipeRefreshLayout swipeRefreshLayout;

    public MyFragment() {
    }

    public static MyFragment newInstance(int numberTab) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt( Const.NUMBER_TAB, numberTab );
        fragment.setArguments( args );
        for (int i = 0; i < Const.SECTION.length; i++)
            list[i] = new ArrayList<Result>();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Const.isFavorites=false;
        View rootView = inflater.inflate( R.layout.fragment_main, container, false );
        numberTab = getArguments().getInt( Const.NUMBER_TAB );
        if (savedInstanceState != null) {
            for (int i = 0; i < Const.SECTION.length; i++) {
                list[i] = savedInstanceState.getParcelableArrayList( Const.KEY_MODELS + i );
                isLoading[i] = savedInstanceState.getBoolean( Const.KEY_IS_LOADING + i );
            }
        }
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById( R.id.swipeRefreshLayout );
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RetroClient.resetModelsObservable(  );
                getModelsList( numberTab );
                swipeRefreshLayout.setRefreshing( false );
            }
        } );
        recyclerView = (RecyclerView) rootView.findViewById( R.id.recyclerView );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        recyclerView.setAdapter( new ResultListAdapter( getContext(), list[numberTab] ) );
        if (list[numberTab].size() == 0 || isLoading[numberTab]) {
            getModelsList( numberTab );
        }
        return rootView;
    }

    private void getModelsList(final int numberTab) {
        if (subscription[numberTab] != null && !subscription[numberTab].isUnsubscribed()) {
            subscription[numberTab].unsubscribe();
        }
        subscription[numberTab] = RetroClient.getModelsObservable( numberTab ).
                subscribeOn( Schedulers.io() ).
                observeOn( AndroidSchedulers.mainThread() ).
                subscribe( new Subscriber<ResultList>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d( Const.TAG, e.getMessage() );
                        Toast toast=Toast.makeText( getContext(),e.getMessage(),Toast.LENGTH_SHORT );
                        toast.show();
                    }

                    @Override
                    public void onNext(ResultList resultList) {
                        int prevSize = list[numberTab].size();
                        isLoading[numberTab] = false;
                        if (isAdded()) {
                            recyclerView.getAdapter().notifyItemRangeRemoved( 0, prevSize );
                        }
                        list[numberTab].clear();
                        list[numberTab].addAll( getSectionFilter( Const.SECTION[numberTab], resultList.getResults() ) );
                        if (isAdded()) {
                            recyclerView.getAdapter().notifyItemRangeInserted( 0, list[numberTab].size() );
                        }
                    }
                } );
    }

    private ArrayList<Result> getSectionFilter(String section, ArrayList<Result> list) {
        ArrayList<Result> temp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get( i ).getSection().equals( section ))
                temp.add( list.get( i ) );
        }
        return temp;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState( outState );
        if (!ResultListAdapter.isTouch) {
            for (int i = 0; i < Const.SECTION.length; i++) {
                outState.putParcelableArrayList( Const.KEY_MODELS + i, list[i] );
                outState.putBoolean( Const.KEY_IS_LOADING + i, isLoading[i] );
            }
        }
        ResultListAdapter.isTouch = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription[numberTab] != null && !subscription[numberTab].isUnsubscribed())
            subscription[numberTab].unsubscribe();
    }
}