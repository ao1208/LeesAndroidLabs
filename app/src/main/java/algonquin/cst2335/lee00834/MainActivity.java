package algonquin.cst2335.lee00834;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This page represents the first page loaded
 * @author Wan-Hsuan Lee
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** This holds the content of text view */
    protected TextView myText;
    /** This holds the edit text for typing into */
    protected EditText myEdit;
    /** This holds the "LOGIN" button */
    protected Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//calling onCreate from parent class
        setContentView(R.layout.activity_main);

        myText = findViewById(R.id.theTextView);
        myEdit = findViewById(R.id.theEditText);
        myButton = findViewById(R.id.theButton);

        myButton.setOnClickListener( click -> {
            String passwd = myEdit.getText().toString();
            if(checkPasswordComplexity( passwd )){
                myText.setText("Your password meets the requirements.");
            }
            else myText.setText("You shall not pass!");
        });
    }

    /**
     *  This function checks if this string has an Upper Case letter,
     *  a lower case letter, a number, and a special symbol (#$%^&*!@?).
     *  @param pw The string object to be checked
     *  @return Return true if...
     */
    boolean checkPasswordComplexity(String pw){

        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);
            if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (Character.isDigit(c)) {
                foundNumber = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }
        }

        if(!foundUpperCase) {
            Toast.makeText( this, "Missing an upper case letter.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!foundLowerCase) {
            Toast.makeText( this, "Missing a lower case letter.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!foundNumber) {
            Toast.makeText( this, "Missing a number.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!foundSpecial) {
            Toast.makeText( this, "Your password does not have a special symbol.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true; //only get here if they're all true
    }

    /**
     * This function checks if the character is a special character (#$%^&*!@?).
     *
     * @param c The character to be checked
     * @return Returns true if the character is a special character, false otherwise.
     */
    boolean isSpecialCharacter(char c){
        switch (c){
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;

        }
    }
}