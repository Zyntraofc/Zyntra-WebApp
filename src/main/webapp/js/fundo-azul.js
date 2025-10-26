window.addEventListener('scroll', function() {
    const signupButton = document.querySelector('.signup-button');
    const secaoAzul = document.querySelector('#app-waves');

    if (!secaoAzul) return;

    const rect = secaoAzul.getBoundingClientRect();

    // Quando o botão estiver sobre a seção azul
    if (rect.top <= -112) {
        signupButton.classList.add('sobre-fundo-azul');
    } else {
        signupButton.classList.remove('sobre-fundo-azul');
    }
});