alert('AVISO: Não esqueça de clicar no confirmar!')

const urlBase = 'http://localhost:8080/api/'

//Pegar a quantidade de músicas
var valorSelecionado = 3

const modulos = document.querySelector('.modulos')
modulos.innerHTML = ''

iterarQuantidades(valorSelecionado)
.then(resultado => modulos.innerHTML = resultado)

async function iterarQuantidades(valorSelecionado) {
    var resultados = []

    for (let i = 0; i < valorSelecionado; i++) {
        const categoria = (i === valorSelecionado - 1) ? 'celebracao' : 'adoracao'
        const res = await fetch(urlBase + `musica?categoria=${categoria}`)
        const musica = await res.json()

        resultados.push(`
            <div class="modulo" id="modulo-${i + 1}">
                <h4>${i + 1}ª</h4>
                <input type="hidden" id="hidden-${i + 1}" value="${musica.id}">
                <h2 class="musica" id="musica-${i + 1}">${musica.nome}</h2>
                <h2 class="cantor" id="cantor-${i + 1}">(${musica.cantor})</h2>
                <button id="botao-${i + 1}" onclick="botaoClicado(${musica.id}, ${i + 1})">Escolher outra Música</button>
            </div>
        `)
    }
    return resultados.join('');
}

//Escolhendo outra música
function botaoClicado(id, posicao) {
    fetch(urlBase + `musica/pular/${id}`)
    .then(res => res.json())
    .then(novaMusica => {
        //Mudar os valores do módulo específico
        const moduloEspecifico = document.getElementById(`modulo-${posicao}`)

        moduloEspecifico.innerHTML = `
            <h4>${posicao}ª</h4>
            <input type="hidden" id="hidden-${posicao}" value="${novaMusica.id}">
            <h2 class="musica" id="musica-${posicao}">${novaMusica.nome}</h2>
            <h2 class="cantor" id="cantor-${posicao}">(${novaMusica.cantor})</h2>
            <button id="botao-${posicao}" onclick="botaoClicado(${novaMusica.id}, ${posicao})">Escolher outra Música</button>
        `
    })
}

//Confirmando
async function confirmar() {
    const obj = []

    for(let i = 0; i < valorSelecionado; i++) {
        const id = parseInt(document.getElementById(`hidden-${i + 1}`).value)
        const nome = document.getElementById(`musica-${i + 1}`).textContent
        const cantor = document.getElementById(`cantor-${i + 1}`).textContent.replace(/[()]/g, '')
        const categoria = (i === valorSelecionado-1) ? 'CELEBRACAO' : 'ADORACAO'

        obj.push({id, nome, cantor, categoria})
    }

    console.log(JSON.stringify(obj))
    
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
            Músicas confirmadas! 
            
            Se você estiver usando o celular, 
            você pode copiar e colar no grupo:

            Graça e paz a todos! 
            Seguem as músicas que serão tocadas:
            
            ${obj[0].nome} - ${obj[0].cantor}
            ${obj[1].nome} - ${obj[1].cantor}
            ${obj[2].nome} - ${obj[2].cantor}

            Fiquem todos com Deus!
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