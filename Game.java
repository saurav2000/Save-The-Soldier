import javax.swing.*;

class Game
{
	private static final int PWIDTH = 1600;
	private static final int PHEIGHT = 1000;

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(PWIDTH, PHEIGHT); 
		frame.setLocationRelativeTo(null);
		Panel p = new Panel(PWIDTH, PHEIGHT);
		frame.add(p);
		frame.setVisible(true);
	}
}