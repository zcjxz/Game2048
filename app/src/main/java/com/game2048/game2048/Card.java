package com.game2048.game2048;


import android.content.Context;
import android.view.Gravity;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;


public class Card extends FrameLayout {
    int num=0;
    private  TextView label;


    public Card(Context context) {
        super(context);

        label=new TextView(getContext());
        label.setTextSize(32);
        label.setGravity(Gravity.CENTER);
        label.setBackgroundColor(0x33ffffff);
        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(-1,-1);
        params.setMargins(10,10,0,0);
        addView(label,params);
        setNum(0);
    }
    public int getNum(){
        return  num;
    }
    public void setNum(int num){
        this.num=num;
        if (num>0) {
            label.setText(num + "");
        }else{
            label.setText("");
        }

        switch (num){
            case 0:
                label.setBackgroundColor(getResources().getColor(R.color.number0));
                break;
            case 2:
                label.setBackgroundColor(getResources().getColor(R.color.number2));
                break;
            case 4:
                label.setBackgroundColor(getResources().getColor(R.color.number4));
                break;
            case 8:
                label.setBackgroundColor(getResources().getColor(R.color.number8));
                break;
            case 16:
                label.setBackgroundColor(getResources().getColor(R.color.number16));
                break;
            case 32:
                label.setBackgroundColor(getResources().getColor(R.color.number32));
                break;
            case 64:
                label.setBackgroundColor(getResources().getColor(R.color.number64));
                break;
            case 128:
                label.setBackgroundColor(getResources().getColor(R.color.number128));
                break;
            case 512:
                label.setBackgroundColor(getResources().getColor(R.color.number512));
                break;
            case 1024:
                label.setBackgroundColor(getResources().getColor(R.color.number1024));
                break;
            case 2048:
                label.setBackgroundColor(getResources().getColor(R.color.number2048));
                break;
        }

    }


    public boolean equals(Card o) {
        return getNum()==o.getNum();
    }
}
