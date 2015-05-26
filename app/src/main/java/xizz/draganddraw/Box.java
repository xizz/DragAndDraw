package xizz.draganddraw;

import java.io.Serializable;

public class Box implements Serializable {
	private PointF mOrigin;
	private PointF mCurrent;

	public Box(PointF origin) {
		mOrigin = mCurrent = origin;
	}

	public PointF getCurrent() {
		return mCurrent;
	}

	public void setCurrent(PointF current) {
		mCurrent = current;
	}

	public PointF getOrigin() {
		return mOrigin;
	}

	public static class PointF extends android.graphics.PointF implements Serializable {
		public PointF(float x, float y) {
			super(x, y);
		}
	}
}

