package com.example.mrb.surfaceviewbasicexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by mrb on 16-06-04.
 */
public class SurfaceViewPanel extends SurfaceView implements SurfaceHolder.Callback
{

    final SurfaceHolder srfcHolder;

    Paint pntColor1;    // Painting object to handle the paint jobs for graphics

    private DrawingThread drawThread;
    private Bitmap btMap;

    private BouncyRectangle bRect;

    public SurfaceViewPanel(Context context)
    {
        super(context);

        srfcHolder = getHolder();
        srfcHolder.addCallback(this);

        init();
    }

    public SurfaceViewPanel(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        srfcHolder = getHolder();
        srfcHolder.addCallback(this);

        init();
    }

    private void init()
    {
        pntColor1 = new Paint();

        pntColor1.setColor(Color.BLUE);

        //bRect = new BouncyRectangle(100, 100, 200, 200);
    }

    public void setRect(BouncyRectangle bRect_PARAM)
    {
        this.bRect = bRect_PARAM;
    }

    public BouncyRectangle getRect()
    {
        return bRect;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(btMap, 0, 0, null);
    }

    private void drawAppGraphics(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        canvas.drawRect(bRect.getLeftEdge(), bRect.getTopEdge(), bRect.getRightEdge(), bRect.getBottomEdge(), pntColor1);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false);

        getThread().setRunning(true);
        getThread().start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(btMap == null)
        {
            btMap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            getThread().setBitmap(btMap, true);
        }
        else
        {
            // getThread().setBitmap(btMap, false);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        getThread().setRunning(false);

        boolean retry = true;

        while(retry)
        {
            try
            {
                getThread().join();
                retry = false;
            }
            catch(InterruptedException iEx)
            {
                iEx.printStackTrace();
            }
        }

        drawThread = null;
    }

    public DrawingThread getThread()
    {
        if(drawThread == null)
        {
            drawThread = new DrawingThread(this);
        }

        return drawThread;
    }

    class DrawingThread extends Thread
    {
        private SurfaceViewPanel svPanel;
        private boolean blnRunning = false;
        private Bitmap threadBitmap;
        private Canvas threadCanvas;

        public DrawingThread(SurfaceViewPanel csvPanel_PARAM)
        {
            this.svPanel = csvPanel_PARAM;
        }

        public void setRunning(boolean blnRunning_PARAM)
        {
            this.blnRunning = blnRunning_PARAM;
        }

        public void setBitmap(Bitmap btMap_PARAM, boolean blnClear)
        {
            threadBitmap = btMap_PARAM;

            /*
            if(blnClear)
            {
                threadBitmap.eraseColor(Color.WHITE);
            }
            */

            threadCanvas = new Canvas(threadBitmap);
        }

        @Override
        public void run()
        {
            //Canvas threadCanvas;

            while (blnRunning)
            {
                threadCanvas = null;

                try
                {
                    threadCanvas = srfcHolder.lockCanvas();

                    synchronized (srfcHolder)
                    {
                        if(threadCanvas != null)
                        {
                            drawAppGraphics(threadCanvas);
                            svPanel.postInvalidate();
                        }
                    }
                }
                finally
                {
                    if(threadCanvas != null)
                    {
                        srfcHolder.unlockCanvasAndPost(threadCanvas);
                    }
                }
            }
        }

    }


}
