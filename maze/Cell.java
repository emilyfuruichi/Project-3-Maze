package maze;

public class Cell 
{
	private int x; //coordinate x increasing to the right
	private int y; //coordinate y increasing down
	private int cellNum; //individual cell ID
	
	/*
	 * for creating interior cells of the maze
	 */
	public Cell(int num, int size)
	{
		cellNum = num;
		x = num%size;
		y = num/size;
	}
	
	/*
	 * for creating the entry and exit points of the maze
	 */
	public Cell(int num, int x, int y)
	{
		cellNum = num;
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getCellNum()
	{
		return cellNum;
	}
	
	public String toString()
	{
		//return "[("+x+","+y+"):"+cellNum+"] ";
		return "["+cellNum+"]";
	}
}
