## Docker setup
This project uses docker containers in order to provide starters. 
Because of this you need to configure docker to be accessible from your linux user. To enable this, please proceed with the steps below.

1. Add your linux user to the docker group
`sudo usermod -aG docker $(whoami)`
2. Restart your machine

## Sobre os containers de banco de dados

Os testes sobem automaticamente 3 instâncias de banco de dados, incluindo Oracle, SQLServer e Postgre.

Segue abaixo os dados de acesso as instâncias. 

**Observação**: Onde você ler `$(whoami)` substitua pelo nome do usuário da máquina. Você pode usar o `$(whoami)` no terminal do linux para descobrir o seu usuário. 

### Oracle
usuário: `$(whoami)`

senha: oracle

host: localhost

porta: 1521

### SQLServer 
usuário: sa

senha: $qlS3rver

host: localhost

porta: 1433

database: `$(whoami)` 

### Postgres 
usuário: `$(whoami)`

senha: postgre

host: localhost

porta: 5432

database: `$(whoami)`

## Como executar a aplicação local?

Executar a main class `agent.pipeline.core.application.AgentPipeline` passando os argumentos:
 
`--environment=LOCAL`
`--ERPDatabaseUsername=$(whoami)`
`--TMOVDatabaseUsername=sa`
`--TMOVDatabaseName=$(whoami)`
`--eventDatabaseUsername=$(whoami)`
`--eventDatabaseName=$(whoami)`