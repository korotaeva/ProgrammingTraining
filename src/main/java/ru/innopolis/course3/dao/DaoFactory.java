package ru.innopolis.course3.dao;


/**
 * Фабрика объектов для работы с базой данных
 * */
public interface DaoFactory<T> {

    public interface DaoCreator<T> {
        public UniversalDao create(T context);
    }

    /** Возвращает подключение к базе данных */
    public T getContext() throws DataException;

    /** Возвращает объект для управления состоянием объекта */
    public UniversalDao getDao(T context, Class dtoClass) throws DataException;
}
