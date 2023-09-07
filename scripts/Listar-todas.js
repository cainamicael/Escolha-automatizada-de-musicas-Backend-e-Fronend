alert('Quando todas as músicas forem tocadas, a contagem de músicas tocadas irá zerar!')

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

        if (dados != []) {
            dados.forEach(dado => {
                let dataFormatada = ''

                if (dado.dataUltimaVezTocada != null) {
                    const data = new Date(dado.dataUltimaVezTocada);

                    const dia = data.getDate();
                    const mes = data.getMonth() + 1; // Lembrando que os meses em JavaScript são base 0
                    const ano = data.getFullYear();

                    dataFormatada = `${dia}/${mes}/${ano}`
                } else {
                    dataFormatada = 'Esta música ainda não foi tocada'
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
                    <p>${dataFormatada}</p>

                    <button class="editar" id="botao-editar" onclick="abrirModal(${dado.id})">Editar ✎</button>
                </div>
            `
            })
        }

        modulos.innerHTML = adicionar
    })

function abrirModal(id) {
    fetch(urlBase + `musica/${id}`)
        .then(res => res.json())
        .then(dado => {
            const modal = document.querySelector('.modal')
            modal.innerHTML = `

            <div class="modal-header">
                <span>Editar - Faça as Alterações</span>
                <button class="fechar-modal" onclick="fecharModal()">Fechar</button>
            </div>

            <div class="modal-main">
                <form>
                    <input type="text" name="nome" placeholder="Nome da música..." value="${dado.nome}" required>
                    <input type="text" name="cantor" placeholder="Nome de quem canta..." value="${dado.cantor}"
                        required>

                    <label for="selecao">Categoria: </label>
                    <select name="categoria" id="selecao">
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
                </form>
            </div>

            <div class="modal-footer">
                <button class="salvar-modal" onclick="salvarModal(${dado.id})">Salvar</button>
            </div>
            `

            const fade = document.querySelector('.fade')
            modal.classList.toggle('hide')
            fade.classList.toggle('hide')
        })
        .catch(e => alert(`Ocorreu um erro no servidor! ${e}`))
}

function fecharModal() {
    const modal = document.querySelector('.modal')
    const fade = document.querySelector('.fade')
    modal.classList.toggle('hide')
    fade.classList.toggle('hide')
    modal.innerHTML = ''
}

async function salvarModal(id) {
    //verificar se a categoria está selecionada
    if(document.getElementsByName('categoria')[0].value != '') {
        //Pegar os dados e enviar via post
        const nome = document.getElementsByName('nome')[0].value
        const cantor = document.getElementsByName('cantor')[0].value
        const categoria = document.getElementsByName('categoria')[0].value

        const obj = { nome, cantor, categoria }

        const config = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(obj)
        }

        const res = await fetch(urlBase + `musica/${id}`, config)
        if (res.ok) {
            alert('Música Atualizada!')
            fecharModal()

            //recarregar a página para já mostrar os valores atualizados
            location.reload()
        } else {
            alert('Erro ao tentar editar música')
        }
    } else {
        alert('Escolha a categoria para salvar!')
    }
}