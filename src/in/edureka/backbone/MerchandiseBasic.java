package in.edureka.backbone;

import in.edureka.utils.StringSplitter;

public class MerchandiseBasic{
	private String _name;
	private String _price;
	private String _itemCategory;
	private String _image;
	
	protected final static int _posCategory = 0;
	protected final static int _posName = _posCategory + 1;
	protected final static int _posPrice = _posName + 1;
	protected final static int _posImage = _posPrice + 1;
	protected final static int _lastColumn = _posImage;

	public MerchandiseBasic() {
		this(null,null,null,null);
	}
	
	public MerchandiseBasic(String name, String price, String category, String image) {
		this._name = name;
		this._price = price;
		this._itemCategory = category;
		this._image = image;
	}

	public MerchandiseBasic(MerchandiseBasic basicItem) {
		this(basicItem.get_name(),
				basicItem.get_price(),
				basicItem.get_itemCategory(),
				basicItem.get_image());
	}
	
	public MerchandiseBasic(String name, String category) {
		this(name,null,category,null);
	}
	
	@Override 
	public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof MerchandiseBasic) {
        	MerchandiseBasic that = (MerchandiseBasic) other;
            result = (this.get_name() == that.get_name() &&
            		this.get_itemCategory() == that.get_itemCategory());
        }
        return result;
    }
	
	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_price() {
		return _price;
	}

	public void set_price(String price) {
		this._price = price;
	}

	public String get_itemCategory() {
		return _itemCategory;
	}

	public void set_itemCategory(String category) {
		this._itemCategory = category;
	}

	public String get_image() {
		return _image;
	}

	public void set_image(String _image) {
		this._image = _image;
	}
	
	public static MerchandiseBasic getObj(String str) {
		
		MerchandiseBasic tempObj = new MerchandiseBasic();
        
        String[] temp = StringSplitter.split(str);

		tempObj.set_itemCategory(temp[_posCategory]);
		tempObj.set_name(temp[_posName]);
		tempObj.set_price(temp[_posPrice]);
		tempObj.set_image(temp[_posImage]);
		
		return tempObj;
	}
}
