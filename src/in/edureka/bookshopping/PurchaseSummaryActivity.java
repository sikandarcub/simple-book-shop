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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PurchaseSummaryActivity extends Activity {

	TextView tvPsUserFullName;
	TextView tvPsUserEmailId;
	Button btnPsRestart;
	ListView lvPsUserItemList;
	TextView tvPsSubTotal;
	TextView tvPsTax;
	TextView tvPsGrandTotal;
	
	private ShopUser currentUser;
	List<ShoppingCartItem> alList;
	private OnClickListener myOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			currentUser.cleanup_shoppingList();
			Intent i=new Intent(getApplicationContext(), ItemDisplayActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			i.putExtra("in.edureka.transport.ShopUser", currentUser);
			startActivity(i);
		}
		
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        currentUser =
			b.getParcelable("in.edureka.transport.ShopUser");
        
        setContentView(R.layout.activity_purchase_summary);
        
        tvPsUserFullName = (TextView)findViewById(R.id.tvPsUserFullName);
        tvPsUserEmailId = (TextView)findViewById(R.id.tvPsUserEmailId);
        btnPsRestart = (Button)findViewById(R.id.btnPsRestart);
        lvPsUserItemList = (ListView)findViewById(R.id.lvPsUserItemList);
        tvPsSubTotal = (TextView)findViewById(R.id.tvPsSubTotal);
        tvPsTax = (TextView)findViewById(R.id.tvPsTax);
        tvPsGrandTotal = (TextView)findViewById(R.id.tvPsGrandTotal);
        
        setupScreenViews();
    }

    private void setupScreenViews() {
    	if (currentUser.get_shoppingList().isEmpty() == true) {
    		Toast.makeText(getApplicationContext(), 
    				"Possible error.\nPlease contact suppport.", 
    				Toast.LENGTH_SHORT).show();
    	}
    	else {
    		initializeListView();
    		initializeTotals();
    		tvPsUserFullName.setText(currentUser.get_fullName());
    		tvPsUserEmailId.setText(currentUser.get_userName());
    		btnPsRestart.setOnClickListener(myOnClickListener );
    	}
	}

	private void initializeTotals() {
		double subTotal = currentUser.get_totalCostExcludingTax();
		double taxCalculated = currentUser.get_totalTax();
		double grandTotal = subTotal + taxCalculated;

		NumberFormat fmt = NumberFormat.getNumberInstance();
		fmt.setMaximumFractionDigits(2);
		fmt.setRoundingMode(RoundingMode.CEILING);
		String strSubTotal = fmt.format(subTotal);
		String strTaxCalculated = fmt.format(taxCalculated);
		String strGrandTotal = fmt.format(grandTotal);
		
		tvPsSubTotal.setText(strSubTotal);
		tvPsTax.setText(strTaxCalculated);
		tvPsGrandTotal.setText(strGrandTotal);
	}

	private void initializeListView() {
		alList = new ArrayList<ShoppingCartItem>();
		ArrayAdapter<ShoppingCartItem> aaAdapter = new MyShoppingCartItemAdapter(this,getShoppingCartItemList());
		lvPsUserItemList.setAdapter(aaAdapter);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_purchase_summary, menu);
        return true;
    }
}
