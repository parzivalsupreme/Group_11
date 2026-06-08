package group11_Project.ui;

import java.awt.EventQueue;
import group11_Project.data.AppData; // New

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private AppData data; // New

	// Launch the application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.put("TabbedPane.focus", new Color(0, 0, 0, 0));
		            UIManager.put("TabbedPane.borderHightlightColor", new Color(0, 0, 0, 0));
		            UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
					LoginForm frame = new LoginForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Create the frame
	public LoginForm() {
		data = new AppData(); // New
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 364);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Blessie Apparel & Cosmetics");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 40));
		lblTitle.setBounds(64, 41, 574, 50);
		contentPane.add(lblTitle);
		
		JLabel lblSubTitle = new JLabel("Group 11");
		lblSubTitle.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSubTitle.setBounds(305, 91, 82, 17);
		contentPane.add(lblSubTitle);
		
		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 11));
		lblUsername.setBounds(212, 137, 111, 14);
		contentPane.add(lblUsername);
		
		JLabel lblNewLabel = new JLabel("PASSWORD");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel.setBounds(212, 195, 125, 14);
		contentPane.add(lblNewLabel);
		
		usernameField = new JTextField();
		usernameField.setHorizontalAlignment(SwingConstants.LEFT);
		usernameField.setBounds(212, 157, 251, 27);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setBounds(212, 216, 251, 27);
		contentPane.add(passwordField);
		
		JButton btnLogin = new JButton("login");
		btnLogin.setFocusable(false);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnLogin) {
		            String username = usernameField.getText();
		            String password = String.valueOf(passwordField.getPassword());

		            boolean found = false; // New (Start here)
		            for(String[] user : data.getUsers()) {
		                if(user[1].equals(username)
		                && user[2].equals(password)) {
		                    found = true;
		                    data.setCurrentUser(user[0]);
		                    data.setCurrentRole(user[3]);
		                    break;
		                }
		            }
		            if(found) {
		                JOptionPane.showMessageDialog(LoginForm.this, "Login successful");
		                dispose();
		                Menu menu = new Menu(data);
		                menu.setVisible(true);
		            } else {
		                JOptionPane.showMessageDialog(LoginForm.this, "Invalid username or password");
		            } // end here
		        }
			}
		});
		btnLogin.setBackground(new Color(0, 0, 0));
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBounds(300, 266, 73, 38);
		contentPane.add(btnLogin);

		
	}
}
