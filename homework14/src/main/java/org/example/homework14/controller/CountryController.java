package org.example.homework14.controller;

import jakarta.validation.Valid;
import org.example.homework14.entity.City;
import org.example.homework14.entity.Country;
import org.example.homework14.entity.Flag;
import org.example.homework14.service.CountryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/countries")
public class CountryController {

    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "name") String sort,
            Model model) {

        Pageable pageable = PageRequest.of(page, 5, Sort.by(sort));
        model.addAttribute("countries", service.getAll(pageable));
        return "countries";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Country country = new Country();
        country.setFlag(new Flag());
        country.getCities().add(new City());

        model.addAttribute("country", country);
        return "country-form";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute Country country,
            BindingResult result) {

        if (result.hasErrors()) {
            return "country-form";
        }

        service.save(country);
        return "redirect:/countries";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("country", service.getById(id));
        return "country-view";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/countries";
    }
}
