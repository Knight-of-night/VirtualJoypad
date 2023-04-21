/**
 * 
 */
package io.github.Knight_of_night.virtualjoypad.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import io.github.Knight_of_night.virtualjoypad.R;
import io.github.Knight_of_night.virtualjoypad.R.color;

/**
 * @author Knight-of-night
 *
 */
public class StickView extends View {
	
	private Paint paint = new Paint();
	private int idx = -1;
	private float bitmapX;
	private float bitmapY;
	// 中心
	private float offsetX = 400;
	private float offsetY = 400;
	// 摇杆固定位置
	private float fixX = 400;
	private float fixY = 400;
	// 半径
	private float rd = 300;
	// 摇杆大小
	public float stickSize = 80;

	/**
	 * @param context
	 * @param fixX 固定X方向位置
	 * @param fixY 固定Y方向位置
	 * @param rd 半径
	 */
	public StickView(Context context, float fixX, float fixY, float rd) {
		super(context);
		
		this.fixX = fixX;
		this.fixY = fixY;
		this.rd = rd;
		
		offsetX = fixX;
		offsetY = fixY;
		bitmapX = fixX;
		bitmapY = fixY;
		
		paint.setColor(getResources().getColor(R.color.stick_color));
	}
	
	/**
	 * @param context
	 */
	public StickView(Context context) {
		this(context, 400, 400, 300);
	}

	public float getStickX() {
		return (bitmapX - offsetX) / rd;
	}

	public float getStickY() {
		return (bitmapY - offsetY) / rd;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
//		Paint paint = new Paint();
//		Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
//		canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint);
//		if(!bitmap.isRecycled()) {
//			bitmap.recycle();
//		}
		
		canvas.drawCircle(offsetX, offsetY, rd, paint);
		canvas.drawCircle(bitmapX, bitmapY, stickSize, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			float x = event.getX();
			float y = event.getY();
			// 如果没有在范围内
			if(((x - fixX) * (x - fixX) + (y - fixY) * (y - fixY)) > (rd * rd)) {
				return super.onTouchEvent(event);
			}
			idx = event.getActionIndex();
			// 保存按下时的位置
			offsetX = x;
			offsetY = y;
			// 移动图标
			bitmapX = offsetX;
			bitmapY = offsetY;
			invalidate();
			return true;
		}else if(event.getAction() == MotionEvent.ACTION_MOVE) {
			// 忽略
			if(idx == -1) {
				return super.onTouchEvent(event);
			}
			// 暂存位置
			float moveX = event.getX(idx);
			float moveY = event.getY(idx);
			
			// 判断有没有超出范围
			double length_2 = Math.pow(moveX - offsetX, 2) + Math.pow(moveY - offsetY, 2);
			if(length_2 > rd * rd) {
				double limit = rd / Math.sqrt(length_2);
				moveX -= offsetX;
				moveY -= offsetY;
				moveX *= limit;
				moveY *= limit;
				moveX += offsetX;
				moveY += offsetY;
			}
			// 移动图标
			bitmapX = moveX;
			bitmapY = moveY;
			invalidate();
			// 显示在屏幕上
//			moveX -= offsetX;
//			moveY -= offsetY;
//			textViewDebug.setText("Stick Position: " + moveX + ", " + moveY);
//			return true;
			return super.onTouchEvent(event);
		}else if(event.getAction() == MotionEvent.ACTION_UP) {
			if(idx == event.getActionIndex()) {
				idx = -1;
				// 摇杆回中
				bitmapX = fixX;
				bitmapY = fixY;
				offsetX = fixX;
				offsetY = fixY;
//				textViewDebug.setText("Stick Position: 0, 0");
				return true;
			}
		}
		
		return super.onTouchEvent(event);
	}

}
