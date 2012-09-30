package in.edureka.bookshopping;

import in.edureka.helpers.UserDatabaseHandler;
import in.edureka.transport.ShopUser;
import in.edureka.utils.EmailValidator;
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

public class RegisterActivity extends Activity {

	EditText etRegisterFullName;
	EditText etRegisterEmailId;
	EditText etRegisterPassword;
	EditText etRegisterPasswordReEnter;
	Button btnRegister;
	TextView tvLoginScreen;
	
	OnClickListener myOnClickListener;
	
	ShopUser currentUser;
	UserDatabaseHandler db;
	
	private String strValidationFailReason;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        db = new UserDatabaseHandler(this);
        
        myOnClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btnRegister:
					String strFullName = etRegisterFullName.getText().toString();
					String strEmailId = etRegisterEmailId.getText().toString();
					String strPassword = etRegisterPassword.getText().toString();

					if (areInputsFilled(strFullName, strEmailId, strPassword,
							etRegisterPasswordReEnter.getText().toString()) == false)
					{
						Toast.makeText(getApplicationContext(), 
								strValidationFailReason + "\nPlease re-check the provided information.", 
								Toast.LENGTH_SHORT).show();
					}
					else
					{
						if (db.readShopUser(strEmailId) != null)
						{
							Toast.makeText(getApplicationContext(), 
									"You are already registered.\nPlease go back and Login.", 
									Toast.LENGTH_SHORT).show();
						}
						else
						{
							currentUser = new ShopUser(strEmailId, strPassword, strFullName);
							if (db.addShopUser(currentUser) == true)
							{
								Toast.makeText(getApplicationContext(), 
										"You are logged in", 
										Toast.LENGTH_SHORT).show();	
								Intent i=new Intent(getApplicationContext(), ItemDisplayActivity.class);
								i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
								i.putExtra("in.edureka.transport.ShopUser", currentUser);
								startActivity(i);
							}
						}

					}
					break;
				case R.id.tvLinkToLogin:
					Intent i = new Intent(getApplicationContext(), BookshopLogin.class);
					startActivity(i);
					break;
				default:
					Toast.makeText(getApplicationContext(),
							"Debug: Weird Stuff",
							Toast.LENGTH_SHORT).show();
				
			}
        	
        }};
        
        etRegisterFullName = (EditText)findViewById(R.id.etRegisterFullName);
        etRegisterEmailId = (EditText)findViewById(R.id.etRegisterEmailId);
        etRegisterPassword = (EditText)findViewById(R.id.etRegisterPassword);
        etRegisterPasswordReEnter = (EditText)findViewById(R.id.etRegisterPasswordConfirm);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        tvLoginScreen = (TextView)findViewById(R.id.tvLinkToLogin);
        
        btnRegister.setOnClickListener(myOnClickListener);
        tvLoginScreen.setOnClickListener(myOnClickListener);
    }

    private boolean areInputsFilled(String strFullName, String strEmailId,
    		String strPassword, String strPasswordReEnter) 
    {
    	EmailValidator emailValidator;
    	
		if (strFullName.isEmpty() == true || strEmailId.isEmpty() == true ||
				strPassword.isEmpty() == true || strPasswordReEnter.isEmpty() == true)
		{
			strValidationFailReason = "One of the Required fields is empty.";
			return false;
		}

		if ( strPassword.compareTo(strPasswordReEnter) != 0 )
		{
			strValidationFailReason = "Password does not match.";
			return false;
		}

		emailValidator = new EmailValidator();
		
		if (emailValidator.validate(strEmailId) == false)
		{
			strValidationFailReason = "Provided Email does not match the normal format.";
			return false;
		}
				
		return true;
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register, menu);
        return true;
    }
}
