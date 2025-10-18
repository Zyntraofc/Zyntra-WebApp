document.addEventListener('DOMContentLoaded', () => {
    // primeiro: inicializa as células sensíveis ocultas
    document.querySelectorAll('tr').forEach(linha => {
        const celulas = linha.querySelectorAll('.sensivel');
        celulas.forEach(td => {
            const valorOriginal = td.textContent.trim();
            td.dataset.original = valorOriginal;
            td.textContent = valorOriginal.replace(/./g, '*'); // começa com asteriscos
        });
    });

    // agora o comportamento do botão
    document.querySelectorAll('.toggleLinha').forEach(botao => {
        botao.dataset.oculto = 'true'; // começa como oculto

        botao.addEventListener('click', e => {
            e.preventDefault();

            // pega a linha do botão clicado
            const linha = botao.closest('tr');

            // pega todas as células sensíveis dessa linha
            const celulas = linha.querySelectorAll('.sensivel');

            // alterna estado oculto/visível
            const oculto = botao.dataset.oculto === 'true';

            celulas.forEach(td => {
                td.textContent = oculto
                    ? td.dataset.original // mostra texto
                    : td.dataset.original.replace(/./g, '*'); // esconde
            });

            // troca ícone e estado
            const img = botao.querySelector('img');
            img.src = oculto
                ? botao.dataset.olho
                : botao.dataset.olhoFechado;
            botao.dataset.oculto = (!oculto).toString();
        });
    });
});
