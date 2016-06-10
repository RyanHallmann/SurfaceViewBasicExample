package com.example.mrb.surfaceviewbasicexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnDraw;
    Button btnReset;
    SurfaceViewPanel svPanel;
    BouncyRectangle bncyRect;
    boolean blnKeepMoving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDraw = (Button) findViewById(R.id.btnDraw);
        btnReset = (Button) findViewById(R.id.btnReset);
        svPanel = (SurfaceViewPanel) findViewById(R.id.svPanel);

        //bncyRect = svPanel.getRect();

        bncyRect = new BouncyRectangle(100, 100, 200, 200, svPanel);
        svPanel.setRect(bncyRect);

        blnKeepMoving = true;

        // This line will call onDraw() in SurfaceViewPanel
        svPanel.invalidate();
    }

    public void StopMotion(View vw)
    {
        blnKeepMoving = false;
    }

    public void StartMotion(View vw)
    {
        //bncyRect.nextStep();
        //svPanel.invalidate();
        new BouncyAnimator().start();

    }

    class BouncyAnimator extends Thread
    {
        @Override
        public void run()
        {
            while(blnKeepMoving)
            {
                bncyRect.nextStep();

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                svPanel.postInvalidate();
            }

        }
    }

}
