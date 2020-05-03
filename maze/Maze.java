/**
 * Project 3: Maze creates a random square maze in any given size.
 * It can then solve that maze using depth first search and breadth first search
 * and write its results to a file.
 */

package maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Random;


public class Maze 
{
	private ArrayList<LinkedList<Cell>> adjList; //adjacency list for the maze
	private int dimension = 1; //dimension of the maze set by constructor or default of 1
	private int numOfCells; //the total number of cells in the maze (size^2)
	private Stack<Integer> solutionPath; //the shortest solution path
	private Map<Integer, Cell> dfsSolution, bfsSolution; //the bfs and dfs solutions respectively
	
	/**
	 * Creates the adjacency list for storing walls that get broken down
	 * an the arraylist of linked lists represents a list of cells with each
	 * inner list representing all the open walls available in that cell to
	 * other cells. The front of each list contains the cell at that position
	 * for easy access to its attributes.
	 * @param size is the number of cells across and down the maze
	 */
	public Maze(int size)
	{
		adjList = new ArrayList<LinkedList<Cell>>();
		if(size>1)
			dimension = size;
		numOfCells = (int) Math.pow(dimension, 2);
		
		createAllWalledMaze();
		mazeGenerationDFS();		
		dfsSolution = solveMazeDFS();
		bfsSolution = solveMazeBFS();
		solutionPath = new Stack<Integer>();
		setSolutionPath();	
		
		writeResultsToFile(dfsSolution, bfsSolution, solutionPath);
	}
	
	/**
	 * Get the adjacency list for analysis
	 * @return current adjacency list
	 */
	public ArrayList<LinkedList<Cell>> getAdjacencyList() 
	{ 
		return adjList; 
	}
	
	/**
	 * Create a file for this maze containing the DFS and BFS solution with the shortest path, 
	 * coordinates of shortest path, cells visited in the shortest path, and total number 
	 * of visited cells for each solution as a text file
	 * @param dfsSolution depth first search solution
	 * @param bfsSolution breadth first search solution
	 * @param shortPath shortest path solution
	 */
	private void writeResultsToFile(Map<Integer, Cell> dfsSolution, Map<Integer, Cell> bfsSolution, Stack<Integer> shortPath)
	{
		FileHandling fh = new FileHandling(dimension);
		String dfsSummary = printSolutionSummary(dfsSolution,shortPath);
		String bfsSummary = printSolutionSummary(bfsSolution,shortPath);
		fh.writeToFile(printGrid(null,dfsSolution), dfsSummary, printGrid(null,bfsSolution), bfsSummary,printGrid("#",null));
	}
	
	/*
	 * Function: Places the cell Id of of the cells that make the shortest path from starting point to ending point
	 * PRE: Maze was solved using BFS or DFS
	 * POST: Stack containing cell Id with the starting cell Id at the top
	 * Return: None
	*/
	private void setSolutionPath() {
		int currentID = numOfCells - 1; //starting at ending cell
		while(currentID != -1) { //traverse the path backwards through the parents of each cell
			solutionPath.push(adjList.get(currentID).getFirst().getCellNum()); //push the ID of the current cell into stack
			currentID = adjList.get(currentID).getFirst().getParentNum(); //traverse to parent cell
		}
	}
	
	/**
	 * Gets the solution path
	 * @return solution path
	 */
	public Stack<Integer> getSolutionPath() 
	{ 
		return solutionPath; 
	}
	
