package com.study.mingappk.common.views.scanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.google.zxing.Result;
import com.study.mingappk.R;
import com.study.mingappk.tmain.baseactivity.BackActivity;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MyScannerActivity extends BackActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    public static String SCAN_RESULT = "scan_result";//扫描的结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("条码扫描");
        mScannerView = new ZXingScannerView(this) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                return new CustomViewFinderView(context);
            }
        };
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Intent intent = new Intent();
        intent.putExtra(SCAN_RESULT, rawResult.getText());
        setResult(RESULT_OK, intent);
        finish();
        //扫码方式
        //  Log.v("TAG", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        //若需要连续扫描，执行下面的代码，等两秒继续扫描
        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(MyScannerActivity.this);
            }
        }, 2000);*/
    }

    //自定义外观
    private static class CustomViewFinderView extends ViewFinderView {
        public static final String TRADE_MARK_TEXT = "将快递条形码放置框内，即开始扫描";
        public static final int TRADE_MARK_TEXT_SIZE_SP = 14;
        public final Paint PAINT = new Paint();

        public CustomViewFinderView(Context context) {
            super(context);
            init();
        }

        public CustomViewFinderView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            PAINT.setColor(Color.WHITE);
            PAINT.setAntiAlias(true);
            float textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    TRADE_MARK_TEXT_SIZE_SP, getResources().getDisplayMetrics());
            PAINT.setTextSize(textPixelSize);
            //是否设置方形扫描框
            setSquareViewFinder(false);
            int themeColor = ThemeUtils.getColorById(getContext(), R.color.theme_color_primary);
            setLaserColor(themeColor);//扫描框中横线颜色
            setBorderColor(themeColor);//扫描框颜色
            setBorderStrokeWidth(8);//扫描框线宽度
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawTradeMark(canvas);
        }

        @Override
        public void drawLaser(Canvas canvas) {
            super.drawLaser(canvas);
            Rect framingRect = this.getFramingRect();
            int middle = framingRect.height() / 2 + framingRect.top;
            canvas.drawRect((float) (framingRect.left + 2), (float) (middle - 4), (float) (framingRect.right - 2), (float) (middle + 4), this.mLaserPaint);
            this.postInvalidateDelayed(2000L, framingRect.left - 10, framingRect.top - 10, framingRect.right + 10, framingRect.bottom + 10);
        }

        private void drawTradeMark(Canvas canvas) {
            Rect framingRect = getFramingRect();
            float tradeMarkTop;
            float tradeMarkLeft;
            if (framingRect != null) {
                tradeMarkTop = framingRect.bottom + PAINT.getTextSize() + 60;
//                tradeMarkLeft = framingRect.left;
                Rect rect = new Rect();
                PAINT.getTextBounds(TRADE_MARK_TEXT, 0, TRADE_MARK_TEXT.length(), rect);
                tradeMarkLeft =framingRect.left+ (framingRect.width() - rect.width()) / 2;// 方框长度-文本长度的一半，实现文字居中
            } else {
                tradeMarkTop = 30;
                tradeMarkLeft = canvas.getHeight() - PAINT.getTextSize() - 30;
            }
            canvas.drawText(TRADE_MARK_TEXT, tradeMarkLeft, tradeMarkTop, PAINT);
        }
    }
}
