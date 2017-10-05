package chatServer;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Go {
	ServerFrame sf;
	public Go(ServerFrame sf){
		this.sf=sf;
	}
	public void go(){
		sf.setCOS(new ArrayList<PrintWriter>());

		try {
			@SuppressWarnings("resource")
			ServerSocket serverSock = new ServerSocket(8888);
			while(true){
				Socket clientSock = serverSock.accept();
				sf.getClientSockList().add(clientSock);
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				sf.getCOS().add(writer);
				Thread listener = new Thread(new ClientHandler(sf, clientSock, writer));
				listener.start();
				sf.pop("Nawi¹zano po³¹czenie!");
			}
		}
		catch (Exception ex){
			sf.pop("B³¹d przy próbie po³¹czenia.");
		}
	}
}
