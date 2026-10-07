# Ticklate - Sistema de Gerenciamento de Tarefas Agendadas

## 📋 Sobre o Projeto

**Ticklate** é um **MVP** de uma aplicação desenvolvida em **Spring Boot** que tem como objetivo gerenciar tarefas agendadas e verificar periodicamente se essas tarefas estão vencidas, enviando notificações quando necessário.

Este projeto foi construído seguindo os **princípios SOLID** e uma arquitetura em camadas (Clean Architecture), demonstrando boas práticas de design de software e separação de responsabilidades.

### 🎯 Objetivo

Criar uma solução simples e eficiente para:
- **Gerenciar tarefas agendadas**: Armazenar tarefas com título e data de vencimento
- **Verificação automática**: Executar verificações periódicas para identificar tarefas vencidas
- **Notificações**: Enviar lembretes quando tarefas estão atrasadas
- **Arquitetura limpa**: Demonstrar aplicação dos princípios SOLID e separação de responsabilidades

### 🏗️ Arquitetura e Design Patterns

O projeto foi desenvolvido seguindo uma **arquitetura em camadas** e os **princípios SOLID**:

- **Single Responsibility Principle (SRP)**: Cada classe tem uma única responsabilidade
  - `TaskCheckerService`: Orquestra a verificação de tarefas vencidas
  - `Scheduler`: Gerencia o agendamento de tarefas
  - `ConsoleNotifier`: Implementa notificações via console
  - `Task`: Representa a entidade de negócio

- **Open/Closed Principle (OCP)**: A interface `Notifier` permite extensão sem modificação
  - Novas implementações de notificação podem ser criadas sem alterar código existente

- **Liskov Substitution Principle (LSP)**: Qualquer implementação de `Notifier` pode ser substituída
  - `ConsoleNotifier` implementa `Notifier` e pode ser substituída por outras implementações

- **Interface Segregation Principle (ISP)**: A interface `Notifier` é específica e coesa
  - Contém apenas o método necessário para enviar lembretes

- **Dependency Inversion Principle (DIP)**: Dependências apontam para abstrações
  - `TaskCheckerService` depende da interface `Notifier`, não de implementações concretas

## 🛠️ Tecnologias e Dependências

### Stack Principal

- **Java 21** - Linguagem de programação
- **Spring Boot 4.0.1** - Framework principal
- **Spring Data JPA** - Abstração de acesso a dados
- **Hibernate** - ORM (Object-Relational Mapping)
- **H2 Database** - Banco de dados em memória (para MVP)
- **Spring Scheduling** - Agendamento de tarefas periódicas

### Dependências de Produção

```gradle
// Spring Boot Starters
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-h2console'

// Database
runtimeOnly 'com.h2database:h2'

// Development
developmentOnly 'org.springframework.boot:spring-boot-devtools'
```

### Dependências de Teste

```gradle
testImplementation 'org.springframework.boot:spring-boot-starter-data-jpa-test'
testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
```

## 📁 Estrutura do Projeto

O projeto segue uma arquitetura em camadas (Clean Architecture), separando responsabilidades:

```
src/main/java/com/dvlprmatheus/ticklate/
├── domain/                  # Camada de Domínio (Entidades e Interfaces)
│   ├── Task.java           # Entidade de tarefa
│   ├── TaskRepository.java # Repositório de tarefas
│   └── Notifier.java       # Interface para notificações
├── application/            # Camada de Aplicação (Casos de Uso)
│   └── TaskCheckerService.java # Serviço de verificação de tarefas vencidas
├── infra/                  # Camada de Infraestrutura (Implementações)
│   ├── Scheduler.java      # Agendador de verificações
│   └── ConsoleNotifier.java # Implementação de notificação via console
└── TicklateApplication.java # Classe principal da aplicação
```

### Descrição das Camadas

#### Domain (Domínio)
- **Responsabilidade**: Contém as entidades de negócio, interfaces e contratos
- **Princípios SOLID**: Define abstrações (interfaces) que as outras camadas dependem (DIP)
- **Componentes**:
  - `Task`: Entidade JPA que representa uma tarefa
  - `TaskRepository`: Interface de repositório para acesso a dados
  - `Notifier`: Interface para estratégias de notificação

#### Application (Aplicação)
- **Responsabilidade**: Contém a lógica de casos de uso e orquestração
- **Princípios SOLID**: Depende de abstrações do domínio (DIP)
- **Componentes**:
  - `TaskCheckerService`: Serviço que coordena a verificação de tarefas vencidas

