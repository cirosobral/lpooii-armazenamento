# Ordem de execução

Para evitar erros na execução, siga os passos a seguir:

0.  Baixe todo esse projeto no seu computador.
1.  Rode, no **MySQL**, o script contido no arquivo XXX.sql para criar o banco de dados, as tabelas e o usuário dbadmin com as permissões necessárias.
2.  Importe o projeto na sua IDE.
3.  Execute os códigos na seguinte ordem:
	1. ifba.persistencia.jdbc.App
	2. ifba.persistencia.dao.App
	3. ifba.persistencia.mapper.App
	4. ifba.persistencia.daoMapper.App

## Descrição do método de conexão

As classes *ifba.persistencia.jdbc.App* e *ifba.persistencia.dao.App* usam o **JDBC** para conexão com o banco de dados. Com isso, os códigos SQL vão direto para os arquivos Java.

Já as classes *ifba.persistencia.mapper.App* e *ifba.persistencia.daoMapper.App* usam o **Hibernate**, que é um framework para o mapeamento objeto-relacional, que ele sim usa o JDBC para conexão com o banco de dados. Desse modo, é o Hibernate que gera o SQL.

### Propriedades do Hibernate

Quando estiver testando as classes que usam o **Hibernate**, procure mexer nas propriedades presentes no arquivo *resources\META-INF\persistence.xml* para observar mudanças no funcionamento do sistema.

#### hibernate.hbm2ddl.auto

`validate`: valida o schema, sem fazer mudanças no banco de dados.
`update`: realiza apenas update no schema.
`create`: cria o schema, destruindo dados anteriores.
`create-drop`: cria o schema e dropa quando ao terminar a execução.

#### hibernate.show_sql

`true`: exibe todo o SQL que está sendo executado.
`false`: executa o SQL sem exibir.
