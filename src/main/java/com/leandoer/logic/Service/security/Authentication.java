package com.leandoer.logic.service.security;

import com.leandoer.logic.domain.User;
import lombok.Data;

@Data
public class Authentication {
    private User user;
    private boolean isAuthenticated;

}
