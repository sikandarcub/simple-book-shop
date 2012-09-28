package in.edureka.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import android.os.Environment;

public class CSVFileReader {
	
	String _fileName;
	ArrayList<String> _storeValues = new ArrayList<String>();
	
	public CSVFileReader(String fileName)
	{
		this._fileName = fileName;
	}
	
	public void ReadFile()
	{
		File directory = Environment.getExternalStorageDirectory();
		File file = new File(directory + "/" + this._fileName);
		
		if (!file.exists())
		{
			throw new RuntimeException("File not found");
		}
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			
			while((line = reader.readLine()) != null)
			{
				this._storeValues.add(line);
			}
			
			reader.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//mutators and accesors 
	public void set_fileName(String newFileName)
	{
		this._fileName=newFileName;
	}
	public String get_fileName()
	{
		return _fileName;
	}
	public ArrayList<String> getFileValues()
	{
		return this._storeValues;
	}

	public ArrayList<String> get_distinctColumnValues(int columnNum)
	{
		ArrayList<String> columnValues = new ArrayList<String>();
		String delimiter = "[,]+";
		
		for (String str : this._storeValues)
		{
			if (str != null)
			{
				String[] tokens = str.split(delimiter);
				columnValues.add(tokens[columnNum - 1]);				
			}
		}
		
		if (columnValues.size() > 0)
		{
			HashSet<String> hs = new HashSet<String>();
			hs.addAll(columnValues);
			columnValues.clear();
			columnValues.addAll(hs);
		}

		return columnValues;
	}
}