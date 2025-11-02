// Esse JavaScript realiza uma animação sincronizada nos tópicos da página,
// destacando o item de navegação conforme o usuário se move pela página.

document.addEventListener("DOMContentLoaded", () => {
    // Seleciona todos os itens de navegação
    const navLinks = document.querySelectorAll(".nav-item");

    // Define as seções da página
    const sections = {
        main: document.querySelector("#main"),
        plataform: document.querySelector("#plataform"),
        celulares: document.querySelector("#app-options"),
        aboutWrapper: document.querySelector("#about-wrapper"),
        footer: document.querySelector("#principles"),
    };

    // Função que atualiza o item ativo conforme o scroll
    function onScroll() {
        const scrollY = window.scrollY + 200; // margem para ativação
        let current = "";

        // Pega as posições das seções
        const topMain = sections.main.offsetTop;
        const topPlataform = sections.plataform.offsetTop;
        const topCelulares = sections.celulares.offsetTop;
        const topAbout = sections.aboutWrapper.offsetTop;
        const topFooter = sections.footer.offsetTop+300;

        // Define qual seção está ativa
        if (scrollY >= topMain && scrollY < topPlataform) {
            current = "main";
        } else if (scrollY >= topPlataform && scrollY < topCelulares) {
            current = "plataform";
        }  else if (scrollY >= topCelulares && scrollY < topAbout) {
            current = "celulares";
        } else if (scrollY >= topAbout && scrollY < topFooter) {
            current = "final";
        } else if (scrollY >= topFooter) {
            current = "footer"; //
        }

        // Atualiza o estilo ativo nos tópicos
        navLinks.forEach(link => {
            const href = link.getAttribute("href").substring(1);
            if (href === current) {
                link.classList.add("active");
            } else {
                link.classList.remove("active");
            }
        });
    }

    // Escuta o scroll
    window.addEventListener("scroll", onScroll);

    // Executa uma vez ao carregar
    onScroll();
});
