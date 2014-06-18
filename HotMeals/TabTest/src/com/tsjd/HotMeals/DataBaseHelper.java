package com.tsjd.HotMeals;

/**
 * 
 * @author Daniel Roeven
 * @author Sander van Dam
 * @author Timo van Niedek
 * @author Jaco Schalij
 * @version 0.5
 *
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
	 
    //The Android's default system path of your application database.
    private String dbPath;
 
    private static String DB_NAME = "recepten";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
 
    /**
     * Constructor
     * Initalizes the DataBasePath
     * @param context
     */
    public DataBaseHelper(Context context) {
    	
    	super(context, DB_NAME, null, 1);
    	
        this.myContext = context;
        dbPath = context.getDatabasePath(DB_NAME).getPath();
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with the Database in the assets folder.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    		
    	}else{
 
    		//By calling this method an empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase().close();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error(e);
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = dbPath;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    		Log.d("DataBaseHelper", "Database exists");
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
    		Log.d("DataBaseHelper", "Database doesnt exist");
			
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies the database from the assets-folder to the just created empty database in the
     * system folder.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	try {
			//Open the local db as the input stream
			InputStream myInput = myContext.getAssets().open(DB_NAME);
 
			// Path to the just created empty db
			String outFileName = dbPath;
			
			Log.d("DataBaseHelper outfile", outFileName);
 
			//Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outFileName);
 
			//transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer))>0){
				myOutput.write(buffer, 0, length);
			}
 
			//Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (IOException e) {
			throw new Error(e);
		}
 
    }
 
    /**
     * Opens the database so it can be read and written.
     * @throws SQLException
     */
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = dbPath;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
    
    /**
     * a simple getter-function for the database.
     * @return the SQLiteDatabase
     */
    public SQLiteDatabase getDatabase()
    {
    	return myDataBase;
    }
 
    
    /**
     * A simple helper function to close te database
     */
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
    	    
    	    throw new Error("shouldn't be closed");
 
    	    //super.close();
 
	}

    /**
     * A function to change the boolean value of the favourite column at a particular row
     * @param favourite boolean value: favourite or not favourite
     * @param idee the row-number
     */
    void changeFavourite(boolean favourite, int idee){
    	String Query;
    	//Build a Query
    	if (favourite){
    		Query = "UPDATE HotMeals SET Favorite='0' WHERE ID="+idee;
    	}
    	else{
    		Query = "UPDATE HotMeals SET Favorite='1' WHERE ID="+idee;
    	}
    	try{
    		//Execute the previous mentioned query
    		myDataBase.execSQL(Query);
    	}catch (Exception e){
    		throw e;
    	}
    }
    
    /**
     * A function to update the TimeViewed column in the HotMeals table at a particular row
     * @param time the new value of the TimeViewed column
     * @param ID the row-number
     */
    public void updateTimeViewed(int time, int ID){
    	String Query;
    	Query = "UPDATE HotMeals SET TimeViewed=" + time + " WHERE ID=" + ID;
    	Log.d("UpdateTime", "Time is: " + time + " for ID " + ID);
    	try{
    		myDataBase.execSQL(Query);
    	}catch (Exception e){
    		throw e;
    	}
    }
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
       
}