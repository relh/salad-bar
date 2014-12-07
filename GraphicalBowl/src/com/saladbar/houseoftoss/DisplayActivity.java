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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Random;

public class DisplayActivity extends Activity {

	ToppingView mToppingView;
	RelativeLayout relativeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		// GET PASSED IN TOPPINGS
		Intent data = getIntent();
		ArrayList<String> toppings = data.getStringArrayListExtra(OrderActivity.EXTRA_SALAD);

		// GRAPHICAL LAYOUT
		relativeLayout = (RelativeLayout) findViewById(R.id.frame);
		
		// Add toppings
		for (int i = 0; i < toppings.size(); i++) {
			String imageSource = toppings.get(i).replaceAll(" ", "_").toLowerCase();
			
			ToppingView toppingView = new ToppingView(getApplicationContext(),
					BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageSource, "drawable", getApplicationContext().getPackageName())));
			
			relativeLayout.addView(toppingView); //getToppingView(toppings.get(i).replaceAll(" ", "_").toLowerCase()));
		} 
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
		private final DisplayMetrics mDisplay;
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
			this.mBitmap = bitmap;
			//this.mBitmap = Bitmap.createScaledBitmap(bitmap,
			//		mBitmapHeightAndWidth, mBitmapHeightAndWidth, false);

			mBitmapHeightAndWidthAdj = mBitmapHeightAndWidth / 2;

			mDisplay = new DisplayMetrics();
			DisplayActivity.this.getWindowManager().getDefaultDisplay()
					.getMetrics(mDisplay);
			mDisplayWidth = mDisplay.widthPixels;
			mDisplayHeight = mDisplay.heightPixels;

			Random r = new Random();
			mX = r.nextInt(mDisplayHeight);
			mY = r.nextInt(mDisplayWidth);
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
				@Override
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