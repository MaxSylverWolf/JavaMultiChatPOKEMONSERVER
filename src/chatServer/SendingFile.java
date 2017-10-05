package chatServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SendingFile extends Thread{
	DataOutputStream dos;
	DataInputStream in;
	long fileSize;
	Integer sock;
	ServerSocket serverSock;
	Socket sock1, sock2;
	ServerFrame sf;
	public SendingFile(ServerFrame sf, DataOutputStream dos, DataInputStream in, long fileSize, String sock, ServerSocket serverSock, Socket sock1, Socket sock2){
		this.dos =dos;
		this.in=in;
		this.fileSize=fileSize;
		this.sock=Integer.parseInt(sock);
		this.serverSock=serverSock;
		this.sock1=sock1;
		this.sock2=sock2;
		this.sf = sf;
	}
	public void run(){
		byte[] bytes = new byte[16384];
		int n;
	    try {
	    	
			while((fileSize > 0) && (n = in.read(bytes, 0, (int)Math.min(bytes.length, fileSize))) != -1){
						try {
								dos.write(bytes,0,n);
						    	  dos.flush();
						    	  fileSize-=n;
						}
						catch (Exception ex) {
							sf.pop("B³¹d wysy³ania bitów.");
							break;
						}
				}
			if(fileSize==0) sf.pop("Wysy³anie pliku zakoñczone.");
			else {
				sf.pop("Wysy³anie pliku zosta³o odrzucone.");
				sock1.close();
				sock2.close();
			}
			sf.getPortList().remove(sf.getPortList().indexOf(sock));
			serverSock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
