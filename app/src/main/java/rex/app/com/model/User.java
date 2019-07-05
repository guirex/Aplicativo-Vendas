package rex.app.com.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class User implements Serializable {
    private String nome;
    private String sobrenome;
    private String funcao;
    private FirebaseUser firebaseUser;

    public User(){

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Exclude
    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    @Exclude
    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", funcao='" + funcao + '\'' +
                ", firebaseUser=" + firebaseUser +
                '}';
    }
}
