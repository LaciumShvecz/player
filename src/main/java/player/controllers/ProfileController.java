package player.controllers;

import player.models.User;
import player.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserServiceImpl userService;


    @GetMapping("/user")
    public String showProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = auth.getName();
        User user = userService.getUserByUsername(username);

        if (user != null) {

            model.addAttribute("user", user);

            return "user";
        } else {
            // Если пользователь не найден, редирект на главную
            return "redirect:/";
        }
    }


    @PostMapping("/user/bookings/{id}/cancel")
    public String cancelBooking(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = auth.getName();
        User user = userService.getUserByUsername(username);

        if (user != null) {
            try {

            } catch (Exception e) {
                // Можно добавить flash сообщение об ошибке
                System.err.println("Ошибка при отмене бронирования: " + e.getMessage());
                // Логирование ошибки
                e.printStackTrace();
            }
        }

        return "redirect:/profile";
    }
    @GetMapping("/profile")
    public String showProfile() {
        // Редирект с /profile на /user
        return "redirect:/user";
    }
}