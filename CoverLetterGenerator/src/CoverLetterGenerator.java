import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class CoverLetterGenerator {

	private String copy = "";
	private Clipboard clip; 
	private Transferable clipTf; 
	private Data data;
	
	
	public CoverLetterGenerator(){
		this.clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipTf = clip.getContents(null);
		if (clipTf != null){
			if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)){
				try{
					copy = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		data = new Data(copy);
	}
	
	public Data getData(){
		return this.data;
	}
	
}
