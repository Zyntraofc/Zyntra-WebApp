///Esse javaScript altera o style do botão de "Entrar" no header, para quando o botão chegar a uma superfice roxa se tornar branco


//Adiciona função na vizualização pelo scroll
window.addEventListener('scroll', function () {

    //Variável imutável com valor do primeiro elemento de classe "signup-button" (botão de login na plataforma)
    const signupButton = document.querySelector('.signup-button');
    //Variável imutável com valor do primeiro elemento de ID "app-waves" (decoração de fundo)
    const secaoAzul = document.querySelector('#app-waves');

    //Se não existir o elemento finaliza a função
    if (!secaoAzul) return;

    //Armazena posição da seção azul em uma variável imutável
    const rect = secaoAzul.getBoundingClientRect();

    // Quando o botão estiver sobre a seção , adiciona novo modo de botão no css, para que o botão mude
    if (rect.top <= -75) {
        signupButton.classList.add('sobre-fundo-azul');
    } else {
        signupButton.classList.remove('sobre-fundo-azul');
    }
});