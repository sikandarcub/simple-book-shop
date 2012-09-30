package in.edureka.helpers;

import android.os.Parcel;
import in.edureka.transport.ShopItem;

public class ShoppingCartItem extends ShopItem {

	private boolean _selected;

	public ShoppingCartItem() {
		super();
		this._selected = false;
	}

	public ShoppingCartItem(Parcel source) {
		super(source);
		this._selected = false;
	}

	public ShoppingCartItem(String name, double price) {
		super(name, price);
		this._selected = false;
	}

	public boolean is_selected() {
		return _selected;
	}

	public void set_selected(boolean _selected) {
		this._selected = _selected;
	}

}
