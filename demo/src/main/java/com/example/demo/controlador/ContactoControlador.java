package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.modelo.Contacto;
import com.example.demo.repositorio.ContacoRepositorio;

@Controller
public class ContactoControlador {

    @Autowired
    private ContacoRepositorio contactoRepositorio;

    @GetMapping({"/",""})
    public String verPaginaInicio(Model modelo){
        List<Contacto> contactos = contactoRepositorio.findAll();
        modelo.addAttribute("contactos", contactos);
        return "index";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistrarContacto(Model modelo){
        modelo.addAttribute("contacto", new Contacto());
        return "nuevo";
    }

    @PostMapping("/nuevo")
    public String guardarContacto(@Validated Contacto contacto,BindingResult bindingResult, RedirectAttributes redirect,Model modelo){
        if(bindingResult.hasErrors()){
            modelo.addAttribute("contacto", contacto);
            return "nuevo";
        }
        contactoRepositorio.save(contacto);
        redirect.addFlashAttribute("msgExito", "El contacto ha sido agregado con exitó");
        return "redirect:/";

    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEditarContacto(@PathVariable Integer id, Model modelo){
        Contacto contacto = contactoRepositorio.getById(id);
        modelo.addAttribute("contacto", contacto);
        return "nuevo";
    }

     @PostMapping("/{id}/editar")
    public String actualizarContacto(@PathVariable Integer id,@Validated Contacto contacto,BindingResult bindingResult, RedirectAttributes redirect,Model modelo){
        Contacto contactoBD = contactoRepositorio.getById(id);
        if(bindingResult.hasErrors()){
            modelo.addAttribute("contacto", contacto);
            return "nuevo";
        }

        contactoBD.setNombre(contacto.getNombre());
        contactoBD.setCelular(contacto.getCelular());
        contactoBD.setEmail(contacto.getEmail());
        contactoBD.setFechaNacimiento(contacto.getFechaNacimiento());

        contactoRepositorio.save(contactoBD);
        redirect.addFlashAttribute("msgExito", "El contacto ha sido actualizado correctamente");
        return "redirect:/";

    }

    @PostMapping("/{id}/eliminar")
     public String eliminarContacto(@PathVariable Integer id,RedirectAttributes redirect){
        Contacto contacto = contactoRepositorio.getById(id);
        contactoRepositorio.delete(contacto);

        redirect.addFlashAttribute("msgExito", "El contacto ha sido eliminado de manera exitosa");

        return "redirect:/";
        
    }

    
}
