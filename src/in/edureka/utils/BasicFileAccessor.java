package in.edureka.utils;

import java.io.File;

import android.os.Environment;

public class BasicFileAccessor {

	String _fileName;
	
	public BasicFileAccessor(String fileName) {
		this._fileName = fileName;
	}

	public String get_fileName() {
		return _fileName;
	}

	public void set_fileName(String _fileName) {
		this._fileName = _fileName;
	}

	static public boolean doesFileExist(String fileName) {
		File file = new File(Environment.getExternalStorageDirectory(), fileName);
		boolean result = false;
		
		if (file.exists())
			result = true;
		
		return result;
	}
}
