package com.application.banque.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.application.banque.models.Check;
import com.application.banque.models.Transaction;
import com.application.banque.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_db";
    private static final int DATABASE_VERSION = 3;

    // User table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_TYPE_OF_ACCOUNT = "type_of_account";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_STATUS = "status";
    public static final String COLUMN_BALANCE = "balance";



    // Constants for Check table
    public static final String TABLE_CHECKS = "checks";
    public static final String COLUMN_CHECK_ID = "check_id";
    public static final String COLUMN_USERNAME_CHECK = "username_check";
    public static final String COLUMN_AMOUNT_CHECK = "amount_check";
    public static final String COLUMN_STATUS_CHECK = "status_check";



    // Table name for transactions
    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COLUMN_TRANSACTION_ID = "transaction_id";
    public static final String COLUMN_TRANSACTION_USERNAME = "username";
    public static final String COLUMN_TRANSACTION_AMOUNT = "amount";
    public static final String COLUMN_TRANSACTION_TYPE = "type";


    private static final String CREATE_TABLE_CHECKS = "CREATE TABLE " + TABLE_CHECKS + " ("
            + COLUMN_CHECK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERNAME_CHECK + " TEXT, "
            + COLUMN_AMOUNT_CHECK + " REAL, "
            + COLUMN_STATUS_CHECK + " TEXT"+
            ")";
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_LAST_NAME + " TEXT," +
                    COLUMN_ADDRESS + " TEXT," +
                    COLUMN_PHONE_NUMBER + " TEXT," +
                    COLUMN_TYPE_OF_ACCOUNT + " TEXT," +
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_STATUS + " TEXT," +
                    COLUMN_BALANCE + " REAL DEFAULT 0.0" +
                    ")";

    private static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " + TABLE_TRANSACTIONS +
            "(" + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TRANSACTION_USERNAME + " TEXT," +
            COLUMN_TRANSACTION_AMOUNT + " REAL," +
            COLUMN_TRANSACTION_TYPE + " TEXT)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CHECKS);
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_ADDRESS, user.getAddress());
        values.put(COLUMN_PHONE_NUMBER, user.getPhoneNumber());
        values.put(COLUMN_TYPE_OF_ACCOUNT, user.getTypeOfAccount());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_STATUS, user.getStatus());
        values.put(COLUMN_BALANCE, user.getBalance());

        long newRowId = db.insert(TABLE_USERS, null, values);

        db.close();
        return newRowId;
    }

    public void updateUserBalance(String username, double newBalance) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BALANCE, newBalance);

        db.update(TABLE_USERS, values, COLUMN_NAME + "=?", new String[]{username});
        db.close();
    }
    public void updateUserBalanceByPhoneNumber(String phoneNumber, double newBalance) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BALANCE, newBalance);

        db.update(TABLE_USERS, values, COLUMN_PHONE_NUMBER + "=?", new String[]{phoneNumber});
        db.close();
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") User user = new User(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_OF_ACCOUNT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE))
            );
            userList.add(user);
        }

        cursor.close();
        db.close();
        return userList;
    }
    public void updateUserStatus(long userId, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, status);

        db.update(TABLE_USERS, values, COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
        db.close();
    }

    public void deleteUser(long userId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
        db.close();
    }
    public List<User> getPendingUsers() {
        List<User> pendingUsers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                null,
                COLUMN_STATUS + "=?",
                new String[]{"Pending"},
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            @SuppressLint("Range") User user = new User(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_OF_ACCOUNT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE))

            );
            pendingUsers.add(user);
        }

        cursor.close();
        db.close();

        return pendingUsers;
    }

    @SuppressLint("Range")
    public User getUserByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(
                TABLE_USERS,
                null,
                COLUMN_NAME + "=?",
                new String[]{name},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_OF_ACCOUNT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE))

            );
        }

        cursor.close();
        db.close();

        return user;
    }
    public boolean isValidUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_NAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);

        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();

        return isValid;
    }

    public void updateUserProfile(String username, String newName, String newLastName, String newAddress, String newPhoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, newName);
        values.put(COLUMN_LAST_NAME, newLastName);
        values.put(COLUMN_ADDRESS, newAddress);
        values.put(COLUMN_PHONE_NUMBER, newPhoneNumber);

        db.update(TABLE_USERS, values, COLUMN_NAME + "=?", new String[]{username});
        db.close();
    }

    public List<String> getAllAccountsByPhoneNumber(String phoneNumber) {
        List<String> accountsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " + COLUMN_TYPE_OF_ACCOUNT +
                " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_PHONE_NUMBER + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{phoneNumber});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String accountType = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_OF_ACCOUNT));
                accountsList.add(accountType);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return accountsList;
    }
    @SuppressLint("Range")
    public User getuserByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(
                TABLE_USERS,
                null,
                COLUMN_PHONE_NUMBER + "=?",
                new String[]{phoneNumber},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_OF_ACCOUNT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE))
            );
        }

        cursor.close();
        db.close();

        return user;
    }


    @SuppressLint("Range")
    public double getSumOfBalancesByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT SUM(" + COLUMN_BALANCE + ") AS total_balance " +
                " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_PHONE_NUMBER + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{phoneNumber});
        double totalBalance = 0.0;
        if (cursor.moveToFirst()) {
            totalBalance = cursor.getDouble(cursor.getColumnIndex("total_balance"));
        }

        cursor.close();
        db.close();

        return totalBalance;
    }

    //______________________________________

    public long addCheckRequest(String username, double amount, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME_CHECK, username);
        values.put(COLUMN_AMOUNT_CHECK, amount);
        values.put(COLUMN_STATUS_CHECK, status);

        long newRowId = db.insert(TABLE_CHECKS, null, values);
        db.close();
        return newRowId;
    }

    public List<Check> getAllCheckRequests() {
        List<Check> checkList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CHECKS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Check check = new Check(
                cursor.getLong(cursor.getColumnIndex(COLUMN_CHECK_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME_CHECK)),
                cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT_CHECK)),
                cursor.getString(cursor.getColumnIndex(COLUMN_STATUS_CHECK))
                );
                checkList.add(check);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return checkList;
    }
    public void updateCheckStatus(long checkId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS_CHECK, newStatus);

        db.update(TABLE_CHECKS, values, COLUMN_CHECK_ID + "=?", new String[]{String.valueOf(checkId)});
        db.close();
    }

    public List<User> getUsersWithCheckRequests() {
        List<User> usersList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT DISTINCT u.* FROM " + TABLE_USERS + " u INNER JOIN " +
                TABLE_CHECKS + " c ON u." + COLUMN_NAME + " = c." + COLUMN_USERNAME_CHECK +
                " WHERE c." + COLUMN_STATUS_CHECK + " = 'Pending'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
                @SuppressLint("Range") String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
                @SuppressLint("Range") String typeOfAccount = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_OF_ACCOUNT));
                @SuppressLint("Range") String password = "********";
                @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));
                @SuppressLint("Range") double balance = cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE));


                User user = new User(id, name, lastName, address, phoneNumber, typeOfAccount ,password ,status ,balance);
                usersList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return usersList;
    }

    public boolean userExists(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TABLE_USERS +
                    " WHERE " + COLUMN_PHONE_NUMBER + " = ?";
            cursor = db.rawQuery(query, new String[]{phoneNumber});

            return cursor != null && cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public long addTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TRANSACTION_USERNAME, transaction.getUsername());
        values.put(COLUMN_TRANSACTION_AMOUNT, transaction.getAmount());
        values.put(COLUMN_TRANSACTION_TYPE, transaction.getType());

        long newRowId = db.insert(TABLE_TRANSACTIONS, null, values);
        db.close();
        return newRowId;
    }

    @SuppressLint("Range")
    public double getUserBalance(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        double balance = 0.0;

        String selectQuery = "SELECT " + COLUMN_BALANCE + " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_NAME + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{username});

        if (cursor.moveToFirst()) {
            balance = cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE));
        }

        cursor.close();
        db.close();

        return balance;
    }

    @SuppressLint("Range")
    public Check getCheckById(long checkId) {
        SQLiteDatabase db = getReadableDatabase();
        Check check = null;

        Cursor cursor = db.query(
                TABLE_CHECKS,
                null,
                COLUMN_CHECK_ID + "=?",
                new String[]{String.valueOf(checkId)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            check = new Check(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_CHECK_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME_CHECK)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT_CHECK)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_STATUS_CHECK))
            );
        }

        cursor.close();
        db.close();

        return check;
    }


}

