import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

class ImageLoader
{
	public static BufferedImage load(String filename)
	{
		BufferedImage img = null;
		try{
			img = ImageIO.read(new File("images/"+filename));
		}
		catch(IOException e){System.out.println("File not available");}

		return img;
	}

}