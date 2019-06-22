package com.xiaolin.blog.controller.admin;

import com.xiaolin.blog.model.Blog;
import com.xiaolin.blog.model.BlogQuery;
import com.xiaolin.blog.model.User;
import com.xiaolin.blog.service.BlogService;
import com.xiaolin.blog.service.CategoryService;
import com.xiaolin.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size=5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("categories",this.categoryService.getAllCategories());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        model.addAttribute("tags", tagService.getAll());
        return "/admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size=5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        /**
         * here you get the blogQuery object from html file javascript
         */
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "/admin/blogs :: blogList";
    }

    @GetMapping("/blogs/create")
    public String create(Model model){
        model.addAttribute("categories",this.categoryService.getAllCategories());
        model.addAttribute("blog", new Blog());
        model.addAttribute("tags", tagService.getAll());
        return "/admin/blog-create";
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession httpSession){
        blog.setUser((User) httpSession.getAttribute("user"));
        blog.setType(categoryService.getCategory(blog.getType().getId()));
        blog.setTags(tagService.getTags(blog.getTagIds()));
        Blog savedBlog;
        if(blog.getId()!=null){
            savedBlog = this.blogService.updateBlog(blog.getId(), blog);
        }else{
            savedBlog = this.blogService.saveBlog(blog);
        }
        if(savedBlog!=null){
            redirectAttributes.addFlashAttribute("message", "Operation succeed");
        }else{
            redirectAttributes.addFlashAttribute("message", "operation failed");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/create")
    public String update(@PathVariable Long id, Model model){
        Blog blog = this.blogService.getBlog(id);
        blog.init();
        model.addAttribute("categories",this.categoryService.getAllCategories());
        model.addAttribute("blog", blog);
        model.addAttribute("tags", tagService.getAll());
        return "/admin/blog-create";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        this.blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","Delete Operation Succeed");
        return "redirect:/admin/blogs";
    }

}
