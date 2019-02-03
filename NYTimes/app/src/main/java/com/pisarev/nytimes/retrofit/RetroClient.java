package com.pisarev.nytimes.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pisarev.nytimes.Const;
import com.pisarev.nytimes.model.ResultList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class RetroClient {

    private static Observable[] observableRetrofit = new Observable[Const.SECTION.length];
    private static BehaviorSubject[] observableModelsList = new BehaviorSubject[Const.SECTION.length];
    private static Subscription[] subscription = new Subscription[Const.SECTION.length];

    public RetroClient() {
    }

    public static void init() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler( Schedulers.io() );
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( Const.BASE_URL )
                .addConverterFactory( GsonConverterFactory.create( gson ) )
                .addCallAdapterFactory( rxAdapter )
                .build();

        Api apiService = retrofit.create( Api.class );

        observableRetrofit[0] = apiService.getJsonEmailed();
        observableRetrofit[1] = apiService.getJsonShared();
        observableRetrofit[2] = apiService.getJsonViewed();
    }

    public static void resetModelsObservable() {
        for (int i = 0; i < Const.SECTION.length; i++) {
            observableModelsList[i] = BehaviorSubject.create();
            if (subscription[i] != null && !subscription[i].isUnsubscribed()) {
                subscription[i].unsubscribe();
            }
            final int finalI = i;
            subscription[i] = observableRetrofit[i].subscribe( new Subscriber<ResultList>() {
                @Override
                public void onCompleted() { }

                @Override
                public void onError(Throwable e) {
                    observableModelsList[finalI].onError( e );
                }

                @Override
                public void onNext(ResultList list) {
                    observableModelsList[finalI].onNext( list );
                }
            } );
        }
    }

    public static Observable<ResultList> getModelsObservable(int numberTab) {
        if (observableModelsList[numberTab] == null) {
            resetModelsObservable( );
        }
        return observableModelsList[numberTab];
    }
}

