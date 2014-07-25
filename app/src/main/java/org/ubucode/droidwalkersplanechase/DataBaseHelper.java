package org.ubucode.droidwalkersplanechase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jon on 7/20/2014.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private Context mycontext;

    private String DB_PATH;
    private static String DB_NAME = "planes.db";//the extension may be .sqlite or .db

    // PlaneCards table name
    private static final String TABLE_PLANES = "planes";

    // Plane Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "cardName";
    private static final String KEY_ETEXT = "cardEffectText";
    private static final String KEY_CTEXT = "cardChaosText";

    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_ETEXT,KEY_CTEXT};
    public SQLiteDatabase myDataBase;

    public DataBaseHelper(Context context) throws IOException {
        super(context,DB_NAME,null,1);
        this.mycontext=context;
        DB_PATH = mycontext.getApplicationInfo().dataDir + "/databases/";
        boolean dbexist = checkdatabase();
        if (dbexist) {
            //System.out.println("Database exists");
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if(dbexist) {
            //System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {
        //SQLiteDatabase checkdb = null;
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            //checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = mycontext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte from inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    public void addPlaneCard(Card plane)
    {
        //for logging
        Log.d("addPlane", plane.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, plane.getName()); // get name
        values.put(KEY_ETEXT, plane.getEffect()); // get effect text
        values.put(KEY_CTEXT, plane.getChaos()); // get chaos text

        // 3. insert
        db.insert(TABLE_PLANES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Card getPlaneCard(int id)
    {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_PLANES, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build PlaneCard object
        Card plane = new Card();
        plane.setId(Integer.parseInt(cursor.getString(0)));
        plane.setName(cursor.getString(1));
        plane.setEffect(cursor.getString(2));
        plane.setChaos(cursor.getString(3));
        plane.setPlaneType(cursor.getString(4));
        plane.setImgPath(cursor.getString(5));

        //log
        Log.d("getPlane("+id+")", plane.toString());

        // 5. return plane
        return plane;
    }

    public List<Card > getAllPlanes()
    {
        List<Card> planes = new LinkedList<Card>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_PLANES;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.myDataBase;
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build plane and add it to list
        Card plane = null;
        if (cursor.moveToFirst()) {
            do {
                plane = new Card();
                plane.setId(Integer.parseInt(cursor.getString(0)));
                plane.setName(cursor.getString(1));
                plane.setEffect(cursor.getString(2));
                plane.setChaos(cursor.getString(3));
                plane.setPlaneType(cursor.getString(4));
                plane.setImgPath(cursor.getString(5));
                // Add plane to planes
                planes.add(plane);
            } while (cursor.moveToNext());
        }

        Log.d("getAllPlanes()", planes.toString());
        Collections.shuffle(planes);
        // return planes
        return planes;
    }


    public int update(Card plane)
    {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.myDataBase;

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("cardName", plane.getName()); // get title
        values.put("cardEffectText", plane.getEffect()); // get effect
        values.put("cardChaosText", plane.getChaos()); // get chaos
        values.put("planeType", plane.getPlaneType());

        // 3. updating row
        int i = db.update(TABLE_PLANES, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(plane.getId()) }); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deletePlane(Card plane)
    {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.myDataBase;

        // 2. delete
        db.delete(TABLE_PLANES, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(plane.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deletePlane", plane.toString());
    }


}
