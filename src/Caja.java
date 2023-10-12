
class Caja {
    private String codigo;
    private String empresa;

    public Caja(String codigo, String empresa) {
        this.codigo = codigo;
        this.empresa = empresa;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return "Caja --> || Código: " + codigo + " | Empresa: " + empresa + " ||";
    }


}