package robot.ascii.impl;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

public class Bar implements Drawable {
	private int column, height;

	public Bar(int column, int height) {
		this.column = column;
		this.height = height;
	}

	@Override
	public void draw(SwingTerminalFrame terminalFrame) {
		// Used to identify the bottom of the terminal
		int maxRow = terminalFrame.getTerminalSize().getRows() - 1;

		// Used to identify where the bar height should be labelled
		int middleOfBar = height / 2;

		// Sets the text and block colors
		terminalFrame.setForegroundColor(TextColor.ANSI.BLACK);
		terminalFrame.setBackgroundColor(TextColor.ANSI.GREEN);

		// Tells the terminal how to draw the bar
		for (int count = 0, rowPos = maxRow; rowPos > maxRow - height; count++, rowPos--) {
			terminalFrame.setCursorPosition(column, rowPos);
			terminalFrame.putCharacter(count == (middleOfBar) ? (char) ('0' + height) : ' ');
		}
	}
}
