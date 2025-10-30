///JavaScript criado para realizar pesquisa automárica no CRUD

//Função de busca
function search() {
    //Valor do input de pesquisa
    let input = document.getElementById("searchbar").value.toLowerCase().trim();

    //Pega cada linha do html do CRUD
    let x = document.getElementsByClassName("linhas");

    //Looping que fará verificação
    for (i = 0; i < x.length; i++) {

        //Seleciona a coluna de pesquisa
        let valorInput = x[i].querySelector('td[data-label="Pesquisar"]');

        let valor = '';
        if (valorInput) {
            valor = valorInput.dataset.original || valorInput.textContent.trim();
        }

        // Verifica se é uma string de porcentagem no formato "X% - Y%"
        if (valor.includes('%') && valor.includes('-')) {
            // Divide a string usando o hífen como separador
            let partes = valor.split('-');

            if (partes.length === 2) {
                // Extrai os números das porcentagens
                let minPercent = parseFloat(partes[0].replace('%', '').trim());
                let maxPercent = parseFloat(partes[1].replace('%', '').trim());

                // Converte o input para número
                let inputNumber = parseFloat(input);

                // Verifica se o input é um número válido e está dentro do intervalo
                if (!isNaN(inputNumber) && !isNaN(minPercent) && !isNaN(maxPercent)) {
                    if (inputNumber > minPercent && inputNumber <= maxPercent) {
                        x[i].style.display = "table-row";
                        continue;
                    } else if (inputNumber === 0 && minPercent === 0) {
                        x[i].style.display = "table-row";
                        continue;
                    } else {
                        x[i].style.display = "none";
                        continue;
                    }
                }
            }
        }

        // Se não for porcentagem faz a busca normal
        if (!valor.toLowerCase().includes(input)) {
            x[i].style.display = "none";
        } else {
            x[i].style.display = "table-row";
        }
    }
}