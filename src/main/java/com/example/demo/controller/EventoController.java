package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Convidado;
import com.example.demo.model.Evento;
import com.example.demo.repository.ConvidadoRepository;
import com.example.demo.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository er;
	
	@Autowired
	private ConvidadoRepository cr;
	
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String form(@Valid Evento e, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos do formulário");
			attributes.addFlashAttribute("flag", 1);
			return "redirect:/eventos";
		}
		if(e.getImagem().equals(""))
			e.setImagem("http://www.bsmc.net.au/wp-content/uploads/No-image-available.jpg");
		er.save(e);
		attributes.addFlashAttribute("mensagem", "Evento "+e.getNome()+" cadastrado com sucesso!");
		attributes.addFlashAttribute("flag", 0);
		return "redirect:/eventos";
	}
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos(Evento e) {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> eventos = er.findAll();
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@RequestMapping(value = "/editarEvento", method = RequestMethod.GET)
	public ModelAndView editarEvento(long id) {
		Evento e = er.findById(id);
		ModelAndView mv = new ModelAndView("evento/editarEvento");
		mv.addObject("evento", e);
		
		return mv;
	}
	
	@RequestMapping(value = "/editarEvento", method = RequestMethod.POST)
	public String editarEvento(@Valid Evento e, BindingResult result, RedirectAttributes attributes ) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos do formulário");
			attributes.addFlashAttribute("flag", 1);
			return "redirect:/editarEventos";
		}
		er.save(e);
		return "redirect:/"+e.getId();
	}
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long id) {
		Evento evento = er.findById(id);
		er.delete(evento);
		return "redirect:/eventos";
	}
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String cpf) {
		Convidado convidado = cr.findByCpf(cpf);
		cr.delete(convidado);
		
		Evento evento = convidado.getEvento();
		
		return "redirect:/"+evento.getId();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("id") long id) {
		Evento e = er.findById(id);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento", e);
		
		Iterable<Convidado> convidados = cr.findByEvento(e);
		mv.addObject("convidados", convidados);
		
		return mv;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String adicionarConvidado(@PathVariable("id") long id, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos do formulário");
			attributes.addFlashAttribute("flag", 1);
			return "redirect:/{id}";
		} else {
			
			Evento e = er.findById(id);
			List<Convidado> convidados = (List<Convidado>) cr.findByEvento(e);
			for (Convidado c : convidados) {
				System.out.println("entrou\nC.cpf: "+c.getCpf()+"\nconvidado.cpf:"+convidado.getCpf()+"\n\n\n");
				if(c.getCpf().equals(convidado.getCpf())) {
					attributes.addFlashAttribute("mensagem", "O CPF informado já está em uso!");
					attributes.addFlashAttribute("flag", 1);
					return "redirect:/{id}";
				}
			}
			
			Evento evento = er.findById(id);
			convidado.setEvento(evento);
			cr.save(convidado);
			attributes.addFlashAttribute("mensagem", "O convidado "+ convidado.getNome() +" foi adicionado!");
			attributes.addFlashAttribute("flag", 0);
			return "redirect:/{id}";
		}
		
	}
	
}
