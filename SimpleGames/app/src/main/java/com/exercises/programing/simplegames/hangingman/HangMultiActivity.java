package com.exercises.programing.simplegames.hangingman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exercises.programing.simplegames.R;

public class HangMultiActivity extends AppCompatActivity {

    String gameWord;
    String checkGuess = "";
    String checkFail = "";
    int guessedcounter = 0;
    int failcounter = 0;
    Button btn_check;
    // this button doesn't require onClicListener because already has onClic on the layout
    TextView txtview_hang;
    EditText edtxt_char;
    TextView txtviewFailed;
    CharSequence txtfailed;
    LinearLayout layLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_multi);

        String wordSent = getIntent().getStringExtra("WORD_ID");
        Log.d("MYLOG", wordSent);
        toastMessage("To test the app only: "+wordSent);
        createTextViews(wordSent);
        gameWord = wordSent.toUpperCase();
        btn_check = findViewById(R.id.btn_check);
        txtview_hang = findViewById(R.id.txtview_hang);
        edtxt_char = findViewById(R.id.edtxt_char);
    }

    public void createTextViews(String word) {
        layLetters = (LinearLayout) findViewById(R.id.layLetters);
        int padding = getResources().getDimensionPixelSize(R.dimen.txt_radio);

        for (int i = 0 ; i < word.length(); i++){
            TextView tv = new TextView(this);
            tv.setText(getResources().getString(R.string.hang_letter));
            tv.setTextSize(getResources().getDimension(R.dimen.txt_size));
            tv.setPadding(0,0,padding,0);
            layLetters.addView(tv);
        }
    }

    //retriving the letter inserted on the editText
    public void insertLetter(View v){
        EditText x = findViewById(R.id.edtxt_char);
        String letter = x.getText().toString();
        Log.d("MYLOG", "The letter introduced is " + letter);
        x.setText("");

        if (letter.length() == 1){
            checkLetter(letter);
        }
        // On the activity_hang_game, the edtxt_char was defined to accept only one character by
        // android:maxLength="1", so the else bellow only needs to complain about empty values.
        else {
            Toast.makeText(this, "Please insert letter", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkLetter(String insertedLetter){
        char charInserted = insertedLetter.toUpperCase().charAt(0);
        //character inserted (in this case letters)
        // the inserted letter is changed to UpperCase so the user will get points for the
        // right letters despite being upper or lowercase
        // charAt is needed so the app is able to search inside the words for the letters,
        // like looking for an item in an array list.

        layLetters = findViewById(R.id.layLetters);
        String repeated = null;
        Boolean hasRepeated = false;

        for (int i=0; i < gameWord.length(); i++) {
            if (gameWord.charAt(i) == charInserted) {
                for (int j=0; j < layLetters.getChildCount(); j++){
                    TextView current_txtview = (TextView) layLetters.getChildAt(j);
                    String current_letter = current_txtview.getText().toString();

                    Log.d("MYLOG", "current_letter: " + current_letter);

                    if (insertedLetter.toUpperCase().equals(current_letter) && !hasRepeated){
                        toastMessage("This letter was already scored");
                        Log.d("MYLOG", "already guessed "+current_letter);

                        repeated = current_letter;
                        Log.d("MYLOG", "Repeated: " + repeated);
                    }
                }

                if (!insertedLetter.toUpperCase().equals(repeated)) {
                    for (int n = 0; n < gameWord.length(); n++){
                        if(gameWord.charAt(i)==gameWord.charAt(n)){
                            showLettersIndex(n, charInserted);
                            hasRepeated=true;
                        }
                    }
                    Log.d("MYLOG", charInserted + " was a match");
                    showLettersIndex(i, charInserted);
                    checkGuess = insertedLetter;
                    guessedcounter++;
                    Log.d("MYLOG", "Check guess is: " + checkGuess);
                    Log.d("MYLOG", "Previous guess: " + repeated);
                }
            }
        }

        if (!insertedLetter.equals(checkGuess) && !insertedLetter.toUpperCase().equals(repeated)){
//if insertedLetter is different from checkGuess, the letter fails and goes to the method below
            letterFailed(Character.toString(charInserted));
        }
        if (guessedcounter == gameWord.length()) {
            Intent multplayIntent = new Intent(this, HangMultiplayerActivity.class);
            startActivity(multplayIntent);
        }
    }

    public void letterFailed(String letterFail) {
        txtviewFailed = findViewById(R.id.txtview_guess);
        String prevFail = txtviewFailed.getText().toString();

        failcounter++;

        if (failcounter<2) {
            //if it's the first wrong guess, set text will only get letterFail
            //else... it will get letterFail AND prevFail
            txtviewFailed.setText(letterFail);
            Log.d("MYLOG","fail count: "+failcounter);
        }
        else{
            char charFail = letterFail.toUpperCase().charAt(0);

            for (int i=0; i < prevFail.length(); i++){
                if (prevFail.charAt(i) == charFail) {
                    Log.d("MYLOG", letterFail + " is already there");
                    checkFail = letterFail;
                    Log.d("MYLOG", "Check fail repeated: "+checkFail);
                    toastMessage("This letter have already been guessed");
                }
            }
            Log.d("MYLOG", "For fail repeated: "+checkFail);

            if (!checkFail.equals(letterFail)){
                txtviewFailed.setText(String.format("%s %s", prevFail, letterFail));
                Log.d("MYLOG", "Always pass: "+checkFail);
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // DECIDE IF RE-GUESSED LETTER ALSO RESULTS IN POINT LOSS OR NOT
        // if yes, leave the code as is
        // if no, put code bellow inside the if different inside the else above
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        ImageView img = findViewById(R.id.imageView);
        String imgID = "ic_hang" + failcounter;
        int resID = getResources().getIdentifier(imgID, "drawable", getPackageName());

        if (failcounter < 7){
            img.setImageResource(resID);
        }
        else{ // GAME OVER
            img.setImageResource(R.drawable.ic_gameover);
            //toastMessage("Game Over!");
            // MAKE A WAY TO STOP PLAYER FROM GUESSING
            // WITHOUT THE NEED FOR A NEW SCREEN:
            LinearLayout laystopguess = findViewById(R.id.layLetters);
            if(laystopguess.getChildCount() > 1) {
                laystopguess.removeAllViews();               //clean the TextView from the letters
                TextView stopguess= new TextView(this);//dynamically create textview
                stopguess.setTextSize(getResources().getDimension(R.dimen.txt_size));
                stopguess.setText("GAME OVER");
                laystopguess.addView(stopguess);              //add to the LinearLayout
            }
            btn_check.setVisibility(View.GONE);
            txtview_hang.setVisibility(View.GONE);
            edtxt_char.setVisibility(View.GONE);
        }
    }

    public void showLettersIndex(int position, char guessed){
        LinearLayout layLatter = (LinearLayout) findViewById(R.id.layLetters);
        TextView txtview = (TextView) layLatter.getChildAt(position);
        txtview.setText(Character.toString(guessed));
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


    //Make sure the player won't lose progress in the game when changing from landscape to
    //portrait view and vice versa
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String txtguessed;
        String testing = null;
        for (int j=0; j < layLetters.getChildCount(); j++){
            TextView current_txtview = (TextView) layLetters.getChildAt(j);
            Log.d("MYLOG", "current_txtview saved: "+current_txtview.getText());
            txtguessed = current_txtview.getText().toString();
            if (testing == null) {
                testing = txtguessed;
            } else {
                testing = String.format("%s%s", testing, txtguessed);
            }
        }
        Log.d("MYLOG", "testing saved: "+testing);
        outState.putString("testing", testing);

        outState.putString("gameWord", gameWord);

        TextView viewFailed = findViewById(R.id.txtview_guess);
        CharSequence txtfailed = viewFailed.getText();
        Log.d("MYLOG", "charsequence saved: "+txtfailed);
        outState.putCharSequence("txtfailed", txtfailed);

        outState.putInt("guessedcounter", guessedcounter);
        outState.putInt("failcounter", failcounter);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        gameWord = savedInstanceState.getString("gameWord");
        txtfailed = savedInstanceState.getCharSequence("txtfailed");
        Log.d("MYLOG", "restored charsequence: "+txtfailed);
        TextView viewFailed = findViewById(R.id.txtview_guess);
        viewFailed.setText(txtfailed);
        Log.d("MYLOG", "txtviewFailed saved: "+txtviewFailed);
        failcounter = savedInstanceState.getInt("failcounter");

        String testing = savedInstanceState.getString("testing");
        Log.d("MYLOG", "testing saved: "+testing);
        char c;
        TextView current_txtview;
        for (int j=0; j < layLetters.getChildCount(); j++){
            current_txtview = (TextView) layLetters.getChildAt(j);
            c = testing.charAt(j);
            Log.d("MYLOG", "current_txtview saved: "+c);
            current_txtview.setText(String.valueOf(c));
        }

        ImageView img = findViewById(R.id.imageView);
        String imgID = "ic_hang" + failcounter;
        int resID = getResources().getIdentifier(imgID, "drawable", getPackageName());
        if (failcounter < 7){
            img.setImageResource(resID);
        }
        if (failcounter == 7){
            btn_check.setVisibility(View.GONE);
            txtview_hang.setVisibility(View.GONE);
            edtxt_char.setVisibility(View.GONE);
            img.setImageResource(R.drawable.ic_gameover);

            LinearLayout laystopguess = findViewById(R.id.layLetters);
            if(laystopguess.getChildCount() > 1) {
                laystopguess.removeAllViews();               //clean the TextView from the letters
                TextView stopguess= new TextView(this);//dynamically create textview
                stopguess.setTextSize(getResources().getDimension(R.dimen.txt_size));
                stopguess.setText(R.string.game_over);
                laystopguess.addView(stopguess);              //add to the LinearLayout
            }
        }
    } //closes onRestore
}
