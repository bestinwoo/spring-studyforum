package project.aha.auth.client;

import project.aha.domain.User;

public interface ClientProxy {
    User getUserData(String accessToken);
}
