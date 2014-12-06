package com.saladbar.houseoftoss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DisplayActivity extends Activity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    
    private RelativeLayout relativeLayout;
    private int layoutWidth;
    private int layoutHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_assembly);
 
        // GRAPHICAL VIEW
		final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.graphicLayout);
		
		relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

	        @Override
	        public void onGlobalLayout() {
	        	relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

	    		layoutWidth = relativeLayout.getWidth();
	    		layoutHeight = relativeLayout.getHeight();
	        
	    		System.out.println(layoutWidth + " " + layoutHeight);
	        }
	    });
      
        */
    } 
    
    
    public ToppingView getToppingView(String imageSource) {
  		ToppingView toppingView = new ToppingView(getApplicationContext(),
  				BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageSource, "drawable", getApplicationContext().getPackageName())));
  		System.out.println(toppingView);
  		return toppingView;
      }
      
      private class ToppingView extends SurfaceView implements
  	SurfaceHolder.Callback {
  		
  		private final Bitmap mBitmap;
  		private final int mBitmapHeightAndWidth, mBitmapHeightAndWidthAdj;
  		private final int mDisplayWidth, mDisplayHeight;
  		private float mX, mY, mDx, mDy, mRotation;
  		private final SurfaceHolder mSurfaceHolder;
  		private final Paint mPainter = new Paint();
  		private Thread mDrawingThread;
  		
  		private static final int MOVE_STEP = 1;
  		private static final float ROT_STEP = 1.0f;
  		
  		public ToppingView(Context context, Bitmap bitmap) {
  			super(context);
  		
  			mBitmapHeightAndWidth = (int) getResources().getDimension(
  					R.dimen.image);
  			this.mBitmap = Bitmap.createScaledBitmap(bitmap,
  					mBitmapHeightAndWidth, mBitmapHeightAndWidth, false);
  		
  			mBitmapHeightAndWidthAdj = mBitmapHeightAndWidth / 2;
  		
  			mDisplayWidth = layoutWidth;
  			mDisplayHeight = layoutHeight;
  		
  			Random r = new Random();
  			mX = (float) r.nextInt(mDisplayHeight);
  			mY = (float) r.nextInt(mDisplayWidth);
  			mDx = (float) r.nextInt(mDisplayHeight) / mDisplayHeight;
  			mDx *= r.nextInt(2) == 1 ? MOVE_STEP : -1 * MOVE_STEP;
  			mDy = (float) r.nextInt(mDisplayWidth) / mDisplayWidth;
  			mDy *= r.nextInt(2) == 1 ? MOVE_STEP : -1 * MOVE_STEP;
  			mRotation = 1.0f;
  		
  			mPainter.setAntiAlias(true);
  		
  			mSurfaceHolder = getHolder();
  			mSurfaceHolder.addCallback(this);
  		}
  		
  		private void drawTopping(Canvas canvas) {
  			canvas.drawColor(Color.DKGRAY);
  			mRotation += ROT_STEP;
  			canvas.rotate(mRotation, mY + mBitmapHeightAndWidthAdj, mX
  					+ mBitmapHeightAndWidthAdj);
  			canvas.drawBitmap(mBitmap, mY, mX, mPainter);
  		}
  		
  		private boolean move() {
  			mX += mDx;
  			mY += mDy;
  			if (mX < 0 - mBitmapHeightAndWidth
  					|| mX > mDisplayHeight + mBitmapHeightAndWidth
  					|| mY < 0 - mBitmapHeightAndWidth
  					|| mY > mDisplayWidth + mBitmapHeightAndWidth) {
  				return false;
  			} else {
  				return true;
  			}
  		}
  		
  		@Override
  		public void surfaceChanged(SurfaceHolder holder, int format, int width,
  				int height) {
  		}
  		
  		@Override
  		public void surfaceCreated(SurfaceHolder holder) {
  			mDrawingThread = new Thread(new Runnable() {
  				public void run() {
  					Canvas canvas = null;
  					while (!Thread.currentThread().isInterrupted() && move()) {
  						canvas = mSurfaceHolder.lockCanvas();
  						if (null != canvas) {
  							drawTopping(canvas);
  							mSurfaceHolder.unlockCanvasAndPost(canvas);
  						}
  					}
  				}
  			});
  			mDrawingThread.start();
  		}
  		
  		@Override
  		public void surfaceDestroyed(SurfaceHolder holder) {
  			if (null != mDrawingThread)
  				mDrawingThread.interrupt();
  		}
  	}   
}