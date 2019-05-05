import javax.swing.*;
import java.awt.*;


class Outside
{
	ArrayList<Soldier> soldiers;
	Image bgImage;

	Outside()
	{
		soldiers = new soldiers();
		bgImage = ImageLoader.load("outsideBG");
	}

	private void spawn()
	{

	}

	public void update()
	{
		for(int i=soldiers.size()-1;i>=0;--i)
		{
			if(soldiers.get(i).isDead())
			{
				soldiers.remove(i);
			}
			soldiers.get(i).update();
		}
		spawn();
	}

	public void draw(Graphics g)
	{
		g.drawImage(bgImage,0,0,null);
		for(int i=0;i<soldiers.size();++i)
		{
			soldiers.get(i).draw(g);
		}
	}

	public Soldier checkPress(int x, int y, boolean hasBed)
	{
		for(int i=0;i<soldiers.size();++i)
		{
			
		}
	}
}