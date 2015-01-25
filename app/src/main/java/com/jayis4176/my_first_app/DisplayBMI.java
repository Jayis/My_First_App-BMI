package com.jayis4176.my_first_app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;


public class DisplayBMI extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = "Your name is " + intent.getStringExtra(MainActivity.EXTRA_NAME) + "\n"
                + "Your height is " + String.valueOf(intent.getDoubleExtra(MainActivity.EXTRA_HEIGHT, 0)) + " m \n"
                + "Your weight is " + String.valueOf(intent.getDoubleExtra(MainActivity.EXTRA_WEIGHT, 0)) + " kg \n"
                + "And...\n"
                + "Your BMI is " + String.valueOf(intent.getDoubleExtra(MainActivity.EXTRA_BMI, 0));

        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);
        setContentView(textView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_bmi, menu);
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
}
