package br.com.unifalmg.blog.controller;

import br.com.unifalmg.blog.entity.User;
import br.com.unifalmg.blog.exception.UserNotFoundException;
import br.com.unifalmg.blog.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@AllArgsConstructor // Feito pelo Lombok
public class BlogController {

    private final UserService service;

    @GetMapping("/")
    public String getHome(){
        return "home";
    }

    @GetMapping("/users")
    public String user(Model model){
        List<User> users = service.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/user")
    public String user(User user){
        return "newuser";
    }

    @PostMapping("/user")
    public String newUser(@ModelAttribute("user") User user) {
        log.info("Entrou no cadastro de usuário");
        User addedUser = service.add(user);
        return "redirect:/user/" + addedUser.getId(); // Talvez seja a barra
    }

    @GetMapping("/user/{id}")
    public String showUser(@PathVariable("id") Integer id,
                           Model model) {
        User user = service.findById(id);
        model.addAttribute("user", user);
        return "showUser";
    }

    @GetMapping("/edituser/{id}")
    public String editUser(@PathVariable("id") Integer id,
                           Model model) {
        try {
            User user = service.findById(id);
            model.addAttribute("user",user);
            return "editUser";
        } catch (UserNotFoundException exception) {
            return "redirect:/user";
        }
    }

    @PostMapping("/edituser/{id}")
    public String editUser(@PathVariable("id") Integer id, @ModelAttribute User user, Model model) {
        try {
            log.info("Entrou na edição de usuário!");
            User editedUser = service.update(id,user);
            return "redirect:/user/" + editedUser.getId();
        } catch (UserNotFoundException exception) {

            return "redirect:/user";
        }
    }

    @DeleteMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        service.deleteById(id);

        return "redirect:/users/";
    }
}