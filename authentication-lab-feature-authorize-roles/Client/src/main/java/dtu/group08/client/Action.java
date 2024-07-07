package dtu.group08.client;

public enum Action {
    PRINT(1, "Print"),
    QUEUE(2, "Show Queue"),
    TOP_QUEUE(3, "Move job to top"),
    START(4, "Start the print server"),
    STOP(5, "Stop the print server"),
    RESTART(6, "Restart the print server"),
    STATUS(7, "See server status"),
    READ_CONFIGURATION(8, "Read configuration"),
    SET_CONFIGURATION(9, "Set configuration"),
    DELETE_WORKER(10, "Remove a worker from DB (only for the manager)"),
    ADD_WORKER(11, "Add worker to database (only for the manager)"),
    SET_USER_ROLE(12, "Set user role (only for manager)"),
    SHOW_USERS(13, "Show users (only for the manager)"),
    SHOW_USER_PERMISSIONS(14, "Show user permissions (only for the manager)"),
    LOGOUT(20, "Logout");

    private final int val;
    private final String description;

    Action(int num, String description) {
        this.val = num;
        this.description = description;
    }

    public int getValue() {
        return this.val;
    }

    public String getDescription() {
        return this.description;
    }

    public static Action fromValue(int value) {
        for (Action option : Action.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
