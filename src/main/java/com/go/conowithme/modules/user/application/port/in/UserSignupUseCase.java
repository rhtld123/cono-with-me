package com.go.conowithme.modules.user.application.port.in;

import com.go.conowithme.modules.user.application.port.in.model.UserSignupCommand;

public interface UserSignupUseCase {
    void signup(UserSignupCommand userSignupCommand);
}
