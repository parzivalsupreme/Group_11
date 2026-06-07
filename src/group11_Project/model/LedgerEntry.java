package group11_Project.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LedgerEntry {
    private LocalDateTime datetime;
    private String item;
    private int quantity;
    private double amount;
    private String method;
    private String type;   // "Sale" or "Expense"
    private String status; // "Paid" or "Pending"

    private static final DateTimeFormatter DISPLAY_FMT =
        DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");

    public LedgerEntry(LocalDateTime datetime, String item, int quantity,
                       double amount, String method, String type, String status) {
        this.datetime = datetime;
        this.item = item;
        this.quantity = quantity;
        this.amount = amount;
        this.method = method;
        this.type = type;
        this.status = status;
    }

    public LocalDateTime getDatetime() { return datetime; }
    public String getItem()            { return item; }
    public int getQuantity()           { return quantity; }
    public double getAmount()          { return amount; }
    public double getTotal()           { return amount * quantity; }
    public String getMethod()          { return method; }
    public String getType()            { return type; }
    public String getStatus()          { return status; }

    // For the Edit
    public void setDatetime(LocalDateTime datetime) { this.datetime = datetime; } // New (Start here)
    public void setItem(String item)                { this.item = item; }
    public void setQuantity(int quantity)           { this.quantity = quantity; }
    public void setAmount(double amount)            { this.amount = amount; }
    public void setMethod(String method)            { this.method = method; }
    public void setType(String type)                { this.type = type; }
    public void setStatus(String status)            { this.status = status; } // End here

    public String getFormattedDatetime() {
        return datetime.format(DISPLAY_FMT);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s x%d @ ₱%.2f — %s / %s / %s",
            getFormattedDatetime(), item, quantity, amount, method, type, status);
    }
}
