package bg.sofia.uni.fmi.mjt.crypto.builder;

import bg.sofia.uni.fmi.mjt.crypto.server.repository.DataRepository;
import bg.sofia.uni.fmi.mjt.crypto.server.repository.UserRepository;
import bg.sofia.uni.fmi.mjt.crypto.user.UserSessionManager;

public class Arguments {

    private UserRepository userRepository;
    private DataRepository dataRepository;
    private UserSessionManager userSessionManager;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public DataRepository getDataRepository() {
        return dataRepository;
    }

    public UserSessionManager getUserSessionManager() {
        return userSessionManager;
    }

    public static ArgumentsBuilder builder(UserRepository userRepository, DataRepository dataRepository,
                                           UserSessionManager userSessionManager) {
        return new ArgumentsBuilder(userRepository, dataRepository, userSessionManager);
    }

    private Arguments(ArgumentsBuilder builder) {
        this.userRepository = builder.userRepository;
        this.dataRepository = builder.dataRepository;
        this.userSessionManager = builder.userSessionManager;
    }

    public static class ArgumentsBuilder {

        private UserRepository userRepository;
        private DataRepository dataRepository;
        private UserSessionManager userSessionManager;

        private ArgumentsBuilder(UserRepository userRepository, DataRepository dataRepository,
                                 UserSessionManager userSessionManager) {
            this.userRepository = userRepository;
            this.dataRepository = dataRepository;
            this.userSessionManager = userSessionManager;
        }

        public Arguments build() {
            return new Arguments(this);
        }

    }

}
