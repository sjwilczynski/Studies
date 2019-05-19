<?php


namespace list8\financialCommitment\commands;


use list8\financialCommitment\FinancialCommitmentIllegalOperationException;
use list8\financialCommitment\repository\FinancialCommitmentNotFoundException;
use list8\financialCommitment\repository\InMemoryRepository;

class PayFinancialCommitmentHandler {

    /**
     * @throws FinancialCommitmentIllegalOperationException
     * @throws FinancialCommitmentNotFoundException
     */
    public function __invoke(PayFinancialCommitmentCommand $command) {
        $repository = InMemoryRepository::getInstance();
        $commitment = $repository->load($command->getCommitmentId());
        $commitment->registerPayment($command->getAmount());
        $repository->save($commitment);
    }

}