package com.ptasek.vpaapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

enum InputException{
    NOIP,
    NOMASK,
    WRONGMASK,
    WRONGIP
}

public class MainActivity extends AppCompatActivity{

    TextView labelBitMask, labelNetwork, labelFirstIp, labelLastIp, labelBroadcast, labelSum, labelClass, labelVisibility;
    EditText inputIp, inputMask;

    IpCalc ipCalc = new IpCalc();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        labelBitMask = findViewById(R.id.labelBitMask);
        labelNetwork = findViewById(R.id.labelNetwork);
        labelFirstIp = findViewById(R.id.labelFirstAddress);
        labelBroadcast = findViewById(R.id.labelBroadcast);
        labelLastIp = findViewById(R.id.labelLastAddress);
        labelClass = findViewById(R.id.labelClass);
        labelSum = findViewById(R.id.labelSum);
        labelVisibility = findViewById(R.id.labelVisibility);

        inputIp = findViewById(R.id.inputAddress);
        inputMask = findViewById(R.id.inputMask);

    }


    @SuppressLint("SetTextI18n")
    public void onCalculate(View view){
        if(inputMask.getText().toString().equals("")){
            errorHandle(InputException.NOMASK);
        } else {
            int mask = Integer.parseInt(inputMask.getText().toString());
            if (mask < 0 || mask > 32) {
                errorHandle(InputException.WRONGMASK);
            }
            else {
                labelBitMask.setText(ipCalc.setMaskBits(mask));
            }
        }
        if(inputIp.getText().toString().equals("")){
            errorHandle(InputException.NOIP);
        } else{
            String ip = inputIp.getText().toString();
            if(ipCalc.checkIp(ip)) {
                labelNetwork.setText(ipCalc.setNetwork());
                labelFirstIp.setText(ipCalc.setFirstAddress());
                labelBroadcast.setText(ipCalc.setBroadcast());
                labelLastIp.setText(ipCalc.setLastAddress());
                labelClass.setText(ipCalc.setAddressClass());
                labelSum.setText(ipCalc.setSum());
                labelVisibility.setText(ipCalc.setVisibility());
            } else errorHandle(InputException.WRONGIP);
        }
    }


    @SuppressLint("SetTextI18n")
    public void handleReset(View view){
        labelNetwork.setText("");
        labelBitMask.setText("");
        labelVisibility.setText("");
        labelClass.setText("");
        labelLastIp.setText("");
        labelFirstIp.setText("");
        labelBroadcast.setText("");
        labelSum.setText("");
        inputIp.setText("");
        inputMask.setText("");
    }

    @SuppressLint("SetTextI18n")
    private void errorHandle(InputException error) {
        switch (error) {
            case NOMASK:
                inputMask.setHintTextColor(Color.RED);
                new CountDownTimer(3000, 3) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        inputMask.setHintTextColor(Color.parseColor("#FF8E8A8A"));
                    }
                }.start();
                break;
            case NOIP:
                inputIp.setHintTextColor(Color.RED);
                new CountDownTimer(3000, 3) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        inputIp.setHintTextColor(Color.parseColor("#FF8E8A8A"));
                    }
                }.start();
                break;
            case WRONGIP:
                inputIp.setText("");
                inputIp.setHintTextColor(Color.RED);
                inputIp.setHint("Wrong IP");
                new CountDownTimer(3000, 3){

                    @Override
                    public void onTick(long l) {}

                    @Override
                    public void onFinish() {
                        inputIp.setHintTextColor(Color.parseColor("#FF8E8A8A"));
                        inputIp.setHint("IP Address");
                    }
                }.start();
                break;
            case WRONGMASK:
                labelBitMask.setTextColor(Color.RED);
                labelBitMask.setText("Wrong mask");
                new CountDownTimer(3000, 3){

                    @Override
                    public void onTick(long l) {}

                    @Override
                    public void onFinish() {
                        labelBitMask.setTextColor(Color.WHITE);
                        labelBitMask.setText("");
                    }
                }.start();
                break;
        }
    }


}