<?php


namespace list9\repository\doctrine;


use Doctrine\ORM\EntityRepository;
use list9\repository\TransactionRepository;
use list9\Transaction;
use Ramsey\Uuid\Uuid;

class DoctrineTransactionRepository extends EntityRepository implements TransactionRepository {


    public function get(Uuid $transactionId): Transaction {
        return $this->_em->find('list9\Transaction', $transactionId);
    }

    public function save(Transaction $transaction): void {
        $this->_em->persist($transaction);
        $this->_em->flush();
    }
}