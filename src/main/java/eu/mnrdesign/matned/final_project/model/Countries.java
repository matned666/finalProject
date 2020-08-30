package eu.mnrdesign.matned.final_project.model;

import java.util.Arrays;

public enum Countries {
    POLAND("Polska", "PL"),
    GERMANY("Niemcy", "DE"),
    SPAIN("Hiszpania", "ES"),
    FRANCE("Francja", "FR");

    private final String plName;
    private final String symbol;

    Countries(String plName, String symbol) {
        this.plName = plName;
        this.symbol = symbol;
    }

    public static Countries fromSymbol (String symbol){
        return Arrays.stream(Countries.values())
                .filter(v->v.getSymbol().equals(symbol))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Country not found"));
    }

    public String getPlName() {
        return plName;
    }

    public String getSymbol() {
        return symbol;
    }
}
