package in.edureka.simplestore;

import java.util.ArrayList;
import java.util.HashSet;

import in.edureka.utils.CSVFileReader;

/**
 * Class handles getting the data from various sources for building a store
 * @author Krishna Koneru
 * @see CSVFileReader, MerchandiseBasic, MerchandiseBooks
 *
 */
public class StoreBuilder<M extends MerchandiseBasic> {
	private String _fileName;
	private ArrayList<M> _itemList;
	private ArrayList<String> _categoryList;
	
	public StoreBuilder(String fileName){
		this._fileName = fileName;
		this._itemList = new ArrayList<M>();
		this._categoryList = new ArrayList<String>();
		this.set_itemList();
		this.set_categoryList();
	}
	
	public ArrayList<M> get_itemList()
	{
		return this._itemList;
	}

	private void set_itemList()
	{
		CSVFileReader reader = new CSVFileReader(this._fileName);
		reader.ReadFile();
		for(String str: reader.getFileValues()) {
			if(str != null) {
				@SuppressWarnings("unchecked")
				M temp = (M) M.getObj(str);
				this._itemList.add(temp);
			}
		}

	}
	
	public ArrayList<String> get_categoryList()
	{
		return this._categoryList;
	}

    private void set_categoryList()
    {
    	for (M item: this._itemList)
    	{
    		if (item != null)
    		{
    			this._categoryList.add(item.get_itemCategory());
    		}
    	}
    	
		if (this._categoryList.size() > 0)
		{
			HashSet<String> hs = new HashSet<String>();
			hs.addAll(this._categoryList);
			this._categoryList.clear();
			this._categoryList.addAll(hs);
		}
    }
}
