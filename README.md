# <a href="https://imgbb.com/"><img src="https://i.ibb.co/c1ykYDW/image-2024-09-22-T23-32-45-105-Z.png" alt="image-2024-09-22-T23-32-45-105-Z" border="0"></a> Curso de Boas práticas de programação: Automatizando testes com Java
<p align ="center">
<a href="https://ibb.co/pwR6CKW"><img src="https://i.ibb.co/jfW97wV/image-2024-09-22-T23-39-02-050-Z.png" alt="image-2024-09-22-T23-39-02-050-Z" border="0"></a>
</p>

## Faça esse curso de Java e:

- Entenda a importância dos testes automatizados no código
- Utilize o JUnit como biblioteca de testes automatizados
- Escreva testes de unidade com JUnit
- Simule comportamentos nos testes com a biblioteca Mockito
- Aprenda a testar classes service e controller do Spring

## Aulas

- Testes de unidade com JUnit
- Boas práticas em testes
- Testes com Mockito
- Testes automatizados em Services
- Testes automatizados em Controllers
- Desafios

## Link do curso:

https://cursos.alura.com.br/course/boas-praticas-programacao-testes-java

## Principais Tecnologias

- <img width="50px" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original-wordmark.svg" title = "Java" /> Java 21 : Utilizaremos a versão LTS mais recente do Java para tirar vantagem das últimas inovações que essa linguagem robusta e amplamente utilizada oferece;
- <img width="50px" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original-wordmark.svg" title = "Spring boot"/> Spring Boot 3 : Trabalharemos com a mais nova versão do Spring Boot, que maximiza a produtividade do desenvolvedor por meio de sua poderosa premissa de autoconfiguração;
- <img width="50px" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original-wordmark.svg" title = "Spring Data JPA"/>  Spring Data JPA: Exploraremos como essa ferramenta pode simplificar nossa camada de acesso aos dados, facilitando a integração com bancos de dados SQL;
- <img width="50px" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/sqldeveloper/sqldeveloper-original.svg" title = "My SQL"/> My SQL: Banco de dados MySQL.
- <img width="100px" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/postman/postman-plain-wordmark.svg" /> Postman: Para testar os métodos http do controller.

## Para saber mais: padrões de organização de testes

Ao escrever testes automatizados é importante pensar em como organizar o código de cada teste, de forma que fique bem fácil de entender cada cenário sendo testado. Para facilitar esse processo, existem alguns padrões de organização do código de cada cenário de teste que podem ser utilizados para ajudar nesse objetivo.<br><br>

Dois padrões são os mais comuns na organização de testes automatizados: "Arrange, Act, Assert" (conhecido como AAA ou Triple A) e "Given, When, Then" (conhecido como GWT). Ambos os padrões têm o objetivo de tornar os testes mais legíveis e fáceis de entender, promovendo uma abordagem estruturada na criação de cenários de teste.<br><br>

### Arrange, Act, Assert (AAA)

O padrão AAA é amplamente utilizado e consiste em três etapas distintas:

a) Arrange (Preparar): Nesta etapa, são realizadas todas as configurações iniciais necessárias para que o cenário de teste possa ser executado. Isso pode incluir a criação de objetos, definição de variáveis, configuração de ambiente e qualquer outra preparação necessária para que o teste seja executado em um estado específico.

b) Act (Agir): Nesta fase, a ação que se deseja testar é executada. Pode ser a chamada de um método, uma interação com a interface do usuário ou qualquer outra operação que seja o foco do teste.

c) Assert (Verificar): Na última etapa, os resultados são verificados em relação ao comportamento esperado. É onde se avalia se o resultado obtido após a ação está de acordo com o que se esperava do teste. Caso haja alguma discrepância entre o resultado real e o esperado, o teste falhará.


### Given, When, Then (GWT)

O padrão GWT também é conhecido como padrão BDD (Behavior-Driven Development). Ele foi projetado para criar testes com uma linguagem mais próxima da forma como os cenários são discutidos entre as pessoas que fazem parte do projeto, incluindo pessoas técnicas, como desenvolvedores e testadores, além de clientes e usuários da aplicação. O padrão GWT tem as seguintes etapas:

a) Given (Dado): Nesta etapa, é descrito o cenário inicial ou o contexto do teste. São definidas as condições iniciais necessárias para a execução do cenário de teste.

b) When (Quando): Aqui, a ação específica que está sendo testada é executada. É a etapa em que a ação do usuário ou do sistema acontece.

c) Then (Então): Nesta etapa, os resultados esperados são verificados. São definidos os critérios de sucesso para o cenário de teste, e o teste é aprovado ou reprovado com base nesses resultados.

Ambos os padrões tornam os testes mais fáceis de ler e entender, mesmo para pessoas não familiarizadas com o código ou com a lógica de negócios do sistema. Além disso, a estruturação dos testes em etapas claras e definidas torna mais fácil para as pessoas desenvolvedoras localizarem e corrigirem problemas em caso de falha.

