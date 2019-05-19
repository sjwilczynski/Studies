<?php


namespace list8\financialCommitment\commands;


use list7\command\Command;
use Ramsey\Uuid\UuidInterface;

class FinancialCommitmentCommand extends Command {

    /**
     * @var UuidInterface
     */
    private $commitmentId;

    /**
     * FinancialCommitmentCommand constructor.
     * @param UuidInterface $commitmentId
     */
    public function __construct(UuidInterface $commitmentId) {
        parent::__construct();
        $this->commitmentId = $commitmentId;
    }

    /**
     * @return UuidInterface
     */
    public function getCommitmentId(): UuidInterface {
        return $this->commitmentId;
    }

}