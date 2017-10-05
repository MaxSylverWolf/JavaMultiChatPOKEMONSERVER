package chatServer;

import java.io.DataInputStream;

public class InTest extends Thread{
	DataInputStream in;
	Boolean execute = true;
	public InTest(DataInputStream in){
		this.in=in;
	}
	public void run(){
		while(execute){
			try {
				int b = in.read();
				if(b==7){
					execute=false;
				}
			} catch (Exception e) {
			}
		}
	}
	public void stopMe(){
		execute=false;
	}
}