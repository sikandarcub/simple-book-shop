package in.edureka.helpers;

import in.edureka.bookshopping.R;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * This class is used to populate the Shopping cart ListView.
 * @author Krishna Koneru & lalit3686.blogspot.in
 *
 */
public class MyShoppingCartItemAdapter extends ArrayAdapter<ShoppingCartItem> {
	private final List<ShoppingCartItem> _list;
	private final Activity _context;
	boolean _checkAll_flag = false;
	boolean _checkItem_flag = false;

	
	public MyShoppingCartItemAdapter(Activity context, List<ShoppingCartItem> list) {
        super(context, R.layout.row_sci, list);
        this._context = context;
        this._list = list;
    }

    static class ViewHolder {
        protected TextView tvScItemName;
        protected TextView tvScItemPrice;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder viewHolder = null;
    	 if (convertView == null) {
    		 LayoutInflater inflator = _context.getLayoutInflater();
    		 convertView = inflator.inflate(R.layout.row_sci, null);
    		 viewHolder = new ViewHolder();
    		 viewHolder.tvScItemName=(TextView) convertView.findViewById(R.id.tvScItemName);
    		 viewHolder.tvScItemPrice=(TextView) convertView.findViewById(R.id.tvScItemPrice);
    		 convertView.setTag(viewHolder);
    		 convertView.setTag(R.id.tvScItemName, viewHolder.tvScItemName);
    		 convertView.setTag(R.id.tvScItemPrice, viewHolder.tvScItemPrice);
    	 }
    	 else {
    		 viewHolder = (ViewHolder) convertView.getTag();
    	 }
         
         viewHolder.tvScItemName.setText(_list.get(position).get_name());
         viewHolder.tvScItemPrice.setText(String.valueOf(_list.get(position).get_price()));
    	 
    	 return convertView;
    }

    public void removeItemAt(int position) {
    	this._list.remove(position);
    }
}
