package com.dto;

import com.domain.Dish;
import com.domain.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Data
public class DishDto extends Dish {


    private List<DishFlavor> flavors=new ArrayList();

    private String categoryName;

    private String copies;
}
