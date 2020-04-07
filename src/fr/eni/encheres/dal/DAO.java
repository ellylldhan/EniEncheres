package fr.eni.encheres.dal;

import fr.eni.encheres.exception.DalException;

import java.util.List;

public interface DAO<T> {
    /**
     * Méthode en charge de récupérer une instance d'objet <T> en fonction de son id
     * @param id
     * @return T
     * @throws DalException
     */
    public T selectById(int id) throws DalException;

    /**
     * Méthode en charge de récupérer tous les enregistrements de la table du type de l'objet o
     * @return List<T>
     * @throws DalException
     */
    public List<T> selectAll() throws DalException;

    /**
     * Méthode en charge d'insérer un enregistrement dans la table du type de l'objet o
     * @param o
     * @throws DalException
     */
    public void insert(T o) throws DalException;

    /**
     * Méthode en charge de modifier un enregistrement de la table du type de l'objet o
     * @param o
     * @throws DalException
     */
    public void update(T o) throws DalException;

    /**
     * Méthode en charge de supprimer un enregistrement par son id
     * @param id
     * @throws DalException
     */
    public void delete(int id) throws DalException;
}
