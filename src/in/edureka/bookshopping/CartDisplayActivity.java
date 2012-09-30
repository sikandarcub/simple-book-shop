package in.edureka.bookshopping;

import java.util.ArrayList;
import java.util.List;

import in.edureka.helpers.MyShoppingCartItemAdapter;
import in.edureka.helpers.ShoppingCartItem;
import in.edureka.transport.ShopItem;
import in.edureka.transport.ShopUser;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CartDisplayActivity extends Activity implements OnItemClickListener, OnItemLongClickListener {

	Button btnScCheckoutOrExit;
	TextView tvScLinkToAddItems;
	TextView tvScIsEmpty;
	TextView tvScTaxPercent;
	TextView tvScGrandTotal;
	ListView lvScUserItemList;
	LinearLayout llScShoppingList;
	Boolean isScEmpty = true;
	
	ShopUser currentUser;
	List<ShoppingCartItem> alList;
	
	private OnClickListener myOnClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnScProceedToCheckoutOrExit:
				if (isScEmpty == true) {
					// Quit Application	
					Toast.makeText(getApplicationContext(), "Exiting", Toast.LENGTH_SHORT).show();
					CartDisplayActivity.this.finish();
				}
				else {
					// Proceed to Checkout
					Intent i=new Intent(getApplicationContext(), PurchaseSummaryActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					i.putExtra("in.edureka.transport.ShopUser", currentUser);
					startActivity(i);					
				}
				break;
			case R.id.tvScLinkToAddItems:
				Intent i=new Intent(getApplicationContext(), ItemDisplayActivity.class);
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
        
        setContentView(R.layout.activity_cart_display);
        
        btnScCheckoutOrExit = (Button)findViewById(R.id.btnScProceedToCheckoutOrExit);
        tvScLinkToAddItems = (TextView)findViewById(R.id.tvScLinkToAddItems);
        tvScIsEmpty = (TextView)findViewById(R.id.tvScIsEmpty);
        tvScTaxPercent = (TextView)findViewById(R.id.tvScTaxPercent);
        tvScGrandTotal = (TextView)findViewById(R.id.tvScGrandTotal);
        llScShoppingList = (LinearLayout)findViewById(R.id.llScShoppingList);
        lvScUserItemList = (ListView)findViewById(R.id.lvScUserItemList);
        
        setupScreenViews();
    }

	protected void syncShoppingList() {
		currentUser.cleanup_shoppingList();
		for(ShoppingCartItem item: alList) {
			if (item != null) {
				currentUser.add_itemToShoppingList((ShopItem)item);
			}
		}
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_cart_display, menu);
        return true;
    }

    private void setupScreenViews() {

        if (currentUser.get_shoppingList().isEmpty() == true) {
        	handleEmptyShoppingCart();
        }
        else {
        	tvScIsEmpty.setVisibility(View.GONE);
        	llScShoppingList.setVisibility(View.VISIBLE);
        	isScEmpty = false;
        	initializeListView();
        	tvScTaxPercent.setText(Float.toString(currentUser.get_taxPercent()));
        	tvScGrandTotal.setText(Double.toString(currentUser.get_totalCostInclusiveTax()));
        }
        btnScCheckoutOrExit.setOnClickListener(myOnClickListener );
        tvScLinkToAddItems.setOnClickListener(myOnClickListener);
	}
    
    private void handleEmptyShoppingCart() {
    	tvScIsEmpty.setVisibility(View.VISIBLE);
    	llScShoppingList.setVisibility(View.GONE);
    	isScEmpty = true;
    	btnScCheckoutOrExit.setText(R.string.str_exit_app);
    }

	private void initializeListView() {
		alList = new ArrayList<ShoppingCartItem>();
		ArrayAdapter<ShoppingCartItem> aaAdapter = new MyShoppingCartItemAdapter(this,getShoppingCartItemList());
		lvScUserItemList.setAdapter(aaAdapter);
		lvScUserItemList.setOnItemClickListener(this);
		lvScUserItemList.setOnItemLongClickListener(this);	
	}

	private List<ShoppingCartItem> getShoppingCartItemList() {
		for (ShopItem item:currentUser.get_shoppingList())
		{
			if(item != null)
			{
				ShoppingCartItem newScItem = new ShoppingCartItem(item.get_name(), item.get_price());
				newScItem.set_quantity(item.get_quantity());
				newScItem.set_selected(false);
				alList.add(newScItem);
			}
		}
		
		return alList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		TextView tvScItemName = (TextView) v.getTag(R.id.tvScItemName);
		TextView tvScItemPrice = (TextView) v.getTag(R.id.tvScItemPrice);
		
		Toast.makeText(v.getContext(), tvScItemName.getText().toString()+" "+
				tvScItemPrice.getText().toString(), Toast.LENGTH_LONG).show();
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View v, int position,
			long id) {
		ArrayAdapter<ShoppingCartItem> aaAdapter = 
				(MyShoppingCartItemAdapter) lvScUserItemList.getAdapter();
		((MyShoppingCartItemAdapter) aaAdapter).removeItemAt(position);
		aaAdapter.notifyDataSetChanged();
		
		if (aaAdapter.isEmpty() == true) {
			handleEmptyShoppingCart();
		}
		else {
			syncShoppingList();
			tvScGrandTotal.setText(Double.toString(currentUser.get_totalCostInclusiveTax()));
		}
		return false;
	}
}

