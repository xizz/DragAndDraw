package xizz.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class BoxDrawingView extends View {
	private static final String TAG = "BoxDrawingView";
	private static final String ARG_STATE = "state";
	private static final String ARG_BOXES = "boxes";

	private Box mCurrentBox;
	private ArrayList<Box> mBoxes = new ArrayList<>();
	private Paint mBoxPaint;
	private Paint mBackgroundPaint;

	public BoxDrawingView(Context context) {
		this(context, null);
	}

	public BoxDrawingView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		Log.d(TAG, "constructor called");
		mBoxPaint = new Paint();
		mBoxPaint.setColor(0x22ff0000);

		mBackgroundPaint = new Paint();
		mBackgroundPaint.setColor(0xfff8efe0);
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Log.d(TAG, "onSaveInstanceState()");
		Bundle bundle = new Bundle();
		bundle.putSerializable(ARG_BOXES, mBoxes);
		bundle.putParcelable(ARG_STATE, super.onSaveInstanceState());
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		Log.d(TAG, "onRestoreInstanceState()");
		Bundle args = (Bundle) state;
		mBoxes = (ArrayList<Box>) args.getSerializable(ARG_BOXES);
		super.onRestoreInstanceState(args.getParcelable(ARG_STATE));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw()");
		canvas.drawPaint(mBackgroundPaint);

		for (Box box : mBoxes) {
			float left = Math.min(box.getOrigin().x, box.getCurrent().x);
			float right = Math.max(box.getOrigin().x, box.getCurrent().x);
			float top = Math.min(box.getOrigin().y, box.getCurrent().y);
			float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

			canvas.drawRect(left, top, right, bottom, mBoxPaint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Box.PointF curr = new Box.PointF(event.getX(), event.getY());

		Log.d(TAG, "onTouchEvent() x=" + curr.x + " y=" + curr.y);

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.d(TAG, "ACTION_DOWN");
				mCurrentBox = new Box(curr);
				mBoxes.add(mCurrentBox);
				break;
			case MotionEvent.ACTION_MOVE:
				Log.d(TAG, "ACTION_MOVE");
				if (mCurrentBox != null) {
					mCurrentBox.setCurrent(curr);
					invalidate();
				}
				break;
			case MotionEvent.ACTION_UP:
				Log.d(TAG, "ACTION_UP");
				mCurrentBox = null;
				break;
			case MotionEvent.ACTION_CANCEL:
				Log.d(TAG, "ACTION_ CANCEL");
				mCurrentBox = null;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				Log.d(TAG, "ACTION_POINTER_DOWN");
				break;
			case MotionEvent.ACTION_POINTER_UP:
				Log.d(TAG, "ACTION_POINTER_UP");
				break;
		}

		return true;
	}
}
