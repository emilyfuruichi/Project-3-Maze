package maze;

import java.util.Random;

public class MazeTester 
{
	private static final int[] GRID_SIZE = {4,5,6,7,8,10};
	
	public static void main(String args[])
	{
		Maze maze;
		for(int i : GRID_SIZE)
		{
			System.out.println("Printing maze ("+i+"x"+i+"):");
			maze = new Maze(i);
		}
	//change
	}
}
