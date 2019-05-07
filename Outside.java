import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Outside
{
	private ArrayList<Soldier> soldiers;
	private Image bgImage, sol1, sol2, sol3, forward;
	private long iTime;
	private boolean spawnFlag;
	private int offsetX, offsetY;
	private int score;

	Outside()
	{
		soldiers = new ArrayList<Soldier>();
		bgImage = ImageLoader.load("outsideBG.png");
		sol1 = ImageLoader.load("s1f.png");
		sol2 = ImageLoader.load("s2f.png");
		sol3 = ImageLoader.load("s3f.png");
		forward = ImageLoader.load("forward.png");
		iTime=0;
		spawnFlag = false;
		offsetX = 1600-120;
		offsetY = 800;
		score=0;
	}

	private void spawn()
	{
		if(iTime==0)
		{
			iTime = System.currentTimeMillis();
			soldiers.add(new Soldier(1,offsetX,offsetY,sol2));
		}
		else
		{
			long k=0;
			if(spawnFlag)
				k = 3000;
			else
				k = 5000;
			long n = System.currentTimeMillis();
			if(n-iTime > k)
			{
				Soldier s=null;
				int type = (int)(Math.random() * 3);
				if(type==0)
					s = new Soldier(type,offsetX - soldiers.size()*120 ,offsetY, sol1);
				else if(type==1)
					s = new Soldier(type,offsetX - soldiers.size()*120 ,offsetY, sol2);
				else if(type==2)
					s = new Soldier(type,offsetX - soldiers.size()*120 ,offsetY, sol3);
				spawnFlag = !spawnFlag;
				soldiers.add(s);
				iTime = System.currentTimeMillis();
			}
		}	
	}

	public void update()
	{
		for(int i=soldiers.size()-1;i>=0;--i)
		{
			soldiers.get(i).update();
			if(soldiers.get(i).isDead())
			{
				soldiers.remove(i);
				score-=2;
			}
		}
		for(int i=0;i<soldiers.size();++i)
			soldiers.get(i).setPosition(offsetX - i*120 , offsetY);	

		spawn();
	}

	public void draw(Graphics g)
	{
		g.drawImage(bgImage,0,0,null);
		g.drawImage(forward,1500,0,null);
		g.setFont(new Font("Helvetica",Font.PLAIN,40));
		g.setColor(Color.red);
		g.drawString("SCORE: "+score,700,30);
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
			{
				score += ((100-soldier.getDead()) * 20)/100; 
				return soldiers.remove(i);
			}
		}

		return null;
	}
}