/**
 * Cell contains the coordinates and cell number for each cell of a maze
 */

package maze;

public class Cell 
{
	private int x; //coordinate x increasing to the right
	private int y; //coordinate y increasing down
	private int cellNum; //individual cell ID
	
	/**
	 * for creating interior cells of the maze
	 * @param num is cell number
	 * @param size is the row/col length
	 */
	public Cell(int num, int size)
	{
		cellNum = num;
		x = num%size;
		y = num/size;
	}
	
	/**
	 * for creating the entry and exit points of the maze
	 * @param num is cell number
	 * @param x coordinate
	 * @param y coordinate
	 */
	public Cell(int num, int x, int y)
	{
		cellNum = num;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * get the x coordinate
	 * @return x coordinate value
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * get the y coordinate
	 * @return y coordinate value
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * get the cell number
	 * @return cell number
	 */
	public int getCellNum()
	{
		return cellNum;
	}
	
	/**
	 * creates a string of the coordinates and cell num
	 * @return string of the cell values
	 */
	public String toString()
	{
		//return "[("+x+","+y+"):"+cellNum+"] ";
		return "["+cellNum+"]";
	}
}
