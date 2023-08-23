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
fetch(urlBase + 'musicas/tocadas')
.then(resposta => resposta.json())
.then(dados => {
    const modulos = document.querySelector('.modulos')
    modulos.innerHTML = ''
    let adicionar = ''

    if(dados != []) {
        dados.forEach(dado => {  
            console.log(dado)
                      
            const data = new Date(dado.dataUltimaVezTocada);

            const dia = data.getDate();
            const mes = data.getMonth() + 1; // Lembrando que os meses em JavaScript são base 0
            const ano = data.getFullYear();

            const dataFormatada = `${dia}/${mes}/${ano}`

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
                    <p>${dataFormatada}</p>
                </div>
            `
        })
    }

    modulos.innerHTML = adicionar
})