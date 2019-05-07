import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Panel extends JPanel implements Runnable
{
	private static int PWIDTH = 1600;
	private static int PHEIGHT = 1000;

	private Thread animator;
	private volatile boolean running = false;

	private volatile int state = 0;
	private Menu menu;
	private Outside outside;
	private Camp camp;
	private Healing healing;
	private Graphics dbg;
	private Image dbImage = null;

	Panel(int w, int h)
	{
		setPreferredSize(new Dimension(PWIDTH,PHEIGHT));

		setFocusable(true);
		requestFocus();

		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e)
			{mousePress(e.getX(), e.getY());}
		});

		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{int keyCode = e.getKeyCode(); keyPress(keyCode);}
		});

		menu = new Menu();
		outside = new Outside();
		camp = new Camp();
		healing = new Healing();
	}

	public void mousePress(int x, int y)
	{
		//Menu
		if(state%10==0)
		{
			int a = menu.checkMouse(x,y,state/10);
			if(a==-1)
				state=-1;
			else if(a==1)
				state = 1;
			else if(a==2)
				state = 20;
			else if(a==3)
				state = 30;
			else if(a==4)
				state = 40;
			else if(a==0)
				state = 0;
		}
		//Outside
		else if(state%10==1)
		{
			int a = outside.checkPress(x,y);
			if(a==1)
				state=2;
			else
			{
				Soldier sol = outside.checkMouse(x,y,camp.hasBed());
				if(sol!=null)
				{
					state = 12;
					camp.callMenu(sol);
				}
			}
		}
		//Camp
		else if(state%10==2)
		{
			int a = camp.checkPress(x,y,state/10);
			if(a==1)
				state = 2;
			else if(a==2)
				state = 1;
			else if(a==3)
				state = 3;
			else if(a%10==4)
				state = (a-4)*10 + 13;
		}
		//healing
		else if(state%10==3)
		{
			int a = healing.checkPress(x,y,state/10);
			if(a==1)
				state = 2;
			else if(a==2)
				state = 3;
		}
	}

	public void keyPress(int keyCode)
	{
		if(keyCode == KeyEvent.VK_Q)
			running=false;
	}

	public void addNotify()
	{
		super.addNotify();
		startGame();
	}

	private void startGame()
	{
		if(animator==null||!running)
		{
			animator = new Thread(this);
			animator.start();
		}
	}

	public void run()
	{
		running=true;
		while(running)
		{
			gameUpdate();
			gameRender();
			paintScreen();
			try
			{
				Thread.sleep(5);
			}
			catch(Exception e)
			{}
		}
		System.exit(0);
	}

	private void gameUpdate()
	{
		if(state==-1)
			running=false;
		if(state!=0)
		{
			outside.update();
			ArrayList<Integer> t = healing.update();
			camp.update(t);
		}
		
	}

	private void gameRender()
	{
		if(dbImage==null)
		{
			dbImage = createImage(PWIDTH,PHEIGHT);
			dbg = dbImage.getGraphics();
		}

		dbg.setColor(Color.white);
		dbg.fillRect (0, 0, PWIDTH, PHEIGHT);

		if(state%10==0)
			menu.draw(dbg,state/10);
		else if(state%10==1)
			outside.draw(dbg);
		else if(state%10==2)
			camp.draw(dbg,state/10);
		else if(state%10==3)
			healing.draw(dbg,state/10);
	}

	private void paintScreen()
	{
		Graphics g;
		try
		{
			g = this.getGraphics();
			if((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, null);
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
		catch(Exception e){}
	}
}