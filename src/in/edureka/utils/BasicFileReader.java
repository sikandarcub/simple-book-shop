package in.edureka.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import android.os.Environment;

public class BasicFileReader extends BasicFileAccessor{
	

	ArrayList<String> _values = new ArrayList<String>();
	
	public BasicFileReader(String fileName)	{
		super(fileName);
	}
	
	public void ReadFile()	{
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
				this._values.add(line);
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
		return this._values;
	}
}