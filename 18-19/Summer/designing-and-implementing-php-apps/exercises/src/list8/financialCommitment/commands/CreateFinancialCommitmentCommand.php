<?php


namespace list8\financialCommitment\commands;


use Money\Money;
use Ramsey\Uuid\UuidInterface;

class CreateFinancialCommitmentCommand extends FinancialCommitmentCommand {

    /**
     * @var Money
     */
    private $amount;

    /**
     * PayFinancialCommitmentCommand constructor.
     * @param Money $amount
     */
    public function __construct(UuidInterface $uuid, Money $amount) {
        parent::__construct($uuid);
        $this->amount = $amount;
    }

    /**
     * @return Money
     */
    public function getAmount(): Money {
        return $this->amount;
    }

}