	/**
	 * Solves the maze using depth first search (right, down, left, up)
	 * the solution is stored in a map with the order as the keys
	 * @return a map of all the cells visited in DFS and their order
	 */
	private Map<Integer,Cell> solveMazeDFS() 
	{
		Map<Integer,Cell> sol = new TreeMap<Integer,Cell>(); //the solution to build and return
		Cell currentCell = adjList.get(0).getFirst(); //cell currently being examined
		Stack<Cell> cellStack = new Stack<Cell>(); //stack of cells visited
		Cell nextCell = null; //neighbor cell to move to next
		int order = 0; //the visitation order, incremented as the solution is built
		
		cellStack.push(currentCell);
		sol.put(order++,currentCell);
		
		while(currentCell.getCellNum() != numOfCells-1) //while maze is not solved
		{
			//choose the next available valid neighbor cell and move in to it
			if(currentCell.getX() +1 < dimension //something exists to the right
					&& adjList.get(currentCell.getCellNum()+1).contains(currentCell) //they are connected
					&& !sol.containsValue(adjList.get(currentCell.getCellNum()+1).getFirst())) //not visited before
			{
				nextCell = adjList.get(currentCell.getCellNum()+1).getFirst();
				nextCell.setParentNum(currentCell.getCellNum());
				cellStack.push(nextCell);
				sol.put(order++,nextCell);
				currentCell = nextCell;
			}
			else if(currentCell.getY() +1 < dimension //something exists below
					&& adjList.get(currentCell.getCellNum()+dimension).contains(currentCell) //they are connected
					&& !sol.containsValue(adjList.get(currentCell.getCellNum()+dimension).getFirst())) //not visited before
			{
				nextCell = adjList.get(currentCell.getCellNum()+dimension).getFirst();
				nextCell.setParentNum(currentCell.getCellNum());
				cellStack.push(nextCell);
				sol.put(order++,nextCell);
				currentCell = nextCell;
			}
			else if(currentCell.getX() -1 >= 0 //something exists to the left
					&& adjList.get(currentCell.getCellNum()-1).contains(currentCell) //they are connected
					&& !sol.containsValue(adjList.get(currentCell.getCellNum()-1).getFirst())) //not visited before
			{
				nextCell = adjList.get(currentCell.getCellNum()-1).getFirst();
				nextCell.setParentNum(currentCell.getCellNum());
				cellStack.push(nextCell);
				sol.put(order++,nextCell);
				currentCell = nextCell;
			}
			else if(currentCell.getY() -1 >= 0 //something exists above
					&& adjList.get(currentCell.getCellNum()-dimension).contains(currentCell) //they are connected
					&& !sol.containsValue(adjList.get(currentCell.getCellNum()-dimension).getFirst())) //not visited before
			{
				nextCell = adjList.get(currentCell.getCellNum()-dimension).getFirst();
				nextCell.setParentNum(currentCell.getCellNum());
				cellStack.push(nextCell);
				sol.put(order++,nextCell);
				currentCell = nextCell;
			}
			else //no more new connected cells to visit
			{
				currentCell = cellStack.pop();
			}
		}
		return sol;
	}
	
	

