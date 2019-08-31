package com.javamentor.jm_spring_boot.controller;

import com.javamentor.jm_spring_boot.model.Authority;
import com.javamentor.jm_spring_boot.model.User;
import com.javamentor.jm_spring_boot.service.AuthorityService;
import com.javamentor.jm_spring_boot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String USER_NOT_FOUND = "User not found.";

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView user(@ModelAttribute User user) {
        if (user == null) {
            user = new User();
        }
        return new ModelAndView("user", "user", user);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String readById(@PathVariable(name="id") Long id, ModelMap model) {
        try {
            User user = userService.findById(id);
            logger.debug("User: {}", user);
            if (user == null) {
                user = new User();
                model.addAttribute("error", USER_NOT_FOUND);
            }
            model.addAttribute(user);
        } catch (EmptyResultDataAccessException e) {
            model.addAttribute(new User());
            model.addAttribute("error", USER_NOT_FOUND);
        }
        return "user";
    }

    @RequestMapping(path = "/name/{username}", method = RequestMethod.GET)
    public String readByUsername(@PathVariable(name="username") String username, ModelMap model) {
        try {
            logger.debug("Username: {}", username);
            User user =  userService.loadUserByUsername(username);
            logger.debug("User: {}", user);
            model.addAttribute(user);
        } catch (EmptyResultDataAccessException e) {
            model.addAttribute(new User());
            model.addAttribute("error", USER_NOT_FOUND);
        }
        return "user";
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public String readAll(ModelMap model) {
        try {
            List<User> users = userService.findAll();
            logger.debug("Users: {}", users);
            model.addAttribute("users", users);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("users", Collections.EMPTY_LIST);
            model.addAttribute("error", e.getMessage());
        }
        return "users";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String create(HttpServletRequest request, @ModelAttribute User user, RedirectAttributes attributes) {
        logger.debug("Create: {}", user);
        String redirectUrl = "redirect:";
        try {
            user = userService.create(user);
            if (user == null) {
                attributes.addFlashAttribute("error", "User create failed.");
                redirectUrl += request.getHeader("Referer");
            } else {
                attributes.addFlashAttribute("message", "User successful created.");
                redirectUrl += "/user/" + user.getId();
            }
        } catch (DataIntegrityViolationException e) {
            attributes.addFlashAttribute("error", "Username is already taken.");
            attributes.addFlashAttribute("user", user);
            redirectUrl += request.getHeader("Referer");
        }
        return redirectUrl;
    }

    @RequestMapping(path = "/update", method = { RequestMethod.POST, RequestMethod.PUT })
    public String update(HttpServletRequest request, @ModelAttribute User user, RedirectAttributes attributes) {
        logger.debug("Update: {}", user);
        try {
            user = userService.update(user);
            if (user == null) {
                attributes.addFlashAttribute("error", "User update failed.");
            } else {
//                attributes.addFlashAttribute("user", user);
                attributes.addFlashAttribute("message", "User successful updated.");
            }
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(path = "/delete", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
    public String delete(HttpServletRequest request, @RequestParam Long id, RedirectAttributes attributes) {
        logger.debug("Delete: {}", id);
        String redirectUrl = "redirect:";
        try {
            userService.deleteById(id);
            attributes.addFlashAttribute("message", "User successful deleted.");
            redirectUrl += "/";
        } catch (InvalidDataAccessApiUsageException e) {
            attributes.addFlashAttribute("error", "Attempt to delete a nonexistent user.");
            redirectUrl += request.getHeader("Referer");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", e.getMessage());
            redirectUrl += request.getHeader("Referer");
        }
        return redirectUrl;
    }

    @ModelAttribute("authority_list")
    public List<Authority> getAuthorityList() {
        return authorityService.findAll();
    }

    @ModelAttribute("authority_names")
    public List<String> authorityNames() {
        return getAuthorityList().stream().map(Authority::getAuthority).collect(Collectors.toList());
    }

}
