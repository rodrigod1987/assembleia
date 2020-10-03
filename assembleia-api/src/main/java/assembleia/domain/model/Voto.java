package assembleia.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import assembleia.domain.enums.VotoEnum;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"associado_id" , "pauta_id"})})
public class Voto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne
	private Associado associado;
	@ManyToOne(optional = false)
	private Pauta pauta;
	private VotoEnum resposta;
	
	public Voto() {
		this.resposta = VotoEnum.Nao;
	}
	
	public int getId() {
		return id;
	}
	
	public VotoEnum getResposta() {
		return resposta;
	}
	
	public Pauta getPauta() {
		return pauta;
	}
	
	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}
	
	public Associado getAssociado() {
		return associado;
	}

	public void setAssociado(Associado associado) {
		this.associado = associado;
	}
	
	@JsonIgnore
	public void responder(VotoEnum voto) {
		this.resposta = voto;
	}
	
}
