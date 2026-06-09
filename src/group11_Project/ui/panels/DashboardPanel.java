package group11_Project.ui.panels;

import group11_Project.data.AppData;
import group11_Project.model.LedgerEntry;
import group11_Project.ui.Theme;
import group11_Project.ui.UiUtils;
import group11_Project.ui.charts.BarChartPanel;
import group11_Project.util.Constants;
import group11_Project.util.FormatUtils;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.*;

public class DashboardPanel extends JPanel {
    private final AppData data;

    private JComboBox<String> cboDashMonth, cboDashYear;
    private JLabel dashTotalSales, dashExpenses, dashBalance, dashSalesCount, dashExpCount;
    private JPanel dashRecentPanel;
    private BarChartPanel barChart;

    public DashboardPanel(AppData data) {
        this.data = data;
        setLayout(null);
        setBackground(Theme.BG);
        build();
    }

    private void build() {
        JPanel userCard = UiUtils.makeCard(20, 16, 300, 90);
        add(userCard);

        JLabel userIcon = new JLabel();
        userIcon.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        userIcon.setForeground(Theme.TEXT);
        userIcon.setBounds(14, 12, 260, 18);
        userCard.add(userIcon);
        userIcon.setText(data.getCurrentUser() + "\uD83D\uDC64"); // New

        JLabel adminLbl = new JLabel();
        adminLbl.setFont(new Font("Arial", Font.PLAIN, 11));
        adminLbl.setForeground(Theme.MUTED);
        adminLbl.setBounds(14, 32, 260, 16);
        userCard.add(adminLbl);
        adminLbl.setText(data.getCurrentRole()); // New

        JPanel monthCard = UiUtils.makeCard(20, 118, 300, 130);
        add(monthCard);

        JLabel monthTitle = new JLabel("Report");
        monthTitle.setFont(new Font("Arial", Font.PLAIN, 10));
        monthTitle.setForeground(Theme.MUTED);
        monthTitle.setBounds(14, 10, 200, 14);
        monthCard.add(monthTitle);

        JLabel monthDisplay = new JLabel("", SwingConstants.CENTER);
        monthDisplay.setFont(new Font("Arial", Font.BOLD, 18));
        monthDisplay.setForeground(Theme.TEXT);
        monthDisplay.setBounds(10, 26, 200, 32);
        monthCard.add(monthDisplay);

        cboDashMonth = UiUtils.makeCombo(Constants.MONTHS);
        cboDashYear  = UiUtils.makeCombo(Constants.YEARS);
        cboDashMonth.setVisible(false);
        cboDashYear.setVisible(false);

        JButton btnUp = UiUtils.makeTinyArrow("▲", 220, 26);
        JButton btnDn = UiUtils.makeTinyArrow("▼", 220, 50);
        monthCard.add(btnUp);
        monthCard.add(btnDn);
        monthCard.add(cboDashMonth);
        monthCard.add(cboDashYear);

        cboDashMonth.addActionListener(e -> { updateMonthDisplay(monthDisplay); refresh(); });
        cboDashYear.addActionListener(e  -> { updateMonthDisplay(monthDisplay); refresh(); });

        btnUp.addActionListener(e -> {
            int mo = cboDashMonth.getSelectedIndex();
            int yr = Integer.parseInt((String) cboDashYear.getSelectedItem());
            mo--;
            if (mo < 0) { mo = 11; yr--; }
            cboDashMonth.setSelectedIndex(mo);
            cboDashYear.setSelectedItem(String.valueOf(yr));
        });
        btnDn.addActionListener(e -> {
            int mo = cboDashMonth.getSelectedIndex();
            int yr = Integer.parseInt((String) cboDashYear.getSelectedItem());
            mo++;
            if (mo > 11) { mo = 0; yr++; }
            cboDashMonth.setSelectedIndex(mo);
            cboDashYear.setSelectedItem(String.valueOf(yr));
        });

        dashTotalSales = buildMetricCard("TOTAL SALES",    "₱0.00",  20, 260, Theme.SUCCESS);
        dashExpenses   = buildMetricCard("TOTAL EXPENSES", "₱0.00", 125, 260, Theme.DANGER); // Change from 230 to 125
        dashBalance    = buildMetricCard("NET BALANCE",    "₱0.00", 230, 260, Theme.TEXT); // Change from 440 to 230

        dashSalesCount = new JLabel("0 transactions");
        dashSalesCount.setFont(new Font("Arial", Font.PLAIN, 10));
        dashSalesCount.setForeground(Theme.MUTED);
        dashSalesCount.setBounds(34, 340, 200, 14);
        add(dashSalesCount);

        dashExpCount = new JLabel("0 items");
        dashExpCount.setFont(new Font("Arial", Font.PLAIN, 10));
        dashExpCount.setForeground(Theme.MUTED);
        dashExpCount.setBounds(244, 340, 200, 14);
        add(dashExpCount);

        JPanel chartCard = UiUtils.makeCard(340, 16, 560, 340);
        add(chartCard);

        barChart = new BarChartPanel(data.getLedger());
        barChart.setBounds(14, 40, 532, 290);
        chartCard.add(barChart);

        JPanel recentCard = UiUtils.makeCard(20, 370, 880, 280);
        add(recentCard);
        UiUtils.addCardTitle(recentCard, "RECENT TRANSACTIONS", 16, 14);
        dashRecentPanel = new JPanel();
        dashRecentPanel.setLayout(new BoxLayout(dashRecentPanel, BoxLayout.Y_AXIS));
        dashRecentPanel.setBackground(Theme.SURFACE);
        JScrollPane scroll = new JScrollPane(dashRecentPanel);
        scroll.setBorder(null);
        scroll.setBounds(1, 36, 878, 238); // New (880 to 878)
        recentCard.add(scroll);

        initMonthYear(monthDisplay);
    }

