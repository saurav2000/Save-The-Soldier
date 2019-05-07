import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Healing
{
	private int[][] occupancy;
	private long[][] time;
	private int[][][] positions;
	private Image bgImage, bedOcc, bedUnocc, entrance, back;

	Healing()
	{
		occupancy = new int[6][4];
		time = new long[6][4];
		positions = new int[6][4][2];
		bgImage = ImageLoader.load("healingBG.png");
		bedUnocc = ImageLoader.load("bedUnocc.png");
		bedOcc = ImageLoader.load("bedOcc.png");
		back = ImageLoader.load("back.png");
		initialise();
	}

	public void initialise()
	{
		for(int i=0;i<6;++i)
		{
			for(int j=0;j<4;++j)
			{
				occupancy[i][j]= -1;
				positions[i][j][1] = 100 + 150*(j%2) + 550*(j/2);
				positions[i][j][0] = 200 + 210*(i%3) + 720*(i/3);
			}
		}
	}

	public ArrayList<Integer> update()
	{
		ArrayList<Integer> res = new ArrayList<>();
		for(int i=0;i<6;++i)
		{
			for(int j=0;j<4;++j)
			{
				if(time[i][j]!=0 && System.currentTimeMillis()-time[i][j]>15000+(i+((int)Math.abs(j-1.5)))*1000)
				{
					res.add(occupancy[i][j]);
					occupancy[i][j]=-1;
					time[i][j]=0;
				}
			}
		}
		return res;
	}

	public void draw(Graphics g, int k)
	{
		g.drawImage(bgImage,0,0,null);
		g.drawImage(back,0,0,null);
		for(int i=0;i<6;++i)
		{
			for(int j=0;j<4;++j)
			{
				if(occupancy[i][j]!= -1)
					g.drawImage(bedOcc,positions[i][j][0],positions[i][j][1],null);
				else
					g.drawImage(bedUnocc,positions[i][j][0],positions[i][j][1],null);
			}
		}

		if(k%10==1)
		{
			g.setColor(Color.blue);
			g.setFont(new Font("TimesRoman", Font.BOLD, 21));
			int l = g.getFontMetrics().stringWidth("CLICK AN EMPTY BED");
			g.drawString("CLICK AN EMPTY BED",800-l/2,450);
		}
	}

	public int checkPress(int x, int y, int k)
	{
		if(k==0)
		{
			if(x>=0&&y>=0&&x<=100&&y<=100)
				return 1;
		}
		else if(k%10==1)
		{
			for(int i=0;i<6;++i)
			{
				for(int j=0;j<4;++j)
				{
					if(x>=positions[i][j][0] && y>=positions[i][j][1] && x<=positions[i][j][0]+60 && y<=positions[i][j][1]+100 && occupancy[i][j]==-1)
					{
						occupancy[i][j] = k/10;
						time[i][j] = System.currentTimeMillis();
						return 2;
					}
				}
			}
		}
		return 0;
	}
}