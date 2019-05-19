<?php


namespace list8\financialCommitment\events;


use list8\event\Event;
use Money\Money;

class FinancialCommitmentPaid extends Event {

    /**
     * @var Money
     */
    private $amount;

    /**
     * FinancialCommitmentCreated constructor.
     * @param Money $amount
     */
    public function __construct(Money $amount) {
        parent::__construct();
        $this->amount = $amount;
    }

}