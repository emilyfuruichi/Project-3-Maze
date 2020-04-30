/**
 * FileHandling class can write txt files.
 */

package maze;

import java.io.File;
import java.io.PrintWriter;

public class FileHandling 
{
	private int size;
	/**
	 * Constructor initializes any necessary values
	 */
	public FileHandling(int size)
	{
		this.size = size;
	}
	
	/**
	 * Creates a txt file populated with the given Strings
	 * placing each string on a new line
	 * @param name name to use for the created csv file
	 * @param array to write to the created file
	 */
	public void writeToFile(String dfs, String dfsSummary, String bfs, String bfsSummary, String shortestGrid)
	{
		try(PrintWriter out = new PrintWriter(new File("Results"+size+"x"+size+".txt"), "UTF-8"))
		{
			out.println("DFS: \n"+dfs);
			out.println(shortestGrid);
			out.println(dfsSummary);
			out.println("BFS:\n"+bfs);
			out.println(shortestGrid);
			out.println(bfsSummary);
			out.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
