package com.uautso.sovs.utils.userextractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.repository.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class LoggedUserImpl implements LoggedUser{
    private static final Logger logger = LoggerFactory.getLogger(LoggedUserImpl.class);

    @Autowired
    private UserAccountRepository repository;
    @Override
    public UserInfo getInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
			log.info("Null Auth detected");
            return null;
        } else {
            UserInfo user = new UserInfo();
            try {

                ObjectMapper mapper = new ObjectMapper().registerModule(new ParameterNamesModule())
                        .registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());

                String jsonAuth = mapper.writeValueAsString(auth);


                HashMap<String, Object> result = new ObjectMapper().readValue(jsonAuth, HashMap.class);

                if (result.get("principal") != null){
                    if (result.get("principal").toString().contains("anonymousUser"))
                        return null;

                    HashMap<String, Object> principal = (HashMap<String, Object>) result.get("principal");

                    logger.info("User principal found {}", principal);

                    Object id = principal.get("id");

                    user.setId(Long.parseLong(id.toString()));

                    return user;

                }

            } catch (Exception e) {
                logger.error("----------------Error has occured on authentication facade" + e.getMessage()
                        + "----------------------");

                e.printStackTrace();

            }
        }
        return null;
    }

    @Override
    public UserAccount getUser() {
        //log.warn("User information found {}", getInfo());
        if (getInfo() != null && getInfo().getId() != null)
            return repository.findById(getInfo().getId()).orElse(null);
        return null;
    }
}
