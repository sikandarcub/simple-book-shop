package in.edureka.bookshopping;

/**
 * Class to Hold information relevant to a user of the Book Shop
 * @author Krishna Koneru
 * 
 */
public class ShopUser {
	// private variables 
	private String _userName;
	private String _password;
	private String _fullName;
	private boolean _passwordFailedValidation;

	// Constructor
	public ShopUser(){
		
	}
	
	// Another Constructor
	public ShopUser(String userName, String password, String fullName) {
		this._userName = userName;
		this._password = password;
		this._fullName = fullName;
		this._passwordFailedValidation = false;
	}

	// Yet another Constructor
	public ShopUser(String userName, String password) {
		this._userName = userName;
		this._password = password;
		this._passwordFailedValidation = false;
	}
	
	// get _userName
	public String get_userName() {
		return _userName;
	}

	// Set _userName
	public void set_userName(String _userName) {
		this._userName = _userName;
	}

	// get _password
	public String get_password() {
		return _password;
	}

	// set _password
	public void set_password(String _password) {
		this._password = _password;
	}

	// get _fullName
	public String get_fullName() {
		return _fullName;
	}

	// set _fullName
	public void set_fullName(String _fullName) {
		this._fullName = _fullName;
	}

	public boolean is_passwordFailedValidation() {
		return _passwordFailedValidation;
	}

	public void set_passwordFailedValidation(boolean _passwordFailedValidation) {
		this._passwordFailedValidation = _passwordFailedValidation;
	}
}
