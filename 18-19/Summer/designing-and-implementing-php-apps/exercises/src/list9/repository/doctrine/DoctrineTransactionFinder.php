<?php


namespace list9\repository\doctrine;


use list9\repository\TransactionFinder;
use list9\Transaction;

class DoctrineTransactionFinder implements TransactionFinder {

    /**
     * @return Transaction[]
     */
    public function findAll(int $limit = 10, int $offset = 0) {

    }
}