	/**
	 * Solves the maze using breadth first search (right, down, left, up)
	 * @return a map of all the cells visited in BFS and their order
	 */
	public Map<Integer, Cell> solveMazeBFS() {
		Map<Integer,Cell> sol = new TreeMap<Integer,Cell>(); //solution building and to be returned
		Cell currentCell = adjList.get(0).getFirst(); //cell currently in
		Queue<Cell> cellQueue = new LinkedList<Cell>(); //queue of visited cells
		Cell nextCell = null; //neighbor cell to move to next
		int order = 0; //the visitation order, incremented as the solution is built
		
		cellQueue.add(currentCell);
		sol.put(order++,currentCell);
		
		while(currentCell.getCellNum() != numOfCells-1) //maze not solved
		{
			if(currentCell.getX() +1 < dimension //something exists to the right
					&& adjList.get(currentCell.getCellNum()+1).contains(currentCell) //they are connected
					&& !sol.containsValue(adjList.get(currentCell.getCellNum()+1).getFirst())) //not visited before
			{
				nextCell = adjList.get(currentCell.getCellNum()+1).getFirst();
				nextCell.setParentNum(currentCell.getCellNum());
				cellQueue.add(nextCell);
				sol.put(order++,nextCell);
			}
			if(currentCell.getY() +1 < dimension //something exists below
					&& adjList.get(currentCell.getCellNum()+dimension).contains(currentCell) //they are connected
					&& !sol.containsValue(adjList.get(currentCell.getCellNum()+dimension).getFirst())) //not visited before
			{
				nextCell = adjList.get(currentCell.getCellNum()+dimension).getFirst();
				nextCell.setParentNum(currentCell.getCellNum());
				cellQueue.add(nextCell);
				sol.put(order++,nextCell);
			}
			if(currentCell.getX() -1 >= 0 //something exists to the left
					&& adjList.get(currentCell.getCellNum()-1).contains(currentCell)
					&& !sol.containsValue(adjList.get(currentCell.getCellNum()-1).getFirst())) //not visited before
			{
				nextCell = adjList.get(currentCell.getCellNum()-1).getFirst();
				nextCell.setParentNum(currentCell.getCellNum());
				cellQueue.add(nextCell);
				sol.put(order++,nextCell);
			}
			if(currentCell.getY() -1 >= 0 //above
					&& adjList.get(currentCell.getCellNum()-dimension).contains(currentCell)
					&& !sol.containsValue(adjList.get(currentCell.getCellNum()-dimension).getFirst())) //not visited before
			{
				nextCell = adjList.get(currentCell.getCellNum()-dimension).getFirst();
				nextCell.setParentNum(currentCell.getCellNum());
				cellQueue.add(nextCell);
				sol.put(order++,nextCell);
			}
			
			cellQueue.poll();
			currentCell = cellQueue.peek();
		}
		
		return sol;
	}

	/**
	 * Fills the adjacency list with lists containing only information
	 * for the cell it represents only. Cells that get added represent
	 * cells that that initial cell links to.
	 */
	public void createAllWalledMaze()
	{
		//create initial maze
		for(int i = 0; i < Math.pow(dimension,2); i++)
		{
			LinkedList<Cell> newList = new LinkedList<Cell>();
			newList.add(new Cell(i,dimension));
			adjList.add(newList);
		}
	}
	
	/**
	 * Using the depth first search a perfect maze is generated 
	 * with all cells reachable and no unnecessary open walls 
	 * or cycles.
	 */
	public void mazeGenerationDFS()
	{
		Random rand = new Random(0); //random seed, necessary to create testable cases
		Cell currentCell = adjList.get((int)(rand.nextDouble() * numOfCells-1)).getFirst(); //cell currently in
		ArrayList<Cell> neighborCells; //list of valid neighbors with which walls can be broken down
		int neighborCount; //the number of valid neighbors with which walls can be broken down
		Stack<Cell> cellStack = new Stack<Cell>(); //stack of visited cells
		int visitedCells = 0; //current visitation number
		
		cellStack.push(currentCell);
		
		while(visitedCells < numOfCells-1)
		{
			//find all neighbors of currentCell with all walls intact
			neighborCells = new ArrayList<Cell>();
			neighborCount = 0;
			
			if(currentCell.getY() -1 >= 0) //cell possible above
			{
				if(adjList.get(currentCell.getCellNum()-dimension).size() <= 1) //check if cell is connected
				{
					neighborCells.add(adjList.get(currentCell.getCellNum()-dimension).getFirst());
					neighborCount++;
				}
			}
			if(currentCell.getX() +1 < dimension) //cell possible right
			{
				if(adjList.get(currentCell.getCellNum()+1).size() <= 1) //check if cell is connected
				{
					neighborCells.add(adjList.get(currentCell.getCellNum()+1).getFirst());
					neighborCount++;
				}
			}
			if(currentCell.getY() +1 < dimension) //cell possible below
			{
				if(adjList.get(currentCell.getCellNum()+dimension).size() <= 1) //check if cell is connected
				{
					neighborCells.add(adjList.get(currentCell.getCellNum()+dimension).getFirst());
					neighborCount++;
				}
			}
			if(currentCell.getX() -1 >= 0) //cell possible left
			{
				if(adjList.get(currentCell.getCellNum()-1).size() <= 1) //check if cell is connected
				{
					neighborCells.add(adjList.get(currentCell.getCellNum()-1).getFirst());
					neighborCount++;
				}
			}
			
			int random = (int)(rand.nextDouble() * neighborCount); //chose a random neighbor
			if(neighborCount > 0 && !adjList.get(currentCell.getCellNum()).contains(neighborCells.get(random)))
			{
				adjList.get(currentCell.getCellNum()).addLast(neighborCells.get(random));
				adjList.get(neighborCells.get(random).getCellNum()).addLast(currentCell);
				cellStack.push(currentCell);
				currentCell = neighborCells.get(random);
				visitedCells++;
			}
			else //no more possible openings for this cell, go back
			{
				currentCell = cellStack.pop();
			}
		}
		
	}
	
