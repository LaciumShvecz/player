package player.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {


    @GetMapping({"", "/", "/index"})
    public String homePage(Model model) {
        try {

            System.out.println("Загружено концертов: " );

            return "index";
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке концертов: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("concerts", List.of()); // Пустой список
            return "index";
        }
    }
}