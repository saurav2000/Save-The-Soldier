import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Panel extends JPanel implements Runnable
{
	private static int PWIDTH = 1600;
	private static int PHEIGHT = 1000;

	private Thread animator;
	private volatile boolean running = false;

	private volatile int state = 0;
	private Menu menu;
	private Graphics dbg;
	private Image dbImage = null;

	Panel(int w, int h)
	{
		setPreferredSize(new Dimension(PWIDTH,PHEIGHT));

		setFocusable(true);
		requestFocus();

		addMouseListener( new MouseAdapter( ) {
			public void mousePressed(MouseEvent e)
			{mousePress(e.getX(), e.getY());}
		});

		addKeyListener( new KeyAdapter( ) {
			public void keyPressed(KeyEvent e)
			{int keyCode = e.getKeyCode(); keyPress(keyCode);}
		});

		menu = new Menu();
	}

	public void mousePress(int x, int y)
	{
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
			else if(a==0)
				state = 0;
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