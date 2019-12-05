package ifba.persistencia.jdbc;

public class Cliente {
	private int id;
	private String nome;
	private String sobrenome;

	public int getID() {
		return id;
	}

	public void setID(int id) {
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
