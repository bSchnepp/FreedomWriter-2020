package io.github.bschneppdev.FreedomWriter.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader
{
	public String read(File file) throws IOException
	{
		StringBuilder Builder = new StringBuilder();
		FileInputStream InStream = new FileInputStream(file);
		BufferedReader Reader = new BufferedReader(new InputStreamReader(InStream));
		
		String CurLine = "";
		while ((CurLine = Reader.readLine()) != null)
		{
			Builder.append(CurLine);
			Builder.append("\n");
		}
		
		InStream.close();
		Reader.close();
		return Builder.toString();
	}
}
