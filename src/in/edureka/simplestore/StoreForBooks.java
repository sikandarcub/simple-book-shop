package in.edureka.simplestore;

import java.util.ArrayList;

public class StoreForBooks extends StoreBasic<MerchandiseBooks> {
	
	public StoreForBooks(float taxPercentage, String fileName) {
		super(taxPercentage, fileName);
	}
	
	public ArrayList<MerchandiseBooks> get_booksForAuthor(String authorName)
	{
		ArrayList<MerchandiseBooks> booksByAuthor = new ArrayList<MerchandiseBooks>();
		for (MerchandiseBooks thisBook : this.get_itemList())
		{
			if (thisBook.get_authorName().compareToIgnoreCase(authorName) == 0)
			{
				booksByAuthor.add(thisBook);
			}
		}
		return booksByAuthor;
	}

}