package in.edureka.simplestore;

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
	
	public static MerchandiseBooks getObj(String str) {
		
		MerchandiseBooks tempObj = new MerchandiseBooks();
        
        String[] temp = StringSplitter.split(str);

		tempObj.set_itemCategory(temp[_posCategory]);
		tempObj.set_name(temp[_posName]);
		tempObj.set_price(temp[_posPrice]);
		tempObj.set_authorName(temp[_posAuthor]);
		
		return tempObj;
	}
}