	/*
	 * Function: Interface method to retieve the initial view of the maze
	 * PRE: Maze generated
	 * Post: None
	 * Return: None
	*/
	public String getInitialMaze() {
		return printGrid(" ",null);
	}
	
	/*
	 * Function: Interface method to retrieve the maze and the nodes visited from the DFS
	 * PRE: Maze generated
	 * Post: None
	 * Return: None
	*/
	public String getDFSResult() {
		
		return printGrid(null,dfsSolution);
	}
	
	/*
	 * Function: Interface method to retrieve the maze and the nodes visited from the BFS
	 * PRE: Maze generated
	 * Post: None
	 * Return: None
	*/
	public String getBFSResult() {
		return printGrid(null,bfsSolution);
	}
	
	/*
	 * Function: Interface method to print the shortest path of the maze
	 * PRE: Maze generated
	 * Post: None
	 * Return: None
	*/
	public String getShortestPath() {
		return printGrid("#",null);
	}
	
	/*
	 * Print Grid
	 * for each cell in one row, check its north wall to determine whether if intact
	 * while on the same row, check its east wall to determine whether if intact
	 * iterate to next row
	 * repeat
	 * 
	 * Maze Symbols: 
	 *  '+' = corner
	 *  '-' = north/south wall
	 *  '|' = west/east wall
	 *  ' ' = cell/broken wall
	 *  
	 *  Function: Prints the initial view of the maze
	 *  PRE: Adjacency list initialized
	 *  POST: None
	 *  Return: String containing maze representation
	*/
	private String printGrid(String charInCell, Map<Integer,Cell> cellMap) {
		String grid = "";
		int numOfDirections = 2; //number of wall directions to check.
		int currentCellCount = 0;
		int solCounter = 0;
		
		for(int i=0; i<dimension; i++) { //traverse adjList

			for(int dir=1; dir<=numOfDirections; dir++) 
			{ 
				// 'dir' indicates which wall directions to print
				// 1 = north, 2 = east
				if(dir == 1) { //if looking at north walls
					grid += "+";
					for(int j=0; j<dimension; j++) { //i represents the current row, j represents current col, i*dimension + j = current cell
						//for each cell in this row, check if north neighbor exists 
						//to determine whether wall is intact
						if(adjList.get(i*dimension+j).get(0).getCellNum() == 0) {
							//at the entry point
							grid += " ";
						}else {
							int northX = adjList.get(i*dimension+j).get(0).getX();		//get the x coordinate of where the north neighbor would be if exist
							int northY = adjList.get(i*dimension+j).get(0).getY() - 1;	//get the y coordinate of where the north neighbor would be if exist
							if(hasNeighbor(northX, northY, adjList.get(i*dimension+j))) {
								//if neighbor exists, then there is a path; thus, broken wall
								grid += " ";
							}
							else //no neighbor = wall
								grid += "-";
						}
						grid += "+";
					}//end for
				}//end if
				
				if(dir == 2) { //if looking at east walls
					grid += "|";
					for(int j=0; j<dimension; j++) { //i represents the current row, j represents current col, i*dimension + j = current cell
						//for each cell in this row, check if east neighbor exists 
						//to determine whether wall is intact
						if(cellMap != null)
						{
							if(charInCell == null) //fill in solution order
							{
								if(cellMap.containsValue(adjList.get(currentCellCount).getFirst()))
								{
									for(Map.Entry<Integer,Cell> ent : cellMap.entrySet())
									{
										if(ent.getValue() == adjList.get(currentCellCount).getFirst())
										{
											grid += ent.getKey()%10;
										}
									}
								}
								else
								{
									grid += " ";
								}
							}
							else //fill in visited cells solution
							{
								if(cellMap.containsValue(adjList.get(currentCellCount).getFirst()))
								{
									grid += charInCell;
								}
								else
								{
									grid += " ";
								}
							}
						}
						else if(solutionPath != null) { //printing the shortest path
							if(solutionPath.contains(adjList.get(currentCellCount).getFirst().getCellNum())) {
								grid += charInCell;
							}
							else
								grid += " ";
						}
						else
							grid += " ";
						++currentCellCount;
						int eastX = adjList.get(i*dimension+j).get(0).getX()+1;		//get the x coordinate of where the east neighbor would be if exist
						int eastY = adjList.get(i*dimension+j).get(0).getY();		//get the y coordinate of where the east neighbor would be if exist
						if(hasNeighbor(eastX, eastY, adjList.get(i*dimension+j))) {
							//if neighbor exists, then there is a path; thus, broken wall
							grid += " ";
						}
						else
							grid += "|";
					}		
				}//end if
				grid += "\n";
			}//end for
			
		}//end for
		
		//print bottom row
		int count = dimension*2+1;
		for(int i=0; i<dimension*2+1; i++) {
			if(i == dimension*2-1)	//at the ending cell
				grid += " ";
			else if(i%2 == 0)	//at a corner
				grid += "+";
			else	//directly below a cell
				grid += "-";
		}	
		grid += "\n";
		return grid;
	}
	
