<?php


namespace list8\financialCommitment\commands;


use list8\financialCommitment\FinancialCommitmentIllegalOperationException;
use list8\financialCommitment\repository\FinancialCommitmentNotFoundException;
use list8\financialCommitment\repository\InMemoryRepository;

class CancelFinancialCommitmentHandler {

    /**
     * @throws FinancialCommitmentIllegalOperationException
     * @throws FinancialCommitmentNotFoundException
     */
    public function __invoke(CancelFinancialCommitmentCommand $command) {
        $repository = InMemoryRepository::getInstance();
        $commitment = $repository->load($command->getCommitmentId());
        $commitment->cancel();
        $repository->save($commitment);
    }

}