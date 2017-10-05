package chatServer;

import java.io.PrintWriter;

public class TellAll {
	ServerFrame sf;
	public TellAll(ServerFrame sf){
		this.sf=sf;
	}
	public void tellEveryone(String message) {
    	for(PrintWriter writer:sf.getCOS()){
			try {
				writer.println(message);
				sf.pop("[ADMIN]: " + message);
                writer.flush();
			}
			catch (Exception ex) {
				sf.pop("B��d wys�ania wiadomo��i do wszystkich");
			}
    	}
    }
}
