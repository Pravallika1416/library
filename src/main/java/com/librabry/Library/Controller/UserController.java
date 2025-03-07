package com.librabry.Library.Controller;

import com.librabry.Library.dto.UserRequest;
import com.librabry.Library.model.User;
import com.librabry.Library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
@PostMapping("/addStudent")
    public User addStudent(@RequestBody UserRequest userRequest)
{
return userService.addStudent(userRequest);
}
    @PostMapping("/addAdmin")
    public User addAdmin(@RequestBody UserRequest userRequest)
    {
return userService.addAdmin(userRequest);
    }

    @GetMapping("/filterUser")
    public List<User> filterUser(@RequestParam("filterBy")String filterType,@RequestParam("operator")String operator,@RequestParam("value")String value){
return userService.filterUser(filterType,operator,value);
    }

}
