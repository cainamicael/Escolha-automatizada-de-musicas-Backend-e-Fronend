alert('AVISO: Não esqueça de clicar no confirmar e caso desista, clique em cancelar!')

//Remover a classe inicio
function removerInicio() {
    document.querySelectorAll('.inicio').forEach(inicio => inicio.remove())
}

//Onchange do select
function pegarValor() {
    const valor = document.getElementById('selecao').value
    if (valor != '') {

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
                    <input type="hidden" id="id" value="${musica.id}">
                    <input type="hidden" id="categoria-${musica.id}" value="${valor}">
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
            <h1>Selecione a catagoria da música:</h1>

            <select name="categoria" id="selecao" onchange="pegarValor()">
                <option value="">Selecione</option>
                <option value="ADORACAO">Adoração</option>
                <option value="APELO">Apelo</option>
                <option value="CEIA">Ceia</option>
                <option value="CELEBRACAO">Celebração</option>
                <option value="CIRCULO_DE_ORACAO">Círculo de Oração</option>
                <option value="CORINHOS">Corinhos</option>
                <option value="FAMILIA">Família</option>
                <option value="GRATIDAO">Gratidão</option>
                <option value="MISSOES">Missões</option>
                <option value="MULHERES">Mulheres</option>
                <option value="NATAL">Natal</option>
            </select>
        </div>
    `

    let totalInicios = document.querySelectorAll('.inicio').length

    document.querySelectorAll('.inicio').forEach((e, indice) => {
        if (indice != totalInicios - 1) {
            e.remove()
        }
    })
}

//Quando clicamos no botão -
function remover(id) {
    const totalElementos = document.querySelectorAll(`.modulo`).length

    if (totalElementos > 1) {
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
            <input type="hidden" id="id" value="${musica.id}">
            <input type="hidden" id="categoria-${musica.id}" value="${musica.categoria}">
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
    //Para só poder fazer o post para confirmar as músicas se tiver alguma música selecionada

    if (document.querySelectorAll('.modulo').length > 0) {
        const obj = []

        //Pegar todos os elementos com a classe .modulo e seus valores, e adicionar no array
        const classeModulo = document.querySelectorAll('.modulo')

        classeModulo.forEach(modulo => {

            //Preciso do nome, cantor e categoria de cada div
            const id = modulo.querySelector('#id').value

            const nome = modulo.querySelector(`#musica-${id}`).textContent
            //Remove o primeiro e o último caractere ( )
            const cantor = modulo.querySelector(`#cantor-${id}`).textContent.slice(1).slice(0, -1)
            const categoria = modulo.querySelector(`#categoria-${id}`).value

            obj.push({ nome, cantor, categoria })
        })

        const config = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(obj)
        }

        try {
            const res = await fetch(urlBase + 'musicas/confirmar', config)

            if (res.ok) {
                alert(`Músicas confirmadas! `)

                let adicionar = ''

                obj.forEach(dado => {
                    adicionar += `
                        <h3>${dado.nome}</h3>
                        <h4>(${dado.cantor})</h4>
                        <br>
                    `
                })

                document.querySelector('.musicas-modal').innerHTML = adicionar

                abrirModal()
            } else {
                alert('Música não confirmada! Ocorreu um erro!')
            }
        } catch (e) {
            alert('Ouve um erro no servidor!')
        }
    } else {
        alert(`Escolha alguma música primeiro! `)
    }
}

//cancelando
async function cancelar() {
    let res = await fetch(urlBase + 'musicas/cancelar')
    console.log(`Status da requisição de cancelamento: ${res.status}`)
    window.location.href = '../index.html'
}

function abrirModal() {
    const modal = document.querySelector('.modal')
    const fade = document.querySelector('.fade')
    modal.classList.toggle('hide')
    fade.classList.toggle('hide')
}

function fecharModal() {
    const modal = document.querySelector('.modal')
    const fade = document.querySelector('.fade')
    modal.classList.toggle('hide')
    fade.classList.toggle('hide')
    document.querySelector('.musicas-modal').innerHTML = ``
}