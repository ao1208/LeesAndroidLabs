package algonquin.cst2335.lee00834;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
            startActivity(nextPage);
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "In onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "In onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "In onDestroy()");
    }
}