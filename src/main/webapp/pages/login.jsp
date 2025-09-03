<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>aion/login</title>
    <link rel="stylesheet" href="../css/login.css">
</head>
<body>
    <main>
    </main>
    
<section>
    <div name="container">
            <!-- <div name="form"> -->
                <div name="options">
                <form action="">
                    <input type="radio" checked name="Entrar" name="opcoes">
                    <label for="Entrar">Entrar</label>
                </form>
                </div>
            <!-- </div> -->

        <div class="conteudo a">
                <h1>Acesse sua conta!</h1>
                <form action="" name="e-senha">
                    <div>
                        <label for="email">E-mail</label>
                    <input type="email" name="" required name="email" placeholder="Digite aqui  seu e-mail">
                    </div>
                    
                    <div>
                        <label for="senha">Senha</label>
                    <input type="password" name="" required name="senha" placeholder="Digite aqui  sua senha">
                    </div>
                    <div class="botao"><button type="submit">Fazer Login
                </button></div>
                </form> 
                
        </div>

        <div name="cadastro">
         
            <div class="conteudo b">
                <h1>Cadastre-se</h1>
                <form action="" name="identificacao">
                    <h3>Dados da empresa</h3>
                    <div>
                        <label for="nome">Nome da empresa</label>
                    <input type="text" name="" name="nome" required>
                    </div>
                    <div>
                        <label for="cnpj">CNPJ</label>
                    <input type="text" name="" name="cnpj" required>
                    </div>
                    
                    <div>
                        <label for="tel">Telefone comercial</label>
                    <input type="tel" name="" name="tel" required>
                    </div>
                <button type="submit">
                        <div class="prosseguir">Prosseguir</div>
                        <img src="../assets/seta.png" alt="">
                </button>
                </form> 
                
            </div> 
       
            <!-- <div class="conteudo c">
                <h1>Cadastre-se</h1>
                <form action="" name="endereco">
                    <h3>Endereço</h3>
                    <div name="line1">
                        <div class="pergunta">
                            <label for="state">Estado</label>
                            <input type="text" name="" name="state" required>
                        </div>
                        <div class="pergunta">
                            <label for="city">Cidade</label>
                            <input type="text" name="" name="city" required>
                        </div>
                    </div>
                    <div name="line2">
                        <div class="pergunta">
                            <label for="rua">Rua</label>
                            <input type="text" name="" name="rua" required>
                        </div> <div class="pergunta">
                            <label for="num">Numero</label>
                            <input type="text" name="" name="num" required>
                        </div>
                    </div>
                    <div class="pergunta">
                        <label for="complemento">Complemento</label>
                    <input type="tel" name="" name="complemento" required>
                    </div>
                    
                    <button>
                <div class="prosseguir">Prosseguir <img src="../assets/seta.png" alt=""></div>
                </button>
                </form> 
            </div> -->
<!--             
            <div class="conteudo d">
                <h1>Cadastre-se</h1>
                <form action="" name="identificacao">
                    <h3>Dados de login</h3>
                    <div>
                        <label for="email-cadastro">E-mail</label>
                    <input type="email" name="" required name="email-cadastro" >
                    </div>
                    
                    <div>
                        <label for="senha-cadastro">Senha</label>
                    <input type="password" name="" required name="senha-cadastro" >
                    </div>
                    
                    <div>
                        <label for="confirma-senha">Confirmação de senha</label>
                    <input type="password" name="" required name="confirma-senha" >
                    </div>
                    
                    <div class="botao"><button type="submit">Finalizar
                    </button></div>
                </form> 
            </div>  -->
        </div>
    </div>

</section>
    
</body>
</html>