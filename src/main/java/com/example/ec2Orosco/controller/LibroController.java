
package com.example.ec2Orosco.controller;

import com.example.ec2Orosco.entity.Autor;
import com.example.ec2Orosco.entity.Editorial;
import com.example.ec2Orosco.entity.Libro;
import com.example.ec2Orosco.repository.AutorRepository;
import com.example.ec2Orosco.repository.EditorialRepository;
import com.example.ec2Orosco.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LibroController {
    @Autowired
    private AutorRepository autorRepository;
     @Autowired
    private EditorialRepository editorialRepository;
    @Autowired
    private LibroRepository libroRepository;

    @RequestMapping("/libros")
    public String libros(Model model) {
        model.addAttribute("libros", libroRepository.findAll());
        return "libros";   
}
    @RequestMapping("/form")
    public String create(Model model) {
        model.addAttribute("auts", autorRepository.findAll());
        model.addAttribute("edts", editorialRepository.findAll());
        return "add";
    }
    @RequestMapping("/add")
    public String guardar(@RequestParam String titulo,@RequestParam int idaut, @RequestParam int idedt, Model model) {
        Libro lib = new Libro();
        Autor aut = autorRepository.findById(idaut).get();
        Editorial edt = editorialRepository.findById(idedt).get();
        lib.setTitulo(titulo);
        lib.setAutor(aut);
        lib.setEditorial(edt);
        libroRepository.save(lib);
        return "redirect:/libros";
    }
    @RequestMapping("/del/{id}")
    public String delete(@PathVariable(value = "id") int id) {
        Libro lib = libroRepository.findById(id).orElse(null);
        libroRepository.delete(lib);
        return "redirect:/libros";
    }
     @RequestMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") int id, Model model) {
        model.addAttribute("auts", autorRepository.findAll());
         model.addAttribute("edts", editorialRepository.findAll());
        model.addAttribute("lib", libroRepository.findById(id).orElse(null));
        return "edit";
    }
    @RequestMapping("/update")
    public String update(@RequestParam int id, @RequestParam String titulo, @RequestParam int autor, @RequestParam int editorial) {
       Libro lib = libroRepository.findById(id).orElse(null);
        Autor aut = autorRepository.findById(autor).get();
       Editorial edt = editorialRepository.findById(editorial).get();
        lib.setTitulo(titulo);
        lib.setAutor(aut);
         lib.setEditorial(edt);
       libroRepository.save(lib);
        return "redirect:/libros";
    }
}
