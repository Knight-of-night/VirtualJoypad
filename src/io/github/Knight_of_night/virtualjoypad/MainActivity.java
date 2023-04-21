package io.github.Knight_of_night.virtualjoypad;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView textViewGithubLink = (TextView) findViewById(R.id.textViewGithubLink);
		Button button1 = (Button) findViewById(R.id.button1);
		
		textViewGithubLink.setMovementMethod(LinkMovementMethod.getInstance());
		button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int joy_type = -1;
				String ip_str = ((EditText) findViewById(R.id.editText1)).getText().toString();
				String port_str = ((EditText) findViewById(R.id.editText2)).getText().toString();
				String sensitivity_str = ((EditText) findViewById(R.id.editText3)).getText().toString();
				
				RadioGroup radioGroup1 = ((RadioGroup) findViewById(R.id.radioGroup1));
				for (int i = 0; i < radioGroup1.getChildCount(); i++) {
					RadioButton radioButton = (RadioButton) radioGroup1.getChildAt(i);
					if(radioButton.isChecked()) {
						joy_type = i;
						break;
					}
				}
				
				Bundle bundle = new Bundle();
				bundle.putString("ip", ip_str);
				bundle.putString("port", port_str);
				bundle.putString("sensitivity", sensitivity_str);
				
				Intent intent = new Intent();
				
				switch (joy_type) {
				case 0:
//					ComponentName componentName = new ComponentName("io.github.Knight_of_night.virtualjoypad", "io.github.Knight_of_night.virtualjoypad.XBoxActivity");
					ComponentName componentName1 = new ComponentName(MainActivity.this, XBoxActivity.class);
					intent.setComponent(componentName1);
					
//					Intent intent1 = new Intent(MainActivity.this, XBoxActivity.class);
//					intent1.putExtras(bundle);
//					startActivity(intent1);
					break;
				
				case 1:
					ComponentName componentName2 = new ComponentName(MainActivity.this, PS4Activity.class);
					intent.setComponent(componentName2);
					break;

				default:
					Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT);
					return;
//					break;
				}
				
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
