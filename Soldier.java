import javax.swing.*;
import java.awt.*;

class Soldier
{
	long timeMillis;
	long initialTime;

	int posX,posY,timX,timY,r;
	long dead;

	Image image;
	boolean selected;

	Soldier(int type, int x1, int y1, int x2, int y2, Image image)
	{
		this.image = image;
		posX=x1;
		posY=y1;
		timX=x2;
		timY=y2;
		if(type==0)
			timeMillis = 6000;
		else if(type==1)
			timeMillis = 8000;
		else
			timeMillis = 10000;
		dead=0;
		selected=false;
		initialTime = System.currentTimeMillis();
	}

	public void update()
	{
		if(!selected)
		{
			long n = System.currentTimeMillis();
			dead = ((n-initialTime)*100)/timeMillis;
			if(dead>100)
				dead=100;
		}		
	}

	public void draw(Graphics g)
	{
		g.drawImage(image,posX,posY,null);
		int a = ((int)dead *360)/100;
		g.setColor(Color.red);
		g.fillArc(timX,timY,r,r,90,a);
		g.setColor(Color.white);
		g.fillArc(timX,timY,r,r,90+a,360-a);
	}

	public int checkPress(int x, int y)
	{
		if(x>=timX-r && x<=timX+r && y>=timY-r && y<=timY+r)
		{
			selected=true;
			return 0;
		}
		return -1;
	}

	public boolean isDead()
	{
		return dead>=100;
	}
}