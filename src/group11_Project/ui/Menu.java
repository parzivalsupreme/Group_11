package group11_Project.ui;

import group11_Project.data.AppData;
import group11_Project.ui.panels.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.*;

public class Menu extends JFrame {

    private static final long serialVersionUID = 1L;

    private final AppData data = new AppData();
    private JPanel contentPane;
    private JTabbedPane tabbedPane;

    private JLabel navDashboard, navLedger, navReports, navUsers;

    private DashboardPanel dashboardPanel;
    private LedgerPanel ledgerPanel;
    private ReportsPanel reportsPanel;
    private UsersPanel usersPanel;

    public Menu() {
        setTitle("Blessie Apparel & Cosmetics");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(80, 60, 1100, 720);
        setLocationRelativeTo(null);

        contentPane = new JPanel(null);
        contentPane.setBackground(Theme.BG);
        setContentPane(contentPane);

        Runnable onDataChanged = this::refreshAllPanels;

        dashboardPanel = new DashboardPanel(data);
        ledgerPanel    = new LedgerPanel(data, this, onDataChanged);
        reportsPanel   = new ReportsPanel(data);
        usersPanel     = new UsersPanel(data, this);

        buildTopBar();
        buildSidebar();
        buildTabbedPane();

        refreshAllPanels();
    }

    private void refreshAllPanels() {
        dashboardPanel.refresh();
        ledgerPanel.refresh();
        reportsPanel.refresh();
        usersPanel.refresh();
    }

    private void buildTopBar() {
        JPanel topBar = new JPanel(null);
        topBar.setBackground(Theme.TOPBAR);
        topBar.setBorder(new MatteBorder(0, 0, 1, 0, Theme.BORDER));
        topBar.setBounds(0, 0, 1100, 46);
        contentPane.add(topBar);

        JLabel hamburger = new JLabel("≡");
        hamburger.setFont(new Font("Arial", Font.BOLD, 20));
        hamburger.setForeground(Theme.TEXT);
        hamburger.setBounds(14, 10, 30, 26);
        topBar.add(hamburger);

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
        JLabel dateLabel = new JLabel(date);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        dateLabel.setForeground(Theme.MUTED);
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dateLabel.setBounds(700, 14, 380, 16);
        topBar.add(dateLabel);
    }

    private void buildSidebar() {
        JPanel sidebar = new JPanel(null);
        sidebar.setBackground(Theme.SIDEBAR);
        sidebar.setBorder(new MatteBorder(0, 0, 0, 1, Theme.BORDER));
        sidebar.setBounds(0, 46, 180, 674);
        contentPane.add(sidebar);

        navDashboard = makeNavItem("\uD83C\uDFE0  DASHBOARD",  10, true);
        navLedger    = makeNavItem("\uD83D\uDDD2  LEDGER",     58, false);
        navReports   = makeNavItem("\uD83D\uDCCA  REPORTS",   106, false);
        navUsers     = makeNavItem("\uD83D\uDC64  USERS",     154, false);

        sidebar.add(navDashboard);
        sidebar.add(navLedger);
        sidebar.add(navReports);
        sidebar.add(navUsers);

        JSeparator sep = new JSeparator();
        sep.setForeground(Theme.BORDER);
        sep.setBounds(10, 210, 160, 2);
        sidebar.add(sep);

        JLabel navSettings = makeNavItem("  SETTINGS", 230, false);
        navSettings.setForeground(new Color(180, 180, 180));
        navSettings.setCursor(Cursor.getDefaultCursor());
        sidebar.add(navSettings);

        JLabel navSupport = makeNavItem("  SUPPORT", 278, false);
        navSupport.setForeground(new Color(180, 180, 180));
        navSupport.setCursor(Cursor.getDefaultCursor());
        sidebar.add(navSupport);

        addNavClick(navDashboard, 0);
        addNavClick(navLedger,    1);
        addNavClick(navReports,   2);
        addNavClick(navUsers,     3);

        JButton btnLogout = new JButton("logout");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 11));
        btnLogout.setBackground(new Color(26, 26, 26));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.setBounds(40, 614, 100, 30);
        btnLogout.addActionListener(e -> {
            dispose();
            LoginForm login = new LoginForm();
            login.setVisible(true);
        });
        sidebar.add(btnLogout);
    }

    private JLabel makeNavItem(String text, int y, boolean active) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.PLAIN, 11));
        lbl.setForeground(active ? Theme.TEXT : Theme.MUTED);
        lbl.setBackground(active ? Theme.SIDEBAR_ACTIVE : Theme.SIDEBAR);
        lbl.setOpaque(true);
        lbl.setHorizontalAlignment(SwingConstants.LEFT);
        lbl.setBorder(new EmptyBorder(0, 16, 0, 0));
        lbl.setBounds(0, y, 180, 44);
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return lbl;
    }

    private void addNavClick(JLabel lbl, int tabIndex) {
        lbl.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                tabbedPane.setSelectedIndex(tabIndex);
                setActiveNav(lbl);
            }
            @Override public void mouseEntered(MouseEvent e) {
                if (tabbedPane.getSelectedIndex() != tabIndex)
                    lbl.setBackground(new Color(230, 230, 230));
            }
            @Override public void mouseExited(MouseEvent e) {
                if (tabbedPane.getSelectedIndex() != tabIndex)
                    lbl.setBackground(Theme.SIDEBAR);
            }
        });
    }

    private void setActiveNav(JLabel active) {
        JLabel[] all = {navDashboard, navLedger, navReports, navUsers};
        for (JLabel nav : all) {
            boolean isActive = nav == active;
            nav.setForeground(isActive ? Theme.TEXT : Theme.MUTED);
            nav.setBackground(isActive ? Theme.SIDEBAR_ACTIVE : Theme.SIDEBAR);
        }
    }

    private void buildTabbedPane() {
        tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setBounds(180, 46, 920, 674);
        tabbedPane.setBackground(Theme.BG);
        tabbedPane.setBorder(null);

        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override protected int calculateTabAreaHeight(int p, int r, int m) { return 0; }
        });

        tabbedPane.addTab("Dashboard", dashboardPanel);
        tabbedPane.addTab("Ledger",    ledgerPanel);
        tabbedPane.addTab("Reports",   reportsPanel);
        tabbedPane.addTab("Users",     usersPanel);

        tabbedPane.addChangeListener(e -> {
            int i = tabbedPane.getSelectedIndex();
            JLabel[] navs = {navDashboard, navLedger, navReports, navUsers};
            if (i >= 0 && i < navs.length) setActiveNav(navs[i]);
            if (i == 0) dashboardPanel.refresh();
            if (i == 2) reportsPanel.refresh();
        });

        contentPane.add(tabbedPane);
    }
}
