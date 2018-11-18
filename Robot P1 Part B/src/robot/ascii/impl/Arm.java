package robot.ascii.impl;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

import robot.RobotMovement;

public class Arm implements RobotMovement, Drawable {
	private int height, width, depth;

	// Control picker functionality
	private boolean pickerToggle;
	private static final boolean TURN_ON = true;
	private static final boolean TURN_OFF = false;

	public Arm(int height, int width, int depth) {
		this.height = height;
		this.width = width;
		this.depth = depth;
		this.pickerToggle = TURN_OFF;
	}

	public boolean getPickerStatus() {
		return pickerToggle;
	}

	public int getHeight() {
		return height;
	}

	public int getDepth() {
		return depth;
	}

	public int getWidth() {
		return width;
	}

	@Override
	public void draw(SwingTerminalFrame terminalFrame) {
		// Used to identify the bottom of the terminal
		int maxRow = terminalFrame.getTerminalSize().getRows() - 1;
		final int ARM_COLUMN = 0, SPACER = 1;

		// Sets the arm color
		terminalFrame.setBackgroundColor(TextColor.ANSI.WHITE);

		// Tells the terminal how to draw the arm height
		for (int rowPos = maxRow; rowPos > maxRow - height; rowPos--) {
			terminalFrame.setCursorPosition(ARM_COLUMN, rowPos);
			terminalFrame.putCharacter(' ');
		}

		// Tells the terminal how to draw the arm width
		for (int i = 1, rowPos = maxRow - height + SPACER; i < width; i++) {
			terminalFrame.setCursorPosition(i, rowPos);
			terminalFrame.putCharacter(' ');
		}

		// Tells the terminal how to draw the arm depth
		for (int i = 0, rowPos = maxRow - height + SPACER; i < depth + SPACER; i++, rowPos++) {
			terminalFrame.setCursorPosition(width, rowPos);
			terminalFrame.putCharacter(' ');
		}
	}

	@Override
	public void pick() {
		this.pickerToggle = TURN_ON;
	}

	@Override
	public void drop() {
		this.pickerToggle = TURN_OFF;
	}

	@Override
	public void up() {
		this.height++;
	}

	@Override
	public void down() {
		this.height--;
	}

	@Override
	public void contract() {
		this.width--;
	}

	@Override
	public void extend() {
		this.width++;
	}

	@Override
	public void lower() {
		this.depth++;
	}

	@Override
	public void raise() {
		this.depth--;
	}
}
