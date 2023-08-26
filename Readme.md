# Sistema de Escolha Automatizada de Músicas para Equipe de Louvor da Igreja

## Descrição do Problema

Na equipe de louvor da igreja, enfrentamos uma problemática recorrente relacionada à escolha de músicas para os cultos. Atualmente, seguimos uma estrutura específica em que são tocadas três músicas durante cada culto, sendo duas músicas de adoração e uma música de celebração. Mas, as vezes, é necessário uma música extra, que pode ser
de adoração ou celebração

Além disso, realizamos cultos em cinco ocasiões ao longo da semana:

- Duas vezes aos domingos
- Uma vez às terças-feiras
- Uma vez às quartas-feiras
- Uma vez às quintas-feiras

No entanto, a questão das repetições de músicas tem sido um desafio constante, tornando essencial encontrar uma maneira de otimizar o processo de escolha das músicas.

## Solução Proposta

Com o intuito de solucionar o problema das repetições de músicas e simplificar o processo de seleção, realizei o desenvolvimento de um sistema automatizado de escolha de músicas.

## Estrutura do Banco de Dados

Cada música cadastrada no sistema conterá as seguintes informações:

- ID único
- Nome da música
- Nome do cantor
- Categoria (Adoração ou Celebração)
- Data da última vez que a música foi tocada
- Pular Música (coluna auxiliar, que é usada na regra de negócio)

## Regras de Negócio

Para garantir uma variedade adequada e evitar repetições excessivas, as seguintes regras de negócio serão implementadas:

1. As músicas não podem ser repetidas, a menos que todas as músicas da categoria correspondente já tenham sido tocadas.
2. Caso haja repetição, a música escolhida será aquela que não foi tocada por mais tempo, minimizando a percepção de repetição.
3. Caso não goste da música escolhida, tem a opção de escolher outra para substitui-la
4. Assim que as músicas estiverem do agrado de quem escolheu, é necessária a confirmação das músicas
5. Caso o usuário desista da escolha, deve existir um botão para cancelar as alterações
6. Deve haver a opção de escolher uma música adicional, podendo ser de adoração ou de celebração

## Tecnologias Utilizadas

O sistema será desenvolvido utilizando as seguintes tecnologias:

- Java + Spring Boot: Framework para o desenvolvimento da aplicação.
- Docker: Para a criação e gerenciamento de contêineres.
- Postgres: Banco de dados relacional para armazenar as informações das músicas e seu histórico de reprodução.
- Railwai: Onde estão hospedados o banco de dados, o backend e o frontend

## Como ficou o front-end



## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para fazer um fork deste repositório, implementar melhorias e enviar pull requests.

## Contato

Em caso de dúvidas ou sugestões, entre em contato conosco pelo e-mail: cainamicaeloficial@gmail.com

---

*Este projeto é desenvolvido e mantido por Cainã Micael*
