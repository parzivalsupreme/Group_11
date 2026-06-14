package group11_Project.util;

import java.text.NumberFormat;
import java.util.Locale;

public final class Constants {
    public static final String[] MONTHS = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    public static final String[] YEARS = {"2015","2016","2017","2018","2019",
    	    "2020","2021","2022","2023","2024",
    	    "2025","2026","2027","2028","2029",
    	    "2030","2031","2032","2033","2034",
    	    "2035"}; // New
    public static final NumberFormat PESO = NumberFormat.getCurrencyInstance(new Locale("fil", "PH"));

    private Constants() {}
}
