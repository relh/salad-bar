package com.saladbar.houseoftoss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DisplayActivity extends Activity {

	ToppingView mToppingView;
	
	private RelativeLayout mFrame;
	private DisplayMetrics mDisplay;
	private int mDisplayWidth, mDisplayHeight;

	private String TAG = "Display Activity"; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		// GET PASSED IN TOPPINGS
		Intent data = getIntent();
		ArrayList<String> toppings = data.getStringArrayListExtra(OrderActivity.EXTRA_SALAD);

		// GRAPHICAL LAYOUT
		mFrame = (RelativeLayout) findViewById(R.id.frame);
		
		mDisplay = new DisplayMetrics();
		DisplayActivity.this.getWindowManager().getDefaultDisplay()
				.getMetrics(mDisplay);
		mDisplayWidth = mDisplay.widthPixels;
		mDisplayHeight = mDisplay.heightPixels;
		
		// Add toppings
		for (int i = 0; i < toppings.size(); i++) {
			String imageSource = toppings.get(i).replaceAll(" ", "_").toLowerCase();
			
			ToppingView toppingView = new ToppingView(getApplicationContext(),
					BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageSource, "drawable", getApplicationContext().getPackageName())));
			
			mFrame.addView(toppingView); //getToppingView(toppings.get(i).replaceAll(" ", "_").toLowerCase()));
			toppingView.start();
		} 
	}

	public class ToppingView extends View {

		private static final int BITMAP_SIZE = 64;
		private static final int REFRESH_RATE = 40;
		private final Paint mPainter = new Paint();
		private ScheduledFuture<?> mMoverFuture;
		private int mBitmapWidth;
		private Bitmap mBitmap;

		// location, speed and direction of the bubble
		private float mXPos, mYPos, mDx, mDy, mRadius, mRadiusSquared;
		private float mXFinal, mYFinal;
		private long mRotate, mDRotate;

		ToppingView(Context context, Bitmap bitmap) {
			super(context);
			
			mBitmap = bitmap;
			mBitmapWidth = (int) getResources().getDimension(
						R.dimen.image);

			// Create a new random number generator to
			// randomize starting position and final position
			Random r = new Random();

			// Starting position
			mXPos = (float)Math.ceil(r.nextDouble()*mDisplayWidth);
			mYPos = (float)Math.ceil(r.nextDouble()*mDisplayHeight);
			Log.i(TAG, "Creating Topping at: x:" + mXPos + " y:" + mYPos);
			
			// Creates the topping bitmap for this BubbleView
			createScaledBitmap(r);

			// Radius of the Bitmap
			mRadius = mBitmapWidth / 2;
			mRadiusSquared = mRadius * mRadius;
			
			setSpeedAndDirection(r);

			mPainter.setAntiAlias(true);
		}

		private void createScaledBitmap(Random r) {
			mBitmapWidth = BITMAP_SIZE * 3;
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmapWidth, mBitmapWidth, false);
		}
		
		private void setSpeedAndDirection(Random r) {
                mDx = (float)Math.ceil(r.nextDouble() * 6 - 3);
                mDy = (float)Math.ceil(r.nextDouble() * 6 - 3);
                System.out.println(mDx + " " + mDy);
        }

        public ToppingView view = this;

		// Start moving the BubbleView & updating the display
		private void start() {

			// Creates a WorkerThread
			ScheduledExecutorService executor = Executors
					.newScheduledThreadPool(1);

			// Execute the run() in Worker Thread every REFRESH_RATE
			// milliseconds
			// Save reference to this job in mMoverFuture
			mMoverFuture = executor.scheduleWithFixedDelay(new Runnable() {
				@Override
				public void run() {
                    mXPos += mDx;
                    mYPos += mDy;
					
					view.invalidate();
                                        
                   // if (mXPos == mXFinal) {
                    	Log.i(TAG, "Made it to final spot!");
                    	stop(false);
                   // }
                }

			}, 0, REFRESH_RATE, TimeUnit.MILLISECONDS);
		}

		private void stop(final boolean wasPopped) {
            Log.i(TAG, "Stopped");
			if (null != mMoverFuture) {

				if (!mMoverFuture.isDone()) {
					mMoverFuture.cancel(true);
				}

				// This work will be performed on the UI Thread
				mFrame.post(new Runnable() {
					@Override
					public void run() { mFrame.removeView(view); }
				});
			}
		}

		@Override
		protected synchronized void onDraw(Canvas canvas) {
			Log.i(TAG, "onDraw() called");
			canvas.save();
            canvas.drawBitmap(mBitmap, mXPos, mYPos, mPainter);
			canvas.restore();
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}