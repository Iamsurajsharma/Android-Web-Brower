package com.example.radheshyam.mybrowser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Radhe Shyam on 10/26/2017.
 */

public class databaseAdapter   {

    database database;
    public  databaseAdapter (Context context){

        database = new database(context);

    }
       public  long insertdata(String name){

        SQLiteDatabase db =   database.getWritableDatabase();
           ContentValues contentValues= new ContentValues();
           contentValues.put(database.NAME,name);

        long id =   db.insert(database.Table_Name,null,contentValues);
            return id;

       }
       public void create(){
           SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

       }
          public String getdata(){


              SQLiteDatabase db = database.getWritableDatabase();
              String[] columns= {database.UID,database.NAME};
             Cursor cursor= db.query(database.Table_Name,columns,null,null,null,null,null );
             StringBuffer buffer = new StringBuffer();
              while(cursor.moveToNext()){

              int cid =cursor.getInt(0);
                  String name = cursor.getString(1);

                  buffer.append(cid+ " " +name+ "\n");
              }
              return buffer.toString();
          }


          public void deletedata(){

              SQLiteDatabase db = database.getWritableDatabase();
              db.delete(database.Table_Name,null,null);
          }

        static class database extends SQLiteOpenHelper{

         private static final String DataBase_Name ="DataBase";
         private static final String Table_Name = "DataBase_Table";
         private static final int DataBase_version =12;
         private static final String UID  ="_id";
         private static final String NAME ="Name";
         private  Context context;
         private static final String CREATE_TABLE= "CREATE TABLE "+Table_Name+
                 " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+NAME+" VARCHAR(255));";
         private static  final String DROP_TABLE = "DROP TABLE IF EXISTS "+Table_Name;

         public database(Context context){
             super(context,DataBase_Name,null,DataBase_version);

             this.context =context;

             Toast.makeText(context, "CONSTRUCTING",Toast.LENGTH_LONG).show();

         }

         @Override
         public void onCreate(SQLiteDatabase db) {

             Toast.makeText(context, "CREATED",Toast.LENGTH_LONG).show();


             // CREATING TABLE
             try {
                 db.execSQL(CREATE_TABLE);


             }catch (SQLException e){
                 Toast.makeText(context, ""+e,Toast.LENGTH_LONG).show();

             }
         }

         @Override
         public void onUpgrade(SQLiteDatabase db, int i, int i1) {

             Toast.makeText(context, "deleting",Toast.LENGTH_LONG).show();

             //DELETING TABLE

             try {
                 db.execSQL(DROP_TABLE);


                 onCreate(db);

             }catch (SQLException e){

                 Toast.makeText(context, ""+e,Toast.LENGTH_LONG).show();



             }
         }

     }
}
