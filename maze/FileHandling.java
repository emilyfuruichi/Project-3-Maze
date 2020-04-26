/**
 * FileHandling class can write txt files.
 */

package maze;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandling 
{
	
	/**
	 * Constructor initializes any necessary values
	 */
	public FileHandling()
	{
		
	}
	
	/**
	 * Creates a txt file populated with the given Strings
	 * placing each string on a new line
	 * @param name name to use for the created csv file
	 * @param array to write to the created file
	 */
	public void writeToFile(String name, String numberedGrid, String shortestGrid, String summary)
	{
		try(PrintWriter out = new PrintWriter(new File(name + ".txt"), "UTF-8"))
		{
			out.println(numberedGrid);
			out.println(shortestGrid);
			out.println(summary);
			out.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
