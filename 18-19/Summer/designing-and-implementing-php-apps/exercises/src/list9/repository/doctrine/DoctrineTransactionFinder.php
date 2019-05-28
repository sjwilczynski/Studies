<?php


namespace list9\repository\doctrine;


use Doctrine\ORM\EntityManager;
use list9\repository\TransactionFinder;
use list9\Transaction;

class DoctrineTransactionFinder implements TransactionFinder {


    /**
     * @var EntityManager
     */
    private $entityManager;

    /**
     * DoctrineTransactionFinder constructor.
     * @param EntityManager $entityManager
     */
    public function __construct(EntityManager $entityManager) {
        $this->entityManager = $entityManager;
    }

    // czy to ma sens jesli nie ma oddzielnej klasy na DTO?

    /**
     * @return Transaction[]
     */
    public function findAll(int $limit = 10, int $offset = 0) {
        return $this->entityManager->createQueryBuilder()
            ->select('transaction')
            ->from('list9\Transaction', 'transaction')
            ->setMaxResults($limit)
            ->setFirstResult($offset)
            ->getQuery()
            ->getArrayResult();
    }
}