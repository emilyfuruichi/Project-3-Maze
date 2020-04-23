package maze;

public class MazeTester 
{
	private static final int[] GRID_SIZE = {2, 3, 4};
	
	public static void main(String args[])
	{
		Maze maze = new Maze();
		for(int i : GRID_SIZE)
		{
			maze.mazeGenerationDFS(i);
			maze.printMaze(i);
			System.out.println(maze.printGrid());
		}	
		
	}
}
