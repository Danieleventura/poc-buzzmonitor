# poc-buzzmonitor


Projeto desenvolvido para conhecimento das tecnologias Java Servlets, ElasticSearch, Tomcat e JSON.


Desenvolvido durante estágio Back End na Buzzmonitor.

### Objetivo

Aplicar os conhecimentos estudados ao longo da semana em um projeto que integra algumas das
tecnologias que utilizamos no dia-a-dia.


#### O que deve ser feito?
Você deve criar uma API que, utilizando uma base de dados aleatória, deverá gerar os
dados necessários para que gráficos de agrupamentos sejam disponibilizados na interface do nosso
produto.

 - Os agrupamentos serão: por sentimento, por serviço e por dia.
 - Deverá ser possível escolher o tipo do agrupamento desejado.
 - Deverá ser possível filtrar os dados: por serviço e por datas.
 - Utilizar Java Servlets para a aplicação web e ElasticSearch como banco de dados.
 - Implementar script para inserção dos dados(posts) disponibilizados no seu ElasticSearch local.
 - Exemplo de formato de saída esperado (agrupamento por serviço):

Exemplo de formato de saída esperado (agrupamento por serviço):

    {
    "data": [
    {
    "label":
    "count":
    },
    {
    "label":
    "count":
    },
    {
    "label":
    "count":
    }
    ]
    "youtube",
    10
    "facebook",
    23
    "twitter",
    13
    }

#### O que deve ser testado?
- Dados agrupados por sentimento
- Dados agrupados por serviço
- Dados agrupados por dia
- Filtrar por período (since-until)
- Filtrar por serviço


#### Para rodar o projeto é necessário:
- Tomcat instalado no Eclipse e rodando localmente

    -https://geekrewind.com/how-to-install-tomcat-on-ubuntu-linux/
    
    -https://www.devmedia.com.br/instalacao-e-configuracao-do-apache-tomcat-no-eclipse/27360
    
- Possuir o banco de dados ElasticSearch instalado
- Ter inserido os dados no banco de dados, esses dados estão disponiveis no dados-original.json
