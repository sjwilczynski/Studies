<?php


namespace list9\repository;


use list9\Transaction;
use Ramsey\Uuid\Uuid;

interface TransactionRepository {
    public function get(Uuid $transactionId): Transaction;

    public function save(Transaction $transaction): void;
}
