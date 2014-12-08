package com.saladbar.houseoftoss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileOutputStream;
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
	private int finishedToppings;

	private String TAG = "Display Activity"; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		// GRAPHICAL LAYOUT
		mFrame = (RelativeLayout) findViewById(R.id.frame);
		mFrame.setDrawingCacheEnabled(true);
		
		mDisplay = new DisplayMetrics();
		DisplayActivity.this.getWindowManager().getDefaultDisplay()
				.getMetrics(mDisplay);
		mDisplayWidth = mDisplay.widthPixels-175; // Custom center-point adjustment
		mDisplayHeight = mDisplay.heightPixels-400;
		
		// GET PASSED IN TOPPINGS
		Intent data = getIntent();
		ArrayList<String> toppings = data.getStringArrayListExtra(OrderActivity.EXTRA_SALAD);
		
		finishedToppings = toppings.size();
		
		// ADD TOPPINGS
		for (int i = 0; i < toppings.size(); i++) {
			String imageSource = toppings.get(i).replaceAll(" ", "_").toLowerCase();
			
			ToppingView toppingView = new ToppingView(getApplicationContext(),
					BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imageSource, "drawable", getApplicationContext().getPackageName())));
			
			mFrame.addView(toppingView); //getToppingView(toppings.get(i).replaceAll(" ", "_").toLowerCase()));
			toppingView.start();
		} 
	}
	
	public Activity displayActivity = this;

	public class ToppingView extends View {

		private static final int BITMAP_SIZE = 64;
		private static final int REFRESH_RATE = 40;
		private Paint mPainter = new Paint();
		private ScheduledFuture<?> mMoverFuture;
		private int mBitmapWidth;
		private Bitmap mBitmap;

		// location, speed and direction of the bubble
		private float mXPos, mYPos, mDx, mDy;
		private float mXFinal, mYFinal;

		ToppingView(Context context, Bitmap bitmap) {
			super(context);
			
			mBitmap = bitmap;
			mBitmapWidth = (int) getResources().getDimension(
						R.dimen.image);

			// Create a new random number generator to randomize starting position and final position
			Random r = new Random(); 
 
			// Starting position
			mXPos = (float)Math.ceil(r.nextDouble()*mDisplayWidth);
			mYPos = (float)Math.ceil(r.nextDouble()*mDisplayHeight);

			// Final positions are randomly distributed around center screen
			mXFinal = (float)(mDisplayWidth/2.0 + Math.ceil(r.nextDouble()*150) - 75);
			mYFinal = (float)(mDisplayHeight/2.0 + Math.ceil(r.nextDouble()*150) - 75);
			
			// Creates the topping bitmap for this BubbleView
			mBitmapWidth = BITMAP_SIZE * 3;
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmapWidth, mBitmapWidth, false);

            mDx = (float)((mXFinal-mXPos)/50.0);
            mDy = (float)((mYFinal-mYPos)/50.0);
            
            System.out.println("D values for 50 time steps: " + mDx + " " + mDy);
            
            mPainter.setAntiAlias(true);
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
					
					view.postInvalidate();
                                        
                   if (Math.abs(mXPos-mXFinal) < 5 && Math.abs(mYPos-mYFinal) < 5) {
                    	Log.i(TAG, "Landed");
                    	stop();
                   }
                }

			}, 0, REFRESH_RATE, TimeUnit.MILLISECONDS);
		}

		private void stop() {
            if (null != mMoverFuture) {

				if (!mMoverFuture.isDone()) {
					mMoverFuture.cancel(true);
				}
				
				finishedToppings--;
				System.out.println(finishedToppings);
				
				if (finishedToppings <= 0) {
					System.out.println("About to finish");
					onPause();
				}
				//mFrame.post(new Runnable() {
				//	@Override
				//	public void run() { mFrame.removeView(view); }
				//});
			}
		}

		@Override
		protected synchronized void onDraw(Canvas canvas) {
			canvas.save();
            canvas.drawBitmap(mBitmap, (int)mXPos, (int)mYPos, mPainter);
			canvas.restore();
		}
	}
	
	public void onPause() {
		super.onPause();
		
		Bitmap bmp = mFrame.getDrawingCache();
		try { 
		    //Write file
		    String filename = "bitmap.png";
		    FileOutputStream stream = displayActivity.openFileOutput(filename, Context.MODE_PRIVATE);
		    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

		    //Cleanup
		    stream.close();
		    bmp.recycle();
		    
		    //Record bitmap midpoint
		    int midX = (int)(mDisplayWidth/2.0);
		    int midY = (int)(mDisplayHeight/2.0);

        	Intent result = new Intent();
        	result.putExtra("x", midX);
        	result.putExtra("y", midY);
        	result.putExtra("image", filename);
        	setResult(Activity.RESULT_OK, result);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		finish();
	}
}