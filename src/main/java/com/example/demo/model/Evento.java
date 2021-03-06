package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity	
public class Evento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@javax.validation.constraints.NotEmpty
	@Column(length = 50)
	private String nome;
	
	@javax.validation.constraints.NotEmpty
	private String local;
	
	@javax.validation.constraints.NotEmpty
	@Column(length = 20)
	private String data;
	
	@javax.validation.constraints.NotEmpty
	@Column(length = 20)
	private String horario;
	
	private String imagem;
	
	@OneToMany
	private List<Convidado> convidados;
	
	private int qtd_convidados;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public List<Convidado> getConvidados() {
		return convidados;
	}
	public void setConvidados(List<Convidado> convidados) {
		this.convidados = convidados;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imageURL) {
		this.imagem = imageURL;
	}
	public int getQtd_convidados() {
		return qtd_convidados;
	}
	public void setQtd_convidados(int qtd_convidados) {
		this.qtd_convidados = qtd_convidados;
	}
}
