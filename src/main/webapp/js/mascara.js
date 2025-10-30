///Esse JavaScript esconde o valor de determinados campos do CRUD quando botão de máscara é selecionado

//Adiciona função na página de CRUD e espera o DOM carregar antes de executar
document.addEventListener('DOMContentLoaded', () => {
    // primeiro: inicializa as células sensíveis ocultas
    //Localiza as linhas e começa com "•" ao invés do valor
    document.querySelectorAll('tr').forEach(linha => {
        //Localiza os valores sensíveis de cada coluna
        const celulas = linha.querySelectorAll('.sensivel');
        celulas.forEach(td => {
            //Recebe o valor original sem espaços ao redor
            const valorOriginal = td.textContent.trim();
            //Substitui cada caractére por "•"
            td.dataset.original = valorOriginal;
            td.textContent = valorOriginal.replace(/./g, '•'); 
        });
    });

    // Quando o botão está ativo usa máscara

    //Localiza o botão na linha
    document.querySelectorAll('.toggleLinha').forEach(botao => {

        //Comexa como ocult
        botao.dataset.oculto = 'true'; // começa como oculto
        //Ao clicar ativa a função
        botao.addEventListener('click', e => {

            //Evita comportamento padrão da célula
            e.preventDefault();

            //Seleciona valores sensíveis de células
            const linha = botao.closest('tr');
            const celulas = linha.querySelectorAll('.sensivel');
            const oculto = botao.dataset.oculto === 'true';

            //Se adequa se o botão tiver ou não selecionado
            celulas.forEach(td => {
                //Originalmente ocuto
                td.textContent = oculto

                //Mosta texto original
                    ? td.dataset.original 
                    //Esconde
                    : td.dataset.original.replace(/./g, '•');
            });

            //Seleciona imagem do botão de máscara
            const img = botao.querySelector('img');

            //Originalmente o icone é de botão oculto
            img.src = oculto
            //Botão de olho aberto
                ? botao.dataset.olho
                //Botão de olho fechado
                : botao.dataset.olhoFechado;
                //Inverte o resultado (De ativo para inativo ou vice-versa)
            botao.dataset.oculto = (!oculto).toString();
        });
    });
});