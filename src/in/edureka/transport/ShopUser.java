package in.edureka.transport;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Class to Hold information relevant to a user of the Book Shop
 * @author Krishna Koneru
 * 
 */
public class ShopUser implements Parcelable{

	// For debug only
	private static final String TAG = "ShopUser";
	
	// private variables 
	private String _userName = null;
	private String _password = null;
	private String _fullName = null;
	private boolean _passwordFailedValidation = false;

	private ArrayList<ShopItem> _shoppingList = new ArrayList<ShopItem>();
	private float _taxPercent = 0;
	
	// Constructor
	public ShopUser() {
		this._userName = null;
		this._password = null;
		this._fullName = null;
		this._passwordFailedValidation = false;
		this.set_shoppingList(new ArrayList<ShopItem>());
	}
	
	// Another Constructor
	public ShopUser(String userName, String password, String fullName) {
		this._userName = userName;
		this._password = password;
		this._fullName = fullName;
		this._passwordFailedValidation = false;
		this.set_shoppingList(new ArrayList<ShopItem>());
	}

	// Yet another Constructor
	public ShopUser(String userName, String password) {
		this._userName = userName;
		this._password = password;
		this._fullName = null;
		this._passwordFailedValidation = false;
		this.set_shoppingList(new ArrayList<ShopItem>());
	}
	
	// Constructor for a 'Parcel' Shop User
	public ShopUser(Parcel source) {
		this();
		readFromParcel(source);
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

	public ArrayList<ShopItem> get_shoppingList() {
		return _shoppingList;
	}

	public void set_shoppingList(ArrayList<ShopItem> _shoppingList) {
		this._shoppingList = _shoppingList;
	}

	public void add_itemToShoppingList(ShopItem item) {
		this._shoppingList.add(item);
	}

	public void cleanup_shoppingList() {
		this._shoppingList.clear();
	}
	
	public boolean is_passwordFailedValidation() {
		return _passwordFailedValidation;
	}

	public void set_passwordFailedValidation(boolean _passwordFailedValidation) {
		this._passwordFailedValidation = _passwordFailedValidation;
	}

    public float get_taxPercent() {
		return _taxPercent;
	}

	public void set_taxPercent(float _taxPercent) {
		this._taxPercent = _taxPercent;
	}
	
	/**
	 * Build the Item Names for display
	 * @return
	 */
	public String get_itemNames() {
		String appendStr = "- ";
		String finalStr = "";
		
		for (ShopItem item:this.get_shoppingList())
		{
			if (item != null)
			{
				if (finalStr.isEmpty() == true)
				{
					finalStr = appendStr + item.get_name() + "\n";
				}
				else
					finalStr += appendStr + item.get_name() + "\n";
			}
		}
		return finalStr;
	}

	/**
	 * Get total cost to the user based on the current purchase;
	 * @return total cost of the current purchase
	 */
    public double get_totalCostExcludingTax() {
    	double totalCost = 0;
		for (ShopItem item:this.get_shoppingList())
		{
			if (item != null)
			{
				totalCost += item.get_price() * item.get_quantity();
			}
		}
		return totalCost;
    }

	/**
	 * Get total tax for the current purchase;
	 * @return total cost of the current purchase
	 */
    public double get_totalTax() {
		double totalCost = 0;
		double tax = 0;
		if ((totalCost = this.get_totalCostExcludingTax()) > 0)
		{
			tax = (totalCost * (double) this.get_taxPercent()) / 100;
		}
		return tax;
    }
    
	/**
	 * Get grand total of purchase including Tax;
	 * @return total cost of the current purchase
	 */
	public double get_totalCostInclusiveTax() {
		double totalCost = 0;
		if ((totalCost = this.get_totalCostExcludingTax()) > 0)
		{
			double tax = (totalCost * (double) this.get_taxPercent()) / 100;
			totalCost += tax;
		}
		return totalCost;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// We just need to write each field into the
		// parcel. When we read from parcel, they
		// will come back in the same order
		Log.v(TAG, "writeToParcel..."+ flags);
		dest.writeString(get_fullName());
		dest.writeString(get_userName());
		dest.writeFloat(get_taxPercent());
		dest.writeTypedList(get_shoppingList());
	}
	
	private void readFromParcel(Parcel source) {
		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		_fullName = source.readString();
		_userName = source.readString();
		_taxPercent = source.readFloat();
		source.readTypedList(_shoppingList, ShopItem.CREATOR);
	}

	/**
    *
    * This field is needed for Android to be able to
    * create new objects, individually or as arrays.
    *
    * This also means that you can use the default
    * constructor to create the object and use another
    * method to hyrdate it as necessary.
    *
    */
    public static final Creator<ShopUser> CREATOR =
        	new Creator<ShopUser>() {
                public ShopUser createFromParcel(Parcel source) {
                    return new ShopUser(source);
                }
     
                public ShopUser[] newArray(int size) {
                    return new ShopUser[size];
                }
            };
}
