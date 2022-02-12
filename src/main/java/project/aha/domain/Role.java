package project.aha.domain;

public enum Role {
    ADMIN{
        @Override
        public String toString() {
            return "ROLE_ADMIN";
        }
    },
    MEMBER {
        @Override
        public String toString() {
            return "ROLE_USER";
        }
    }
}
