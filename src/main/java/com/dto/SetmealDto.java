package com.dto;

import com.domain.DishFlavor;
import com.domain.Setmeal;
import com.domain.SetmealDish;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CWB
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Data
public class SetmealDto extends Setmeal implements Serializable {

    private List<SetmealDish> setmealDishes=new ArrayList();

    private String categoryName;

    private String copies;

}
