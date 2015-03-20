package com.example.lddean.tab_to_do_list;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

public class MainActivity extends Activity {

    private ViewStub[] viewStub = new ViewStub[3];
    private Button currentBtn;
    private Button lastBtn;

    private int[] tabBtnIds = {R.id.todolist, R.id.donelist,
            R.id.member};

    private Button[] tabBtn = new Button[4];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewStub[0] = (ViewStub) findViewById(R.id.btn1ViewStub);
        viewStub[1] = (ViewStub) findViewById(R.id.btn2ViewStub);
        viewStub[2] =(ViewStub) findViewById(R.id.btn3ViewStub);
        //viewStub[3] = (ViewStub) findViewById(R.id.btn4ViewStub);
        currentBtn = (Button) findViewById(R.id.donelist);
        TabBtnClickListener tabBtnListener = new TabBtnClickListener();
        for(int i=0; i<tabBtnIds.length; i++) {
            tabBtn[i] = (Button) findViewById(tabBtnIds[i]);
            tabBtn[i].setOnClickListener(tabBtnListener);
        }

    }

    class TabBtnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            lastBtn = currentBtn;
            currentBtn = (Button) v;
            if(currentBtn.getId() == lastBtn.getId()) {
                return;
            }
            currentBtn.setBackgroundColor(Color.BLUE);
            lastBtn.setBackgroundColor(Color.GRAY);
            int currentIndex = -1;
            switch(currentBtn.getId()) {
                case R.id.todolist:
                    currentIndex = 0;
                    break;
                case R.id.donelist:
                    currentIndex = 1;
                    break;
                case R.id.member:
                    currentIndex = 2;
                    break;
                //case R.id.btn4:
                 //   currentIndex = 3;
                 //   break;
            }
            for(int i=0; i<viewStub.length; i++) {
                viewStub[i].setVisibility(View.INVISIBLE);
            }
            for(int i=0; i<viewStub.length; i++) {
                if(currentIndex == -1) {
                    break;
                }
                if(currentIndex != i) {
                    viewStub[i].setVisibility(View.INVISIBLE);
                } else {
                    viewStub[i].setVisibility(View.VISIBLE);
                }
            }
        }
    }
}