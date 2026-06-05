package group11_Project.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public final class UiUtils {
    private UiUtils() {}

    public static void applyUIDefaults() {
        UIManager.put("TabbedPane.focus",                 new Color(0, 0, 0, 0));
        UIManager.put("TabbedPane.borderHightlightColor", new Color(0, 0, 0, 0));
        UIManager.put("TabbedPane.contentBorderInsets",   new Insets(0, 0, 0, 0));
    }

    public static JPanel makeCard(int x, int y, int w, int h) {
        JPanel card = new JPanel(null);
        card.setBackground(Theme.SURFACE);
        card.setBorder(new LineBorder(Theme.BORDER, 1, true));
        card.setBounds(x, y, w, h);
        return card;
    }

    public static void addCardTitle(JPanel card, String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.PLAIN, 9));
        lbl.setForeground(Theme.MUTED);
        lbl.setBounds(x, y, card.getPreferredSize().width, 14);
        card.add(lbl);
    }

    public static JLabel addMetricLabel(JPanel card, String text, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Courier New", Font.BOLD, 14));
        lbl.setForeground(color);
        lbl.setBounds(10, 30, 100, 22);
        card.add(lbl);
        return lbl;
    }

    public static JButton makeButton(String text, int x, int y, int w, int h) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.setBackground(new Color(26, 26, 26));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBounds(x, y, w, h);
        return btn;
    }

    public static JComboBox<String> makeCombo(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(new Font("Arial", Font.PLAIN, 11));
        cb.setBackground(Theme.SURFACE);
        cb.setForeground(Theme.TEXT);
        cb.setFocusable(false);
        return cb;
    }

    public static JTextField styledField() {
        JTextField f = new JTextField();
        styleTextField(f);
        return f;
    }

    public static void styleTextField(JTextField f) {
        f.setFont(new Font("Arial", Font.PLAIN, 12));
        f.setBorder(new CompoundBorder(
            new LineBorder(Theme.BORDER, 1, true), new EmptyBorder(3, 7, 3, 7)));
        f.setBackground(new Color(248, 246, 242));
    }

    public static void addDialogLabel(JDialog dlg, String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.PLAIN, 9));
        lbl.setForeground(Theme.MUTED);
        lbl.setBounds(x, y, 200, 14);
        dlg.add(lbl);
    }

    public static JButton makeTinyArrow(String sym, int x, int y) {
        JButton b = new JButton(sym);
        b.setFont(new Font("Arial", Font.PLAIN, 9));
        b.setBackground(new Color(230, 230, 230));
        b.setForeground(Theme.TEXT);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(Theme.BORDER, 1));
        b.setBounds(x, y, 24, 20);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static JPanel makeStatBox(String label, String val, int x, int y) {
        JPanel box = new JPanel(null);
        box.setBackground(new Color(240, 240, 240));
        box.setBorder(new LineBorder(Theme.BORDER, 1));
        box.setBounds(x, y, 82, 26);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 7));
        lbl.setForeground(Theme.MUTED);
        lbl.setBounds(4, 2, 74, 10);
        box.add(lbl);
        JLabel v = new JLabel(val);
        v.setFont(new Font("Arial", Font.BOLD, 9));
        v.setForeground(Theme.TEXT);
        v.setBounds(4, 12, 74, 12);
        v.setName("val");
        box.add(v);
        return box;
    }
}
