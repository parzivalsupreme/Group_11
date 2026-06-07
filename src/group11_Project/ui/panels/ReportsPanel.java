package group11_Project.ui.panels;

import group11_Project.data.AppData;
import group11_Project.model.LedgerEntry;
import group11_Project.ui.Theme;
import group11_Project.ui.UiUtils;
import group11_Project.ui.charts.ReportChartPanel;
import group11_Project.util.Constants;
import group11_Project.util.FormatUtils;
import java.awt.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.*;

public class ReportsPanel extends JPanel {
    private final AppData data;

    private JLabel repBalance, repExp, repRent, repTotal;
    private JPanel repSalesPanel;
    private JComboBox<String> cboRepMonth, cboRepYear;
    private ReportChartPanel repChart;
    private JRadioButton rSales; // New
    private JRadioButton rExp; // New

    public ReportsPanel(AppData data) {
        this.data = data;
        setLayout(null);
        setBackground(Theme.BG);
        build();
    }

    private void build() {
        JLabel monthDisplay = new JLabel("", SwingConstants.CENTER);
        monthDisplay.setFont(new Font("Arial", Font.BOLD, 20));
        monthDisplay.setForeground(Theme.TEXT);
        monthDisplay.setBorder(new LineBorder(Theme.BORDER, 1));
        monthDisplay.setBackground(Theme.SURFACE);
        monthDisplay.setOpaque(true);
        monthDisplay.setBounds(330, 14, 220, 40);
        add(monthDisplay);

        cboRepMonth = UiUtils.makeCombo(Constants.MONTHS);
        cboRepYear  = UiUtils.makeCombo(Constants.YEARS);
        cboRepMonth.setVisible(false);
        cboRepYear.setVisible(false);
        add(cboRepMonth);
        add(cboRepYear);

        JButton btnUp = UiUtils.makeTinyArrow("▲", 554, 14);
        JButton btnDn = UiUtils.makeTinyArrow("▼", 554, 36);
        add(btnUp);
        add(btnDn);

        cboRepMonth.addActionListener(e -> { updateRepMonthDisplay(monthDisplay); refresh(); });
        cboRepYear.addActionListener(e  -> { updateRepMonthDisplay(monthDisplay); refresh(); });

        btnUp.addActionListener(e -> {
            int mo = cboRepMonth.getSelectedIndex();
            int yr = Integer.parseInt((String) cboRepYear.getSelectedItem());
            mo--;
            if (mo < 0) { mo = 11; yr--; }
            cboRepMonth.setSelectedIndex(mo);
            cboRepYear.setSelectedItem(String.valueOf(yr));
        });
        btnDn.addActionListener(e -> {
            int mo = cboRepMonth.getSelectedIndex();
            int yr = Integer.parseInt((String) cboRepYear.getSelectedItem());
            mo++;
            if (mo > 11) { mo = 0; yr++; }
            cboRepMonth.setSelectedIndex(mo);
            cboRepYear.setSelectedItem(String.valueOf(yr));
        });

        JLabel dateLbl = new JLabel("DATE: XX/XX", SwingConstants.LEFT);
        dateLbl.setFont(new Font("Arial", Font.PLAIN, 11));
        dateLbl.setForeground(Theme.MUTED);
        dateLbl.setBackground(new Color(225, 225, 225));
        dateLbl.setOpaque(true);
        dateLbl.setBorder(new EmptyBorder(0, 8, 0, 0));
        dateLbl.setBounds(20, 68, 460, 28);
        add(dateLbl);

        JPanel balCard  = UiUtils.makeCard(500, 68, 120, 60); add(balCard);
        JPanel expCard  = UiUtils.makeCard(630, 68, 120, 60); add(expCard);
        JPanel rentCard = UiUtils.makeCard(760, 68, 140, 60); add(rentCard);

        UiUtils.addCardTitle(balCard,  "Balance",    10, 8);
        UiUtils.addCardTitle(expCard,  "Expenses",   10, 8);
        UiUtils.addCardTitle(rentCard, "Rent Funds", 10, 8);

        repBalance = UiUtils.addMetricLabel(balCard,  "₱0.00", Theme.TEXT);
        repExp     = UiUtils.addMetricLabel(expCard,  "₱0.00", Theme.DANGER);
        repRent    = UiUtils.addMetricLabel(rentCard, "₱0.00", Theme.MUTED);

        JPanel salesCard = UiUtils.makeCard(20, 108, 460, 400);
        add(salesCard);

        JLabel salesTitle = new JLabel("Sales");
        salesTitle.setFont(new Font("Arial", Font.BOLD, 11));
        salesTitle.setForeground(Theme.TEXT);
        salesTitle.setBounds(14, 10, 200, 16);
        salesCard.add(salesTitle);

        repSalesPanel = new JPanel();
        repSalesPanel.setLayout(new BoxLayout(repSalesPanel, BoxLayout.Y_AXIS));
        repSalesPanel.setBackground(Theme.SURFACE);
        JScrollPane scroll = new JScrollPane(repSalesPanel);
        scroll.setBorder(null);
        scroll.setBounds(0, 32, 460, 250);
        salesCard.add(scroll);

        JPanel optionsPanel = new JPanel(null);
        optionsPanel.setBackground(Theme.SURFACE);
        optionsPanel.setBorder(new MatteBorder(1, 0, 0, 0, Theme.BORDER));
        optionsPanel.setBounds(0, 284, 460, 80);
        salesCard.add(optionsPanel);

        JLabel optLbl = new JLabel("Filters"); // New
        optLbl.setFont(new Font("Arial", Font.PLAIN, 10));
        optLbl.setForeground(Theme.MUTED);
        optLbl.setBounds(12, 8, 200, 14);
        optionsPanel.add(optLbl);

        ButtonGroup bg = new ButtonGroup();
        rSales = new JRadioButton("Sales (PAID)"); // New
        rSales.setFont(new Font("Arial", Font.PLAIN, 10));
        rSales.setBackground(Theme.SURFACE);
        rSales.setForeground(Theme.TEXT);
        rSales.setSelected(true);
        rSales.setBounds(12, 24, 120, 18);
        bg.add(rSales);
        optionsPanel.add(rSales);

        rExp = new JRadioButton("Additional Expenses"); // New
        rExp.setFont(new Font("Arial", Font.PLAIN, 10));
        rExp.setBackground(Theme.SURFACE);
        rExp.setForeground(Theme.TEXT);
        rExp.setBounds(12, 44, 160, 18);
        bg.add(rExp);
        optionsPanel.add(rExp);

        rSales.addActionListener(e -> refresh()); // New
        rExp.addActionListener(e -> refresh()); // New

        JPanel totalRow = new JPanel(null);
        totalRow.setBackground(Theme.SURFACE);
        totalRow.setBorder(new MatteBorder(1, 0, 0, 0, Theme.BORDER));
        totalRow.setBounds(0, 364, 460, 36);
        salesCard.add(totalRow);

        JLabel totalLbl = new JLabel("Total");
        totalLbl.setFont(new Font("Arial", Font.BOLD, 12));
        totalLbl.setForeground(Theme.TEXT);
        totalLbl.setBounds(14, 9, 100, 18);
        totalRow.add(totalLbl);

        repTotal = new JLabel("₱0.00");
        repTotal.setFont(new Font("Courier New", Font.BOLD, 13));
        repTotal.setForeground(Theme.SUCCESS);
        repTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        repTotal.setBounds(300, 9, 140, 18);
        totalRow.add(repTotal);

        JPanel chartCard = UiUtils.makeCard(500, 108, 400, 400);
        add(chartCard);
        UiUtils.addCardTitle(chartCard, "Chart", 16, 10);

        repChart = new ReportChartPanel(data.getLedger());
        repChart.setBounds(12, 32, 376, 356);
        chartCard.add(repChart);

        initMonthYear(monthDisplay);
    }