	/*
	 * Function: Iterates through a linked list of Cells to determine if there exist a cell
	 * 			 that has the coordinates (x,y)
	 * PRE: None
	 * POST: None
	 * Return: if found, true
	 * 			else false
	*/
	public boolean hasNeighbor(int x, int y, LinkedList<Cell> list) {
		
		for( Cell v : list) { //traverse through each cell
			//if there is a cell with coord at (x,y) return true
			if(v.getX() == x && v.getY() == y)
				return true;
		}
		return false;
	}
	
	/**
	 * Creates a string with the shortest path coordinates, length of the shortest path,  
	 * and total number of visited cells for a given solution
	 * @param allVisitedMap all the visited cells
	 * @param shortestPath cellNums of the shortest path
	 * @return a text summary of the solution given
	 */
	public String printSolutionSummary(Map<Integer,Cell> allVisitedMap, Stack<Integer> shortestPath)
	{
		String sol = "";
		
		for(Integer num : shortestPath)
		{
			sol = "("+adjList.get(num).getFirst().getX()+","+adjList.get(num).getFirst().getY()+") "+sol;
			//sol = num + " " + sol;
		}
		
		sol = "Path: " + sol;
		sol += "\nLength of path: " + shortestPath.size();
		sol += "\nVisited cells: "+ allVisitedMap.size() + "\n";
		
		return sol;
	}
	
	/**
	 * for testing
	 * prints out the adjacency list to the console
	 */
	public void printAdjList()
	{
		System.out.println("Printing adjList ("+dimension+"x"+dimension+")");
		
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
	
	/**
	 * For testing
	 * prints out the order it occurs in the solution as the key
	 * followed by the [cell number] since the cell is the value
	 * followed by a space
	 * @param map the solution map
	 */
	public void printMap(Map<Integer,Cell> map)
	{
		System.out.print("print map: ");
		for(Map.Entry<Integer, Cell> m : map.entrySet())
		{
			System.out.print(""+m.getKey()+m.getValue()+" ");
		}
		System.out.println();
	}
}
