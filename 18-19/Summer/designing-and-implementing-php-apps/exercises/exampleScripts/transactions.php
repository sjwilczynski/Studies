<?php

use list9\Status;
use list9\Transaction;
use Money\Money;
use Ramsey\Uuid\Uuid;

require_once __DIR__ . "/../bootstrap.php";

$transactionRepository = $entityManager->getRepository('list9\Transaction');

$transaction = new Transaction(Uuid::uuid1(), Money::PLN(500), 'acc1', 'acc2', Status::ACTIVE());

$transactionRepository->save($transaction);