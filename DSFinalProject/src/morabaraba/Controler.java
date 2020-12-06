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
			player = player == 1 ? 2 : 1;
			return;
		}
		if (turns != 0)
		{
			if (gameBoard[x][y] == 0)
			{
				phaseOne(x, y);

				turns--;
				if (player == 1)
					cowCountRed++;
				else
					cowCountBlue++;
			} else
			{
				throw new IllegalArgumentException(
						"This Spot is owned by Player " + gameBoard[x][y] + "! Please choose a new location");
			}

		}
		if (isMill(x, y))
		{
			System.out.println("MILL");
			killCow = true;
			message = "Player " + player + " gets to kill a cow";
			return;
		}
		player = player == 1 ? 2 : 1;
	}

	private void phaseOne(int x, int y)
	{
		gameBoard[x][y] = player;

		message = "Player " + player + "s turn!";

	}

	private void phaseTwoAndThree(int x, int y) throws IllegalArgumentException
	{

	}

	private boolean isAdjacent(int curSpaceX, int curSpaceY, int newSpaceX, int newSpaceY)
	{
		return false;
	}

	private boolean isMill(int x, int y)
	{
		if ((player == 1 && cowCountRed < 2) || (player == 2 && cowCountBlue < 2))
		{
			return false;
		}
		if (millCaseOne(x, y) || millCaseTwo(x, y))
			return true;
		return false;
	}

	private boolean millCaseOne(int x, int y)
	{
		if (gameBoard[0][y] == player && gameBoard[1][y] == player && gameBoard[2][y] == player)
		{
			return true;
		}
		return false;
	}

	private boolean millCaseTwo(int x, int y)
	{
		if ((y==1||y==2||y==0)&&gameBoard[x][0] == player && gameBoard[x][1] == player && gameBoard[x][2] == player)
		{
			return true;
		} else if ((y==6||y==5||y==4)&&gameBoard[x][6] == player && gameBoard[x][5] == player && gameBoard[x][4] == player)
		{
			return true;
		} else if ((y==2||y==3||y==4)&&gameBoard[x][2] == player && gameBoard[x][3] == player && gameBoard[x][4] == player)
		{
			return true;
		} else if ((y==0||y==7||y==6)&&gameBoard[x][0] == player && gameBoard[x][7] == player && gameBoard[x][6] == player)
		{
			return true;
		}
		return false;
	}

	private boolean endGame()
	{
		// if either player is down to 2 cows
		// if both players still have 12 cows after phase 1
		// if either player has no legal moves... ie in phase 2 but w/o adjacent space
		return false;
	}

	private void shootCow(int x, int y) throws IllegalArgumentException
	{
		// TODO
		if(gameBoard[x][y] == 0 || gameBoard[x][y] == player)
		{
			throw new IllegalArgumentException("Can't kill an empty space or your own cow!");
		}
		boolean temp = false;
		if(isMill(x,y))
		{
			for(int i = 0; i<3; i++)
			{
				for(int j = 0; j<8; j++)
				{
					if(!isMill(i,j)&&gameBoard[i][j]==gameBoard[x][y])
					{
						temp = true;
						break;
					}
				}
			}
		}
		if(temp)
			throw new IllegalArgumentException("Can't kill cows in a mill");
		gameBoard[x][y]=0;
		if (player == 2)
			cowCountRed--;
		else
			cowCountBlue--;
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
