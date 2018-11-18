package robot.ascii.impl;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

public class Block implements Drawable {
	private int column, row, height;

	public Block(int column, int row, int height) {
		this.column = column;
		this.row = row;
		this.height = height;
	}

	public int getBlockTop() {
		return row + height;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public void draw(SwingTerminalFrame terminalFrame) {
		// Used to identify the bottom of the terminal
		int maxRow = terminalFrame.getTerminalSize().getRows() - 1;

		// Sets the block color according to its height
		switch (height) {
		case 1:
			terminalFrame.setBackgroundColor(TextColor.ANSI.YELLOW);
			break;
		case 2:
			terminalFrame.setBackgroundColor(TextColor.ANSI.RED);
			break;
		case 3:
			terminalFrame.setBackgroundColor(TextColor.ANSI.BLUE);
			break;
		}

		// Tells the terminal how to draw the block
		for (int rowPos = maxRow; rowPos > maxRow - height; rowPos--) {
			terminalFrame.setCursorPosition(column, rowPos - row);
			terminalFrame.putCharacter(' ');
		}
	}

	// THE FOLLOWING SHIFTS THE BLOCK'S POSITION IF PICKED BY THE ROBOT ARM
	public void up() {
		this.row++;
	}

	public void down() {
		this.row--;
	}

	public void left() {
		this.column--;
	}

	public void right() {
		this.column++;
	}
}
