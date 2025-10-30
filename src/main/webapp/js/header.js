///Esse JavaScript foi criado para realizar uma animação sincronizada nos tópicos da página, do header conforme o usuário se mover pela página
 
//Adiciona função na página de CRUD e espera o DOM carregar antes de executar
document.addEventListener("DOMContentLoaded", () => {

    //Seleciona o elemento do intem de navegação (tópico de localização da página)
    const navLinks = document.querySelectorAll(".nav-item");

    // Pega as seções necessárias da página para localizar depois
    const sections = {
        main: document.querySelector("#main"),
        plataform: document.querySelector("#plataform"),
        vantagens: document.querySelector(".vantagens"),
        celulares: document.querySelector("#celulares"),
        aboutWrapper: document.querySelector("#about-wrapper"),
    };

    //Função que mudará o estado conforme o scrow
    function onScroll() {
        //Recebe o scroll e margem de ativação
        const scrollY = window.scrollY + 200; 

        //Armazena seção atual
        let current = "";

        //Localiza cada setTop
        const topMain = sections.main.offsetTop;
        const topPlataform = sections.plataform.offsetTop;
        const topVantagens = sections.vantagens.offsetTop;
        const topCelulares = sections.celulares.offsetTop;
        const topAbout = sections.aboutWrapper.offsetTop;

        //Ativa as animações dependendo de onde o usuário está
        if (scrollY >= topMain && scrollY < topPlataform) {
            current = "main";
        } else if (scrollY >= topPlataform && scrollY < topVantagens) {
            current = "plataform";
        } else if (scrollY >= topVantagens && scrollY < topAbout) {
            current = "celulares"; 
        } else if (scrollY >= topAbout) {
            current = "final";
        }

        //Ativa os links dos tópicos
        navLinks.forEach(link => {
            const href = link.getAttribute("href").substring(1);
            if (href === current) {
                link.classList.add("active");
            } else {
                link.classList.remove("active");
            }
        });
    }

    //Adiciona função na página
    window.addEventListener("scroll", onScroll);

    //Utiliza a função
    onScroll();
});
