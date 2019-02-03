package com.pisarev.nytimes.SQLite;

import com.pisarev.nytimes.model.Result;

import java.util.ArrayList;

public interface IMyDataBase {

    ArrayList<Result> getResultList();

    void addResult(Result result);

    void deleteItemResult(String value);
}

