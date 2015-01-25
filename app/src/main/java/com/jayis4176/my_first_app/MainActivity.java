package com.jayis4176.my_first_app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_HEIGHT,
            MySQLiteHelper.COLUMN_WEIGHT,
            MySQLiteHelper.COLUMN_BMI
    };

    public final static String EXTRA_NAME = "com.jayis4176.my_first_app.NAME";
    public final static String EXTRA_HEIGHT = "com.jayis4176.my_first_app.H";
    public final static String EXTRA_WEIGHT = "com.jayis4176.my_first_app.W";
    public final static String EXTRA_BMI = "com.jayis4176.my_first_app.BMI";
    public final static String EXTRA_TESTORS = "com.jayis4176.my_first_app.Testors";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MySQLiteHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void GoCalcBMI (View view) {
        Intent intent = new Intent(this, DisplayBMI.class);
        EditText input_name = (EditText) findViewById(R.id.edit_name);
        EditText input_height = (EditText) findViewById(R.id.edit_height);
        EditText input_weight = (EditText) findViewById(R.id.edit_weight);

        // READ IN NAME
        String test_taker = input_name.getText().toString();

        // READ DB
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_TESTORS + " WHERE " + MySQLiteHelper.COLUMN_NAME + " = '" + test_taker + "'", null);
        if (cursor.getCount()<=0) {
            String test_height = input_height.getText().toString();
            double tmp_height = Double.parseDouble(test_height) / 100;
            String test_weight = input_weight.getText().toString();
            double tmp_weight = Double.parseDouble(test_weight);

            double tmp_BMI = tmp_weight / tmp_height / tmp_height;
            String test_BMI = String.valueOf(tmp_BMI);

            // save to DB
            database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_NAME, test_taker);
            values.put(MySQLiteHelper.COLUMN_HEIGHT, tmp_height);
            values.put(MySQLiteHelper.COLUMN_WEIGHT, tmp_weight);
            values.put(MySQLiteHelper.COLUMN_BMI, tmp_BMI);
            long newRowId;
            newRowId = database.insert(
                    MySQLiteHelper.TABLE_TESTORS,
                    null,
                    values);
/**/
            intent.putExtra(EXTRA_NAME, test_taker);
            intent.putExtra(EXTRA_HEIGHT, tmp_height);
            intent.putExtra(EXTRA_WEIGHT, tmp_weight);
            intent.putExtra(EXTRA_BMI, tmp_BMI);
        }
        else {

            cursor.moveToFirst();
            double tmp_height = cursor.getDouble(cursor.getColumnIndex(MySQLiteHelper.COLUMN_HEIGHT));
            double tmp_weight = cursor.getDouble(cursor.getColumnIndex(MySQLiteHelper.COLUMN_WEIGHT));
            double tmp_BMI = cursor.getDouble(cursor.getColumnIndex(MySQLiteHelper.COLUMN_BMI));

            intent.putExtra(EXTRA_NAME, test_taker);
            intent.putExtra(EXTRA_HEIGHT, tmp_height);
            intent.putExtra(EXTRA_WEIGHT, tmp_weight);
            intent.putExtra(EXTRA_BMI, tmp_BMI);
            /**/
        }

        startActivity(intent);
    }
    /**/

    public void SeeAll (View view) {
        String tmp_str = "Testors List: \n" + "-----------------------------\n";
        Intent intent = new Intent(this, ShowAllTestors.class);

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TESTORS,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            tmp_str = tmp_str +
                    "Name: " + cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_NAME)) + "\n" +
                    "BMI: " + cursor.getDouble(cursor.getColumnIndex(MySQLiteHelper.COLUMN_BMI)) + "\n" +
                    cursor.getDouble(cursor.getColumnIndex(MySQLiteHelper.COLUMN_HEIGHT)) + " cm\n" +
                    cursor.getDouble(cursor.getColumnIndex(MySQLiteHelper.COLUMN_WEIGHT)) + " kg\n" +
                    "-----------------------------\n"
                    ;
            cursor.moveToNext();
        }

        intent.putExtra(EXTRA_TESTORS, tmp_str);

        startActivity(intent);
    }
}
