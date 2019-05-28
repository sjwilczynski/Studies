<?php


namespace list9;

use Money\Money;
use Ramsey\Uuid\Uuid;

class Transaction {
    /**
     * @var Uuid
     *
     */
    private $id;

    /**
     * @var Money
     *
     */
    private $amount;

    //strange why not UUid?
    /**
     * @var string
     */
    private $fromAccount;

    /**
     * @var string
     */
    private $toAccount;

    /**
     * @var Status
     */
    private $status;

    /**
     * Transaction constructor.
     * @param Uuid $id
     * @param Money $amount
     * @param string $fromAccount
     * @param string $toAccount
     * @param Status $status
     */
    public function __construct(Uuid $id, Money $amount, string $fromAccount, string $toAccount, Status $status) {
        $this->id = $id;
        $this->amount = $amount;
        $this->fromAccount = $fromAccount;
        $this->toAccount = $toAccount;
        $this->status = $status;
    }


}