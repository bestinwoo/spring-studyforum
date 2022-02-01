package project.aha.domain;

public enum Role {
    ADMIN{
        @Override
        public String toString() {
            return "A";
        }
    },
    MEMBER {
        @Override
        public String toString() {
            return "M";
        }
    }
}
