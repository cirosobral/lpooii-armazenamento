package ifba.persistencia.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {

	public static void main(String[] args) {

		System.out.println("MySQL JDBC Testing");

		try {
			// Realiza a conexão usando o driver JDBC
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/javapersistence?serverTimezone=UTC", "dbadmin",
					"senhasupersecreta");
			
			// Define o comando SQL que será enviado ao MySQL através da conexão para
			// inserir um cliente
			String SQL_INSERT = "INSERT INTO `cliente` VALUES (NULL, ?, ?);";

			// Prapara uma declaração (statement), com o SQL acima, antes de enviar para o
			// banco de dados
			PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT);

			// Atribui valores à declaração preparada, nos locais previamente definidos
			// pelas interrogações (essa é a maior utilidade da declaração preparada)
			preparedStatement.setString(1, "Fulano");
			preparedStatement.setString(2, "de Tal");

			// Executa a declaração no banco de dados, com os valores acima informados
			int id = preparedStatement.executeUpdate();

			// Exibe o id do novo cliente armazenado no banco de dados
			System.out.println("Cliente inserido com id: " + id);

			// Define a consulta SQL para buscar todos os clientes
			String SQL_SELECT = "SELECT * FROM `cliente`;";

			// Prapara novamente uma declaração, dessa vez com o SQL da consulta
			preparedStatement = conn.prepareStatement(SQL_SELECT);

			// Executa a declaração no banco de dados e armazena o resultado no ResultSet
			ResultSet resultSet = preparedStatement.executeQuery();

			// Cria uma lista de clientes
			List<Cliente> clientes = new ArrayList<Cliente>();

			// Percorre todos os resultados do ResultSet
			while (resultSet.next()) {
				// Para cada resultado extrai os campos
				id = resultSet.getInt("id");
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

			// Para cada cliente na lista de clientes
			for (Cliente cliente : clientes) {
				// Exibe o nome completo
				System.out.println(cliente.nomeCompleto());
			}
			
			System.out.println("---------------------------");

			// Define a consulta SQL que busca o cliente com base no id
			String SQL_SELECT_WHERE = "SELECT * FROM `cliente` WHERE `id` = ?;";

			// Prapara uma declaração (statement), com o SQL acima, antes de enviar a
			// consulta
			preparedStatement = conn.prepareStatement(SQL_SELECT_WHERE);

			// Atribui o valor 1 à declaração preparada, para que seja buscado o cliente com
			// id = 1
			preparedStatement.setInt(1, 1);

			// Executa a declaração no banco de dados e armazena o resultado no ResultSet
			resultSet = preparedStatement.executeQuery();

			// Verifica se o resultado possui algum resultado
			if (resultSet.first()) {
				// Extrai os campos
				id = resultSet.getInt("id");
				String nome = resultSet.getString("nome");
				String sobrenome = resultSet.getString("sobrenome");

				// Cria o objeto e atribui o valor a seus atributos
				Cliente x = new Cliente();
				x.setID(id);
				x.setNome(nome);
				x.setSobrenome(sobrenome);

				// E exibe o nome completo
				System.out.println(x.nomeCompleto());
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
