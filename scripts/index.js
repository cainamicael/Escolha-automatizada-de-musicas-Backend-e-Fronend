alert('AVISO: Não esqueça de clicar no confirmar e caso desista, clique em cancelar!')

const arraySelecionadas = []

//Remover a classe inicio
function removerInicio() {
    document.querySelectorAll('.inicio').forEach(inicio => inicio.remove())
}

function pegarValor() {
    valor = document.getElementById('selecao').value 
    if(valor != '') {
        
        //tirar a classe inicio
        removerInicio()

        //adicionar módulo com a categoria selecionada
        fetch(urlBase + `musica?categoria=${valor}`)
        .then(res => res.json())
        .then(musica => {
            const modulos = document.querySelector('.modulos').innerHTML
            document.querySelector('.modulos').innerHTML = modulos + `
                <div class="modulo" id="modulo-${musica.id}">
                    <h4>♬</h4>
                    <input type="hidden" id="hidden-${musica.id}" value="${musica.id}">
                    <h2 class="musica" id="musica-${musica.id}">${musica.nome}</h2>
                    <h2 class="cantor" id="cantor-${musica.id}">(${musica.cantor})</h2>
                    <button id="botao-${musica.id}" onclick="botaoClicado(${musica.id})">Escolher outra Música</button>
                    <button id="adicionar" onclick="adicionar()">+</button>
                    <button id="remover" onclick="remover(${musica.id})">-</button>
                </div>
            `
        })
        
    }
}

//Quando clicamos no botão +
function adicionar() {
    //Pegar tudo o que tem nos módulos, guardar e concatenar
    const modulos = document.querySelector('.modulos').innerHTML

    document.querySelector('.modulos').innerHTML = modulos + `
        <div class="inicio">
            <h1>Selecione a catagoria da primeira música:</h1>

            <select name="categoria" id="selecao" onchange="pegarValor()">
                <option value="">Selecione</option>
                <option value="ADORACAO">Adoração</option>
                <option value="CELEBRACAO">Celebração</option>
            </select>
        </div>
    `

    let totalInicios = document.querySelectorAll('.inicio').length

    document.querySelectorAll('.inicio').forEach((e, indice) => {
        if(indice != totalInicios-1) {
            e.remove()
        }
    })
}

//Quando clicamos no botão -
function remover(id) {
    const totalElementos = document.querySelectorAll(`.modulo`).length

    if(totalElementos > 1) {
        const removerElemento = document.querySelector(`#modulo-${id}`).remove()
    } else {
        alert('Insira outras músicas para poder remover esta, ou clique em cancelar e começe do zero')
    }

}

//Escolhendo outra música
function botaoClicado(id) {
    fetch(urlBase + `musica/pular/${id}`)
    .then(res => res.json())
    .then(musica => {
        //Mudar os valores do módulo específico
        const moduloEspecifico = document.getElementById(`modulo-${id}`)

        moduloEspecifico.innerHTML = `
            <h4>♬</h4>
            <input type="hidden" id="hidden-${musica.id}" value="${musica.id}">
            <h2 class="musica" id="musica-${musica.id}">${musica.nome}</h2>
            <h2 class="cantor" id="cantor-${musica.id}">(${musica.cantor})</h2>
            <button id="botao-${musica.id}" onclick="botaoClicado(${musica.id})">Escolher outra Música</button>
            <button id="adicionar" onclick="adicionar()">+</button>
            <button id="remover" onclick="remover(${musica.id})">-</button>
        `
        moduloEspecifico.id = `modulo-${musica.id}`
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