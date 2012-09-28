package in.edureka.bookshopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDatabaseHandler extends SQLiteOpenHelper {
	
	// All Static Variables
	// For debug only
	private static final String TAG = "UserDBHandler";
	
	// Database Version
	private static final int DATABASE_VERSION = 1;
	
	// Database Name
	private static final String DATABASE_NAME = "usersManager";

	// Table Name
	private static final String TABLE_USERS = "users";
	
	// Users Table Column Names
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_FULLNAME = "fullname";
	
	public UserDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
				+ KEY_USERNAME + " TEXT PRIMARY KEY,"
				+ KEY_PASSWORD + " TEXT,"
				+ KEY_FULLNAME + " TEXT" + ");";
		db.execSQL(CREATE_USERS_TABLE);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if there exists one
		db.execSQL("DROP TABLE IF EXISTS;");
       
		// Recreate the table
       onCreate(db);
	}
	
	// Create, Read, Update and Delete [CRUD] methods follow

	/**
	 * Add new user
	 * @param user Add this user to Users table
	 * @return true successful addition, false addition failure
	 */
	public boolean addShopUser(ShopUser user)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_USERNAME, user.get_userName()); // Username aka. email id
		values.put(KEY_PASSWORD, user.get_password()); // Password
		values.put(KEY_FULLNAME, user.get_fullName()); // Full Name
		
		// Insert row
		long rowId = db.insert(TABLE_USERS, null, values);
		db.close(); // Closing database connection
		
		if (rowId < 0)
			return false;
		
		return true;
	}
	
	// Get user data
	public ShopUser readShopUser(String userName)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_USERS, new String [] {KEY_USERNAME,
				KEY_PASSWORD, KEY_FULLNAME }, KEY_USERNAME + "=?", 
				new String[] { userName }, null, null, null, null);
		
		ShopUser user = null;
		
		if (cursor != null)
		{
			cursor.moveToFirst();
			if (cursor.getCount() > 0)
				user = new ShopUser(cursor.getString(0),
						cursor.getString(1),cursor.getString(2));
		}
		
		db.close();
		return user;
	}
	
	// Update User data
	public int updateShopUser(ShopUser user)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_PASSWORD, user.get_password());
		values.put(KEY_FULLNAME, user.get_fullName());
		
		// updating row
		int result = db.update(TABLE_USERS, values, 
				KEY_USERNAME + "=?", new String[] { user.get_userName() });
		db.close();
		
		return result;
	}
	
	// Delete user data
	public void deleteShopUserData(ShopUser user)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete(TABLE_USERS, KEY_USERNAME + "=?", 
				new String[] { user.get_userName() });
		db.close();
	}

	// Get User Count
	public int getShopUserCount()
	{
		String countQuery = " SELECT COUNT * FROM " + TABLE_USERS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		db.close();
		
		// return count
		return cursor.getCount();
	}
	
	/**
	 * Check if User credentials are present in User Table
	 * @param user
	 * @return true valid credentials, false invalid credentials
	 */
	public boolean isValidLogin(ShopUser user)
	{
		Boolean matchResult = false;
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_USERS, new String [] { KEY_USERNAME,
				KEY_PASSWORD, KEY_FULLNAME }, 
				KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?", 
				new String[] { user.get_userName(), user.get_password() }, 
				null, null, null, null);
		
		if (cursor != null)
		{
			if(cursor.getCount() > 0)
			{
				matchResult = true;
				cursor.moveToFirst();
				if (user.get_password().compareTo(cursor.getString(1)) == 0)
				{
					user = new ShopUser(cursor.getString(0),cursor.getString(1),
						cursor.getString(2));
					user.set_passwordFailedValidation(false);
				}
				else
					user.set_passwordFailedValidation(true);
				
				Log.w(TAG, "Got: " + cursor.getString(0) + " " + cursor.getString(1) +
						" " + cursor.getString(2));
				
			}
		}
		
		db.close();
		
		return matchResult;
	}
}
