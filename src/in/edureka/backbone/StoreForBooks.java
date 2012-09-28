package in.edureka.backbone;

import java.util.ArrayList;

import android.util.Log;

public class StoreForBooks extends StoreBasic<MerchandiseBooks> {

	// For debug only
	private static final String TAG = "StoreForBooks";

	public StoreForBooks(float taxPercentage, String fileName) {
		super(taxPercentage, fileName);
		this.build_inventory();
	}

	@Override
	public void set_itemList() {
		this._itemList = new ArrayList<MerchandiseBooks>();
	}

	@Override
	public void set_itemList(ArrayList<String> strValues) {
		for(String thisStr: strValues) {
			MerchandiseBooks newBook = MerchandiseBooks.getObj(thisStr);
			if (newBook != null)
			{
				this._itemList.add(newBook);
			}
		}
	}

	@Override
	public ArrayList<MerchandiseBooks> get_itemList() {
		return _itemList;
	}

	public ArrayList<MerchandiseBooks> get_booksForAuthor(String authorName)
	{
		ArrayList<MerchandiseBooks> booksByAuthor = new ArrayList<MerchandiseBooks>();
		for (MerchandiseBooks thisBook : this._itemList)
		{
			if (thisBook.get_authorName().compareToIgnoreCase(authorName) == 0)
			{
				booksByAuthor.add(thisBook);
			}
		}
		return booksByAuthor;
	}

	@Override
	public MerchandiseBooks get_itemDetails(String itemName, String categoryName) {
		Log.w(TAG, "Got: " + itemName + " " + categoryName);
		for(MerchandiseBooks curItem: this.get_itemsForCategory_full(categoryName)) {
			Log.w(TAG, "Got: " + curItem.get_name() + " " + curItem.get_itemCategory() + " " + curItem.get_price());
			if (curItem.get_name().compareTo(itemName) == 0) {
				Log.w(TAG, "Got: " + curItem.get_price());
				return curItem;
			}
		}
		return null;
	}

	@Override
	public ArrayList<MerchandiseBooks> get_itemsForCategory_full(
			String categoryName) {
		ArrayList<MerchandiseBooks> categoryBasedItems = new ArrayList<MerchandiseBooks>();
		for(MerchandiseBooks item: this._itemList) {
			if (item != null) {
				String currentCategory = item.get_itemCategory();
				if (currentCategory.compareTo(categoryName) == 0)
					categoryBasedItems.add(item);
			}
		}
		return categoryBasedItems;
	}
	
	@Override
	public boolean build_inventory() {
		StoreBuilder builder = new StoreBuilder(this.get_inventoryListFile());
		
		this.set_itemList(builder.get_itemList());
		builder.set_categoryList(this.get_itemList());
		this.set_categoryList(builder.get_categoryList());

		if (this.get_categoryList().size() < 1)
			return false;
		if (this.get_itemList().size() < 1)
			return false;
		
		return true;
	}
}