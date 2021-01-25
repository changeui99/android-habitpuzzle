package com.changeui.habbitpuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class PuzzleActivityView extends AppCompatActivity implements PuzzleActivityPresenter.View {

    ImageView cancelButton;
    ImageView imagebg;
    private Intent intent;
    private RelativeLayout rootlayout;
    private PuzzleActivityPresenter presenter;
    private ImageView preview;

    private Puzzle2 puzzles[][] = new Puzzle2[5][9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        intent = getIntent();

        presenter = new PuzzleActivityPresenter(this, intent.getIntExtra("id",0));
        presenter.setHabitData(intent.getLongExtra("habitid", 0));
        rootlayout = findViewById(R.id.rootlayout);

        cancelButton = findViewById(R.id.cancelButton);
        imagebg = findViewById(R.id.imagebg);
        preview = findViewById(R.id.preview);

        setUI();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setUI() {
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        imagebg.getLayoutParams().width = presenter.getNewwidth();
        imagebg.getLayoutParams().height = presenter.getNewheight();

        for (int i = 0; i < 45; i++){
            if (presenter.getpuzzleState(i) == 2){
                Puzzle2 temppuzzle = new Puzzle2(this, presenter.getpuzzleWidth(), presenter.getImage(i), true,
                        i / 9, i % 9, presenter.getbgMarginRight(), presenter.getbgMarginTop(), 2);

                puzzles[i / 9][i % 9] = temppuzzle;

                rootlayout.addView(temppuzzle);
            }
        }
        for (int i = 0; i < 45; i++){
            if (presenter.getpuzzleState(i) != 2 && presenter.getpuzzleState(i) != 0){
                Puzzle2 temppuzzle = new Puzzle2(this, presenter.getpuzzleWidth(), presenter.getImage(i), false,
                        i / 9, i % 9, presenter.getbgMarginRight(), presenter.getbgMarginTop(), presenter.getpuzzleState(i));

                puzzles[i / 9][i % 9] = temppuzzle;

                rootlayout.addView(temppuzzle);
            }
        }

        for (int i = 0; i < 45; i++){
            if (puzzles[i/9][i%9] != null){
                puzzles[i/9][i%9].setPosition();
            }
        }
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View customLayout = this.getLayoutInflater().inflate(R.layout.preview_layout, null);
        builder.setView(customLayout);

        final AlertDialog dialog = builder.create();

        ImageView collectionimage = customLayout.findViewById(R.id.collectionimage);

        collectionimage.setImageResource(presenter.getImage());

        dialog.show();
    }

    public class Puzzle2 extends RelativeLayout {
        private int _xDelta;
        private int _yDelta;
        private int puzzleWidth;
        private boolean isFit;
        private int image, i, j, marginleft, margintop;
        private int positionx, positiony;
        private touchArea toucharea;
        private puzzleImage puzzleImage;

        private ArrayList<Puzzle2> relatedPuzzles = new ArrayList<>();
        private boolean isRight = false, isLeft = false, isTop = false, isBottom = false;

        public Puzzle2(Context context, int puzzleWidth, int image, boolean isFit, int i, int j, int marginleft, int margintop, int state) {
            super(context);
            this.puzzleWidth = puzzleWidth;
            this.image = image;
            this.isFit = isFit;
            this.i = i;
            this.j = j;
            this.marginleft = marginleft;
            this.margintop = margintop;

            if (state == 3 || state == 7 || state == 8 || state == 9 || state == 13 || state == 14 || state == 15 || state == 17) {
                isTop = true;
            }

            if (state == 4 || state == 7 || state == 10 || state == 11 || state == 13 || state == 14 || state == 16 || state == 17) {
                isLeft = true;
            }

            if (state == 5 || state == 8 || state == 10 || state == 12 || state == 13 || state == 15 || state == 16 || state == 17) {
                isBottom = true;
            }

            if (state == 6 || state == 9 || state == 11 || state == 12 || state == 14 || state == 15 || state == 16 || state == 17) {
                isRight = true;
            }

            relatedPuzzles.add(this);

            toucharea = new touchArea(context, isFit, puzzleWidth / 2, this);
            puzzleImage = new puzzleImage(context, puzzleWidth, image);

            addView(puzzleImage);
            addView(toucharea);

            if (state != 2){
                puzzleImage.setVisibility(INVISIBLE);
            }

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(puzzleWidth, puzzleWidth);

            int marginX = 0;
            int marginY = 0;

            positionx = marginleft + i * puzzleWidth / 2;
            positiony = margintop + j * puzzleWidth / 2;

            if (state == 2) {
                marginX = positionx;
                marginY = positiony;
            } else {
                marginX = new Random().nextInt(presenter.getwidth() - puzzleWidth);
                marginY = new Random().nextInt(presenter.getbgHeight() - puzzleWidth);
            }


            layoutParams.leftMargin = marginX;
            layoutParams.topMargin = marginY;
            layoutParams.rightMargin = -puzzleWidth * 3;
            layoutParams.bottomMargin = -puzzleWidth * 3;

            setLayoutParams(layoutParams);
        }

        public void isPositioned(RelativeLayout.LayoutParams layoutParams){
            int x = layoutParams.leftMargin;
            int y = layoutParams.topMargin;
            int distance = 20;

            if (Math.abs(x - positionx) < 15 && Math.abs(y - positiony) < 15){
                layoutParams.leftMargin = positionx;
                layoutParams.topMargin = positiony;
                isFit = true;

                this.setLayoutParams(layoutParams);
                presenter.setpuzzleState(i, j, getState());

                toucharea.changeTouchable(isFit);
            }

            if (!isFit) {
                boolean move = false;

                if (i < 4 && !isRight){
                    if (puzzles[i+1][j] != null){
                        RelativeLayout.LayoutParams templayout = (RelativeLayout.LayoutParams) puzzles[i+1][j].getLayoutParams();
                        int tempX = templayout.leftMargin - puzzleWidth / 2;
                        int tempY = templayout.topMargin;

                        if (Math.abs(x - tempX) < distance && Math.abs(y - tempY) < distance){
                            if (!move){
                                MoveLayout(tempX - x, tempY - y);
                                move = true;
                            }

                            setRight(true);
                            puzzles[i+1][j].setLeft(true);
                            addRelative(puzzles[i+1][j]);
                        }
                    }
                }

                if (j < 8 && !isBottom){
                    if (puzzles[i][j + 1] != null){
                        RelativeLayout.LayoutParams templayout = (RelativeLayout.LayoutParams) puzzles[i][j + 1].getLayoutParams();
                        int tempX = templayout.leftMargin;
                        int tempY = templayout.topMargin - puzzleWidth / 2;

                        if (Math.abs(x - tempX) < distance && Math.abs(y - tempY) < distance){
                            if (!move){
                                MoveLayout(tempX - x, tempY - y);
                                move = true;
                            }

                            setBottom(true);
                            puzzles[i][j + 1].setTop(true);
                            addRelative(puzzles[i][j + 1]);
                        }
                    }
                }

                if (i > 0 && !isLeft){
                    if (puzzles[i-1][j] != null){
                        RelativeLayout.LayoutParams templayout = (RelativeLayout.LayoutParams) puzzles[i-1][j].getLayoutParams();
                        int tempX = templayout.leftMargin + puzzleWidth / 2;
                        int tempY = templayout.topMargin;

                        if (Math.abs(x - tempX) < distance && Math.abs(y - tempY) < distance){
                            if (!move){
                                MoveLayout(tempX - x, tempY - y);
                                move = true;
                            }

                            setLeft(true);
                            puzzles[i-1][j].setRight(true);
                            addRelative(puzzles[i-1][j]);
                        }
                    }
                }

                if (j > 0 && !isTop){
                    if (puzzles[i][j - 1] != null){
                        RelativeLayout.LayoutParams templayout = (RelativeLayout.LayoutParams) puzzles[i][j - 1].getLayoutParams();
                        int tempX = templayout.leftMargin;
                        int tempY = templayout.topMargin + puzzleWidth / 2;

                        if (Math.abs(x - tempX) < distance && Math.abs(y - tempY) < distance){
                            if (!move){
                                MoveLayout(tempX - x, tempY - y);
                                move = true;
                            }

                            setTop(true);
                            puzzles[i][j - 1].setBottom(true);
                            addRelative(puzzles[i][j - 1]);
                        }
                    }
                }
            }
        }

        public void MoveLayout(int x, int y){
            for (Puzzle2 puzzle : relatedPuzzles) {
                RelativeLayout.LayoutParams tempLayout = (RelativeLayout.LayoutParams) puzzle.getLayoutParams();
                int currentX = tempLayout.leftMargin;
                int currentY = tempLayout.topMargin;

                tempLayout.leftMargin = currentX + x;
                tempLayout.topMargin = currentY + y;

                puzzle.setLayoutParams(tempLayout);
            }
        }

        public void addRelative(Puzzle2 puzzle2){
            for (Puzzle2 puzzle : puzzle2.getRelatedPuzzles()) {
                if (!relatedPuzzles.contains(puzzle)){
                    relatedPuzzles.add(puzzle);
                }
            }

            for (Puzzle2 puzzle : relatedPuzzles) {
                if (puzzle != this){
                    puzzle.setRelatedPuzzles(relatedPuzzles);
                }
            }
        }

        public ArrayList<Puzzle2> getRelatedPuzzles() {
            return relatedPuzzles;
        }

        public void setRelatedPuzzles(ArrayList<Puzzle2> relatedPuzzles) {
            this.relatedPuzzles = relatedPuzzles;
        }

        public void setLeft(boolean b){
            isLeft = b;
            presenter.setpuzzleState(i, j, getState());
        }

        public void setRight(boolean b){
            isRight = b;
            presenter.setpuzzleState(i, j, getState());
        }

        public void setTop(boolean b){
            isTop = b;
            presenter.setpuzzleState(i, j, getState());
        }

        public void setBottom(boolean b){
            isBottom = b;
            presenter.setpuzzleState(i, j, getState());
        }

        public void functionActionDown(int X, int Y){
            for (Puzzle2 puzzle : relatedPuzzles){
                puzzle.functionActionDown2(X, Y);
            }
        }

        public void functionActionDown2(int X, int Y){
            RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) this.getLayoutParams();
            _xDelta = X - lParams.leftMargin;
            _yDelta = Y - lParams.topMargin;
        }

        public void functionActionMove(int X, int Y){
            for (Puzzle2 puzzle : relatedPuzzles){
                puzzle.functionActionMove2(X, Y);
            }
        }

        public void functionActionMove2(int X, int Y){
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.getLayoutParams();
            layoutParams.leftMargin = X - _xDelta;
            layoutParams.topMargin = Y - _yDelta;
            layoutParams.rightMargin = -puzzleWidth;
            layoutParams.bottomMargin = -puzzleWidth;
            this.setLayoutParams(layoutParams);
        }

        public void functionActionUp(){
            ArrayList<Puzzle2> temparray = new ArrayList<>();
            temparray.addAll(relatedPuzzles);

            for (Puzzle2 puzzle : temparray){
                puzzle.isPositioned((RelativeLayout.LayoutParams) puzzle.getLayoutParams());
            }
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }

        public void setPosition(){
            if (puzzleImage.getVisibility() == INVISIBLE){
                setRelatedPuzzles(findRel(new ArrayList<Puzzle2>()));

                puzzleImage.setVisibility(VISIBLE);
                RelativeLayout.LayoutParams layout = (RelativeLayout.LayoutParams) this.getLayoutParams();

                for (Puzzle2 puzzle : relatedPuzzles) {
                    if (puzzle != this) {
                        int x = this.i - puzzle.getI();
                        int y = this.j - puzzle.getJ();

                        RelativeLayout.LayoutParams templayout = (RelativeLayout.LayoutParams) puzzle.getLayoutParams();

                        templayout.leftMargin = layout.leftMargin - (puzzleWidth / 2) * x;
                        templayout.topMargin = layout.topMargin - (puzzleWidth / 2) * y;

                        puzzle.setLayoutParams(templayout);
                    }
                }
            }
        }

        public ArrayList<Puzzle2> findRel(ArrayList<Puzzle2> array){
            array.add(this);

            if (isTop){
                if (!array.contains(puzzles[i][j-1])){
                    array = puzzles[i][j-1].findRel(array);
                }
            }

            if (isLeft) {
                if (!array.contains(puzzles[i-1][j])){
                    array = puzzles[i-1][j].findRel(array);
                }
            }

            if (isBottom){
                if (!array.contains(puzzles[i][j+1])){
                    array = puzzles[i][j+1].findRel(array);
                }
            }

            if (isRight) {
                if (!array.contains(puzzles[i+1][j])){
                    array = puzzles[i+1][j].findRel(array);
                }
            }

            return array;
        }

        public class puzzleImage extends AppCompatImageView {
            private int puzzleWidth;
            private int image;

            public puzzleImage(Context context, int puzzleWidth, int image) {
                super(context);
                this.puzzleWidth = puzzleWidth;
                this.image = image;

                setImageResource(image);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(puzzleWidth, puzzleWidth);
                setLayoutParams(layoutParams);
            }
        }

        public class touchArea extends RelativeLayout {
            private int areawidth;
            private boolean isFit;
            private Puzzle2 parent;

            public touchArea(Context context, boolean isFit, int areawidth, Puzzle2 parent) {
                super(context);
                this.areawidth = areawidth;
                this.isFit = isFit;
                this.parent = parent;

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(areawidth, areawidth);

                layoutParams.leftMargin = areawidth / 2;
                layoutParams.topMargin = areawidth / 2;

                setLayoutParams(layoutParams);
                if (!isFit){
                    setOnTouchListener(new ChoiceTouchListener());
                }
            }

            public void changeTouchable(boolean a){
                if (!a) {
                    setOnTouchListener(new ChoiceTouchListener());
                } else {
                    setOnTouchListener(null);
                }
            }

            private final class ChoiceTouchListener implements View.OnTouchListener {

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    final int X = (int) motionEvent.getRawX();
                    final int Y = (int) motionEvent.getRawY();

                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_DOWN :
                            parent.functionActionDown(X, Y);
                            break;
                        case MotionEvent.ACTION_UP:
                            parent.functionActionUp();
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            parent.functionActionMove(X, Y);
                            break;
                    }

                    rootlayout.invalidate();
                    return true;
                }
            }
        }

        public int getState() {
            if (isFit){
                return 2;
            }

            if (!isTop && !isLeft && !isBottom && !isRight) {
                return 1;
            }

            if (isTop && !isLeft && !isBottom && !isRight) {
                return 3;
            }

            if (!isTop && isLeft && !isBottom && !isRight) {
                return 4;
            }

            if (!isTop && !isLeft && isBottom && !isRight) {
                return 5;
            }

            if (!isTop && !isLeft && !isBottom && isRight) {
                return 6;
            }

            if (isTop && isLeft && !isBottom && !isRight) {
                return 7;
            }

            if (isTop && !isLeft && isBottom && !isRight) {
                return 8;
            }

            if (isTop && !isLeft && !isBottom && isRight) {
                return 9;
            }

            if (!isTop && isLeft && isBottom && !isRight) {
                return 10;
            }

            if (!isTop && isLeft && !isBottom && isRight) {
                return 11;
            }

            if (!isTop && !isLeft && isBottom && isRight) {
                return 12;
            }

            if (isTop && isLeft && isBottom && !isRight) {
                return 13;
            }

            if (isTop && isLeft && !isBottom && isRight) {
                return 14;
            }

            if (isTop && !isLeft && isBottom && isRight) {
                return 15;
            }

            if (!isTop && isLeft && isBottom && isRight) {
                return 16;
            }

            if (isTop && isLeft && isBottom && isRight) {
                return 17;
            }

            return 1;
        }
    }
}