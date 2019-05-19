<?php


namespace list8\financialCommitment\repository;


use list8\financialCommitment\FinancialCommitment;
use Ramsey\Uuid\UuidInterface;

interface FinancialCommitmentRepository {

    /**
     * @throws FinancialCommitmentNotFoundException
     */
    public function load(UuidInterface $id): FinancialCommitment;

    public function save(FinancialCommitment $commitment): void;
}