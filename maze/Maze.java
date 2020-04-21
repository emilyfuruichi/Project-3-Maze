package maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Maze 
{
	private ArrayList<LinkedList<Cell>> adjList;
	private Cell entry, exit;	
	
	/**
	 * creates the adjacency list for storing walls that get broken down
	 * an the arraylist of linked lists represents a list of cells with each
	 * inner list representing all the open walls available in that cell to
	 * other cells
	 * @param size is the number of cells across and down the maze
	 */
	public void createMaze(int size)
	{
		adjList = new ArrayList<LinkedList<Cell>>(); //adjacency list, the first node of each contains that cell's info
		//entry = new Cell(-1,0,-1); //0 is the entry point
		//exit = new Cell(-2,size-1,size); //-1 is the exit point
		
		//create initial maze
		for(int i = 0; i < Math.pow(size,2); i++)
		{
			LinkedList<Cell> newList = new LinkedList<Cell>();
			newList.add(new Cell(i,size));
			adjList.add(newList);
		}
		//adjList.get(0).addLast(entry);
		//adjList.get((int)Math.pow(size,2)-1).addLast(exit);
	}
	
	/**
	 * Using the depth first search pseudocode given on pg2 of assignment as a guide
	 * can prob be broken up more later
	 * @param size is the number of cells across and down the maze
	 */
	public void mazeGenerationDFS(int size)
	{
		createMaze(size);
		int totalCells = (int)Math.pow(size,2)-1;
		Cell currentCell = adjList.get((int)(Math.random() * ((int)Math.pow((size),2)-1))).get(0);
		ArrayList<Cell> neighborCells;
		int neighborCount;
		Stack<Cell> cellStack = new Stack<Cell>();
		int visitedCells = 0;
		
		cellStack.push(currentCell);
		
		while(visitedCells < totalCells)
		{
			//find all neighbors of currentCell with all walls intact
			neighborCells = new ArrayList<Cell>();
			neighborCount = 0;
			//System.out.print("currentCell " + currentCell);
			
			//if(adjList.get(currentCell.getCellNum()).size() <=1)
			if(currentCell.getY() -1 >= 0) //above
			{
				if(adjList.get(currentCell.getCellNum()-size).size() <= 1)
				{
					neighborCells.add(adjList.get(currentCell.getCellNum()-size).get(0));
					neighborCount++;
				}
			}
			if(currentCell.getX() +1 < size) //right
			{
				if(adjList.get(currentCell.getCellNum()+1).size() <= 1)
				{
					neighborCells.add(adjList.get(currentCell.getCellNum()+1).get(0));
					neighborCount++;
				}
			}
			if(currentCell.getY() +1 < size) //below
			{
				if(adjList.get(currentCell.getCellNum()+size).size() <= 1)
				{
					neighborCells.add(adjList.get(currentCell.getCellNum()+size).get(0));
					neighborCount++;
				}
			}
			if(currentCell.getX() -1 >= 0) //left
			{
				if(adjList.get(currentCell.getCellNum()-1).size() <= 1)
				{
					neighborCells.add(adjList.get(currentCell.getCellNum()-1).get(0));
					neighborCount++;
				}
			}
			
			int random = (int)(Math.random() * neighborCount); //chose a random neighbor
			if(neighborCount > 0 && !adjList.get(currentCell.getCellNum()).contains(neighborCells.get(random)))
			{
				adjList.get(currentCell.getCellNum()).addLast(neighborCells.get(random));
				adjList.get(neighborCells.get(random).getCellNum()).addLast(currentCell);
				cellStack.push(currentCell);
				//System.out.println("adding to the adjList and stack "+neighborCells.get(random));
				currentCell = neighborCells.get(random);
				visitedCells++;
			}
			else //if(!cellStack.isEmpty())
			{
				//System.out.println("popping " + cellStack.peek());
				currentCell = cellStack.pop();
			}
			//System.out.println("visited cells: "+visitedCells+"/"+totalCells);
		}
		
	}
	
	/**
	 * for testing
	 * prints out the adjacency list
	 * @param size
	 */
	public void printMaze(int size)
	{
		System.out.println("Printing adjList ("+size+"x"+size+")");
		
		for(LinkedList<Cell> v : adjList)
		{
			System.out.print(v.get(0).getCellNum() + ": ");
			
			for(Cell c : v)
			{
				System.out.print(c+"->");
			}
			System.out.println();
		}
		System.out.println();
	}
}
