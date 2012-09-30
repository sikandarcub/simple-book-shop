package in.edureka.transport;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class ShopItem implements Parcelable{
	// For debug only
	private static final String TAG = "ShopItem";
	
	private String _name;
	private double _price;
	private int _quantity;
	
	public ShopItem() {
		this._name = null;
		this._price = 0;
		this._quantity = 0;
	}
	
	// Constructor for a 'Parcel' Shop Item
	public ShopItem(Parcel source) {
		this();
		readFromParcel(source);
	}
	
	public ShopItem(String name, double price) {
		this._name = name;
		this._price = price;
		this._quantity = 1;
	}

	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public double get_price() {
		return _price;
	}
	public void set_price(double _price) {
		this._price = _price;
	}
	public int get_quantity() {
		return _quantity;
	}
	public void set_quantity(int _quantity) {
		this._quantity = _quantity;
	}
	
	@Override
	public int describeContents() {
		return 50;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		Log.v(TAG, "writeToParcel..."+ flags);
		dest.writeString(get_name());
		dest.writeDouble(get_price());
		dest.writeInt(get_quantity());
	}

	private void readFromParcel(Parcel in) {
		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		_name = in.readString();
		_price = in.readDouble();
		_quantity = in.readInt();
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
    public static final Creator<ShopItem> CREATOR =
        	new Creator<ShopItem>() {
                public ShopItem createFromParcel(Parcel source) {
                    return new ShopItem(source);
                }
     
                public ShopItem[] newArray(int size) {
                    return new ShopItem[size];
                }
            };
}
