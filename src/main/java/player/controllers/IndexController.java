package player.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import player.services.TrackService;

import java.util.List;

@Controller
public class IndexController {

    private final TrackService trackService;

    public IndexController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tracks", trackService.getAllTracks());
        return "index";
    }
}