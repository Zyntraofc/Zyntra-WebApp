import React, { useState, useEffect } from 'react';
import './Navbar.css';

const Navbar = () => {
    const [activeSection, setActiveSection] = useState('inicio');
    const [isScrolled, setIsScrolled] = useState(false);

    // Efeito para detectar scroll e mudar fundo
    useEffect(() => {
        const handleScroll = () => {
            setIsScrolled(window.scrollY > 100);
        };

        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, []);

    // Efeito para detectar seção ativa
    useEffect(() => {
        const handleScrollSection = () => {
            const sections = ['main', 'plataform', 'celulares', 'final'];
            const currentSection = sections.find(section => {
                const element = document.getElementById(section);
                if (element) {
                    const rect = element.getBoundingClientRect();
                    return rect.top <= 100 && rect.bottom >= 100;
                }
                return false;
            });

            if (currentSection) {
                setActiveSection(currentSection === 'main' ? 'inicio' :
                    currentSection === 'plataform' ? 'plataforma' :
                        currentSection === 'celulares' ? 'aplicative' : 'sobre');
            }
        };

        window.addEventListener('scroll', handleScrollSection);
        return () => window.removeEventListener('scroll', handleScrollSection);
    }, []);

    const handleNavClick = (section) => {
        setActiveSection(section);
    };

    return (
        <header className={isScrolled ? 'scrolled' : ''}>
            <section className="brand-container">
                <img
                    src="/src/assets/logo-azul.png"
                    alt="Logo Aion"
                    className="brand-logo"
                />
                <p className="brand-name">aion</p>
            </section>

            <section className="navigation-container">
                <ol className="navigation-menu" id="navigation-menu">
                    <div>
                        <li>
                            <a
                                href="https://zyntra-webapp.onrender.com/#main"
                                className={nav-item inicio ${activeSection === 'inicio' ? 'active' : ''}}
                                onClick={() => handleNavClick('inicio')}
                            >
                                <p className="nav-link">Início</p>
                            </a>
                            <div className="active-indicator"></div>
                        </li>
                    </div>

                    <div>
                        <li>
                            <a
                                href="https://zyntra-webapp.onrender.com/#plataform"
                                className={nav-item plataforma ${activeSection === 'plataforma' ? 'active' : ''}}
                                onClick={() => handleNavClick('plataforma')}
                            >
                                <p className="nav-link">Plataforma</p>
                            </a>
                            <div className="active-indicator"></div>
                        </li>
                    </div>

                    <div>
                        <li>
                            <a
                                href="https://zyntra-webapp.onrender.com/#celulares"
                                className={nav-item aplicative ${activeSection === 'aplicative' ? 'active' : ''}}
                                onClick={() => "handleNavClick('aplicative')"}
                            >
                                <p className="nav-link">Aplicativo</p>
                            </a>
                            <div className="active-indicator"></div>
                        </li>
                    </div>

                    <div>
                        <li>
                            <a
                                href="#final"
                                className={nav-item sobre ${activeSection === 'sobre' ? 'active' : ''}}
                                onClick={() => handleNavClick('sobre')}
                            >
                                <p className="nav-link">Sobre nós</p>
                            </a>
                            <div className="active-indicator"></div>
                        </li>
                    </div>
                </ol>
            </section>

            <section className="auth-buttons">
                <a href="">
                    <p className="login-button">Notícias</p>
                </a>
                <a href="https://ms-aion-react-v1.onrender.com/">
                    <button className="signup-button">
                        <p>Entrar</p>
                    </button>
                </a>
            </section>
        </header>
    );
};

export default Navbar;