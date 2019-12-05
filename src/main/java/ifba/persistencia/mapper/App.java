package ifba.persistencia.mapper;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import ifba.persistencia.model.Cliente;

public class App {

	public static void main(String[] args) {

		System.out.println("MySQL ORM Testing");

		// Cria um EntityManagerFactory apontando para a conexão MySQL-conn, definida no
		// arquivo "resources\META-INF\persistence.xml"
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQL-conn");

		// E usa o EntityManagerFactory para criar um objeto da classe EntityManager,
		// que permite operações como criar, ler, atualizar e deletar
		EntityManager em = emf.createEntityManager();

		try {
			// Inicia uma transação no EntityManager
			em.getTransaction().begin();

			// Cria e define valores para novo cliente
			Cliente novoCliente = new Cliente();
			novoCliente.setNome("Zé");
			novoCliente.setSobrenome("Goiaba");

			try {
				// Salva o objeto no banco de dados
				em.persist(novoCliente);

				// Realiza o commit da transação
				em.getTransaction().commit();

				// Exibe o id do novo cliente armazenado no banco de dados
				System.out.println("Cliente inserido com id: " + novoCliente.getId());
			} catch (PersistenceException e) {
				// Caso tenha ocorrido alguma exceção de persistência, será feito o rollback da
				// operação
				em.getTransaction().rollback();

				// Relança a exceção para ser capturada pelo bloco try-catch externo
				throw e;
			}

			// O c minúsculo refere-se ao objeto e o Cliente com C maiúsculo se refere à
			// classe Cliente
			String strQuery = "SELECT c FROM Cliente c WHERE c.id IS NOT NULL";

			// Cria uma consulta para a classe Cliente
			TypedQuery<Cliente> tq = em.createQuery(strQuery, Cliente.class);

			// Cria uma lista de clientes
			List<Cliente> clientes;
			try {
				// Executa a consulta e extrai os objetos da classe Cliente
				clientes = tq.getResultList();

				// Para cada cliente na lista de clientes
				for (Cliente cliente : clientes) {
					// Exibe o nome completo
					System.out.println(cliente.nomeCompleto());
				}
			} catch (NoResultException e) {
				// Caso a consulta não traga nenhum resultado
				// Relança a exceção para ser capturada pelo bloco try-catch externo
				throw e;
			}
			
			System.out.println("---------------------------");

			// O c minúsculo refere-se ao objeto e o Cliente com C maiúsculo se refere à
			// classe Cliente
			// :id é uma consulta parametrizada com valor a ser definido
			String query = "FROM Cliente c WHERE c.id = :id";

			// Cria uma consulta para a classe Cliente
			tq = em.createQuery(query, Cliente.class);

			// Define que o parâmetro :id da consulta definida acima receberá o valor 3
			tq.setParameter("id", 3);

			Cliente x = null;
			try {
				// Obtem o objeto único
				x = tq.getSingleResult();

				// Exibe o nome completo
				System.out.println(x.nomeCompleto());
			} catch (NoResultException e) {
				// Caso a consulta não traga nenhum resultado
				// Relança a exceção para ser capturada pelo bloco try-catch externo
				throw e;
			}

			// Outra forma de fazer a mesma coisa (substitui da linha 77 a 88)
			// Encontra o cliente de id = 3
			x = em.find(Cliente.class, 3);

			// Se o cliente existir
			if (x != null) {
				// Exibe o nome completo
				System.out.println(x.nomeCompleto());
			}
		} catch (PersistenceException e) {
			System.err.println(e);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
