import javax.swing.*;
import java.awt.*;

class Soldier
{
	private long timeMillis;
	private long initialTime;

	private int posX,posY,timX,timY,r,type;
	private long dead,treatment;

	private Image image;
	private boolean selected, treated;

	Soldier(int type, int x1, int y1, Image image)
	{
		this.image = image;
		this.type = type;
		posX=x1;
		posY=y1;
		r=30;
		timX=(x1+24);
		timY=(y1-r);
		if(type==0)
			timeMillis = 10000;
		else if(type==1)
			timeMillis = 15000;
		else
			timeMillis = 20000;
		dead=0;
		selected=false;
		treated = false;
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
		else if(!treated)
		{
			long n = System.currentTimeMillis();
			treatment = ((n-initialTime)*100)/timeMillis;
			if(treatment>100)
				treatment=100;
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

	public void drawTreatmentTimer(Graphics g, int x, int y)
	{
		g.setColor(Color.green);
		int a = ((int)treatment *360)/100;
		g.fillArc(x,y,25,25,90,a);
		g.setColor(Color.white);
		g.fillArc(x,y,25,25,90+a,360-a);
	}

	public int checkPress(int x, int y)
	{
		if(x>=timX-r && x<=timX+r && y>=timY-r && y<=timY+r)
		{
			return 0;
		}
		return -1;
	}

	public void setTreating(int power)
	{
		timeMillis = (dead * 10 * 1000)/power;
		selected = true;
		initialTime = System.currentTimeMillis();
	}

	public boolean isDead()
	{
		return dead>=100;
	}

	public int getDead()
	{
		return (int)dead;
	}

	public boolean isTreated()
	{
		return treatment>=100;
	}

	public void setPosition(int x, int y)
	{
		posX = x;
		posY = y;
		timX = x+24;
		timY = y-r;
	}

	public int getType()
	{
		return type;
	}
}