    public void initMonthYear(JLabel monthDisplay) {
        LocalDate now = LocalDate.now();
        cboRepMonth.setSelectedIndex(now.getMonthValue() - 1);
        cboRepYear.setSelectedItem(String.valueOf(now.getYear()));
        updateRepMonthDisplay(monthDisplay);
    }

    private void updateRepMonthDisplay(JLabel lbl) {
        if (cboRepMonth == null || cboRepYear == null) return;
        String mo = (String) cboRepMonth.getSelectedItem();
        String yr = (String) cboRepYear.getSelectedItem();
        lbl.setText(mo.substring(0, 3).toUpperCase() + " " + yr);
    }

    public void refresh() {
        int mo = cboRepMonth.getSelectedIndex();
        int yr = Integer.parseInt((String) cboRepYear.getSelectedItem());

        List<LedgerEntry> filtered = data.getLedger().stream()
            .filter(e -> e.getDatetime().getMonthValue() - 1 == mo && e.getDatetime().getYear() == yr)
            .collect(Collectors.toList());

        // New (Start here)
        double totalSales = filtered.stream().filter(e -> e.getType().equalsIgnoreCase("Sale"))
        		.mapToDouble(LedgerEntry::getTotal).sum();
        double totalExp = filtered.stream().filter(e -> e.getType().equalsIgnoreCase("Expense"))
        	    .mapToDouble(LedgerEntry::getTotal).sum();
        double totalRent = filtered.stream().filter(e -> e.getType().equalsIgnoreCase("Rent"))
        	    .mapToDouble(LedgerEntry::getTotal).sum();
        // End here

        repBalance.setText(FormatUtils.fmt(totalSales - totalExp - totalRent)); // New
        repExp.setText(FormatUtils.fmt(totalExp));
        repRent.setText(FormatUtils.fmt(totalRent)); // New
        repTotal.setText(FormatUtils.fmt(totalSales));

        repSalesPanel.removeAll();
        
        // New (Start here)
        Map<String, Double> itemMap = new LinkedHashMap<>();
        if (rSales.isSelected()) {
            filtered.stream()
                .filter(e ->
                    e.getType().equalsIgnoreCase("Sale")
                    && e.getStatus().equalsIgnoreCase("Paid"))
                .forEach(e ->
                    itemMap.merge(
                        e.getItem(),
                        e.getTotal(),
                        Double::sum));
        }
        else if (rExp.isSelected()) {

            filtered.stream()
                .filter(e ->
                    e.getType().equalsIgnoreCase("Expense")
                    || e.getType().equalsIgnoreCase("Rent"))
                .forEach(e ->
                    itemMap.merge(
                        e.getItem(),
                        e.getTotal(),
                        Double::sum));
        } // End here

        if (itemMap.isEmpty()) {
            JLabel none = new JLabel("No sales data for this period.");
            none.setFont(new Font("Arial", Font.ITALIC, 12));
            none.setForeground(Theme.MUTED);
            none.setAlignmentX(Component.CENTER_ALIGNMENT);
            none.setBorder(new EmptyBorder(16, 10, 0, 10));
            repSalesPanel.add(none);
        } else {
            for (Map.Entry<String, Double> en : itemMap.entrySet()) {
                JPanel row = new JPanel(null);
                row.setBackground(Theme.SURFACE);
                row.setMaximumSize(new Dimension(460, 40));
                row.setMinimumSize(new Dimension(460, 40));
                row.setPreferredSize(new Dimension(460, 40));
                row.setBorder(new MatteBorder(0, 0, 1, 0, Theme.BORDER));
                JLabel k = new JLabel(en.getKey());
                k.setFont(new Font("Arial", Font.PLAIN, 12));
                k.setForeground(Theme.TEXT);
                k.setBounds(12, 11, 240, 18);
                row.add(k);
                JLabel v = new JLabel(FormatUtils.fmt(en.getValue()));
                v.setFont(new Font("Courier New", Font.BOLD, 12));
                v.setForeground(Theme.SUCCESS);
                v.setHorizontalAlignment(SwingConstants.RIGHT);
                v.setBounds(320, 11, 120, 18);
                row.add(v);
                repSalesPanel.add(row);
            }
        }
        repSalesPanel.revalidate();
        repSalesPanel.repaint();

        if (repChart != null) {
            repChart.setMonthYear(mo, yr);
            repChart.repaint();
        }
    }
}
