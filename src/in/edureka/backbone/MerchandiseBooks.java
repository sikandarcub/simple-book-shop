package in.edureka.backbone;

import in.edureka.utils.StringSplitter;


public class MerchandiseBooks extends MerchandiseBasic {
	private String _authorName;
	private final static int _posAuthor = _lastColumn + 1;

	public MerchandiseBooks() {
		super();
		this._authorName = null;
	}

	public MerchandiseBooks(String name, String price, 
			String categoryName, String authorName) {
		super(name, price, categoryName);
		this._authorName = authorName;
	}
	
	public MerchandiseBooks(String name, String categoryName) {
		super(name, categoryName);
		// TODO Auto-generated constructor stub
	}

	public String get_authorName() {
		return _authorName;
	}

	public void set_authorName(String _authorName) {
		this._authorName = _authorName;
	}

	@Override 
	public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof MerchandiseBooks) {
        	MerchandiseBooks that = (MerchandiseBooks) other;
            result = (this.get_name() == that.get_name() &&
            		this.get_itemCategory() == that.get_itemCategory());
        }
        return result;
    }
	
	/**
	 * Breaks up the Provided string to give a Book object
	 * @param str
	 * @return null for invalid details, MerchandiseBooks for valid details
	 */

	public static MerchandiseBooks getObj(String str) {
		
		MerchandiseBooks tempObj = null;
        
        String[] temp = StringSplitter.split(str);
        
        if ((temp.length - 1) == _posAuthor)
        {
        	tempObj = new MerchandiseBooks();
    		tempObj.set_itemCategory(temp[_posCategory]);
    		tempObj.set_name(temp[_posName]);
    		tempObj.set_price(temp[_posPrice]);
    		tempObj.set_authorName(temp[_posAuthor]);
        }

		return tempObj;
	}
}
