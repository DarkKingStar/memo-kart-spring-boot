package com.sudipta.admin.dto;

import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private CategoryDTO parentCategory;
    private int level;
}
