<?php


namespace list8\financialCommitment\repository;


use list8\financialCommitment\FinancialCommitment;
use Ramsey\Uuid\UuidInterface;

class InMemoryRepository implements FinancialCommitmentRepository {

    /**
     * @var FinancialCommitmentRepository
     */
    private static $instance;

    /**
     * @var FinancialCommitment[]
     */
    private $commitments = [];

    /**
     * InMemoryRepository constructor.
     */
    private function __construct() {
    }

    public static function getInstance() {
        if (self::$instance == null) {
            self::$instance = new InMemoryRepository();
        }
        return self::$instance;
    }


    public function load(UuidInterface $id): FinancialCommitment {
        if (!array_key_exists($id->toString(), $this->commitments)) {
            throw new FinancialCommitmentNotFoundException();
        }
        return $this->commitments[$id->toString()];
    }

    public function save(FinancialCommitment $commitment): void {
        $this->commitments[$commitment->getId()->toString()] = $commitment;
    }
}