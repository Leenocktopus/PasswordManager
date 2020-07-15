package com.leandoer.ui.context;

import com.leandoer.logic.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserHolder {
    User user;
}
