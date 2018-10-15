package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	public String form(Evento e) {
		
		er.save(e);
		
		return "redirect:/eventos";
	}
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos(Evento e) {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> eventos = er.findAll();
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@RequestMapping(value = "/deletar/{id}")
	public String deletarEvento(@PathVariable("id") long id) {
		Evento evento = er.findById(id);
		er.delete(evento);
		return "redirect:/eventos";
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
	public String adicionarConvidado(@PathVariable("id") long id, @Valid Convidado convidado) {
		Evento evento = er.findById(id);
		convidado.setEvento(evento);
		cr.save(convidado);
		
		return "redirect:/{id}";
	}
	
}
