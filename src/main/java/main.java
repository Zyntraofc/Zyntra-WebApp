import org.example.dao.EmpresaDAO;
import org.example.model.Empresa;


public class main {
    public static void main(String[] args){
        Empresa e = new Empresa(1, 1, 1, "teste", "12345678901010", "testeee@gmail.com", "11912345670");
        EmpresaDAO dao = new EmpresaDAO();
        System.out.println(dao.inserirEmpresa(e) ? "Sucesso" : "Falha");
    }
}
