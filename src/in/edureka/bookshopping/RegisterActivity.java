package in.edureka.bookshopping;

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
				case R.id.btn_Register:
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
						if (db.readShopUser(strEmailId).get_userName() != null)
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
										"You are thru!!", 
										Toast.LENGTH_SHORT).show();	
							}
						}

					}
					break;
				case R.id.tv_Link_To_Login:
					Intent i = new Intent(getApplicationContext(), BookshopLogin.class);
					startActivity(i);
					break;
				default:
					Toast.makeText(getApplicationContext(),
							"Debug: Weird Stuff",
							Toast.LENGTH_SHORT).show();
				
			}
        	
        }};
        
        etRegisterFullName = (EditText)findViewById(R.id.et_Register_FullName);
        etRegisterEmailId = (EditText)findViewById(R.id.et_Register_EmailId);
        etRegisterPassword = (EditText)findViewById(R.id.et_Register_Password);
        etRegisterPasswordReEnter = (EditText)findViewById(R.id.et_Register_Password_Confirm);
        btnRegister = (Button)findViewById(R.id.btn_Register);
        tvLoginScreen = (TextView)findViewById(R.id.tv_Link_To_Login);
        
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
			strValidationFailReason = "One of the Required fields is empty";
			return false;
		}

		if ( strPassword.compareTo(strPasswordReEnter) != 0 )
		{
			strValidationFailReason = "Password does not match";
			return false;
		}

		emailValidator = new EmailValidator();
		
		if (emailValidator.validate(strEmailId) == false)
		{
			strValidationFailReason = "Provided Email does not match the normal format";
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
