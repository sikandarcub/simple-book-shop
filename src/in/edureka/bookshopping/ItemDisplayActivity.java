package in.edureka.bookshopping;

import java.util.ArrayList;

import in.edureka.backbone.MerchandiseBooks;
import in.edureka.backbone.StoreForBooks;
import in.edureka.transport.ShopItem;
import in.edureka.transport.ShopUser;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDisplayActivity extends Activity {

	// For debug only
	private static final String TAG = "ItemDisplay";
	
	Spinner spCategoryList;
	Spinner spItemList;
	TableLayout tlSelectedItemDetails;
	TextView tvBookAuthor;
	TextView tvBookPrice;
	ImageView ivBookCover;
	Button btnItemAdd;
	TextView tvLinkToCartDisplay;
	
	StoreForBooks bookStore;
	MerchandiseBooks bookSelected;
	
	String[] strCategories;
	String[] strItemsInCategory;
	
	String strSelectedCategory;
	String strSelectedItem;

	ShopUser currentUser;
	
	private OnItemSelectedListener myOnItemSelectedListener = new OnItemSelectedListener () {

		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			switch (parent.getId()) {
			case R.id.spCategoryList:
				strSelectedCategory = strCategories[position];
				refreshItemList(strSelectedCategory);
		        break;
			case R.id.spItemList:
				strSelectedItem = strItemsInCategory[position];
				displayItemInformation(strSelectedItem,strSelectedCategory);
				break;
			default:
				Toast.makeText(getApplicationContext(), 
						"Debug: Weird Stuff", 
						Toast.LENGTH_SHORT).show();	
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}

	};

	private OnClickListener myOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnItemAdd:
				String itemNames = null;
				String displayMessage = null;
				itemNames = addItemToUserShoppingList(bookSelected);
				if (itemNames != null)	{
					displayMessage = "Error: Could not add item to shopping cart";
				}
				else {
					displayMessage = "Item added to Cart.\nContents of your cart:\n" + itemNames;
				}
				Toast.makeText(getApplicationContext(), 
						displayMessage, 
						Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.tvLinkToCartDisplay:
				Intent i=new Intent(getApplicationContext(), CartDisplayActivity.class);
				i.putExtra("in.edureka.transport.ShopUser", currentUser);
				startActivity(i);
				break;
				
			default:
				Toast.makeText(getApplicationContext(), 
						"Debug: Weird Stuff", 
						Toast.LENGTH_SHORT).show();
			
			}
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle b = getIntent().getExtras();
        currentUser =
			b.getParcelable("in.edureka.transport.ShopUser");
		
        setContentView(R.layout.activity_item_display);
        
        spCategoryList = (Spinner)findViewById(R.id.spCategoryList);
        spItemList = (Spinner)findViewById(R.id.spItemList);
        tlSelectedItemDetails = (TableLayout)findViewById(R.id.tlSelectedItemDetails);
        tvBookAuthor = (TextView)findViewById(R.id.tvBookAuthor);
        tvBookPrice = (TextView)findViewById(R.id.tvBookPrice);
        ivBookCover = (ImageView)findViewById(R.id.ivBookCover);
        btnItemAdd = (Button)findViewById(R.id.btnItemAdd);
        tvLinkToCartDisplay = (TextView)findViewById(R.id.tvLinkToCartDisplay);
        
        spCategoryList.setOnItemSelectedListener(myOnItemSelectedListener);
        spItemList.setOnItemSelectedListener(myOnItemSelectedListener);
        
        btnItemAdd.setOnClickListener(myOnClickListener );
        tvLinkToCartDisplay.setOnClickListener(myOnClickListener);

        initializeCategoryList();
    }

    /**
     * Derives and fills the relevant data for the Item
     * @param currentItem Title for the selected item
     * @param currentCategory Category selected by the user
     */
    protected void displayItemInformation(String currentItem,
			String currentCategory) {
		MerchandiseBooks thisBook = new MerchandiseBooks();
		
		thisBook = bookStore.get_itemDetails(
				currentItem,currentCategory);
		
		if (thisBook.get_name() != null)
		{	
			tvBookAuthor.setText(thisBook.get_authorName());
			tvBookPrice.setText(thisBook.get_price());
		}
	}

    /**
     * Derives the Item list for the selected Category and updates itemList spinner
     * @param currentCategory Category selected by the user
     */
	protected void refreshItemList(String currentCategory) {
		ArrayList<String> alItems = new ArrayList<String>();
		alItems = bookStore.get_itemsForCategory_nameOnly(currentCategory);
		
		strItemsInCategory = new String[alItems.size()];
		strItemsInCategory = alItems.toArray(strItemsInCategory);
		
		ArrayAdapter<String> aaItems = 
				new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.simple_spinner_item,
						strItemsInCategory);
		aaItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spItemList.setAdapter(aaItems);
	}

	/**
	 * Derives the category list and updates categoryList spinner
	 */
	private void initializeCategoryList() {
        float taxPercentage = (float) 6.5;
        String fileDB = new String("fileDB.csv"); 
        bookStore = new StoreForBooks(taxPercentage, fileDB);
        if (bookStore.get_totalNumOfItemInStore() < 0 )
        {
        	Toast.makeText(getApplicationContext(), "No Items in Store", Toast.LENGTH_SHORT).show();
        }
        
        ArrayList<String> alCategories = bookStore.get_categoryList();
        
        Log.w(TAG, "Got: " + alCategories.size());
        
        strCategories = new String[alCategories.size()];
        strCategories = alCategories.toArray(strCategories);

        ArrayAdapter<String> aaCategories = 
        		new ArrayAdapter<String>(
        				this,
        				android.R.layout.simple_spinner_item,
        				strCategories);
        aaCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spCategoryList.setAdapter(aaCategories);
        
		Toast.makeText(getApplicationContext(), 
				"Debug: " + currentUser.get_fullName() + " " + currentUser.get_userName(), 
				Toast.LENGTH_SHORT).show();		
	}

	/**
	 * Adds the selected Book to the Shopping list of the user
	 * @param thisBook Book selected to be added to cart
	 * @param itemNames List of the current selections
	 * @return Formatted list of all item added to cart (Names only)
	 */
	protected String addItemToUserShoppingList(MerchandiseBooks thisBook) {
		ShopItem newBoook = new ShopItem(thisBook.get_name(), Double.parseDouble(thisBook.get_price()));
		currentUser.add_itemToShoppingList(newBoook);
		
		String appendStr = "- ";
		String finalStr = null;

		for (ShopItem item:currentUser.get_shoppingList())
		{
			if (item != null)
			{
				finalStr += appendStr + item.get_name() + "\n";
			}
		}
		return finalStr;
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_item_display, menu);
        return true;
    }
}
