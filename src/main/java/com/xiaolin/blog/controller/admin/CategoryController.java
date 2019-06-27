package com.xiaolin.blog.controller.admin;

import com.xiaolin.blog.model.Type;
import com.xiaolin.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String getCategories(@PageableDefault(size=10,sort={"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                Model model){
        model.addAttribute("page", this.categoryService.listType(pageable));
        return "admin/categories";
    }

    @GetMapping("/categories/create")
    public String createNewCategory(Model model) {
        model.addAttribute("type", new Type());
        return "admin/category-create";
    }

    @GetMapping("/categories/{id}/create")
    public String getCategory(Model model, @PathVariable Long id){
        model.addAttribute("type", this.categoryService.getCategory(id));
        return "admin/category-create";
    }

    @PostMapping("/categories")
    public String post(@Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "admin/category-create";
        }
        Type typeDB = this.categoryService.getCategoryByName(type.getName());
        // check if the record exists in the database
        if(typeDB !=null){
            redirectAttributes.addFlashAttribute("message", "Category already exists");
        }else{
            Type t = this.categoryService.saveCategory(type);
            if(t==null){
                redirectAttributes.addFlashAttribute("message", "Adding Category Failed");
            }else{
                redirectAttributes.addFlashAttribute("message", "Adding Category Succeed");
            }
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{id}")
    public String put(@Valid Type type, BindingResult bindingResult,@PathVariable Long id, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "admin/category-create";
        }
        Type t = this.categoryService.updateType(id,type);
        if(t==null){
            redirectAttributes.addFlashAttribute("message", "Update Category Failed");
        }else{
            redirectAttributes.addFlashAttribute("message", "Update Category Succeed");
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/{id}/delete")
    public String delete(@PathVariable Long id){
        this.categoryService.deleteType(id);
        return "redirect:/admin/categories";
    }

}
