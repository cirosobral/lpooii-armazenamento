package ifba.persistencia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ifba.persistencia.jdbc.Cliente;

public class ClienteDAO {
	private Connection conn;

	public ClienteDAO() throws SQLException {
		// Realiza a conexão usando o driver JDBC
		this.conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/javapersistence?serverTimezone=UTC",
				"dbadmin", "senhasupersecreta");
	}

	public int insert(Cliente Cliente) throws SQLException {
		// Define o comando SQL que será enviado ao MySQL através da conexão para
		// inserir um cliente
		String sql = "INSERT INTO `cliente` VALUES (NULL, ?, ?);";

		// Prapara uma declaração (statement), com o SQL acima, antes de enviar para o
		// banco de dados
		PreparedStatement ps = this.conn.prepareStatement(sql);

		// Atribui valores à declaração preparada, nos locais previamente definidos
		// pelas interrogações (essa é a maior utilidade da declaração preparada)
		ps.setString(1, Cliente.getNome());
		ps.setString(2, Cliente.getSobrenome());

		// Executa a declaração no banco de dados, com os valores acima informados
		return ps.executeUpdate();
	}

	public List<Cliente> getAll() throws SQLException {
		// Define a consulta SQL para buscar todos os clientes
		String sql = "SELECT * FROM `cliente`;";

		// Prapara uma declaração com o SQL da consulta
		PreparedStatement ps = this.conn.prepareStatement(sql);

		// Executa a declaração no banco de dados e armazena o resultado no ResultSet
		ResultSet resultSet = ps.executeQuery();

		return this.mapeia(resultSet);
	};

	public Cliente getById(int id) throws SQLException {
		// Define a consulta SQL que busca o cliente com base no id
		String sql = "SELECT * FROM `cliente` WHERE `id` = ?;";

		// Prapara uma declaração (statement), com o SQL acima, antes de enviar a
		// consulta
		PreparedStatement ps = this.conn.prepareStatement(sql);

		// Atribui o valor id à declaração preparada
		ps.setInt(1, id);

		// Executa a declaração no banco de dados e armazena o resultado no ResultSet
		ResultSet resultSet = ps.executeQuery();

		// Executa a declaração no banco de dados e armazena o resultado no ResultSet
		resultSet = ps.executeQuery();

		// Cria a variável cliente, da classe Cliente com valor nulo
		Cliente cliente = null;

		// Verifica se o resultado possui algum resultado
		if (resultSet.first()) {
			// Extrai os campos
			id = resultSet.getInt("id"); // Completamente desnecessário, visto que é o mesmo id passado como parâmetro.
											// Está presente meramente por fins didáticos
			String nome = resultSet.getString("nome");
			String sobrenome = resultSet.getString("sobrenome");

			// Instancia o objeto e atribui o valor a seus atributos
			cliente = new Cliente();
			cliente.setID(id);
			cliente.setNome(nome);
			cliente.setSobrenome(sobrenome);
		}

		return cliente;
	}

	// Método criado para permitir reuso do código que recupera os dados do
	// ResultSet e transforma em uma lista de objetos
	private List<Cliente> mapeia(ResultSet resultSet) throws SQLException {
		// Cria uma lista de clientes
		List<Cliente> clientes = new ArrayList<Cliente>();

		// Percorre todos os resultados do ResultSet
		while (resultSet.next()) {
			// Para cada resultado extrai os campos
			int id = resultSet.getInt("id");
			String nome = resultSet.getString("nome");
			String sobrenome = resultSet.getString("sobrenome");

			// Cria o objeto e atribui o valor a seus atributos
			Cliente obj = new Cliente();
			obj.setID(id);
			obj.setNome(nome);
			obj.setSobrenome(sobrenome);

			// Então adciona o objeto criado na lista de clientes
			clientes.add(obj);
		}

		return clientes;
	}

	// Método criado para generalizar a busca por um valor do tipo String (pode ser
	// usado para buscar por nome ou sobrenome), realizando reuso de código
	private List<Cliente> getBy(String campo, String valor) throws SQLException {
		// Define a consulta SQL que busca o cliente com base no campo informado no
		// parâmetro campo da função
		String sql = "SELECT * FROM `Cliente` WHERE `" + campo + "` = ?;";

		// Prapara uma declaração (statement), com o SQL acima, antes de enviar a
		// consulta
		PreparedStatement ps = this.conn.prepareStatement(sql);

		// Atribui a variável valor à declaração preparada
		ps.setString(1, valor);

		// Executa a declaração no banco de dados e armazena o resultado no ResultSet
		ResultSet resultSet = ps.executeQuery();

		return this.mapeia(resultSet);
	}

	public List<Cliente> getByNome(String nome) throws SQLException {
		// Chama o método getBy definindo o parâmetro campo como "nome" e o parâmetro
		// valor com o valor da variável nome
		return this.getBy("nome", nome);
	}

	public boolean update(Cliente Cliente) throws SQLException {
		// Define o comando SQL que será enviado ao MySQL através da conexão para
		// atualizar um cliente
		String sql = "UPDATE `cliente` SET `nome` = ?, `sobrenome` = ? WHERE `id` = ?;";

		// Prapara uma declaração (statement), com o SQL acima, antes de enviar para o
		// banco de dados
		PreparedStatement ps = this.conn.prepareStatement(sql);

		// Atribui valores à declaração preparada, nos locais previamente definidos
		// pelas interrogações
		ps.setString(1, Cliente.getNome());
		ps.setString(2, Cliente.getSobrenome());
		ps.setInt(3, Cliente.getID());

		// Executa a declaração no banco de dados, com os valores acima informados
		return ps.execute();
	}

	public boolean delete(int id) throws SQLException {
		// Define o comando SQL que será enviado ao MySQL através da conexão para
		// remover um cliente
		String sql = "DELETE `cliente` WHERE `id` = ?;";

		// Prapara uma declaração (statement), com o SQL acima, antes de enviar para o
		// banco de dados
		PreparedStatement ps = this.conn.prepareStatement(sql);

		// Atribui o valor id à declaração preparada
		ps.setInt(1, id);

		// Executa a declaração no banco de dados, com o id informado acima
		return ps.execute();
	}
}
