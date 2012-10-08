package in.edureka.backbone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import in.edureka.utils.BasicFileReader;

/**
 * Class handles getting the data from various sources for building a store
 * @author Krishna Koneru
 * @see BasicFileReader, MerchandiseBasic
 *
 */
public class StoreBuilder {
	private String _fileName;
	private ArrayList<String> _fileValues;
	private ArrayList<String> _categoryList;
	
	public StoreBuilder(String fileName){
		this._fileName = fileName;
		this._fileValues = new ArrayList<String>();
		this._categoryList = new ArrayList<String>();
		this.set_fileValues();
	}
	
	public ArrayList<String> get_itemList()
	{
		return this._fileValues;
	}

	private void set_fileValues()
	{
		BasicFileReader reader = new BasicFileReader(this._fileName);
		reader.ReadFile();
		this._fileValues = reader.getFileValues();
	}
	
	public ArrayList<String> get_categoryList()
	{
		return this._categoryList;
	}

    public <M extends MerchandiseBasic> void set_categoryList(ArrayList<M> itemList)
    {
    	ArrayList<String> tempList = new ArrayList<String>();
    	for (M item: itemList)
    	{
    		if (item != null)
    		{
    			tempList.add(item.get_itemCategory());
    		}
    	}
    	
		if (tempList.size() > 0)
		{
			HashSet<String> hs = new HashSet<String>();
			hs.addAll(tempList);
			tempList.clear();
			tempList.addAll(hs);
		}
		
		for (String str : tempList)
		{
			if (str != null)
			{
				this._categoryList.add(str);
			}
		}
		Collections.sort(this._categoryList);
    }
}
