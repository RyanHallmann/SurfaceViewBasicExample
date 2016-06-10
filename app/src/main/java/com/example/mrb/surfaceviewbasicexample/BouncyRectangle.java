package com.example.mrb.surfaceviewbasicexample;

import android.graphics.Rect;
import android.graphics.drawable.shapes.RectShape;

/**
 * Created by mrb on 16-06-04.
 */
public class BouncyRectangle extends RectShape
{
    private float fltLeftEdge;
    private float fltTopEdge;

    private float fltWidth;
    private float fltHeight;

    private float fltRightEdge;
    private float fltBottomEdge;

    private float fltStepX;
    private float fltStepY;

    private SurfaceViewPanel svPanel;

    public BouncyRectangle(float fltLeft_PARAM, float fltTop_PARAM, float fltWidth_PARAM, float fltHeight_PARAM, SurfaceViewPanel svPanel_PARAM)
    {
        this.fltLeftEdge = fltLeft_PARAM;
        this.fltTopEdge = fltTop_PARAM;

        this.fltWidth = fltWidth_PARAM;
        this.fltHeight = fltHeight_PARAM;

        this.fltRightEdge = fltLeftEdge + fltWidth;
        this.fltBottomEdge = fltTopEdge + fltHeight;

        fltStepX = (float) Math.random() * 5;
        fltStepY = (float) Math.random() * 5;

        svPanel = svPanel_PARAM;
    }



    public float getTopEdge()
    {
        return fltTopEdge;
    }

    public float getLeftEdge()
    {
        return fltLeftEdge;
    }

    public float getRightEdge()
    {
        return fltRightEdge;
    }

    public float getBottomEdge()
    {
        return fltBottomEdge;
    }

    public void nextStep()
    {
        if((
                (fltRightEdge <= (svPanel.getWidth() - fltStepX))

                &&
                        (fltBottomEdge <= (svPanel.getHeight() - fltStepY)))

            &&

        ((fltLeftEdge + fltStepX >= 0) && (fltTopEdge + fltStepY >= 0)))
        {
            fltLeftEdge = fltLeftEdge + fltStepX;
            fltTopEdge = fltTopEdge + fltStepY;

            fltRightEdge = fltLeftEdge + fltWidth;
            fltBottomEdge = fltTopEdge + fltHeight;
        }
        else
        {
            rebound();
        }
    }

    private void rebound()
    {
        if ((fltRightEdge > (svPanel.getWidth() - fltStepX)) || (fltLeftEdge + fltStepX < 0))
        {
            fltStepX = fltStepX * -1;
        }

        if ((fltBottomEdge > (svPanel.getHeight() - fltStepY)) || (fltTopEdge + fltStepY < 0))
        {
            fltStepY = fltStepY * -1;
        }
    }

}
