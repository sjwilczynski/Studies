<?php

use list9\repository\doctrine\DoctrineTransactionFinder;
use list9\Status;
use list9\Transaction;
use Money\Money;
use Ramsey\Uuid\Uuid;

require_once __DIR__ . "/../bootstrap.php";

$transactionRepository = $entityManager->getRepository('list9\Transaction');


//create and save
$newTransaction = new Transaction(Uuid::uuid1(), Money::PLN(50), 'acc4', 'acc7', Status::CLOSED());
$transactionRepository->save($newTransaction);

//fetch, edit, save

$id = Uuid::fromString('a35db5cc-816f-11e9-94c0-3c2c30aa334b');
$transaction = $transactionRepository->get($id);
$transaction->setAmount(Money::USD(300));
$transactionRepository->save($transaction);

// transaction finder
$transactionFinder = new DoctrineTransactionFinder($entityManager);
$transactions = $transactionFinder->findAll(3, 1);
foreach ($transactions as $trans) {
    var_dump($trans);
}


//what UTs for this classes, mock of repository finder or mock of database?