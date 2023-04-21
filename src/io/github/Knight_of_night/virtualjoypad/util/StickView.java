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
	// ����
	private float offsetX = 400;
	private float offsetY = 400;
	// ҡ�˹̶�λ��
	private float fixX = 400;
	private float fixY = 400;
	// �뾶
	private float rd = 300;
	// ҡ�˴�С
	public float stickSize = 80;

	/**
	 * @param context
	 * @param fixX �̶�X����λ��
	 * @param fixY �̶�Y����λ��
	 * @param rd �뾶
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
			// ���û���ڷ�Χ��
			if(((x - fixX) * (x - fixX) + (y - fixY) * (y - fixY)) > (rd * rd)) {
				return super.onTouchEvent(event);
			}
			idx = event.getActionIndex();
			// ���水��ʱ��λ��
			offsetX = x;
			offsetY = y;
			// �ƶ�ͼ��
			bitmapX = offsetX;
			bitmapY = offsetY;
			invalidate();
			return true;
		}else if(event.getAction() == MotionEvent.ACTION_MOVE) {
			// ����
			if(idx == -1) {
				return super.onTouchEvent(event);
			}
			// �ݴ�λ��
			float moveX = event.getX(idx);
			float moveY = event.getY(idx);
			
			// �ж���û�г�����Χ
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
			// �ƶ�ͼ��
			bitmapX = moveX;
			bitmapY = moveY;
			invalidate();
			// ��ʾ����Ļ��
//			moveX -= offsetX;
//			moveY -= offsetY;
//			textViewDebug.setText("Stick Position: " + moveX + ", " + moveY);
//			return true;
			return super.onTouchEvent(event);
		}else if(event.getAction() == MotionEvent.ACTION_UP) {
			if(idx == event.getActionIndex()) {
				idx = -1;
				// ҡ�˻���
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
