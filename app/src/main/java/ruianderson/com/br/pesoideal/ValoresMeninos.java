package ruianderson.com.br.pesoideal;

/**
 * Created by Rui on 21/03/2015.
 */
public class ValoresMeninos {

    private int idade;
    private double normal;
    private double sobrepeso;
    private double obesidade;

    public ValoresMeninos(int idade, double normal, double sobrepeso, double obesidade) {
        this.idade = idade;
        this.normal = normal;
        this.sobrepeso = sobrepeso;
        this.obesidade = obesidade;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getNormal() {
        return normal;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }

    public double getSobrepeso() {
        return sobrepeso;
    }

    public void setSobrepeso(double sobrepeso) {
        this.sobrepeso = sobrepeso;
    }

    public double getObesidade() {
        return obesidade;
    }

    public void setObesidade(double obesidade) {
        this.obesidade = obesidade;
    }
}
