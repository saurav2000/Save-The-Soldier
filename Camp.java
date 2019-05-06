import javax.swing.*;
import java.awt.*;

class Camp
{
	int offsetX,offsetY;
	Soldier[] beds;
	boolean[] doctorBusy;
	int[] doctorValue;
	int[][] positions;

	Soldier tempSoldier;
	boolean[] doctorMenu;
	boolean[] doctorSelected;
	boolean[] bedMenu;
	int bedSelected;

	Image[] docImg;
	Image occ,unOcc,menuImage,back,forward;

	Camp()
	{
		beds = new Soldier[6];
		doctorBusy = new boolean[5];
		doctorSelected = new boolean[5];
		bedSelected = -1;
		bedMenu = new boolean[6];
		doctorMenu = new boolean[5];
		positions = new int[6][2];
		doctorValue = new int[5];
		docImg = new Image[5];
		loadDoctors();
		occ = ImageLoader.load("bedOcc.png");
		unOcc = ImageLoader.load("bedUnocc.png");
		menuImage = ImageLoader.load("bg.png");
		back = ImageLoader.load("back.png");
		forward = ImageLoader.load("forward.png");
		offsetX = 350;
		offsetY = 220;
	}

	private void loadDoctors()
	{
		doctorValue[0]=100;
		doctorValue[1]=30;
		doctorValue[2]=40;
		doctorValue[3]=80;
		doctorValue[4]=60;

		for(int i=0;i<5;++i)
		{
			docImg[i] = ImageLoader.load("d"+(i+1)+".png");
		}
	}

	public boolean hasBed()
	{
		for(int i=0;i<6;++i)
		{
			if(beds[i]==null)
				return true;
		}
		return false;
	}

	private void drawBeds(Graphics g)
	{
		g.drawImage(forward,1500,0,null);
		g.drawImage(back,0,0,null);
		for(int i=0;i<6;++i)
		{

		}
	}

	private void drawMenu(Graphics g)
	{
		g.drawImage(menuImage,offsetX,offsetY,null);
		g.setColor(Color.green);
		Graphics2D g2 = (Graphics2D)g;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(3));
		for(int i=0;i<5;++i)
		{
			if(doctorMenu[i])
				g.drawImage(docImg[i],offsetX+80+160*i,offsetY+120,null);
			if(doctorSelected[i])
				g2.drawRect(offsetX+80+160*i,offsetY+120,100,100);
		}

		for(int i=0;i<6;++i)
		{
			if(bedMenu[i])
			{
				g.drawImage(unOcc,offsetX+78+137*i,offsetY+320,null);
				if(bedSelected==i)
					g2.drawRect(offsetX+78+137*i,offsetY+320,59,100);
			}
			else
				g.drawImage(occ,200+i*30,300,null);
		}
		g2.setStroke(oldStroke);
		// g2.dispose();
	}

	public void draw(Graphics g, int k)
	{
		drawBeds(g);
		if(k==1)
			drawMenu(g);
	}

	public int checkPress(int x, int y, int k)
	{
		if(k==1)
		{
			for(int i=0;i<5;++i)
			{
				if(x>=offsetX+80+160*i && x<=offsetX+180+160*i && y>=offsetY+120&&y<=offsetY+220)
				{
					if(doctorMenu[i])
						doctorSelected[i]= !doctorSelected[i];
				}
			}
			for(int i=0;i<6;++i)
			{
				if(x>=offsetX+78+137*i && x<=offsetX+137+137*i && y>=offsetY+320 && y<=offsetY+420)
				{
					if(bedMenu[i])
						bedSelected=i;
				}
			}
			if(x>=offsetX+350 && x<=offsetX+450 && y>=offsetY+480 && y<=offsetY+530 && isValidSelection())
			{
				beds[bedSelected] = tempSoldier;
				int p=0;
				for(int i=0;i<5;++i)
				{
					if(doctorSelected[i])
						p+=doctorValue[i];
				}
				tempSoldier.setTreating(p);
				return 1;
			}
		}
		else
		{
			if(x>=0&&y>=0&&x<=100&&y<=100)
				return 2;
			else if(x>=1500&&x<=1600&&y>=0&&y<=100)
				return 3;
		}
		return 0;
	}

	public void callMenu(Soldier s)
	{
		tempSoldier = s;
		bedSelected = -1;

		for(int i=0;i<5;++i)
		{
			if(doctorBusy[i])
				doctorMenu[i]=false;
			else
				doctorMenu[i]=true;

			doctorSelected[i]=false;
		}

		for(int i=0;i<6;++i)
		{
			if(beds[i]==null)
				bedMenu[i] = true;
		}

	}

	private boolean isValidSelection()
	{
		boolean k = false;

		for(int i=0;i<6;i++)
			k = k || doctorSelected[i];

		return k&&(bedSelected!= -1);
	}
}