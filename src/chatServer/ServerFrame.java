package chatServer;
//Version 1.0.0
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.DefaultCaret;

@SuppressWarnings("serial")
public class ServerFrame extends JFrame {

	private ArrayList<PrintWriter> clientOutputStreams;
    private ArrayList<String> onlineUsers = new ArrayList<>();
    private ArrayList<Socket> clientSockList = new ArrayList<>();
    private ArrayList<Integer> portList = new ArrayList<>();
    private JPanel contentPane;
	private JTextArea textField;
	private JTextField inputField;
    
	public JTextArea getTextField(){return textField;}
	public ArrayList<String> getOnlineUsers(){return onlineUsers;}
	public ArrayList<PrintWriter> getCOS(){return clientOutputStreams;}
	public ArrayList<Integer> getPortList(){return portList;}
	public ArrayList<Socket> getClientSockList(){return clientSockList;}

	public void setCOS(ArrayList<PrintWriter> COS){clientOutputStreams = COS;}
	
	public void pop(String s){
		textField.append(s + "\n");
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new ServerFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				new TellAll(ServerFrame.this).tellEveryone("Admin`Wy³¹czanie servera...`Chat");
				new TellAll(ServerFrame.this).tellEveryone("Admin`Server zost¹³ wy³¹czony.`Chat");
				System.exit(0);
			}
		});
		setTitle("Poke-MultiChat SERVER");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 490, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);		
		
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 5, 462, 293);
		contentPane.add(scrollPane);
		textField = new JTextArea();
		scrollPane.setViewportView(textField);
		textField.setBorder(new LineBorder(Color.LIGHT_GRAY));
		textField.setLineWrap(true);
		DefaultCaret caret = (DefaultCaret)textField.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textField.setEditable(false);
		textField.setBackground(Color.ORANGE);
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 15));	
		textField.setForeground(Color.BLUE);
		inputField = new JTextField();
		inputField.setBorder(new LineBorder(Color.GRAY));
		inputField.setBounds(69, 309, 176, 25);
		contentPane.add(inputField);
		inputField.setColumns(10);
		
		JButton btnKick = new JButton("Wyrzuæ");
		btnKick.setIcon(new ImageIcon("C:\\projects\\newChat\\ChatApp\\icons\\34.gif"));		
		btnKick.setForeground(Color.WHITE);
		btnKick.setBorder(null);
		btnKick.setBackground(new Color(255,0,0));
		btnKick.setFont(new Font("Times New Roman", Font.PLAIN, 13));		
		btnKick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = inputField.getText();
				new TellAll(ServerFrame.this).tellEveryone(username + "`zosta³ wyrzucony`Wyrzuæ");
				inputField.setText("");
			}
		});
		btnKick.setBounds(255, 309, 89, 25);
		contentPane.add(btnKick);
		
		JLabel lblUsername = new JLabel(" Nazwa:");
		lblUsername.setOpaque(true);
		lblUsername.setBackground(new Color(255, 0, 0));
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblUsername.setBounds(6, 309, 53, 25);
		contentPane.add(lblUsername);
		
		JLabel lbAbout = new JLabel("Autor");
		lbAbout.setIcon(new ImageIcon("C:\\projects\\newChat\\ChatApp\\icons\\41.gif"));
		
		lbAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(null, 
						"by Dominika Wojtaszewska,\n Uniwersytet Przyrodniczo-Humanistyczny w Siedlcach \n Instytut Informatyki \n Projekt z przedmiotu Technologie Sieciowe \n nr 3 - J Chat: \n - wysy³anie wiadomoœci priv \n - emotikony \n - wysy³anie pliku", 
						"Autor",JOptionPane.PLAIN_MESSAGE);
			}
		});
		lbAbout.setOpaque(true);
		lbAbout.setForeground(Color.WHITE);
		lbAbout.setHorizontalAlignment(SwingConstants.CENTER);
		lbAbout.setBounds(367, 309, 70, 25);
		lbAbout.setBackground(new Color(255,0,0));
		lbAbout.setFont(new Font("Times New Roman", Font.PLAIN, 13));		
		contentPane.add(lbAbout);
		portList.add(8888);
	
	
	}

}
