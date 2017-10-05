package chatServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PortGo {
	ServerFrame sf;
	public PortGo(ServerFrame sf){this.sf=sf;}
	public void portGo(String sock, String size, PrintWriter writer, String data0, String data1, String data2, String data4, String data5){
		try {
			ServerSocket serverSock = new ServerSocket(Integer.parseInt(sock));
			DataInputStream[] in = {null, null};
			DataOutputStream[] dos = {null, null};
			Socket clieSock[] = {null, null};
			DataInputStream din = null;
			DataOutputStream ddos = null;
			int con = 0;
			writer.println(data0 + "`" + data1 + "`" + data2 + "`" + data4 + "`" + data5);
		    writer.flush();
			while(true){
				Socket clientSock = serverSock.accept();
				dos[con] = new DataOutputStream(clientSock.getOutputStream());
				in[con] = new DataInputStream(clientSock.getInputStream());
				clieSock[con]=clientSock;
				con++;
				if(con==2){
				    break;
				}
			}
			InTest test1 = new InTest(in[0]);
			test1.start();
			InTest test2 = new InTest(in[1]);
			test2.start();
			while(true){
				if(test1.isAlive()==false){
					test2.stopMe();
					din=in[0];
					ddos=dos[1];
					break;
				}
				if(test2.isAlive()==false){
					test1.stopMe();
					din=in[1];
					ddos=dos[0];
					break;
				}
			}
			long fileSize = Long.parseLong(size);
		    Thread sending = new SendingFile(sf, ddos, din, fileSize, sock, serverSock, clieSock[0], clieSock[1]);
		    sending.start();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			sf.pop("B³¹d przy próbie po³¹czenia.");
		}
	}
}
