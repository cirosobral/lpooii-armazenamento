package ifba.persistencia.daoMapper;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ifba.persistencia.model.Cliente;

public class ClienteDAO {
	// Cria um EntityManagerFactory como uma constante, apontando para a conexão
	// MySQL-conn, definida no arquivo "resources\META-INF\persistence.xml"
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
			.createEntityManagerFactory("MySQL-conn");

	private EntityManager em;

	public ClienteDAO() {
		// E usa o EntityManagerFactory para criar um objeto da classe EntityManager,
		// que permite operações como criar, ler, atualizar e deletar
		this.em = ENTITY_MANAGER_FACTORY.createEntityManager();
	}

	public Cliente insert(Cliente Cliente) {
		try {
			// Inicia uma transação no EntityManager
			this.em.getTransaction().begin();
			
			// Salva o objeto no banco de dados
			this.em.persist(Cliente);
			
			// Realiza o commit da transação
			this.em.getTransaction().commit();
		} catch (Exception e) {
			// Caso tenha ocorrido alguma exceção de persistência, será feito o rollback da
			// operação
			this.em.getTransaction().rollback();
		}

		return Cliente;
	}

	public Cliente update(Cliente Cliente) {
		try {
			// Inicia uma transação no EntityManager
			this.em.getTransaction().begin();
			
			// Atualiza os dados do objeto no banco de dados
			this.em.merge(Cliente);
			
			// Realiza o commit da transação
			this.em.getTransaction().commit();
		} catch (Exception e) {
			// Caso tenha ocorrido alguma exceção de persistência, será feito o rollback da
			// operação
			this.em.getTransaction().rollback();
		}

		return Cliente;
	}

	public Cliente getById(int id) {
		// Cria uma variável cliente sem instanciar o objeto
		Cliente Cliente = null;

		try {
			// Busca no banco de dados o objeto da classe cliente com o id fornecido
			Cliente = this.em.find(Cliente.class, id);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return Cliente;
	}

	public List<Cliente> getAll() throws SQLException {
		// Cria uma lista de clientes
		List<Cliente> Clientes = null;

		try {
			// Cria uma consulta para a classe Cliente, executa a consulta e extrai os objetos da classe Cliente
			Clientes = em.createQuery("FROM Cliente c", Cliente.class).getResultList();
		} catch (NoResultException e) {
			System.err.println(e);
		}

		return Clientes;
	};

	private List<Cliente> getBy(String field, String value) throws SQLException {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteria = builder.createQuery(Cliente.class);
		Root<Cliente> from = criteria.from(Cliente.class);
		criteria.select(from);
		criteria.where(builder.equal(from.get(field), value));

		TypedQuery<Cliente> query = em.createQuery(criteria);

		List<Cliente> Clientes = null;

		try {
			// Obtem o objeto e faz a saída do objeto cliente
			Clientes = query.getResultList();
		} catch (NoResultException e) {
			System.err.println(e);
		}

		return Clientes;
	}

	public List<Cliente> getByNome(String fname) throws SQLException {
		return getBy("nome", fname);
	}

	@Override
	protected void finalize() throws Throwable {
		this.em.close();
		super.finalize();
	}
}
