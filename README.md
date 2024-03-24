# tech.challenge.ecommerce

```
Tech Challenge FIAP Fase 5 - Ecommerce API

Abaixo se encontra a documentação e relatório técnico do projeto
criado para o Tech Challenge da Fase 5 do curso de Arquitetura e
Desenvolvimento em Java da Pós Tech FIAP

Este projeto foi desenvolvido por:

Henrqiue Estevam de Matos
```
## Relatório Técnico

---
- [Introdução - Desenvolvimento, Arquitetura e Entidades](#introdução---desenvolvimento-arquitetura-e-entidades)
- [Bibliotecas em Destaque](#bibliotecas-em-destaque)
- [Plugins da IDE](#plugins-da-ide)
- [Como Subir a Aplicação](#como-subir-a-aplicação)

### Introdução - Desenvolvimento, Arquitetura e Entidades

Seguindo os requisitos do Tech Challenge da Fase 5 disponibilizados no 
portal do aluno da FIAP, este projeto foi desenvolvido com o intuito de
mostrar o conhecimento aprendido a respeito da aruqitetura de microsserviços,
da codificação limpa e segura, utilizando o recurso de login e autenticação
de usuários através de tokens JWT. Vale ressaltar que o projeto foi totalmente 
desenvolvido em inglês.

#### Desenvolvimento e Arquitetura

Para gestão de dados foi utilizado o banco de dados relacional **PostgreSQL**
Para iniciar o desenvolvimento da solução, foi criado o seguinte modelo
de dados: 

![image](https://github.com/henriquemt13/tech.challenge.ecommerce/assets/47531611/8de8a0cd-b061-493d-91c9-0f14f1aed411)

Com as entidades criadas, o desenvolvimento da solução foi iniciado, separando
as responsabilidades em 4 microsserviços:

- tech.challenge.ecommerce.auth.api
- tech.challenge.ecommerce.items.api
- tech.challenge.ecommerce.bag.api
- tech.challenge.ecommerce.payment.api

Cada microsserviço foi desenvolvido com base na seguinte arquitetura:

![image](https://github.com/henriquemt13/tech.challenge.ecommerce/assets/47531611/73ef0eec-4929-462b-af03-3498f2eca732)

Desta forma, a camada **Controller** nunca terá acesso direto ao banco de dados pela
**Repository**, sempre passará primeiro pela camada **Service**, e esta sim
tem a responsabilidade de "implementar" os métodos declarados na camada de
repositório.

O fluxo da aplicação segue um esquema muito simples e intuitivo com os microsserviços
desenvolvidos:
![image](https://github.com/henriquemt13/tech.challenge.ecommerce/assets/47531611/da9502dd-e68c-4eb1-b9ee-34ff8ccd6194)

Quando um novo usuário é cadastrado, a informação de que ele tem permissão de usuário
administrador ou não é registrada no banco e retornada durante a autenticação, limitando
assim o acesso à API tech.challenge.ecommerce.items.api, utilizada para a criação e
deleção de itens ao ecommerce, bem como a visualização e atualização dos mesmos.

A API tech.challenge.ecommerce.bag.api fica então com seu acesso livre ao usuário
que for ou não administrador, mas que possua um token de autenticação válido, e é
responsável por gerir sua sacola de compras, que se mantém existente caso a sessão 
do usuário expire.

Por fim, o payment.api é responsável por fazer a simulação de pagamento, contendo
um endpoint para exibir um resumo da compra, com todos os itens e seu valor total
e um endpoint para de fato finalizar a compra, atualizando a sacola com o atributo
**payed** como verdadeiro.

A API de segurança, auth.api, ficou responsável por gerir o cadastro de usuários, 
seu nome, usuário, senha e nível de permissão, que se limitou a Administrador ou Cliente.
Para isso, foi utilizado o Spring Security, implementando uma configuração na aplicação, 
no pacote **Configuration**, que limita o acesso a seus endpoints sem o envio de um
token **JWT** de autenticação, como demonstrado no print abaixo:

![image](https://github.com/henriquemt13/tech.challenge.ecommerce/assets/47531611/1be5cb75-4579-40ee-bc22-4eba8077c699)

Para a criação e validação do token, foi utilizado um Filter no Spring, ou seja
antes mesmo de acessar a lógica do endpoint chamado pela requisição, o 
Spring acessa as informações recebidas no **Body** e **Header** e utiliza
o algorítmo do JWT para criar um token de acesso, com base na validação de
usuário e senha enviados, ou para validar um token enviado.

É importante ressaltar que para este projeto, o token de acesso foi 
configurado para ter a duração de 2 horas, a partir do momento de
sua criação.


## Bibliotecas Em Destaque

---

Como já mencionado, o Spring Secutiry foi responsável por estabelecer
toda a parte de segurnaça implementada no primeiro microsserviço, enquanto
o Auth0.JWT foi utilizado para geração dos tokens de acesso.

Impossível não ressaltar novamente a importancia do MapStruct, que foi
muito utilizado para fazer a comunicação entre os objetos das camadas,
transformando objetos de requests em objetos de Model, por exemplo.

Outra biblioteca mega importante foi o Flyway, para o uso de Migrations no
banco de dados. Isso possibilitou o desenvolvimento ágil e muito tempo de
configuração de banco de dados economizada.


## Plugins da IDE

---

O SonarLint foi um ótimo companheiro de desenvolvimento para esse projeto,
sendo muito utilizado para identificar lógicas que poderiam ser refatoradas para
uma forma mais simples, importações não utilizadas e nomenclaturas incorretas.

## Como Subir a Aplicação

---
Requisitos: Docker instalado no sistema operacional

Ao Clonar o projeto para seu diretório local, execute o comando:
```
docker compose up

```

A partir desse momento, a imagem do banco de dados PostgreSQL e dos dois microsserviços será criada.
Ao finalizar este processo as APIS subirão 
nas portas 8080, 8081, 8082 e 8083 respectivamente.

*Nota : Ao subir a aplicação, é possível acessar seus Swaggers, através dos links:* http://localhost:{PORTA_DA_APLICAÇÃO}/swagger-ui/index.html#/
