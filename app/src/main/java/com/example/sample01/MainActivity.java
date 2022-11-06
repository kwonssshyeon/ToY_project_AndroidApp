package com.example.sample01;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.sample01.DataBase.SmokeDataBaseHelper;
import com.example.sample01.DataBase.NoSmokeDataBaseHelper;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sample01.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    float val = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getNoSmoke();
        getSmoke();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void getNoSmoke() {

        NoSmokeDataBaseHelper dbHelper = new NoSmokeDataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        Cursor cursor1 = db.rawQuery("SELECT * FROM noSmoking_area where COUNT_ID <=10000 ",null);
        Cursor cursor2 = db.rawQuery("SELECT * FROM noSmoking_area where COUNT_ID BETWEEN 10001 and 20000 ",null);
        Cursor cursor3 = db.rawQuery("SELECT * FROM noSmoking_area where COUNT_ID BETWEEN 20001 and 30000 ",null);
        Cursor cursor4 = db.rawQuery("SELECT * FROM noSmoking_area where COUNT_ID BETWEEN 30001 and 40000 ",null);
        Cursor cursor5 = db.rawQuery("SELECT * FROM noSmoking_area where COUNT_ID >=40001 ",null);


        if (cursor1.moveToNext())
            val = cursor1.getFloat(5);
        if (cursor2.moveToNext())
            val = cursor2.getFloat(5);
        if (cursor3.moveToNext())
            val = cursor1.getFloat(5);
        if (cursor4.moveToNext())
            val = cursor1.getFloat(5);
        if (cursor5.moveToNext())
            val = cursor1.getFloat(5);

        cursor1.close();
        cursor2.close();
        cursor3.close();
        cursor4.close();
        cursor5.close();
        dbHelper.close();
    }

    public void getSmoke(){
        SmokeDataBaseHelper dbHelper = new SmokeDataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM smoking_area",null);

        if (cursor.moveToNext())
            val = cursor.getFloat(9);

        cursor.close();
        dbHelper.close();
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}