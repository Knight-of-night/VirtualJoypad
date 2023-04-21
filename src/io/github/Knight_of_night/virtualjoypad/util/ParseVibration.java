/**
 * 
 */
package io.github.Knight_of_night.virtualjoypad.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Vibrator;

/**
 * @author Knight-of-night
 *
 */
public class ParseVibration implements ParseData {
	
	private Vibrator vibrator;
	
	public long vibrationBreak = 40;

	/**
	 * 
	 */
	public ParseVibration(Vibrator vibrator) {
		this.vibrator = vibrator;
	}

	@Override
	public void run(String data) {
		// 跳过空数据
		if (data == null || "" == data) {
			return;
		}
		// 解析Json
		try {
			JSONObject jsonObject = new JSONObject(data.trim());
			// 检查标记
			if ("xbox".compareTo(jsonObject.getString("id")) != 0) {
				return;
			}
			// 读取
			int largeMotor = jsonObject.getInt("LargeMotor");
			int smallMotor = jsonObject.getInt("SmallMotor");
			// 震动逻辑。因为只有一个震动马达，所以不好表现手柄的震动。
			if ((largeMotor == 0) && (smallMotor == 0)) {
				vibrator.cancel();
			}else if((largeMotor == 255) && (smallMotor == 255)) {
				vibrator.vibrate(new long[] {0, 1000}, 0);
			}else {
				// 震动不能调节强度，自创用震动频率代替强度，但是想不出比较好的映射，现在的方法将两个马达看成一样。
				long largeVibrationDiv5 = largeMotor / 5;
				long smallVibrationDiv5 = smallMotor / 5;
				long vibrationDuration = largeVibrationDiv5 + smallVibrationDiv5;
				vibrator.vibrate(new long[] {0, vibrationDuration, vibrationDuration}, 0);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
