package fr.rammex.prisonUltimateCool.economy;

public enum Currency {
    COINS("coins", "Coin", "Coins"),
    TOKENS("tokens", "Token", "Tokens"),
    GEMS("gems", "Gemme", "Gemmes");

    private final String columnName;
    private final String singular;
    private final String plural;

    Currency(String columnName, String singular, String plural) {
        this.columnName = columnName;
        this.singular = singular;
        this.plural = plural;
    }

    public String getColumnName() { return columnName; }
    public String getSingular() { return singular; }
    public String getPlural() { return plural; }
}
