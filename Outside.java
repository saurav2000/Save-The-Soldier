import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Outside
{
	private ArrayList<Soldier> soldiers;
	private Image bgImage, sol1, forward;

	Outside()
	{
		soldiers = new ArrayList<Soldier>();
		bgImage = ImageLoader.load("outsideBG.png");
		sol1 = ImageLoader.load("s1f.png");
		forward = ImageLoader.load("forward.png");
	}

	private void spawn()
	{
		if(soldiers.size()!=1)
			soldiers.add(new Soldier(0,100,200,sol1));
	}

	public void update()
	{
		for(int i=soldiers.size()-1;i>=0;--i)
		{
			soldiers.get(i).update();
			if(soldiers.get(i).isDead())
			{
				soldiers.remove(i);
			}
		}
		spawn();
	}

	public void draw(Graphics g)
	{
		g.drawImage(bgImage,0,0,null);
		g.drawImage(forward,1500,0,null);
		for(int i=0;i<soldiers.size();++i)
		{
			soldiers.get(i).draw(g);
		}
	}

	public int checkPress(int x, int y)
	{
		if(x>=1500&x<=1600&&y>=0&&y<=100)
			return 1;
		return 0;
	}

	public Soldier checkMouse(int x, int y, boolean hasBed)
	{
		if(!hasBed)
			return null;

		for(int i=0;i<soldiers.size();++i)
		{
			Soldier soldier = soldiers.get(i);
			if(soldier.checkPress(x,y)==0)
				return soldiers.remove(i);
		}

		return null;
	}
}