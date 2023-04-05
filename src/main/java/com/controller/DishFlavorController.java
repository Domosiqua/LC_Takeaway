package com.controller;


import com.domain.DishFlavor;
import com.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("dishflavor")
public class DishFlavorController {
    @Autowired
    private DishFlavorService service;
}
