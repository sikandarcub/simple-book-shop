package in.edureka.bookshopping;

import java.util.ArrayList;

import in.edureka.simplestore.MerchandiseBooks;
import in.edureka.simplestore.StoreForBooks;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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
	TextView tvBookName;
	TextView tvBookAuthor;
	TextView tvBookPrice;
	
	StoreForBooks bookStore;
	MerchandiseBooks bookSelected;
	
	String[] strCategories;
	String[] strItemsInCategory;
	
	String strSelectedCategory;
	String strSelectedItem;

	private OnItemSelectedListener myOnItemSelectedListener = new OnItemSelectedListener () {

		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			switch (parent.getId()) {
			case R.id.spCategoryList:
				Toast.makeText(getApplicationContext(), 
						"Debug: Right stuff cat", 
						Toast.LENGTH_SHORT).show();	

				strSelectedCategory = strCategories[position];
				
				ArrayList<String> alItems = new ArrayList<String>();
				alItems = bookStore.get_itemsForCategorySimple(strSelectedCategory);
				
				strItemsInCategory = new String[alItems.size()];
				strItemsInCategory = alItems.toArray(strItemsInCategory);
				
				ArrayAdapter<String> aaItems = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,strItemsInCategory);
				spItemList.setAdapter(aaItems);
		        
		        break;
			case R.id.spItemList:
				Toast.makeText(getApplicationContext(), 
						"Debug: Right stuff item", 
						Toast.LENGTH_SHORT).show();
				
				strSelectedItem = strItemsInCategory[position];
				
				MerchandiseBooks thisBook = new MerchandiseBooks();
				
				thisBook = (MerchandiseBooks) bookStore.get_itemDetails(
						strSelectedItem,strSelectedCategory);
				
				if (thisBook != null)
				{
					Log.w(TAG, "Book: " + thisBook.get_name() + " " 
				                        + thisBook.get_authorName() + " " 
				                        + thisBook.get_price());
					
					tvBookName.setText(thisBook.get_name());
					tvBookAuthor.setText(thisBook.get_authorName());
					tvBookPrice.setText(thisBook.get_price());
					
				}

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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);
        
        spCategoryList = (Spinner)findViewById(R.id.spCategoryList);
        spItemList = (Spinner)findViewById(R.id.spItemList);
        tlSelectedItemDetails = (TableLayout)findViewById(R.id.tlSelectedItemDetails);
        tvBookName = (TextView)findViewById(R.id.tvBookName);
        tvBookAuthor = (TextView)findViewById(R.id.tvBookAuthor);
        tvBookPrice = (TextView)findViewById(R.id.tvBookPrice);
        
        spCategoryList.setOnItemSelectedListener(myOnItemSelectedListener);
        spItemList.setOnItemSelectedListener(myOnItemSelectedListener);

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

        ArrayAdapter<String> aaCategories = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,strCategories);
        spCategoryList.setAdapter(aaCategories);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_item_display, menu);
        return true;
    }
}
