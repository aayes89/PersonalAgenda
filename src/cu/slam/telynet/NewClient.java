package cu.slam.telynet;

import android.support.v7.app.ActionBarActivity;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;


@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class NewClient extends ActionBarActivity {

	Button save;
	Switch visited;
	EditText etCode;
	EditText etName;
	EditText etTelf;
	EditText etEmail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_client);
		etCode = (EditText)findViewById(R.id.textEdit1);
		etName = (EditText)findViewById(R.id.textEdit2);
		etTelf = (EditText)findViewById(R.id.textEdit3);
		etEmail = (EditText)findViewById(R.id.textEdit4);
		visited = (Switch)findViewById(R.id.switch1);		
		save = (Button)findViewById(R.id.btnSave);
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {			
				saveData();
			}
		});
	}
	
	public void saveData(){
		// A bundle to save all data acquired				 
		Bundle data = new Bundle();
		String code = etCode.getText().toString();
		String name = etName.getText().toString();
		String telf = etTelf.getText().toString();
		String email = etEmail.getText().toString();
		if(!code.isEmpty()&&!name.isEmpty()&&!telf.isEmpty()&&!email.isEmpty()){
			data.putString(MainActivity.KEY_CODE,code);
			data.putString(MainActivity.KEY_NAME,name);
			data.putString(MainActivity.KEY_TELF,telf);
			data.putString(MainActivity.KEY_EMAIL,email);
			data.putInt(MainActivity.KEY_STATUS, visited.isChecked()?1:0); 
			
			//Creamos el Intent y le ponemos el Bundle como extras
			Intent intent = new Intent();
			intent.putExtras(data);
			setResult(RESULT_OK, intent);
			this.finish();
		}else{
			new AlertDialog.Builder(this)
			.setTitle("Attention!")
			.setMessage("You must fill all boxes")
			.show();
		}
	}
	
}
