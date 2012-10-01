package in.edureka.bookshopping;

import java.math.RoundingMode;
import java.text.NumberFormat;
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

	Button btnScProceedToCheckout;
	Button btnScLinkToAddItems;
	TextView tvScIsEmpty;
	TextView tvScTaxPercent;
	TextView tvScGrandTotal;
	ListView lvScUserItemList;
	LinearLayout llScShoppingList;
	
	private ShopUser currentUser;
	List<ShoppingCartItem> alList;
	
	private OnClickListener myOnClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnScProceedToCheckout:
				// Proceed to Checkout
				Intent psIntent=new Intent(getApplicationContext(), PurchaseSummaryActivity.class);
				psIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				psIntent.putExtra("in.edureka.transport.ShopUser", currentUser);
				startActivity(psIntent);			
				break;
			case R.id.btnScLinkToAddItems:
				Intent idIntent=new Intent(getApplicationContext(), ItemDisplayActivity.class);
				idIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				idIntent.putExtra("in.edureka.transport.ShopUser", currentUser);
				startActivity(idIntent);

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
        
        btnScProceedToCheckout = (Button)findViewById(R.id.btnScProceedToCheckout);
        btnScLinkToAddItems = (Button)findViewById(R.id.btnScLinkToAddItems);
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
        	initializeListView();
        	tvScTaxPercent.setText(Float.toString(currentUser.get_taxPercent()));
        	updateTotalCost();
        }
        btnScProceedToCheckout.setOnClickListener(myOnClickListener);
        btnScLinkToAddItems.setOnClickListener(myOnClickListener);
	}
    
    private void handleEmptyShoppingCart() {
    	tvScIsEmpty.setVisibility(View.VISIBLE);
    	llScShoppingList.setVisibility(View.GONE);
    	btnScProceedToCheckout.setVisibility(View.GONE);
    }

	private void initializeListView() {
		alList = new ArrayList<ShoppingCartItem>();
		ArrayAdapter<ShoppingCartItem> aaAdapter = new MyShoppingCartItemAdapter(this,getShoppingCartItemList());
		lvScUserItemList.setAdapter(aaAdapter);
		lvScUserItemList.setOnItemClickListener(this);
		lvScUserItemList.setOnItemLongClickListener(this);	
	}

	private List<ShoppingCartItem> getShoppingCartItemList() {
		for (ShopItem item:currentUser.get_shoppingList()) {
			if(item != null) {
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
		syncShoppingList();

		if (aaAdapter.isEmpty() == true) {
			handleEmptyShoppingCart();
		}
		else {
			updateTotalCost();
		}
		return false;
	}

	private void updateTotalCost() {
		double totalCost = currentUser.get_totalCostInclusiveTax();
		NumberFormat fmt = NumberFormat.getNumberInstance();
		fmt.setMaximumFractionDigits(2);
		fmt.setRoundingMode(RoundingMode.CEILING);
		String strTotalCost = fmt.format(totalCost);
		tvScGrandTotal.setText(strTotalCost);		
	}
}

