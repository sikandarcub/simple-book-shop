package in.edureka.bookshopping;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BookshopLogin extends Activity {
	
	TextView tvRegisterScreen;
	EditText etLoginEmailId;
	EditText etLoginPassword;
	Button btnLogin;

	OnClickListener myOnClickListener;
	
	ShopUser currentUser;
	UserDatabaseHandler db;
	
	private String strValidationFailReason;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshop_login);
        
        db = new UserDatabaseHandler(this);

        myOnClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btn_Login:
					if (areInputsFilled(etLoginEmailId.getText().toString(),
							etLoginPassword.getText().toString()) == false)
					{
						Toast.makeText(getApplicationContext(), 
								strValidationFailReason + "\nPlease check and try again.", 
								Toast.LENGTH_SHORT).show();
					}
					else
					{
						currentUser = new ShopUser(etLoginEmailId.getText().toString(),
								etLoginPassword.getText().toString());
						if (db.isValidLogin(currentUser) == true)
						{
							Toast.makeText(getApplicationContext(), 
									"You are thru!!", 
									Toast.LENGTH_SHORT).show();							
							
						}
						else
						{
							Toast.makeText(getApplicationContext(), 
									"You do not required access.\nPlease register to login.", 
									Toast.LENGTH_SHORT).show();
						}
						
					}
					break;
				case R.id.tv_Register_Here:
					Intent i=new Intent(getApplicationContext(), RegisterActivity.class);
					startActivity(i);
					break;
				default:
					Toast.makeText(getApplicationContext(), 
							"Debug: Weird Stuff", 
							Toast.LENGTH_SHORT).show();				
				}
				
				
				
			}
        };
        
        tvRegisterScreen = (TextView)findViewById(R.id.tv_Register_Here);
        etLoginEmailId = (EditText)findViewById(R.id.et_Login_EmailId);
        etLoginPassword = (EditText)findViewById(R.id.et_Login_Password);
        btnLogin = (Button)findViewById(R.id.btn_Login);
       
        btnLogin.setOnClickListener(myOnClickListener);
        tvRegisterScreen.setOnClickListener(myOnClickListener);
    }

	/**
	 * Check if the user provided details pass the basic checks
	 * @param strEmailId Email ID string to Check
	 * @param strPassword Password string to check
	 * @return true valid inputs, false invalid inputs
	 */
	private Boolean areInputsFilled(String strEmailId, String strPassword)
	{
		if (strEmailId.isEmpty() == true || strPassword.isEmpty() == true )
		{
			strValidationFailReason = "One of the Required fields is empty";
			return false;
		}
		
		return true;
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_bookshop_login, menu);
        return true;
    }
}
