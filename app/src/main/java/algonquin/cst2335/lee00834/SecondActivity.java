package algonquin.cst2335.lee00834;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;

import algonquin.cst2335.lee00834.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding  = ActivitySecondBinding.inflate(getLayoutInflater());
        //loads the XML file  /res/layout/activity_second.xml
        setContentView(binding.getRoot());

        Intent fromPrevious = getIntent();//return the Intent that got us here
//should be variables in fromPrevious:

        //null if EMAIL is not found
        String email = fromPrevious.getStringExtra("Email");//get the value of EMAIL variable
        String passwd = fromPrevious.getStringExtra("Password");

        binding.textView3.setText("Welcome back " + email);

        binding.callButton.setOnClickListener( click -> {
            String phoneNumber = binding.editTextPhone.getText().toString();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumber ));
            startActivity(call);
        });

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
}