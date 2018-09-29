package rs.fon.rateit.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pavlepavle on 17-Jun-17.
 */

public class StorageDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "rateit.db";

    public StorageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (Username text primary key, Email text, Password text, Admin integer);");
        db.execSQL("CREATE TABLE \"movies\" ( `MovieID` INTEGER PRIMARY KEY AUTOINCREMENT, `Title` text, `Year` integer );");
        db.execSQL("CREATE TABLE \"ratings\" ( `User` TEXT, `Movie` INTEGER, `Rating` INTEGER, PRIMARY KEY(`User`,`Movie`), FOREIGN KEY(`User`) REFERENCES `users`(`Username`), FOREIGN KEY(`Movie`) REFERENCES `movies`(`MovieID`) );");
        //db.execSQL("CREATE TABLE ratings (RatingID INTEGER PRIMARY KEY AUTOINCREMENT, User text, Movie INTEGER, Rating INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS movies");
        db.execSQL("DROP TABLE IF EXISTS ratings");
        onCreate(db);
    }
}