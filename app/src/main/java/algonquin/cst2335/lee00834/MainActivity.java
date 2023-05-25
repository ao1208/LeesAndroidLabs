package algonquin.cst2335.lee00834;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.lee00834.MainViewModel;
import algonquin.cst2335.lee00834.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    private MainViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot()); //loads XML on screen

        variableBinding.theButton.setOnClickListener( click -> {
            model.editString.postValue(variableBinding.theEdit.getText().toString());
            variableBinding.theText.setText(model.editString.getValue());
        });

        model.editString.observe(this, s -> {
            variableBinding.theText.setText(model.editString.getValue());
        });

        model.isSelected.observe(this, selected -> {
            variableBinding.theCheckbox.setChecked(selected);
            variableBinding.theRadio.setChecked(selected);
            variableBinding.theSwitch.setChecked(selected);
            Toast.makeText(this, "The value is now: " + selected, Toast.LENGTH_SHORT).show();
        });

        variableBinding.theCheckbox.setOnCheckedChangeListener( ( btn , isChecked) -> {
            model.isSelected.postValue(isChecked) ;//set to b, notify all observers
        });

        variableBinding.theRadio.setOnCheckedChangeListener( ( btn, isChecked) -> {
            model.isSelected.postValue(isChecked) ;//set to b, notify all observers
        });

        variableBinding.theSwitch.setOnCheckedChangeListener( (btn, isChecked) -> {
            model.isSelected.postValue(isChecked) ;//set to b, notify all observers
        });

        variableBinding.theImageButton.setOnClickListener( click -> {
            int width = variableBinding.theImageButton.getWidth();
            int height = variableBinding.theImageButton.getHeight();
            Toast.makeText(this, "The width = " + width + " and height = " + height, Toast.LENGTH_SHORT).show();
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