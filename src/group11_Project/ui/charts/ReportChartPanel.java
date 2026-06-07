package group11_Project.ui.charts;

import group11_Project.model.LedgerEntry;
import group11_Project.ui.Theme;
import group11_Project.util.FormatUtils;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

public class ReportChartPanel extends JPanel {
    private final List<LedgerEntry> data;
    private int month = java.time.LocalDate.now().getMonthValue() - 1;
    private int year  = java.time.LocalDate.now().getYear();

    public ReportChartPanel(List<LedgerEntry> data) {
        this.data = data;
        setBackground(new Color(248, 246, 242));
    }

    public void setMonthYear(int m, int y) {
        this.month = m;
        this.year = y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<LedgerEntry> filtered = data.stream()
            .filter(e -> e.getDatetime().getYear() == year && e.getDatetime().getMonthValue() - 1 == month)
            .collect(Collectors.toList());

        double totalSales = filtered.stream().filter(e -> e.getType().equals("Sale"))
            .mapToDouble(LedgerEntry::getTotal).sum();
        double totalExp = filtered.stream().filter(e -> e.getType().equals("Expense"))
            .mapToDouble(LedgerEntry::getTotal).sum();
        double totalRent = filtered.stream().filter(e -> e.getType().equalsIgnoreCase("Rent")) 
        	.mapToDouble(LedgerEntry::getTotal).sum(); // New
        double balance = totalSales - totalExp - totalRent; // New

        double[] vals = {totalSales, totalExp + totalRent, Math.max(balance, 0)}; // New
        String[] labels = {"Sales", "Expenses", "Balance"};
        Color[] colors = {Theme.SUCCESS, Theme.DANGER, balance >= 0 ? Theme.ACCENT : Theme.DANGER};
        double maxV = Math.max(1, Arrays.stream(vals).max().getAsDouble());

        int W = getWidth(), H = getHeight();
        int padL = 50, padR = 16, padT = 16, padB = 40;
        int chartH = H - padT - padB, chartW = W - padL - padR;
        int bw = chartW / 5, gap = (chartW - (bw * 3)) / 4;

        g2.setColor(new Color(200, 200, 200));
        g2.drawLine(padL, padT, padL, padT + chartH);
        g2.drawLine(padL, padT + chartH, padL + chartW, padT + chartH);

        g2.setFont(new Font("Arial", Font.PLAIN, 9));
        for (int i = 0; i <= 4; i++) {
            int yy = padT + chartH - (int) (chartH * i / 4.0);
            g2.setColor(Theme.MUTED);
            g2.drawString(FormatUtils.fmt(maxV * i / 4), 1, yy + 3);
            g2.setColor(new Color(220, 218, 214));
            g2.drawLine(padL, yy, padL + chartW, yy);
        }

        for (int i = 0; i < 3; i++) {
            int bh = (int) (vals[i] / maxV * chartH);
            int bx = padL + gap + (bw + gap) * i;
            g2.setColor(colors[i]);
            g2.fillRoundRect(bx, padT + chartH - bh, bw, bh, 4, 4);
            g2.setColor(Theme.TEXT);
            g2.setFont(new Font("Arial", Font.BOLD, 10));
            g2.drawString(labels[i], bx + (bw / 2) - 16, padT + chartH + 16);
            g2.setFont(new Font("Courier New", Font.BOLD, 10));
            if (bh > 14) g2.drawString(FormatUtils.fmt(vals[i]), bx + 2, padT + chartH - bh - 4);
        }
    }
}
