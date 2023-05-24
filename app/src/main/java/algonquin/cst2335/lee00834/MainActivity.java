package algonquin.cst2335.lee00834;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2335.lee00834.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot()); //loads XML on screen

        TextView mytext = variableBinding.theText;
        Button mybutton = variableBinding.theButton;
        EditText myedit = variableBinding.theEdit;
        mybutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String editString = myedit.getText().toString();
                mytext.setText("Your edit text has: " + editString);
            }
        });

//        setContentView(R.layout.activity_main);
//        TextView mytext = findViewById(R.id.theText);
//        Button mybutton = findViewById(R.id.theButton);
//        EditText myedit = findViewById(R.id.theEdit);
//        mybutton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String editString = myedit.getText().toString();
//                mytext.setText("Your edit text has: " + editString);
//            }
//        });
    }
}