package group11_Project.ui.panels;

import group11_Project.data.AppData;
import group11_Project.ui.Theme;
import group11_Project.ui.UiUtils;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.*;

public class UsersPanel extends JPanel {
    private final AppData data;
    private final JFrame parent;

    private JPanel userGridPanel;

    public UsersPanel(AppData data, JFrame parent) {
        this.data = data;
        this.parent = parent;
        setLayout(null);
        setBackground(Theme.BG);
        build();
    }

    private void build() {
        JButton btnAdd = UiUtils.makeButton("+ Add User", 20, 18, 110, 30);
        btnAdd.addActionListener(e -> showAddUserDialog());
        add(btnAdd);

        userGridPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        userGridPanel.setBackground(Theme.BG);
        JScrollPane scroll = new JScrollPane(userGridPanel);
        scroll.setBorder(null);
        scroll.setBounds(20, 60, 880, 590);
        add(scroll);
    }

    public void refresh() {
        userGridPanel.removeAll();
        for (int i = 0; i < data.getUsers().size(); i++) {
            final int idx = i;
            String[] u = data.getUsers().get(i);
            JPanel card = UiUtils.makeCard(0, 0, 200, 210);
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(new CompoundBorder(
                new LineBorder(Theme.BORDER, 1, true), new EmptyBorder(20, 16, 16, 16)));

            String initials = Arrays.stream(u[0].split(" "))
                .filter(w -> !w.isEmpty()).map(w -> String.valueOf(w.charAt(0)))
                .collect(Collectors.joining()).toUpperCase();
            if (initials.length() > 2) initials = initials.substring(0, 2);
            JLabel av = new JLabel(initials, SwingConstants.CENTER);
            av.setFont(new Font("Arial", Font.BOLD, 14));
            av.setForeground(new Color(26, 26, 26));
            av.setBackground(Theme.ACCENT);
            av.setOpaque(true);
            av.setPreferredSize(new Dimension(48, 48));
            av.setMaximumSize(new Dimension(48, 48));
            av.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(av);
            card.add(Box.createRigidArea(new Dimension(0, 10)));

            JLabel name = new JLabel(u[0], SwingConstants.CENTER);
            name.setFont(new Font("Arial", Font.BOLD, 13));
            name.setForeground(Theme.TEXT);
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(name);

            JLabel role = new JLabel(u[1] + " · " + u[2], SwingConstants.CENTER);
            role.setFont(new Font("Arial", Font.PLAIN, 10));
            role.setForeground(Theme.MUTED);
            role.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(role);
            card.add(Box.createRigidArea(new Dimension(0, 8)));

            JLabel access = new JLabel(u[3], SwingConstants.CENTER);
            access.setFont(new Font("Arial", Font.PLAIN, 10));
            access.setForeground(new Color(12, 84, 96));
            access.setBackground(new Color(232, 244, 248));
            access.setOpaque(true);
            access.setBorder(new EmptyBorder(3, 10, 3, 10));
            access.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(access);

            if (i > 0) {
                card.add(Box.createRigidArea(new Dimension(0, 8)));
                JButton del = UiUtils.makeButton("Remove", 0, 0, 80, 24);
                del.setBackground(new Color(255, 245, 245));
                del.setForeground(Theme.DANGER);
                del.setAlignmentX(Component.CENTER_ALIGNMENT);
                del.addActionListener(e -> {
                    if (JOptionPane.showConfirmDialog(parent, "Remove this user?", "Confirm",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        data.getUsers().remove(idx);
                        refresh();
                    }
                });
                card.add(del);
            }

            userGridPanel.add(card);
        }
        userGridPanel.revalidate();
        userGridPanel.repaint();
    }

    private void showAddUserDialog() {
        JDialog dlg = new JDialog(parent, "Add User", true);
        dlg.setSize(380, 280);
        dlg.setLocationRelativeTo(parent);
        dlg.setLayout(null);
        dlg.getContentPane().setBackground(Theme.SURFACE);

        JTextField fName     = UiUtils.styledField(); fName.setBounds(16, 54, 160, 28);
        JTextField fUsername = UiUtils.styledField(); fUsername.setBounds(190, 54, 160, 28);
        JComboBox<String> fRole   = UiUtils.makeCombo(new String[]{"Admin", "Cashier", "Inventory", "Viewer"});
        fRole.setBounds(16, 116, 160, 28);
        JComboBox<String> fAccess = UiUtils.makeCombo(new String[]{"Full Access", "Limited Access", "Read Only"});
        fAccess.setBounds(190, 116, 160, 28);

        UiUtils.addDialogLabel(dlg, "Full Name",   16, 36);
        UiUtils.addDialogLabel(dlg, "Username",   190, 36);
        UiUtils.addDialogLabel(dlg, "Role",        16, 98);
        UiUtils.addDialogLabel(dlg, "Access",     190, 98);

        dlg.add(fName); dlg.add(fUsername); dlg.add(fRole); dlg.add(fAccess);

        JButton save = UiUtils.makeButton("Add User", 190, 190, 100, 32);
        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.PLAIN, 12));
        cancel.setBounds(80, 190, 100, 32);
        cancel.addActionListener(e -> dlg.dispose());
        dlg.add(cancel);

        save.addActionListener(e -> {
            String name = fName.getText().trim();
            String uname = fUsername.getText().trim();
            if (name.isEmpty()) { JOptionPane.showMessageDialog(dlg, "Enter full name."); return; }
            data.getUsers().add(new String[]{name, uname,
                (String) fRole.getSelectedItem(), (String) fAccess.getSelectedItem()});
            dlg.dispose();
            refresh();
        });
        dlg.add(save);
        dlg.setVisible(true);
    }
}
