package clientmod.bnguiclient;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clientmod.bnlogin.BnUdpLogin;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.net.SocketException;
import javax.swing.Action;
/**
 * 
 * @author levymateus
 *
 */
public class BnUdpGuiLogin extends JFrame implements  bnprotocol.BnUdpServerProtocolInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private JTextField txtField_Login;
	
	private JTextField textField_IP;
	 
	private JTextField textField_Port;
		
	private JButton btnLogin;
	
	private final Action actionLogin = new ActionLogin();

	public BnUdpGuiLogin() {
		init();
		this.setVisible(true);
	}
	
	public BnUdpGuiLogin(String nickname, String addres, String port) {
		init();
		this.txtField_Login.setText(nickname);;
		this.textField_IP.setText(addres);
		this.textField_Port.setText(port);
		this.setVisible(true);
	}
	
	private void init(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 236);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(123, 11, 46, 14);
		contentPane.add(lblLogin);
	
                JLabel lblNick = new JLabel("Nick");
		lblNick.setBounds(10, 33, 46, 14);
		contentPane.add(lblNick);
                
		txtField_Login = new JTextField();
		txtField_Login.setBounds(67, 36, 153, 20);
		contentPane.add(txtField_Login);
		txtField_Login.setColumns(10);
		
		JLabel lblIp = new JLabel("IP");
		lblIp.setBounds(10, 79, 46, 14);
		contentPane.add(lblIp);
		
		textField_IP = new JTextField();
		textField_IP.setBounds(67, 76, 153, 20);
		contentPane.add(textField_IP);
		textField_IP.setColumns(10);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(10, 125, 46, 14);
		contentPane.add(lblPort);
		
		textField_Port = new JTextField();
		textField_Port.setBounds(67, 122, 153, 20);
		contentPane.add(textField_Port);
		textField_Port.setColumns(10);
		
		btnLogin = new JButton("Login");
		btnLogin.setAction(actionLogin);
		btnLogin.setBounds(131, 163, 89, 23);
		contentPane.add(btnLogin);
		
		this.setTitle("Login");
	}
	
	private class ActionLogin extends AbstractAction {	
				
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public ActionLogin() {
			putValue(NAME, "Login");
		}
		
		public void actionPerformed(ActionEvent e) {
			
			new Thread(){
				
				public void run(){
					
					try {
						
						String reply = BnUdpLogin.getInstance().loginAttempt(txtField_Login.getText(), textField_IP.getText(), Integer.parseInt(textField_Port.getText()));
						
						switch (reply) {

						case CONNECTION_ACCEPTED:
							BnUdpTelaClienteChat chat = new BnUdpTelaClienteChat();
							chat.setServerIP(textField_IP.getText());
							chat.setNickname(txtField_Login.getText());
							chat.setServerPort(textField_Port.getText());
							chat.setVisible(true);
							dispose();
							return;
							
						case INVALID_LOGIN: 
							JOptionPane.showMessageDialog(null, "Login invalido");
							return;
							
						case BnUdpLogin.TIMED_OUT:
							JOptionPane.showMessageDialog(null, "Tempo limite de resposta excedido\nTente novamente");
							return;
							
						case RUNTIME_ERROR:
							JOptionPane.showMessageDialog(null, "Usuário Inexistente\nTente novamente");
							return;
							
						default:
							JOptionPane.showMessageDialog(null, "Erro interno: " + reply);
							break;
						}
					} catch (SocketException e) {
						System.err.println("Client (GUI) " + e.getMessage() ); 
					}
				}
			}.start();
			
		}
	}
	
}
