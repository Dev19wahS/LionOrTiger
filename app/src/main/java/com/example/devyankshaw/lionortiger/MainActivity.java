package com.example.devyankshaw.lionortiger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player {//Here Player is the name of our own datatype

        ONE, TWO, NO//This are the two valid values that will be accepted by our datatype only

    }

    Player currentPlayer = Player.ONE;

    Player[] playerChoices = new Player[9];

    int[][] winnerRowsColumns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};//States to identify the winner

    private boolean gameOver = false;


    private  Button btnReset;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       setPlayerNo();

        btnReset = findViewById(R.id.btnReset);
        gridLayout = findViewById(R.id.gridLayout);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    resetTheGame();
                btnReset.setVisibility(View.GONE);
            }
        });



    }

    public void imageViewIsTapped(View imageView) {

        ImageView tappedImageView = (ImageView) imageView;
        int tappedImageViewTag = Integer.parseInt(tappedImageView.getTag().toString());

        if (playerChoices[tappedImageViewTag] == Player.NO && gameOver == false) {

            tappedImageView.setTranslationX(-2000);

            playerChoices[tappedImageViewTag] = currentPlayer;

            if (currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.lion);
                currentPlayer = Player.TWO;
            } else if (currentPlayer == Player.TWO) {
                tappedImageView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.ONE;
            }

            tappedImageView.animate().translationXBy(2000).alpha(1).rotationBy(3600).setDuration(2000);

            //Toast.makeText(MainActivity.this, tappedImageView.getTag().toString(),Toast.LENGTH_SHORT).show();

            for (int[] winnerColumns : winnerRowsColumns) {

                if (playerChoices[winnerColumns[0]] == playerChoices[winnerColumns[1]]
                        && playerChoices[winnerColumns[1]] == playerChoices[winnerColumns[2]]
                        && playerChoices[winnerColumns[0]] != Player.NO && gameOver == false) {
                    btnReset.setVisibility(View.VISIBLE);
                    gameOver = true;

                    String winnerOfGame = "";

                    if (currentPlayer == Player.ONE) {
                        winnerOfGame = "Player Two (Tiger)";
                    } else if (currentPlayer == Player.TWO) {
                        winnerOfGame = "Player One (Lion)";
                    }

                    Toast.makeText(this, winnerOfGame + " is the Winner", Toast.LENGTH_LONG).show();
                }else if((my_contains(playerChoices, Player.NO) == false) && gameOver == false) {
                    //If so, and gameOver is false, then its a tie.
                    gameOver = true;
                    Toast.makeText(this, "No Winner", Toast.LENGTH_LONG).show();
                    btnReset.setVisibility(View.VISIBLE);
                }
            }
        }


    }

    public static boolean my_contains(Player[] arr, Player item) {
        for (Player n : arr) {
            if (item == n) {
                return true;
            }
        }
        return false;
    }
    //Reset Game Function
    private void resetTheGame(){

        for (int index = 0; index < gridLayout.getChildCount(); index++ ){

            ImageView imageView = (ImageView)gridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }
        currentPlayer = Player.ONE;

        setPlayerNo();

        gameOver = false;




    }

    private void setPlayerNo(){

        for(int index = 0; index < playerChoices.length; index++){

            playerChoices[index] = Player.NO;
        }
    }
}
