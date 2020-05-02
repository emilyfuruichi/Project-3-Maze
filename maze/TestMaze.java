package maze;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestMaze {

	private Maze testMaze;
	private static final int[] GRID_SIZE = {4,5,6,7,8,10};
	File testFile;
	@BeforeEach
	void setUp() throws Exception {
		
	}

	/*
	 * Function: Tests whether the maze generation creates the same "random" maze each time
	 * 			 using varying maze sizes
	 * PRE: None
	 * POST: None
	 * Note: This compares the ASCII output to test for correctness
	*/
	@Test
	void testMazeGenerationDFS() throws IOException {
		for(int i : GRID_SIZE)
		{
			testMaze = new Maze(i);
			testFile = new File("TestCases/"+i+"x"+i+"Tests/"+i+"x"+i+"TestMaze.txt");
			Scanner scan = new Scanner(testFile);
			String expected = "";
			while(scan.hasNextLine()) {
				expected += scan.nextLine();
				expected += "\n";
			}
			scan.close();
			assertEquals(expected, testMaze.getInitialMaze());
		}
	}
	

	@Test
	void testMazeGenerationDFSTrivialCase() throws IOException {
		testMaze = new Maze(0);
		testFile = new File("TestCases/TrivialTests/1x1TestMaze.txt");
		Scanner scan = new Scanner(testFile);
		String expected = "";
		while(scan.hasNextLine()) {
			expected += scan.nextLine();
			expected += "\n";
		}
		scan.close();
		assertEquals(expected, testMaze.getInitialMaze());
		
	}
	
	/*
	 * Function: Tests whether the DFS traversed the maze in the correct order
	 * 			 using varying maze sizes
	 * PRE: None
	 * POST: None
	 * Note: This compares the ASCII output to test for correctness
	*/
	@Test
	void testSolveMazeDFS() throws IOException {
		for(int i : GRID_SIZE)
		{
			testMaze = new Maze(i);
			testFile = new File("TestCases/"+i+"x"+i+"Tests/"+i+"x"+i+"DFSTestMaze.txt");
			Scanner scan = new Scanner(testFile);
			String expected = "";
			while(scan.hasNextLine()) {
				expected += scan.nextLine();
				expected += "\n";
			}
			assertEquals(expected, testMaze.getDFSResult());
		}
	}
	
	
	@Test
	void testSolveMazeDFSTrivialCase() throws IOException {
		testMaze = new Maze(0);
		testFile = new File("TestCases/TrivialTests/1x1DFSTestMaze.txt");
		Scanner scan = new Scanner(testFile);
		String expected = "";
		while(scan.hasNextLine()) {
			expected += scan.nextLine();
			expected += "\n";
		}
		scan.close();
		assertEquals(expected, testMaze.getDFSResult());
		
	}
	
	/*
	 * Function: Tests whether the BFS traversed the maze in the correct order
	 * 			 using varying maze sizes
	 * PRE: None
	 * POST: None
	 * Note: This compares the ASCII output to test for correctness
	*/
	@Test
	void testSolveMazeBFS() throws IOException {
		for(int i : GRID_SIZE)
		{
			testMaze = new Maze(i);
			testFile = new File("TestCases/"+i+"x"+i+"Tests/"+i+"x"+i+"BFSTestMaze.txt");
			Scanner scan = new Scanner(testFile);
			String expected = "";
			while(scan.hasNextLine()) {
				expected += scan.nextLine();
				expected += "\n";
			}
			scan.close();
			assertEquals(expected, testMaze.getBFSResult());
		}
	}
	
	@Test
	void testSolveMazeFFSTrivialCase() throws IOException {
		testMaze = new Maze(0);
		testFile = new File("TestCases/TrivialTests/1x1BFSTestMaze.txt");
		Scanner scan = new Scanner(testFile);
		String expected = "";
		while(scan.hasNextLine()) {
			expected += scan.nextLine();
			expected += "\n";
		}
		scan.close();
		assertEquals(expected, testMaze.getBFSResult());
		
	}
	
	/*
	 * Function: Tests whether the shortest path is correct using varying maze sizes
	 * PRE: None
	 * POST: None
	 * Note: This compares the ASCII output to test for correctness
	*/
	@Test
	void testShortestPathASCII() throws IOException {
		for(int i : GRID_SIZE)
		{
			testMaze = new Maze(i);
			testFile = new File("TestCases/"+i+"x"+i+"Tests/"+i+"x"+i+"PathTestMaze.txt");
			Scanner scan = new Scanner(testFile);
			String expected = "";
			while(scan.hasNextLine()) {
				expected += scan.nextLine();
				expected += "\n";
			}
			scan.close();
			assertEquals(expected, testMaze.getShortestPath());
		}
	}
	
	@Test
	void testShortestPathTrivialCase() throws IOException {
		testMaze = new Maze(0);
		testFile = new File("TestCases/TrivialTests/1x1PathTestMaze.txt");
		Scanner scan = new Scanner(testFile);
		String expected = "";
		while(scan.hasNextLine()) {
			expected += scan.nextLine();
			expected += "\n";
		}
		scan.close();
		assertEquals(expected, testMaze.getShortestPath());
		
	}

	/*
	 * Function: Test the correctness of the shortest path using the Cell Id of a 4x4 maze
	 * PRE: None
	 * POST: None
	*/
	@Test
	void testSetSolutionPath4x4()
	{
		testMaze = new Maze(4);
		int[] expected = {0, 1, 5, 9, 8, 12, 13, 14, 15};
		Stack<Integer> actual = testMaze.getSolutionPath();
		for(int i=0; i<expected.length; i++) {
			assertEquals(expected[i], actual.get(actual.size()-i-1)); //top of the stack starts at size-1 
		}
	}
	
	/*
	 * Function: Test the correctness of the shortest path using the Cell Id of a 10x10 maze
	 * PRE: None
	 * POST: None
	*/
	@Test
	void testSetSolutionPath10x10()
	{
		testMaze = new Maze(10);
		int[] expected = {0, 10, 11, 1, 2, 12, 13, 3, 4, 14, 15, 25, 26, 16, 17, 7, 
				8, 18, 19, 29, 28, 38, 48, 58, 68, 67, 57, 47, 37, 36, 46, 45, 44, 
				54, 53, 52, 42, 43, 33, 23, 22, 32, 31, 30, 40, 41, 51, 61, 60, 70,
				80, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99};
		Stack<Integer> actual = testMaze.getSolutionPath();
		for(int i=0; i<expected.length; i++) {
			assertEquals(expected[i], actual.get(actual.size()-i-1)); //top of the stack starts at size-1 
		}
	}
	
	/*
	 * Function: Test to ensure that no Cells are isolated within the maze
	 * PRE: None
	 * POST: None
	*/
	@Test
	void testNoIsolationCondition() {
		for(int i : GRID_SIZE)
		{
			testMaze = new Maze(i);
			for(LinkedList<Cell> v : testMaze.getAdjacencyList())
			{
				assertTrue(v.size() > 1);
			}
		}
	}
	
	@Test
	void testCreateWallMaze() {
		for(int i : GRID_SIZE)
		{
			testMaze = new Maze(i);
			for(LinkedList<Cell> v : testMaze.getAdjacencyList())
			{
				assertTrue(v.size() > 1);
			}
		}
	}
	
	
}
