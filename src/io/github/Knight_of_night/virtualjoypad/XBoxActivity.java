package io.github.Knight_of_night.virtualjoypad;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import io.github.Knight_of_night.virtualjoypad.util.DataSender;
import io.github.Knight_of_night.virtualjoypad.util.ParseVibration;
import io.github.Knight_of_night.virtualjoypad.util.StickView;


/**
 * @author Knight-of-night
 *
 */
public class XBoxActivity extends Activity {
	
	public final static int XUSB_GAMEPAD_DPAD_UP = 0x0001;
	public final static int XUSB_GAMEPAD_DPAD_DOWN = 0x0002;
	public final static int XUSB_GAMEPAD_DPAD_LEFT = 0x0004;
	public final static int XUSB_GAMEPAD_DPAD_RIGHT = 0x0008;
	public final static int XUSB_GAMEPAD_START = 0x0010;
	public final static int XUSB_GAMEPAD_BACK = 0x0020;
	public final static int XUSB_GAMEPAD_LEFT_THUMB = 0x0040;
	public final static int XUSB_GAMEPAD_RIGHT_THUMB = 0x0080;
	public final static int XUSB_GAMEPAD_LEFT_SHOULDER = 0x0100;
	public final static int XUSB_GAMEPAD_RIGHT_SHOULDER = 0x0200;
	public final static int XUSB_GAMEPAD_GUIDE = 0x0400;
	public final static int XUSB_GAMEPAD_A = 0x1000;
	public final static int XUSB_GAMEPAD_B = 0x2000;
	public final static int XUSB_GAMEPAD_X = 0x4000;
	public final static int XUSB_GAMEPAD_Y = 0x8000;
	
	private long exitTime = 0;
	private DataSender dataSender;
	private Handler handler = new Handler();
	private StickView stickL;
	private StickView stickR;
	private int buttonState = 0;
	private int leftTriggerValue = 0;
	private int rightTriggerValue = 0;
	
	private OnTouchListener buttonListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			if(event.getAction() == MotionEvent.ACTION_DOWN) {
				// 获取按钮状态
				switch (v.getId()) {
				case R.id.buttonDpadLeft:
					buttonState = buttonState | XUSB_GAMEPAD_DPAD_LEFT;
					break;
				case R.id.buttonDpadRight:
					buttonState = buttonState | XUSB_GAMEPAD_DPAD_RIGHT;
					break;
				case R.id.buttonDpadUp:
					buttonState = buttonState | XUSB_GAMEPAD_DPAD_UP;
					break;
				case R.id.buttonDpadDown:
					buttonState = buttonState | XUSB_GAMEPAD_DPAD_DOWN;
					break;
				case R.id.buttonA:
					buttonState = buttonState | XUSB_GAMEPAD_A;
					break;
				case R.id.buttonB:
					buttonState = buttonState | XUSB_GAMEPAD_B;
					break;
				case R.id.buttonX:
					buttonState = buttonState | XUSB_GAMEPAD_X;
					break;
				case R.id.buttonY:
					buttonState = buttonState | XUSB_GAMEPAD_Y;
					break;
				case R.id.buttonLeft:
					buttonState = buttonState | XUSB_GAMEPAD_LEFT_SHOULDER;
					break;
				case R.id.buttonRight:
					buttonState = buttonState | XUSB_GAMEPAD_RIGHT_SHOULDER;
					break;
				case R.id.buttonLeftThumb:
					buttonState = buttonState | XUSB_GAMEPAD_LEFT_THUMB;
					break;
				case R.id.buttonRightThumb:
					buttonState = buttonState | XUSB_GAMEPAD_RIGHT_THUMB;
					break;
				case R.id.buttonStart:
					buttonState = buttonState | XUSB_GAMEPAD_START;
					break;
				case R.id.buttonBack:
					buttonState = buttonState | XUSB_GAMEPAD_BACK;
					break;
				case R.id.buttonGuide:
					buttonState = buttonState | XUSB_GAMEPAD_GUIDE;
					break;
				default:
					break;
				}
				// 发送
				String data = getState();
				if (data != null || "" != data) {
					dataSender.send(data);
				}
				v.setPressed(true);
				return true;
			}else if(event.getAction() == MotionEvent.ACTION_UP) {
				// 获取按钮状态
				switch (v.getId()) {
				case R.id.buttonDpadLeft:
					buttonState = buttonState ^ XUSB_GAMEPAD_DPAD_LEFT;
					break;
				case R.id.buttonDpadRight:
					buttonState = buttonState ^ XUSB_GAMEPAD_DPAD_RIGHT;
					break;
				case R.id.buttonDpadUp:
					buttonState = buttonState ^ XUSB_GAMEPAD_DPAD_UP;
					break;
				case R.id.buttonDpadDown:
					buttonState = buttonState ^ XUSB_GAMEPAD_DPAD_DOWN;
					break;
				case R.id.buttonA:
					buttonState = buttonState ^ XUSB_GAMEPAD_A;
					break;
				case R.id.buttonB:
					buttonState = buttonState ^ XUSB_GAMEPAD_B;
					break;
				case R.id.buttonX:
					buttonState = buttonState ^ XUSB_GAMEPAD_X;
					break;
				case R.id.buttonY:
					buttonState = buttonState ^ XUSB_GAMEPAD_Y;
					break;
				case R.id.buttonLeft:
					buttonState = buttonState ^ XUSB_GAMEPAD_LEFT_SHOULDER;
					break;
				case R.id.buttonRight:
					buttonState = buttonState ^ XUSB_GAMEPAD_RIGHT_SHOULDER;
					break;
				case R.id.buttonLeftThumb:
					buttonState = buttonState ^ XUSB_GAMEPAD_LEFT_THUMB;
					break;
				case R.id.buttonRightThumb:
					buttonState = buttonState ^ XUSB_GAMEPAD_RIGHT_THUMB;
					break;
				case R.id.buttonStart:
					buttonState = buttonState ^ XUSB_GAMEPAD_START;
					break;
				case R.id.buttonBack:
					buttonState = buttonState ^ XUSB_GAMEPAD_BACK;
					break;
				case R.id.buttonGuide:
					buttonState = buttonState ^ XUSB_GAMEPAD_GUIDE;
					break;
				default:
					break;
				}
				// 发送
				String data = getState();
				if (data != null || "" != data) {
					dataSender.send(data);
				}
				v.setPressed(false);
				return true;
			}
			
