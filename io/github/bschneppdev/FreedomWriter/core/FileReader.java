package io.github.bschneppdev.FreedomWriter.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader
{
	public String read(File file) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(file);
		String str = "";
		while (scanner.hasNextLine())
		{
			str += scanner.nextLine() + '\n';
		}
		scanner.close();
		return str;
	}
}
