package com.exercises.programing.simplegames.hangingman;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exercises.programing.simplegames.R;

public class HangGameActivity extends AppCompatActivity {

    String gameWord, checkGuess = "", checkFail = "";
    int guessedcounter = 0, failcounter = 0, pontuation = 0;
    boolean multiplayer;
    Button btn_check;
    // this button doesn't require onClicListener because already has onClic on the layout
    TextView txtview_hang, txtviewFailled;
    ImageView img;
    EditText edtxt_char;
    CharSequence txtfailed;
    LinearLayout layLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_game);
        layLetters = (LinearLayout) findViewById(R.id.layLetters);
        txtviewFailled = (TextView) findViewById(R.id.txtview_guess);
        img = findViewById(R.id.imageView);
        multiplayer = getIntent().getBooleanExtra("MULTIPLAYER",false);
        if (multiplayer) {
            String wordSent = getIntent().getStringExtra("WORD_ID");
            Log.d("MYLOG", wordSent);
            gameWord = wordSent.toUpperCase();
            Log.d("MYLOG", "gameWord is "+gameWord);
            btn_check = findViewById(R.id.btn_check);
            txtview_hang = findViewById(R.id.txtview_hang);
            edtxt_char = findViewById(R.id.edtxt_char);
            createTextViews();
        }
        else {
            if (savedInstanceState == null){
                gameWord = setRandomWord();
                createTextViews();
                Log.d("MYLOG","ELSE SINGLE PLAYER");
            }
        }
        Log.d("MYLOG","To test the app only: "+gameWord);
    } // closes onCreate

    public void createTextViews() {
        int padding = getResources().getDimensionPixelSize(R.dimen.padding);

        for (int i = 0 ; i < gameWord.length(); i++){
            TextView tv = new TextView(this);
            tv.setText(getResources().getString(R.string.hang_letter));
            tv.setTextSize(getResources().getDimension(R.dimen.txt_size));
            tv.setPadding(0,0,padding,0);
            layLetters.addView(tv);
        }
    }

    public String setRandomWord(){
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//  TRY TO FIND A WAY TO RUN THE RANDOMS IN THE BACKGROUND TREAD
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //String words = getString(R.string.char4letters1);
        int randomArray = 5;//(int) (Math.random() * 5);
        //char4letters5 is the smallest list in this app and contains 91 words
        //char4letters4 is the biggest list in this app and contains 107 words
        //char4letters3 contains 97 words
        //char4letters2 contains 99 words
        //char4letters1 contains 96 words
        String wordID = "char4letters" + randomArray;
        int resID = getResources().getIdentifier(wordID, "string", getPackageName());
        //get from Resources get the identifier wordID from string and get its Package name
        String words = getString(resID);

        String[] arrayWords = words.split(", ");
        //Put inside arrayWords all the items inside words separated by the split
        Log.d("MYLOG", "The array length is: "+arrayWords.length);
        int randomNumber = (int) (Math.random() * arrayWords.length);
        String randomWord = arrayWords[randomNumber];

        return randomWord.toUpperCase();
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
            toastMessage("Please insert letter");
        }
    }

    public void checkLetter(String insertedLetter){
        char charInserted = insertedLetter.toUpperCase().charAt(0);
        // character inserted (in this case letters)
        // the inserted letter is changed to UpperCase so the user will get points for the
        // right letters despite being upper or lowercase
        // charAt is needed so the app is able to search inside the words for the letters,
        // like looking for an item in an array list.

        String repeated = null;
        Boolean repeatedInGuess = false;
        Boolean repeatedInWord = false;
        for (int i = 0; i < gameWord.length(); i++) {
            for (int j = i + 1; j < gameWord.length(); j++) {
                if (gameWord.charAt(i) == gameWord.charAt(j)) {
                    repeatedInWord = true;
                }
            }
        }

        for (int i=0; i < gameWord.length(); i++) {
            //for each time we check if i is smaller than the length of gameWord,
            //keep running the code inside and add 1 to i by the end of each loop
            Log.d("MYLOG", "guessed counter: " + guessedcounter);
            if (gameWord.charAt(i) == charInserted) {
                //check if any letter in the gameWord matches with the inserted character
                for (int j=0; j < layLetters.getChildCount(); j++){
                    //for each time we check if j is smaller than the amount of itens inside layLetters,
                    //keep running the code inside and add 1 to j by the end of each loop
                    TextView current_txtview = (TextView) layLetters.getChildAt(j);
                    //current_txtview is set to the TextView inside layLetters in the j position
                    String current_letter = current_txtview.getText().toString();
            //current_letter is set to the text (which is turned into String) from current_txtview
                    Log.d("MYLOG", "current_letter: " + current_letter);

                    if (insertedLetter.toUpperCase().equals(current_letter) && !repeatedInGuess){
                        //Make insertedLetter be uppercase (or it will not match with gameWord)
                        //if that is equal to current_letter AND hasRepeated is false do this:
                        toastMessage("This letter was already scored");
                        Log.d("MYLOG", "already guessed "+current_letter);
                //set repeated to have the value of current_letter, because we need a variable
                        //that is only set inside this if to make other conditions work

                        repeated = current_letter;
                        Log.d("MYLOG", "Repeated: " + repeated);
                        break;
                        // adding the break only prevents the toast from showing letter times letter
                        // (2x2, 3x3, 4x4), the toast still shows for the number of times the letter
                        // is repeated. Which is why another break is needed for the other for loop
                    }
                }

                if (!insertedLetter.toUpperCase().equals(repeated)) {
                    // if insertedLetter is NOT repeated
                    for (int n = 0; n < gameWord.length(); n++){
                        if(gameWord.charAt(i)==gameWord.charAt(n)){
                            // if the gameWord has a repeated letter
                            showLettersIndex(n, charInserted);
                            // insert the letter into the other positions(n) in the correct guess list
                            repeatedInGuess=true;
                            guessedcounter++;
                        }
                    }
                    //if gameWord doesn't have a repeated letter, we don't need to worry
                    Log.d("MYLOG", charInserted + " was a match");
                    showLettersIndex(i, charInserted);
                    //just insert the letter into the first found correct position
                    checkGuess = insertedLetter;
                    Log.d("MYLOG", "Check guess is: " + checkGuess);
                    Log.d("MYLOG", "Previous guess: " + repeated);
                    pontuation++;
                }
                if (repeatedInWord) break;
                // If repeatedInWord is true, break will stop the loop even if the condition of the
                // loop was not satisfied. If repeatedInWord is false, the loop will only stop after
                // the condition is satisfied
                // This if break need to be at the end because repeatedInWord only checks if there
                // is a repeated letter inside the gameWord, so even if there guess was not a
                // repeated letter, the break would activate. Putting it at the end of the loop
                // allows all the code above to be run
            }
        }

        if (!insertedLetter.equals(checkGuess) && !insertedLetter.toUpperCase().equals(repeated)){
//if insertedLetter is different from checkGuess, the letter fails and goes to the method below
            letterFailed(Character.toString(charInserted));
        }

// WIN THE GAME ---------------------------------------------------------------------------------
        if (guessedcounter == gameWord.length()) {
            pontuation++;
            if (multiplayer) {
                Log.d("MYLOG", "guessed counter: " + guessedcounter);
                Intent multplayIntent = new Intent(this, HangMultiplayerActivity.class);
                startActivity(multplayIntent);
            } // closes if multiplayer
            else {
                pontuation++;
                toastMessage("You got "+pontuation+" points");
                clearScreen();
                //re-start game
                guessedcounter=0;
                failcounter=0;
                gameWord = setRandomWord();
                Log.d("MYLOG", "To test the app only: "+gameWord);
            }
        } // closes if guessedcounter
    } // closes if chackLetter

    public void letterFailed(String letterFail) {
        txtviewFailled = findViewById(R.id.txtview_guess);
        String prevFail = txtviewFailled.getText().toString();

        failcounter++;

        if (failcounter<2) {
            //if it's the first wrong guess, set text will only get letterFail
            //else... it will get letterFail AND prevFail
            txtviewFailled.setText(letterFail);
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
                txtviewFailled.setText(String.format("%s %s", prevFail, letterFail));
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
        else { // GAME OVER ---------------------------------------------------------------------------------
            if (multiplayer) {
                img.setImageResource(R.drawable.ic_gameover);
                // MAKE PLAYER STOP FROM GUESSING WITHOUT THE NEED FOR A NEW SCREEN:
                LinearLayout laystopguess = findViewById(R.id.layLetters);
                if (laystopguess.getChildCount() > 1) {
                    laystopguess.removeAllViews();               //clean the TextView from the letters
                    TextView stopguess = new TextView(this);//dynamically create textview
                    stopguess.setTextSize(getResources().getDimension(R.dimen.txt_size));
                    stopguess.setText("GAME OVER");
                    laystopguess.addView(stopguess);              //add to the LinearLayout
                }
                btn_check.setVisibility(View.GONE);
                txtview_hang.setVisibility(View.GONE);
                edtxt_char.setVisibility(View.GONE);
            } // closes if multiplayer
            else {
                toastMessage("Game Over!");
                Intent gameOver = new Intent(this,HangOverActivity.class);
                gameOver.putExtra("POINTS_ID",pontuation);
                startActivity(gameOver); //goes to intent that calls HangOverActivity
                finish();
            }
        } // closes else GAME OVER
    } // closes letterFailed

    public void clearScreen(){
        txtviewFailled.setText("");
// Make every letter inside layLetters _ again:
        for (int i=0; i < layLetters.getChildCount(); i++){
            TextView current_txtview = (TextView) layLetters.getChildAt(i);
            current_txtview.setText(getResources().getString(R.string.hang_letter));
        }
        img.setImageResource(R.drawable.ic_hang0);
    }

    public void showLettersIndex(int position, char guessed){
        TextView txtview = (TextView) layLetters.getChildAt(position);
        txtview.setText(Character.toString(guessed));
    }

    //This method personalizes the Toast message
    @TargetApi(Build.VERSION_CODES.M)
    protected void toastMessage(String message){
        Toast toast = Toast.makeText(this, message,  Toast.LENGTH_SHORT);
        View view = toast.getView();

//Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(getResources().getColor
                (R.color.colorPrimaryOld, getResources().newTheme()), PorterDuff.Mode.SRC_IN);
//Gets the TextView from the Toast so it can be edited
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
//The most important part, display the Toast
        toast.show();
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
        Log.d("MYLOG", "restored gameWord: "+gameWord);
        txtfailed = savedInstanceState.getCharSequence("txtfailed");
        Log.d("MYLOG", "restored charsequence: "+txtfailed);
        TextView viewFailed = findViewById(R.id.txtview_guess);
        viewFailed.setText(txtfailed);
        Log.d("MYLOG", "txtviewFailed restored: "+txtviewFailled);
        failcounter = savedInstanceState.getInt("failcounter");
        guessedcounter = savedInstanceState.getInt("guessedcounter");

        if (!multiplayer) createTextViews();
// for some reason, createTextViews is destroyed completely or partially in the single player
// but just adding the createTextView here causes the multiplayer to give fatalError
// when changing orientation of the screen
        String testing = savedInstanceState.getString("testing");
        Log.d("MYLOG", "testing restored: "+testing);
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

    @Override // in use
    protected void onResume() {
        super.onResume();
        Log.d("MYLOG", "ON RESUME RUNNING");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MYLOG", "ON PAUSE RUNNING");
    }

    @Override // in use
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MYLOG", "ON DESTROY RUNNING");
    }
}