			return false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_xbox);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		String ip = bundle.getString("ip");
		String port = bundle.getString("port");
		String sensitivity = bundle.getString("sensitivity");
		
		String msg = "Connecting to " + ip + "\non" + port + "\nsensitivity: " + sensitivity + " ms";
		
		final TextView textViewDebug = (TextView) findViewById(R.id.textViewDebug);
		textViewDebug.setText(msg);
		textViewDebug.setVisibility(View.INVISIBLE);
		// 获取屏幕大小
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		// 左侧按键旋转
		GridLayout gridLayoutLeft = (GridLayout) findViewById(R.id.GridLayoutLeft);
		gridLayoutLeft.setPivotX(getResources().getDimension(R.dimen.button_size));
		gridLayoutLeft.setPivotY(getResources().getDimension(R.dimen.button_size));
		gridLayoutLeft.setX(screenWidth / 4);
		gridLayoutLeft.setY(screenHeight / 2);
		gridLayoutLeft.setRotation(-45);
		// 右侧按键旋转
		GridLayout gridLayoutRight = (GridLayout) findViewById(R.id.GridLayoutRight);
		gridLayoutRight.setPivotX(getResources().getDimension(R.dimen.button_size));
		gridLayoutRight.setPivotY(getResources().getDimension(R.dimen.button_size));
		gridLayoutRight.setX(screenWidth / 4 * 3);
		gridLayoutRight.setY(screenHeight / 4);
		gridLayoutRight.setRotation(45);
		
		// 设置按钮Listener
		Button buttonDpadLeft = (Button) findViewById(R.id.buttonDpadLeft);
		Button buttonDpadRight = (Button) findViewById(R.id.buttonDpadRight);
		Button buttonDpadUp = (Button) findViewById(R.id.buttonDpadUp);
		Button buttonDpadDown = (Button) findViewById(R.id.buttonDpadDown);
		Button buttonA = (Button) findViewById(R.id.buttonA);
		Button buttonB = (Button) findViewById(R.id.buttonB);
		Button buttonX = (Button) findViewById(R.id.buttonX);
		Button buttonY = (Button) findViewById(R.id.buttonY);
		Button buttonLeft = (Button) findViewById(R.id.buttonLeft);
		Button buttonRight = (Button) findViewById(R.id.buttonRight);
		Button buttonLeftThumb = (Button) findViewById(R.id.buttonLeftThumb);
		Button buttonRightThumb = (Button) findViewById(R.id.buttonRightThumb);
		Button buttonStart = (Button) findViewById(R.id.buttonStart);
		Button buttonBack = (Button) findViewById(R.id.buttonBack);
		Button buttonGuide = (Button) findViewById(R.id.buttonGuide);
		buttonDpadLeft.setOnTouchListener(buttonListener);
		buttonDpadRight.setOnTouchListener(buttonListener);
		buttonDpadUp.setOnTouchListener(buttonListener);
		buttonDpadDown.setOnTouchListener(buttonListener);
		buttonA.setOnTouchListener(buttonListener);
		buttonB.setOnTouchListener(buttonListener);
		buttonX.setOnTouchListener(buttonListener);
		buttonY.setOnTouchListener(buttonListener);
		buttonLeft.setOnTouchListener(buttonListener);
		buttonRight.setOnTouchListener(buttonListener);
		buttonLeftThumb.setOnTouchListener(buttonListener);
		buttonRightThumb.setOnTouchListener(buttonListener);
		buttonStart.setOnTouchListener(buttonListener);
		buttonBack.setOnTouchListener(buttonListener);
		buttonGuide.setOnTouchListener(buttonListener);
		
		// 设置Trigger
		SeekBar leftTrigger = (SeekBar) findViewById(R.id.leftTrigger);
		SeekBar righttTrigger = (SeekBar) findViewById(R.id.rightTrigger);
		leftTrigger.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				seekBar.setProgress(0);
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				leftTriggerValue = progress;
				
			}
		});
		righttTrigger.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				seekBar.setProgress(255);
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				rightTriggerValue = 255 - progress;
				
			}
		});
		righttTrigger.setProgress(255);
		
		// 设置Stick
		stickL = new StickView(XBoxActivity.this, screenWidth / 8, screenHeight / 2, 200);
		stickR = new StickView(XBoxActivity.this, screenWidth / 3 * 2, screenHeight / 3 * 2, 200);
		// 显示Stick
		FrameLayout xboxLayout = (FrameLayout) findViewById(R.id.XBoxLayout);
		xboxLayout.addView(stickR, 0);
		xboxLayout.addView(stickL, 0);
