# Blessie Apparel & Cosmetics

A desktop business management application built with Java Swing for **Group 11**. It provides a simple interface for tracking sales and expenses, viewing reports, and managing users.

## Features

- **Login** — Secure entry screen before accessing the app
- **Dashboard** — Overview of total sales, expenses, net balance, recent transactions, and a monthly income chart
- **Ledger** — Add, filter, search, and delete sales/expense entries
- **Reports** — Monthly sales breakdown, balance/expense summaries, and bar chart
- **Users** — View and manage user accounts with roles and access levels

## Requirements

- Java JDK 8 or later
- No external dependencies (uses standard Java Swing libraries)

## Project Structure

```
src/group11_Project/
├── Main.java                 # Application entry point
├── model/
│   └── LedgerEntry.java      # Ledger transaction model
├── data/
│   └── AppData.java          # Shared in-memory data store
├── util/
│   ├── Constants.java        # Months, years, currency format
│   └── FormatUtils.java      # Peso formatting helpers
└── ui/
    ├── LoginForm.java        # Login screen
    ├── Menu.java             # Main window shell (sidebar + tabs)
    ├── Theme.java            # Color palette
    ├── UiUtils.java          # Reusable UI components
    ├── charts/
    │   ├── BarChartPanel.java
    │   └── ReportChartPanel.java
    ├── panels/
    │   ├── DashboardPanel.java
    │   ├── LedgerPanel.java
    │   ├── ReportsPanel.java
    │   └── UsersPanel.java
    └── table/
        └── DeleteButtonEditor.java
```
