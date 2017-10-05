package chatServer;

public class UserRemove {
		ServerFrame sf;
	public UserRemove(ServerFrame sf){
		this.sf=sf;
	}
	public void userRemove (String data) {
        String message;
        String add = "` `Connect", done = "Server` `Done";
        sf.getOnlineUsers().remove(data);
        String[] tempList = new String[(sf.getOnlineUsers().size())];
        sf.getOnlineUsers().toArray(tempList);

        for (String token:tempList) {

            message = (token + add);
            new TellAll(sf).tellEveryone(message);
        }
        new TellAll(sf).tellEveryone(done);
}
}
