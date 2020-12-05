package morabaraba;

public class Controler
{

	/** the amount of cows the red player has */
	private int cowCountRed;

	/** the amount of cows the blue player has */
	private int cowCountBlue;

	/** phase two and three holder variable for the last space chosen */
	private int[] curSpace;

	/**
	 * boolean that keeps track if the next click should kill a cow; false = no
	 * kill, true = kill
	 */
	private Boolean killCow;

	/** 3 by 8 int array to keep track of the state of the gameboard */
	private int[][] gameBoard;

	/** message for the label */
	private String message;

	/** keeps track if this click is the first or second for phase two and three */
	private Boolean secondClick;

	/** holds which player is next; 1 is red, 2 is blue */
	private int player;

	/** keeps track of how many turns have happened */
	private int turns;

	/** constructor that initializes variables */
	public Controler()
	{
		curSpace = new int[2];
		gameBoard = new int[3][8];
		reset();
	}

	public void buttonPress(int x, int y) throws IllegalArgumentException
	{
		if (killCow)
		{
			shootCow(x, y);
			killCow = !killCow;
			return;
		}
		if (turns != 0)
		{
			if(gameBoard[x][y]==0)
			{
				phaseOne(x, y);
				turns--;
				if(player == 1) cowCountRed++;
				else cowCountBlue++;
				message = "Player " + player + "s turn!";
			} else
			{
				throw new IllegalArgumentException("This Spot is owned by Player "+ gameBoard[x][y]+"! Please choose a new location");
			}
		}

	}

	private void phaseOne(int x, int y)
	{
		gameBoard[x][y] = player;
		player = player == 1 ? 2 : 1;
	}

	private void shootCow(int x, int y)
	{
		// TODO
	}

	public int getSpaceOwner(int x, int y)
	{
		return gameBoard[x][y];
	}

	public void reset()
	{
		turns = 24;
		cowCountRed = 0;
		cowCountBlue = 0;
		killCow = false;
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				gameBoard[i][j] = 0;
			}
		}
		message = "Player 1s turn!";
		secondClick = false;
		player = 1;
	}

	public String getMessage()
	{
		return message;
	}
}
