package in.edureka.helpers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import in.edureka.transport.ShopItem;
import in.edureka.transport.ShopUser;
import in.edureka.utils.BasicFileAccessor;
import in.edureka.utils.BasicFileWriter;

public class FinalTransaction {
	private ShopUser _user;
	private String _transactionLog;
	private Date _transactionDate;
	private BasicFileWriter _writer;

	public FinalTransaction() {
		this._user = null;
		this._transactionLog = null;
		this._transactionDate = new Date();
		this._writer = null;
	}
	
	private String buildXml(boolean writeHeader) {
	    XmlSerializer serializer = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
	    try {
	        serializer.setOutput(writer);
	        if (writeHeader == true) {
	        	serializer.startDocument("UTF-8", true);
	        }
	        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
	        serializer.startTag(null, "purchase");
	        serializer.attribute(null, "userlogin", this._user.get_userName());
	        serializer.attribute(null, "datetime", this.get_transactionDate().toString());
	        serializer.startTag(null, "itemlist");
	        serializer.attribute(null, "count", String.valueOf(this._user.get_shoppingList().size()));
	        for (ShopItem item: this._user.get_shoppingList()) {
	            serializer.startTag(null, "item");
	            serializer.startTag(null, "name");
	            serializer.text(item.get_name());
	            serializer.endTag(null, "name");
	            serializer.startTag(null, "price");
	            serializer.text(String.valueOf(item.get_price()));
	            serializer.endTag(null, "price");
	            serializer.startTag(null, "quantity");
	            serializer.text(String.valueOf(item.get_quantity()));
	            serializer.endTag(null, "quantity");
	            serializer.endTag(null, "item");
	        }
	        serializer.endTag(null, "itemlist");
	        serializer.endTag(null, "purchase");
	        serializer.endDocument();
	        return writer.toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    } 
	}
	
	public void commitDetails() {
		boolean appendToFile = false;
		String xmlValues = null;
		boolean buildHeaderFlag = true;
		
		if (BasicFileAccessor.doesFileExist(_transactionLog) == true)
			appendToFile = true;
		
		if (appendToFile == true)
			buildHeaderFlag = false;
		
		xmlValues = buildXml(buildHeaderFlag);
		
		this._writer = new BasicFileWriter(this._transactionLog, appendToFile);
		
		try {
			_writer.writeFile(xmlValues);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public ShopUser get_user() {
		return _user;
	}

	public void set_user(ShopUser user) {
		this._user = user;
	}

	public String get_transactionLog() {
		return _transactionLog;
	}

	public void set_transactionLog(String _transactionLog) {
		this._transactionLog = _transactionLog;
	}
	public Date get_transactionDate() {
		return _transactionDate;
	}
	public void set_transactionDate(Date _transactionDate) {
		this._transactionDate = _transactionDate;
	}
}
