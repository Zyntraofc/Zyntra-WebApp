document.querySelectorAll('.toggleLinha').forEach(botao => {
    botao.addEventListener('click', e => {
        e.preventDefault();

        // pega a linha do botão clicado
        const linha = botao.closest('tr');

        // pega todas as células sensíveis dessa linha
        const celulas = linha.querySelectorAll('.sensivel');

        // guarda os valores originais se ainda não tiver guardado
        celulas.forEach(td => {
            if (!td.dataset.original) td.dataset.original = td.textContent.trim();
        });

        // alterna estado oculto/visível
        const oculto = botao.dataset.oculto !== 'false';
        celulas.forEach(td => {
            td.textContent = oculto
                ? td.dataset.original.replace(/./g, '*')
                : td.dataset.original;
        });

        // troca ícone e estado
        const img = botao.querySelector('img');
        img.src = oculto
            ? botao.dataset.olhoFechado
            : botao.dataset.olho;
        botao.dataset.oculto = !oculto;
    });
});