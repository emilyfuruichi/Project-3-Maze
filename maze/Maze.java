/**
 * Maze creates a random square maze in any given size.
 * It can then solve that maze using depth first search and breadth first search.
 */

package maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class Maze 
{
	private ArrayList<LinkedList<Cell>> adjList;
	private int dimension, numOfCells;
	private Stack<Integer> solutionPath;
	private Map<Cell,Integer> dfsSolution;
	
	/**
	 * Creates the adjacency list for storing walls that get broken down
	 * an the arraylist of linked lists represents a list of cells with each
	 * inner list representing all the open walls available in that cell to
	 * other cells
	 * @param size is the number of cells across and down the maze
	 */
	public Maze(int size)
	{
		adjList = new ArrayList<LinkedList<Cell>>(); //adjacency list, the first node of each contains that cell's info
		dimension = size;
		numOfCells = (int) Math.pow(size, 2);
//		Map<Cell,Integer> solution;
		
		createAllWalledMaze();
		mazeGenerationDFS();
//		printAdjList();
//		System.out.println("Maze: \n"+printGrid(" ",null, 0)); //will need to print to a file
//		solution = solveMazeDFS();
//		printMap(solution);
//		System.out.println("DFS: \n"+printGrid(null,solution, 0)); //will need to print to a file
//		solution = solveMazeBFS();
//		System.out.println(printGrid("#",solution, 0)); //will need to print to a file
//		System.out.println(printGrid("#",null, 0)); //will need to print to a file
//		System.out.println("BFS: \n"+printGrid(null,solution)); //will need to print to a file
//		System.out.println(printGrid("#",solution)); //will need to print to a file
		
		
		dfsSolution = solveMazeDFS();
		solutionPath = new Stack<Integer>();
		setSolutionPath();
		printInitialMaze();
		printDFS();
		printShortestPath();
		
		
//		System.out.println(printGrid("#",null, 0)); //will need to print to a file

//		printSolutionPath();
	}
	
	private void setSolutionPath() {
		int currentID = numOfCells - 1; //starting at ending cell
		while(currentID != -1) { //traverse the path backwards through the parents of each cell
			solutionPath.push(adjList.get(currentID).getFirst().getCellNum()); //push the ID of the current cell into stack
			currentID = adjList.get(currentID).getFirst().getParentNum(); //traverse to parent cell
		}
	}
	
	private Map<Cell, Integer> solveMazeBFS() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Solves the maze using depth first search (right, down, left, up)
	 * Note: hashmap may not be the best form to store the solution, may redo format
	 * @return a map of all the cells visited in DFS and their order
	 */
	private Map<Cell,Integer> solveMazeDFS() 
	{
		Map<Cell,Integer> sol = new HashMap<Cell,Integer>(); //add cells in order visited
		Cell currentCell = adjList.get(0).getFirst();
		Stack<Cell> cellStack = new Stack<Cell>();
		Cell nextCell = null;
//		int parentID = -1;
		int order = 0;
		
		cellStack.push(currentCell);
		sol.put(currentCell,order++);
		
		while(currentCell.getCellNum() != numOfCells-1) //maze not solved
		{
			if(currentCell.getX() +1 < dimension //something exists to the right
					&& adjList.get(currentCell.getCellNum()+1).contains(currentCell) //they are connected
					&& !sol.containsKey(adjList.get(currentCell.getCellNum()+1).getFirst())) //not visited before
			{
				nextCell = adjList.get(currentCell.getCellNum()+1).getFirst();
				nextCell.setParentNum(currentCell.getCellNum());
				cellStack.push(nextCell);
				//System.out.println("pushed: "+ cellStack.peek());
				sol.put(nextCell,order++);
				currentCell = nextCell;
			}
			else if(currentCell.getY() +1 < dimension //something exists below
					&& adjList.get(currentCell.getCellNum()+dimension).contains(currentCell) //they are connected
					&& !sol.containsKey(adjList.get(currentCell.getCellNum()+dimension).getFirst())) //not visited before
			{
				nextCell = adjList.get(currentCell.getCellNum()+dimension).getFirst();
				nextCell.setParentNum(currentCell.getCellNum());
				cellStack.push(nextCell);
				//System.out.println("pushed: "+ cellStack.peek());
				sol.put(nextCell,order++);
				currentCell = nextCell;
			}
			else if(currentCell.getX() -1 >= 0 //something exists to the left
					&& adjList.get(currentCell.getCellNum()-1).contains(currentCell)
					&& !sol.containsKey(adjList.get(currentCell.getCellNum()-1).getFirst())) //not visited before
			{
				nextCell = adjList.get(currentCell.getCellNum()-1).getFirst();
				nextCell.setParentNum(currentCell.getCellNum());
				cellStack.push(nextCell);
				//System.out.println("pushed: "+ cellStack.peek());
				sol.put(nextCell,order++);
				currentCell = nextCell;
			}
			else if(currentCell.getY() -1 >= 0 //above
					&& adjList.get(currentCell.getCellNum()-dimension).contains(currentCell)
					&& !sol.containsKey(adjList.get(currentCell.getCellNum()-dimension).getFirst())) //not visited before
			{
				nextCell = adjList.get(currentCell.getCellNum()-dimension).getFirst();
				nextCell.setParentNum(currentCell.getCellNum());
				cellStack.push(nextCell);
				//System.out.println("pushed: "+ cellStack.peek());
				sol.put(nextCell,order++);
				currentCell = nextCell;
			}
			else
			{
				//System.out.println("POPPING " + cellStack.peek());
				currentCell = cellStack.pop();
			}
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
	 * Using the depth first search pseudocode given on pg2 of assignment as a guide
	 * can prob be broken up more later
	 * @param size is the number of cells across and down the maze
	 */
	public void mazeGenerationDFS()
	{
		int totalCells = numOfCells-1;
		Random rand = new Random(0);
		Cell currentCell = adjList.get((int)(rand.nextDouble() * totalCells)).getFirst();
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
			
			if(currentCell.getY() -1 >= 0) //above
			{
				if(adjList.get(currentCell.getCellNum()-dimension).size() <= 1)
				{
					neighborCells.add(adjList.get(currentCell.getCellNum()-dimension).getFirst());
					neighborCount++;
				}
			}
			if(currentCell.getX() +1 < dimension) //right
			{
				if(adjList.get(currentCell.getCellNum()+1).size() <= 1)
				{
					neighborCells.add(adjList.get(currentCell.getCellNum()+1).getFirst());
					neighborCount++;
				}
			}
			if(currentCell.getY() +1 < dimension) //below
			{
				if(adjList.get(currentCell.getCellNum()+dimension).size() <= 1)
				{
					neighborCells.add(adjList.get(currentCell.getCellNum()+dimension).getFirst());
					neighborCount++;
				}
			}
			if(currentCell.getX() -1 >= 0) //left
			{
				if(adjList.get(currentCell.getCellNum()-1).size() <= 1)
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
			else
			{
				currentCell = cellStack.pop();
			}
		}
		
	}
	
	/*
	 * Function: Interface method to print the initial view of the grid
	 * PRE: Maze generated
	 * Post: None
	 * Return: None
	*/
	public void printInitialMaze() {
		System.out.println("Maze: \n"+printGrid(" ",null)); //will need to print to a file
	}
	
	/*
	 * Function: Interface method to print the maze and the nodes visited from the DFS
	 * PRE: Maze generated
	 * Post: None
	 * Return: None
	*/
	public void printDFS() {
		System.out.println("DFS: \n"+printGrid(null,dfsSolution)); //will need to print to a file
	}
	
	/*
	 * Function: Interface method to print the shortest path of the maze
	 * PRE: Maze generated
	 * Post: None
	 * Return: None
	*/
	public void printShortestPath() {
		System.out.println(printGrid("#",null)); //will need to print to a file
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
	 *  Function: Prints the initial view of the maze, the visited cells from DFS,
	 *  		  the visited cells from BFS, and the shortest path
	 *  PRE: Adjacency list initialized
	 *  POST: None
	 *  Return: String containing maze representation
	*/
	public String printGrid(String charInCell, Map<Cell,Integer> cellMap) {
		String grid = "";
		int numOfDirections = 2; //number of wall directions to check.
		int solverCount = 0;
		int currentCellCount = 0;
		
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
								if(cellMap.containsKey(adjList.get(currentCellCount).getFirst()))
								{
									grid += cellMap.get(adjList.get(currentCellCount).getFirst())%10;
								}
								else
								{
									grid += " ";
								}
							}
							else //fill in visited cells solution
							{
								if(cellMap.containsKey(adjList.get(currentCellCount).getFirst()))
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
						if(hasNeighbor(eastX, eastY, adjList.get(i*dimension+j))) { //determine if current cell has a path to its east neighbor
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
	 * to be done
	 * @param map
	 * @return
	 */
	public String printSolutionSummary(Map<Cell,Integer> map)
	{
		String sol = "";
		
		sol += "Path: ";
		for(Map.Entry<Cell, Integer> m : map.entrySet())
		{
			sol += "("+m.getKey().getX()+","+m.getKey().getY()+")";
		}
		sol += "\nLengths of path: " + map.size(); //all cells visited
		sol += "\nVisited cells: "+ map.size(); //shortest path length
		
		return sol;
	}
	
	/**
	 * for testing
	 * prints out the adjacency list
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
	 * prints out the [cell number] since the cell is the value
	 * followed by the order it occurs in the solution as the key
	 * followed by a space
	 * @param map the solution map
	 */
	public void printMap(Map<Cell,Integer> map)
	{
		System.out.print("print map: ");
		for(Map.Entry<Cell, Integer> m : map.entrySet())
		{
			System.out.print(""+m.getValue()+m.getKey()+" "); //[cell]order number
		}
		System.out.println();
	}
}
