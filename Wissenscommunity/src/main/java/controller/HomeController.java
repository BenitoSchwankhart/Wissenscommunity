package controller;


import service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

  @Autowired
  private UserService userService;


  /**
   * Request-Mapping für die Startseite.
   * @return home-Seite.
   */
  @GetMapping("/")
  public String showHome(Model model, Authentication authentication) {
    model.addAttribute("user", userService.currentUser(authentication));
    return "home";
  }

  /**
   * Request-Mapping für die Login-Seite.
   *
   * @return login-Seite.
   */
  @GetMapping("/login")
  public String showLogin() {
    return "login";
  }

  @GetMapping("/home.html")
  public  String showAddHome( Authentication authentication, Model model){
    model.addAttribute("user", userService.currentUser(authentication));
    return "home";
  }

}
