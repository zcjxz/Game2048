package com.game2048.game2048;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {
    private Card[][] cardsMap=new Card[4][4];
    private List<Point> emptyPoints=new ArrayList<Point>();
    private ScaleAnimation scaleAnimation;

    public GameView(Context context) {
        super(context);
        initView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth=(Math.min(w,h)-10)/4;
        addCards(cardWidth,cardWidth);
        startGame();
    }

    private void addCards(int cardWidth,int cardHeight){
        Card c;
        for (int y = 0; y < 4; y++) {
            for (int x=0;x<4;x++){
                c=new Card(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeight);
                cardsMap[x][y]=c;
            }
        }
    }
    private void initView(){
        this.setBackgroundColor(0xffbbada0);
        this.setColumnCount(4);

        scaleAnimation = new ScaleAnimation(0.5f,1,0.5f,1,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(100);


        setOnTouchListener(new OnTouchListener() {
            private float startX,startY,offsetX,offsetY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX=event.getX();
                        startY=event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        offsetX=event.getX()-startX;
                        offsetY=event.getY()-startY;

                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(offsetX)>Math.abs(offsetY)){
                            if (offsetX<-20){
                                //左划
                                swipeLeft();
                            }else if (offsetX>20){
                                //右划
                                swipeRight();
                            }
                        }else{
                            if (offsetY<-20){
                                //上划
                                swipeUp();
                            }else if (offsetY>20){
                                //下划
                                swipeDown();
                            }
                        }

                        break;
                }
                return true;
            }
        });
    }
    private void addRandomNum(){
        emptyPoints.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum()<=0){
                    emptyPoints.add(new Point(x,y));
                }
            }
        }
        Point p=emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
        cardsMap[p.x][p.y].startAnimation(scaleAnimation);
    }
    public void startGame(){
        MainActivity.getMainActivity().clearScore();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }
    private void swipeLeft(){
        boolean merge=false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1=x+1;x1<4;x1++){
                    if (cardsMap[x1][y].getNum()>0){

                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;
                            merge=true;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            addScore(cardsMap[x][y].getNum());
                            merge=true;
                        }
                        break;

                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeRight(){
        boolean merge=false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1=x-1;x1>=0;x1--){
                    if (cardsMap[x1][y].getNum()>0){

                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            merge=true;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            addScore(cardsMap[x][y].getNum());
                            merge=true;
                        }
                        break;

                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeUp(){
        boolean merge=false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1=y+1;y1<4;y1++){
                    if (cardsMap[x][y1].getNum()>0){

                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            merge=true;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            addScore(cardsMap[x][y].getNum());
                            merge=true;
                        }
                        break;

                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeDown(){
        boolean merge=false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >=0; y--) {
                for (int y1=y-1;y1>=0;y1--){
                    if (cardsMap[x][y1].getNum()>0){

                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            merge=true;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            addScore(cardsMap[x][y].getNum());
                            merge=true;
                        }
                        break;

                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }
    public void addScore(int addScore){
        MainActivity.getMainActivity().addScore(addScore);
        MainActivity.getMainActivity().checkTopScore();
    }


    private void checkComplete(){
        boolean complete=true;
        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum()==0||
//                        (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                        (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
//                        (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                        (y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){
                    complete=false;
                    break ALL;
                }
            }
        }
        if (complete){
            new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }
    public String addSuperNum(int row,int col,int num){
        if ((row>0&&row<5)&&
                (col>0&&col<5)&&
                (num==2||num==4||num==8||num==16||num==32||num==64||num==128||num==512||num==1024||num==2048)){
            row--;
            col--;
            cardsMap[row][col].setNum(num);
            return null;
        }else{
            return "你TM在逗我";
        }
    }
}
