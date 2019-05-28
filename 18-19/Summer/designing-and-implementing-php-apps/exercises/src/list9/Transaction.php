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

    /**
     * @return Uuid
     */
    public function getId(): Uuid {
        return $this->id;
    }

    /**
     * @return Money
     */
    public function getAmount(): Money {
        return $this->amount;
    }

    /**
     * @param Money $amount
     */
    public function setAmount(Money $amount): void {
        $this->amount = $amount;
    }

    /**
     * @return string
     */
    public function getFromAccount(): string {
        return $this->fromAccount;
    }

    /**
     * @param string $fromAccount
     */
    public function setFromAccount(string $fromAccount): void {
        $this->fromAccount = $fromAccount;
    }

    /**
     * @return string
     */
    public function getToAccount(): string {
        return $this->toAccount;
    }

    /**
     * @param string $toAccount
     */
    public function setToAccount(string $toAccount): void {
        $this->toAccount = $toAccount;
    }

    /**
     * @return Status
     */
    public function getStatus(): Status {
        return $this->status;
    }

    /**
     * @param Status $status
     */
    public function setStatus(Status $status): void {
        $this->status = $status;
    }


}