import java.awt.*;

class Menu
{
	Image bgImage,menu,about,htp1,htp2,forward,back;

	Menu()
	{
		bgImage = ImageLoader.load("menupic.png");
		menu = ImageLoader.load("menu.png");
		about = ImageLoader.load("about.png");
		htp1 =  ImageLoader.load("htp1.png");
		htp2 =  ImageLoader.load("htp2.png");
		back = ImageLoader.load("back.png");
		forward = ImageLoader.load("forward.png");
	}

	public void draw(Graphics g, int k)
	{
		g.drawImage(bgImage,0,0,null);
		if(k==0)
			g.drawImage(menu,200,0,null);
		else if(k==2)
			g.drawImage(about,200,50,null);
		else if(k==3)
		{
			g.drawImage(htp1,200,50,null);
			g.drawImage(forward,200,750,null);
		}
		else if(k==4)
		{
			g.drawImage(htp2,200,50,null);
			g.drawImage(back,200,750,null);
		}

	}

	public int checkMouse(int x, int y, int k)
	{
		if(k==0)
		{
			if(x>=301&&y>=441&&x<=(301+98)&&y<=(441+31))
				return 1;
			else if(x>=285&&y>=543&&x<=(285+129)&&y<=(543+31))
				return 2;
			else if(x>=218&&y>=644&&x<=(218+264)&&y<=(644+31))
				return 3;
			else if(x>=305&&y>=755&&x<=(305+90)&&y<=(755+37))
				return -1;
		}
		else if(k==2)
		{
			if(x>=1055 && y>=661 &&x<=(1055+195) && y<=(661+136))
				return 0;
		}
		else if(k==3)
		{
			if(x>=1043 && y>=608 &&x<=(1043+195) && y<=(608+136))
				return 0;
			if(x>=200 && y>=750 && x<=300 && y<=850)
				return 4;
		}
		else if(k==4)
		{
			if(x>=1043 && y>=608 &&x<=(1043+195) && y<=(608+136))
				return 0;
			if(x>=200 && y>=750 && x<=300 && y<=850)
				return 3;
		}
		return -2;
	}
}