package ifba.persistencia.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * A Java Persistence API (JPA) facilita o uso de dados de objetos
 * com um banco de dados. Com ele, você pode persistir os dados do
 * objeto em um banco de dados.
 *
 * Hibernate é um sistema de mapeamento relacional de objetos que
 * implementa o JPA.
 */

// O uso da anotação @Entity define quais objetos devem ser persistidos no banco de dados
@Entity
// Define o nome da tabela criada para a entidade
@Table(name = "cliente")
public class Cliente implements Serializable {
	/**
	 * Número de identificação da classe
	 */
	private static final long serialVersionUID = -1994028445102527639L;

	// Todas as entidades devem definir uma chave primária
	@Id
	// Gera automaticamente o valor para essa variável
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Define que a coluna não pode ter valores duplicados
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	// Define que a coluna não pode ter valor nulo
	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String sobrenome;

	// Getters e setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	// Métodos próprios da classe cliente
	public String nomeCompleto() {
		return this.nome + " " + this.sobrenome;
	}
}
