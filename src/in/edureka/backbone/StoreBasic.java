package in.edureka.backbone;

import java.util.ArrayList;

public abstract class StoreBasic<T extends MerchandiseBasic> {
	
	protected ArrayList<T> _itemList;
	private ArrayList<String> _categoryList;
	private float _taxPercentage;
	private String _inventoryListFile;
	
	// Constructor
	public StoreBasic(float taxPercentage, String fileName) {
		this._categoryList = new ArrayList<String>();
		this._taxPercentage = taxPercentage;
		this._inventoryListFile = fileName;
		this.set_itemList();
	}
	
	// Function that each store type should implement
	public abstract T get_itemDetails(String itemName, String categoryName);
	public abstract ArrayList<T> get_itemsForCategory_full(String categoryName);
	public abstract boolean build_inventory();
	
	// Abstract Getter and Setter
	public abstract void set_itemList();
	public abstract void set_itemList(ArrayList<String> strValues);
	public abstract ArrayList<T> get_itemList();
	
	// Miscellaneous Getter/Setter and Access functions
	public float get_taxPercentage() {
		return this._taxPercentage;
	}
	
	public void set_taxPercentage(float taxPercentage) {
		this._taxPercentage = taxPercentage;
	}
		
	public ArrayList<String> get_categoryList() {
		return _categoryList;
	}

	public void set_categoryList(ArrayList<String> categoryList) {
		this._categoryList = categoryList;
	}

	public String get_inventoryListFile() {
		return _inventoryListFile;
	}
	
	// Other utility functions for a generic Store
	public int get_totalNumOfCategories() {
		return _categoryList.size();
	}

	public ArrayList<String> get_itemsForCategory_nameOnly(String categoryName) {
		ArrayList<String> categoryBasedItems = new ArrayList<String>();
		for(T item: this._itemList) {
			if (item != null) {
				if (item.get_itemCategory().compareToIgnoreCase(categoryName) == 0)
					categoryBasedItems.add(item.get_name());
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
		
	public int get_totalNumOfItemInStore() {
		return this._itemList.size();
	}
}
