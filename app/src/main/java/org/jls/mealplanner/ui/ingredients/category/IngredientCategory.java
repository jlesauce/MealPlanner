package org.jls.mealplanner.ui.ingredients.category;

public enum IngredientCategory {

    PROTEINS("proteins", "Protéines"),
    VEGETABLES("vegetables", "Légumes"),
    CARBOHYDRATES("carbohydrates", "Féculents"),
    NONE("null", "null");

    private final String id;
    private final String displayName;

    IngredientCategory(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public static IngredientCategory toIngredientCategory(final String id) {
        switch (id) {
            case "proteins":
                return PROTEINS;
            case "vegetables":
                return VEGETABLES;
            case "carbohydrates":
                return CARBOHYDRATES;
            default:
                throw new IllegalArgumentException("Unknown ingredient category identifier: " + id);
        }
    }

    public String id() {
        return id;
    }

    public String displayName() {
        return displayName;
    }

    public String toString() {
        return this.id;
    }
}
