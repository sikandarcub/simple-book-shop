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
				case R.id.btnLogin:
					String strEmailId = etLoginEmailId.getText().toString();
					String strPassword = etLoginPassword.getText().toString();
					
					if (areInputsFilled(strEmailId, strPassword) == false)
					{
						Toast.makeText(getApplicationContext(), 
								strValidationFailReason + "\nPlease check and try again.", 
								Toast.LENGTH_SHORT).show();
					}
					else
					{
						currentUser = new ShopUser(strEmailId, strPassword);
						if (db.isValidLogin(currentUser) == true)
						{
							Toast.makeText(getApplicationContext(),
									"You are Logged On.",
									Toast.LENGTH_SHORT).show();
							Intent i=new Intent(getApplicationContext(), ItemDisplayActivity.class);
							startActivity(i);
						}
						else
						{
							String strToDisplay = null;
							if (currentUser.is_passwordFailedValidation() == true)
							{
								strToDisplay = "Your password does not match.\n" +
										"Please check and try again";
							}
							else
							{
								strToDisplay = "You do not have required access.\n" +
										"Please register to login.";
							}
							Toast.makeText(getApplicationContext(), strToDisplay, 
									Toast.LENGTH_SHORT).show();
						}
					}
					break;
				case R.id.tvRegisterHere:
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
        
        tvRegisterScreen = (TextView)findViewById(R.id.tvRegisterHere);
        etLoginEmailId = (EditText)findViewById(R.id.etLoginEmailId);
        etLoginPassword = (EditText)findViewById(R.id.etLoginPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
       
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
			strValidationFailReason = "One of the Required fields is empty.";
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
