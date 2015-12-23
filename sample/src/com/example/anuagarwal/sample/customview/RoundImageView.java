package com.example.anuagarwal.sample.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Custom Circular Image view.
 * <p/>
 * <com.tarento.ui.widget.RoundImageView
 * android:id="@+id/home_image_driver"
 * android:layout_width="120dp"
 * android:layout_height="120dp"
 * android:padding="@dimen/spacing_normal"
 * android:src="@drawable/ic_user" />
 */
public class RoundImageView extends ImageView {

    private static int mBorderColor = android.R.color.white;
    private int borderWidth = 0;
    private int viewWidth;
    private int viewHeight;
    private Bitmap image;
    private Paint paint;
    private Paint paintBorder;
    private Paint shaderPaint;
    private BitmapShader shader;
    private boolean needToDrawOverlay;

    public RoundImageView(final Context context) {
        super(context);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    public static void setBorderColor(int color) {
        mBorderColor = color;
    }

    /**
     * To initialize the paint components.
     */
    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);

        shaderPaint = new Paint();
        shaderPaint.setAntiAlias(true);
        shaderPaint.setColor(getResources().getColor(android.R.color.white));
        shaderPaint.setStyle(Paint.Style.FILL);

        paintBorder = new Paint();
        paintBorder.setColor(getResources().getColor(mBorderColor));
        borderWidth = 2;
        paintBorder.setAntiAlias(true);

        needToDrawOverlay = false;
    }

    public void setBackgroundColor(int color) {
        paintBorder.setColor(color);
        invalidate();
    }

    public void drawOverlay(boolean drawOverlay) {
        needToDrawOverlay = drawOverlay;

        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        // load the bitmap
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();
        if (bitmapDrawable != null) {
            image = bitmapDrawable.getBitmap();
        }

        // init shader
        if (image != null) {
            shader = createBitmapShader(image, canvas);

            paint.setShader(shader);

            // calculate the center point.
            int circleCenter = viewWidth / 2;

            // circleCenter is the x or y of the view's center
            // radius is the radius in pixels of the cirle to be drawn
            // paint contains the shader that will texture the shape
            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth,
                    circleCenter + borderWidth - 4.0f, paintBorder);
            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth,
                    circleCenter - 4.0f, paint);
        }

        if (needToDrawOverlay) {
            int circleCenter = viewWidth / 2;

            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth,
                    circleCenter + borderWidth - 4.0f, shaderPaint);
        }
    }

    /**
     * To create bitmap shader.
     *
     * @param image
     * @param canvas
     * @return BitmapShader object
     */
    private BitmapShader createBitmapShader(Bitmap image, Canvas canvas) {
        return new BitmapShader(
                Bitmap.createScaledBitmap(image, this.getWidth(), this.getHeight(), false),
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec, widthMeasureSpec);

        viewWidth = width;
        viewHeight = height;

        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            result = viewWidth;
        }

        return result;
    }

    private int measureHeight(int measureSpecHeight, int measureSpecWidth) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = viewHeight;
        }

        return (result);
    }

}
