public enum ItemGroup {
    MILK("milk"),
    YOGURT("yogurt"),
    SALAMI("salami");

    private final String value;

    private ItemGroup(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
