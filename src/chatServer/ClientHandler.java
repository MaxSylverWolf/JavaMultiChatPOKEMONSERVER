package chatServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable	{
	BufferedReader reader;
	Socket sock;
    PrintWriter client;       
    ServerFrame sf;
    
	public ClientHandler(ServerFrame sf, Socket clientSocket, PrintWriter user) {
        client = user;
        this.sf = sf;
		try {
			sock = clientSocket;
			this.sf.getTextField().append(clientSocket.getRemoteSocketAddress().toString() + " - ");
			InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(isReader);
		}
		catch (Exception ex) {
			this.sf.pop("B³¹d uruchomienia StreamReader");
		}

	}

	public void run() {
                    String message;
		String[] data;
                    String connect = "Connect";
                    String disconnect = "Disconnect";
                    String chat = "Chat";

		try {
			while ((message = reader.readLine()) != null) {

				data = message.split("`");
				
                 if (data[2].equals(connect)) {
                                    	
                	 new TellAll(sf).tellEveryone((data[0] + "`" + data[1] + "`" + chat));
                     sf.pop(data[0] + " " + data[1]);
                     new UserAdd(sf).userAdd(data[0]);
                                    	

				} else if (data[2].equals(disconnect)) {

                     
                     sf.pop(data[0] + " wylogowa³ siê.");
                     sf.getCOS().remove(client);
                     client.close();
                     new UserRemove(sf).userRemove(data[0]);
                     new TellAll(sf).tellEveryone((data[0] + "`wylogowa³ siê." + "`" + chat));
                     break;

				} else if (data[2].equals(chat)) {
					if(data[1].contains("#")){
						data[1].replaceAll("# ", "#");
						String[] s = data[1].split("#");
						new TellAll(sf).tellEveryone(data[0] + "`" + s[1] + "`Private`" + s[0]);
					}else new TellAll(sf).tellEveryone(message);

				}else if (data[2].equals("connecto")) {

					String add = "` `Connect", done = "Server` `Done";
	                String[] tempList = new String[(sf.getOnlineUsers().size())];
	                sf.getOnlineUsers().toArray(tempList);
	                
	                for (String token:tempList) {
	                    
	                    message = (token + add);
	                    new TellAll(sf).tellEveryone(message);
	                }
	                new TellAll(sf).tellEveryone(done);
	                new TellAll(sf).tellEveryone(data[0] + "``bool");
	                
				}else if(data[2].equals("fileDecide")){
					new TellAll(sf).tellEveryone(data[0] + "`" + data[1] + "`" + data[2] + "`" + data[3]);
				}else if(data[2].equals("fileDecideYes")){
					int num0 = 0, num=-1;
				    for(String s:sf.getOnlineUsers()){
				    	if(s.equals(data[0])) num = num0;
				    	num0++;
				    		}
				    PrintWriter writer = sf.getCOS().get(num);
					writer.println(data[0] + "`" + data[1] + "`" + data[2] + "`" + data[3] + "`" + (sf.getPortList().get(sf.getPortList().size()-1)+1) + "`" + data[4]);
					writer.flush();
					sf.getPortList().add(sf.getPortList().get(sf.getPortList().size()-1)+1);
				}else if(data[2].equals("fileDecideNo")){
					sf.pop("fileDecideNo");
					int num0 = 0, num=-1;
				    for(String s:sf.getOnlineUsers()){
				    	if(s.equals(data[0])) num = num0;
				    	num0++;
				    		}
				    PrintWriter writer = sf.getCOS().get(num);
					writer.println(data[0] + "`" + data[1] + "`fileDecideNo`" + data[3]);
					writer.flush();
					sf.getPortList().add(sf.getPortList().get(sf.getPortList().size()-1)+1);
				}else if(data[2].equals("receiveFile")){
				    int num0 = 0, num=-1;
				    for(String s:sf.getOnlineUsers()){
				    	if(s.equals(data[3])) num = num0;
				    	num0++;
				    		}
				    PrintWriter writer = sf.getCOS().get(num);
				    new PortGo(sf).portGo(data[4], data[1], writer, data[0], data[1], data[2], data[4], data[5]);
				}else {
					sf.pop("Brak obs³ugi opcji.");
                  }


		     }
		}
		catch (Exception ex) {
			sf.pop("Utracono po³¹czenie");
			ex.printStackTrace();
			sf.pop("haha" + ex);
			sf.getCOS().remove(client);
		}
	}
}