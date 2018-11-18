// Programming 1 2017 S2 Assignment 1B | S3602814 Thien Nguyen
// ASCIIBot; designed by Caspar, additional code by Ross

package robot.ascii;

import control.Control;
import control.RobotControl;
import robot.Robot;
import robot.ascii.impl.Arm;
import robot.ascii.impl.Bar;
import robot.ascii.impl.Block;
import robot.impl.RobotImpl;
import robot.impl.RobotInitException;

public class ASCIIBot extends AbstractASCIIBot implements Robot {

	public static void main(String[] args) {
		new RobotControl().control(new ASCIIBot(), null, null);
	}

	// MUST CALL DEFAULT SUPERCLASS CONSTRUCTOR!
	public ASCIIBot() {
		super();
	}

	private Arm arm;
	private Bar[] bar;
	private Block[] block;

	// Manage target's block index
	private int targetBlock;
	private static final boolean TARGET_FOUND = true;
	private static final boolean TARGET_NOT_FOUND = false;

	// Checks picker status
	private static final boolean PICKER_IS_ON = true;
	private static final boolean PICKER_IS_OFF = false;

	@Override
	public void init(int[] barHeights, int[] blockHeights, int height, int width, int depth) {
		// in addition to validating init params this also sets up keyboard
		// support for the ASCIIBot!
		try {
			RobotImpl.validateInitParams(this, barHeights, blockHeights, height, width, depth);
		} catch (RobotInitException e) {
			System.err.println(e.getMessage());
			System.exit(0);
		}

		// Transfers the bar heights into the ASCIIBot bar array
		this.bar = new Bar[Control.MAX_BARS];
		for (int i = 0; i < barHeights.length; i++)
			bar[i] = new Bar(Control.FIRST_BAR_COLUMN + i, barHeights[i]);

		// Transfers blocks to ASCIIBot
		final int IS_AN_EVEN_NUMBER = 2, CAN_DIVIDE_CLEANLY = 0;
		this.block = new Block[blockHeights.length];
		for (int i = 0, src1Height = 0, src2Height = 0; i < blockHeights.length; i++) {
			if (i % IS_AN_EVEN_NUMBER == CAN_DIVIDE_CLEANLY) {
				block[i] = new Block(Control.SRC1_COLUMN, src1Height, blockHeights[i]);
				src1Height += blockHeights[i];
			} else {
				block[i] = new Block(Control.SRC2_COLUMN, src2Height, blockHeights[i]);
				src2Height += blockHeights[i];
			}
		}

		// Identifies the initial arm position to be internally tracked by ASCIIBot
		arm = new Arm(height, width, depth);

		// Draws the arm, bars and blocks in their initial positions
		drawFrame();
	}

	private void drawFrame() {
		// clear previous contents between each draw (i.e. after each move)
		delayAnimation();

		// Draws each block to the terminal
		for (int i = 0; i < bar.length; i++)
			bar[i].draw(terminalFrame);

		// Draws each block to the terminal
		for (int i = 0; i < block.length; i++)
			block[i].draw(terminalFrame);

		// Draws the arm position to the terminal
		arm.draw(terminalFrame);

		// must flush to display contents after each complete draw operation
		terminalFrame.flush();
	}

	// Identifies the block the arm would manipulate
	private boolean targetBlock() {
		int maxRow = terminalFrame.getTerminalSize().getRows() - 1;

		// Checks if the picker is just above the target block
		for (int i = 0; i < block.length; i++) {
			int armHeight = maxRow - arm.getHeight() + 1;
			int armWidth = arm.getWidth(), blockColumn = 0;
			int armDepth = maxRow - arm.getDepth() - armHeight, blockTop = 0;

			blockTop = block[i].getBlockTop();
			blockColumn = block[i].getColumn();

			if (blockTop == armDepth && blockColumn == armWidth) {
				targetBlock = i;
				return TARGET_FOUND;
			}
		}
		return TARGET_NOT_FOUND;
	}

	// Will only allow the user to use pick() if picker is inactive and found target
	@Override
	public void pick() {
		if (arm.getPickerStatus() == PICKER_IS_OFF) {
			if (targetBlock() == TARGET_FOUND) {
				arm.pick();
				System.out.println("pick()");
			} else {
				System.out.println("Error: No block available");
			}
		} else
			System.out.println("Error: Picker is currently active");
	}

	// Will only allow the user to use drop() if picker is active
	@Override
	public void drop() {
		if (arm.getPickerStatus() == PICKER_IS_ON) {
			arm.drop();
			System.out.println("drop()");
		} else
			System.out.println("Error: Picker is currently inactive");
	}

	@Override
	public void up() {
		arm.up();
		if (arm.getPickerStatus() == PICKER_IS_ON)
			block[targetBlock].up();
		drawFrame();
		System.out.println("up()");
	}

	@Override
	public void down() {
		arm.down();
		if (arm.getPickerStatus() == PICKER_IS_ON)
			block[targetBlock].down();
		drawFrame();
		System.out.println("down()");
	}

	@Override
	public void contract() {
		arm.contract();
		if (arm.getPickerStatus() == PICKER_IS_ON)
			block[targetBlock].left();
		drawFrame();
		System.out.println("contract()");
	}

	@Override
	public void extend() {
		arm.extend();
		if (arm.getPickerStatus() == PICKER_IS_ON)
			block[targetBlock].right();
		drawFrame();
		System.out.println("extend()");
	}

	@Override
	public void lower() {
		arm.lower();
		if (arm.getPickerStatus() == PICKER_IS_ON)
			block[targetBlock].down();
		drawFrame();
		System.out.println("lower()");
	}

	@Override
	public void raise() {
		arm.raise();
		if (arm.getPickerStatus() == PICKER_IS_ON)
			block[targetBlock].up();
		drawFrame();
		System.out.println("raise()");
	}
}
