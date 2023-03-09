package cu.slam.telynet;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cu.slam.telynet.Client.Client;
import Controller.Control;
import Controller.OpenPhone;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private Control ctrl;
	OpenPhone phoneCall;
	private List<Client> clients;
	private ArrayList<String> dataClient;
	ListView list;
	public static Comparator<Client> oname; 
	public static Comparator<Client> ocode;
	
	public static final String KEY_CODE = "code";
	public static final String KEY_NAME = "name";
	public static final String KEY_TELF = "telephone";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_STATUS = "status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// Constructor 
        super.onCreate(savedInstanceState);
        //setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        
        try{
        	ctrl = new Control(getApplicationContext());
        	clients = ctrl.getClientList();
        }catch (IOException e){
        	Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        	Log.e("IOE", e.getMessage());
        }
        list = (ListView) findViewById(R.id.listView1);
        registerForContextMenu(list);
        updateList();
        
    }
    
    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	// TODO Auto-generated method stub
    	if(v.getId()== R.id.listView1){
    		menu.setHeaderTitle("MENU");
    		menu.add(R.string.menu_phonecall);
    		menu.add(R.string.menu_fvisited);
    		menu.add(R.string.menu_fnvisited);
    		menu.add(R.string.menu_ocode);
    		menu.add(R.string.menu_oname);
    		menu.add(R.string.menu_reset);
    		
    	}
    	//super.onCreateContextMenu(menu, v, menuInfo);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
				
    	if(item.toString().equals("Phone Call")){			
			phoneCall = new OpenPhone(getApplicationContext(), clients.get(info.position).getTelephone());
			phoneCall.launchIntent();
			return true;
    	}else if(item.toString().equals("Filter Visited")){
    		filter(1);
    		return true;
    	}else if(item.toString().equals("Filter Not Visited")){
    		filter(0);
    		return true;
    	}else if(item.toString().equals("Order by Code")){
    		orderByCode();
    		updateList();
    	}
    	else if(item.toString().equals("Order by Name")){
    		orderByName();
    		updateList();
    	}
    	else if(item.toString().equals("Reset")){
    		orderByName();
    		updateList();
    	}
    	return super.onContextItemSelected(item);
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
        switch(id){
        case R.id.action_addclient:
        	launchAddClientActivity();
        	break;
        case R.id.action_settings:
        	AboutMsg();
        	break;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    public void launchAddClientActivity(){
    	// Start a new Activity waiting for result
    	startActivityForResult(new Intent(this, NewClient.class),1);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// Handle a result of the activity
    	  if(requestCode == 1){
    		  if(resultCode == RESULT_OK){
    			  Bundle saco = data.getExtras();
    			  String code=saco.getString(KEY_CODE);
    			  String name = saco.getString(KEY_NAME);
    			  String telephone = saco.getString(KEY_TELF);
    			  String email = saco.getString(KEY_EMAIL);
    			  int status = saco.getInt(KEY_STATUS);
    			  Client newClient = new Client(code, name, telephone, email, status);
    			  ctrl.AddClient(newClient);
    			  updateList();
    			  Toast.makeText(getApplicationContext(), "Client added!", Toast.LENGTH_SHORT).show();
    		  }
    	  }
    
    }
    
    public void AboutMsg(){
    	// Just a AlertDialog message for the User
    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	dialog.setTitle("Telynet Clients");
    	dialog.setMessage("Aplicación para prueba técnica de selección de Telynet\n\nHecho por Allan Ayes\n");
    	dialog.setPositiveButton("OK", null);
    	dialog.show();
    }
    
    public void updateList(){
    	// Update the list activity
    	dataClient = new ArrayList<String>();
    	for(Client c:clients)
    		dataClient.add(c.code+"\n"+c.name+"\n"+c.email+"\n"+c.telephone+"\n"+c.status);
    	list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,dataClient));
    }     
    
        
    public void filter(int stats){
		dataClient = new ArrayList<String>();    	    	    	
		for(Client c:clients)
			if(c.getStatus()==stats)
				dataClient.add(c.code+"\n"+c.name+"\n"+c.email+"\n"+c.telephone+"\n"+c.status);
		list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,dataClient));
			
	}
    
    public void orderByCode(){
    	ocode = new Comparator<Client>() {
			
			@Override
			public int compare(Client arg0, Client arg1) {				
				return arg0.getCode().compareTo(arg1.getCode());
			}    	
    	};
    	Collections.sort(clients,ocode);
    }
    public void orderByName(){
    	oname = new Comparator<Client>() {
			
			@Override
			public int compare(Client arg0, Client arg1) {
				return arg0.getName().compareTo(arg1.getName());
			}
		};
		Collections.sort(clients,oname);
    }
}
