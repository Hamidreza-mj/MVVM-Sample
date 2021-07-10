package hlv.app.mvvmsample.util.faker;

import java.util.ArrayList;

import hlv.app.mvvmsample.model.User;

public class TemporaryContentProvider {

    public static final ArrayList<User> ITEMS = new ArrayList<>();

    private static final int COUNT = 100000;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(User item) {
        ITEMS.add(item);
    }

    private static User createPlaceholderItem(int position) {
        return new User(position, "Username " + position, position, position % 3 == 0);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
}
