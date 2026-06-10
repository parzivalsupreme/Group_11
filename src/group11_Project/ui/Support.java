package group11_Project.ui;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class Support extends JFrame {
	private JPanel contentPane;
	
	public static void main(String[] args) {
	    LoginForm.main(args);
	}
	
	public Support() {
		setTitle("Support");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose instead of exiting the entire app
        setBounds(80, 60, 1100, 720);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel(null);
        contentPane.setBackground(Theme.BG);
        setContentPane(contentPane);
        
        buildTopBar();
        buildFAQSection();
	}
	
	private void buildTopBar() {
        JPanel topBar = new JPanel(null);
        topBar.setBackground(Theme.TOPBAR);
        topBar.setBorder(new MatteBorder(0, 0, 1, 0, Theme.BORDER));
        topBar.setBounds(0, 0, 1100, 150);
        contentPane.add(topBar);
        
        JLabel hamburger = new JLabel("Frequently Asked Questions");
        hamburger.setFont(new Font("Arial", Font.BOLD, 40));
        hamburger.setForeground(new Color(140, 140, 140));
        hamburger.setBounds(270, -20, 600, 200);
        topBar.add(hamburger);
    }
	
	private void buildFAQSection() {

	    JTextPane faqPane = new JTextPane();
	    faqPane.setEditable(false);
	    faqPane.setBackground(Theme.BG);
	    faqPane.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));

	    javax.swing.text.StyledDocument doc = faqPane.getStyledDocument();

	    javax.swing.text.Style questionStyle =
	            faqPane.addStyle("Question", null);
	    javax.swing.text.StyleConstants.setBold(questionStyle, true);
	    javax.swing.text.StyleConstants.setFontSize(questionStyle, 16);

	    javax.swing.text.Style answerStyle =
	            faqPane.addStyle("Answer", null);
	    javax.swing.text.StyleConstants.setFontSize(answerStyle, 14);

	    try {
	        // Question 1
	        doc.insertString(doc.getLength(),
	                "How do I add a ledger entry?\n",
	                questionStyle);

	        doc.insertString(doc.getLength(),
	                "Navigate to the Ledger tab and click the '+' button. "
	              + "Fill in the item name, quantity, amount, payment method, "
	              + "type, status, and date. Click Save to add the entry.\n\n",
	                answerStyle);

	        // Question 2
	        doc.insertString(doc.getLength(),
	                "How do I edit a ledger entry?\n",
	                questionStyle);

	        doc.insertString(doc.getLength(),
	                "In the Ledger table, click the '...' button located in the "
	              + "last column of the desired row. Modify the information and "
	              + "click Save to update the record.\n\n",
	                answerStyle);

	        // Question 3
	        doc.insertString(doc.getLength(),
	                "How do I delete a ledger entry?\n",
	                questionStyle);

	        doc.insertString(doc.getLength(),
	                "Select a row in the Ledger table and click the trash/delete "
	              + "button (" + "\uD83D\uDDD1\"" + "). Confirm the deletion when prompted.\n\n",
	                answerStyle);

	        // Question 4
	        doc.insertString(doc.getLength(),
	                "How do I generate reports?\n",
	                questionStyle);

	        doc.insertString(doc.getLength(),
	                "Open the Reports tab. Use the available filters to select "
	              + "specific transaction types or statuses. The report totals "
	              + "will automatically update based on your selection.\n\n",
	                answerStyle);

	        // Question 5
	        doc.insertString(doc.getLength(),
	                "How do I add a user?\n",
	                questionStyle);

	        doc.insertString(doc.getLength(),
	                "Go to the Users tab and click '+ Add User'. Enter the "
	              + "user's information, assign a role, and create a password. "
	              + "Click Add User to save the account.\n\n",
	                answerStyle);

	        // Question 6
	        doc.insertString(doc.getLength(),
	                "What is the difference between Admin and Guest accounts?\n",
	                questionStyle);

	        doc.insertString(doc.getLength(),
	                "Administrators have full access to the system, including "
	              + "adding, editing, and deleting records. Guest accounts can "
	              + "view information but cannot modify important data.\n\n",
	                answerStyle);

	        // Question 7
	        doc.insertString(doc.getLength(),
	                "Where is my data stored?\n",
	                questionStyle);

	        doc.insertString(doc.getLength(),
	                "User and ledger information are automatically saved in text "
	              + "files. The data remains available even after the application "
	              + "is closed and reopened.\n\n",
	                answerStyle);

	        // Question 8
	        doc.insertString(doc.getLength(),
	                "What should I do if I cannot log in?\n",
	                questionStyle);

	        doc.insertString(doc.getLength(),
	                "Verify that your username and password are correct. "
	              + "If the issue persists, contact an administrator to verify "
	              + "your account information.\n\n",
	                answerStyle);
	        
	     // Question 9
	        doc.insertString(doc.getLength(),
	        		"Who can modify system data?\n",
	                questionStyle);

	        doc.insertString(doc.getLength(),
	                "Only users with the Administrator role can add, edit, "
	              + "or delete ledger entries and manage user accounts. "
	              + "Guest users can view information but cannot make " 
	              + "changes to the system.\n\n",
	                answerStyle);

	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }

	    JScrollPane scrollPane = new JScrollPane(faqPane);
	    scrollPane.setBounds(40, 180, 1000, 450);
	    scrollPane.setBorder(null);

	    contentPane.add(scrollPane);
	}
}
