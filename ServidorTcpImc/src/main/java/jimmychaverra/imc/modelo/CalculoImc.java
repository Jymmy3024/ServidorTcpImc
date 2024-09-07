package jimmychaverra.imc.modelo;

import java.io.Serializable;

public class CalculoImc implements Serializable {

    private float peso;
    private float altura;

    public static class Imc{
        public float resultado;
        public String mensaje;
    }

    private Imc imc;

    public CalculoImc(float peso, float altura){
        this.peso = peso;
        this.altura = altura;
    }

    public Imc getImc(){
        imc = new Imc();
        if(this.peso<=0 || this.altura<=0){
            imc.mensaje = "ERROR: El peso y la altura deben ser mayores que 0";
            return imc;
        }else{
            imc.resultado = peso / (altura * altura);
            if(imc.resultado < 18.5){
                imc.mensaje = "Debes consultar con tu medico, tu peso es muy bajo!";
            }else if(imc.resultado >= 18.5 && imc.resultado < 24.9){
                imc.mensaje = "Estas bien de peso";
            }else{
                imc.mensaje = "Debes consultar un Medico, tu peso es muy alto";
            }
            return imc;
        }
    }

    public float getPeso() {
        return this.peso;
    }

    public float getAltura() {
        return this.altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }
}
