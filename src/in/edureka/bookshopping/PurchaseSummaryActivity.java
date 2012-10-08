package in.edureka.bookshopping;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import in.edureka.helpers.FinalTransaction;
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
	
	private FinalTransaction thisPurchase;
	List<ShoppingCartItem> alList;
	private OnClickListener myOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			thisPurchase.get_user().cleanup_shoppingList();
			Intent i=new Intent(getApplicationContext(), ItemDisplayActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			i.putExtra("in.edureka.transport.ShopUser", thisPurchase.get_user());
			startActivity(i);
		}
		
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisPurchase = new FinalTransaction();

        Bundle b = getIntent().getExtras();
        thisPurchase.set_user(
			(ShopUser) b.getParcelable("in.edureka.transport.ShopUser"));
        
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
    	if (thisPurchase.get_user().get_shoppingList().isEmpty() == true) {
    		Toast.makeText(getApplicationContext(), 
    				"Possible error.\nPlease contact suppport.", 
    				Toast.LENGTH_SHORT).show();
    	}
    	else {
    		initializeListView();
    		initializeTotals();
    		tvPsUserFullName.setText(thisPurchase.get_user().get_fullName());
    		tvPsUserEmailId.setText(thisPurchase.get_user().get_userName());
    		btnPsRestart.setOnClickListener(myOnClickListener );
    		
    		thisPurchase.set_transactionLog("BookShop/logs/purchases.xml");
    		thisPurchase.commitDetails();
    	}
	}

	private void initializeTotals() {
		double subTotal = thisPurchase.get_user().get_totalCostExcludingTax();
		double taxCalculated = thisPurchase.get_user().get_totalTax();
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
		for (ShopItem item:thisPurchase.get_user().get_shoppingList()) {
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
