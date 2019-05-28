<?php


namespace list9\repository;


use list9\Transaction;

interface TransactionFinder {
    /**
     * @return Transaction[]
     */
    public function findAll(int $limit = 10, int $offset = 0);
}
