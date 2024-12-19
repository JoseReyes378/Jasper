package org.example.demo1.dao;


import org.example.demo1.model.Copia;
import org.example.demo1.model.Usuario;
import org.example.demo1.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class CopiaDAO extends GenericDAO<Copia> {
    public CopiaDAO() {
        super(Copia.class);
    }

    public List<Copia> findByUsuario(Usuario usuario) {
        String sql = "SELECT * FROM copia WHERE usuario_id = :usuarioId";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNativeQuery(sql, Copia.class)
                    .setParameter("usuarioId", usuario.getId())
                    .list();
        }
    }
}
