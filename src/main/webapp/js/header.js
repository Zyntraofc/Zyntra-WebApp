document.addEventListener("DOMContentLoaded", () => {
  const navLinks = document.querySelectorAll(".nav-item");
  const sections = {
    main: document.querySelector("#main"),
    plataform: document.querySelector("#plataform"),
    celulares: document.querySelector("#celulares"),
    nos: document.querySelector("#about-us"),
  };

  function onScroll() {
    const scrollY = window.scrollY + 200;

    const topMain = sections.main.offsetTop;
    const topPlataform = sections.plataform.offsetTop;
    const topCelulares = sections.celulares.offsetTop;
    const topNos = sections.nos.offsetTop;

    let current = "";

    if (scrollY >= topMain && scrollY < topPlataform) {
      current = "main";
    } else if (scrollY >= topPlataform && scrollY < topCelulares) {
      current = "plataform";
    } else if (scrollY >= topCelulares && scrollY < topNos) {
      current = "celulares";
    } else if (scrollY >= topNos) {
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

