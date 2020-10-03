package assembleia.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import assembleia.domain.enums.Situacao;

@Entity
public class Pauta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String descricao;
	private LocalDate data;
	private Situacao situacao;
	private int tempoExecucao = 60;
	private LocalDate dataInicio;
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "Pauta_Associado", 
		joinColumns = { @JoinColumn(name = "pauta_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "associado_id") },
		uniqueConstraints = { @UniqueConstraint(columnNames = {"pauta_id", "associado_id"})})
	private List<Associado> associados = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "pauta")
	private List<Voto> votos = new ArrayList<>();

	public Pauta() {
		this.data = LocalDate.now();
	}

	public Pauta(int tempoExecucao) {
		super();
		this.tempoExecucao = tempoExecucao;
	}

	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public LocalDate getData() {
		return data;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public int getTempoExecucao() {
		return tempoExecucao;
	}

	public void setTempoExecucao(int seconds) {
		this.tempoExecucao = seconds;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public List<Associado> getAssociados() {
		return associados;
	}

	public List<Voto> getVotos() {
		return this.votos;
	}

	@JsonIgnore
	public void iniciar() {
		this.dataInicio = LocalDate.now();
		this.situacao = Situacao.Iniciada;
	}

	@JsonIgnore
	public void finalizar() {
		this.situacao = Situacao.Finalizada;
	}

	@JsonIgnore
	public void addAssociado(Associado associado) {
		this.associados.add(associado);
	}

	@JsonIgnore
	public boolean isFinalizada() {
		return this.situacao.equals(Situacao.Finalizada);
	}

}
