document.getElementById('formulario').addEventListener('submit', async (e) => {
    e.preventDefault()

    const nome = document.getElementsByName('nome')[0].value
    const cantor = document.getElementsByName('cantor')[0].value
    const categoria = document.getElementsByName('categoria')[0].value

    const obj = {nome, cantor, categoria}

    const config = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
    }

    const res = await fetch(urlBase + 'musica', config)

    if(res.ok) {
        alert('Música cadastrada com sucesso! (Cuidado para não ser uma música herege)')
        console.log(obj)

        document.getElementsByName('nome')[0].value = ''
        document.getElementsByName('cantor')[0].value = ''

    } else {
        alert('Erro ao tentar cadastrar música')
        console.log(obj)
    }
}) 