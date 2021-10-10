package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ScrollPaneUI;

import Class.Constant;
import chatRmi.ChatClient;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;

public class GuiClient extends JFrame implements ActionListener, FocusListener, KeyListener {

	private JPanel contentPane;
	private Vector<String> listData = new Vector<String>();

	private Vector<String> listData_Send = new Vector<String>();
	private JButton btnSendMail ; 
	JList list ; 
	String nameSend = ""; 
	private JTextField txtMess;

	
	ChatClient client ; 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String s  = JOptionPane.showInputDialog(null, "Moi ban nhap ten: ") ; 

					GuiClient frame = new GuiClient(s);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuiClient(String nameSend) {
		this.nameSend = nameSend ; 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 668, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
	
		try {// set icon giao dien---------------------------
			Image iconmes = ImageIO.read(new File( new Constant().LINK_PATH_IMAGE + "logoMess.png"));
			this.setIconImage(iconmes); 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		
		}
		
		 list = new JList(listData);
	

		list.updateUI();
		list.setCellRenderer(new ContentCell());
		
		
		
		JScrollPane scroll  = new JScrollPane(list);
	
		scroll.setPreferredSize(getPreferredSize());
		scroll.createVerticalScrollBar();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scroll, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.NORTH);
		
		JLabel lbImageUser = new JLabel("");
		try {
			BufferedImage bufferImage_user = ImageIO.read(new File(new Constant().LINK_PATH_IMAGE+ "logoUser.png"));
			ImageIcon imageIcon_user = new ImageIcon(bufferImage_user.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			lbImageUser.setIcon(imageIcon_user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		panel_2.add(lbImageUser);

		JLabel lblUser = new JLabel("Phan van");
		lblUser.setText(this.nameSend);
		panel_2.add(lblUser);
		
		JPanel panel_controlSend = new JPanel();
		contentPane.add(panel_controlSend, BorderLayout.SOUTH);
		panel_controlSend.setLayout(new BorderLayout(0, 0));
		
		txtMess = new JTextField();
		panel_controlSend.add(txtMess, BorderLayout.CENTER);
		txtMess.setColumns(10);
		txtMess.addKeyListener(this);
		
		btnSendMail = new JButton("Send");
		panel_controlSend.add(btnSendMail, BorderLayout.EAST);
		btnSendMail.addActionListener(this);
		InitGui();
		 client = new ChatClient(nameSend) ; 

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					// nhận tin 
					String s = client.ClientReceive();
					if(s != null ) {
						listData.add(s) ; 
						list.updateUI() ; 
					}
					
					
				}
			}
		}).start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource().equals(btnSendMail)) {
			// gủi tin nhắn
			String s = txtMess.getText() ; 
			client.sendMess(s); 
			listData.add("Me: " + s) ; 
			list.updateUI();  
			txtMess.setText("");
		}
	
	}
	public void InitGui() {
		listData.removeAllElements();

		list.updateUI();

	}
	public void getMessSend() {
		listData.removeAllElements();
		// nhận tin nhắn và cập nhật lại giao diện 
			list.updateUI();
	
	}
	// get thời gian hiện tại 
	public String getDateTime() {
		String d = String.valueOf( java.time.LocalDate.now());
		String h = String.valueOf(java.time.LocalTime.now());
		String[] timeArr = h.split("\\.");
		System.out.print(h);
		System.out.println(timeArr.length);
		return d + " " + timeArr[0] ; 
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == Event.ENTER) {
			// gủi tin nhắn
						String s = txtMess.getText() ; 
						client.sendMess(s); 
						listData.add("Me: " + s) ; 
						list.updateUI();  
						txtMess.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}


class ContentCell implements ListCellRenderer{

	@Override
	public JPanel getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,boolean cellHasFocus) {
		// TODO Auto-generated method stub
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JLabel text = new JLabel(value.toString()); 
		panel.add(text, BorderLayout.CENTER); 
		return panel;
	}
	
	
	
}




