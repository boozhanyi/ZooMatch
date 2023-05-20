package com.example.zoomatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gameplay extends AppCompatActivity {

    int[] animals = {R.drawable.ic_lion, R.drawable.ic_monkey, R.drawable.ic_tiger, R.drawable.ic_snake, R.drawable.ic_sheep};
    int widthOfBlock, noOfBlocks = 8, widthOfScreen;
    ArrayList<ImageView> animal = new ArrayList<>();
    int animalToBeDragged, animalToBeReplace;
    int notAnimal;
    private boolean activityStarted = false;
    int interval = 1000;
    TextView scoreText, moveText;
    ImageView star1, star2, star3;
    Handler mHandler = new Handler();
    public int final_score = 0;
    int move = 10;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        scoreText = findViewById(R.id.scoreText);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);

        moveText = findViewById(R.id.moveText);
        moveText.setText(Integer.toString(move));


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        widthOfScreen = displayMetrics.widthPixels;
        widthOfBlock = widthOfScreen / noOfBlocks;
        createBoard();
        for (ImageView imageView : animal) {
            imageView.setOnTouchListener(new OnSwipeListener(this) {
                @Override
                void OnSwipeLeft() {
                    super.OnSwipeLeft();
                    animalToBeDragged = imageView.getId();
                    animalToBeReplace = animalToBeDragged - 1;
                    animalInterchange();
                }

                @Override
                void OnSwipeRight() {
                    super.OnSwipeRight();
                    animalToBeDragged = imageView.getId();
                    animalToBeReplace = animalToBeDragged + 1;
                    animalInterchange();
                }

                @Override
                void OnSwipeTop() {
                    super.OnSwipeTop();
                    animalToBeDragged = imageView.getId();
                    animalToBeReplace = animalToBeDragged - noOfBlocks;
                    animalInterchange();
                }

                @Override
                void OnSwipeBottom() {
                    super.OnSwipeBottom();
                    animalToBeDragged = imageView.getId();
                    animalToBeReplace = animalToBeDragged + noOfBlocks;
                    animalInterchange();
                }
            });
        }
        mHandler = new Handler();
        startRepeat();
    }

    private void checkRow() {
        for (int i = 0; i < 62; i++) {
            int choseAnimal = (int) animal.get(i).getTag();
            boolean isBlank = (int) animal.get(i).getTag() == notAnimal;
            Integer[] notValid = {6, 7, 14, 15, 22, 23, 30, 31, 38, 39, 46, 47, 54, 55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i)) {
                int x = i;
                if ((int) animal.get(x++).getTag() == choseAnimal && !isBlank &&
                        (int) animal.get(x++).getTag() == choseAnimal &&
                        (int) animal.get(x).getTag() == choseAnimal) {
                    final_score = final_score + 3;
                    scoreText.setText(Integer.toString(final_score));
                    animal.get(x).setImageResource(notAnimal);
                    animal.get(x).setTag(notAnimal);
                    x--;
                    animal.get(x).setImageResource(notAnimal);
                    animal.get(x).setTag(notAnimal);
                    x--;
                    animal.get(x).setImageResource(notAnimal);
                    animal.get(x).setTag(notAnimal);
                }
            }
        }
        moveDownCandies();
        checkScore();
    }

    private void checkColumn() {
        for (int i = 0; i < 47; i++) {
            int choseAnimal = (int) animal.get(i).getTag();
            int x = i;
            if ((int) animal.get(x).getTag() == choseAnimal &&
                    (int) animal.get(x + noOfBlocks).getTag() == choseAnimal &&
                    (int) animal.get(x + 2 * noOfBlocks).getTag() == choseAnimal) {
                final_score = final_score + 3;
                scoreText.setText(Integer.toString(final_score));
                animal.get(i).setImageResource(notAnimal);
                animal.get(i).setTag(notAnimal);
                x = x + noOfBlocks;
                animal.get(x).setImageResource(notAnimal);
                animal.get(x).setTag(notAnimal);
                x = x + noOfBlocks;
                animal.get(x).setImageResource(notAnimal);
                animal.get(x).setTag(notAnimal);
            }
        }
        moveDownCandies();
        checkScore();
    }

    private void checkScore() {
        if (final_score >= 15 & final_score < 20)
            star1.setVisibility(View.VISIBLE);
        else if (final_score >= 20 & final_score < 30) {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
        } else if (final_score >= 30) {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
        }
    }

    private void moveDownCandies() {
        Integer[] firstRow = {1, 2, 3, 4, 5, 6, 7};
        List<Integer> list = Arrays.asList(firstRow);
        for (int i = 55; i >= 0; i--) {
            if ((int) animal.get(i + noOfBlocks).getTag() == notAnimal) {
                animal.get(i + noOfBlocks).setImageResource((int) animal.get(i).getTag());
                animal.get(i + noOfBlocks).setTag(animal.get(i).getTag());
                animal.get(i).setImageResource(notAnimal);
                animal.get(i).setTag(notAnimal);

                if (list.contains(i) && (int) animal.get(i).getTag() == notAnimal) {
                    int randomColor = (int) Math.floor(Math.random() * animals.length);
                    animal.get(i).setImageResource(animals[randomColor]);
                    animal.get(i).setTag(animals[randomColor]);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            if ((int) animal.get(i).getTag() == notAnimal) {
                int randomColor = (int) Math.floor(Math.random() * animals.length);
                animal.get(i).setImageResource(animals[randomColor]);
                animal.get(i).setTag(animals[randomColor]);
            }
        }
    }

    Runnable repeatChecker = new Runnable() {
        @Override
        public void run() {
            try {
                checkRow();
                checkColumn();
                if (move == 0 && !activityStarted) {
                    activityStarted = true;
                    String score = Integer.toString(final_score);
                    Intent intent = new Intent(Gameplay.this, Game_End.class);
                    intent.putExtra("final_score", score);
                    startActivity(intent);
                }
            } finally {
                mHandler.postDelayed(repeatChecker, interval);
            }
        }
    };

    void startRepeat() {
        repeatChecker.run();
    }

    /*public boolean checkSwipeMatch() {
        boolean check = false;
        int choseAnimal = (int) animal.get(animalToBeDragged).getTag();
        if (choseAnimal == (int) animal.get(animalToBeReplace + 1).getTag() && choseAnimal == (int) animal.get(animalToBeReplace + 2).getTag()) {
            check = true;
        } else if (choseAnimal == (int) animal.get(animalToBeReplace - 1).getTag() && choseAnimal == (int) animal.get(animalToBeReplace - 2).getTag()) {
            check = true;
        } else if (choseAnimal == (int) animal.get(animalToBeReplace - noOfBlocks).getTag() && choseAnimal == (int) animal.get(animalToBeReplace + noOfBlocks).getTag()) {
            check = true;
        } else if (choseAnimal == (int) animal.get(animalToBeReplace - noOfBlocks).getTag() && choseAnimal == (int) animal.get(animalToBeReplace - 2 * noOfBlocks).getTag()) {
            check = true;
        } else if (choseAnimal == (int) animal.get(animalToBeReplace + noOfBlocks).getTag() && choseAnimal == (int) animal.get(animalToBeReplace + 2 * noOfBlocks).getTag()) {
            check = true;
        } else if (choseAnimal == (int) animal.get(animalToBeReplace - 1).getTag() && choseAnimal == (int) animal.get(animalToBeReplace + 1).getTag()) {
            check = true;
        }
        return check;
    }*/

    private void animalInterchange() {
        int background = (int) animal.get(animalToBeReplace).getTag();
        int background1 = (int) animal.get(animalToBeDragged).getTag();
        animal.get(animalToBeDragged).setImageResource(background);
        animal.get(animalToBeReplace).setImageResource(background1);
        animal.get(animalToBeDragged).setTag(background);
        animal.get(animalToBeReplace).setTag(background1);
        move = move - 1;
        moveText.setText(Integer.toString(move));
    }


    public void createBoard() {
        GridLayout gridlayout = findViewById(R.id.gameLayout);
        gridlayout.setColumnCount(noOfBlocks);
        gridlayout.setRowCount(noOfBlocks);
        gridlayout.getLayoutParams().width = widthOfScreen;
        gridlayout.getLayoutParams().height = widthOfScreen;
        ArrayList<Integer> checkRandom = new ArrayList<>();
        for (int i = 0; i < noOfBlocks * noOfBlocks; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(widthOfBlock, widthOfBlock));
            imageView.setMaxHeight(widthOfBlock);
            imageView.setMaxWidth(widthOfBlock);

            int randomAnimal = (int) Math.floor(Math.random() * animals.length);

            if (checkRandom.size() > 2) {
                while (randomAnimal == checkRandom.get(i - 1) && randomAnimal == checkRandom.get(i - 2)) {
                    randomAnimal = (int) Math.floor(Math.random() * animals.length);
                }
            }

            if (checkRandom.size() > 16) {
                while (randomAnimal == checkRandom.get(i - noOfBlocks) && randomAnimal == checkRandom.get(i - 2 * noOfBlocks)) {
                    randomAnimal = (int) Math.floor(Math.random() * animals.length);
                }
            }
            checkRandom.add(randomAnimal);
            imageView.setImageResource(animals[randomAnimal]);
            imageView.setTag(animals[randomAnimal]);
            animal.add(imageView);
            gridlayout.addView(imageView);
        }
    }
}