#### Infrastructure (Infraestrutura)
- **Responsabilidade**: Implementações concretas de interfaces e integrações externas
- **Princípios SOLID**: Implementa as interfaces definidas no domínio
- **Componentes**:
  - `Scheduler`: Implementa o agendamento usando Spring Scheduling
  - `ConsoleNotifier`: Implementa `Notifier` para notificações via console

## 🔧 Configurações

### Application Properties

O projeto utiliza `application.yaml` com as seguintes configurações:

```yaml
spring:
  application:
    name: ticklate
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: update
  h2:
    console:
      enabled: true
```

### Configuração de Agendamento

O agendador verifica tarefas vencidas a cada **5 segundos** (configurado no `Scheduler`):

```java
@Scheduled(fixedDelay = 5000) // Verifica a cada 5 segundos
```

## 🔄 Funcionalidades

### Gerenciamento de Tarefas

- **Criação**: Tarefas são criadas com título e data de vencimento
- **Persistência**: Armazenamento em banco de dados H2 (em memória para MVP)
- **Rastreamento**: Controle de notificação para evitar notificações duplicadas

### Verificação Automática

- **Agendamento**: Verificação periódica automática de tarefas vencidas
- **Identificação**: Busca por tarefas com data de vencimento passada e ainda não notificadas
- **Processamento**: Processamento transacional para garantir consistência

### Sistema de Notificações

- **Interface Abstrata**: `Notifier` define o contrato para notificações
- **Implementação**: `ConsoleNotifier` envia notificações via console
- **Extensibilidade**: Fácil adição de novas implementações (email, SMS, push, etc.)

### Seed de Dados

A aplicação inclui um `CommandLineRunner` que cria tarefas de exemplo na inicialização:

```java
new Task("Estudando SOLID", LocalDateTime.now().plusSeconds(10)),
new Task("MVP Ticklate", LocalDateTime.now().plusSeconds(20))
```

## 🗄️ Modelo de Dados

### Entidade Task

A entidade `Task` possui os seguintes atributos:

- **id** (UUID): Identificador único da tarefa
- **title** (String): Título da tarefa
- **dueDate** (LocalDateTime): Data e hora de vencimento
- **notified** (boolean): Flag indicando se já foi notificada

### Métodos da Entidade

- `isOverdue()`: Verifica se a tarefa está vencida e ainda não foi notificada
- `markAsNotified()`: Marca a tarefa como notificada

### Repositório

O `TaskRepository` estende `JpaRepository` e inclui um método customizado:

```java
List<Task> findByDueDateBeforeAndNotifiedFalse(LocalDateTime now)
```

Este método busca todas as tarefas que:
- Têm data de vencimento anterior ao momento atual
- Ainda não foram notificadas

## 🚀 Como Executar

### Pré-requisitos

- **Java 21** ou superior
- **Gradle** (ou use o wrapper incluído: `gradlew`)

### Executando a Aplicação

1. **Clone o repositório** (se aplicável):
```bash
git clone <repository-url>
cd ticklate
```

2. **Execute a aplicação**:
```bash
./gradlew bootRun
```

Ou no Windows:
```bash
gradlew.bat bootRun
```

3. **A aplicação iniciará** e começará a verificar tarefas a cada 5 segundos

4. **Console H2** (opcional): Acesse `http://localhost:8080/h2-console` para visualizar o banco de dados
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `sa`
   - Password: (vazio)

### Observando as Notificações

Ao executar a aplicação, você verá no console mensagens como:

```
[TICKLATE] - The task 'Estudando SOLID' is overdue.
[TICKLATE] - The task 'MVP Ticklate' is overdue.
```

As notificações aparecem quando as tarefas de exemplo atingem suas datas de vencimento (10 e 20 segundos após a inicialização).

## 🧪 Testes

### Estrutura de Testes

O projeto inclui estrutura para testes (usando JUnit 5 e Spring Boot Test):

```bash
# Executar todos os testes
./gradlew test

# Executar testes com informações detalhadas
./gradlew test --info
```

### Tipos de Testes Recomendados

Para um projeto completo, considere implementar:

- **Testes Unitários**: Testar lógica de negócio isoladamente
  - `TaskCheckerServiceTest`: Verificar lógica de busca e notificação
  - `TaskTest`: Testar métodos da entidade (`isOverdue`, `markAsNotified`)

- **Testes de Integração**: Testar integração entre componentes
  - `TaskRepositoryTest`: Verificar queries do repositório
  - `SchedulerTest`: Verificar agendamento (usando `@MockBean`)

