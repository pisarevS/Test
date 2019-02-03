package com.pisarev.nytimes.retrofit;

import com.pisarev.nytimes.model.ResultList;

import retrofit2.http.GET;
import rx.Observable;


public interface Api
{
    @GET("/svc/mostpopular/v2/emailed/30.json?api-key=Z9mGvV6tpYpeHhct8pxjzJACMSH3CDgf")
    Observable<ResultList> getJsonEmailed();

    @GET("https://api.nytimes.com/svc/mostpopular/v2/shared/30.json?api-key=Z9mGvV6tpYpeHhct8pxjzJACMSH3CDgf")
    Observable<ResultList>getJsonShared();

    @GET("https://api.nytimes.com/svc/mostpopular/v2/viewed/30.json?api-key=Z9mGvV6tpYpeHhct8pxjzJACMSH3CDgf")
    Observable<ResultList>getJsonViewed();
}

