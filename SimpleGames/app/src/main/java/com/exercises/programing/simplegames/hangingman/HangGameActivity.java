package com.exercises.programing.simplegames.hangingman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exercises.programing.simplegames.R;

public class HangGameActivity extends AppCompatActivity {

    String gameWord;
    String checkGuess = "";
    String checkFail = "";
    int guessedcounter = 0;
    int failcounter = 0;
    int pontuation = 0;

    TextView txtviewFailed;
    CharSequence txtfailed;
    LinearLayout layLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_game);

        if (savedInstanceState == null){
            setRandomWord();
            toastMessage("To test the app only: "+gameWord);
        }
    }

    //retriving the letter inserted on the editText
    public void insertLetter(View v){
        EditText x = findViewById(R.id.edtxt_char);
        String letter = x.getText().toString();
        Log.d("MYLOG", "The letter introduced is " + letter);
        //Log.d is good to check if the variables are working properly
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
        // character inserted (in this case letters)
        // the inserted letter is changed to UpperCase so the user will get points for the
        // right letters despite being upper or lowercase
        // charAt is needed so the app is able to search inside the words for the letters,
        // like looking for an item in an array list.

        layLetters = findViewById(R.id.layLetters);
        String repeated = null;
        Boolean hasRepeated = false;

        for (int i=0; i < gameWord.length(); i++) {
            //for each time we check if i is smaller than the length of gameWord,
            //keep running the code inside and add 1 to i by the end of each loop
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

                    if (insertedLetter.toUpperCase().equals(current_letter) && !hasRepeated){
                        //Make insertedLetter be uppercase (or it will not match with gameWord)
                        //if that is equal to current_letter AND hasRepeated is false do this:
                        repeated = current_letter;
                //set repeated to have the value of current_letter, because we need a variable
                        //that is only set inside this if to make other conditions work
                        Log.d("MYLOG", "Repeated: " + repeated);
                        toastMessage("This letter was already scored");
//THIS HERE IS BEING REPEATED SEVERAL TIMES WHEN THE WORD HAS REPEATED LETTERS:
// x² -> if 2 R in the word, Toast appears 4 times. if 3 A in a word, Toast appears 9 times.
// 1 for each letter repeated times 1 for each space the letter is present, I suppose
                            Log.d("MYLOG", "current letter repeated: " + current_letter);
                            break;
                    }
                }

                if (!insertedLetter.toUpperCase().equals(repeated)) {
                //if uppercase insertedLetter does NOT equal repeated do the code inside
                    for (int n = 0; n < gameWord.length(); n++){
                        if(gameWord.charAt(i)==gameWord.charAt(n)){
                        //if the gameWord has a repeated letter
                            showLettersIndex(n, charInserted);
                        //insert the letter into the other positions(n) in the correct guess list
                            hasRepeated=true;
                        }
                    }
                    //if gameWord doesn't have a repeated letter, we don't need to worry
                    Log.d("MYLOG", charInserted + " was a match");
                    showLettersIndex(i, charInserted);
                    //just insert the letter into the first found correct position
                    checkGuess = insertedLetter;
                    guessedcounter++;
                    Log.d("MYLOG", "Check guess is: " + checkGuess);
                    Log.d("MYLOG", "Previous guess 2: " + repeated);
                    pontuation++;
                }
            }
        }

        if (!insertedLetter.equals(checkGuess) && !insertedLetter.toUpperCase().equals(repeated)){
//if insertedLetter is different from checkGuess, the letter fails and call the method below
            letterFailed(Character.toString(charInserted));
        }
        if (guessedcounter == gameWord.length()){ // WIN GAME STAGE
            pontuation++;
            toastMessage("You got "+pontuation+" points");
            clearScreen();
            //re-start game
            setRandomWord();
            toastMessage("To test the app only: "+gameWord);
        }
    }

    public void setRandomWord(){
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
        Log.d("MYLOG", "The array lengh is: "+arrayWords.length);
        int randomNumber = (int) (Math.random() * arrayWords.length);
        String randomWord = arrayWords[randomNumber];

        gameWord = randomWord.toUpperCase();
    }

    public void clearScreen(){
        TextView txtviewFailled = (TextView) findViewById(R.id.txtview_guess);
        layLetters = findViewById(R.id.layLetters);
        txtviewFailled.setText("");
        guessedcounter=0;
        failcounter=0;
        // Makes every letter inside layLetters x again:
        for (int i=0; i < layLetters.getChildCount(); i++){
            TextView current_txtview = (TextView) layLetters.getChildAt(i);
            current_txtview.setText("x ");
        }
        ImageView img = findViewById(R.id.imageView);
        img.setImageResource(R.drawable.ic_hang0);
    }

    public void letterFailed(String letterFail) {
        TextView txtviewFailed = findViewById(R.id.txtview_guess);
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
        // if no, put code bellow inside the if different, inside the else above
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        ImageView img = findViewById(R.id.imageView);
        String imgID = "ic_hang" + failcounter;
        int resID = getResources().getIdentifier(imgID, "drawable", getPackageName());

        if (failcounter < 7){
            img.setImageResource(resID);
        }
        else{ // GAME OVER
            toastMessage("Game Over!");
            Intent gameOver = new Intent(this,HangOverActivity.class);
            gameOver.putExtra("POINTS_ID",pontuation);
            startActivity(gameOver); //goes to intent that calls HangOverActivity
            finish();
        }
    }

    public void showLettersIndex(int position, char guessed){
        layLetters = findViewById(R.id.layLetters);
        TextView txtview = (TextView) layLetters.getChildAt(position);
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
        layLetters = findViewById(R.id.layLetters);
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
        //works if we have a n if in the onCreate
        txtfailed = savedInstanceState.getCharSequence("txtfailed");
        Log.d("MYLOG", "restored charsequence: "+txtfailed);
        TextView viewFailed = findViewById(R.id.txtview_guess);
        viewFailed.setText(txtfailed);
        Log.d("MYLOG", "txtviewFailed saved: "+txtviewFailed);
        failcounter = savedInstanceState.getInt("failcounter");

        String testing = savedInstanceState.getString("testing");
        Log.d("MYLOG", "testing saved: "+testing);
        layLetters = findViewById(R.id.layLetters);
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
    } //closes onRestore
}
