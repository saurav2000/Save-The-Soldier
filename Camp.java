import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Camp
{
	private int offsetX,offsetY;
	private Soldier[] beds;
	private int[] doctorBusy;
	private int[] doctorValue;
	private int[][] positions;

	private Soldier tempSoldier;
	private boolean[] doctorMenu;
	private boolean[] doctorSelected;
	private boolean[] bedMenu;
	private int bedSelected;

	private Image[] docImg;
	private Image occ,unOcc,menuImage,back,forward,bed;

	Camp()
	{
		beds = new Soldier[6];
		doctorBusy = new int[]{-1,-1,-1,-1,-1};
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
		bed = ImageLoader.load("bed.png");
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
		g.setFont(new Font("TimesRoman", Font.BOLD, 21));

		for(int i=0;i<6;++i)
		{
			g.drawImage(bed,296 + 419 *(i%3), 50 + 475 * (i/3),null);
			if(beds[i]!=null)
			{
				beds[i].drawTreatmentTimer(g,370+ 419 *(i%3) ,375 + 475 * (i/3));
				if(beds[i].isTreated())
				{
					g.setColor(Color.blue);
					g.drawString("MOVE",340 + 419 *(i%3),425 + 475 * (i/3));
				}
			}
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

		if(isValidSelection())
		{
			g.setFont(new Font("TimesRoman", Font.BOLD, 21));
			// System.out.println(g.getFontMetrics().stringWidth("TREAT"));
			g.drawString("TREAT", offsetX + 410 ,offsetY + 510);
		}

		g2.setStroke(oldStroke);
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
			if(x>=offsetX+410 && x<=offsetX+491 && y>=offsetY+485 && y<=offsetY+510 && isValidSelection())
			{
				beds[bedSelected] = tempSoldier;
				int p=0;
				for(int i=0;i<5;++i)
				{
					if(doctorSelected[i])
					{
						p+=doctorValue[i];
						doctorBusy[i] = bedSelected;
					}
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
			else
			{
				for(int i=0;i<6;++i)
				{
					int a = x - (340 + 419 * (i%3));
					int b = y - (425 + 475 * (i/3));
					if(a>=0 && b<=0 && a<=70 && b>=(-25) && beds[i]!=null)
					{
						beds[i] = null;
						int temp=0;
						for(int j=4;j>=0;--j)
						{
							if(doctorBusy[j]==i)
								temp = temp*10+j;
						}
						return (temp*10)+4;
					}
				}
			}
		}

		return 0;
	}

	public void callMenu(Soldier s)
	{
		tempSoldier = s;
		bedSelected = -1;

		for(int i=0;i<5;++i)
		{
			if(doctorBusy[i] != -1)
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

		for(int i=0;i<5;i++)
			k = k || doctorSelected[i];

		return k&&(bedSelected!= -1);
	}

	public void update(ArrayList<Integer> doc)
	{
		for(int i=0;i<doc.size();++i)
		{
			int x = doc.get(i);
			if(x%10 == 0)
			{
				doctorBusy[0]=-1;
				x/=10;
			}
			while(x>0)
			{
				doctorBusy[x%10]=-1;
				x/=10;
			}

		}

		for(int i=0;i<6;++i)
		{
			if(beds[i]!=null)
				beds[i].update();
		}

		for(int i=0;i<5;++i)
		{
			if(doctorBusy[i] != -1)
				doctorMenu[i]=false;
			else
				doctorMenu[i]=true;
		}
	}
}