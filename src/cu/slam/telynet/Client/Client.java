package cu.slam.telynet.Client;

import cu.slam.telynet.MainActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Client {

    public String code;
    public String name;
    public String telephone;
    public String email;
    public int status;

    public Client() {
    }

    public Client(String code, String name, String telephone, String email, int status) {
        this.code = code;
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public long save(SQLiteDatabase dbClients){
		ContentValues values = new ContentValues();
		values.put(MainActivity.KEY_CODE, code);
		values.put(MainActivity.KEY_NAME, name);
		values.put(MainActivity.KEY_TELF, telephone);
		values.put(MainActivity.KEY_EMAIL, email);
		values.put(MainActivity.KEY_STATUS, status);
		return dbClients.insert("client", null, values);
	}	
}
