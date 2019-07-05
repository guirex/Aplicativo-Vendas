package rex.app.com.model;

import java.io.Serializable;

public class Cliente implements Serializable {
    private Long codigoDeBarras;
    private String CPF;
    private String Nome;
    private String Sobrenome;
    private String url_foto;
    private boolean situacao;
    private String key; //atributo apenas local

    public Cliente() {
    }

    public Long getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(Long codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getSobrenome() {
        return Sobrenome;
    }

    public void setSobrenome(String Sobrenome) {
        this.Sobrenome = Sobrenome;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return "Cliente{" +
                "codigoDeBarras=" + codigoDeBarras +
                ", cpf='" + CPF + '\'' +
                ", nome='" + Nome + '\'' +
                ", sobrenome='" + Sobrenome + '\'' +
                ", url_foto='" + url_foto + '\'' +
                ", situacao=" + situacao +
                ", key='" + key + '\'' +
                '}';
    }
}
