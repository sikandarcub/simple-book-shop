package in.edureka.bookshopping;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PurchaseSummaryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_summary);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_purchase_summary, menu);
        return true;
    }
}
