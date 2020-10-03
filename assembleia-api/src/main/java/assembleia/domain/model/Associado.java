package assembleia.domain.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Associado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome;
	@Column(unique = true)
	private long cpf;
	@ManyToMany(mappedBy = "associados")
	private List<Pauta> pautas;
	
	
	public Associado() {};
	
	public Associado(String nome, int cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}

	public int getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public long getCpf() {
		return cpf;
	}
	
	@JsonIgnore
	public List<Pauta> getPautas() {
		return pautas;
	}

}
