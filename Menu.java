import java.awt.*;

class Menu
{
	private int quitL; 
	private int quitH;

	public void draw(Graphics g)
	{
		// g.drawImage()
		g.setFont(new Font("TimesRoman",Font.BOLD,30));
		g.setColor(Color.blue);
		g.drawString("QUIT",0,0+20);
		quitH = g.getFontMetrics().getHeight();
		quitL = g.getFontMetrics().stringWidth("QUIT");
	}

	public int checkMouse(int x, int y)
	{
		if(x>=0 && y>=0 && x<=(0+quitL) && y<=(0 + quitH))
			return -1;
		return 0;
	}
}