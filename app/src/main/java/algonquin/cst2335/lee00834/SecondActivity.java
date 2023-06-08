package algonquin.cst2335.lee00834;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import algonquin.cst2335.lee00834.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding  = ActivitySecondBinding.inflate(getLayoutInflater());
        //loads the XML file  /res/layout/activity_second.xml
        setContentView(binding.getRoot());

        //should be variables in fromPrevious:
        Intent fromPrevious = getIntent();//return the Intent that got us here
        //null if EMAIL is not found
        String email = fromPrevious.getStringExtra("Email");//get the value of EMAIL variable

        // Welcome Message
        binding.textView3.setText("Welcome back " + email);

        // Sets history phone number
        SharedPreferences prefs = getSharedPreferences("myData", MODE_PRIVATE);
        binding.editTextPhone.setText(prefs.getString("phoneNum", ""));

        //Sets the phone number and adds the call button the functionality of on-click dialing phone call
        binding.callButton.setOnClickListener( click -> {
            String phoneNumber = binding.editTextPhone.getText().toString();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumber ));
            startActivity(call);
        });
        // Loads existing image file
        File file = new File(getFilesDir(), "image.png");
        if (file.exists()) {
            Bitmap imgBitmap = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/image.png");
            binding.image.setImageBitmap(imgBitmap);
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Bitmap bitmap = data.getParcelableExtra("data");
                            binding.image.setImageBitmap (bitmap);
                        }
                    }
                });

        binding.changeButton.setOnClickListener( click -> {
            cameraResult.launch(cameraIntent);
        });
    }

    protected void onPause() {
        super.onPause();

        // Saves the phone number
        SharedPreferences.Editor editor = getSharedPreferences("myData", MODE_PRIVATE).edit();
        editor.putString("phoneNum", binding.editTextPhone.getText().toString());
        editor.apply();

        // Saves Image File
        try (FileOutputStream fOS = openFileOutput("image.png", MODE_PRIVATE)) { // Throws fileNotFoundException
            Bitmap bitmap = ((BitmapDrawable) binding.image.getDrawable()).getBitmap();
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOS)) {
                fOS.flush(); // Throws IOException
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}