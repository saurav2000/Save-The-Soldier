import javax.swing.*;
import java.awt.*;

class Camp
{
	Soldier[] beds;
	int[] doctorBusy;
	int[][] positions;

	Camp()
	{
		beds = new Soldier[6];
		doctorBusy = new int[5];
		positions = new int[6][2];
	}

	public boolean isEmpty()
	{
		for(int i=0;i<6;++i)
		{
			if(beds[i]==null)
				return true;
		}
		return false;
	}

	public void drawBeds(Graphics g, boolean b)
	{
		for(int i=0;i<6;++i)
		{
			
		}
	}

	public void drawMenu(Graphics g)
	{
		
	}

	public int checkMenuPress(int x, int y)
	{
		
	}

	public int checkPress(int x, int y)
	{

	}
}