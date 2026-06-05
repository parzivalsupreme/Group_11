package group11_Project.ui.table;

import group11_Project.ui.Theme;
import java.awt.*;
import java.util.function.IntConsumer;
import javax.swing.*;
import javax.swing.table.*;

public class DeleteButtonEditor extends DefaultCellEditor {
    private final IntConsumer onDelete;
    private int currentRow;

    public DeleteButtonEditor(JCheckBox cb, IntConsumer onDelete) {
        super(cb);
        this.onDelete = onDelete;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table,
            Object value, boolean isSelected, int row, int col) {
        currentRow = row;
        JButton b = new JButton("✕");
        b.setFont(new Font("Arial", Font.PLAIN, 10));
        b.setForeground(Theme.DANGER);
        b.setBackground(Theme.SURFACE);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.addActionListener(e -> {
            fireEditingStopped();
            onDelete.accept(currentRow);
        });
        return b;
    }
}
