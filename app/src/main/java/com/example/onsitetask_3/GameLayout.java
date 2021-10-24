package com.example.onsitetask_3;

import static com.example.onsitetask_3.MainActivity.start;
import static com.example.onsitetask_3.MainActivity.msgtv;
import static com.example.onsitetask_3.MainActivity.timetv;
import static java.lang.Math.random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameLayout extends View {

    Paint red,blue,green;
    int height, Width,touched,length,width;
    boolean firstTime=true,done=false;
    int[] mid_x,mid_y,left_x,left_y,right_x,right_y,r;
    CountDownTimer timer;

    public GameLayout(Context context) {
        super(context);
        mid_x=new int[6];
        mid_y=new int[6];
        left_x=new int[6];
        left_y=new int[6];
        right_x=new int[6];
        right_y=new int[6];
        r=new int[6];
        length=600;
        width=60;

    }

    public GameLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mid_x=new int[6];
        mid_y=new int[6];
        left_x=new int[6];
        left_y=new int[6];
        right_x=new int[6];
        right_y=new int[6];
        r=new int[6];
        length=600;
        width=60;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        brushes();
        height=getHeight();
        Width =getWidth();
        for(int i=0;i<6;i++){
            if(firstTime) {
                mid_x[i] = Width / 2;
                mid_y[i] = 150 * (i + 1) + 5;
            }
            left_x[i]=mid_x[i]-length/2;
            left_y[i]=mid_y[i]-width/2;
            right_x[i]=mid_x[i]+length/2;
            right_y[i]=mid_y[i]+width/2;
        }
        if(firstTime){
            firsttimetodo();
        }

        canvas.drawRect(left_x[r[0]],left_y[r[0]],right_x[r[0]],right_y[r[0]],red);
        canvas.drawRect(left_x[r[1]],left_y[r[1]],right_x[r[1]],right_y[r[1]],red);
        canvas.drawRect(left_x[r[2]],left_y[r[2]],right_x[r[2]],right_y[r[2]],green);
        canvas.drawRect(left_x[r[3]],left_y[r[3]],right_x[r[3]],right_y[r[3]],green);
        canvas.drawRect(left_x[r[4]],left_y[r[4]],right_x[r[4]],right_y[r[4]],blue);
        canvas.drawRect(left_x[r[5]],left_y[r[5]],right_x[r[5]],right_y[r[5]],blue);
        if(firstTime && check()){
            firstTime=true;
        }
        else{
            firstTime=false;
        }
        if(!done)
        invalidate();


    }

    void firsttimetodo(){
        msgtv.setText("Group similar coloured wires together");
        ArrayList<Integer> indices=new ArrayList<>();
        for (int i=0;i<6;i++){
            do{
                r[i]=(randbw(0,6));
            }while (indices.contains(r[i]));
            indices.add(r[i]);
        }

        timetv.setTextColor(Color.parseColor("#EAE9EE"));
        timer=new CountDownTimer(5000,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timetv.setText(String.valueOf(millisUntilFinished/1000)+":"+String.valueOf(millisUntilFinished/1000-millisUntilFinished));
            }

            @Override
            public void onFinish() {

                timetv.setText("0:0");
                timetv.setTextColor(Color.parseColor("#990000"));
                msgtv.setText("Time's Up");
                endgame();
            }
        }.start();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            int touched_x = (int) event.getX();
            int touched_y = (int) event.getY();
            for(int i=0;i<6;i++) {
                if (touched_x < right_x[i] && touched_x > left_x[i] && touched_y < right_y[i] && touched_y > left_y[i]) {
                    touched = i;
                }
            }
        }
        if(event.getAction()==MotionEvent.ACTION_MOVE) {
            if (touched != -1) {
                int touched_x = (int) event.getX();
                int touched_y = (int) event.getY();
                mid_x[touched] = touched_x;
                mid_y[touched] = touched_y;
                if(mid_x[touched]>Width-length/2) {
                    mid_x[touched] = Width - length / 2;
                }
                else if(mid_x[touched]<length/2) {
                    mid_x[touched] = length / 2;
                }
                else if(mid_y[touched]>height-width/2) {
                    mid_y[touched] = height - width / 2;
                }
                else if(mid_y[touched]<width/2) {
                    mid_y[touched] = width / 2;
                }
            }
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
            touched=-1;
            if(check()){
                msgtv.setText("Done");
                timer.cancel();
                endgame();

            }
        }




        return true;
    }

    Boolean check(){
        for(int i=0;i<6;i++){
            if((mid_y[r[0]]<mid_y[i] && mid_y[r[1]]>mid_y[i]) || (mid_y[r[1]]<mid_y[i] && mid_y[r[0]]>mid_y[i])){
                return false;
            }
            if((mid_y[r[2]]<mid_y[i] && mid_y[r[3]]>mid_y[i]) || (mid_y[r[3]]<mid_y[i] && mid_y[r[2]]>mid_y[i])){
                return false;
            }
            if((mid_y[r[4]]<mid_y[i] && mid_y[r[5]]>mid_y[i]) || (mid_y[r[5]]<mid_y[i] && mid_y[r[4]]>mid_y[i])){
                return false;
            }
        }
        return true;
    }

    void brushes(){
        red = new Paint();
        red.setColor(Color.parseColor("#990000"));
        red.setStyle(Paint.Style.FILL);
        blue=new Paint();
        blue.setColor(Color.parseColor("#000099"));
        blue.setStyle(Paint.Style.FILL);
        green=new Paint();
        green.setColor(Color.parseColor("#009900"));
        blue.setStyle(Paint.Style.FILL);
    }

    public int randbw ( int start, int end){
        return start + (int) (random() * (end - start));
    }
    void endgame(){
        start.setText("Restart");
        done=true;
        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                msgtv.setText("Group similar coloured wires together");
                if(timer!=null)
                    timer.cancel();
                done=false;
                firstTime=true;
                invalidate();
            }
        });
    }

}
