package chatServer;



class ChatServerMain {
    public static void main(String[] args){
    	ServerFrame m = new ServerFrame();
    	m.setVisible(true);
    	m.pop("Server wystartował...");
    	new Go(m).go();
    }
}