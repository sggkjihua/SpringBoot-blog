package com.xiaolin.blog.controller.admin;

import com.xiaolin.blog.model.Type;
import com.xiaolin.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String getCategories(@PageableDefault(size=10,sort={"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                Model model){
        model.addAttribute("page", this.categoryService.listType(pageable));
        return "/admin/categories";
    }

    @GetMapping("/categories/create")
    public String createNewCategory(){
        return "/admin/category-create";
    }

    @PostMapping("/categories")
    public String post(Type type){
        System.out.println("we come here!!!!!!!");
        Type t = this.categoryService.saveCategory(type);
        if(t==null){
            System.out.println("t is null");
        }else{
            System.out.println("t is not null and "+ t.toString());
        }
        return "redirect:/admin/categories";
    }

}
