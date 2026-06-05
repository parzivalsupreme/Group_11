package group11_Project.util;

import java.text.NumberFormat;
import java.util.Locale;

public final class Constants {
    public static final String[] MONTHS = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    public static final String[] YEARS = {"2024", "2025", "2026", "2027", "2028"};
    public static final NumberFormat PESO = NumberFormat.getCurrencyInstance(new Locale("fil", "PH"));

    private Constants() {}
}
