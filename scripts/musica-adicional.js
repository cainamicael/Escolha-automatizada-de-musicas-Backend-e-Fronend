alert('AVISO: Não esqueça de clicar no confirmar e caso desista, clique em cancelar!')
const urlBase = 'http://localhost:8080/api/'

//Pegando a categoria assim que abre a página (default)
var categoriaSelecionada;
const categoriaInicial = document.getElementById('selecao').value
categoriaSelecionada = categoriaInicial

setarValores(categoriaSelecionada)

//Acionado caso haja mudança de categoria
function pegarValor() {
    let categoriaAlterada = document.getElementById('selecao').value 
    categoriaSelecionada = categoriaAlterada

    setarValores(categoriaSelecionada)
}

//Visual de como vão aparecer as musicas
function setarValores(categoriaSelecionada) {
    fetch(urlBase + `musica?categoria=${categoriaSelecionada}`)
    .then(res => res.json())
    .then(musica => {
        const modulos = document.querySelector('.modulos')

        modulos.innerHTML = `
            <div class="modulo">
                <h4 class="id">Id: ${musica.id}</h4>
                <h2 class="musica">${musica.nome}</h2>
                <h2 class="cantor">(${musica.cantor})</h2>
                <button onclick="botaoClicado(${musica.id})">Escolher outra Música</button>
            </div>
        
        `
    })
}

//Escolhendo outra música
function botaoClicado(id) {
    fetch(urlBase + `musica/pular/${id}`)
    .then(res => res.json())
    .then(novaMusica => {
        const modulos = document.querySelector('.modulos')

        modulos.innerHTML = `
            <div class="modulo">
                <h4 class="id">Id: ${novaMusica.id}</h4>
                <h2 class="musica">${novaMusica.nome}</h2>
                <h2 class="cantor">(${novaMusica.cantor})</h2>
                <button onclick="botaoClicado(${novaMusica.id})">Escolher outra Música</button>
            </div>
        
        `
    })
}

//Confirmando
async function confirmar() {
    const id = parseInt(document.querySelector('.id').textContent.match(/\d+/)[0])
    const nome = document.querySelector('.musica').textContent
    const cantor = document.querySelector('.cantor').textContent.replace(/[()]/g, '')
    const categoria = categoriaSelecionada

    const obj = [{id, nome, cantor, categoria}]

    const config = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
    }

    try {
        const res = await fetch(urlBase + 'musicas/confirmar', config)

        if(res.ok) { 
            alert(`
                Confirmada com sucesso!

                ${obj[0].nome} - ${obj[0].cantor}
                
            `)
        } else {
            alert('Música não confirmada! Ocorreu um erro!')
        }
    }  catch(e) {
        alert('Ouve um erro no servidor!')
    }
}

//cancelando
async function cancelar() {
    let res = await fetch(urlBase + 'musicas/cancelar')
    console.log(`Status da requisição de cancelamento: ${res.status}`)
    window.location.href = '../index.html'
}