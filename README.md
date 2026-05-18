# 🚀 Desafio Backend - BTG Pactual

Este projeto foi desenvolvido como solução para o desafio backend do BTG Pactual, utilizando microsserviços com Spring Boot, RabbitMQ e MongoDB.

O objetivo da aplicação é consumir pedidos enviados por uma fila RabbitMQ, persistir os dados no MongoDB e disponibilizar informações através de uma API REST.

---

# 🛠️ Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data MongoDB
- Spring AMQP
- RabbitMQ
- MongoDB
- Docker
- Docker Compose
- Maven
- SLF4J

---

# 📚 Funcionalidades

## ✅ Consumo de pedidos

A aplicação consome mensagens da fila RabbitMQ contendo pedidos realizados por clientes.

## ✅ Persistência de dados

Os pedidos recebidos são armazenados no MongoDB.

## ✅ API REST

A aplicação disponibiliza endpoints para:

- Listar pedidos por cliente
- Calcular valor total dos pedidos do cliente
- Retornar quantidade de pedidos realizados

---

# 🧱 Arquitetura da aplicação

```bash
RabbitMQ → Spring Boot → MongoDB → API REST
