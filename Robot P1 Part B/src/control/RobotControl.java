package control;

import robot.Robot;

//Robot Assignment for Programming 1 s2 2017
//Adapted by Caspar and Ross from original Robot code in written by Dr Charles Thevathayan
public class RobotControl implements Control
{
	// we need to internally track where the arm is
	private int height = Control.INITIAL_HEIGHT;
	private int width = Control.INITIAL_WIDTH;
	private int depth = Control.INITIAL_DEPTH;

	private int[] barHeights;
	private int[] blockHeights;

	private Robot robot;

	// called by RobotImpl
	@Override
	public void control(Robot robot, int barHeightsDefault[], int blockHeightsDefault[])
	{
		this.robot = robot;

		// some hard coded init values you can change these for testing
		this.barHeights = new int[]
		{ 3, 4, 1, 5, 2, 3, 2, 6 };
		this.blockHeights = new int[]
		{ 3, 2, 1, 2, 1, 1, 2, 2, 1, 1, 2, 1, 2, 3 };

		// FOR SUBMISSION: uncomment following 2 lines
		//	this.barHeights = barHeightsDefault;
		//	this.blockHeights = blockHeightsDefault;

		// initialise the robot
		robot.init(this.barHeights, this.blockHeights, height, width, depth);

		// a simple private method to demonstrate how to control robot
		// note use of constant from Control interface
		// You should remove this method call once you start writing your code
		extendToWidth(Control.MAX_WIDTH);

		// ADD ASSIGNMENT PART A METHOD CALL(S) HERE

	}

	// simple example method to help get you started
	private void extendToWidth(int width)
	{
		while (this.width < width)
		{
			robot.extend();
			this.width++;
		}
	}

	// WRITE THE REST OF YOUR METHODS HERE!

}
