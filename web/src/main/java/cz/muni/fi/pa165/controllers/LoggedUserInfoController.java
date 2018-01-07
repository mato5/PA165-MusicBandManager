package cz.muni.fi.pa165.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cz.fi.muni.pa165.dto.ManagerDTO;
import cz.fi.muni.pa165.dto.MemberDTO;
import cz.fi.muni.pa165.facade.ManagerFacade;
import cz.fi.muni.pa165.facade.MemberFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@RestController
@RequestMapping("/user")
public class LoggedUserInfoController {

    private final static Logger logger = LoggerFactory.getLogger(LoggedUserInfoController.class);

    @Inject
    private MemberFacade memberFacade;

    @Inject
    private ManagerFacade managerFacade;

    @RequestMapping(method = RequestMethod.GET)
    private String getUserPrincipal() throws JsonProcessingException {
        List<?> authorities = new ArrayList<>(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        String email = getLogin();
        Long id = getId(email);

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode userInfoObjectNode = mapper.createObjectNode();
        userInfoObjectNode.put("username", email);
        userInfoObjectNode.put("id", String.valueOf(id));
        userInfoObjectNode.put("role", authorities.get(0).toString());

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userInfoObjectNode);
    }

    private String getLogin() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Long getId(String email) {

        Long foundId = null;

        MemberDTO memberByEmail = memberFacade.findMemberByEmail(email);
        ManagerDTO managerByEmail = managerFacade.findManagerByEmail(email);

        if (memberByEmail != null) {
            foundId = memberByEmail.getId();
        } else if (managerByEmail != null) {
            foundId = managerByEmail.getId();
        } else {
        }

        return foundId;
    }
}