package algonquin.cst2335.lee00834;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import algonquin.cst2335.lee00834.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    protected ActivityMainBinding binding;

    //equivalent to        static void main(String args[])
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calling onCreate from parent class
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //loads an XML file on the page
        setContentView(binding.getRoot());
        Log.e(TAG, "In onCreate()");

        binding.loginButton.setOnClickListener( click -> {
            Log.e(TAG, "You clicked the button");

            //where to go:                   leaving here    going to SecondActivity
            Intent nextPage = new Intent( this, SecondActivity.class);

            nextPage.putExtra("Email", binding.emailText.getText().toString());
            nextPage.putExtra("Password", binding.passwdText.getText().toString());

            //go to another page
            startActivity(nextPage); //carries all the data to the next page
        });

//        //where you can save files: returns a File object representing the directory
//        // where the app is running from, called the Sandbox.
//        File myDir = getFilesDir();
//        String path = myDir.getAbsolutePath();
//
//        // Context.MODE_PRIVATE: only this app that created the file can open it.
//        SharedPreferences savedprefs = getSharedPreferences("MyFileName", Context.MODE_PRIVATE);
//
//        //get an editor
//        SharedPreferences.Editor edit = savedprefs.edit();
//
//        binding.emailText.setText(
//                savedprefs.getString("NAME", "default")
//        );
    }

    @Override //now visible, not listening for clicks
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "In onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "In onResume()");
    }

    @Override //leaving the screen, no longer listening for input
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "In onPause()");
    }

    @Override //no longer visible on screen
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "In onStop()");
    }

    @Override //garbage collected, app is gone
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "In onDestroy()");
    }
}