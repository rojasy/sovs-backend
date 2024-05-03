package com.uautso.sovs.utils.userextractor;


import com.uautso.sovs.model.UserAccount;

public interface LoggedUser {

    UserInfo getInfo();

    UserAccount getUser();
}
