## MUSEU TREZE DE MAIO
O projeto Museu Treze de Maio é um sistema web de gestão de acervo histórico e bibliográfico para o Museu Treze de Maio (Santa Maria/RS). Desenvolvido em Java com arquitetura MVC, o sistema permite cadastrar materiais bibliográficos do acervo histórico físico, além de controlar exemplares no acervo e disponibilizar consultas públicas aos dados. O objetivo é digitalizar, organizar e democratizar o acesso ao patrimônio histórico-cultural afro-brasileiro do museu, auxiliando a equipe técnica do museu, pesquisadores e estudantes. O desenvolvimento foi realizado como projeto extensionista na UFN, integrando disciplinas de Projeto de Software e Banco de Dados.

## Descrição do Problema e Solução Proposta
Muitos museus sofrem com acervos físicos não catalogados, com informações acessíveis apenas localmente. O problema abordado é a dificuldade de gerenciar e consultar registros de obras, documentos e livros do museu, bem como de controlar empréstimos, de forma manual. A solução proposta é um sistema integrado onde:

- Livros, jornais e revistas são cadastrados digitalmente.
- Interfaces gráficas facilitam a navegação e filtros de busca tornam consultas públicas intuitivas.
- O controle de obras é facilitado para a equipe do museu.

## Objetivos do projeto

O sistema tem como objetivo digitalizar, organizar e facilitar o acesso ao patrimônio histórico-cultural do museu, reduzindo o uso de controles manuais e centralizando as informações do acervo em uma única aplicação.

Com isso, é possível:

- cadastrar e consultar obras do acervo bibliográfico/histórico;
- controlar exemplares vinculados a cada obra;
- manter dados de apoio como autores, editoras e assuntos;
- registrar histórico de ações realizadas por usuários autorizados;
- disponibilizar busca pública para consulta do acervo.

