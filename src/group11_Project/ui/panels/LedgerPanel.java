package group11_Project.ui.panels;

import group11_Project.data.AppData;
import group11_Project.model.LedgerEntry;
import group11_Project.data.Database;
import group11_Project.ui.Theme;
import group11_Project.ui.UiUtils;
import group11_Project.ui.table.DeleteButtonEditor;
import group11_Project.util.Constants;
import group11_Project.util.FormatUtils;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class LedgerPanel extends JPanel {
    private final AppData data;
    private final JFrame parent;
    private final Runnable onDataChanged;

    private DefaultTableModel ledgerTableModel;
    private JComboBox<String> cboLedgerMonth, cboLedgerYear, cboLedgerStatus;
    private JTextField txtLedgerSearch;

    public LedgerPanel(AppData data, JFrame parent, Runnable onDataChanged) {
        this.data = data;
        this.parent = parent;
        this.onDataChanged = onDataChanged;
        setLayout(null);
        setBackground(Theme.BG);
        build();
    }

    private void build() {
        JPanel headerRow = new JPanel(null);
        headerRow.setBackground(Theme.BG);
        headerRow.setBounds(20, 14, 880, 44);
        add(headerRow);

        JLabel monthLbl = new JLabel("", SwingConstants.CENTER);
        monthLbl.setFont(new Font("Arial", Font.BOLD, 20));
        monthLbl.setForeground(Theme.TEXT);
        monthLbl.setBorder(new LineBorder(Theme.BORDER, 1));
        monthLbl.setBackground(Theme.SURFACE);
        monthLbl.setOpaque(true);
        monthLbl.setBounds(300, 0, 220, 40);
        headerRow.add(monthLbl);

        cboLedgerMonth = UiUtils.makeCombo(new String[]{
            "All Months", "January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October", "November", "December"
        });
        cboLedgerYear   = UiUtils.makeCombo(Constants.YEARS);
        cboLedgerStatus = UiUtils.makeCombo(new String[]{"All Status", "Paid", "Pending"});
        txtLedgerSearch = new JTextField(12);
        UiUtils.styleTextField(txtLedgerSearch);

        cboLedgerMonth.setVisible(false);
        cboLedgerYear.setVisible(false);

        cboLedgerMonth.addActionListener(e -> { updateLedgerMonthDisplay(monthLbl); refresh(); });
        cboLedgerYear.addActionListener(e  -> { updateLedgerMonthDisplay(monthLbl); refresh(); });
        cboLedgerStatus.addActionListener(e -> refresh());
        txtLedgerSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { refresh(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { refresh(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { refresh(); }
        });

        headerRow.add(cboLedgerMonth);
        headerRow.add(cboLedgerYear);

        JButton up = UiUtils.makeTinyArrow("▲", 524, 0);
        JButton dn = UiUtils.makeTinyArrow("▼", 524, 22);
        up.addActionListener(e -> {
            int mo = cboLedgerMonth.getSelectedIndex();
            int yr = Integer.parseInt((String) cboLedgerYear.getSelectedItem());
            if (mo == 0) { mo = 12; yr--; } else mo--;
            if (mo == 0) mo = 12;
            cboLedgerMonth.setSelectedIndex(mo);
            cboLedgerYear.setSelectedItem(String.valueOf(yr));
        });
        dn.addActionListener(e -> {
            int mo = cboLedgerMonth.getSelectedIndex();
            int yr = Integer.parseInt((String) cboLedgerYear.getSelectedItem());
            mo++;
            if (mo > 12) { mo = 1; yr++; }
            cboLedgerMonth.setSelectedIndex(mo);
            cboLedgerYear.setSelectedItem(String.valueOf(yr));
        });
        headerRow.add(up);
        headerRow.add(dn);

        JPanel filterRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        filterRow.setBackground(Theme.BG);
        filterRow.setBounds(20, 62, 880, 32);
        add(filterRow);

        JButton btnAdd = new JButton("+");
        btnAdd.setFont(new Font("Arial", Font.PLAIN, 14));
        btnAdd.setBackground(Theme.SURFACE);
        btnAdd.setForeground(Theme.TEXT);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorder(new LineBorder(Theme.BORDER, 1));
        btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAdd.setPreferredSize(new Dimension(30, 28));
        btnAdd.addActionListener(e -> showAddEntryDialog());
        filterRow.add(btnAdd);

        JButton btnDel = new JButton("\uD83D\uDDD1");
        btnDel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12)); // Change font from Arial to Segoe UI Emoji
        btnDel.setBackground(Theme.SURFACE);
        btnDel.setForeground(Theme.DANGER);
        btnDel.setFocusPainted(false);
        btnDel.setBorder(new LineBorder(Theme.BORDER, 1));
        btnDel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDel.setPreferredSize(new Dimension(30, 28));
        filterRow.add(btnDel);

        cboLedgerStatus.setPreferredSize(new Dimension(100, 28));
        filterRow.add(cboLedgerStatus);

        txtLedgerSearch.setPreferredSize(new Dimension(130, 28));
        filterRow.add(txtLedgerSearch);

        String[] cols = {"TIME", "QUANTITY", "METHOD", "AMOUNT", "ITEM", "STATUS", ""};
        ledgerTableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return c == 6; }
        };

        JTable table = new JTable(ledgerTableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setGridColor(Theme.BORDER);
        table.setBackground(Theme.SURFACE);
        table.setSelectionBackground(new Color(245, 243, 239));
        table.setFocusable(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 10));
        header.setBackground(new Color(230, 230, 230));
        header.setForeground(Theme.TEXT);
        header.setBorder(new MatteBorder(0, 0, 1, 0, Theme.BORDER));
        header.setReorderingAllowed(false);

        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(70);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(160);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        table.getColumnModel().getColumn(6).setMaxWidth(40);

        table.getColumnModel().getColumn(5).setCellRenderer((t, val, sel, foc, row, col) -> {
            JLabel badge = new JLabel(String.valueOf(val), SwingConstants.CENTER);
            badge.setFont(new Font("Arial", Font.PLAIN, 10));
            badge.setOpaque(true);
            String s = String.valueOf(val);
            if (s.equals("Paid")) {
                badge.setBackground(Theme.PAID_BG);
                badge.setForeground(new Color(6, 95, 70));
            } else {
                badge.setBackground(Theme.PEND_BG);
                badge.setForeground(new Color(146, 64, 14));
            }
            return badge;
        });

        table.getColumn("").setCellRenderer((t, val, sel, foc, row, col) -> {
            JButton b = new JButton("✕");
            b.setFont(new Font("Arial", Font.PLAIN, 10));
            b.setForeground(Theme.DANGER);
            b.setBackground(Theme.SURFACE);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
            return b;
        });
        table.getColumn("").setCellEditor(new DeleteButtonEditor(new JCheckBox(), this::confirmDeleteEntry));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 100, 880, 550);
        scroll.setBorder(new LineBorder(Theme.BORDER, 1, true));
        add(scroll);

        initMonthYear(monthLbl);
    }

    public void initMonthYear(JLabel monthLbl) {
        LocalDate now = LocalDate.now();
        cboLedgerMonth.setSelectedIndex(0);
        cboLedgerYear.setSelectedItem(String.valueOf(now.getYear()));
        updateLedgerMonthDisplay(monthLbl);
    }

    private void updateLedgerMonthDisplay(JLabel lbl) {
        int mo = cboLedgerMonth.getSelectedIndex();
        String yr = (String) cboLedgerYear.getSelectedItem();
        if (mo == 0) {
            lbl.setText("ALL  " + yr);
        } else {
            lbl.setText(Constants.MONTHS[mo - 1].substring(0, 3).toUpperCase() + " " + yr);
        }
    }

    public void refresh() {
        int moSel = cboLedgerMonth.getSelectedIndex();
        int yr    = Integer.parseInt((String) cboLedgerYear.getSelectedItem());
        String statusFilter = (String) cboLedgerStatus.getSelectedItem();
        String q = txtLedgerSearch.getText().toLowerCase().trim();

        ledgerTableModel.setRowCount(0);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd hh:mm a");
        for (LedgerEntry e : data.getLedger()) {
            int eMo = e.getDatetime().getMonthValue();
            int eYr = e.getDatetime().getYear();
            if (moSel > 0 && eMo != moSel) continue;
            if (eYr != yr) continue;
            if (!statusFilter.equals("All Status") && !e.getStatus().equals(statusFilter)) continue;
            if (!q.isEmpty() && !e.getItem().toLowerCase().contains(q)) continue;

            ledgerTableModel.addRow(new Object[]{
                e.getDatetime().format(dtf),
                e.getQuantity(),
                e.getMethod(),
                FormatUtils.fmt(e.getTotal()),
                e.getItem(),
                e.getStatus(),
                "✕"
            });
        }
    }

    private void showAddEntryDialog() {
        JDialog dlg = new JDialog(parent, "Add Ledger Entry", true);
        dlg.setSize(420, 380);
        dlg.setLocationRelativeTo(parent);
        dlg.setLayout(null);
        dlg.getContentPane().setBackground(Theme.SURFACE);

        JTextField fItem    = UiUtils.styledField(); fItem.setBounds(16, 54, 180, 28);
        JTextField fQty     = UiUtils.styledField(); fQty.setBounds(210, 54, 80, 28);
        JTextField fAmt     = UiUtils.styledField(); fAmt.setBounds(16, 116, 130, 28);
        JComboBox<String> fMethod = UiUtils.makeCombo(new String[]{"Cash", "GCash", "Card", "Bank Transfer"});
        fMethod.setBounds(160, 116, 130, 28);
        JComboBox<String> fType   = UiUtils.makeCombo(new String[]{"Sale", "Expense"});
        fType.setBounds(16, 178, 130, 28);
        JComboBox<String> fStatus = UiUtils.makeCombo(new String[]{"Paid", "Pending"});
        fStatus.setBounds(160, 178, 130, 28);
        JTextField fDate = UiUtils.styledField();
        fDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        fDate.setBounds(16, 240, 275, 28);

        UiUtils.addDialogLabel(dlg, "Item Name",       16, 36);
        UiUtils.addDialogLabel(dlg, "Qty",            210, 36);
        UiUtils.addDialogLabel(dlg, "Amount (₱)",      16, 98);
        UiUtils.addDialogLabel(dlg, "Method",         160, 98);
        UiUtils.addDialogLabel(dlg, "Type",            16, 160);
        UiUtils.addDialogLabel(dlg, "Status",         160, 160);
        UiUtils.addDialogLabel(dlg, "Date (yyyy-MM-dd HH:mm)", 16, 222);

        dlg.add(fItem); dlg.add(fQty); dlg.add(fAmt);
        dlg.add(fMethod); dlg.add(fType); dlg.add(fStatus); dlg.add(fDate);

        JButton save = UiUtils.makeButton("Save", 200, 308, 90, 32);
        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.PLAIN, 12));
        cancel.setBounds(100, 308, 90, 32);
        cancel.addActionListener(e -> dlg.dispose());
        dlg.add(cancel);

        save.addActionListener(e -> {
            try {
                String item = fItem.getText().trim();
                if (item.isEmpty()) { JOptionPane.showMessageDialog(dlg, "Enter item name."); return; }
                int qty = Integer.parseInt(fQty.getText().trim());
                double amt = Double.parseDouble(fAmt.getText().trim());
                LocalDateTime dt = LocalDateTime.parse(fDate.getText().trim(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                data.getLedger().add(new LedgerEntry(dt, item, qty, amt,
                    (String) fMethod.getSelectedItem(),
                    (String) fType.getSelectedItem(),
                    (String) fStatus.getSelectedItem()));
                Database.saveLedger(data.getLedger()); // For the database
                dlg.dispose();
                onDataChanged.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, "Invalid input: " + ex.getMessage());
            }
        });
        dlg.add(save);
        dlg.setVisible(true);
    }

    private void confirmDeleteEntry(int viewRow) {
        if (viewRow < 0) return;
        String time = (String) ledgerTableModel.getValueAt(viewRow, 0);
        String item = (String) ledgerTableModel.getValueAt(viewRow, 4);
        if (JOptionPane.showConfirmDialog(parent, "Delete this entry?", "Confirm",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            data.getLedger().removeIf(e -> e.getItem().equals(item) &&
                e.getDatetime().format(DateTimeFormatter.ofPattern("MMM dd hh:mm a")).equals(time));
            Database.saveLedger(data.getLedger()); // For the database
            onDataChanged.run();
        }
    }
}
