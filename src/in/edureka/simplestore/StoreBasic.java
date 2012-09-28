package in.edureka.simplestore;

import java.util.ArrayList;

import android.util.Log;

public abstract class StoreBasic<T extends MerchandiseBasic> {
	
	// For debug only
	private static final String TAG = "StoreBasic";
		
	private ArrayList<T> _itemList;
	private ArrayList<String> _categoryList;
	private float _taxPercentage;
	private String _inventoryListFile;
	
	// Constructor
	public StoreBasic(float taxPercentage, String fileName) {
		this._itemList = new ArrayList<T>();
		this._categoryList = new ArrayList<String>();
		this._taxPercentage = taxPercentage;
		this._inventoryListFile = fileName;
		
		this.build_inventory();
	}
	
	// Miscellaneous Getter/Setter and Access functions
	public float get_taxPercentage() {
		return this._taxPercentage;
	}
	
	public void set_taxPercentage(float taxPercentage) {
		this._taxPercentage = taxPercentage;
	}
	
	public ArrayList<T> get_itemList() {
		return _itemList;
	}
	
	public ArrayList<String> get_categoryList() {
		return _categoryList;
	}

	// Other utility functions for a generic Store
	private boolean build_inventory() {
		StoreBuilder<T> builder = new StoreBuilder<T>(this._inventoryListFile);
		
		this._categoryList = builder.get_categoryList();
		this._itemList = builder.get_itemList();

		if (this._categoryList.size() < 1)
			return false;
		if (this._itemList.size() < 1)
			return false;
		
		return true;
	}
	
	public int get_totalNumOfCategories() {
		return _categoryList.size();
	}

	public ArrayList<String> get_itemsForCategorySimple(String categoryName) {
		ArrayList<String> categoryBasedItems = new ArrayList<String>();
		for(T item: this._itemList) {
			if (item != null) {
				if (item.get_itemCategory().compareToIgnoreCase(categoryName) == 0)
					categoryBasedItems.add(item.get_name());
			}
		}
		return categoryBasedItems;
	}

	public ArrayList<T> get_itemsForCategoryCompound(String categoryName) {
		ArrayList<T> categoryBasedItems = new ArrayList<T>();
		for(T item: this._itemList) {
			if (item != null) {
				if (item.get_itemCategory().compareToIgnoreCase(categoryName) == 0)
					categoryBasedItems.add(item);
			}
		}
		return categoryBasedItems;
	}
	
	public int get_numberOfItemsInCategory(String categoryName) {
		int countOfItems = 0;
		for(T item: this._itemList) {
			if (item != null) {
				if (item.get_itemCategory().compareToIgnoreCase(categoryName) == 0)
					countOfItems += 1;
			}
		}
		return countOfItems;
	}
	
	public Object get_itemDetails(String itemName, String categoryName) {
		Log.w(TAG, "Got: " + itemName + " " + categoryName);
		for(T curItem: this.get_itemsForCategoryCompound(categoryName)) {
			Log.w(TAG, "Got: " + curItem.get_name() + " " + curItem.get_itemCategory() + " " + curItem.get_price());
			if (curItem.get_name().compareTo(itemName) == 0) {
				Log.w(TAG, "Got: " + curItem.get_price());
				return curItem;
			}
		}
		return null;
	}

	public boolean get_itemDetails(T item) {
		String searchCategory = item.get_itemCategory();
		for(T curItem: this.get_itemsForCategoryCompound(searchCategory)) {
			if (curItem.equals(item)) {
				item = curItem; 
				return true;
			}
		}
		return false;
	}
	
	public int get_totalNumOfItemInStore() {
		return this._itemList.size();
	}
}