- **Testes de Integração com Spring Context**: Verificar carregamento do contexto
  - `TicklateApplicationTests`: Verificar inicialização da aplicação

## 📡 API e Extensibilidade

### Status Atual (MVP)

O projeto atual é um MVP focado na lógica core de verificação e notificação. Para evoluir para uma API REST completa, considere adicionar:

- **Controllers REST**: Endpoints para CRUD de tarefas
- **DTOs**: Objetos de transferência de dados
- **Validações**: Validação de dados de entrada
- **Tratamento de Exceções**: Handler global de exceções

### Extensibilidade do Sistema de Notificações

Graças ao design baseado em SOLID, é fácil adicionar novas implementações de notificação:

```java
@Component
public class EmailNotifier implements Notifier {
    @Override
    public void sendReminder(String message) {
        // Implementação de envio de email
    }
}

@Component
public class SmsNotifier implements Notifier {
    @Override
    public void sendReminder(String message) {
        // Implementação de envio de SMS
    }
}
```

O Spring automaticamente injetará a implementação disponível (ou você pode usar `@Primary` para definir uma padrão).

## 📝 Logging e Monitoramento

### Console Output

O sistema atual utiliza `System.out.println` para notificações. Para produção, considere:

- **SLF4J + Logback**: Logging estruturado
- **Níveis de Log**: INFO, DEBUG, WARN, ERROR
- **Logging de Operações**: Registrar verificações, notificações enviadas, erros

### Exemplo de Melhoria

```java
@Service
public class TaskCheckerService {
    private static final Logger log = LoggerFactory.getLogger(TaskCheckerService.class);
    
    @Transactional
    public void checkOverdueTasks() {
        log.debug("Checking for overdue tasks...");
        List<Task> tasks = taskRepository.findByDueDateBeforeAndNotifiedFalse(LocalDateTime.now());
        log.info("Found {} overdue tasks", tasks.size());
        // ... resto do código
    }
}
```

## 🔒 Considerações para Produção

Para evoluir este MVP para um ambiente de produção, considere:

1. **Banco de Dados**: Migrar de H2 para PostgreSQL, MySQL ou outro banco de dados persistente
2. **Flyway/Liquibase**: Implementar migrações de banco de dados
3. **Configuração Externa**: Externalizar configurações (application-prod.yaml, variáveis de ambiente)
4. **Testes**: Adicionar suíte completa de testes
5. **Documentação API**: Swagger/OpenAPI se adicionar endpoints REST
6. **Segurança**: Spring Security se necessário autenticação/autorização
7. **Monitoramento**: Health checks, métricas, tracing
8. **Deploy**: Docker, CI/CD, orquestração (Kubernetes, etc.)

## 📦 Próximos Passos

Este MVP pode ser estendido com:

- **API REST completa**: CRUD de tarefas via endpoints HTTP
- **Múltiplos tipos de notificação**: Email, SMS, Push notifications
- **Usuários e Autenticação**: Sistema multi-usuário
- **Categorias e Tags**: Organização de tarefas
- **Recorrência**: Tarefas recorrentes
- **Dashboard Web**: Interface gráfica para gerenciamento
- **Filtros e Busca**: Pesquisa avançada de tarefas
- **Estatísticas**: Relatórios e métricas

## 📚 Princípios SOLID Aplicados

Este projeto demonstra a aplicação prática dos princípios SOLID:

### Single Responsibility Principle (SRP)
✅ Cada classe tem uma única responsabilidade bem definida

### Open/Closed Principle (OCP)
✅ A interface `Notifier` permite extensão sem modificação do código existente

### Liskov Substitution Principle (LSP)
✅ Qualquer implementação de `Notifier` pode substituir outras sem quebrar o comportamento

### Interface Segregation Principle (ISP)
✅ A interface `Notifier` é específica e contém apenas o necessário

### Dependency Inversion Principle (DIP)
✅ Camadas de alto nível (Application) dependem de abstrações (Domain), não de implementações (Infrastructure)

## 📄 Licença

Este projeto está sob licença. Consulte o arquivo `LICENSE` para mais detalhes.

## 👥 Contribuindo

Este é um projeto MVP destinado a demonstrar conceitos de arquitetura e design. Sinta-se livre para:

1. Fazer fork do projeto
2. Criar uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abrir um Pull Request

## 📚 Recursos Adicionais

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Spring Scheduling](https://docs.spring.io/spring-framework/reference/integration/scheduling.html)

---

**Ticklate** - Simplificando o gerenciamento de tarefas agendadas 🎯
