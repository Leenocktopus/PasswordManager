package com.leandoer.ui.context;

import com.leandoer.logic.domain.Password;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PasswordHolder {
    Password password;
}
