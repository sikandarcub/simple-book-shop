package in.edureka.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.os.Environment;

public class BasicFileWriter extends BasicFileAccessor{

	private boolean _appendToFile = false;
	
	public BasicFileWriter(String fileName) {
		super(fileName);
	}

	public BasicFileWriter(String fileName, boolean appendValue) {
		super(fileName);
		this._appendToFile = appendValue;
	}
	
	public void writeFile(String values) throws IOException {
		File file = new File(Environment.getExternalStorageDirectory(), _fileName);
		
		// if file doesn't exists, then create it
		if (!file.exists()) {
			file.createNewFile();
			_appendToFile = false;
		}
		
		FileWriter writer = new FileWriter(file, _appendToFile);
		BufferedWriter bw = new BufferedWriter(writer);
		
		bw.write(values);
		bw.close();
	}
}
