package ifba.persistencia.daoMapper;

import java.util.List;

import ifba.persistencia.model.Cliente;

public class App {
	public static void main(String[] args) {

		System.out.println("MySQL DAO Mapper Testing");

		try {
			// Cria o DAO para cliente, que vai realizar a conexão com o banco de dados
			ClienteDAO cDAO = new ClienteDAO();

			// Cria um cliente e define seus atributos
			Cliente novoCliente = new Cliente();
			novoCliente.setNome("Supla");
			novoCliente.setSobrenome("Papito");

			// Insere o cliente no banco de dados
			novoCliente = cDAO.insert(novoCliente);

			// Exibe o id do novo cliente armazenado no banco de dados
			System.out.println("Cliente inserido com id: " + novoCliente.getId());

			// Recupera a lista de todos os clientes
			List<Cliente> clientes = cDAO.getAll();

			// Para cada cliente na lista de clientes
			for (Cliente cliente : clientes) {
				// Exibe o nome completo
				System.out.println(cliente.nomeCompleto());
			}
			
			System.out.println("---------------------------");

			// Recupera o cliente com id = 4
			Cliente x = cDAO.getById(4);

			// Se o cliente existir
			if (x != null) {
				// Exibe o nome completo
				System.out.println(x.nomeCompleto());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}