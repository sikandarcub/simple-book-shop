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
	
	Spinner spIdCategoryList;
	Spinner spIdItemList;
	TableLayout tlIdSelectedItemDetails;
	TextView tvIdBookAuthor;
	TextView tvIdBookPrice;
	ImageView ivIdBookCover;
	Button btnIdItemAdd;
	TextView tvIdLinkToCartDisplay;
	
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
			case R.id.spIdCategoryList:
				strSelectedCategory = strCategories[position];
				refreshItemList(strSelectedCategory);
		        break;
			case R.id.spIdItemList:
				strSelectedItem = strItemsInCategory[position];
				displayItemInformation(strSelectedItem, strSelectedCategory);
				Log.w(TAG, "Got: " + bookSelected.get_authorName() + " " + bookSelected.get_price());
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
			case R.id.btnIdItemAdd:
				String itemNames = null;
				String displayMessage = null;
				
				if(bookSelected != null) {
					itemNames = addItemToUserShoppingList(bookSelected);
				}
				
				if (itemNames.isEmpty() == true)	{
					displayMessage = "Error: Could not add item to shopping cart";
				}
				else {
					displayMessage = "Item added to Cart.\nContents of your cart:\n" + itemNames;
				}
				Toast.makeText(getApplicationContext(), 
						displayMessage, 
						Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.tvIdLinkToCartDisplay:
				Intent i=new Intent(getApplicationContext(), CartDisplayActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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
        
        spIdCategoryList = (Spinner)findViewById(R.id.spIdCategoryList);
        spIdItemList = (Spinner)findViewById(R.id.spIdItemList);
        tlIdSelectedItemDetails = (TableLayout)findViewById(R.id.tlIdSelectedItemDetails);
        tvIdBookAuthor = (TextView)findViewById(R.id.tvIdBookAuthor);
        tvIdBookPrice = (TextView)findViewById(R.id.tvIdBookPrice);
        ivIdBookCover = (ImageView)findViewById(R.id.ivIdBookCover);
        btnIdItemAdd = (Button)findViewById(R.id.btnIdItemAdd);
        tvIdLinkToCartDisplay = (TextView)findViewById(R.id.tvIdLinkToCartDisplay);
        
        spIdCategoryList.setOnItemSelectedListener(myOnItemSelectedListener);
        spIdItemList.setOnItemSelectedListener(myOnItemSelectedListener);
        
        btnIdItemAdd.setOnClickListener(myOnClickListener );
        tvIdLinkToCartDisplay.setOnClickListener(myOnClickListener);

        initializeCategoryList();
    }


	/**
	 * Derives the category list and updates categoryList spinner
	 */
	private void initializeCategoryList() {
        float taxPercentage = (float) 6.5;
        String fileDB = new String("fileDB.csv"); 
        bookStore = new StoreForBooks(taxPercentage, fileDB);
        
        currentUser.set_taxPercent(bookStore.get_taxPercentage());
        if (bookStore.get_totalNumOfItemInStore() < 0 )
        {
        	Toast.makeText(getApplicationContext(), "No Items in Store", Toast.LENGTH_SHORT).show();
        }
        
        bookSelected = new MerchandiseBooks();
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
        
        spIdCategoryList.setAdapter(aaCategories);
        
		Toast.makeText(getApplicationContext(), 
				"Debug: " + currentUser.get_fullName() + " " + currentUser.get_userName(), 
				Toast.LENGTH_SHORT).show();		
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
		
		spIdItemList.setAdapter(aaItems);
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
			tvIdBookAuthor.setText(thisBook.get_authorName());
			tvIdBookPrice.setText(thisBook.get_price());
		}
		
		bookSelected.set_name(thisBook.get_name());
		bookSelected.set_price(thisBook.get_price());
		bookSelected.set_authorName(thisBook.get_authorName());
	}

	/**
	 * Adds the selected Book to the Shopping list of the user
	 * @param thisBook Book selected to be added to cart
	 * @return Formatted list of all item added to cart (Names only)
	 */
	protected String addItemToUserShoppingList(MerchandiseBooks thisBook) {
		ShopItem newBoook = new ShopItem(
				thisBook.get_name(), Double.parseDouble(thisBook.get_price()));
		currentUser.add_itemToShoppingList(newBoook);
		return currentUser.get_itemNames();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_item_display, menu);
        return true;
    }
}
