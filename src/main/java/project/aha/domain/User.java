package project.aha.domain;

import lombok.*;


import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private String phone;
    private LocalDateTime registerDate;
    private LocalDateTime leaveDate;
    private Long roleID;
}
