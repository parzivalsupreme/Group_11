package group11_Project.ui.charts;

import group11_Project.model.LedgerEntry;
import group11_Project.ui.Theme;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import javax.swing.*;

public class BarChartPanel extends JPanel {
    private final List<LedgerEntry> data;
    private int month = LocalDate.now().getMonthValue() - 1;
    private int year  = LocalDate.now().getYear();

    public BarChartPanel(List<LedgerEntry> data) {
        this.data = data;
        setBackground(new Color(248, 246, 242));
        setOpaque(true);
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

        int days = YearMonth.of(year, month + 1).lengthOfMonth();
        double[] sales = new double[days], exps = new double[days];
        double max = 1;
        for (LedgerEntry e : data) {
            LocalDateTime dt = e.getDatetime();
            if (dt.getYear() == year && dt.getMonthValue() - 1 == month) {
                int d = dt.getDayOfMonth() - 1;
                if (e.getType().equals("Sale")) {
                    sales[d] += e.getTotal();
                    max = Math.max(max, sales[d]);
                } else {
                    exps[d] += e.getTotal();
                    max = Math.max(max, exps[d]);
                }
            }
        }

        int W = getWidth(), H = getHeight();
        int padL = 40, padR = 10, padT = 16, padB = 28;
        int chartW = W - padL - padR, chartH = H - padT - padB;
        double barW = (double) chartW / days;
        double grouped = barW * 0.38;

        g2.setColor(new Color(200, 200, 200));
        g2.drawLine(padL, padT, padL, padT + chartH);
        g2.drawLine(padL, padT + chartH, padL + chartW, padT + chartH);

        g2.setFont(new Font("Arial", Font.PLAIN, 8));
        g2.setColor(Theme.MUTED);
        for (int i = 0; i <= 4; i++) {
            int yy = padT + chartH - (int) (chartH * i / 4.0);
            g2.drawLine(padL - 3, yy, padL, yy);
            g2.drawString("₱" + (int) (max * i / 4), 1, yy + 3);
        }

        for (int d = 0; d < days; d++) {
            int x0 = padL + (int) (d * barW);
            int sh = (int) (sales[d] / max * chartH);
            int eh = (int) (exps[d] / max * chartH);
            int bx = x0 + (int) (barW / 2 - grouped);

            if (sh > 0) {
                g2.setColor(Theme.SUCCESS);
                g2.fillRect(bx, padT + chartH - sh, (int) grouped - 1, sh);
            }
            if (eh > 0) {
                g2.setColor(new Color(220, 80, 80));
                g2.fillRect(bx + (int) grouped, padT + chartH - eh, (int) grouped - 1, eh);
            }
            if ((days <= 16 || d % 2 == 0) && barW > 8) {
                g2.setColor(Theme.MUTED);
                g2.drawString(String.valueOf(d + 1), x0 + (int) (barW / 2) - 4, padT + chartH + 14);
            }
        }

        g2.setColor(Theme.SUCCESS);
        g2.fillRect(padL, H - 12, 8, 8);
        g2.setColor(Theme.TEXT);
        g2.drawString("Sales", padL + 10, H - 5);
        g2.setColor(new Color(220, 80, 80));
        g2.fillRect(padL + 48, H - 12, 8, 8);
        g2.setColor(Theme.TEXT);
        g2.drawString("Expenses", padL + 58, H - 5);
    }
}
