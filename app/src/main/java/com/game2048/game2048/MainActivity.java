package com.game2048.game2048;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    private TextView tv_score;

    static MainActivity mainActivity=null;
    private int score=0;
    private GameView gameView;
    private Button newGame;
    private LinearLayout scoreLayout;
    private long[] mHits=new long[3];
    private TextView topScore;
    private SharedPreferences sp;
    private int top_score;
    static String SP_TOP_SCORE="top_score";

    static MainActivity getMainActivity(){
        return mainActivity;
    }
    public MainActivity() {
        mainActivity=this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_score = (TextView) findViewById(R.id.tv_score);
        gameView = (GameView) findViewById(R.id.game_view);
        newGame = (Button) findViewById(R.id.newGame);
        scoreLayout = (LinearLayout) findViewById(R.id.score_layout);
        topScore = (TextView) findViewById(R.id.top_score);

        sp = getSharedPreferences("TopScore",MODE_PRIVATE);
        top_score = sp.getInt(SP_TOP_SCORE, 0);
        setTopScore(top_score);

        scoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.arraycopy(mHits,1,mHits,0,mHits.length-1);
                mHits[mHits.length-1]= SystemClock.uptimeMillis();
                if(mHits[0]>=(SystemClock.uptimeMillis()-500)){
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("欢迎来到作弊器");
                    View superView=View.inflate(MainActivity.this,R.layout.super_dialog,null);
                    builder.setView(superView);
                    final EditText et_row = (EditText) superView.findViewById(R.id.super_row);
                    final EditText et_col = (EditText) superView.findViewById(R.id.super_col);
                    final EditText et_num = (EditText) superView.findViewById(R.id.super_num);
                    builder.setPositiveButton("出来吧，数字！", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String row_string=et_row.getText().toString().trim();
                            String col_string = et_col.getText().toString().trim();
                            String num_string = et_num.getText().toString().trim();

                            if (TextUtils.isEmpty(row_string)||TextUtils.isEmpty(col_string)||TextUtils.isEmpty(num_string)){
                                Toast.makeText(MainActivity.this, "虽然是作弊，也请对我认真点！", Toast.LENGTH_SHORT).show();
                            }else {
                                int row=Integer.parseInt(row_string);
                                int col=Integer.parseInt(col_string);
                                int num=Integer.parseInt(num_string);
                                String s = gameView.addSuperNum(row, col, num);
                                if (s != null) {
                                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.setNegativeButton("我需要作弊吗？辣鸡", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.startGame();
            }
        });
    }

    public void clearScore(){
        score = 0;
        showScore();
    }

    public void addScore(int addScore){
        score+=addScore;
        showScore();
    }

    public void showScore(){
        tv_score.setText(score+"");
    }

    public void setTopScore(int score){
        topScore.setText("Top: "+score);
    }

    public void checkTopScore(){
        int now_score = Integer.parseInt(tv_score.getText().toString());
        if (now_score>top_score){
            setTopScore(now_score);
            sp.edit().putInt(SP_TOP_SCORE,now_score).commit();
        }
    }
}
