package com.pisarev.nytimes.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.pisarev.nytimes.model.Result;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyDataBase extends SQLiteOpenHelper implements IMyDataBase {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyDataBase";
    private static final String TABLE_RESULT = "TableResult";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ABSTRACT = "abstract";
    private static final String KEY_SECTION = "section";
    private static final String KEY_IMAGE_URL = "image";
    private Context context;


    public MyDataBase(@Nullable Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABlE " + TABLE_RESULT + "("
                + KEY_TITLE + " text,"
                + KEY_ABSTRACT + " text,"
                + KEY_SECTION + " text,"
                + KEY_IMAGE_URL + " text)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_RESULT );
        onCreate( db );
    }

    @Override
    public ArrayList<Result> getResultList() {
        ArrayList<Result> resultArrayList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_RESULT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( selectQuery, null );
        if (cursor.moveToFirst()) {
            do {
                Result result = new Result();
                result.setTitle( cursor.getString( 0 ) );
                result.setAbstract( cursor.getString( 1 ) );
                result.setSection( cursor.getString( 2 ) );
                result.setImageString( cursor.getString( 3 ) );
                resultArrayList.add( result );
            } while (cursor.moveToNext());
        }
        return resultArrayList;
    }

    @Override
    public void addResult(Result result) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( KEY_TITLE, result.getTitle() );
        values.put( KEY_ABSTRACT, result.getAbstract() );
        values.put( KEY_SECTION, result.getSection() );
        values.put( KEY_IMAGE_URL, convertToString( result.getMedia().get( 0 ).getMediaMetadata().get( 2 ).getUrl() ) );
        db.insert( TABLE_RESULT, null, values );
        db.close();
    }

    @Override
    public void deleteItemResult(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete( TABLE_RESULT, KEY_TITLE + "=?", new String[]{value} );
    }

    private String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, os );
        byte[] byteArray = os.toByteArray();
        return Base64.encodeToString( byteArray, 0 );
    }

    public Bitmap convertToBitmap(String base64String) {
        byte[] decodedString = Base64.decode( base64String, Base64.DEFAULT );
        Bitmap bitmapResult = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length );
        return bitmapResult;
    }

    private String convertToString(String Url) {
        final String[] str = new String[1];
        Picasso.with( context ).load( Url ).into( new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                str[0] = convertToBase64( bitmap );
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        } );
        return str[0];
    }


}
