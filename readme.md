# Delivery API

API desenvolvida em **Java 17 + Spring Boot** para gerenciar **Clientes**, **Restaurantes** e **Produtos** de uma aplicação de delivery.  
Este projeto é parte de uma entrega acadêmica e pode conter implementações em desenvolvimento.

---

## Tecnologias utilizadas
- Java 17  
- Spring Boot 3  
- Spring Data JPA  
- Hibernate  
- H2 Database (testes)  
- Maven  

---

## Como executar

```bash
# Clonar o repositório
git clone <url-do-repositorio>

# Entrar no diretório
cd delivery-api

# Rodar a aplicação
mvn spring-boot:run
```
## Endpoints principais
### Clientes
- POST /cliente -> cadastrar cliente
- GET /cliente -> listar clientes
- GET /clientes{id} -> buscar cliente por ID

#### Payload de exemplo
```
{
  "nome": "Maria Oliveira",
  "email": "maria.oliveira@example.com"
}
```
### Restaurante
- POST /restaurantes -> cadastrar restaurante
- GET /restaurantes -> listar restaurantes
- GET /restaurantes{id} -> buscr restaurante po ID

#### Exemplo
```
{
  "nome": "Restaurante Central",
  "categoria": "Brasileira",
  "telefone": "11987654321",
  "taxaEntrega": 5.00,
  "tempoEntregaMinutos": 45
}
```
### Produtos
- POST /produtos -> cadastrar produto vinculado a um restaurante
- GET /produtos -> listar produtos
- GET /produtos{id} -> buscar produto por ID

```
{
  "nome": "Pizza de Calabresa",
  "categoria": "Pizza",
  "descricao": "Pizza grande com bastante recheio",
  "preco": 49.90,
  "disponivel": true,
  "restaurante": {
    "id": 1
  }
}
```
## Estrutura do projeto
```
src/main/java/com/deliverytech/delivery/
 ├── controller   # Controllers da API
 ├── model        # Entidades JPA
 ├── repository   # Repositórios JPA
 └── service      # Regras de negócio
```

## Observações

- O projeto pode conter erros de compilação e execução.
- O foco do projeto foi didático para exercitar o que foi aprendido em aula.
s