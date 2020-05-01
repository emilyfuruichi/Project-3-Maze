/**
 * FileHandling class can write Strings to text files.
 * It is used to save visual representations of solutions for Mazes.
 */

package maze;

import java.io.File;
import java.io.PrintWriter;

public class FileHandling 
{
	private int size; //dimension of the maze
	
	/**
	 * Constructor initializes the size (dimension) of the maze
	 * @param size the size to use
	 */
	public FileHandling(int size)
	{
		this.size = size;
	}
	
	/**
	 * Creates a txt file populated with the given Strings placing each string on a new line
	 * Files are named "Results" followed by the dimensions of the maze
	 * @param dfs the depth first search solution illustrated in ASCII
	 * @param dfsSummary a written description of the DFS solution
	 * @param bfs the breadth first search solution illustrated in ASCII
	 * @param bfsSummary a written description of the BFS solution
	 * @param shortestGrid the shortest path solution illustrated in ASCII
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
