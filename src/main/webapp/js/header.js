document.addEventListener("DOMContentLoaded", () => {
  const navLinks = document.querySelectorAll(".nav-item");

  // Pega as seções necessárias
  const sections = {
    main: document.querySelector("#main"),
    plataform: document.querySelector("#plataform"),
    vantagens: document.querySelector(".vantagens"),
    celulares: document.querySelector("#celulares"),
    aboutWrapper: document.querySelector("#about-wrapper"),
  };

  function onScroll() {
    const scrollY = window.scrollY + 200; // margem de ativação
    let current = "";

    const topMain = sections.main.offsetTop;
    const topPlataform = sections.plataform.offsetTop;
    const topVantagens = sections.vantagens.offsetTop;
    const topCelulares = sections.celulares.offsetTop;
    const topAbout = sections.aboutWrapper.offsetTop;

    if (scrollY >= topMain && scrollY < topPlataform) {
      current = "main";
    } else if (scrollY >= topPlataform && scrollY < topVantagens) {
      current = "plataform";
    } else if (scrollY >= topVantagens && scrollY < topAbout) {
      current = "celulares"; // ativa “Aplicativo” entre vantagens e about-wrapper
    } else if (scrollY >= topAbout) {
      current = "final";
    }

    navLinks.forEach(link => {
      const href = link.getAttribute("href").substring(1);
      if (href === current) {
        link.classList.add("active");
      } else {
        link.classList.remove("active");
      }
    });
  }

  window.addEventListener("scroll", onScroll);
  onScroll();
});
