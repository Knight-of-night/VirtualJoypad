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
		// ����������
		if (data == null || "" == data) {
			return;
		}
		// ����Json
		try {
			JSONObject jsonObject = new JSONObject(data.trim());
			// �����
			if ("xbox".compareTo(jsonObject.getString("id")) != 0) {
				return;
			}
			// ��ȡ
			int largeMotor = jsonObject.getInt("LargeMotor");
			int smallMotor = jsonObject.getInt("SmallMotor");
			// ���߼�����Ϊֻ��һ���������Բ��ñ����ֱ����𶯡�
			if ((largeMotor == 0) && (smallMotor == 0)) {
				vibrator.cancel();
			}else if((largeMotor == 255) && (smallMotor == 255)) {
				vibrator.vibrate(new long[] {0, 1000}, 0);
			}else {
				// �𶯲��ܵ���ǿ�ȣ��Դ�����Ƶ�ʴ���ǿ�ȣ������벻���ȽϺõ�ӳ�䣬���ڵķ�����������￴��һ����
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