//		GridLayout stickContainer = (GridLayout) findViewById(R.id.stickContainer);
//		stickContainer.addView(stickL);
//		stickContainer.addView(stickR);
		
		// 连接TCP
		Toast.makeText(XBoxActivity.this, "Waiting for connection...", Toast.LENGTH_SHORT).show();
		dataSender = new DataSender(ip, Integer.parseInt(port), new ParseVibration((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)));
		dataSender.start();
		
//		if(!dataSender.isConnected()) {
//			Toast.makeText(XBoxActivity.this, "Failed to connect", Toast.LENGTH_LONG).show();
//			finish();
//		}
		
		// 启动Timer
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						String data = getState();
						if (data != null || "" != data) {
							dataSender.send(data);
							data += dataSender.dataReceive;
							textViewDebug.setText(data);
						}
						
					}
				});
				
			}
		}, 2000, Integer.parseInt(sensitivity));

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	public void exit() {
		if((System.currentTimeMillis() - exitTime) > 1000) {
			Toast.makeText(XBoxActivity.this, "再按一次返回退出", Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		}else {
			// 关闭TCP
			try {
				dataSender.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finish();
		}
	}
	
	public String getState() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", "xbox");
			jsonObject.put("wButtons", buttonState);
			jsonObject.put("bLeftTrigger", leftTriggerValue);// 0 - 255
			jsonObject.put("bRightTrigger", rightTriggerValue);
			jsonObject.put("sThumbLX", (int)(stickL.getStickX() * 32767));// -32768 - 32767
			jsonObject.put("sThumbLY", (int)(-stickL.getStickY() * 32767));
			jsonObject.put("sThumbRX", (int)(stickR.getStickX() * 32767));
			jsonObject.put("sThumbRY", (int)(-stickR.getStickY() * 32767));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
}

