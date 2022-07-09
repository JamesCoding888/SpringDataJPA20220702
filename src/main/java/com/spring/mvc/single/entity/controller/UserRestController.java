package com.spring.mvc.single.entity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 在每個方法會自動加上 @ResponseBody
@RequestMapping("/rest/user")
public class UserRestController {

}
