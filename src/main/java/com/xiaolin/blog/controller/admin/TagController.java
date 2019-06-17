package com.xiaolin.blog.controller.admin;


import com.xiaolin.blog.model.Tag;
import com.xiaolin.blog.service.TagService;
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

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String getTags(@PageableDefault(size=10,sort={"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                Model model){
        model.addAttribute("page", this.tagService.listTag(pageable));
        return "/admin/tags";
    }

    @GetMapping("/tags/create")
    public String createNewTag(Model model) {
        model.addAttribute("tag", new Tag());
        return "/admin/tag-create";
    }

    @GetMapping("/tags/{id}/create")
    public String getTag(Model model, @PathVariable Long id){
        model.addAttribute("tag", this.tagService.getTag(id));
        return "/admin/tag-create";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "admin/tag-create";
        }
        Tag tagDB = this.tagService.getTagByName(tag.getName());
        // check if the record exists in the database
        if(tagDB !=null){
            redirectAttributes.addFlashAttribute("message", "Tag already exists");
        }else{
            Tag t = this.tagService.saveTag(tag);
            if(t==null){
                redirectAttributes.addFlashAttribute("message", "Adding Tag Failed");
            }else{
                redirectAttributes.addFlashAttribute("message", "Adding Tag Succeed");
            }
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String put(@Valid Tag tag, BindingResult bindingResult, @PathVariable Long id, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "admin/category-create";
        }
        Tag t = this.tagService.updateTag(id,tag);
        if(t==null){
            redirectAttributes.addFlashAttribute("message", "Update Tag Failed");
        }else{
            redirectAttributes.addFlashAttribute("message", "Update Tag Succeed");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id){
        this.tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }
}
