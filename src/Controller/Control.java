package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import cu.slam.telynet.MainActivity;
import cu.slam.telynet.Client.Client;

public class Control {

	private List<Client> clients;
	private File dbClients;
	private Context context;
	SQLiteDatabase sqli;
	
	public Control(Context context) throws IOException{		
		// Constructor and Database creation
		this.context = context;
		clients = new ArrayList<Client>();
		dbClients = new File(context.getCacheDir(),"/clients.db");
		//Toast.makeText(context, "Existe: "+dbClients.exists(), Toast.LENGTH_SHORT).show();
	
		if(!dbClients.exists()){
			dbClients.getParentFile().mkdirs();
			dbClients.createNewFile();		
			sqli = SQLiteDatabase.openDatabase(dbClients.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);		
			sqli.execSQL("CREATE TABLE 'client'('code' TEXT PRIMARY KEY, 'name' TEXT, 'telephone' TEXT, 'email' TEXT, 'status' INTEGER);");
			/*Filling db with data*/
			ClientGen cg=new ClientGen();
			for(int i=0;i<100;i++){
				String insert = "INSERT INTO client(code,name,telephone,email,status) VALUES ('"+cg.genRanCode()+"','"+cg.genRanName()+"','"+cg.genRanTelf()+"','"+cg.genRanEmail()+"','"+cg.genRanStatus()+"');";
				Log.v("INSERT", insert);
				sqli.execSQL(insert);
			}
			sqli.close();
		}
		// Saving all client into a list
		Toast.makeText(this.context, "Loading data...", Toast.LENGTH_SHORT).show();
		loadClientsFromDB();
	}
	
	public void AddClient(Client c){
		// Add a new client and save in the db
		clients.add(c);
		sqli = SQLiteDatabase.openDatabase(dbClients.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
		//String insert = "INSERT INTO client(code,name,telephone,email,status) VALUES ('"+c.getCode()+"','"+c.getName()+"','"+c.getTelephone()+"','"+c.getEmail()+"','"+c.getStatus()+"');";
		//sqli.execSQL(insert);
		
		// Other way to insert into database		
		c.save(sqli);
		sqli.close();
	}
	
	public void loadClientsFromDB(){
		// query all the client in the db and add into a list
		Client c=null;
		sqli = SQLiteDatabase.openDatabase(dbClients.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
		String query = "SELECT * FROM 'client'";
        Cursor cursor = sqli.rawQuery(query, null);
        cursor.moveToFirst();
        
        while (cursor.isAfterLast() == false) {
            for (int j = 0; j < cursor.getColumnCount(); j++){
                String code = cursor.getString(0);
                String name = cursor.getString(1);
                String telf = cursor.getString(2);
                String email = cursor.getString(3);
                int status = Integer.valueOf(cursor.getString(4));                
                c = new Client(code, name, telf, email, status);
            }
            clients.add(c);
            cursor.moveToNext();
        }
        cursor.close();
        sqli.close();
	}
	
	public List<Client> getClientList(){
		// get the list of clients		
		return clients;
	}	
	
}