## Protótipo de interface
O protótipo de interface do sistema foi desenvolvido utilizando a ferramenta Figma Make e está disponível em:
[Protótipo de Interface](https://paper-bell-55152035.figma.site/).

## Diagramas
Os diagramas do projeto estão disponíveis em: [Diagramas](documentacao/Diagramas), organizados da seguinte forma:

#### Engenharia de Requisitos
- Diagrama de Caso de Uso.
- Descrição dos Casos de Uso.

#### Modelagem do Domínio
- Diagrama de Domínio.

#### Projeto de Software
- Diagrama de Classes representando a arquitetura e as classes envolvidas no desenvolvimento do sistema;

#### Diagramas de Sequência
- Para a entidade obra, o diagrama representa adequadamente a interação entre interface, controladores, serviços, persistência e banco de dados.


* OBS: Todos os diagramas foram desenvolvidos utilizando a ferramenta Astah UML.

## Tecnologias Utilizadas

- **Linguagem:** Java 17
- **Framework:** Spring Boot 4.0.6
- **Persistência:** Spring Data JPA + MySQL
- **Segurança:** Spring Security
- **Template engine:** Thymeleaf
- **Build e dependências:** Maven
- **Bibliotecas de apoio:** Lombok, Jackson
- **Ambiente opcional:** Docker e Docker Compose

## Principais funcionalidades

- **Autenticação e autorização** com Spring Security.
- **Cadastro de usuários** com perfis de acesso.
- **Perfis de acesso**:
    - `ADMIN`
    - `BIBLIOTECARIO`
- **Busca pública de obras** por termo, tipo e período.
- **Visualização de detalhes da obra**.
- **Cadastro e edição de obras** do tipo livro, jornal e revista.
- **Gestão de exemplares** associados às obras.
- **Cadastro, edição e exclusão** de:
    - autores;
    - editoras;
    - assuntos.
- **Histórico de acesso/alterações** para rastrear operações sobre obras.
- **Carga inicial de dados** a partir de `src/main/resources/config/dados_obras.json`.
- **Usuários padrão criados automaticamente** na primeira execução.

## Arquitetura adotada

O projeto segue uma organização inspirada em **MVC**, separando responsabilidades em camadas:

- **Model**: entidades do domínio (`Obra`, `Exemplar`, `Autor`, `Editora`, `Assunto`, `Usuario`, `ObraHistorico`, etc.)
- **DTO**: objetos de entrada e saída para transporte de dados
- **Mapper**: conversão entre entidade e DTO
- **Repository**: acesso aos dados com JPA
- **Service**: regras de negócio
- **Controller**: rotas, telas e respostas HTTP
- **Config**: configuração de segurança, encoder e carregamento inicial de dados
- **View**: páginas Thymeleaf em `src/main/resources/templates`

Fluxo simplificado:

`View -> Controller -> Service -> Repository -> Banco de Dados`.

## Dados iniciais e usuários padrão

Na inicialização da aplicação, o projeto tenta criar automaticamente os seguintes usuários:

- **Administrador**
    - e-mail: `admin@museu.com`
    - senha: `admin123`
    - perfil: `ADMIN`

- **Bibliotecário**
    - e-mail: `bibliotecario@museu.com`
    - senha: `biblio123`
    - perfil: `BIBLIOTECARIO`

Além disso, o sistema carrega obras iniciais a partir do arquivo JSON configurado em `src/main/resources/config/dados_obras.json`.

## Requisitos para execução

- Java 17
- Maven 3.9+ ou Maven Wrapper (`mvnw` / `mvnw.cmd`)
- MySQL 8.0+
- Git
- Opcional: Docker e Docker Compose

## Configuração do banco de dados

A configuração local atual está em `src/main/resources/application.properties` e usa, por padrão:

- banco: `MuseuTreze`
- usuário: `root`
- senha: `laboratorio`
- URL: `jdbc:mysql://localhost:3306/MuseuTreze?createDatabaseIfNotExist=true`

Se o seu MySQL usar outra senha/usuário, ajuste esse arquivo antes de executar.

## Instruções de instalação e execução do sistema
### 1. Clonar o repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd museu-completo-v2
```

### 2. Configurar o banco de dados

Verifique se o MySQL está em execução e se as credenciais em `src/main/resources/application.properties` estão corretas.

### 3. Executar a aplicação

No Windows, use o Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

Ou, se preferir, execute o JAR após o build:

```powershell
.\mvnw.cmd clean package
java -jar target\MuseuTreze-0.0.1-SNAPSHOT.jar
```

### 4. Acessar no navegador

```text
http://localhost:8080
```

## Observações importantes

- O projeto usa **Thymeleaf** para renderização das páginas no servidor.
- O carregamento de obras iniciais acontece apenas quando ainda não existem registros no banco.
- O histórico de operações registra ações como cadastro, edição e exclusão de obras.
- Algumas rotas de alteração usam `PUT` e `DELETE`; se o front-end for chamado via formulário HTML puro, pode ser necessário tratar isso com JavaScript ou com a configuração adequada de método HTTP.
- O pacote `src/main/java/inf/laboratorio/museutreze/config/` concentra boa parte do comportamento inicial da aplicação, incluindo segurança, senha e seed de dados.

## Equipe de Desenvolvimento
- Fares Mahmud -> [GitHub](https://github.com/FaresMahmud).
- Gustavo Anibele -> [GitHub](https://github.com/anibele).
- Igor Gabiatti -> [GitHub](https://github.com/IgorGabiatti).
- Igor Ribas -> [GitHub](https://github.com/iguirote).
- Giuliano -> [GitHub](https://github.com/iguirote).

## Vídeo Demonstrativo
Aqui o link do vídeo de demonstração do projeto: https://youtu.be/qZ4Z1Z6A0Iw.

## Informações Adicionais
- Projeto extensionista da [UFN](https://site.ufn.edu.br/).
- Curso: [Sistemas de Informação](https://site.ufn.edu.br/pagina/sistemas-de-informacao).
- Professor responsável: [Herysson Rodrigues Figueiredo](https://github.com/Herysson).
