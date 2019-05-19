<?php


namespace list8\financialCommitment\commands;


use list8\financialCommitment\FinancialCommitment;
use list8\financialCommitment\repository\InMemoryRepository;

class CreateFinancialCommitmentHandler {

    public function __invoke(CreateFinancialCommitmentCommand $command) {
        $repository = InMemoryRepository::getInstance();
        $repository->save(new FinancialCommitment($command->getAmount(), $command->getCommitmentId()));
    }


}