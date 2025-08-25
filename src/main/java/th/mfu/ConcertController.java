package th.mfu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConcertController {
    private HashMap<Integer, Concert> concerts = new HashMap<>();
    private int nextId = 1;

    @InitBinder
    public final void initBinderUsuariosForm(final WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping("/concerts")
    public String listConcerts(Model model) {
        model.addAttribute("concerts", new ArrayList<>(concerts.values()));
        return "list-concert";
    }

    @GetMapping("/add-concert")
    public String addAConcertForm(Model model) {
        model.addAttribute("concert", new Concert());
        return "add-concert-form";
    }

    @PostMapping("/concerts")
    public String saveConcert(@ModelAttribute Concert concert) {
        concert.setId(nextId);
        concerts.put(nextId, concert);
        nextId++;
        return "redirect:/concerts";
    }

    @GetMapping("/delete-concert/{id}")
    public String deleteConcert(@PathVariable int id) {
        concerts.remove(id);
        return "redirect:/concerts";
    }

    @GetMapping("/delete-concert")
    public String removeAllConcerts() {
        concerts.clear();
        nextId = 1;
        return "redirect:/concerts";
    }
}