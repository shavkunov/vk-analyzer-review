package ru.spbau.shavkunov.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller used to map address of website to index.html.
 * In tutorials people write only word index in return statement. But in my case it doesn't work.
 * Why? I spent a lot of time finding this bug.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public @NotNull String index() {
        return "index.html";
    }
}
