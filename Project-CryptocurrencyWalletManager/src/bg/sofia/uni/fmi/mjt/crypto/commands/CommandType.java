package bg.sofia.uni.fmi.mjt.crypto.commands;

public enum CommandType {
    LOGIN("login"),
    LOGOUT("logout"),
    HELP("help"),
    REGISTER("register"),
    DEPOSIT("deposit-money"),
    BUY("buy"),
    SELL("sell"),
    LIST("list-offerings"),
    WALLET_SUMMARY("get-wallet-summary"),
    WALLET_OVERALL_SUMMARY("get-wallet-overall-summary"),
    INVALID("invalid");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static CommandType getCommandType(String command) {
        for (CommandType type : CommandType.values()) {
            if (type.getCommand().equals(command)) {
                return type;
            }
        }
        return INVALID;
    }
}
