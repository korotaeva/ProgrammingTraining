package ru.innopolis.course3.dao;

import java.io.Serializable;
import java.util.List;

/**
 *  Created by korot on 26.12.2016.
 *
 *  Унифицированный интерфейс управления состоянием объектов
 * @param <T> тип объекта
 * @param <PK> тип первичного ключа
 */
public interface UniversalDao<T extends Identified<PK>, PK extends Serializable> {

    /** Создает новую запись и соответствующий ей объект */
    public T create() throws DataException;

    /** Создает новую запись, соответствующую объекту object */
    public T createByObject(T object)  throws DataException;

    /** Возвращает объект соответствующий записи с первичным ключом key или null */
    public T getByPK(PK key) throws DataException;

    /** Возвращает объект соответствующий записи с первичным ключом key или null */
    public List<T> getByKey(String key, String name) throws DataException;

    /** Сохраняет состояние объекта в базе данных */
    public void update(T object) throws DataException;

    /** Удаляет запись об объекте из базы данных */
    public void delete(T object) throws DataException;

    /** Возвращает список объектов соответствующих всем записям в базе данных */
    public List<T> getAll() throws DataException;
}
