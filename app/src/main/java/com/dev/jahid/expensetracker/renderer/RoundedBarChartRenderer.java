package com.dev.jahid.expensetracker.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class RoundedBarChartRenderer extends BarChartRenderer {
    public RoundedBarChartRenderer(BarChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {
        float radius = 20f; // corner radius

        mRenderPaint.setStyle(Paint.Style.FILL);

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());
        mBarBorderPaint.setColor(dataSet.getBarBorderColor());
        mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getBarBorderWidth()));

        final boolean drawBorder = dataSet.getBarBorderWidth() > 0.f;

        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(mAnimator.getPhaseX(), mAnimator.getPhaseY());
        buffer.setDataSet(index);
        buffer.setInverted(mChart.isInverted(dataSet.getAxisDependency()));
        buffer.setBarWidth(mChart.getBarData().getBarWidth());

        buffer.feed(dataSet);

        trans.pointValuesToPixel(buffer.buffer);

        for (int j = 0; j < buffer.size(); j += 4) {
            float left = buffer.buffer[j];
            float top = buffer.buffer[j + 1];
            float right = buffer.buffer[j + 2];
            float bottom = buffer.buffer[j + 3];

            Path path = new Path();
            path.addRoundRect(new RectF(left, top, right, bottom),
                    new float[]{radius, radius, radius, radius, 0f, 0f, 0f, 0f},
                    Path.Direction.CW);
            c.drawPath(path, mRenderPaint);

            if (drawBorder) {
                c.drawPath(path, mBarBorderPaint);
            }
        }
    }
}

