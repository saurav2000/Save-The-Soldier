import javax.swing.*;
import java.awt.*;

class Healing
{
	int[][] occupancy;
	long[][] time;
	int[][][] positions;
	Image bgImage, minibedOcc, minibedUn, entrance, back, forward;
	Healing()
	{
		occupancy = new int[6][4];
		time = new long[6][4];
		positions = new int[6][4][2];
		bgImage = ImageLoader.load("healingBG.png");
		minibedUn = ImageLoader.load("minibedUn.png");
		minibedOcc = ImageLoader.load("minibedOcc.png");
		entrance =  ImageLoader.load("healentrance.png");
		back = ImageLoader.load("back.png");
		forward = ImageLoader.load("forward.png");
		initialise();
	}

	public void initialise()
	{

	}

	public void update()
	{
		for(int i=0;i<6;++i)
		{
			for(int j=0;j<4;++j)
			{
				if(System.currentTimeMillis()-time[i][j]>10000)
				{
					occupancy[i][j]=0;
					time[i][j]=0;
				}
			}
		}
	}

	public int set(int i, int j)
	{
		occupancy[i][j]=1;
		time[i][j]=System.currentTimeMillis();
		return 2;//Treatment
	}

	public void draw(Graphics g)
	{
		g.drawImage(bgImage,0,0,null);
		g.drawImage(entrance,0,450,null);
		g.drawImage(back,0,0,null);
		g.drawImage(forward,1500,0,null);
		for(int i=0;i<6;++i)
		{
			for(int j=0;j<4;++j)
			{
				if(occupancy[i][j]==1)
					g.drawImage(minibedOcc,positions[i][j][0],positions[i][j][1],null);
				else
					g.drawImage(minibedUn,positions[i][j][0],positions[i][j][1],null);
			}
		}
	}

	public int checkPress(int x, int y, int k)
	{
		if(k==0)
		{
			if(x>=0&&y>=0&&x<=100&&y<=100)
				return 1;
		}
		return 0;
	}
}