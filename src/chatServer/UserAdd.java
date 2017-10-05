package chatServer;

public class UserAdd {
	ServerFrame sf;
	public UserAdd(ServerFrame sf){
		this.sf=sf;
	}
	public void userAdd (String data) {
        String message;
        sf.getOnlineUsers().add(data);
        String add = "` `Connect", done = "Server` `Done";
        String[] tempList = new String[(sf.getOnlineUsers().size())];
        sf.getOnlineUsers().toArray(tempList);
        
        for (String token:tempList) {
            
            message = (token + add);
            new TellAll(sf).tellEveryone(message);
        }
        new TellAll(sf).tellEveryone(done);
}
}
