<?php


namespace list8\financialCommitment\repository;


use list8\financialCommitment\FinancialCommitment;
use Ramsey\Uuid\UuidInterface;

class InMemoryRepository implements FinancialCommitmentRepository {

    /**
     * @var FinancialCommitment[]
     */
    private $commitments = [];

    public function load(UuidInterface $id): FinancialCommitment {
        if (!array_key_exists($id, $this->commitments)) {
            throw new FinancialCommitmentNotFoundException();
        }
        return $this->commitments[$id->toString()];
    }

    public function save(FinancialCommitment $commitment): void {
        $this->commitments[$commitment->getId()->toString()] = $commitment;
    }
}