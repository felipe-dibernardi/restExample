package br.com.fdbst.restexample.service;

import br.com.fdbst.restexample.entity.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Classe que implementa as regras de negócio da entidade Usuário. 
 * @author Felipe Di Bernardi S Thiago
 */
@Stateless
public class UsuarioService {
    
    @PersistenceContext
    private EntityManager em;
   
    /**
     * Lista todos os Usuarios do sistema.
     * @return Lista de Usuarios.
     */
    public List<Usuario> listAll() {
        Query query = em.createNamedQuery("Usuario.listAll");
        return query.getResultList();
    }
    
    /**
     * Lista os Usuarios do sistema atraves de parametros.
     * @param nome Nome do Usuario.
     * @return Lista de Usuarios.
     */
    public List<Usuario> listByParams(final String nome) {
        StringBuilder strQuery = new StringBuilder();
        
        strQuery.append("SELECT u FROM Usuario u");
        strQuery.append(" WHERE u.id IS NOT NULL");
        
        if (nome != null && !nome.equals("")) {
            strQuery.append(" AND u.nome like '%").append(nome).append("%'");
        }
        
        strQuery.append(" ORDER BY u.nome");
        
        Query query = em.createQuery(strQuery.toString());
        
        return query.getResultList();
    }
    
    /**
     * Recupera um Usuario do sistema através do Id.
     * @param id Id do Usuario selecionado.
     * @return Usuario selecionado.
     */
    public Usuario find(final Integer id) {
        return em.find(Usuario.class, id);
    }
    
    /**
     * Insere um Usuario o sistema.
     * @param usuario Usuario a ser inserido.
     * @return Usuario inserido.
     */
    public Usuario insert(final Usuario usuario) {
        em.persist(usuario);
        em.flush();
        return usuario;
    }
    
    /**
     * Atualiza um Usuario do sistema.
     * @param usuario Usuario a ser atualizado.
     * @return Usuario atualizado.
     */
    public Usuario update(final Usuario usuario) {
        em.merge(usuario);
        em.flush();
        return usuario;
    }
    
    /**
     * Remove um Usuario do sistema através do Id.
     * @param id Id do Usuario a ser removido.
     * @return Usuario removido.
     */
    public Usuario remove(final Integer id) {
        Usuario usuario = this.find(id);
        em.remove(usuario);
        em.flush();
        return usuario;
    }
}
