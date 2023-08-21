alert('Quando todas as músicas forem tocadas, a contagem de músicas tocadas irá zerar!')
const urlBase = 'http://localhost:8080/api/'

//Setando as músicas cadastradas e tocadas
fetch(urlBase + 'quantidades')
.then(resposta => resposta.json())
.then(quantidades => {
    const musicasCadastradas = quantidades.quantidadeRegistros
    const musicasTocadas = quantidades.quantidadeMusicasTocadas

    //Mudando os textos
    document.getElementById('musicas-cadastradas').innerText = musicasCadastradas + ' MÚSICAS CADASTRADAS'
    
    document.getElementById('musicas-tocadas').innerText = musicasTocadas + ' MÚSICAS JÁ TOCADAS'
})

//Pegando cada música dinamicamente
fetch(urlBase + 'musicas')
.then(resposta => resposta.json())
.then(dados => {
    const modulos = document.querySelector('.modulos')
    modulos.innerHTML = ''
    let adicionar = ''

    dados.forEach(dado => {
        let data;

        if(dado.dataUltimaVezTocada != null) {
            data = dado.dataUltimaVezTocada
        } else {
            data = 'A música ainda não foi tocada!'
        }

        adicionar += `
            <div class="modulo">
                <span>ID:</span>
                <p>${dado.id}</p>

                <span>NOME:</span>
                <p>${dado.nome}</p>

                <span>QUEM CANTA:</span>
                <p>${dado.cantor}</p>

                <span>CATEGORIA:</span>
                <p>${dado.categoria}</p>

                <span>QUANDO FOI TOCADA: </span>
                <p>${data}</p>
            </div>
        `
    })

    modulos.innerHTML = adicionar
})