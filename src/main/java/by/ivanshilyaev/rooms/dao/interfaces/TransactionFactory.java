package by.ivanshilyaev.rooms.dao.interfaces;

public interface TransactionFactory {
    Transaction createTransaction();

    void close();
}
