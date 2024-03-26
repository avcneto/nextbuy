## nextBuy

![](https://imgur.com/38i1Shu.png)
![Licença](https://img.shields.io/badge/license-MIT-green)
![Badge em Desenvolvimento](https://img.shields.io/badge/release%20date-mar/24-yellow)
![Maven Version](https://img.shields.io/badge/maven-4.0.0-blue)
![Java Version](https://img.shields.io/badge/java-17-blue)

# <h1 align="center">NextBuy</h1>

Apresentamos o **NextBuy**, uma inovadora ferramenta que revolucionará a forma como você gerencia seu e-commerce bem como a experiência de compra dos seus usuários.
Com nosso sistema em sua mão, você pode se cadastrar rapidamente, os items a venda em sua loja, cadastras seus usuários, o carrinho de compras dos seus usuários e forma de pagamento.
Simplifique sua vida, torne-se mais sustentável e economize tempo com nossa ferramenta completa de gestão.

## 📄 Índice

- [Descrição do Projeto](#descrição-do-projeto)
- [Arquitetos Responsáveis](#arquitetos-responsáveis)
- [Funcionalidades](#funcionalidades)
- [Acesso ao Projeto](#acesso-ao-projeto)
- [Execução do Projeto](#execução-do-projeto)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Acesso ao Banco de Dados](#acesso-ao-banco-de-dados)
- [Relatório Técnico](#relatório-técnico)
- [Desafios](#desafios)
- [Documentação Técnica](#documentação-técnica)

## Descrição do Projeto

Este projeto visa criar uma solução abrangente para a gestão eficiente de um sistema de compras e gestão de usuários
proporcionando uma experiência fluida tanto para os proprietários quanto para os clientes. Com um sistema web completo,
o foco principal é oferecer interfaces intuitivas e endpoints para o cadastro e gerenciamento de clientes, produtos para venda,
gestão de carrinho de compras e meio de pagamento.

Os benefícios de utilização são múltiplos. Para os proprietários, o sistema oferece uma gestão centralizada e eficiente
de todos os aspectos do seu negócio, desde o cadastro detalhado dos usuários e dos produtos a venda até o acompanhamento dos carrinhos de compras e
gastos dos clientes. Isso permite uma melhor tomada de decisões, otimização de recursos e um controle mais preciso sobre
as operações.

Para os clientes, o sistema proporciona uma experiência mais personalizada e conveniente. Eles podem facilmente realizar
buscas pelo nome do produto ou ID de cadastro do produto, podem adicionar os produtos no carrinho de compras que irá calcular o valor total
com base nas quantidades e valores de cada produto. Também é possível excluir os produtos dos carrinhos Além disso, após finalizar a escolhas dos produtos
o cliente pode finalizar a compra escolhendo o método de pagamento que lhe convém.

Em resumo, este projeto visa revolucionar a forma como os e-commerces gerenciam seus
negócios e interagem com seus clientes, proporcionando uma plataforma completa e eficiente para atender às suas
necessidades.

## Arquitetos Responsáveis

| [<img src="https://avatars.githubusercontent.com/u/42851702?v=4" width=115><br><sub>Lucas Mendes</sub>](https://github.com/Luzeraaa) | [<img src="https://avatars.githubusercontent.com/u/56560361?v=4" width=115><br><sub>Aderson Neto</sub>](https://github.com/avcneto) | [<img src="https://avatars.githubusercontent.com/u/19624216?v=4" width=115><br><sub>Felipe Chimin</sub>](https://github.com/flpchimin) | [<img src="https://avatars.githubusercontent.com/u/52970727?v=4" width=115><br><sub>Gustavo Makimori</sub>](https://github.com/gyfmaki) | [<img src="https://avatars.githubusercontent.com/u/88151987?v=4" width=115><br><sub>Pedro Paratelli</sub>](https://github.com/PedroParatelli) | 
| :----------------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------------------------------------------: | 

## Funcionalidades

Os endpoints e os dados necessários para consumo da API construída estão disponíveis no [tópico](#documentação-técnica)
abaixo.

Cadastro de usuários/clientes

- Os cadastro serão únicos e criados passando os parametros requisitados.
- Cada usuário terá um ID gerado depois do cadastro o qual será utilizado para relacionar com seu carrinho de compras.

Cadastro de produtos/items

- Serão cadastrados os produtos oferecidos pela sua loja online.
- O usuário poderá, no momento da compra, consultar os produtos e adicionar no carrinho de compras.

Gerenciamento do Carrinho de Compras
- Cada Usuário terá uma seção do carrinho de compras atrelada ao seu ID de usuário de cadastro. Assim será possível persistir os
  produtos que o usuários colocou no carrinho de compras

Gerenciamento do Meio de Pagamento
- Cada Usuário após terminar de escolher os produtos poderá finalizar sua compra e ir para o método de pagamento. O usuário poderá
  cadastrar seu cartão de crédito ou escolher outras formas de pagamento para finalizar sua compra.

## Acesso ao projeto

Você pode [acessar o código fonte do projeto inicial](https://github.com/avcneto/nextbuy)
ou [baixá-lo](https://github.com/avcneto/nextbuy/archive/refs/heads/main.zip).

## Execução do Projeto

1. Fazer o [download](https://github.com/avcneto/nextbuy/archive/refs/heads/main.zip) do repositorio;

2. Instalar o [Docker](https://www.docker.com/products/docker-desktop/) (Caso esteja em ambiente Windowns instalar WSL);

3. Abrir com a IDE de preferência;

4. Digitar no terminal:; 'docker-compose up'

5. Configurar as varíaveis de ambiente para acessar o banco de dados:

    - _DATASOURCE_PASSWORD=fiap_
    - _DATASOURCE_USER=fiap_
    - _MONGO_INITDB_ROOT_DATABASE=items
    - _AUTHENTICATION_DATABASE=admin
    - _JTW_TOKEN_KEY=user4d1381e44ae829040b6568e9e2b2cfa72c2f95946a04a760key
    - _JWT_TOKEN_EXPIRATION=3600000

## Tecnologias utilizadas

- Java 17 (Versão atualizada e estável da linguagem Java)
- Gradle (Ferramenta amplamente adotada para gerenciamento de dependências)
- Spring: Boot, Security, MVC, Data JPA, Web (Frameworks populares para desenvolvimento de aplicativos Java)
- Hibernate (Framework de mapeamento objeto-relacional para acesso a dados)
- JPA (Java Persistence API) (Especificação padrão para persistência de dados em Java)
- Lombok (Biblioteca para reduzir a verbosidade do código e automatizar tarefas comuns)
- Jakarta Bean Validation (Especificação para validação de dados em Java)
- Swagger & OpenAPI (Ferramentas e especificações para projetar, criar e documentar APIs RESTful)
- Docker
- PostgresSQL
- MongoDB
- MySQL

<div style="display: inline_block"><br>
<img src=https://raw.githubusercontent.com/github/explore/5b3600551e122a3277c2c5368af2ad5725ffa9a1/topics/java/java.png width="65" height="60"
/>
<img src=https://www.vhv.rs/dpng/d/571-5718602_transparent-ubuntu-logo-png-logo-postman-icon-png.png width="60" height="55"
/>
<img src=https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSAaUBgVyY4CJWh02Lx0PuWeq4EcbeY0-3v0PUJ5BqTxIMAxgSvlkWLY9pKM8ZIo71s4xs&usqp=CAU width="60" height="55"
/>
<img src=https://oopy.lazyrockets.com/api/v2/notion/image?src=https:%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F3ed7a304-a24b-4c45-831f-1755950e4260%2Flombok.png&blockId=552b6017-489d-4bcd-bb44-803f5e94bac9&width=256   width="60" height="55"
/>
<img src=https://th.bing.com/th/id/R.d8469eae9c8a4aa8ba0104a9d636d5f8?rik=WXdhpHKO0QTl6g&riu=http%3a%2f%2fhmkcode.github.io%2fimages%2fspring%2fspring.png&ehk=l%2b%2fhOIEAi407AyPHHjQT0NnUHU%2fH%2bjQzbnquLbAEdSI%3d&risl=&pid=ImgRaw&r=0 width="60" height="55" width="60" height="55"
/>
<img src=https://upload.wikimedia.org/wikipedia/commons/thumb/2/29/Postgresql_elephant.svg/540px-Postgresql_elephant.svg.png width="60" height="55" width="60" height="55"
/>
<img src=https://th.bing.com/th/id/OIP.T8gtXVwOgIygp25jK16IywAAAA?rs=1&pid=ImgDetMain width="60" height="55" width="60" height="55"
/></div>

## Relatório Técnico

A arquitetura utilizada neste projeto baseia-se na combinação de conceitos MVC (Model-View-Controller) e DDD (Domain
Driven Design), orientados a microserviços.
Essa combinação permite obter os benefícios de ambos os conceitos, utilizando a arquitetura MVC para a divisão das
responsabilidades de apresentação e controle de fluxo, e o DDD para criar um modelo de domínio encapsulado e rico.

A versão 17 do Java foi escolhida como base para o projeto devido à sua estabilidade e atualização no momento do
desenvolvimento. Para facilitar a configuração e o gerenciamento de dependências, o projeto adotou o Gradle, que possui
uma estrutura simples e ampla biblioteca de plugins. Além disso, o Gradle possui uma vasta integração com repositórios
centrais e uma
documentação extensa, tornando-o uma escolha popular e confiável para a construção e gerenciamento de projetos Java.

Para de reduzir a verbosidade e os famosos códigos boilerplates do código, além de automatizar a geração de getters,
setters, construtores e outros métodos comuns, o projeto utilizou o Lombok, uma biblioteca para Java.

O Hibernate é amplamente utilizado no desenvolvimento Java devido às suas vantagens significativas. Ele simplifica o
acesso a dados, abstraindo o mapeamento objeto-relacional e automatizando tarefas comuns, aumentando a produtividade dos
desenvolvedores. Além disso, oferece portabilidade, permitindo executar aplicativos em diferentes bancos de dados, e
suporta consultas flexíveis, cache e gerenciamento de transações, proporcionando um ambiente eficiente e robusto para o
desenvolvimento de aplicativos que interagem com bancos de dados relacionais.

Para validar e garantir a integridade dos dados no aplicativo Java, foi utilizado o Jakarta Bean Validation (anteriormente conhecida como Bean Validation 2.0).
Essa abordagem eficiente permite verificar se os dados inseridos atendem a padrões específicos, como formato de e-mail,
CPF, entre outros. O uso do @Validator com expressões regulares ajuda a manter a consistência dos
dados e reduzir erros ou entradas inválidas, oferecendo uma forma poderosa e flexível de validação de dados no projeto.

Para garantir a persistência de dados, foi implementada uma instância do PostgreSQL, MongoDB e MySQL em contêineres Docker,
proporcionando isolamento eficiente de responsabilidades, portabilidade, escalabilidade, facilidade de backup e
segurança, otimizando o desenvolvimento e a manutenção da aplicação.

## Desafios

- Definir e compreender os relacionamentos.
- Incluir as regras de validações.
- Definição da arquitetura do projeto (DDD/MVC/tecnologias e outros).
- Determinação das responsabilidades dos membros da equipe.

## Documentação Técnica

---

### Disclaimer

Documentação via SwaggerUI:  
User: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)  
Payments: [http://localhost:8081/swagger-ui/index.html#/](http://localhost:8081/swagger-ui/index.html#/)  
Items: [http://localhost:8083/swagger-ui/index.html#/](http://localhost:8083/swagger-ui/index.html#/)  
Cart: [http://localhost:8282/api/swagger-ui/index.html#/](http://localhost:8282/api/swagger-ui/index.html#/)

Postman Collection:  
[User Collection](nextbuy/user/src/main/resources/doc/user.postman_collection)  
[Payments Collection]("ToDo")  
[Items Collection](nextbuy/items/src/main/resources/doc/TC5_item.postman_collection.json)  
[Cart Collection]("ToDo")

Postman Documentation:  
[User Documentation](TBD)  
[Payments Documentation]()  
[Items Documentation](TBD)  
[Cart Documentation](TBD)