    public void initMonthYear(JLabel monthDisplay) {
        LocalDate now = LocalDate.now();
        cboDashMonth.setSelectedIndex(now.getMonthValue() - 1);
        cboDashYear.setSelectedItem(String.valueOf(now.getYear()));
        updateMonthDisplay(monthDisplay);
    }

    private void updateMonthDisplay(JLabel lbl) {
        if (cboDashMonth == null || cboDashYear == null) return;
        String mo = (String) cboDashMonth.getSelectedItem();
        String yr = (String) cboDashYear.getSelectedItem();
        lbl.setText(mo.substring(0, 3).toUpperCase() + " " + yr);
    }

    private JLabel buildMetricCard(String label, String value, int x, int y, Color valueColor) {
        JPanel card = UiUtils.makeCard(x, y, 100, 80); // Change from 196 to 100
        add(card);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 9));
        lbl.setForeground(Theme.MUTED);
        lbl.setBounds(12, 10, 172, 14);
        card.add(lbl);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Courier New", Font.BOLD, 12)); // New
        val.setForeground(valueColor);
        val.setBounds(12, 26, 172, 28);
        card.add(val);

        return val;
    }

    public void refresh() {
        List<LedgerEntry> ledgerData = data.getLedger();
        double totalSales = ledgerData.stream()
            .filter(e -> e.getType().equals("Sale"))
            .mapToDouble(LedgerEntry::getTotal).sum();
        double totalExp = ledgerData.stream()
            .filter(e -> e.getType().equals("Expense"))
            .mapToDouble(LedgerEntry::getTotal).sum();
        double bal = totalSales - totalExp;

        dashTotalSales.setText(FormatUtils.fmt(totalSales));
        dashExpenses.setText(FormatUtils.fmt(totalExp));
        dashBalance.setText(FormatUtils.fmt(bal));
        dashBalance.setForeground(bal >= 0 ? Theme.SUCCESS : Theme.DANGER);

        long salesCount = ledgerData.stream().filter(e -> e.getType().equals("Sale")).count();
        long expCount   = ledgerData.stream().filter(e -> e.getType().equals("Expense")).count();
        dashSalesCount.setText(salesCount + " transactions");
        dashExpCount.setText(expCount + " items");

        dashRecentPanel.removeAll();
        List<LedgerEntry> recent = ledgerData.stream()
            .sorted(Comparator.comparing(LedgerEntry::getDatetime).reversed())
            .limit(6)
            .collect(Collectors.toList());

        if (recent.isEmpty()) {
            JLabel none = new JLabel("No transactions yet. Add entries in Ledger.");
            none.setFont(new Font("Arial", Font.ITALIC, 12));
            none.setForeground(Theme.MUTED);
            none.setAlignmentX(Component.CENTER_ALIGNMENT);
            none.setBorder(new EmptyBorder(20, 10, 0, 10));
            dashRecentPanel.add(none);
        } else {
            for (LedgerEntry e : recent) {
                dashRecentPanel.add(buildRecentRow(e));
            }
        }
        dashRecentPanel.revalidate();
        dashRecentPanel.repaint();

        if (barChart != null) {
            int mo = cboDashMonth != null ? cboDashMonth.getSelectedIndex() : LocalDate.now().getMonthValue() - 1;
            int yr = cboDashYear != null ? Integer.parseInt((String) cboDashYear.getSelectedItem()) : LocalDate.now().getYear();
            barChart.setMonthYear(mo, yr);
            barChart.repaint();
        }
    }

    private JPanel buildRecentRow(LedgerEntry e) {
        JPanel row = new JPanel(null);
        row.setBackground(Theme.SURFACE);
        row.setMaximumSize(new Dimension(878, 52)); // New (880 to 878)
        row.setMinimumSize(new Dimension(878, 52));
        row.setPreferredSize(new Dimension(878, 52));
        row.setBorder(new MatteBorder(0, 0, 1, 0, Theme.BORDER));

        JLabel itemLbl = new JLabel(e.getItem());
        itemLbl.setFont(new Font("Arial", Font.BOLD, 12));
        itemLbl.setForeground(Theme.TEXT);
        itemLbl.setBounds(12, 8, 240, 16);
        row.add(itemLbl);

        JLabel dateLbl = new JLabel(e.getDatetime().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
        dateLbl.setFont(new Font("Arial", Font.PLAIN, 10));
        dateLbl.setForeground(Theme.MUTED);
        dateLbl.setBounds(12, 26, 240, 14);
        row.add(dateLbl);

        JLabel amtLbl = new JLabel(FormatUtils.fmt(e.getTotal()));
        amtLbl.setFont(new Font("Courier New", Font.BOLD, 12));
        amtLbl.setForeground(e.getType().equals("Sale") ? Theme.SUCCESS : Theme.DANGER);
        amtLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        amtLbl.setBounds(260, 10, 140, 16);
        row.add(amtLbl);

        JLabel statusLbl = new JLabel(e.getStatus());
        statusLbl.setFont(new Font("Arial", Font.PLAIN, 9));
        statusLbl.setOpaque(true);
        statusLbl.setBackground(e.getStatus().equals("Paid") ? Theme.PAID_BG : Theme.PEND_BG);
        statusLbl.setForeground(e.getStatus().equals("Paid") ? new Color(6, 95, 70) : new Color(146, 64, 14));
        statusLbl.setBorder(new EmptyBorder(2, 6, 2, 6));
        statusLbl.setHorizontalAlignment(SwingConstants.CENTER);
        statusLbl.setBounds(420, 16, 54, 18);
        row.add(statusLbl);

        JLabel dots = new JLabel("•••");
        dots.setFont(new Font("Arial", Font.BOLD, 12));
        dots.setForeground(Theme.MUTED);
        dots.setHorizontalAlignment(SwingConstants.RIGHT);
        dots.setBounds(830, 16, 30, 18);
        row.add(dots);

        return row;
    }
}
