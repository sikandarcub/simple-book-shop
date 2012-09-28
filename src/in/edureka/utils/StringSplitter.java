package in.edureka.utils;

public class StringSplitter {
	
	public static final String[] split(String strToSplit)
	{
		String delimiter = ",";
		return strToSplit.split(delimiter);
	}
}
