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
		
		// Add toppings
		for (int i = 0; i < toppings.size(); i++) {
			String imageSource = toppings.get(i).replaceAll(" ", "_").toLowerCase();
			
			ToppingView toppingView = new ToppingView(getApplicationContext(),
					BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageSource, "drawable", getApplicationContext().getPackageName())));
			
			mFrame.addView(toppingView); //getToppingView(toppings.get(i).replaceAll(" ", "_").toLowerCase()));
		} 
	} 
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {

			// Get the size of the display so this View knows where borders are
			mDisplayWidth = mFrame.getWidth();
			mDisplayHeight = mFrame.getHeight();

		}
	}

	public ToppingView getToppingView(String imageSource) {
		ToppingView toppingView = new ToppingView(getApplicationContext(),
				BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageSource, "drawable", getApplicationContext().getPackageName())));
		System.out.println(toppingView);
		return toppingView;
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
		private long mRotate, mDRotate;

		ToppingView(Context context, Bitmap bitmap) {
			super(context);
			
			mBitmap = bitmap;

			mBitmapWidth = (int) getResources().getDimension(
						R.dimen.image);

			float x = 400;
			float y = 400;
			
			Log.i(TAG, "Creating Bubble at: x:" + x + " y:" + y);

			// Create a new random number generator to
			// randomize size, rotation, speed and direction
			Random r = new Random();

			// Creates the bubble bitmap for this BubbleView
			createScaledBitmap(r);

			// Radius of the Bitmap
			mRadius = mBitmapWidth / 2;
			mRadiusSquared = mRadius * mRadius;
			
			// Adjust position to center the bubble under user's finger
			mXPos = x - mRadius;
			mYPos = y - mRadius;

			// Set the BubbleView's speed and direction
			setSpeedAndDirection(r);

			// Set the BubbleView's rotation
			setRotation(r);

			mPainter.setAntiAlias(true);

		}

		private void setRotation(Random r) {
			// TO DO - set rotation in range [1..3]
            mDRotate = (long)Math.ceil(r.nextDouble() * 6 - 3);
		}

		private void setSpeedAndDirection(Random r) {
                mDx = (float)Math.ceil(r.nextDouble() * 6 - 3);
                mDy = (float)Math.ceil(r.nextDouble() * 6 - 3);

                Log.i("my bubble", String.valueOf(mDx));
                Log.i("my bubble", String.valueOf(mDy));
        }

		private void createScaledBitmap(Random r) {

			mBitmapWidth = BITMAP_SIZE * 3;

            Log.i("BM SIZE", String.valueOf(BITMAP_SIZE));
            Log.i("BM SIZE", String.valueOf(mBitmapWidth));

            // TO DO - create the scaled bitmap using size set above
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmapWidth, mBitmapWidth, false);
			
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

                    // TO DO - implement movement logic.
                    // Each time this method is run the BubbleView should
                    // move one step. If the BubbleView exits the display,
                    // stop the BubbleView's Worker Thread.
                    // Otherwise, request that the BubbleView be redrawn.
                    Log.i("Am I called?", "I'm not being called");

                    if (moveWhileOnScreen()) {
                        view.invalidate();
                    } else {
                        Log.i("Off screen", "I WENT OFFSCREEN MOFOS");
                        stop(false);
                    }
                }

			}, 0, REFRESH_RATE, TimeUnit.MILLISECONDS);
		}

		// Cancel the Bubble's movement
		// Remove Bubble from mFrame
		// Play pop sound if the BubbleView was popped

		private void stop(final boolean wasPopped) {

            Log.i("GOT TO STOP", "DIDI II HHUH?");
			if (null != mMoverFuture) {

				if (!mMoverFuture.isDone()) {
					mMoverFuture.cancel(true);
				}

				// This work will be performed on the UI Thread
				mFrame.post(new Runnable() {
					@Override
					public void run() {

						// TO DO - Remove the BubbleView from mFrame

                        mFrame.removeView(view);

						// TO DO - If the bubble was popped by user,
						// play the popping sound
						
						Log.i(TAG, "Bubble removed from view!");
					}
				});
			}
		}

		// Draw the Bubble at its current location
		@Override
		protected synchronized void onDraw(Canvas canvas) {

			// TODO - save the canvas
			canvas.save();

			// TODO - increase the rotation of the original image by mDRotate
            mRotate += mDRotate;

			// TODO Rotate the canvas by current rotation
			// Hint - Rotate around the bubble's center, not its position

            canvas.rotate(mRotate, mXPos, mYPos);

			// TODO - draw the bitmap at it's new location
            canvas.drawBitmap(mBitmap, mXPos, mYPos, mPainter);

			// TODO - restore the canvas
			canvas.restore();

		}

		// Returns true if the BubbleView is still on the screen after the move
		// operation
		private synchronized boolean moveWhileOnScreen() {

			// TO DO - Move the BubbleView
            mXPos += mDx;
            mYPos += mDy;

            return isOutOfView();

		}

		// Return true if the BubbleView is still on the screen after the move
		// operation
		private boolean isOutOfView() {

            Log.i("pos", String.valueOf(mXPos));
            Log.i("pos", String.valueOf(mYPos));

			// TO DO - Return true if the BubbleView is still on the screen after
			// the move operation
            if (mXPos < 0 || mXPos > mDisplayWidth || mYPos < 0 || mYPos > mDisplayHeight) {
                Log.i("OH SHIZ", "Im out of view");
                return false;
            }

			return true;
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}