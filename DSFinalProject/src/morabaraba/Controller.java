package morabaraba;

/**
 * 
 * @author jackb
 * @author julia mach
 * 
 * @version Fall 2020
 * 
 *          This class is our controller class and it takes care of all the back
 *          end. This class stores game state, handles rules, throws exception
 *          when broken, and makes sure everything runs smoothly.
 *
 */
public class Controller
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
	private boolean killCow;

	/** 3 by 8 int array to keep track of the state of the gameboard */
	private int[][] gameBoard;

	/** message for the label */
	private String message;

	/** keeps track if this click is the first or second for phase two and three */
	private boolean secondClick;

	/** holds which player is next; 1 is red, 2 is blue */
	private int player;

	/** keeps track of how many turns have happened */
	private int turns;

	/** constructor that initializes variables */
	public Controller()
	{
		curSpace = new int[2];
		gameBoard = new int[3][8];
		reset();
	}

	/**
	 * Determines what action is to be next when a user clicks a board space
	 * 
	 * @param x the location of the first value for the board space
	 * @param y the location of the second value for the board space
	 * @throws IllegalArgumentException for illegal input of player illegally
	 *                                  clicking on space
	 */
	public void buttonPress(int x, int y) throws IllegalArgumentException
	{
		if (endGame())
		{
			throw new IllegalArgumentException("Game is over; please reset");
		}
		if (killCow)
		{
			shootCow(x, y);
			killCow = !killCow;
			player = player == 1 ? 2 : 1;
			updateMessage(5);
			endGame();
			return;
		}
		// this means we are in phase one
		if (turns != 0)
		{
			if (gameBoard[x][y] == 0)
			{
				gameBoard[x][y] = player;

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
		} else // this evaluates in phase 2 or 3
		{
			updateMessage(2);
			if (!secondClick)
			{ // make sure this click has an adjacent spot

				if ((player == 1 && cowCountRed > 3 && !hasAdjacent(x, y))
						|| (player == 2 && cowCountBlue > 3 && !hasAdjacent(x, y)))
				{
					throw new IllegalArgumentException(
							"This spot has no space to move to. Please choose a new location");
				}
				if (gameBoard[x][y] != player && gameBoard[x][y] != 0)
				{
					throw new IllegalArgumentException(
							"This Spot is owned by Player " + gameBoard[x][y] + "! Please choose a new location");
				} else if (gameBoard[x][y] != player && gameBoard[x][y] == 0)
				{
					throw new IllegalArgumentException("This Spot is owned by no one! Please choose a new location");
				}
				curSpace[0] = x;
				curSpace[1] = y;
				secondClick = !secondClick;
				updateMessage(3);
				return;
			} else
			{
				if (gameBoard[x][y] == 0)
				{
					phaseTwoAndThree(x, y);
					secondClick = !secondClick;
				} else
				{
					if (gameBoard[x][y] != 0)
					{
						throw new IllegalArgumentException("This Spot is owned by Player " + gameBoard[x][y]
								+ "! Please choose an empty location");
					} else
					{
						throw new IllegalArgumentException(
								"This Spot is owned by no one! Please choose an empty location");
					}
				}
			}
		}
		if (isMill(x, y))
		{
			killCow = true;
			updateMessage(4);
			return;
		}
		player = player == 1 ? 2 : 1;
		updateMessage(5);
		endGame();
	}

	/**
	 * Updates the message for the user
	 * 
	 * @param source the source from where the message is coming from
	 */
	private void updateMessage(int source)
	{
		if (source == 1)
		{
			if (player == 1)
			{
				message = "Player " + player + "s turn! You have " + (turns / 2) + " cows left to place.";
			} else
			{
				message = "Player " + player + "s turn! You have " + ((turns + 1) / 2) + " cows left to place.";
			}
		} else if (source == 2)
		{
			message = "Player " + player + ", select a cow to move!";
		} else if (source == 3)
		{
			message = "Player " + player + ", select a spot to move to!";
		} else if (source == 4)
		{
			message = "Player " + player + ", select a cow to kill!";
		} else if (source == 5)
		{
			if (turns != 0)
			{
				updateMessage(1);
			} else
			{
				updateMessage(2);
			}
		}
	}

	/**
	 * Execution for phase two and three of the game
	 * 
	 * @param x the location of the first value for the board space
	 * @param y the location of the second value for the board space
	 * @throws IllegalArgumentException if player does not pick adjacent spot in
	 *                                  phase 2
	 */
	private void phaseTwoAndThree(int x, int y) throws IllegalArgumentException
	{
		if ((player == 1 && cowCountRed > 3) || (player == 2 && cowCountBlue > 3))
		{
			if (!isAdjacent(curSpace, x, y))
				throw new IllegalArgumentException("That space is not adjacent to the cow you chose!");
		}
		gameBoard[x][y] = player;
		gameBoard[curSpace[0]][curSpace[1]] = 0;
	}

	/**
	 * Checks to see if spots are adjacent
	 * 
	 * @param curSpace the current location of player�s cow
	 * @param newX     the new x location for the cow
	 * @param newY     the new y location for the cow
	 * @return true is spots are adjacent, false otherwise
	 */
	private boolean isAdjacent(int[] oldSpace, int newSpaceX, int newSpaceY)
	{
		if (oldSpace[0] == newSpaceX) // if x value is equal to the x value in the new space
		{
			if (Math.abs(oldSpace[1] - newSpaceY) == 1) // check if they are exactly one apart
				return true;
			else if (Math.abs(oldSpace[1] - newSpaceY) == 7) // check if they are exactly seven apart
				return true;
		} else
		{
			if (oldSpace[1] == newSpaceY && Math.abs(oldSpace[0] - newSpaceX) == 1)
				// check if the y values are the same and if the x values are one apart
				return true;
		}
		return false;
	}

	/**
	 * Method to check if space is location in a mill
	 * 
	 * @param x, the location of the first value for the board space
	 * @param y, the location of the second value for the board space
	 * @return true is selected cow is in mill, false otherwise
	 */
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

	/**
	 * Checks for mills that have the same y values
	 * 
	 * @param x, the location of the first value for the board space
	 * @param y, the location of the second value for the board space
	 * @return true if selected cow is in mill, false otherwise
	 */
	private boolean millCaseOne(int x, int y)
	{
		if (gameBoard[0][y] == player && gameBoard[1][y] == player && gameBoard[2][y] == player)
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks for mills that have same x values
	 * 
	 * @param x, the location of the first value for the board space
	 * @param y, the location of the second value for the board space
	 * @return true if selected cow is in mill, false otherwise
	 */
	private boolean millCaseTwo(int x, int y)
	{
		// check a bunch of cases that result in mills
		if ((y == 1 || y == 2 || y == 0) && gameBoard[x][0] == player && gameBoard[x][1] == player
				&& gameBoard[x][2] == player)
		{
			return true;
		} else if ((y == 6 || y == 5 || y == 4) && gameBoard[x][6] == player && gameBoard[x][5] == player
				&& gameBoard[x][4] == player)
		{
			return true;
		} else if ((y == 2 || y == 3 || y == 4) && gameBoard[x][2] == player && gameBoard[x][3] == player
				&& gameBoard[x][4] == player)
		{
			return true;
		} else if ((y == 0 || y == 7 || y == 6) && gameBoard[x][0] == player && gameBoard[x][7] == player
				&& gameBoard[x][6] == player)
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks for if any conditions for ending the game are met
	 * 
	 * @return true if the game should end, false otherwise
	 */
	private boolean endGame()
	{
		// if either player is down to 2 cows
		// if both players still have 12 cows after phase 1
		// if either player has no legal moves... ie in phase 2 but w/o adjacent space

		boolean redAdj = false;

		boolean blueAdj = false;

		if (turns == 0)
		{
			if (cowCountBlue < 3)
			{
				message = "RED WINS!!!";
				return true;
			}
			if (cowCountRed < 3)
			{
				message = "BLUE WINS!!!";
				return true;
			}
			if (cowCountRed == 12 && cowCountBlue == 12)
			{
				message = "Draw :(";
				return true;
			}
			for (int i = 0; i < 3; i++)
			{
				for (int j = 0; j < 8; j++)
				{
					if (gameBoard[i][j] == 1 && hasAdjacent(i, j))
					{
						redAdj = true;
					} else if (gameBoard[i][j] == 2 && hasAdjacent(i, j))
					{
						blueAdj = true;
					}
				}
			}
			if (!redAdj)
			{
				message = "BLUE WINS!!!";
				return true;
			}
			if (!blueAdj)
			{
				message = "RED WINS!!!";
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks to see if current spot has an open spot adjacent to it
	 * 
	 * @param x, the location of the first value for the board space
	 * @param y, the location of the second value for the board space return true if
	 *           cow has an adjacent spot, false otherwise
	 */
	private boolean hasAdjacent(int x, int y)
	{
		if (gameBoard[x][y] == 0)
		{
			return true;
		}
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (isAdjacent(new int[]
				{ x, y }, i, j) && gameBoard[i][j] == 0)
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method for shooting a cow if a legal shoot
	 * 
	 * @param x, the location of the first value for the cow being shot
	 * @param y, the location of the second value for the cow being shot
	 * @throws IllegalArgumentException if shoot is not legal
	 */
	private void shootCow(int x, int y) throws IllegalArgumentException
	{
		if (gameBoard[x][y] == 0 || gameBoard[x][y] == player)
		{
			throw new IllegalArgumentException("Can't kill an empty space or your own cow!");
		}
		boolean temp = false;
		player = player == 1 ? 2 : 1; // switch player to be able to check for that players cows
		if (isMill(x, y))
		{
			for (int i = 0; i < 3; i++)
			{
				for (int j = 0; j < 8; j++)
				{
					if (!isMill(i, j) && gameBoard[i][j] == gameBoard[x][y])
					{
						temp = true;
						break;
					}
				}
			}
		}
		player = player == 1 ? 2 : 1; // switch player back so that game state is preserved
		if (temp)
			throw new IllegalArgumentException("Can't kill cows in a mill");
		gameBoard[x][y] = 0;

		if (player == 2)
			cowCountRed--;
		else
			cowCountBlue--;
	}

	/**
	 * Method for returning the space owner of a designated spot
	 * 
	 * @param x, the location of the first value for the board space
	 * @param y, the location of the second value for the board space
	 * @return the spaceOwner of the spot
	 */
	public int getSpaceOwner(int x, int y)
	{
		return gameBoard[x][y];
	}

	/**
	 * Resets the game board to its original state
	 */
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
		message = "Player 1s turn! You have 12 cows left to place.";
		secondClick = false;
		player = 1;
	}

	/**
	 * Message for updating the message
	 * 
	 * @return the message to the user
	 */
	public String getMessage()
	{
		return message;
	}
}
